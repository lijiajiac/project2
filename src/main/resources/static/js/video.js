$(document).ready(function () {
    /*-----------------------------------缩虐图的hover事件-----------------------------------------------*/
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
    /*-----------------------------------缩虐图的hover事件-----------------------------------------------*/

    /*------------------------- 视频上传添加视频按钮事件  --------------------------------*/
    $('#myfile').change(function () {
        var maxSize = 1024 * (1024 * 20);//最大允许上传，即：20M
        if ($("#myfile").val() == "" || $("#myfile").val() == null) {
            alert("请先上传视频");
            return false;
        }
        var reg = /\.(mp4)/;
        if (!reg.test($("#myfile").val())) {
            alert("文件类型必须是[.mp4]");
            return false;
        }
        var files = document.getElementById("myfile").files;  //得到所有文件的对象
        if (files.length > 0) {
            var count = 0;
            var cunsize = 0;
            for (var a = 0; a < files.length; a++) {  //遍历出每个文件对象
                var fi = files[a];           //得到每个文件对象
                var name = fi.name;          //获取文件名称
                var size = fi.size;           //获取文件大小
                if (size > maxSize) {
                    alert("视频大小不能超过20MB");
                    return false;
                }
                //计算文件大小，小数后保留2位
                var sizekb = (size / (1024 * 1024)).toFixed(2);
                count = sizekb + count;//累积上传文件的大小
                var str = sizekb + "MB";//每一个文件的大小
                var path = $(this).val();//当前视频路径
                var tbos = document.getElementById("yida-tbody");
                var trs = tbos.getElementsByTagName("tr");
                if (trs.length > 0) {   //判断视频是否已经存在
                    for (var a = 0; a < trs.length; a++) {
                        var na = trs[a].childNodes[0].innerText;
                        var si = trs[a].childNodes[1].innerText;
                        if (si == str && na == name) {
                            alert("请不要上传已存在的视频！！！");
                            return false;
                        }
                    }
                }

                addfile(str, path, name); //动态创建tr展示添加的视频信息
                cunsize = Number(cunsize) + Number(sizekb);
            }
            //显示未上传的个数及大小
            var td = document.getElementById("yida-tf-td");
            td.setAttribute("style", "color:red");
            td.innerHTML = "";
            td.innerText = files.length + "个视频";
            var td2 = document.getElementById("yida-tf-td2");
            td2.setAttribute("style", "color:red");
            td2.innerHTML = "";
            td2.innerText = "(未上传)总计:" + cunsize + "MB";
        }

    });
    /*------------------------- 视频上传---添加视频按钮事件  --------------------------------*/

    /*-------------------------    视频上传      -----------------------------*/
    $("#uploadfile").click(function () {
        if ($("#myfile").val() == "" || $("#myfile").val() == null) {
            alert("请先上传视频");
            return false;
        }
        var reg = /\.(mp4)/;
        if (!reg.test($("#myfile").val())) {
            alert("文件类型必须是[.mp4]");
            return false;
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
            url: "getAjaxUploadFiles2",
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
                            if (tds[b].childNodes.length > 1) {
                                var TD = tds[b];
                                TD.innerHTML = "";
                                var sp6 = document.createElement("a");//创建标签
                                sp6.href = "javascript:void(0)";
                                sp6.setAttribute("class", "iconfont icon-icon1");
                                sp6.setAttribute("style", "text-decoration:none;color:black;");
                                sp6.onclick = function () {  //给创建的标签添加点击事件
                                    var nod = this.parentNode.parentNode; //的到当前元素的父元素，的父元素。即创建出的这个TR
                                    var t = nod.parentNode;  //得到tr的父标签table
                                    t.removeChild(nod);

                                    var spotImages = document.getElementById("spotImages").value;
                                    console.log(spotImages);
                                    if (spotImages.split(":").length > 2) {
                                        var sp = spotImages.split(",");
                                        for (var a = 0; a < sp.length; a++) {
                                            var s = sp[a].split(":");
                                            var ss = s[1].substring(1, s[1].length - 1);
                                            var p = s[0].substring(1, s[0].length - 1);
                                            var nas = p.split("&");
                                            var na = nas[1];
                                            if (na == nod.childNodes[0].innerText && ss == nod.childNodes[1].innerText) {
                                                var number = spotImages.indexOf(sp[a]);
                                                if (number > 2) {
                                                    var s1 = spotImages.substring(0, number - 1);
                                                    document.getElementById("spotImages").value = s1;
                                                } else {
                                                    var s2 = spotImages.substring(sp[a].length, spotImages.length);
                                                    document.getElementById("spotImages").value = s2;
                                                }
                                            }
                                        }
                                    } else {
                                        document.getElementById("spotImages").innerText = "";
                                    }

                                    var tboy = document.getElementById("yida-tbody");
                                    var trs = tboy.getElementsByTagName("tr");
                                    var coun = 0;
                                    for (var a = 0; a < trs.length; a++) {
                                        var td = trs[a].childNodes[2].childNodes.length;
                                        if (td > 1) {
                                            coun++;
                                        }
                                    }
                                    if (coun == 0) {   //点击后当页面没有未上传的视频才更新文件大小和个数，有则不更新
                                        var tboy2 = document.getElementById("yida-tbody");
                                        var trs2 = tboy2.getElementsByTagName("tr");
                                        var size = 0;
                                        for (var a = 0; a < trs2.length; a++) {
                                            var tdsi = trs[a].childNodes[1].innerText;
                                            var split = tdsi.split("M");
                                            size = Number(size) + Number(split[0]);
                                        }
                                        var tf = document.getElementById("yida-tf-td2");
                                        tf.innerText = "总计:" + size + "MB";
                                        tf.setAttribute("style", "color:black");
                                        var tf2 = document.getElementById("yida-tf-td");
                                        tf2.setAttribute("style", "color:black");
                                        tf2.innerText = trs2.length + "个视频";
                                    }
                                }
                                TD.appendChild(sp6);//添加子标签
                            }
                        }
                    }
                    addtd();//动态展示未上传的文件和大小
                }
            },
            error: function (xhr) {//请求失败回调
                var str = "ERROR:" + xhr.statusText + "---" + xhr.status;
                alert("上传失败---" + str);
            }
        });
    });


    /*-------------------------    视频上传      -----------------------------*/

