
$(function () {

	/**
 * 上传图片显示
 */
    var head_img = $('#headImg');
    var display_img = $('#display_img');

    // 初始化图片宽高
    display_img.css('height',display_img.css('width'));

    $('#upload_img').on('click',upload_file);
    $('#headImg').on('change',display_file);
    function upload_file() {
        $('#headImg').trigger('click');
    }
    function display_file() {
        // 设定图片的宽高
        display_img.css('height',display_img.css('width'));
        // 显示图片
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
        if (check_all()){
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
                    if (data.code===1){
                        // window.location.href = "";
                        alert("成功");
                    }
                    else {
                        $("#nickName_msg").text(data.datas.nickName);
                        $("#email_msg").text(data.datas.email);
                        $("#password_msg").text(data.datas.password);
                        $("#phoneNumber_msg").text(data.datas.phoneNumber);
                    }

                }
            });
        }
        else {
            alert("信息填写出错!");
        }

    });

    //表单检测
    //绑定事件
    $("#nickName").on('blur',check_nickname);
    $("#email").on('blur',check_email);
    $("#password").on('blur',check_password);
    $("#phoneNumber").on('blur',check_phoneNumber);
    $("#rpassword").on('blur',check_rpassword);

    //重置按钮绑定事件
    $('button[type="reset"]').on('click',function (e) {
        $("#nickName").val("");
        $("#email").val("");
        $("#password").val("");
        $("#phoneNumber").val("");
        $("#rpassword").val("");
    });

    // 定义变量
    var nickName_f = false;
    var email_f = false;
    var password_f = false;
    var phoneNumber_f = false;
    var rpassword_f = false;

    function check_nickname() {
        var reg = /^[\u4e00-\u9fa5]{4,8}$/;
        var res = reg.test($("#nickName").val());
        if (res){
            $("#nickName_msg").text("");
            nickName_f = true;
            return true;
        }
        else {
            $("#nickName_msg").text("昵称格式错误");
            nickName_f = false;
            return false;
        }
    }
    
    function check_email() {
        var reg = /^[0-9A-Za-z][\.-_0-9A-Za-z]*@[0-9A-Za-z]+(\.[0-9A-Za-z]+)+$/;
        var res = reg.test($("#email").val());
        if (res){
            $("#email_msg").text("");
            email_f = true;
            return true;
        }
        else {
            $("#email_msg").text("邮箱格式错误");
            email_f = false;
            return false;
        }
    }
    
    function check_password() {
        var reg = /^(\w){6,15}$/;
        var res = reg.test($("#password").val());
        if (res){
            $("#password_msg").text("");
            password_f = true;
            return true;
        }
        else {
            $("#password_msg").text("密码格式错误");
            password_f = false;
            return false;
        }
    }
    
    function check_phoneNumber() {
        var reg = /^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/;
        var res = reg.test($("#phoneNumber").val());
        if (res){
            $("#phoneNumber_msg").text("");
            phoneNumber_f = true;
            return true;
        }
        else {
            $("#phoneNumber_msg").text("手机号格式错误");
            phoneNumber_f = false;
            return false;
        }
    }
    
    function check_rpassword() {
        if ($("#password").val()===$("#rpassword").val()){
            $("#rpassword_msg").text("");
            rpassword_f = true;
            return true;
        }
        else {
            $("#rpassword_msg").text("两次输入的密码不匹配");
            rpassword_f = false;
            return false;
        }
    }
    
    //检查所有提交的数据
    function check_all() {
        if(nickName_f&&email_f&&phoneNumber_f&&password_f&&rpassword_f){
            return true;
        }
        else {
            return false;
        }
    }
});
