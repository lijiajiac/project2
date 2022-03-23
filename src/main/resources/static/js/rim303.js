function check() {
    //标题
    var title = $("[name='title']").val();
    if (title == "" || title == null) {
        $("#show_span").text("请输入标题");
        showAndHide();
        return false;
    }
    if (title.length > 15) {
        $("#show_span").text("标题长度不得大于15");
        showAndHide();
        return false;
    }

    //所属类型
    var area = $("[name='classify.id']").val();
    console.log(area);
    if (area == "" || area == 0) {
        $("#show_span").text("请选择所属类型");
        showAndHide();
        return false;
    }
    //住所类型
    var stay = $("[name='stay']").val();
    if (stay == "" || stay == null) {
        $("#show_span").text("请输入住所类型");
        showAndHide();
        return false;
    }
    if (stay.length > 50) {
        $("#show_span").text("住所类型长度不得大于50");
        showAndHide();
        return false;
    }

    //交通信息
    var traffic = $("[name='traffic']").val();
    if (traffic == "" || traffic == null) {
        $("#show_span").text("请输入交通信息");
        showAndHide();
        return false;
    }
    if (traffic.length > 50) {
        $("#show_span").text("交通信息长度不得大于50");
        showAndHide();
        return false;
    }

    //参考价格
    var price = $("[name='price']").val();
    if (price == "" || price == null) {
        $("#show_span").text("请输入参考价格");
        showAndHide();
        return false;
    }

    //推荐菜
    var recommendation = $("[name='recommendation']").val();
    if (recommendation == "" || recommendation == null) {
        $("#show_span").text("请输如推荐菜信息");
        showAndHide();
        return false;
    }
    if (recommendation.length > 200) {
        $("#show_span").text("推荐菜信息长度不得大于200的长度");
        showAndHide();
        return false;
    }

    //地址
    var addr = $("[name='city']").val();
    if (addr == "" || addr == null) {
        $("#show_span").text("地址不能为空");
        showAndHide();
        return false;
    }

    //地图显示名称
    var territory = $("[name='territory']").val();
    if (territory == "" || territory == null) {
        $("#show_span").text("地图显示名称不能为空");
        showAndHide();
        return false;
    }
    if (territory.length > 50) {
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

    //联系电话
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

    //简介
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