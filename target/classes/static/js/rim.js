$(document).ready(function () {
    /*------------------------修改页缩虐图样式--------------------------*/
    $("#uploadfile_a2").hover(function () {//缩虐图hover时间
        var attr = $("#yida-my-img").attr("src");   //鼠标经过
        if (attr != "../img/shangchuan.jpg") {
            $("#icon").attr("class", "iconfont icon-icon1 bg-dark d-inline-block");
            $("#icon").click(function () {
                var imgs = document.getElementById("yida-my-img");
                $("#myfile2").val() == null;
                imgs.src = "../img/shangchuan.jpg";
            });
        }
    }, function () {  //鼠标离开
        $("#icon").attr("class", "iconfont icon-icon1 bg-dark d-none")
    });
    /*------------------------修改页缩虐图样式--------------------------*/


    /*---------------------------配图上传----------------------------------*/
    $("#uploadfile").click(function () {
        var maxSize = 1024;//最大允许上传1024kb，即：1M
        if ($("#myfile").val() == "" || $("#myfile").val() == null) {
            $("#show_span").text("请先上传图片");
            showAndHide();
            return false;
        }
        var reg = /\.(jpg|jpeg|png|JPG|PNG)/;
        if (!reg.test($("#myfile").val())) {
            $("#show_span").text("文件类型必须是[.gif,jpeg,jpg,png]");
            showAndHide();
        } else {
            var file = $("#myfile")[0].files[0];
            var size = file.size;
            if (size > maxSize * 1024) {
                $("#show_span").text("图片大小不能超过1M");
                showAndHide();
            }
        }
        //文件上传
        var form = $("#myform")[0];
        var formData = new FormData(form);
        //添加文件对象到表单
        var file = $("#myfile")[0].files;
        for (var a = 0; a < file.length; a++) {
            formData.append("file", file[a]);
        }

        $.ajax({
            url: "getAjaxUploadFiles3",
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
                    for (var a = 0; a < len.length; a++) { //  遍历所有tr
                        if (len[a].className != "text-center yida-ysc") { //不等于说明是新上传的图片
                            var tds = len[a].childNodes;
                            for (var b = 0; b < tds.length; b++) {
                                if (b == 3 && tds[b] != "undefined") {
                                    var TD = tds[b];
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
                                    }
                                    TD.appendChild(sp6);//添加子标签
                                }
                            }
                        }

                    }

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
                    var mytd2 = document.getElementById("yida-tf-td2");//获取标签对象
                    mytd2.setAttribute('style', 'color:black');
                    var s = mytd2.innerText;
                    var strings1 = s.split(":");
                    var strings = strings1[1].substring(0, strings1[1].length - 2);  //分离出大小
                    var st = Number(strings);
                    mytd2.innerHTML = "总计:" + ((size + st).toFixed(2)) + "kb";
                    filePut();
                    //上传成功后清空file
                    var fil = document.getElementById("myfile");
                    fil.outerHTML = fil.outerHTML;
                }
            },
            error: function (xhr) {//请求失败回调
                var str = "ERROR:" + xhr.statusText + "---" + xhr.status;
                alert("上传失败---" + str);
            }
        });
    });

    function filePut() {
        var mytd = document.getElementById("yida-tf-td");//获取标签对象
        mytd.setAttribute('style', 'color:black');//设置属性字体颜色
        mytd.innerHTML = 0 + '个文件(未上传)';
    }

//动态展示总上传文件大小
    function addtd2() {
        var mytdy = document.getElementById("yida-tbody"); //获取对象
        var len = mytdy.childNodes;    //得到指定对象下的所有孩子，即所有tr
        var size = 0;  //显示文件大小
        for (var a = 0; a < len.length; a++) {     //  遍历所有tr
            if (typeof (len[a].innerHTML) != "undefined") {
                var tr = len[a].childNodes;       //得到tr下的所有孩子。即所有td
                if (tr != null) {
                    var ss = new Array();
                    for (var b = 0; b < tr.length; b++) {// 遍历td.
                        if (typeof (tr[b].innerHTML) != "undefined") {
                            var s = tr[b];
                            ss.push(s);
                        }
                    }
                    var int = ss[2].innerText;
                    var strings = int.split("k");
                    var st = Number(strings[0].substring(0, strings[0].length - 2));
                    size = size + st;
                }
            }
        }
        var mytd2 = document.getElementById("yida-tf-td2");//获取标签对象
        $("ida-tf-td2").attr
        mytd2.setAttribute('style', 'color:black');
        mytd2.innerHTML = "总计:" + (size.toFixed(2)) + "kb";

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