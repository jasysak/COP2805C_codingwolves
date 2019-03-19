var parsedData = {
    Code:'Failed',
    FilesProcessed: 0,
    FilesParsed: 0,
    Data :[]
}; //Global Variable

var persistantData={

};
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
                    parsedData["FilesProcessed"] = iteration;
                    parsedData["FilesParsed"] = iteration-filesSkipped;
                    parsedData["Data"].push({
                        FileName: individualFile.name, //name of the file we are uploading
                        CheckSum: checkSum, //MD5 hash
                        Path: "FakeWebPath", /*Path of the file in local system.
                        There is no way to retrieve this without creating security issues.*/
                        ParsedData:lines //insert sanitized array containing strings
                    });
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
        dataType: 'json',
        success: function (data) {
            console.log('Update',data);
            delete senderObject;
            resetAjaxSender(parsedData);
        },
        error: function (e) {
            console.log('error');
        }

    });

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