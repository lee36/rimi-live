# rimi-live
rimi直播项目

## 项目环境
[![Travis](https://img.shields.io/badge/nginx-1.15.1-brightgreen.svg)](https://github.com/nginx/nginx)
[![Travis](https://img.shields.io/badge/spring--boot-2.0.3-lightgrey.svg)](https://github.com/nginx/nginx)
[![Travis](https://img.shields.io/badge/nginx--rtmp--module-all-blue.svg)](https://github.com/arut/nginx-rtmp-module)
[![Travis](https://img.shields.io/badge/JDK-1.8-red.svg)](https://www.java.com/zh_CN/)
<br>

使用以下代码下载nginx的模块，需要编译安装
> wget https://github.com/arut/nginx-rtmp-module/archive/master.zip
<br>

* 使用`druid`作为mysql的数据库连接池
* 使用`redis`作为缓存的数据库
* 页面中使用`ajax`和`websocket`
* 开发软件为`IntelliJ IDEA`
<br>

## 项目需求
1. 首页会推荐各个类型的直播（可以看到主播的名字和房间号以及缩略图）
2. 直播间的观众可以进行互动以及发送弹幕
3. 观众可以选择对主播进行关注，关注的主播开播时会自动提醒
4. 主播不直播是观众可以选择观看主播上传的视频
5. 直播画面可以进行设置（弹幕，清晰度）
6. 用户通过邮箱进行注册和激活
<br>

## 数据库表项
#### 普通用户
| 字段名 | 类型 | 备注 |
|:-------:|:-------------:|:----------:|
| id | String | 主键，用户的id，非自增 |
| email | String | 用户的邮箱，用于登录，不能重复 |
| nickname | String | 昵称，可以重复 |
| headimg | File | 账户的头像 |
| balance | String | 账户的余额 |
| medal | String | 账户的勋章 |
| status | int | 该用户的状态 |
| createtime | timestamp | 该用户创建的时间 |
<br>

#### 主播
| 字段名 | 类型 | 备注 |
|:-------:|:-------------:|:----------:|
| id | String | 主键，用户的id，非自增 |
| email | String | 用户的邮箱，用于登录，不能重复 |
| nickname | String | 昵称，可以重复 |
| headimg | File | 账户的头像 |
| phonenumber | String | 主播的手机号 |
| liveno | String | 直播间号 |
| medal | String | 账户的勋章 |
| status | int | 该用户的状态 |
| createtime | timestamp | 该用户创建的时间 |
<br>

#### 管理员
| 字段名 | 类型 | 备注 |
|:-------:|:-------------:|:----------:|
| id | String | 主键，管理员id，非自增 |
| username | String | 管理员用户名 |
| password | String | 管理员密码 |
<br>

#### 权限
| 字段名 | 类型 | 备注 |
|:-------:|:-------------:|:----------:|
| id | String | ID |
| name | String | 权限名 |
| url | String | 权限url |
<br>

#### 用户-权限
| 字段名 | 类型 | 备注 |
|:-------:|:-------------:|:----------:|
| id | String | ID |
| userid | String | 账户的id |
| auth | String | 权限 |
<br>

#### 直播间
| 字段名 | 类型 | 备注 |
|:-------:|:-------------:|:----------:|
| id | String | ID |
| livename | String | 直播间名 |
| type | int | 直播类型id（分类） |
| keyword | String | 直播间的关键字 |
| info | String | 直播间的介绍 |
| hotnum | long | 热度 |
| status | int | 直播间的状态 |
| livepic | String | 直播间的缩略图 |
<br>

#### 分类
| 字段名 | 类型 | 备注 |
|:-------:|:-------------:|:----------:|
| id | String | ID |
| typename | String | 类型名（分区） |
<br>

#### 视频
| 字段名 | 类型 | 备注 |
|:-------:|:-------------:|:----------:|
| id | String | ID |
| anchor | String | 主播名 |
| video | String | 视频的URL地址 |
<br>
