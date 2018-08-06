
$(function () {
    //进入页面后连接socket
    var socket = new SockJS('http://localhost:8080/rimilive');
    var stompClient = Stomp.over(socket);
    //初始化窗口大小
    var video_panel = $('#video');
    var msg_panel_body = $('.live-msg-display-panel');
    var msg_panel_footer = $('.live-msg-input-panel');
    var video_panel_width = video_panel.css('width').substring(0,video_panel.css('width').indexOf('p'));
    video_panel.css('height',video_panel_width/16*9+'px');
    msg_panel_body.css('height',(video_panel_width/16*9-50)+'px');
    msg_panel_footer.css('height','50px');
    //窗口大小变化时改变
    $(window).resize(function() {
        //使直播视频的宽度高度为16:9
        video_panel_width = video_panel.css('width').substring(0,video_panel.css('width').indexOf('p'));
        video_panel.css('height',video_panel_width/16*9+'px');
        msg_panel_body.css('height',(video_panel_width/16*9-50)+'px');
        msg_panel_footer.css('height','50px');
    });
    //发送弹幕信息
    $("#sender").on("click",function(){
        var text=$("#msgText").val();
        alert(stompClient)
        stompClient.send("/messageReciver",{},JSON.stringify({'msg':text}));
        $("")
    });
});