//动态添加TR，文件信息展示    添加界面，添加视频使用
    function addfile(str, path, name) {

        var tbd = document.getElementById("yida-tbody");//获取标签对象
        var tr = document.createElement("tr");//创建标签
        tbd.appendChild(tr);//添加子标签

        var td = document.createElement("td");//创建标签
        td.innerText = name;//文件名


        var td2 = document.createElement("td");//创建标签
        td2.setAttribute('class', 'text-center');//设置文本居中展示
        td2.innerText = str;//文件大小

        var td3 = document.createElement("td");//创建标签
        td3.setAttribute('class', 'text-center');//设置文本居中展示
        //td3.setAttribute('style', 'color:red');//设置文本居中展示
        var spn3 = document.createElement("span");//创建标签
        spn3.style = "color:red;margin:5px;";
        spn3.innerHTML = "未上传";
        var spn6 = document.createElement("a");//创建标签
        spn6.href = "javascript:void(0)";
        spn6.setAttribute("style", "text-decoration:none;color:red;");
        spn6.onclick = function () {  //给创建的标签添加点击事件
            var node = this.parentNode.parentNode; //的到当前元素的父元素，的父元素。即创建出的这个TR
            node.remove();
            var chis = node.childNodes[1];   //得到当前点击删除的视频的大小
            var maxsize = document.getElementById("yida-tf-td2").innerText; //得到总大小
            var split = chis.innerText.split("M");    //分离出各自的大小
            var split2 = maxsize.split("M");
            var strings = split2[0].split(":");
            var ss = Number(strings[1]) - Number(split[0]);    //总大小减去删除的大小。然后更新总大小
            document.getElementById("yida-tf-td2").innerText = "(未上传)总计:" + (ss.toFixed(2)) + "MB";
            var str = document.getElementById("yida-tf-td").innerText;    //获取未上传的文件个数
            var sp = str.split("个");
            var number = Number(sp[0]);
            document.getElementById("yida-tf-td").innerText = number - 1 + "个视频"; //更新未上传文件个数
            // 当点击后没有未上传文件时
            if (number - 1 <= 0) {
                var byid = document.getElementById("yida-tbody");
                var chis = byid.childNodes;
                if (chis.length < 0) {    //得到所有子tr，小于0就没有任何文件 。默认显示如下
                    var tf = document.getElementById("yida-tf-td2");
                    tf.innerText = "总计:0.00MB";
                    tf.setAttribute("style", "color:black");
                    var tf2 = document.getElementById("yida-tf-td");
                    tf2.setAttribute("style", "color:black");
                } else {            //当有已上传的文件时.获取文件的大小。和个数。然后更新显示的文件大小和个数
                    var byid = document.getElementById("yida-tbody");
                    var ele = byid.getElementsByTagName("tr");//得到指定标签下的所有指定标签
                    var size = 0;
                    for (var a = 0; a < ele.length; a++) {
                        var tr = ele[a];
                        var ss = tr.childNodes[1].innerText;
                        var split1 = ss.split("M");
                        size = Number(size) + Number(split1[0]);
                    }
                    var tf = document.getElementById("yida-tf-td2");
                    tf.innerText = "总计:" + size + "MB";
                    tf.setAttribute("style", "color:black");
                    var tf2 = document.getElementById("yida-tf-td");
                    tf2.setAttribute("style", "color:black");
                    tf2.innerText = ele.length + "个视频";
                }
            }
        };
        spn6.setAttribute("class", "iconfont icon-icon1");
        td3.appendChild(spn3);//添加子标签
        td3.appendChild(spn6);//添加子标签

        tr.appendChild(td);//添加子标签
        tr.appendChild(td2);//添加子标签
        tr.appendChild(td3);//添加子标签
    }

