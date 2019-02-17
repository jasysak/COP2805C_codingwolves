$(document).ready(function () {
    $('.custom-select').change(function () {
        $('#width_tmp_option').html($('.custom-select option:selected').text());
        $(this).width($('#width_tmp_select').width());
    });
});
$(document).on('click','#searchbtn',function(){
    $('#searchModal').modal('show');
});
$(document).on('click','#adminbtn',function(){
    $('#adminModal').modal('show');
})

