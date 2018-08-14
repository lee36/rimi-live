$(function () {
    //页面加载的时候加载直播间信息
    $.ajax({
        url:"http://localhost:8080/liveroom/all",
        data:{},
        success:function (data) {
            if (data.code==1) {
                var objs = data.datas;
                for (var i = 0; i < objs.length; i++) {
                    //生成class
                    var className = "row" + i;
                    $("#wrap").append(`
                   <div class="row ${className}">
                      <div class="title" style="text-align: left;font-size: 26px;border-bottom: 1px solid ghostwhite;">&nbsp;&nbsp;${objs[i].typeName}</div>
                   </div>
                `);
                    var liverooms = objs[i].liveRooms;
                    if (liverooms == null) {
                        $("." + className).append(`
                     <div class="col-sm-3">
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <h3>还没有主播入住</h3>
                                </div>
                            </div>
                        </div>
                    `)
                    } else {
                        //加载板块信息
                        for (var j = 0; j < liverooms.length; j++) {
                            $("." + className).append(`
                        <div class="col-sm-3">
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <div class="video-head">
                                        <img src="http://192.168.241.132/tmp/${liverooms[j].livepic}" width="100%" class="content"/>
                                    </div>
                                    <div class="video-body">
                                        <div class="col-sm-7" style="text-align: left;">${liverooms[j].livename}</div>
                                        <div class="col-sm-5" style="text-align: right;color: orange;">${objs[i].typeName}</div>
                                    </div>
                                    <div class="video-footer" style="text-align: left;color: lightslategray;">
                                        &nbsp;&nbsp;&nbsp;&nbsp;${liverooms[j].anchor.nickName}
                                    </div>
                                </div>
                            </div>
                        </div>
                    `)
                        }
                    }
                }
            }else{
                alert("空空如也");
            }

        }
    });
    //个人中心
    $("#user_display_model").on("click",showSelfCenter);
    // 绑定事件
    $('#logout_btn').on('click',logout);
    // 判断是否登录
    check_user_display();
    // 显示提示消息
    $('#login_msg').css('display','none');
   // 登录
    $('#login_btn').on('click',login);
    function showSelfCenter(){
       $.ajax({
          url:"http://localhost:8080/user/get",
          data:{
              "email":$.cookie("email")
          },
          success:function(data){
             if(data.code==1){
                 //跳转页面
                 window.location.href="http://www.baidu.com";
             }else{
                 alert(data.message);
             }
          }
       });
    }
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
                    $.cookie('email', res.datas.email,{path:'/'});
                    $.cookie('password',res.datas.password,{path:'/'});
                    $.cookie('nickName',res.datas.nickName,{path:'/'});
                    $.cookie('headImg',res.datas.headImg,{path:'/'});
                    $("#show_name").text(res.datas.nickName);
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
            $("#show_headImg").attr('src',"http://localhost:8080/head/"+$.cookie('headImg'));
            $("#show_name").text($.cookie('nickName'));

        }
    }
    
    // 登出
    function logout() {
        $.cookie('email', null,{path:'/'});
        $.cookie('password',null,{path:'/'});
        $.cookie('nickName',null,{path:'/'});
        $.cookie('headImg',null,{path:'/'});
        check_user_display();
    }
});