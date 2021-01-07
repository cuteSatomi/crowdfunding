<%--
  User: zzx
  Date: 2021-01-07 10:49
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<!-- 引入封装的头部 -->
<%@include file="/WEB-INF/include-head.jsp" %>
<script type="text/javascript">
    $(function () {

        // 为右箭头添加单击事件
        $("#toRightBtn").click(function () {

            // 将选中的角色加入到右边
            // select:eq(0)表示找到页面中的第一个select； ">" 表示子类选择器，select只有option这一个子类，获取选中的加入到右边即可
            $("select:eq(0)>option:selected").appendTo("select:eq(1)");
        });

        // 为左箭头添加单击事件
        $("#toLeftBtn").click(function () {

            // 将选中的角色加入到左边
            $("select:eq(1)>option:selected").appendTo("select:eq(0)");
        });

        // 为提交按钮绑定事件，使得select中的option全部选中
        $("#submitBtn").click(function () {
            $("select:eq(1)>option").prop("selected","selected");
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
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">数据列表</a></li>
                <li class="active">分配角色</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-body">
                    <form action="assign/do/role/assign.html" method="post" role="form" class="form-inline">
                        <div class="form-group">
                            <input type="hidden" name="adminId" value="${param.adminId }" />
                            <input type="hidden" name="pageNum" value="${param.pageNum }" />
                            <input type="hidden" name="keyword" value="${param.keyword }" />
                            <label for="exampleInputPassword1">未分配角色列表</label><br>
                            <select class="form-control" multiple="" size="10" style="width:100px;overflow-y:auto;">
                                <c:forEach items="${requestScope.unAssignedRoleList}" var="role">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <ul>
                                <li id="toRightBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                                <br>
                                <li id="toLeftBtn" class="btn btn-default glyphicon glyphicon-chevron-left"
                                    style="margin-top:20px;"></li>
                            </ul>
                        </div>
                        <div class="form-group" style="margin-left:40px;">
                            <label for="exampleInputPassword1">已分配角色列表</label><br>
                            <select name="roleIdList" class="form-control" multiple="" size="10" style="width:100px;overflow-y:auto;">
                                <c:forEach items="${requestScope.assignedRoleList}" var="role">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <button id="submitBtn" type="submit" style="width: 100px;margin: 18px 0 0 228px;" class="btn btn-sm btn-success btn-block">保存</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
