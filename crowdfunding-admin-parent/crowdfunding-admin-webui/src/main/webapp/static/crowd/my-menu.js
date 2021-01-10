function generateTree() {
    // 准备生成树形结构的JSON数据，去服务器请求真实的数据
    $.ajax({
        url: "menu/get/whole/tree.json",
        type: "POST",
        dataType: "json",
        success: function (response) {
            // 得到请求的结果
            var result = response.result;

            if (result == "SUCCESS") {
                // 创建JSON对象存储du对zTree所做的设置
                var setting = {
                    view: {
                        addDiyDom: myAddDiyDom,
                        addHoverDom: myAddHoverDom,
                        removeHoverDom: myRemoveHoverDom
                    },
                    data: {
                        key: {
                            url: "cat"
                        }
                    }
                };

                // 从结果中得到数据
                var zNodes = response.data;

                // 初始化zTree
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            }
            // 如果请求失败，显示提示信息
            if (result == "FAILED") {
                layer.msg(response.message);
            }

        },
        error: function (response) {

        }
    });
}

function myAddDiyDom(treeId, treeNode) {
    // treeId是整个树形结构附着的ul的标签的id
    // console.log("treeId=" + treeId);
    // 当前树形节点的全部数据，包括从服务器请求到的Menu对象的全部属性
    // console.log(treeNode);

    // 将默认标签替换成自定义的标签，即存在库里的icon字段的值
    // 根据spanId得到span标签，spanId的定义规则：ul标签的id_当前span的序号_ico，“ul标签的id_当前span的序号”可以通过treeNode的tId得到
    var spanId = treeNode.tId + "_ico";

    // 通过spanId删除旧的class，然后生成新的class，因为图标样式是class控制的
    $("#" + spanId).removeClass().addClass(treeNode.icon);
}

// 鼠标移入节点范围时添加按钮组
function myAddHoverDom(treeId, treeNode) {
    // 首先需要找到超链接的id
    var anchorId = treeNode.tId + "_a";

    // 给span设置有规律的id
    var spanId = treeNode.tId + "_spanId";

    // 根据id查看是否已经有span存在了，防止重复生成
    if ($("#" + spanId).length > 0) {
        return;
    }

    // 显示想要的button，定义三个按钮的HTML
    var addBtn = "<a id='" + treeNode.id + "' class='addBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='新增节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '></i></a>";
    var editBtn = "<a id='" + treeNode.id + "' class='editBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='编辑节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg '></i></a>";
    var removeBtn = "<a id='" + treeNode.id + "' class='removeBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='删除节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg '></i></a>";

    // 获取当前节点的level
    var level = treeNode.level;
    // 声明变量存储HTML代码
    var btnHTML = "";

    // 要根据当前level的等级来判断加哪些按钮
    if (level == 0) {
        // 0代表是根节点，根节点只有新增按钮
        btnHTML = addBtn;
    } else if (level == 1) {
        // 1代表是分支节点，分支节点有新增和编辑功能，如果不存在叶子节点则还有删除按钮
        btnHTML = addBtn + " " + editBtn;
        if (treeNode.children.length == 0) {
            // 如果不存在叶子节点，则加上删除按钮
            btnHTML += (" " + removeBtn);
        }
    } else if (level == 2) {
        // 2代表叶子节点，只有编辑和删除操作
        btnHTML = editBtn + " " + removeBtn;
    }

    // 在超链接后面加入span标签，并且设置对应的id
    $("#" + anchorId).after("<span id='" + spanId + "'>" + btnHTML + "</span>");
}

// 鼠标移出节点范围时删除按钮组
function myRemoveHoverDom(treeId, treeNode) {
    // 得到span标签的id
    var spanId = treeNode.tId + "_spanId";
    // 根据id删除span
    $("#" + spanId).remove();
}