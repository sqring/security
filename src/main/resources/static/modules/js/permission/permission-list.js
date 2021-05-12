var $table = $("#table");
$(function () {
    var options = {
        url: contextPath + 'permission/list', // 请求url
        idField: 'id',
        parentIdField: 'parentId',
        treeShowField: 'name',
        columns: [
            {title: '序号', width: 20, formatter: function (value, row, index) {
                    return index+1;
                }},
            {field: 'id', visible: false},
            {title: '名称', field: 'name', width: '200px'},
            {title: '地址', field: 'url', width: '300px'},
            {title: '权限值', field: 'code', width: '300px'},
            {title: '图标', field: 'icon', width: '100px', formatter: iconFormatter},
            {title: '类型', field: 'type', width: '100px', formatter: function (value, row, index) {
                    return value == 1 ? '菜单': '按钮';
                }},
            {title: '操作', visible: false, field: 'action', width: '100px', formatter: $.operationFormatter},
        ]
    };


    $.treeTable(options);

});


function iconFormatter(value, row, index) {
    return '<span class="'+value+'"></span>'
}