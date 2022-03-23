function check() {
    var title = $("[name='title']").val();
    if (title == "" || title == null) {
        $("#show_span").text("请输入咨询点标题");
        showAndHide();
        return false;
    }
    if (title.length > 15) {
        $("#show_span").text("咨询点标题长度不得大于15");
        showAndHide();
        return false;
    }

    var telone = $("[name='tho']").val();
    var reg = /^([0-9]{3,4}-)?([0-9]{3,4}-)?[0-9]{3,8}$/;

    if (telone != null || telone != "") { $("#show_span").text("联系电话不能为空");
        showAndHide();
        return false;}

    if (telone != null || telone != "") {
        if (reg.test(telone) == false) {
            $("#show_span").text("联系电话格式错误");
            showAndHide();
            return false;
        }
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