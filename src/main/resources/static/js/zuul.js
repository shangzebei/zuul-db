/**
 * Created by shang-mac on 2017/7/2.
 */
function save() {
    var url = $("#url").val();
    var local = $("#local").val();
    var from = new FormData();
    from.append("url", url);
    from.append("local", local)
    http.postAjax_clean("home/add", from, function (data) {
        if (data.state == true) {
            window.location.reload();
        }

    })
}