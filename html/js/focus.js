$(function () {
    // 获取信息]
    let post_data = {};
    post_data.email = $.cookie('email');
    $.ajax({
        url: "http://10.1.0.177:8080/liveroom/userFocus",
        data: post_data,
        success: function (data) {
            if (data.code === 1) {
                $.each(data.datas, function (index, data) {
                    let html = `
                        <div class="col-sm-3">
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <div class="video-head">
                                        <a href="../live-room/live.html?anchorId=${data.anchor.id}">
                                          <img src="http://192.168.241.132/tmp/${data.liveRoom.livepic+'.jpg'}" width="100%" class="content"/>
                                        </a>
                                    </div>
                                    <div class="video-body">
                                        <div class="col-sm-7" style="text-align: left;">${data.liveRoom.livename}</div>
                                        <div class="col-sm-5" style="text-align: right;color: orange;">${data.liveRoom.status==1?"直播中":"未开播"}</div>
                                    </div>
                                    <div class="video-footer" style="text-align: left;color: lightslategray;">
                                        &nbsp;&nbsp;&nbsp;&nbsp;${data.anchor.nickName}
                                    </div>
                                </div>
                            </div>
                        </div>
                    `;
                    $('#focus_row').append(html);
                })
            }else {
                alert("出错了")
            }
        }
    });

    // 绑定事件
    $('#logout_btn').on('click',logout);
// 判断是否登录
    check_user_display();


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
        window.location.href = '../index.html';
    }
});