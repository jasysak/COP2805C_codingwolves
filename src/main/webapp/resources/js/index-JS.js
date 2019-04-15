var parsedData = {
    Code:'Failed',
    FilesProcessed: 0,
    FilesParsed: 0,
    Data :[]
}; //Global Variable that stores parsed data.

var persistentData=[]; //Global Variable that stores persistent data
function resetAjaxSender(ajaxSenderObject){
    ajaxSenderObject["Code"]="OK";
    ajaxSenderObject["FilesProcessed"] =0;
    ajaxSenderObject["FilesParsed"] = 0;
    ajaxSenderObject["Data"] = [];
}
$(document).ready(function () {
    $('.custom-select').change(function () {
        $('#width_tmp_option').html($('.custom-select option:selected').text());
        $(this).width($('#width_tmp_select').width());
    });
    $.ajax({
        type: 'POST',
        contentType : "application/json",
        url: "initializer",
        success: function(data){
            console.log(data);
            createPersistentObject(persistentData,data);
            console.log(persistentData);
        },
        error:function (e) {
            console.log('Client side database error');
        }
    });
    inputFiles = document.querySelector('input[type="file"]');
    inputFiles.addEventListener('change',function(evt){
        files = evt.target.files;//all the files that have been uploaded
        var filesSkipped = 0; //keep track of files that
        for (var iteration = 0, file ; file = files[iteration];iteration++) {
            if (!file.type.match('text.*')) {
                filesSkipped++; //if the file format is not a txt then skip the for loop
                continue; //go to next iteration
            }
            reader = new FileReader(); //declare a reader object from FileReader API
            reader.onload = (function (individualFile) {
                return function (e) {
                    lines = e.target.result.toString().replace(/\W/g, ' ').split(/\s+/).map(function (line) {
                        return validateArray(line.split(',')).toString().toLowerCase();
                    });
                    lines.push("##$@@");
                    checkSum = CryptoJS.MD5(e.target.result).toString(); //checksum using Crypto JS package
                    parsedData["Code"]='OK'; //at least one file has been processed
                    insertIntoObject(persistentData,parsedData,individualFile.name,
                        checkSum,"FakeWebPath",lines,iteration,filesSkipped);
                    $('.container').append('<nav class="navbar navbar-light" ' +
                        'style="background-color: #e3f2fd;">' +
                        '<p class="navbar-brand" style="width: 75%;">' +individualFile.name+'</p>'+
                        '<button class="btn btn-outline-danger" type="button" ' +
                        'id ="'+individualFile.name+'" style="float: right;">' +
                        'Delete' + '</button>' + '</nav>');
                };
            })(file);

            reader.readAsText(file); //read the next file
        }

    },false);
});
$(document).on('click','#searchbtn',function(){
    $('#searchModal').modal('show');
});
$(document).on('click','#reset',function(){
    console.log(JSON.stringify(parsedData));
});

$(document).on('click','#adminbtn',function(){
    $('#adminModal').modal('show');
});

$(document).on('click','#upload',function (event) {
    var senderObject ={};
    senderObject["code"]=parsedData["Code"];
    senderObject["filesProcessed"] = parsedData["FilesProcessed"];
    senderObject["filesParsed"]=parsedData["FilesParsed"];
    senderObject["fileName"]= mergeArray(parsedData,"FileName");
    senderObject["checkSum"]=mergeArray(parsedData,"CheckSum");
    senderObject["path"]=mergeArray(parsedData,"Path");
    senderObject["parsedData"]=mergeArray(parsedData,"ParsedData");
    console.log(senderObject);
    $.ajax({
        type: 'POST',
        contentType : "application/json",
        url: "performMagic",
        data: JSON.stringify(senderObject),
        success: function (e) {
            console.log('Sender', senderObject)
            console.log('Update');
            insertIntoPersistentObject(persistentData,parsedData);
            senderObject = {};
            resetAjaxSender(parsedData);
            console.log('delparsed', parsedData);
            console.log('delsender',senderObject)
        },
        error: function (e) {
            console.log('error');
        }
    });

});
$(document).on('click','.btn-outline-danger',function(){
    var fileName = $(this).attr('id');
    if(confirm("Are you sure you want to delete this?")){
        $.ajax({
            url:'delete',
            type: 'POST',
            contentType:'application/json',
            data: fileName,
            success: function (data) {
                console.log(data['actionPerformed']);
                removeFromPersistentObject(persistentData,fileName)
                console.log(persistentData);
            },
            error: function (e) {
              console.log('File cannot be deleted');
            }
        });
        $(this).closest('nav').remove();
    }
});
/**
 * The function accepts an array and returns filtered data
 *
 * @returns {*} array without null, undefined, whitespace or NaN
 * @param rawArray An uncorrected array
 * @example validateArray (["1","hello", 3, null, "", undefined])
 *
 */
