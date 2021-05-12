

$(function () {
    var options = {
        url: contextPath + "user/page",
        pageNumber: 1, // 当前页面
        pageSize: 3, //每页显示条数
        columns: [
            {title: '序号', width: 20, formatter: function (value, row, index) {
                    return index+1;
                }},
            {field: 'id', visible: false},
            {field: 'username', title: '用户名'},
            {field: 'nickName', title: '昵称'},
            {field: 'mobile', title: '手机号'},
            {field: 'accountNonExpired', title: '帐号过期', formatter: statusFormatter},
            {field: 'accountNonLocked', title: '是否锁定', formatter: statusFormatter},
            {field: 'credentialsNonExpired', title: '密码过期', formatter: statusFormatter},
            {field: 'action', title: '操作', width: 50, visible: false,
                align: 'center', formatter: $.operationFormatter}
        ]
    };

    $.pageTable(options);
});


function statusFormatter(value, row, index) {
    return value == true ? "<span class='badge bg-success'>否</span>"
        : "<span class='badge bg-danger'>是</span>"
}


var $table = $("#table");
//搜索功能
function searchForm() {

    var query = {
        size: 3, // 每页显示多少条
        current: 1, // 当前页码
        username: $("#username").val().trim(),
        mobile: $("#mobile").val().trim()
    };
    $table.bootstrapTable("refresh", {query: query});

}
