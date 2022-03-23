$(document).ready(function () {

    //修改页缩虐图的hover事件
    $("#uploadfile_a2").hover(function () {//缩虐图hover时间
        var attr = $("#yida-my-img").attr("src");   //鼠标经过
        if (attr != "../img/shangchuan.jpg") {
            $("#icon").attr("class", "iconfont icon-icon1 bg-dark d-inline-block");
            $("#icon").click(function () {
                file = null;
                var imgs = document.getElementById("yida-my-img");
                imgs.src = "../img/shangchuan.jpg";
            });
        }
    }, function () {  //鼠标离开
        $("#icon").attr("class", "iconfont icon-icon1 bg-dark d-none")
    });


    $("#uploadfile").click(function () {
        var maxSize = 1024;//最大允许上传1024kb，即：1M
        if ($("#myfile").val() == "" || $("#myfile").val() == null) {
            $("#show_span").text("请选择需要上传的配图");
            showAndHide();
            return false;
        }
        var reg = /\.(jpg|jpeg|png|JPG|PNG)/;
        if (!reg.test($("#myfile").val())) {
            $("#show_span").text("文件类型必须是[.gif,jpeg,jpg,png]");
            showAndHide();
            //不符合时情况file重写选择上传的文件
            var files = document.getElementById("myfile");
            files.value = "";
            return false;
        } else {
            var file = $("#myfile")[0].files[0];
            var size = file.size;
            if (size > maxSize * 1024) {
                $("#show_span").text("图片大小不能超过1M");
                showAndHide();
                return false;
            }
        }
        //文件上传
        var form = $("#myform")[0];
        var formData = new FormData(form);
        //添加文件对象到表单
        //var file = $("#myfile")[0].files;
        var file = document.getElementById("myfile").files;
        for (var a = 0; a < file.length; a++) {
            formData.append("file", file[a]);
        }
        debugger;
        $.ajax({
            url: "/getAjaxUploadFiles",
            type: 'POST',
            async: true,
            cache: false,//不使用缓存
            data: formData,
            processData: false,//必须false避开jQuery对formdata的默认处理,而 XMLHttpRequest会对formdata正确处理
            contentType: false,//必须false才会自动加上正确的Content-Type，formdata里指 定过了
            success: function (result, status, xhr) {//请求成功回调
                if (status == "success") {

                    //文件上传后更新界面保存数据库的信息
                    var spotImages = document.getElementById("spotImages");
                    var value = spotImages.value;
                    if (value != null && value != "") {
                        spotImages.value = value + "," + result.toString().substring(1, result.toString().length - 1);
                    } else {
                        spotImages.value = result.toString().substring(1, result.toString().length - 1);
                    }

                    var mytdy = document.getElementById("yida-tbody"); //获取对象
                    var len = mytdy.childNodes;//得到指定对象下的所有孩子，即所有tr
                    for (var a = 0; a < len.length; a++) {     //  遍历所有tr
                        var tds = len[a].childNodes;
                        for (var b = 0; b < tds.length; b++) {
                            if (b == 3) {//tds.lastChild)
                                var TD = tds[b];
                                console.log(TD.innerHTML);
                                TD.innerHTML = "";
                                TD.innerText = "";
                                var sp6 = document.createElement("a");//创建标签
                                sp6.href = "javascript:void(0)";
                                sp6.setAttribute("class", "iconfont icon-icon1");
                                sp6.setAttribute("style", "text-decoration:none;color:black;");
                                sp6.onclick = function () {  //给创建的标签添加点击事件
                                    var nod = this.parentNode.parentNode; //的到当前元素的父元素，的父元素。即创建出的这个TR
                                    var t = nod.parentNode;  //得到tr的父标签table
                                    t.removeChild(nod);
                                    addtd2();//动态展示总上传文件大小

                                    var spotImages = document.getElementById("spotImages").value;
                                    console.log(spotImages);
                                    if (spotImages.split(":").length > 2) {
                                        var sp = spotImages.split(",");
                                        for (var a = 0; a < sp.length; a++) {
                                            var s = sp[a].split(":");
                                            var ss = s[1].substring(1, s[1].length - 1);
                                            var so = ss.substring(0, ss.length - 1);
                                            var s3 = Number(so).toFixed(2);
                                            s3 = s3 + "kb";
                                            var p = s[0].substring(1, s[0].length - 1);
                                            var nas = p.split("&");
                                            var na = nas[1];
                                            console.log(na + "---" + nod.childNodes[1].innerText + "-----------" + s3 + "--" + nod.childNodes[2].innerText);
                                            if (na == nod.childNodes[1].innerText && s3 == nod.childNodes[2].innerText) {
                                                var number = spotImages.indexOf(sp[a]);
                                                if (number == 0) {
                                                    var s1 = spotImages.substring(sp[a].length + 1, spotImages.length);          //通过查出的位置索引，截取去掉需要删除的配图信息，
                                                    document.getElementById("spotImages").value = s1;
                                                } else {
                                                    var s2 = spotImages.substring(0, number - 1);
                                                    var s3 = spotImages.substring(sp[a].length + number, spotImages.length);
                                                    document.getElementById("spotImages").value = s2 + s3;
                                                }
                                            }
                                        }
                                    } else {
                                        document.getElementById("spotImages").innerHTML = "";
                                    }
                                    console.log(document.getElementById("spotImages").value);

                                }
                                TD.appendChild(sp6);//添加子标签
                            }
                        }
                    }
                    filePut();
                    var size = 0;  //显示文件大小
                    var mytdy = document.getElementById("yida-tbody"); //获取对象
                    var len = mytdy.childNodes;    //得到指定对象下的所有孩子，即所有tr
                    for (var a = 1; a < len.length; a++) {
                        if (typeof (len[a].innerHTML) != "undefined") {  //去除掉无关的子元素或者莫名的undefined
                            var tr = len[a].childNodes;
                            if (tr != null) {
                                for (var b = 0; b < tr.length; b++) {// 遍历td.
                                    if (typeof (tr[b].innerHTML) != "undefined") {
                                        var td = tr[b];                //拿到显示文件大小的td的值
                                        if (td.className == "yida-size") { //得到已经上传的大小
                                            var strings = td.innerText.split("k");
                                            var st = Number(strings[0].substring(0, strings[0].length - 2));
                                            size = Number(size) + st;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //更新上传后的显示文件总大小
                    var mytd2 = document.getElementById("yida-tf-td2");//获取标签对象
                    mytd2.setAttribute('style', 'color:black');
                    mytd2.innerHTML = "总计:" + ((size).toFixed(2)) + "kb";

                    //上传成功后清空file
                    var files = document.getElementById("myfile");
                    files.value = "";
                    //未上传文件为0
                    document.getElementById("imgsize").value = 0;
                }
            },
            error: function (xhr) {//请求失败回调
                var str = "ERROR:" + xhr.statusText + "---" + xhr.status;
                alert("上传失败---" + str);
            }
        });
    });

});

function filePut() {
    var mytdy = document.getElementById("yida-tbody"); //获取对象
    var len = mytdy.getElementsByTagName("tr");   //得到指定对象下的所有孩子，即所有tr
    var mytd = document.getElementById("yida-tf-td");//获取标签对象
    mytd.setAttribute('style', 'color:black');//设置属性字体颜色
    mytd.innerHTML = len.length + '个文件';
}


//动态展示总上传文件大小
function addtd2() {
    var mytdy = document.getElementById("yida-tbody"); //获取对象
    var len = mytdy.getElementsByTagName("tr"); //得到指定对象下的所有孩子，即所有tr
    var size = 0;  //显示文件大小
    for (var a = 0; a < len.length; a++) {     //  遍历所有tr
        var tr = len[a].childNodes;       //得到tr下的所有孩子。即所有td
        if (tr != null) {
            for (var b = 0; b < tr.length; b++) {      // 遍历td.
                if (tr[3].childNodes.length > 1) {
                    return false;
                }
                if (b == 2 && tr[3].childNodes.length < 2) { //只有上传的才参与计算
                    var td = tr[b];                //拿到显示文件大小的td的值
                    var strings = td.innerText.split("k");  //分离出大小
                    var st = Number(strings[0]);       //拿到大小
                    size = Number(size) + st;
                }
            }
        }
    }
    var mytd2 = document.getElementById("yida-tf-td2");//获取标签对象
    mytd2.setAttribute('style', 'color:black');
    mytd2.innerHTML = "总计:" + (size.toFixed(2)) + "kb";

    var mytd = document.getElementById("yida-tf-td");//获取标签对象
    mytd.setAttribute('style', 'color:black');//设置属性字体颜色
    mytd.innerHTML = len.length + '个文件';


    if (len.length == 0) {
        var mytd2 = document.getElementById("yida-tf-td2");//获取标签对象
        mytd2.setAttribute('style', 'color:black');
        mytd2.innerHTML = "总计:" + 0.00 + "kb";
        var mytdy = document.getElementById("yida-tbody");
        var mytd = document.getElementById("yida-tf-td");//获取标签对象
        mytd.setAttribute('style', 'color:black');//设置属性字体颜色
        mytd.innerHTML = 0 + '个文件';
    }

}