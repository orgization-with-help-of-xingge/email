<%--
  Created by IntelliJ IDEA.
  User: leilei
  Date: 2019/7/14
  Time: 12:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="UTF-8">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/main.css">

    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
        table tbody tr:nth-child(odd){background:#F4F4F4;}
        table tbody td:nth-child(even){color:#C00;}
    </style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="#">企业邮件系统 - 角色维护</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li style="padding-top:8px;">
                    <div class="btn-group">
                        <button type="button" class="btn btn-default btn-success dropdown-toggle" data-toggle="dropdown">
                            <i class="glyphicon glyphicon-user"></i> 张三 <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="#"><i class="glyphicon glyphicon-cog"></i> 个人设置</a></li>
                            <li><a href="#"><i class="glyphicon glyphicon-comment"></i> 消息</a></li>
                            <li class="divider"></li>
                            <li><a href="index.html"><i class="glyphicon glyphicon-off"></i> 退出系统</a></li>
                        </ul>
                    </div>
                </li>
                <li style="margin-left:10px;padding-top:8px;">
                    <button type="button" class="btn btn-default btn-danger">
                        <span class="glyphicon glyphicon-question-sign"></span> 帮助
                    </button>
                </li>
            </ul>
            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="Search...">
            </form>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <div class="tree">
                <ul style="padding-left:0px;" class="list-group">
                    <%--<li class="list-group-item tree-closed" >
                        <a href="main.html"><i class="glyphicon glyphicon-dashboard"></i> 控制面板</a>
                    </li>--%>
                    <li class="list-group-item">
                        <span><i class="glyphicon glyphicon glyphicon-tasks"></i> 用户管理 <span class="badge" style="float:right">1</span></span>
                        <ul style="margin-top:10px;">
                            <li style="height:30px;">
                                <a href="/user/index"><i class="glyphicon glyphicon-user"></i> 用户维护</a>
                            </li>
                            <%--<li style="height:30px;">
                                <a href="role.html" style="color:red;"><i class="glyphicon glyphicon-king"></i> 角色维护</a>
                            </li>--%>
                            <%--<li style="height:30px;">
                                <a href="permission.html"><i class="glyphicon glyphicon-lock"></i> 许可维护</a>
                            </li>--%>
                        </ul>
                    </li>
                    <%--<li class="list-group-item tree-closed">
                        <span><i class="glyphicon glyphicon-ok"></i> 业务审核 <span class="badge" style="float:right">3</span></span>
                        <ul style="margin-top:10px;display:none;">
                            <li style="height:30px;">
                                <a href="auth_cert.html"><i class="glyphicon glyphicon-check"></i> 实名认证审核</a>
                            </li>
                            <li style="height:30px;">
                                <a href="auth_adv.html"><i class="glyphicon glyphicon-check"></i> 广告审核</a>
                            </li>
                            <li style="height:30px;">
                                <a href="auth_project.html"><i class="glyphicon glyphicon-check"></i> 项目审核</a>
                            </li>
                        </ul>
                    </li>
                    <li class="list-group-item tree-closed">
                        <span><i class="glyphicon glyphicon-th-large"></i> 业务管理 <span class="badge" style="float:right">7</span></span>
                        <ul style="margin-top:10px;display:none;">
                            <li style="height:30px;">
                                <a href="cert.html"><i class="glyphicon glyphicon-picture"></i> 资质维护</a>
                            </li>
                            <li style="height:30px;">
                                <a href="type.html"><i class="glyphicon glyphicon-equalizer"></i> 分类管理</a>
                            </li>
                            <li style="height:30px;">
                                <a href="process.html"><i class="glyphicon glyphicon-random"></i> 流程管理</a>
                            </li>
                            <li style="height:30px;">
                                <a href="advertisement.html"><i class="glyphicon glyphicon-hdd"></i> 广告管理</a>
                            </li>
                            <li style="height:30px;">
                                <a href="message.html"><i class="glyphicon glyphicon-comment"></i> 消息模板</a>
                            </li>
                            <li style="height:30px;">
                                <a href="project_type.html"><i class="glyphicon glyphicon-list"></i> 项目分类</a>
                            </li>
                            <li style="height:30px;">
                                <a href="tag.html"><i class="glyphicon glyphicon-tags"></i> 项目标签</a>
                            </li>
                        </ul>
                    </li>
                    <li class="list-group-item tree-closed" >
                        <a href="param.html"><i class="glyphicon glyphicon-list-alt"></i> 参数管理</a>
                    </li>--%>
                </ul>
            </div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="queryText" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="queryBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" onclick="deletes()" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='${APP_PATH}/admin/add'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <form id="roleForm" >
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="allSelBox" type="checkbox"></th>
                                <th>名称</th>
                                <th>姓名</th>
                                <th>邮件地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="tableContent">
                            </tbody>

                            <tfoot id="pageContent">
                            </tfoot>
                        </table>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>
<script src="${APP_PATH}/layer/layer.js"></script>
<script type="text/javascript">
    likeflag=false;
    $(function () {
        $(".list-group-item").click(function(){
            if ( $(this).find("ul") ) {
                $(this).toggleClass("tree-closed");
                if ( $(this).hasClass("tree-closed") ) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });

        getPage(1);

        $("#queryBtn").click(function () {
            if ($("#queryText").val()==""){
                likeflag=false;
                getPage(1);
            } else {
                likeflag=true;
                getPage(1);
            }
        })


    });

    $("tbody .btn-success").click(function(){
        window.location.href = "assignPermission.html";
    });

    function getPage(pageNo){
        var pageContent="";
        var tableContent="";
        var data={page:pageNo,rows:2};
        if (likeflag==true){
            data.username=$("#queryText").val();
        }
        $.ajax({
            type:"GET",
            url:"${APP_PATH}/admin/getdata",
            data:data,
            beforeSend:function () {
                loadingInex=layer.msg("处理中",{icon:16});
            },
            success:function (result) {
                layer.close(loadingInex);
                var page=result;
                var roles=page.rows;
                $.each(roles,function (i, user) {
                    tableContent+=' <tr>';
                    tableContent+='     <td>'+(i+1)+'</td>';
                    tableContent+='     <td><input type="checkbox" name="username" value='+user.username+' id="checkbox"></td>';
                    tableContent+='         <td>'+user.username+'</td>';
                    tableContent+='         <td>'+user.realname+'</td>';
                    tableContent+='         <td>'+user.email+'</td>';
                    tableContent+='         <td>';
                    tableContent+='     <button type="button" class="btn btn-primary btn-xs" onclick="editUser(\''+user.username+'\')"><i class=" glyphicon glyphicon-pencil"></i></button>';
                    tableContent+='     <button type="button" class="btn btn-danger btn-xs" onclick="removeUser(\''+user.username+'\',\''+user.realname+'\')"><i class=" glyphicon glyphicon-remove"></i></button>';
                    tableContent+='     </td>';
                    tableContent+=' </tr>';
                })
                pageContent+='<tr >';
                pageContent+='    <td colspan="6" align="center">';
                pageContent+='        <ul class="pagination">';
                if (page.object>1){
                    pageContent+=' <li onclick="getPage('+(pageNo-1)+')"><a href="#">上一页</a></li>';
                }
                for(var i=0;i<page.value;i++){
                    if (pageNo==(i+1)){
                        pageContent+='<li class="active"><a href="#">'+(pageNo)+'</a></li>';
                    }else {
                        pageContent+='<li><a href="#" onclick="getPage('+(i+1)+')">'+(i+1)+'</a></li>';
                    }
                }
                if (page.object<parseInt(page.value)){
                    pageContent+='   <li onclick="getPage('+(pageNo+1)+')"><a href="#">下一页</a></li>';
                }
                pageContent+='    </ul>';
                pageContent+='    </td>';
                pageContent+='  </tr>';
                $("#tableContent").html(tableContent);
                $("#pageContent").html(pageContent);
            }
        })
    }

    function removeUser(username,realname) {
        layer.confirm("是否删除角色"+realname+"<"+username+"@sixl.xyz>",  {icon: 3, title:'提示'}, function(cindex){
            $.ajax({
                type:"POST",
                url:"${APP_PATH}/user/deluser",
                data:{
                    username:username
                },
                beforeSend:function () {
                    loadingInex=layer.msg("处理中",{icon:16});
                },
                success:function (result) {
                    layer.close(loadingInex);
                    if (result.success) {
                        window.location.href="${APP_PATH}/admin/index";
                    }else {
                        layer.msg("删除失败", {time:1500, icon:5, shift:6}, function () {
                        });
                    }
                }
            })
        }, function(cindex){
            layer.close(cindex);
        });
    }

    $("#allSelBox").click(function () {
        var flag=this.checked;
        $("#tableContent :checkbox").each(function () {
            this.checked=flag;
        })
    })


    function deletes() {
        var boxes=$("#tableContent :checkbox");
        if(boxes.length==0){
            layer.msg("请选择要删除的用户",{time:2000,icon:5,shift:6},function () {
            });
        }else {
            layer.confirm("是否删除整页菜单",{icon: 3, title:'提示'},function (cindex) {
                layer.close(cindex);
                $.ajax({
                    type:"POST",
                    url:"${APP_PATH}/user/deluser",
                    data:$("#roleForm").serialize(),
                    success:function (result) {
                        if (result.success){
                            window.location.href="${APP_PATH}/admin/index";
                        }else {
                            layer.msg("删除失败",{time:2000,icon:5,shift:6},function () {
                            });
                        }
                    }

                })
            },function (cindex) {
                layer.close(cindex);
            })

        }

    }

    function assignRole(id){
        window.location.href="${APP_PATH}/role/assign?roleid="+id;
    }
    function editUser(username){
        window.location.href="${APP_PATH}/admin/editUser?username="+username;
    }

</script>
</body>
</html>
