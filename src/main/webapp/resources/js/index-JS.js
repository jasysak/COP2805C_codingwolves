var parsedData = {
    Code:'Failed',
    FilesProcessed: 0,
    FilesParsed: 0,
    Data :[]
};

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
                };
            })(file);
            reader.readAsText(file); //read the next file
        }
        }, false);
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

/**
 *
 * @returns {*} array without null, undefined, whitespace or NaN
 * @param rawArray An uncorrected array
 * @example validateArray (["1","hello", 3, null, "", undefined])
 *
 */
function validateArray (rawArray){
    return rawArray.filter(Boolean);
}