//上传后更新大小
    function addtd() {

        var mytdy = document.getElementById("yida-tbody"); //获取对象
        var eles = mytdy.getElementsByTagName("tr");//得到所有tr
        var size = 0;
        for (var a = 0; a < eles.length; a++) {
            var tds = eles[a].childNodes[1].innerText;
            console.log(tds);
            var tdsize = tds.split("M");
            size = Number(size) + Number(tdsize[0]);
        }
        console.log(size);
        var mytd2 = document.getElementById("yida-tf-td2");//获取标签对象
        mytd2.setAttribute('style', 'black:red');
        mytd2.innerHTML = "总计:" + (size.toFixed(2)) + "MB";
        var mytdy = document.getElementById("yida-tbody");
        var mytd = document.getElementById("yida-tf-td");//获取标签对象
        mytd.setAttribute('style', 'black:red');//设置属性字体颜色
        mytd.innerHTML = eles.length + '个文件';

        /*if (eles.length != 0) {
            var mytd2 = document.getElementById("yida-tf-td2");//获取标签对象
            mytd2.setAttribute('style', 'black:red');
            mytd2.innerHTML = "总计:" + (size.toFixed(2)) + "MB";
            var mytdy = document.getElementById("yida-tbody");
            var mytd = document.getElementById("yida-tf-td");//获取标签对象
            mytd.setAttribute('style', 'black:red');//设置属性字体颜色
            mytd.innerHTML = eles.length + '个文件';
        } else if (eles.length == 0) {
            var mytd2 = document.getElementById("yida-tf-td2");//获取标签对象
            mytd2.setAttribute('style', 'color:black');
            mytd2.innerHTML = "总计:" + 0.00 + "MB";
            var mytdy = document.getElementById("yida-tbody");
            // var length = mytdy.childNodes.length;  //的到TBODY下所有的tr的数量
            var mytd = document.getElementById("yida-tf-td");//获取标签对象
            mytd.setAttribute('style', 'color:black');//设置属性字体颜色
            mytd.innerHTML = 0 + '个文件';

        }
*/

    }

});