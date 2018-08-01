
$(function () {
    var head_img = $('#headImg');
    var display_img = $('#display_img');
    $('#upload_img').on('click',upload_file);
    $('#headImg').on('change',display_file);

    function upload_file() {
        $('#headImg').trigger('click');
    }

    function display_file() {
        var reader = new FileReader();
        reader.readAsDataURL(head_img.get(0).files[0]);
        reader.onload = function (ev) {
            display_img.attr('src',this.result);
        }
    }
});