<%--
  User: zzx
  Date: 2021-01-06 14:11
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<!-- 引入封装的头部 -->
<%@include file="/WEB-INF/include-head.jsp" %>
<link rel="stylesheet" href="ztree/zTreeStyle.css">
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my-menu.js"></script>
<script type="text/javascript">
    $(function () {
        // 生成树形结构
        generateTree();

        // 为新增节点按钮绑定单击事件
        $("#treeDemo").on("click", ".addBtn", function () {
            // 打开新增的模态框
            $("#menuAddModal").modal("show");

            // 将id存入window对象中，因为新增节点需要父节点的id
            window.pid = this.id;

            // 返回false表示不打开链接
            return false;
        });

        // 为模态框的保存按钮添加单击事件
        $("#menuSaveBtn").click(function () {
            var pid = window.pid;
            var name = $("#menuAddModal [name=name]").val().trim();
            var url = $("#menuAddModal [name=url]").val().trim();
            // 获取选中的radio
            var icon = $("#menuAddModal [name=icon]:checked").val();

            // 发送ajax请求
            $.ajax({
                url: "menu/save.json",
                type: "POST",
                data: {
                    pid,
                    name,
                    url,
                    icon
                },
                dataType: "json",
                success: function (response) {
                    var result = response.result;

                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");
                        // 重新生成树形结构
                        generateTree();
                    }

                    if (result == "FAILED") {
                        layer.msg("操作失败！" + response.message);
                    }

                },
                error: function (response) {
                    layer.msg(response.status + " " + response.statusText)
                }
            });
            // 成功还是失败都需要关闭模态框
            $("#menuAddModal").modal("hide");

            // 重置模态框内容
            $("#menuResetBtn").click();
        });

        // 为编辑按钮绑定单击事件
        $("#treeDemo").on("click", ".editBtn", function () {
            // 打开编辑的模态框
            $("#menuEditModal").modal("show");
            // 保存当前id
            window.id = this.id;
            // 获取zTreeObj对象
            var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var key = "id";
            var value = window.id;

            // 根据zTreeObj得到当前节点对象
            var currentNode = zTreeObj.getNodeByParam(key, value);

            // 从当前节点对象中得到表单回显所需要的值
            $("#menuEditModal [name=name]").val(currentNode.name);
            $("#menuEditModal [name=url]").val(currentNode.url);
            $("#menuEditModal [name=icon]").val([currentNode.icon]);

            return false;
        });

        // 给模态框中的更新按钮绑定单击事件
        $("#menuEditBtn").click(function () {
            // 获取数据
            var name = $("#menuEditModal [name=name]").val();
            var url = $("#menuEditModal [name=url]").val();
            var icon = $("#menuEditModal [name=icon]:checked").val();

            // 发送ajax请求
            $.ajax({
                url: "menu/update.json",
                type: "POST",
                data: {
                    id: window.id,
                    name,
                    url,
                    icon
                },
                dataType: "json",
                success: function (response) {
                    var result = response.result;

                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");
                        // 重新生成树形结构
                        generateTree();
                    }

                    if (result == "FAILED") {
                        layer.msg("操作失败！" + response.message);
                    }

                },
                error: function (response) {
                    layer.msg(response.status + " " + response.statusText)
                }
            });
            // 成功还是失败都需要关闭模态框
            $("#menuEditModal").modal("hide");
        });

        // 给删除按钮绑定单击事件
        $("#treeDemo").on("click", ".removeBtn", function () {
            // 打开确认模态框
            $("#menuConfirmModal").modal("show");

            // 保存当前id
            window.id = this.id;
            // 获取zTreeObj对象
            var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var key = "id";
            var value = window.id;

            // 根据zTreeObj得到当前节点对象
            var currentNode = zTreeObj.getNodeByParam(key, value);
            // 在提示信息中设置name
            $("#removeNodeSpan").html("<i class='" + currentNode.icon + "'></i>" + currentNode.name);

            // 关闭默认行为
            return false;
        });

        // 给模态框中的确认删除按钮绑定单击事件
        $("#confirmBtn").click(function () {
            $.ajax({
                url: "menu/remove.json",
                type: "POST",
                data: {
                    id: window.id,
                },
                dataType: "json",
                success: function (response) {
                    var result = response.result;

                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");
                        // 重新生成树形结构
                        generateTree();
                    }

                    if (result == "FAILED") {
                        layer.msg("操作失败！" + response.message);
                    }

                },
                error: function (response) {
                    layer.msg(response.status + " " + response.statusText)
                }
            });
            // 成功还是失败都需要关闭模态框
            $("#menuConfirmModal").modal("hide");
        });
    });

</script>
<body>
<!-- 引入封装的nav -->
<%@include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <!-- 引入封装的侧边栏 -->
        <%@include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/modal-menu-confirm.jsp" %>
<%@include file="/WEB-INF/modal-menu-add.jsp" %>
<%@include file="/WEB-INF/modal-menu-edit.jsp" %>
</body>
</html>
