$(function () {
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

    // 更新用户信息
    function update_user() {
        var post_data = {};
        post_data.id = $('#hidden_id').val();
        post_data.nickName = $('#update_nickname').val();
        post_data.password = $('#update_password').val();
        if ($('#update_rpassword').val()===post_data.password) {
            if (post_data.id.substring(0,1)==='a'){
                $.ajax({
                    type:"post",
                    url:"http://localhost:8080/anchor/updateAnchor",
                    data:post_data,
                    success:function (data) {
                        alert("修改成功，请重新登录");
                        logout();
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
                        alert("修改成功，请重新登录");
                        logout();
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
            url:"http://localhost:8080/user/get",
            data:{
                "email":$.cookie("email")
            },
            success:function (data) {
                //获取到个人信息时
                if (data.code===1){
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
            $("#show_headImg").attr('src',$.cookie('headImg'));
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

    }

    // 时间戳转日期
    function fmtDate(obj){
        var date =  new Date(obj);
        var y = 1900+date.getYear();
        var m = "0"+(date.getMonth()+1);
        var d = "0"+date.getDate();
        return y+"-"+m.substring(m.length-2,m.length)+"-"+d.substring(d.length-2,d.length);
    }
});