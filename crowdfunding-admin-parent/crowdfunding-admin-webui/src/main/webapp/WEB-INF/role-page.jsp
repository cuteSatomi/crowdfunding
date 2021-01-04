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
                    layer.msg(response.status + " " + response.statusText)
                }
            });
            // 由于不论成功还是失败都需要关闭和清理模态框，因此这一步放在下面来做
            $("#addModal").modal("hide");
            $("#addModal [name=roleName]").val("");
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
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i
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
                                <th width="30"><input type="checkbox"></th>
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
<%@include file="modal-role-add.jsp" %>
</body>
</html>
