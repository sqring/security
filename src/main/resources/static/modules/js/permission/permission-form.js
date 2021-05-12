// permissionTree
$(function () {
    loadPermissionTree();
});


// 加载权限树
function loadPermissionTree() {

    var menuSetting = {
        view: {
            showLine: true // 显示连接线
        },
        check: {
            enable: false // 不显示勾选框
        },
        data: {
            simpleData: {
                enable: true, // 开启简单模式,List自动转json
                idKey: "id", // 唯一的标识属性名
                pIdKey: "parentId", // 父节点唯一标识的属性名称
                rootPId: 0 // 根节点数据
            },
            key: {
                name: "name", // 显示的节点名称对应属性名称
                title: "name" // 鼠标放上去显示的
            }
        },
        callback: {
            onClick: function (event, treeId, treeNode) {
                // treeNode 代表的是点击的那个节点json对象
                // 被点击之后阻止跳转
                event.preventDefault();

                console.log(treeNode.id, treeNode.name)
                if(treeNode.id == $("#id").val()) {
                    console.log(treeId)
                    layer.tips('自已不能作为父资源', '#'+treeId, {time: 1000})
                    return;
                }

                // 就将选择的节点放到父资源处
                // $('#parentId').val(treeNode.id);
                // $('#parentName').val(treeNode.name);
                parentPermission(treeNode.id, treeNode.name);
            }
        }
    };

    // 查询所有的权限资源
    $.post(contextPath + "permission/list", function (data) {
        var permissionTree = $.fn.zTree.init($("#permissionTree"), menuSetting, data.data);
        var parentIdVal = $("#parentId").val();
        console.log('parentIdVal', parentIdVal);
        // 注意如果判断不等于0,不要使用两个等于号,
        if(parentIdVal !== null && parentIdVal !== '' && parentIdVal !== undefined && parentIdVal != 0) {
            // 通过父节点id来获取这个id的节点对象
            var nodes = permissionTree.getNodesByParam("id", parentIdVal, null);
            // console.log(nodes[0].name);
            $("#parentName").val(nodes[0].name);
        }
    })
}


function parentPermission(parentId, parentName) {
    if(parentId == null || parentName == null) {
        parentId = 0;
        parentName = '根菜单';
    }
    $('#parentId').val(parentId);
    $('#parentName').val(parentName);
}
