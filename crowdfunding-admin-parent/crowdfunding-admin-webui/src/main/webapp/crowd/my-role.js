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

}

// 生成分页页码导航条
function generateNavigator(pageInfo) {

}

// 翻页时的回调函数
function paginationCallBack(pageIndex, jQuery) {

}