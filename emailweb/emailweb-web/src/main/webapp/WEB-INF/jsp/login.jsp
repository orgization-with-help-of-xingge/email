<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
    <link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/login.css">
    <link rel="stylesheet" href="${APP_PATH}/autoemail/autoMail.1.0.min.js">

    <style>

    </style>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <div><a class="navbar-brand" href="index.html" style="font-size:32px;">企业级邮件系统</a></div>
        </div>
    </div>
</nav>

<div class="container">
    <form id="loginForm" class="form-signin" action="/doLogin" role="form" method="post">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-user"></i> 管理员登录界面</h2>
        <div class="form-group has-success has-feedback">
            <input type="text" name="username" class="form-control" id="loginaccount" placeholder="请输入登录账号" onclick="autoMail()" autofocus>
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="text" name="password" class="form-control" id="loginpassword" placeholder="请输入登录密码" style="margin-top:10px;">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <select class="form-control" >
                <option value="user">管理</option>
            </select>
        </div>
        <div class="checkbox">
            <label>
                <input type="checkbox" value="remember-me"> 记住我
            </label>
            <br>
            <label>
                忘记密码
            </label>
            <label style="float:right">
                <a href="reg.html">我要注册</a>
            </label>
        </div>
        <a class="btn btn-lg btn-success btn-block" onclick="dologin()" > 登录</a>
    </form>
</div>
<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/layer/layer.js"></script>
<script src="${APP_PATH}/autoemail/autoMail.1.0.min.js"></script>

<script>

    function autoMail(){
        $('#loginaccount').autoMail({
            emails:['qq.com','163.com','126.com','sina.com','sohu.com','yahoo.cn','gmail.com','hotmail.com','live.cn']
        });
    }


    function dologin() {
        var loginaccount=$("#loginaccount").val();
       if(loginaccount==""){
           layer.msg("用户名不能为空", {time:1500, icon:5, shift:6}, function () {

           });
           return ;
       }
       var loginpassword=$("#loginpassword").val();
       if (loginpassword == "") {
           layer.msg("密码不能为空", {time:1500, icon:5, shift:6}, function () {
           });
           return ;
       }
       //js中除了var声明的变量其他都是全局变量，默认是属于window的，比如方法中有username="zhangsan"，就相当于window.username="zhangsan"

       $.ajax({
            type:"POST",
            url:"asylogin",
           data:{
                "username":loginaccount,
               "passwd":loginpassword
            },
           beforeSend:function () {
               loadingInex=layer.msg("处理中",{icon:16});
           },
           success:function (result) {
                layer.close(loadingInex);
                if (result.success==1){
                    window.location.href="${APP_PATH}/admin/index";
                }else {
                    layer.alert("用户名密码错误", function(index){
                        layer.close(index);
                    });

                }
           }

        })
    }
</script>
</body>
</html>