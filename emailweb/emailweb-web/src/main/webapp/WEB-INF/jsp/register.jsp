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
    <form id="loginForm" class="form-signin" <%--action="/register"--%> role="form" method="post">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-user"></i> 用户登录</h2>
        <div class="form-group has-success has-feedback">
            <input type="text" name="registeraccount" class="form-control" id="registeraccount" placeholder="请输入登录账号" autofocus>
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="text" name="registerpassword" class="form-control" id="registerpassword" placeholder="请输入登录密码" style="margin-top:10px;">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="text" name="registerpasswordsure" class="form-control" id="registerpasswordsure" placeholder="请确认登录密码" style="margin-top:10px;">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <select class="form-control" >
                <option value="member">用户</option>
                <option value="user">管理</option>
            </select>
        </div>
       <%-- <div class="checkbox">
            <label>
                <input type="checkbox" value="remember-me"> 记住我
            </label>
            <br>
            <label>
                忘记密码
            </label>
            <label style="float:right">
                <a href="/user/resigter">我要注册</a>
            </label>
        </div>--%>
        <a class="btn btn-lg btn-success btn-block" onclick="dologin()" > 注册</a>
    </form>
</div>
<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/layer/layer.js"></script>
<script>
    function dologin() {
        var registeraccount=$("#registeraccount").val();
       if(registeraccount==""){
           layer.msg("用户名不能为空", {time:1500, icon:5, shift:6}, function () {

           });
           return ;
       }
       var registerpassword=$("#registerpassword").val();
       if (!/^[a-zA-Z]\w{5,17}$/.test(registerpassword)){
           layer.msg("以字母开头，长度在6~18之间，只能包含字母、数字和下划线", {time:1500, icon:5, shift:6}, function () {
           });
           return ;
       }
       var registerpasswordsure=$("#registerpasswordsure").val();
       if (registerpasswordsure!=registerpassword){
           layer.msg("两次密码不一样", {time:1500, icon:5, shift:6}, function () {
           });
           return ;
       }


       //js中除了var声明的变量其他都是全局变量，默认是属于window的，比如方法中有username="zhangsan"，就相当于window.username="zhangsan"

       $.ajax({
            type:"POST",
            url:"asyregister",
           data:{
                "username":registeraccount,
               "passwd":registerpassword
            },
           beforeSend:function () {
               loadingInex=layer.msg("处理中",{icon:16});
           },
           success:function (result) {
                layer.close(loadingInex);
                if (result.success==1){
                    layer.confirm("注册成功,前往登录？",  {icon: 3, title:'提示'}, function(cindex){
                        window.location.href="login";
                        layer.close(cindex);
                    }, function(cindex){
                        layer.close(cindex);
                    });
                }else {
                    layer.alert("系统繁忙，请稍后再试", function(index){
                        layer.close(index);
                    });

                }
           }

        })
    }
</script>
</body>
</html>