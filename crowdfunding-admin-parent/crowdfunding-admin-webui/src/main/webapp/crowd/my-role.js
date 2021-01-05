// 显示确认是否删除的模态框
function showConfirmModal(roleArray) {
    // 显示模态框
    $("#confirmModal").modal("show");

    // 清除旧数据
    $("#roleNameDiv").empty();

    // 在window对象中定义一个存放角色id的数组
    window.roleIdArray = [];

    for (var i = 0; i < roleArray.length; i++) {
        var role = roleArray[i];
        var roleName = role.roleName;
        // 将其拼到roleNameDiv中
        $("#roleNameDiv").append(roleName + "<br>");
        var roleId = role.roleId;
        window.roleIdArray.push(roleId);
    }
}

// 执行分页，生成页面效果，任何时候调用这个函数都会重新加载页面
function generatePage() {
    // 获取分页数据
    var pageInfo = getPageInfoRemote();

    // 填充表格
    fillTableBody(pageInfo);
}

// 访问服务器获取pageInfo数据
function getPageInfoRemote() {

    var ajaxResult = $.ajax({
        type: "POST",
        url: "role/get/page/info.json",
        data: {
            pageNum: window.pageNum,
            pageSize: window.pageSize,
            keyword: window.keyword
        },
        async: false,
        dataType: "json"
    });

    var statusCode = ajaxResult.status;

    // 判断当前响应状态码是否是200，不是200说明发生了错误，return null
    if (statusCode != 200) {
        layer.msg("失败！响应状态码：" + statusCode + "， 说明信息：" + ajaxResult.statusText);
        return null;
    }

    var resultEntity = ajaxResult.responseJSON;
    var result = resultEntity.result;

    if (result == "FAILED") {
        layer.msg(resultEntity.message);
        return null;
    }

    // 确认result成功后获取pageInfo
    var pageInfo = resultEntity.data;

    return pageInfo;
}

// 填充表格
function fillTableBody(pageInfo) {
    // 首先清空tbody中的旧数据
    $("#rolePageBody").empty();
    // 这个清空是为了没有搜索结果时不显示页码导航条
    $("#Pagination").empty();

    // 对pageInfo对象进行校验，若是不满足则返回
    if (pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0) {
        // 显示提示信息
        $("#rolePageBody").append("<tr><td colspan='4' align='center'>抱歉，没有查询到宁搜索的数据</td></tr>");
        return;
    }

    for (var i = 0; i < pageInfo.list.length; i++) {
        // 获取role
        var role = pageInfo.list[i];
        // 获取role的id和name
        var roleId = role.id;
        var roleName = role.name;

        // 这里用的不是真实的role的id 而是根据索引生成，从1开始
        var numberTd = "<td>" + (i + 1) + "</td>";
        var checkboxTd = "<td><input id='" + roleId + "' class='itemCheckBox' type='checkbox'></td>";
        var roleNameTd = "<td>" + roleName + "</td>";
        var checkBtn = "<button type='button' class='btn btn-success btn-xs'><i class=' glyphicon glyphicon-check'></i></button>";

        // 通过button标签的id属性（别的属性其实也可以）把roleId值传递到button按钮的单击响应函数中，在单击响应函数中使用this.id
        var pencilBtn = "<button id='" + roleId + "' type='button' class='btn btn-primary btn-xs pencilBtn'><i class=' glyphicon glyphicon-pencil'></i></button>";

        // 通过button标签的id属性（别的属性其实也可以）把roleId值传递到button按钮的单击响应函数中，在单击响应函数中使用this.id
        var removeBtn = "<button id='" + roleId + "' type='button' class='btn btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>";

        var buttonTd = "<td>" + checkBtn + " " + pencilBtn + " " + removeBtn + "</td>";

        var tr = "<tr>" + numberTd + checkboxTd + roleNameTd + buttonTd + "</tr>";

        // 将上面拼接的tr追加到tbody中
        $("#rolePageBody").append(tr);
    }
    generateNavigator(pageInfo);
}

// 生成分页页码导航条
function generateNavigator(pageInfo) {
    // 获取总记录数
    var totalRecord = pageInfo.total;

    // 声明一个对象来存储Pagination需要设置的属性
    var properties = {
        num_edge_entries: 3,                                    // 边缘页数
        num_display_entries: 5,                                 // 主体页数
        callback: paginationCallBack,                           // 点击页码后执行的函数
        items_per_page: pageInfo.pageSize,                      // 每页显示的条数
        current_page: pageInfo.pageNum - 1,                     // 当前页码
        prev_text: "上一页",                                     // 上一页按钮显示的文本
        next_text: "下一页"                                      // 下一页按钮显示的文本
    };

    $("#Pagination").pagination(totalRecord, properties);
}

// 翻页时的回调函数
function paginationCallBack(pageIndex, jQuery) {
    // 将全选的checkbox置为false
    $("#summaryCheckBox").prop("checked", false);

    // 修改window对象的pageNum属性
    window.pageNum = pageIndex + 1;

    // 调用分页函数
    generatePage();

    // 取消页码超链接的默认行为
    return false;
}