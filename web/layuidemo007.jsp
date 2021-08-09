<%--
  Created by IntelliJ IDEA.
  User: NFZ
  Date: 2021/6/17
  Time: 17:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script src="/layui-v2.5.6/layui/layui.js"></script>
<link rel="stylesheet" href="/layui-v2.5.6/layui/css/layui.css"></link>

<html>
<head>
    <title>Title</title>
</head>

<body>
    <%--我是修改--%>
    <script type="text/html" id="updateHtml">

        <form class="layui-form layui-form-pane" action=""  onsubmit="return false">
            <div class="layui-form-item">
            <label class="layui-form-label">姓名</label>
            <div class="layui-input-inline">
                <input type="text" name=""  placeholder="请输入姓名：" class="layui-input">
            </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">年龄</label>
                    <div class="layui-input-inline">
                        <input type="text" name=""  placeholder="请输入年龄：" class="layui-input">
                    </div>
            </div>
                <label class="layui-form-label">是否神仙</label>
                <div class="layui-input-inline">
                    <input type="radio" name="shen" value="神仙" title="神仙" checked="">
                    <input type="radio" name="shen" value="凡人" title="凡人">
                </div>
            </div>

            <%--下面应该有  提交 或者  返回按钮--%>
        </form>

    </script>


    <%--加一个搜索框--%>
    <form class="layui-form layui-form-pane" action=""  onsubmit="return false">
        <div class="layui-form-item">
            <label class="layui-form-label">姓名</label>
            <div class="layui-input-inline">
                <input type="text" name=""  placeholder="请输入姓名：" class="layui-input">
            </div>

            <label class="layui-form-label">年龄</label>
            <div class="layui-input-inline">
                <select name="">
                    <option value="1">100岁以下</option>
                    <option value="2" selected="">100岁以上</option>
                </select>
            </div>

                <label class="layui-form-label">是否神仙</label>
                <div class="layui-input-inline">
                    <input type="radio" name="shen" value="神仙" title="神仙" checked="">
                    <input type="radio" name="shen" value="凡人" title="凡人">
                </div>
            <div class="layui-input-inline">
            <button type="submit" class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
            </div>
        </div>
    </form>


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
    layui.use(['layer','form','table','util','jquery'], function(args){
        var form = layui.form;
        var table = layui.table;
        var util=layui.util;
        var $=layui.jquery;

        table.render({
            elem: '#test'
            // ,url:'/test/table/demo1.json'
            ,url:'/layuiServlet'
            ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
            ,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
                title: '提示'
                ,layEvent: 'LAYTABLE_TIPS'
                ,icon: 'layui-icon-tips'
            }]
            ,title: '用户数据表'
            ,cols: [[
                {type: 'checkbox', fixed: 'left'}
                // ,{field:'email', title:'邮箱', width:150, edit: 'text', templet: function(res){
                //         return '<em>'+ res.email +'</em>'
                //     }}
                ,{field:'id', title:'序号', width:80, edit: 'text', sort: true}
                ,{field:'name', title:'姓名', width:100}
                ,{field:'age', title:'年龄',width:100,templet:function (d) {
                    //如果年龄大于100岁  变红
                        var age=d.age
                        if(age>=100) {
                            return "<span style='color:red'>" + age + "</span>"
                        }else{
                            return "<span>" + age + "</span>"
                        }
                    }}
                ,{field:'birthday', title:'出生日期',width:160,templet:function (d) {
                    console.log(d);      //templet  自定义模板
                        // d 就是这行数据  拿到这个数据就可以进行一个修改
                        //下面的任务是 把 d.birthday 修改成能看懂的日期
                        //util.toDateString(d.birthday,"YYYY-MM-dd HH:mm:ss");
                        return  util.toDateString(d.birthday,"yyyy-MM-dd HH:mm:ss");
                    }}
                    ,{field:'shen',title:'是否是神仙',width:150,templet:function (d) {
                        if(d.shen){
                            return " <input type=\"checkbox\" checked=\"\" name=\"open\" lay-skin=\"switch\" lay-filter=\"switchTest\" lay-text=\"神仙|凡人\" value='神仙'>"
                        }else {
                            return " <input type=\"checkbox\" name=\"close\" lay-skin=\"switch\" lay-filter=\"switchTest\" lay-text=\"神仙 | 凡人\" value='凡人'>"
                        }
                    }}
                    ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}
            ]]
            ,page: true
        });
        //头工具栏事件
        table.on('toolbar(test)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                case 'getCheckData':
                    var data = checkStatus.data;
                    layer.alert(JSON.stringify(data));
                    break;
                case 'getCheckLength':
                    var data = checkStatus.data;
                    layer.msg('选中了：'+ data.length + ' 个');
                    break;
                case 'isAll':
                    layer.msg(checkStatus.isAll ? '全选': '未全选');
                    break;

                //自定义头工具栏右侧图标 - 提示
                case 'LAYTABLE_TIPS':
                    layer.alert('这是工具栏右侧自定义的一个图标按钮');
                    break;
            };
        });

        //监听行工具事件
        table.on('tool(test)', function(obj){
            var data = obj.data;
            //console.log(obj)
            if(obj.event === 'del'){
                layer.confirm('真的删除行么', function(index){
                    obj.del();
                    layer.close(index);
                });
            } else if(obj.event === 'edit'){
                layer.open({
                    title:'编辑',
                    area: ['600px', '600px'],
                    type: 1,
                  //  content: '<h1>你好时间。。。</h1>'
                    content: $("#updateHtml").html(), //一般不把html写在这里
                    success:function (layero, index) {
                        console.log(layero)
                        console.log(index)
                        console.log("我是layero 弹出成功后的回调")
                        form.render(); //重点强调：当界面 发生改变时，记得 重新渲染模块，不然不显示效果
                    },
                    btn:['确定修改','取消修改','其他1','其他2'],
                    yes:function (index,layero) {
                        alert("向后台发送ajax")
                        return false ; // 开启该代码可禁止点击该按钮关闭
                    },
                    btn2:function (index,layero) {
                        alert("我取消了修改，嘿嘿")
                        return false ; // 开启该代码可禁止点击该按钮关闭
                    },

                });



                // layer.prompt({
                //     formType: 2
                //     ,value: data.email
                // }, function(value, index){
                //     obj.update({
                //         email: value
                //     });
                //     layer.close(index);
                // });
            }
        });

        //监听指定开关
        form.on('switch(switchTest)', function(data){
            console.log(data)
            //console.log(data.othis.text())
            var x=this.checked ? true : false;
            if(x){
                data.value="神仙"
                console.log(data.value)
            }else{
                data.value="凡人"
                console.log(data.value)
            }
                $.ajax({
                    url:'/ShenServlet',
                    type:'get',
                    data:{
                        "shen":data.othis.text()
                    },
                    dataType:'json',
                    success:function(res){
                        //console.log(res)
                    }

                })
            //msg  是判定开关的状态
            layer.msg('开关checked：'+ (this.checked ? 'true' : 'false'), {
                offset: '6px'
            });
            //layer.tips('温馨提示：请注意开关状态的文字可以随意定义，而不仅仅是ON|OFF', data.othis)
        });
    });

</script>

</body>
</html>
