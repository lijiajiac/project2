function check() {
    var title = $("[name='title']").val();
    if (title == "" || title == null) {
        $("#show_span").text("请输入景点标题");
        showAndHide();
        return false;
    }
    if (title.length > 15) {
        $("#show_span").text("景点标题长度不得大于15");
        showAndHide();
        return false;
    }

    //校验 内容
    editor.sync();
    var area = document.getElementById('editor_id').value; // 原生API
    console.log(area);
    if (area == "" || area == null) {
        $("#show_span").text("请输入发布内容");
        showAndHide();
        return false;
    }

    // 校验缩略图
    var photo_sign2 = $("#myfile2").val();
    if (photo_sign2 == null || photo_sign2 == "") {
        $("#show_span").text("请先上传缩虐图");
        showAndHide();
        return false;
    }

    return true;
}


var intervalID = null;

function showAndHide() {  /*js校验提示框显示*/
    //debugger;
    $("#show_span").fadeIn("slow");

    intervalID = window.setInterval("hidetip();", 3000);//设置一个定时器
}

function hidetip() {
    $("#show_span").fadeOut("slow");
    window.clearInterval(intervalID)
}