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
    if (area == "" || area == null) {
        $("#show_span").text("请选择景区");
        showAndHide();
        return false;
    }

    var playtime = $("[name='time']").val();
    var reg1 = /^([0-9]|1[0-9]|2[0-4])$/;
    if (playtime == "" || playtime == null) {
        $("#show_span").text("游玩时长不能为空");
        showAndHide();
        return false;
    }
    if (reg1.test(playtime) == false) {
        $("#show_span").text("输入正确格式的游玩时长(0-24)");
        showAndHide();
        return false;
    }
    var opentime = $("[name='time2']").val();
    var reg2 = /^([01]?[0-9]|2[0-3]):([0-5][0-9])-([01]?[0-9]|2[0-3]):([0-5][0-9])$/;
    if (opentime == "" || opentime == null) {
        $("#show_span").text("开放时间不能为空");
        showAndHide();
        return false;
    }
    if (reg2.test(opentime) == false) {
        $("#show_span").text("输入正确格式的开放时间(8:00-12:00)");
        showAndHide();
        return false;
    }

    var traffic = $("[name='traffic']").val();
    if (traffic == "" || traffic == null) {
        $("#show_span").text("交通信息不能为空");
        showAndHide();
        return false;
    }
    if (traffic.length > 50) {
        $("#show_span").text("交通信息长度不能大于50");
        showAndHide();
        return false;
    }
    var reg3 = /(^[1-9]\d*(\.\d{1,2})?$)|(^0(\.\d{1,2})?$)/;
    var adultticket = $("[name='adult']").val();
    if (adultticket == "" || adultticket == null) {
        $("#show_span").text("成人门票不能为空");
        showAndHide();
        return false;
    }
    if (reg3.test(adultticket) == false) {
        $("#show_span").text("请输入成人门票:整数或者保留两位小数");
        showAndHide();
        return false;
    }
    var childticket = $("[name='child']").val();
    if (childticket == "" || childticket == null) {
        $("#show_span").text("儿童门票不能为空");
        showAndHide();
        return false;
    }
    if (reg3.test(childticket) == false) {
        $("#show_span").text("请输入儿童门票:整数或者保留两位小数");
        showAndHide();
        return false;
    }
    var ticketinfo = $("[name='tickets']").val();
    if (ticketinfo == "" || ticketinfo == null) {
        $("#show_span").text("门票信息不能为空");
        showAndHide();
        return false;
    }
    if (ticketinfo.length > 100) {
        $("#show_span").text("门票信息长度不得大于100");
        showAndHide();
        return false;
    }

    var addr = $("[name='site']").val();
    if (addr == "" || addr == null) {
        $("#show_span").text("地址不能为空");
        showAndHide();
        return false;
    }

    var mapname = $("[name='territory']").val();
    if (mapname == "" || mapname == null) {
        $("#show_span").text("地图显示名称不能为空");
        showAndHide();
        return false;
    }
    if (mapname.length > 50) {
        $("#show_span").text("地图显示名称长度不得大于50");
        showAndHide();
        return false;
    }
    // 校验经度：经度整数部分为0-180,小数部分为0到6位
    var reglong = /^(\-|\+)?(((\d|[1-9]\d|1[0-7]\d|0{1,3})\.\d{0,6})|(\d|[1-9]\d|1[0-7]\d|0{1,3})|180\.0{0,6}|180)$/;
    // 校验纬度：纬度整数部分为0-90,小数部分为0到6位!;
    var reglat = /^(\-|\+)?([0-8]?\d{1}\.\d{0,6}|90\.0{0,6}|[0-8]?\d{1}|90)$/;
    var longitude = $("[name='longitude']").val();// 获取经度
    var latitude = $("[name='latitude']").val();// 获取纬度

    if (longitude == "" || longitude == null) {
        $("#show_span").text("经度不能为空");
        showAndHide();
        return false;
    }

    if (latitude == "" || latitude == null) {
        $("#show_span").text("纬度不能为空");
        showAndHide();
        return false;
    }
    // 校验经度：经度整数部分为0-180,小数部分为0到6位
    if (reglong.test(longitude) == false) {
        $("#show_span").text("经度整数部分为(+|-)0到180,小数部分为0到6位");
        showAndHide();
        return false;
    }
    // 校验纬度：纬度整数部分为0-90,小数部分为0到6位!;
    if (reglat.test(latitude) == false) {
        $("#show_span").text("纬度整数部分为(+|-)0到90,小数部分为0到6位");
        showAndHide();
        return false;
    }
    var telone = $("[name='tho']").val();
    var telanother = $("[name='tho2']").val();
    //var reg = /^1[1-9][0-9]{9}$/;
    var reg = /^([0-9]{3,4}-)?([0-9]{3,4}-)?[0-9]{3,8}$/;
    if (telone != null && telone != "") {
        if (reg.test(telone) == false) {
            $("#show_span").text("选填号码1非法");
            showAndHide();
            return false;
        }
    }
    if (telanother != null && telanother != "") {
        if (reg.test(telanother) == false) {
            $("#show_span").text("选填号码2非法");
            showAndHide();
            return false;
        }
    }

    var brief = $("[name='brief']").val();
    if (brief == "" || brief == null) {
        $("#show_span").text("简介不能为空");
        showAndHide();
        return false;
    }
    if (brief.length > 1024) {
        $("#show_span").text("简介长度不能超过1024");
        showAndHide();
        return false;
    }

    // 判断景点图片是否上传
    var mytdy = document.getElementById("yida-tbody"); //获取对象
    var len = mytdy.getElementsByTagName("tr");
    if (len.length == 0) {
        $("#show_span").text("请先上传配图");
        showAndHide();
        return false;
    }

    // 判断是否有未上传的景点图片
    var sizeimg = document.getElementById("imgsize").value;
    if (parseInt(sizeimg) != 0) {
        $("#show_span").text("有未上传的图片，请上传");
        showAndHide();
        sizeimg == 0;
        return false;
    }

    // 校验缩略图
    var ddd = document.getElementById("yida-my-img");
    if (ddd.src != "http://localhost:8080/img/shangchuan.jpg") {
        return true;
    }

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