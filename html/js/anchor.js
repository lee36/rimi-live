
$(function () {
	/**
 * 上传图片显示
 */
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
    /**
     * 提交操作
     */
    $("#btn").on("click",function(e){
    	var formdata=new FormData();
    	formdata.append("nickName",$("#nickName").val());
    	formdata.append("email",$("#email").val());
    	formdata.append("phoneNumber",$("#phoneNumber").val());
    	formdata.append("password",$("#password").val());
    	formdata.append("gender",$("input[name='gender']").val());
    	formdata.append("file", $('#headImg')[0].files[0]);
    	$.ajax({
    		type:"post",
    		url:"http://localhost:8080/anchor/regist",
    		data:formdata,
    		processData: false,
    		cache: false,
    		contentType: false,
    		success:function(data){
    			alert(1);
    		}
    	});
    });
});
