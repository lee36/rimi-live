$(function () {
    $('#login_msg').css('display','none');
   // 登录
    $('#login_btn').on('click',login);

    // 登录函数
    function login() {
        post_data = {};
        post_data.email = $('#inputEmail3').val();
        post_data.password = $('#inputPassword3').val();
        $.ajax({
            type:"post",
            url:'http://localhost:8080/user/login',
            data:post_data,
            success:function (res) {
                if (res.code===1){
                    $.cookie('email', res.datas.email);
                    $.cookie('password',res.datas.password);
                    $.cookie('nickName',res.datas.nickName);
                    $.cookie('headImg',res.datas.headImg);
                    $('#login_display_reset').trigger('click');
                }
                else {
                    $('#login_msg').css('display','inline');
                }
            }
        })
    }
});