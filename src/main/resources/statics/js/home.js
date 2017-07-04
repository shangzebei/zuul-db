/**
 * Created by shang-mac on 2017/7/2.
 */
$(document).ready(function () {
    http.getAjax_clean("home/getAll", function (data) {
        $('#table').bootstrapTable({
            columns: [{
                field: 'id',
                title: 'ID'
            }, {
                field: 'title',
                title: '标识符'
            }, {
                field: 'path',
                title: '匹配路由'
            },{
                field: 'local',
                title: '目标路由'
            },{
                field: 'ops',
                title: '操作',
                formatter: operateFormatter
            }],
            data: data
        });
    })
});
function operateFormatter(value, row, index) {
    return [
        '<button type="button" class="btn btn-primary zuul-btn" onclick="change('+index+')">修改目标</button>',
        '<button type="button" class="btn btn-danger zuul-btn" onclick="del('+index+')">删  除</button>'
    ].join('');
}
function save() {
    var url = $("#url").val();
    var local = $("#local").val();
    var path = $("#path").val();
    var from = new FormData();
    from.append("title", url);
    from.append("local", local)
    from.append("path", path)
    http.postAjax_clean("home/add", from, function (data) {
        if (data.state == true) {
            window.location.reload();
        }

    })
}
function del(i) {
    BootstrapDialog.alert("delete");
}
function change(i) {

}