function validateArray (rawArray){
    return rawArray.filter(Boolean);
}

/**
 * This function will accepted a complex nested object and flattened the object into an array.
 *
 * @param finalObject This is the object that needs to be flattened
 * @param objectKey This is the key of the object that needs to be flattened
 * @returns {Array} This returns the flattened array
 */
function mergeArray (finalObject, objectKey){
    var returnedArray = [];
    arrayLength = finalObject["Data"].length;
    finalObject["Data"].forEach(function (fileData) {
        if(!Array.isArray(fileData[objectKey])){
            returnedArray.push(fileData[objectKey]);
        }
        else{
            returnedArray = returnedArray.concat.apply(returnedArray,fileData[objectKey]);
        }
    });

    return returnedArray;
}

/**
 * This function converts the AJAX data from Spring MVC to a persistent data object
 * @param destinationObject This is the persistent data object
 * @param SourceObject This is the AJAX data object sent from the Spring Rest Controller
 */
function createPersistentObject (destinationObject, SourceObject){
    sourceObjectLength = SourceObject["fileNames"].length;
        if (sourceObjectLength <=0){
            destinationObject.push({
                FileName:'',
                CheckSum:''
            });
        }
        else{
            for (var i = 0; i<sourceObjectLength; i++){
                destinationObject.push({
                    FileName:SourceObject["fileNames"][i],
                    CheckSum:SourceObject["checksum"][i]
                });
                $('.container').append('<nav class="navbar navbar-light" ' +
                    'style="background-color: #e3f2fd;">' +
                    '<p class="navbar-brand" style="width: 75%;">' +destinationObject[i]["FileName"]+'</p>'+
                    '<button class="btn btn-outline-danger" type="button" ' +
                    'id ="'+destinationObject[i]["FileName"]+'" style="float: right;">' +
                    'Delete' + '</button>' + '</nav>');
            }
        }
}

function insertIntoObject(persistentObject, destinationObject,
                          fileName, Checksum, path, parsedData, iteration, filesSkipped){
    let insertOperation = false;
    let Operation ='';
    if (persistentObject <=0){
        persistentObject.push({
            FileName:'',
            CheckSum:''
        });
    }
    for (var i=0; i<persistentObject.length; i++){
        if(persistentObject[i]['FileName'].includes(fileName) &&
            persistentObject[i]['CheckSum'].includes(Checksum)){
            console.log("1o");
           insertOperation =false;
        }
        else if(persistentObject[i]['FileName'].includes(fileName) &&
            !persistentObject[i]['CheckSum'].includes(Checksum)){
            insertOperation = true;
            Operation = 'Update';
        }
        else{
            insertOperation = true;
            Operation = 'Add';
        }
    }
    console.log('insertOp',insertOperation)
    if(insertOperation){
        destinationObject["FilesProcessed"] = iteration;
        destinationObject["FilesParsed"] = iteration-filesSkipped;
        destinationObject["Data"].push({
            Operation: Operation,
            FileName: fileName, //name of the file we are uploading
            CheckSum: Checksum, //MD5 hash
            Path: path, /*Path of the file in local system.
                        There is no way to retrieve this without creating security issues.*/
            ParsedData:parsedData //insert sanitized array containing strings
        });
    }
}

function removeFromPersistentObject(persistentObject, fileName) {
    persistentLength = persistentObject.length;
    for(i = 0; i<persistentLength; i++){
     if(persistentObject[i]['FileName'].includes(fileName)){
         persistentObject.splice(i,1);
     }
    }
}

function insertIntoPersistentObject(persistentObject,parsedObject) {
    parsedObjectLength = parsedObject["Data"].length;
    //let j = persistentObject.length;
    for(i=0,j = persistentObject.length;i<parsedObjectLength;i++,j++){
        persistentObject[j]["FileName"]=parsedObject["Data"][i]["FileName"];
        persistentObject[j]["CheckSum"]=parsedObject["Data"][i]["CheckSum"];
    }
}