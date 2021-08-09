<%--
  Created by IntelliJ IDEA.
  User: NFZ
  Date: 2021/6/17
  Time: 17:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script src="js/layui-v2.5.6/layui/layui.js"></script>
<link rel="stylesheet" href="js/layui-v2.5.6/layui/css/layui.css"></link>

<html>
<head>
    <title>Title</title>
</head>

<body>
<!-- 数据表格如何去使用 -->
<table class="layui-hide" id="test" lay-filter='test'></table>
<script type="text/html" id="toolbarDemo">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="getCheckData">获取选中行数据</button>
        <button class="layui-btn layui-btn-sm" lay-event="getCheckLength">获取选中数目</button>
        <button class="layui-btn layui-btn-sm" lay-event="isAll">验证是否全选</button>
    </div>
</script>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>



<script>
    layui.use(['layer','form','table'], function(args){
        var form = layui.form;
        var table = layui.table;

        table.render({
            elem: '#test'
            ,url:'/TableServlet'
            ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
            ,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
                title: '提示'
                ,layEvent: 'LAYTABLE_TIPS'
                ,icon: 'layui-icon-tips'
            }]
            ,title: '用户数据表'
            ,cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field:'id', title:'序号', width:100, sort: true}
                ,{field:'name', title:'姓名', width:80, edit: 'text'}
                ,{field:'age', title:'年龄', width:100, sort: true}
                ,{field:'clazz', title:'班级',width:100}
            ]]
            ,page: true
            ,limit:10
            ,limits:[5,10,20,30]
        });
    });

</script>



</body>
</html>
