$(function () {
    // 初始化信息
    var head_img = $('#headImg');
    // 显示导航栏用户信息
    check_user_display();
    // 获取用户信息
    get_user_info();

    // 初始化CSS
    $('#login_msg').css('display','none');
    $('#update_nickname').val($.cookie('nickName'));

    // 初始化头像尺寸
    var display_img = $('#display_img');
    display_img.css('max-height','350px');
    display_img.css('height',display_img.css('width'));

    // 绑定事件
    $('#logout_btn').on('click',logout);
    $('.personal-info-list').on('click','#get_code_btn',get_code);
    $('#save_user_btn').on('click',update_user);
    head_img.on('change',display_file);
    $('.upload_img').on('click',upload_file);
    $('#save_liveroom_btn').on('click',update_liveroom);

    // 点击获取板块信息
    $('#update_liveroom_btn').on('click',get_block_list);

    // 触发headImg的点击事件
    function upload_file() {
        $('#headImg').trigger('click');
    }

    // 显示上传图片(html)
    function display_file() {
        // 设定图片的宽高
        display_img.css('height',display_img.css('width'));
        // 显示图片
        var id_temp = $('#hidden_id').val();
        var reader = new FileReader();
        reader.readAsDataURL(head_img.get(0).files[0]);
        reader.onload = function (ev) {
            display_img.attr('src',this.result);
            //提交图片
            var formdata=new FormData();
            formdata.append("id", id_temp);
            formdata.append("file", $('#headImg')[0].files[0]);
            if (id_temp.substring(0,1)==='a'){
                $.ajax({
                    type:"post",
                    url:"http://localhost:8080/anchor/updateAnchorImg",
                    data:formdata,
                    processData: false,
                    cache: false,
                    contentType: false,
                    success:function(data){
                        if (data.code===1){
                            // window.location.href = "";
                            alert("修改成功");
                        }
                        else {
                            alert("修改失败")
                        }

                    }
                });
            }
            else {
                $.ajax({
                    type:"post",
                    url:"http://localhost:8080/user/updateUserImg",
                    data:formdata,
                    processData: false,
                    cache: false,
                    contentType: false,
                    success:function(data){
                        if (data.code===1){
                            // window.location.href = "";
                            alert("修改成功");
                        }
                        else {
                            alert("修改失败")
                        }

                    }
                });
            }
        };
        // 放置快速多次修改
        $('#upload_img').off('click').removeClass('upload_img btn-info').addClass('btn-default').text('请稍后');
        setTimeout(function () {
            $('#upload_img').on('click',upload_file).removeClass('btn-default').addClass('upload_img btn-info').text('修改头像');
        },20000);
    }

    // 更新用户信息
    function update_user() {
        var post_data = {};
        post_data.id = $('#hidden_id').val();
        post_data.password = $('#update_password').val();
        // 如果昵称没有改变则不上传
        if ($('#update_nickname').val()!==$.cookie('nickName')) {
            post_data.nickName = $('#update_nickname').val();
        }
        if ($('#update_rpassword').val()===post_data.password) {
            if (post_data.id.substring(0,1)==='a'){
                $.ajax({
                    type:"post",
                    url:"http://localhost:8080/anchor/updateAnchor",
                    data:post_data,
                    success:function (data) {
                        if (data.code===1){
                            alert("修改成功，请重新登录");
                            logout();
                        }
                        else {
                            alert("修改失败");
                        }
                        // window.location.href = "";
                    },
                    error:function () {
                      alert("数据提交出错")
                    }
                });
            }
            else {
                $.ajax({
                    type:"post",
                    url:"http://localhost:8080/user/updateUser",
                    data:post_data,
                    success:function (data) {
                        if (data.code===1){
                            alert("修改成功，请重新登录");
                            logout();
                        }
                        else {
                            alert("修改失败");
                        }
                        // window.location.href = "";
                    },
                    error:function () {
                        alert("数据提交出错")
                    }
                });
            }
        }
       else {
            $('#login_msg').css('display','block').text('两次输入密码不相同');
        }
    }
    
    // 获取推流码
    function get_code() {
        $.ajax({
            url:"http://localhost:8080/user/getcode",
            data:{
                "email":$.cookie("email")
            },
            success:function (data) {
                if (data.code===1){
                    $('#list_show_code').text(data.datas).css('line-height','30px');
                }
            },
            error:function () {
                alert("推流码获取失败")
            }
        })
    }

    // 获取用户信息
    function get_user_info() {
        $.ajax({
            url:"http://10.1.0.177:8080/user/get",
            data:{
                "email":$.cookie("email")
            },
            success:function (data) {
                //获取到个人信息时
                if (data.code===1){
                    $("#display_img").attr('src',"http://localhost:8080/head/"+$.cookie('headImg'));
                    $("#show_headImg").attr('src',"http://localhost:8080/head/"+$.cookie('headImg'));
                    $('#list_show_email').text(data.datas.email);
                    $('#list_show_nickName').text(data.datas.nickName);
                    $('#list_show_medal').text(data.datas.medal);
                    $('#list_show_creatTime').text(fmtDate(data.datas.createTime));
                    $('#hidden_id').val(data.datas.id);
                    // 判断用户是否为主播
                    if ( data.datas.id.substring(0,1)==='a'){
                        // 生成主体数据
                        let html = `<li class="list-group-item">
                                        <div class="row">
                                            <div class="col-sm-4">
                                                <span>手机号</span>
                                            </div>
                                            <div class="col-sm-8">
                                                <span id="list_show_phoneNumber">${data.datas.phoneNumber}</span>
                                            </div>
                                        </div>
                                    </li>
                                    <li class="list-group-item">
                                        <div class="row">
                                            <div class="col-sm-4" id="get_code_btn_list">
                                                <button id="get_code_btn" class="btn btn-default">获取推流码</button>
                                            </div>
                                            <div class="col-sm-8">
                                                <span id="list_show_code"></span>
                                            </div>
                                        </div>
                                    </li>`;
                        $(".personal-info-list").append(html);
                        $('#anchor_personal_panel').append(
                            ` <!--修改直播间信息按钮-->
                <button class="btn btn-info" id="update_liveroom_btn" data-toggle="modal" data-target="#liveModal">修改直播间信息</button>`
                        )
                    }
                    else {
                        // 生成主体数据
                    }
                }
                else {
                    alert("获取数据失败")
                }
            },
            error:function (data) {
                alert("获取数据失败")
            }
        })
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
        check_user_display();
        // 跳转页面
        window.location.href = '../index.html';
    }

    // 时间戳转日期
    function fmtDate(obj){
        var date =  new Date(obj);
        var y = 1900+date.getYear();
        var m = "0"+(date.getMonth()+1);
        var d = "0"+date.getDate();
        return y+"-"+m.substring(m.length-2,m.length)+"-"+d.substring(d.length-2,d.length);
    }

    // 获取板块信息
    function get_block_list() {
        $.ajax({
            url:'http://localhost:8080/type/all',
            success:function (data) {
                if (data.code===1){
                    // 加载板块信息
                    $.each(data.datas,function (index,item) {
                        let html = `<option value="${item.id}">${item.typename}</option>>`;
                        $('#update_block').append(html);
                    });
                }
                else {
                    alert("出错啦")
                }
            },
            error:function () {
                alert("未获取到数据")
            }
        })
    }

    // 更新直播间信息
    function update_liveroom() {
        let post_data = {};
        post_data.id = $('#hidden_id').val();
        post_data.roomName = $('#update_name').val();
        post_data.info = $('#update_info').val();
        post_data.type = $('#update_block').val();
        $.ajax({
            type:'post',
            data:post_data,
            url:'http://localhost:8080/liveroom/updateRoom',
            success:function (data) {
                if (data.code===1){
                    alert('修改成功');
                    $('#save_liveroom_reset').trigger('click');
                }
                else {
                    alert('出错了');
                }
            },
            error:function () {
                alert('无法连接');
            }
        })
    }
});