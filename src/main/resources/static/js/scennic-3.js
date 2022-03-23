//  缩虐图
$('#myfile2').change(function () {
    var maxSize = 1024;//最大允许上传1024kb，即：1M
    if ($("#myfile2").val() == "" || $("#myfile2").val() == null) {
        $("#show_span").text("请先上传缩虐图");
        showAndHide();
        return false;
    }
    var reg = /\.(jpg|jpeg|png|JPG|PNG)/;
    if (!reg.test($("#myfile2").val())) {
        $("#show_span").text("文件类型必须是[.gif,jpeg,jpg,png]");
        showAndHide();
        //不符合时情况file重写选择上传的文件
        var files = document.getElementById("myfile2");
        files.value = "";
        return false;
    }
    var file = $("#myfile2")[0].files[0];  //得到所有文件的对象
    if (file != null) {
        var name = file.name;          //获取文件名称
        var size = file.size;
        if (size > maxSize * 1024) {
            $("#show_span").text("图片大小不能超过1M");
            showAndHide();
            return false;
        }
        var url = window.URL.createObjectURL(file);//图片预览
        var imgs = document.getElementById("yida-my-img");
        imgs.innerHTML = "";
        imgs.src = url;
        imgs.style = "width: 60px;height: 60px;";
        var sp = document.getElementById("icon");
        sp.onclick = function () {
            //清空file重写选择
            var files = document.getElementById("myfile2");
            files.value = "";
            imgs.src = "../img/shangchuan.jpg";
        }

        $("#uploadfile_a2").hover(function () {//缩虐图hover时间
            var attr = $("#yida-my-img").attr("src");   //鼠标经过
            if (attr != "../img/shangchuan.jpg") {
                $("#icon").attr("class", "iconfont icon-icon1 bg-dark d-inline-block");
            }
        }, function () {  //鼠标离开
            $("#icon").attr("class", "iconfont icon-icon1 bg-dark d-none")
        });

    }
});


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
