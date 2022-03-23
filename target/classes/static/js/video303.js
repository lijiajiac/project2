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

    var area = $("[name='scenic.id']").val();
    if (area == 0) {
        $("#show_span").text("请选择分类");
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

    // 判断景点图片是否上传
    var photo_sign = $("#myfile").val();
    if (photo_sign == null || photo_sign == "") {
        $("#show_span").text("请先上传视频");
        showAndHide();
        return false;
    }

    // 判断是否有未上传的景点图片
    var val = $("#spotImages").val();
    if (val == null || val == '') {
        $("#show_span").text("有未上传的图片，请上传");
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