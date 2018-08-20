$(function () {
    //进入页面后连接socket
    var socket = new SockJS('http://localhost:8080/rimilive');
    var stompClient = Stomp.over(socket);

    // 绑定事件
    $('#liveroom_header_focus').on('click',swich_to_unfocus);
    $('#liveroom_header_nofocus').on('click',swich_to_focus);
    $('#logout_btn').on('click',logout);

    //初始化窗口大小
    var canvas = $('#canvas');
    var video_panel = $('#video');
    var msg_panel_body = $('.live-msg-display-panel');
    var msg_panel_footer = $('.live-msg-input-panel');
    var video_panel_width = video_panel.css('width').substring(0,video_panel.css('width').indexOf('p'));
    video_panel.css('height',video_panel_width/16*9+'px');
    msg_panel_body.css('height',(video_panel_width/16*9-50)+'px');
    msg_panel_footer.css('height','50px');

    // 初始化样式
    $('#liveroom_header_nofocus').css('display','inline');
    $('#liveroom_header_focus').css('display','none');
    check_user_display();

    //载入页面获取信息
    let post_data = {};
    post_data.email = $.cookie('email');
    post_data.anchorId = getQueryString('anchorId');
    $.ajax({
        url:'http://localhost:8080/liveroom/getAnchorAndLiveRoom',
        data:post_data,
        success:function (data) {
            if (data.code===1){
                // 显示直播间的信息

                $('#header_liveroom_name').text(data.datas.liveRoom.livename);
                $('#header_liveroom_info').text(data.datas.liveRoom.info);
                $('#liveroom_focus').text(data.datas.liveRoom.hotnum);
                $("#tip").text(data.datas.liveRoom.id);
                if (data.datas.focus===true){
                    $('#liveroom_header_nofocus').css('display','none');
                    $('#liveroom_header_focus').css('display','inline');
                }
                $('#header_anchor_name').text(data.datas.anchor.nickName);
                let no = data.datas.liveRoom.livepic;
                // 播放器初始化
                var videoObject = {
                    autoplay:true,
                    container: '#video',//“#”代表容器的ID，“.”或“”代表容器的class
                    variable: 'player',//该属性必需设置，值等于下面的new chplayer()的对象
                    flashplayer:true,//如果强制使用flashplayer则设置成true
                    video:[
                        ['rtmp://192.168.241.132:1935/rimilive/h'+no, 'video/rtmp', '高清', 0],
                        ['rtmp://192.168.241.132:1935/rimilive/m'+no, 'video/rtmp', '标准', 10],
                        ['rtmp://192.168.241.132:1935/rimilive/l'+no, 'video/rtmp', '流畅', 0]
                    ]
                    // video:[
                    //     ['rtmp://192.168.241.132:1935/rimilive/h'+no, 'video/flv', '高清', 0],
                    //     ['rtmp://192.168.241.132:1935/rimilive/m'+no, 'video/flv', '标准', 10],
                    //     ['rtmp://192.168.241.132:1935/rimilive/l'+no, 'video/flv', '流畅', 0]
                    // ]//视频地址
                };
                player = new ckplayer(videoObject);
            }
            else {
                alert("出错了")
            }
        },
        error:function () {
            alert("获取信息失败！")
        }
    });

    // 初始化弹幕显示的层
    // set_barrage_display();
    //测试
    // var content = document.getElementById('canvas').getContext('2d');
    // content.fillStyle = 'white';
    // content.fillText('TEST',50,50);
    // content.stroke();
    // content.fill();

    //窗口大小变化时改变
    $(window).resize(function() {
        //使直播视频的宽度高度为16:9
        video_panel_width = video_panel.css('width').substring(0,video_panel.css('width').indexOf('p'));
        video_panel.css('height',video_panel_width/16*9+'px');
        msg_panel_body.css('height',(video_panel_width/16*9-50)+'px');
        msg_panel_footer.css('height','50px');
        set_barrage_display();
    });
    // 播放窗口改变时
    video_panel.resize(function () {
        set_barrage_display();
    });
    //发送弹幕信息
    $("#sender").on("click",function(){
        var msg_text = $("#msgText");
        var text=msg_text.val();
        msg_text.val("");
        var room=$("#tip").text();
        stompClient.send("/messageReciver",{},JSON.stringify({'msg':text,'nickName':$.cookie("nickName"),'roomId':room}));
    });
    //订阅者接受消息
    stompClient.connect({}, function(frame) {
        // 注册发送消息
        stompClient.subscribe('/topic/'+$("#tip").text(), function(data) {
            var str=data.body;
            var obj=JSON.parse(str);
            if(obj.nickName!=="null"){
                $("#chat-ul").append(`
              <li class="chat_li" style="list-style: none;margin-left: -30px">
                <span>${obj.nickName}:${obj.msg}</span>
              </li>
             `);
                scorll_to_bottom();
            }else{
                alert("请先登录，或者联系管理员");
            }
        });
    });

    function set_barrage_display() {
        canvas.css({
            top:video_panel.offset().top+'px',
            left:video_panel.offset().left+'px',
            width:video_panel_width+'px',
            height:(video_panel_width/16*9-50)+'px'
        });
    }

    // 关注按钮切换
    // 取消关注函数
    function swich_to_unfocus() {
        $('#liveroom_header_nofocus').css('display','inline');
        $('#liveroom_header_focus').css('display','none');
        let post_data = {};
        post_data.email = $.cookie('email');
        post_data.anchorId = getQueryString('anchorId');
        post_data.flag = 0;
        $.ajax({
            url:'http://localhost:8080/liveroom/focus',
            data:post_data,
            success:function (data) {
                if (data.code===1){
                    alert("已取消关注！");
                    $('#liveroom_focus').text(parseInt($('#liveroom_focus').text())-1);
                }
            }
        })
    }
    // 关注主播函数
    function swich_to_focus() {
        $('#liveroom_header_nofocus').css('display','none');
        $('#liveroom_header_focus').css('display','inline');
        let post_data = {};
        post_data.email = $.cookie('email');
        post_data.anchorId = getQueryString('anchorId');
        post_data.flag = 1;
        $.ajax({
            url:'http://localhost:8080/liveroom/focus',
            data:post_data,
            success:function () {
                alert("关注成功！");
                $('#liveroom_focus').text(parseInt($('#liveroom_focus').text())+1);
            }
        })
    }

    //截取地址栏中url的参数值
    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null)
            return unescape(r[2]);
        return null;
    }

    // 显示导航栏用户信息的函数
    function check_user_display() {
        if ($.cookie('email')==='null' || $.cookie('email')===undefined){
            $('.login_undisplay_model').css('display','block');
            $('.login_display_model').css('display','none');
        }
        else {
            $('.login_undisplay_model').css('display','none');
            $('.login_display_model').css('display','block');
            $(".login_display_model img").attr("src","http://localhost:8080/head/"+$.cookie('headImg'));
            $("#show_name").text($.cookie('nickName'));
        }
    }

    // 登出
    function logout() {
        $.cookie('email', null,{path:'/'});
        $.cookie('password',null,{path:'/'});
        $.cookie('nickName',null,{path:'/'});
        $.cookie('headImg',null,{path:'/'});
        //check_user_display();
        // 跳转页面
        window.location.href = '../index.html';
    }
    
    // 控制msg_panel滚动条
    function scorll_to_bottom() {
        $('.live-msg-display-panel').scrollTop($('.live-msg-display-panel')[0].scrollHeight);
    }
});
