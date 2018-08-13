$(function () {
    //初始化对象
    let login_model = $('#login_model');
    let input_panel = $('#input_panel');
    let login_btn = $('#login_btn');
    let logout_btn = $('#logout_btn');
    let main_model = $('#main_model');
    let admin_name = $('#admin_name');

    // 初始化页码
    $.cookie('user_page',0,{path:'/'});
    $.cookie('anchor_page',0,{path:'/'});
    $.cookie('ban_page',0,{path:'/'});

    // 事件绑定
    login_btn.on('click',admin_login);
    logout_btn.on('click',logout);

    $('#swich-li-user').on('click',load_user);
    $('#swich-li-anchor').on('click',load_anchor);
    $('#swich-li-ban').on('click',load_ban);
    $('#user-pagination').on('click','.user-page-list',change_user_page);
    $('#anchor-pagination').on('click','.anchor-page-list',change_anchor_page);
    $('#ban-pagination').on('click','.ban-page-list',change_ban_page);

    $('#user-table').on('click','.ban-user-btn',ban_user);
    $('#anchor-table').on('click','.ban-anchor-btn',ban_anchor);
    $('#user-table').on('click','.delete-user-btn',delete_user);
    $('#anchor-table').on('click','.delete-anchor-btn',delete_anchor);
    $('#ban-table').on('click','.unban-btn',unban);

    //初始化样式
    login_model.css({
        'height':window.innerHeight+'px',
        'min-width':'800px',
        'min-height':'600px',
    });
    main_model.css({
        'display':'none'
    });
    check_user_display();

    input_panel.css({
       'margin-top': login_model.height()/2-input_panel.height()/1.5+'px',
    });

    // 自适应设置
    window.onresize = function () {
        login_model.css({
            height:window.innerHeight+'px'
        });
        input_panel.css({
            'margin-top': login_model.height()/2-input_panel.height()/1.5+'px'
        });
    };

    //登录事件
    function admin_login() {
        let post_data = {};
        post_data.username = $('#admin_username').val();
        post_data.password = $('#admin_password').val();

        $.ajax({
            type:'post',
            data:post_data,
            url:'http://10.1.0.177:8080/admin/login',
            success:function (data) {
                if (data.code===1){
                    $.cookie('username',data.datas.username,{path:'/'});
                    $.cookie('password',data.datas.password,{path:'/'});
                    check_user_display();
                    load_user();
                }
                else {
                    alert("登录失败,用户名或密码错误")
                }
            },
            error:function () {
                alert('请求失败')
            }
        })
    }

    // 加载用户事件
    function load_user() {
        let post_data = {};
        post_data.username = $.cookie('username');
        post_data.password = $.cookie('password');
        post_data.page = $.cookie('user_page');
        $.ajax({
            data:post_data,
            url:'http://10.1.0.177:8080/admin/loadUser?size=5',
            success:function (data) {
                if (data.code===1){
                    // 显示数据
                    clear_data();
                    display_user(data);
                }
                else {
                    alert("登录失败,用户名或密码错误")
                }
            },
            error:function () {
                alert('请求失败')
            }
        })
    }
    // 加载主播事件
    function load_anchor() {
        let post_data = {};
        post_data.username = $.cookie('username');
        post_data.password = $.cookie('password');
        post_data.page = $.cookie('anchor_page');
        $.ajax({
            data:post_data,
            url:'http://10.1.0.177:8080/admin/loadAnchor',
            success:function (data) {
                if (data.code===1){
                    // 显示数据
                    clear_data();
                    display_anchor(data);
                }
                else {
                    alert("登录失败,用户名或密码错误")
                }
            },
            error:function () {
                alert('请求失败')
            }
        })
    }
    // 加载被封禁用户事件
    function load_ban() {
        let post_data = {};
        post_data.username = $.cookie('username');
        post_data.password = $.cookie('password');
        post_data.page = $.cookie('ban_page');
        $.ajax({
            data:post_data,
            url:'http://10.1.0.177:8080/admin/loadBanUser',
            success:function (data) {
                if (data.code===1){
                    // 显示数据
                    clear_data();
                    display_ban(data);
                }
                else {
                    alert("登录失败,用户名或密码错误")
                }
            },
            error:function () {
                alert('请求失败')
            }
        })
    }

    // 显示用户信息的函数
    function check_user_display() {
        if ($.cookie('username')==='null' || $.cookie('username')===undefined){
            login_model.css({
                'display':'block'
            });
            main_model.css({
                'display':'none'
            });
        }
        else {
            admin_name.text($.cookie('username'));
            login_model.css({
                'display':'none'
            });
            main_model.css({
                'display':'block'
            });
        }
    }

    // 登出
    function logout() {
        $.cookie('username', null,{path:'/'});
        $.cookie('password',null,{path:'/'});
        check_user_display();
    }

    // 加载用户到页面
    function display_user(data) {
        $.each(data.datas.lists,function (key,value) {
            let html = `<tr class="table-tr">
                <td class="user-id-td">${value.id}</td>
                <td>${value.email}</td>
                <td>${value.nickName}</td>
                <td>${value.medal}</td>
                <td>${value.balance}</td>
                <td>${value.gender==0?'男':'女'}</td>
                <td>${fmtDate(value.createTime)}</td>
                <td><button class="btn btn-default ban-user-btn">封禁</button>&nbsp;&nbsp;<button class="btn btn-danger delete-user-btn">删除</button></td>
            </tr>
            `;
            $('#user-table').append(html);
        });
        if(data.datas.totalPages>12){
            if (data.datas.currentPage<4){
                for (let i=0;i<data.datas.currentPage+3;i++){
                    let a = `<li class="user-page-list"><a href="#">${i+1}</a></li>`;
                    $('#user-pagination').append(a);
                }
                let a = `<li class="user-page-list"><a href="#">12</a></li>`;
                $('#user-pagination').append(a);
            }
            else if (data.datas.currentPage>data.datas.totalPages-5){
                let a = `<li class="user-page-list"><a href="#">1</a></li>`;
                $('#user-pagination').append(a);
                for (let i=data.datas.currentPage-3;i<data.datas.totalPages;i++){
                    let a = `<li class="user-page-list"><a href="#">${i+1}</a></li>`;
                    $('#user-pagination').append(a);
                }
            }
            else {
                let a = `<li class="user-page-list"><a href="#">1</a></li>`;
                $('#user-pagination').append(a);
                for (let i=data.datas.currentPage-3;i<data.datas.currentPage+3;i++){
                    let a = `<li class="user-page-list"><a href="#">${i+1}</a></li>`;
                    $('#user-pagination').append(a);
                }
                a = `<li class="user-page-list"><a href="#">12</a></li>`;
                $('#user-pagination').append(a);
            }
        }else {
            for(let i=0;i<data.datas.totalPages;i++){
                let a = `<li class="user-page-list"><a href="#">${i+1}</a></li>`;
                $('#user-pagination').append(a);
            }
        }
        $.each($('.user-page-list'),function (key,item) {
            if (parseInt(item.innerText)===parseInt(data.datas.currentPage+1)){
                $(item).addClass('active');
            }
        })
    }

    // 加载主播到页面
    function display_anchor(data) {
        $.each(data.datas.lists,function (key,value) {
            let html = `<tr class="table-tr">
                <td class="anchor-id-td">${value.id}</td>
                <td>${value.email}</td>
                <td>${value.nickName}</td>
                <td>${value.medal}</td>
                <td>${value.balance}</td>
                <td>${value.gender==0?'男':'女'}</td>
                <td>${value.liveNo}</td>
                <td><button class="btn btn-default ban-anchor-btn">封禁</button>&nbsp;&nbsp;<button class="btn btn-danger delete-anchor-btn">删除</button></td>
            </tr>
            `;
            $('#anchor-table').append(html);
        });
        if(data.datas.totalPages>12){
            if (data.datas.currentPage<4){
                for (let i=0;i<data.datas.currentPage+3;i++){
                    let a = `<li class="anchor-page-list"><a href="#">${i+1}</a></li>`;
                    $('#anchor-pagination').append(a);
                }
                let a = `<li class="anchor-page-list"><a href="#">12</a></li>`;
                $('#anchor-pagination').append(a);
            }
            else if (data.datas.currentPage>data.datas.totalPages-5){
                let a = `<li class="anchor-page-list"><a href="#">1</a></li>`;
                $('#anchor-pagination').append(a);
                for (let i=data.datas.currentPage-3;i<data.datas.totalPages;i++){
                    let a = `<li class="anchor-page-list"><a href="#">${i+1}</a></li>`;
                    $('#anchor-pagination').append(a);
                }
            }
            else {
                let a = `<li class="anchor-page-list"><a href="#">1</a></li>`;
                $('#anchor-pagination').append(a);
                for (let i=data.datas.currentPage-3;i<data.datas.currentPage+3;i++){
                    let a = `<li class="anchor-page-list"><a href="#">${i+1}</a></li>`;
                    $('#anchor-pagination').append(a);
                }
                a = `<li class="anchor-page-list"><a href="#">12</a></li>`;
                $('#anchor-pagination').append(a);
            }
        }else {
            for(let i=0;i<data.datas.totalPages;i++){
                let a = `<li class="anchor-page-list"><a href="#">${i+1}</a></li>`;
                $('#anchor-pagination').append(a);
            }
        }
        $.each($('.anchor-page-list'),function (key,item) {
            if (parseInt(item.innerText)===parseInt(data.datas.currentPage+1)){
                $(item).addClass('active');
            }
        })
    }

    // 加载封禁用户到页面
    function display_ban(data){
        $.each(data.datas.lists,function (key,value) {
            let html = `<tr class="table-tr">
                <td class="ban-id-td">${value.id}</td>
                <td>${value.email}</td>
                <td>${value.nickName}</td>
                <td><button class="btn btn-default unban-btn">解除</button>&nbsp;&nbsp;</td>
            </tr>
            `;
            $('#ban-table').append(html);
        });
        if(data.datas.totalPages>12){
            if (data.datas.currentPage<4){
                for (let i=0;i<data.datas.currentPage+3;i++){
                    let a = `<li class="ban-page-list"><a href="#">${i+1}</a></li>`;
                    $('#ban-pagination').append(a);
                }
                let a = `<li class="ban-page-list"><a href="#">12</a></li>`;
                $('#ban-pagination').append(a);
            }
            else if (data.datas.currentPage>data.datas.totalPages-5){
                let a = `<li class="ban-page-list"><a href="#">1</a></li>`;
                $('#ban-pagination').append(a);
                for (let i=data.datas.currentPage-3;i<data.datas.totalPages;i++){
                    let a = `<li class="ban-page-list"><a href="#">${i+1}</a></li>`;
                    $('#ban-pagination').append(a);
                }
            }
            else {
                let a = `<li class="ban-page-list"><a href="#">1</a></li>`;
                $('#ban-pagination').append(a);
                for (let i=data.datas.currentPage-3;i<data.datas.currentPage+3;i++){
                    let a = `<li class="ban-page-list"><a href="#">${i+1}</a></li>`;
                    $('#ban-pagination').append(a);
                }
                a = `<li class="ban-page-list"><a href="#">12</a></li>`;
                $('#ban-pagination').append(a);
            }
        }else {
            for(let i=0;i<data.datas.totalPages;i++){
                let a = `<li class="ban-page-list"><a href="#">${i+1}</a></li>`;
                $('#ban-pagination').append(a);
            }
        }
        $.each($('.ban-page-list'),function (key,item) {
            if (parseInt(item.innerText)===parseInt(data.datas.currentPage+1)){
                $(item).addClass('active');
            }
        })
    }

    // 清空
    function clear_data() {
        $(".table-tr").remove();
        $("#user-pagination").text("");
        $("#anchor-pagination").text("");
        $("#ban-pagination").text("");
    }

    // 时间戳转日期
    function fmtDate(obj){
        var date =  new Date(obj);
        var y = 1900+date.getYear();
        var m = "0"+(date.getMonth()+1);
        var d = "0"+date.getDate();
        return y+"-"+m.substring(m.length-2,m.length)+"-"+d.substring(d.length-2,d.length);
    }

    // 翻页
    function change_user_page(){
        $.cookie('user_page',parseInt(this.innerText-1))
        load_user();
    }
    function change_anchor_page(){
        $.cookie('anchor_page',parseInt(this.innerText-1))
        load_anchor();
    }
    function change_ban_page(){
        $.cookie('ban_page',parseInt(this.innerText-1))
        load_ban();
    }

    // 封禁用户
    function ban_user(){
        let post_data = {}
        post_data.id = $('.user-id-td').eq($('.ban-user-btn').index(this)).text();
        post_data.username = $.cookie('username');
        post_data.password = $.cookie('password');
        $.ajax({
            type:'get',
            data:post_data,
            url:'http://10.1.0.177:8080/admin/banUser',
            success:function (data) {
                if (data.code===1){
                    alert('操作成功')
                    load_user();
                } else {
                    alert('操作失败')
                }
            },
            error:function () {
                alert('无法连接')
            }
        })
    }
    // 封禁主播
    function ban_anchor(){
        let post_data = {}
        post_data.id = $('.anchor-id-td').eq($('.ban-anchor-btn').index(this)).text();
        post_data.username = $.cookie('username');
        post_data.password = $.cookie('password');
        $.ajax({
            type:'get',
            data:post_data,
            url:'http://10.1.0.177:8080/admin/banUser',
            success:function (data) {
                if (data.code===1){
                    alert('操作成功')
                    load_anchor();
                } else {
                    alert('操作失败')
                }
            },
            error:function () {
                alert('无法连接')
            }
        })
    }
    // 删除用户
    function delete_user(){
        if (window.confirm("是否删除？")){
            let post_data = {}
            post_data.id = $('.user-id-td').eq($('.delete-user-btn').index(this)).text();
            post_data.username = $.cookie('username');
            post_data.password = $.cookie('password');
            $.ajax({
                type:'get',
                data:post_data,
                url:'http://10.1.0.177:8080/admin/deleteUser',
                success:function (data) {
                    if (data.code===1){
                        alert('操作成功')
                        load_user();
                    } else {
                        alert('操作失败')
                    }
                },
                error:function () {
                    alert('无法连接')
                }
            })
        }
    }
    // 删除主播
    function delete_anchor(){
        if (window.confirm("是否删除？")){
            let post_data = {}
            post_data.id = $('.anchor-id-td').eq($('.delete-anchor-btn').index(this)).text();
            post_data.username = $.cookie('username');
            post_data.password = $.cookie('password');
            $.ajax({
                type:'get',
                data:post_data,
                url:'http://10.1.0.177:8080/admin/deleteUser',
                success:function (data) {
                    if (data.code===1){
                        alert('操作成功')
                        load_anchor();
                    } else {
                        alert('操作失败')
                    }
                },
                error:function () {
                    alert('无法连接')
                }
            })
        }
    }
    
    // 解封
    function unban() {
        let post_data = {}
        post_data.id = $('.ban-id-td').eq($('.unban-btn').index(this)).text();
        post_data.username = $.cookie('username');
        post_data.password = $.cookie('password');
        $.ajax({
            type:'get',
            data:post_data,
            url:'http://10.1.0.177:8080/admin/freeUser',
            success:function (data) {
                if (data.code===1){
                    alert('操作成功')
                    load_ban();
                } else {
                    alert('操作失败')
                }
            },
            error:function () {
                alert('无法连接')
            }
        })
    }
});