<%--
  User: zzx
  Date: 2020-12-30 14:18
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<!-- 引入封装的头部 -->
<%@include file="/WEB-INF/include-head.jsp" %>
<link rel="stylesheet" href="css/pagination.css"/>
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<link rel="stylesheet" href="ztree/zTreeStyle.css">
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my-role.js"></script>
<script type="text/javascript">

    $(function () {
        // 为分页操作准备初始化数据
        window.pageNum = 1;
        window.pageSize = 5;
        window.keyword = "";

        generatePage();

        // 输入关键字的查询
        $("#searchBtn").click(function () {

            // 将关键字输入框中的文本赋值给window对象，并且将当前页码置为1
            window.keyword = $("#keywordInput").val();
            window.pageNum = 1;

            // 调用generatePage()重新请求页面
            generatePage();
        });

        // 单击新增按钮弹出模态框
        $("#showAddModalBtn").click(function () {
            $("#addModal").modal("show");
        });

        // 点击保存按钮发送ajax请求
        $("#saveRoleBtn").click(function () {

            // 获取输入的角色名称，后代选择器，找出后代中name为roleName的
            var roleName = $("#addModal [name=roleName]").val().trim();

            // 发送ajax请求
            $.ajax({
                url: "role/save.json",
                type: "POST",
                dataType: "json",
                data: {
                    name: roleName
                },
                success: function (response) {
                    // 获取结果
                    var result = response.result;
                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");

                        // 成功保存角色后重新调用generatePage()请求结果，为了能看到新增的角色，直接跳到最后一页
                        window.pageNum = 88888;
                        generatePage();
                    }
                    if (result == "FAILED") {
                        layer.msg("操作失败！" + response.message);
                    }
                },
                error: function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            // 由于不论成功还是失败都需要关闭和清理模态框，因此这一步放在下面来做
            $("#addModal").modal("hide");
            $("#addModal [name=roleName]").val("");
        });

        // 点击铅笔按钮弹出更新模态框
        // on函数的第一个参数表示事件类型，第二个函数表示要绑定该事件的元素，第三个函数表示事件的相应函数
        // 比如下面代码表示找到class为pencilBtn的按钮，为其绑的一个单击事件，事件的内容就是第三个参数里的内容
        $("#rolePageBody").on("click", ".pencilBtn", function () {
            // 打开模态框
            $("#editModal").modal("show");

            // 获取当前角色名称，得到当前jQuery对象的父节点的前一个兄弟节点的text文本，就是roleName
            var roleName = $(this).parent().prev().text();

            // 将roleId设置到window对象中
            window.roleId = this.id;

            // 将得到的roleName回显到模态框中
            $("#editModal [name=roleName]").val(roleName);
        });

        // 给更新按钮绑定单击事件
        $("#updateRoleBtn").click(function () {

            // 从文本框获取新的角色名称
            var roleName = $("#editModal [name=roleName]").val();

            // 发送ajax请求
            $.ajax({
                url: "role/update.json",
                type: "POST",
                data: {
                    id: window.roleId,
                    name: roleName,
                },
                dataType: "json",
                success: function (response) {
                    // 获取结果
                    var result = response.result;
                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");

                        // 成功保存角色后重新调用generatePage()请求结果
                        generatePage();
                    }
                    if (result == "FAILED") {
                        layer.msg("操作失败！" + response.message);
                    }
                },
                error: function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            // 不论成功或者失败都需要关闭模态框
            $("#editModal").modal("hide");
        });
        // 点击模态框内的确定删除按钮，执行删除操作
        $("#removeRoleBtn").click(function () {
            // 因为后端是根据roleId来删除角色的，因此还需要得到id信息，将其转为json字符串
            var requestBody = JSON.stringify(window.roleIdArray);
            $.ajax({
                url: "role/remove/by/role/id/array.json",
                type: "POST",
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                data: requestBody,
                success: function (response) {
                    // 获取结果
                    var result = response.result;
                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");

                        // 成功保存角色后重新调用generatePage()请求结果
                        generatePage();

                        // 删除成功后将全选框清空
                        $("#summaryCheckBox").prop("checked", false);
                    }
                    if (result == "FAILED") {
                        layer.msg("操作失败！" + response.message);
                    }
                },
                error: function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            // 不论成功或者失败都需要关闭模态框
            $("#confirmModal").modal("hide");
        });

        // 点击单行内的删除按钮，弹出模态框确认是否删除
        $("#rolePageBody").on("click", ".removeBtn", function () {
            var roleName = $(this).parent().prev().text();
            var roleArray = [{
                roleId: this.id,
                roleName: roleName
            }];
            showConfirmModal(roleArray);
        });

        // 设置checkbox的全选操作
        $("#summaryCheckBox").click(function () {
            var currentStatus = this.checked;
            $(".itemCheckBox").prop("checked", currentStatus);
        });

        // 当下面的单行角色中checkbox全部选中时，最上面的也勾选，只要有一个没选中，就取消勾选
        $("#rolePageBody").on("click", ".itemCheckBox", function () {
            var totalCheckBox = $(".itemCheckBox").length;
            var checkedCheckBox = $(".itemCheckBox:checked").length;
            $("#summaryCheckBox").prop("checked", totalCheckBox == checkedCheckBox);
        });

        // 给批量删除的按钮绑定单击事件
        $("#batchRemoveBtn").click(function () {
            var roleArray = [];
            // 获取并且遍历选中的行
            $(".itemCheckBox:checked").each(function () {
                var roleId = this.id;
                var roleName = $(this).parent().next().text();

                roleArray.push({
                    roleId,
                    roleName
                });
            });

            // 如果一个都没选择就点击批量删除，提示错误信息
            if (roleArray.length == 0) {
                layer.msg("请至少选中一个执行删除");
                return;
            }

            // 弹出模态框
            showConfirmModal(roleArray);
        });

        // 给分配权限按钮绑定单击事件
        $("#rolePageBody").on("click", ".checkBtn", function () {
            // 打开分配权限的模态框
            $("#assignModal").modal("show");

            // 将当前id存入window对象中
            window.roleId = this.id;

            // 生成权限的树形结构
            fillAuthTree();
        });

        // 给分配权限模态框的确定按钮绑定单击事件
        $("#assignBtn").click(function () {
            // 定义一个存放authId的数组
            var authIdArray = [];

            // 得到zTree对象
            var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");

            // 根据zTree对象的getCheckedNodes方法得到被勾选的节点
            var checkedNodes = zTreeObj.getCheckedNodes();

            // 遍历checkedNodes
            for (var i = 0; i < checkedNodes.length; i++) {
                var checkedNode = checkedNodes[i];
                // 得到authId
                var authId = checkedNode.id;
                // 将authId push进数组
                authIdArray.push(authId);
            }

            var requestBody = {
                authIdArray,
                roleId: [window.roleId]
            };

            requestBody = JSON.stringify(requestBody);

            // 发送ajax请求
            $.ajax({
                url: "assign/do/role/assign/auth.json",
                type: "POST",
                data: requestBody,
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                success: function (response) {
                    var result = response.result;
                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");
                    }
                    if (result == "FAILED") {
                        layer.msg("操作失败！" + response.message);
                    }
                },
                error: function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            $("#assignModal").modal("hide");
        });
    });
</script>
<body>
<!-- 引入封装的侧边栏 -->
<%@include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>
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
                                <input id="keywordInput" class="form-control has-success" type="text"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning"><i
                                class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button id="batchRemoveBtn" type="button" class="btn btn-danger"
                            style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button id="showAddModalBtn" type="button" class="btn btn-primary" style="float:right;"><i
                            class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="summaryCheckBox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody"></tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"><!--这里显示分页--></div>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/modal-role-add.jsp" %>
<%@include file="/WEB-INF/modal-role-edit.jsp" %>
<%@include file="/WEB-INF/modal-role-confirm.jsp" %>
<%@include file="/WEB-INF/modal-role-assign-auth.jsp" %>
</body>
</html>
