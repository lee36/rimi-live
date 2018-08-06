$(function () {
    // 绑定事件
    $('#logout_btn').on('click',logout);
    // 判断是否登录
    check_user_display();
    // 显示提示消息
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
                    check_user_display();
                }
                else {
                    $('#login_msg').css('display','inline');
                }
            }
        });
    }
    
    // 显示用户信息的函数
    function check_user_display() {
        if ($.cookie('email')==='null' || $.cookie('email')===undefined){
            $('.login_undisplay_model').css('display','block');
            $('.login_display_model').css('display','none');
        }
        else {
            $('.login_undisplay_model').css('display','none');
            $('.login_display_model').css('display','block');
        }
    }
    
    // 登出
    function logout() {
        $.cookie('email', null);
        $.cookie('password',null);
        $.cookie('nickName',null);
        $.cookie('headImg',null);
        check_user_display();
    }
});