/* ----------------------长寿概况配图js-----------------*/

/*-----------------------选择图片更改事件--------------------*/
$(document).ready(function () {
    $('#myfile').change(function () {
        var maxSize = 1024;//最大允许上传1024kb，即：1M
        if ($("#myfile").val() == "" || $("#myfile").val() == null) {
            alert("请先上传图片");
            return false;
        }
        var reg = /\.(jpg|jpeg|png|JPG|PNG)/;
        if (!reg.test($("#myfile").val())) {
            alert("文件类型必须是[.gif,jpeg,jpg,png]");
        }
        var files = document.getElementById("myfile").files;  //得到所有文件的对象
        if (files.length != null) {
            for (var a = 0; a < files.length; a++) {  //遍历出每个对象
                var fi = files[a];           //得到每个对象
                var name = fi.name;          //获取文件名称
                var size = fi.size;           //获取文件大小
                if (size > maxSize * 1024) {
                    alert("图片大小不能超过1M");
                    return false;
                }
                //计算文件大小
                var sizekb = (size / 1024).toFixed(2);//小数后保留2位
                var str = sizekb + "kb"; //图片大小
                var url = window.URL.createObjectURL(fi);//图片预览

                //给每一个文件，添加对应的表格信息，创建子标签
                addfile(str, url, name);
                readsize();
            }
        }
    });
    /*--------------------通用方法--------------------------*/

    //动态添加表格信息
    function addfile(size, url, name) {
        var tbd = document.getElementById("yida-tbody");//获取标签对象
        var tr = document.createElement("tr");//创建标签
        tbd.appendChild(tr);//添加子标签

        var td = document.createElement("td");//创建标签
        var spn4 = document.createElement("img");//创建标签
        spn4.setAttribute("style", "height:30px;winth:30px;");
        spn4.src = url;
        td.appendChild(spn4);//添加子标签*/

        var td1 = document.createElement("td");//创建标签
        td1.innerText = name;  //文件名

        var td2 = document.createElement("td");//创建标签
        td2.innerText = url;   //文件路径

        var t3 = document.createElement("td");//创建标签
        t3.setAttribute("class", "td-size");
        t3.innerText = size;  //文件大小

        var td3 = document.createElement("td");//创建标签
        td3.setAttribute('class', 'text-center');//设置文本居中展示
        td3.setAttribute('style', 'color:red');//设置文本居中展示
        var spn3 = document.createElement("span");//创建标签
        spn3.style = "color:red;margin:5px;";
        spn3.innerHTML = "未上传";
        var spn6 = document.createElement("a");//创建标签
        spn6.href = "javascript:void(0)";
        spn6.setAttribute("style", "text-decoration:none;color:red;");
        spn6.onclick = function () {  //给创建的标签添加点击事件
            var node = this.parentNode.parentNode; //的到当前元素的父元素，的父元素。即创建出的这个TR
            var ta = node.parentNode;  //得到tr的父标签table
            ta.removeChild(node);         //根据table删除掉当前创建出来的tr
            addtd(this);//从新计算总上传文件大小
        };
        spn6.setAttribute("class", "iconfont icon-icon1");
        td3.appendChild(spn3);//添加子标签
        td3.appendChild(spn6);//添加子标签

        tr.appendChild(td);//添加子标签
        tr.appendChild(td1);//添加子标签
        tr.appendChild(td2);//添加子标签
        tr.appendChild(t3);//添加子标签
        tr.appendChild(td3);//添加子标签
    }

    //上传后黑色垃圾桶
    function blackbutton() {
        var mytdy = document.getElementById("yida-tbody"); //获取对象
        var len = mytdy.childNodes;//得到指定对象下的所有孩子，即所有tr
        for (var a = 0; a < len.length; a++) {     //  遍历所有tr
            if (len[a] != null && typeof (len[a]) != "undefined") {
                var tds = len[a].childNodes;
                for (var b = 0; b < tds.length; b++) {
                    if (tds[b] != null && typeof (tds[b]) != "undefined") {
                        var td = tds[b];
                        if (tds[tds.length].innerText)
                            if (td.innerText == "未上传") {

                            } else {
                                var mytdy = document.getElementById("yida-tbody");//获取标签对象
                                var len = mytdy.childNodes;    //得到指定对象下的所有孩子，即所有tr
                                if (len.length != 0) {
                                    var size = 0;  //显示文件大小
                                    var count = 0;   //显示未上传个数
                                    for (var a = 0; a < len.length; a++) {     //  遍历所有tr
                                        var tr = len[a].childNodes;       //得到tr下的所有孩子。即td
                                        if (tr != null && typeof (tr) != "undefined") {
                                            for (var b = 0; b < tr.length; b++) {      // 遍历td.
                                                if (tr[b] != null && typeof (tr[b] != "undefined")) {
                                                    var td = tr[b];  //得到每个td
                                                    if (td.className == "td-size") {
                                                        var si = td.innerText;
                                                        var s = si.substring(0, si.length - 2);
                                                        size = size + Number(s);
                                                        count++;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                var mytd2 = document.getElementById("yida-tf-td2");//获取标签对象
                                mytd2.setAttribute('style', 'color:black');
                                mytd2.innerHTML = "总计:" + (size.toFixed(2)) + "kb";
                                var mytdy = document.getElementById("yida-tbody");
                                var mytd = document.getElementById("yida-tf-td");//获取标签对象
                                mytd.setAttribute('style', 'color:black');//设置属性字体颜色
                                mytd.innerHTML = count + '个文件';
                            }
                    }
                }
            }
        }
    }

    //动态显示总文件大小
    function readsize() {
        var mytdy = document.getElementById("yida-tbody");//获取标签对象
        var len = mytdy.childNodes;    //得到指定对象下的所有孩子，即所有tr
        if (len.length != 0) {
            var size = 0;  //显示文件大小
            var count = 0;   //显示未上传个数
            for (var a = 0; a < len.length; a++) {     //  遍历所有tr
                var tr = len[a].childNodes;       //得到tr下的所有孩子。即td
                if (tr != null && typeof (tr) != "undefined") {
                    for (var b = 0; b < tr.length; b++) {      // 遍历td.
                        if (tr[b] != null && typeof (tr[b] != "undefined")) {
                            var td = tr[b];  //得到每个td
                            if (td.className == "td-size") {
                                var si = td.innerText;
                                var s = si.substring(0, si.length - 2);
                                size = size + Number(s);
                                count++;
                            }
                        }
                    }
                }
            }
        }
        var mytd2 = document.getElementById("yida-tf-td2");//获取标签对象
        mytd2.setAttribute('style', 'color:red');
        mytd2.innerHTML = "(未上传)总计:" + (size.toFixed(2)) + "kb";
        var mytdy = document.getElementById("yida-tbody");
        var mytd = document.getElementById("yida-tf-td");//获取标签对象
        mytd.setAttribute('style', 'color:red');//设置属性字体颜色
        mytd.innerHTML = count + '个文件(未上传)';
    }

    //未上传垃圾桶点击按钮
    function addtd(t) {
        var mytdy = document.getElementById("yida-tbody");//获取标签对象
        var len = mytdy.childNodes;
        var size = 0;
        var count = 0;
        for (var a = 0; a < len.length; a++) {
            if (len[a] != null && typeof (len[a]) != "undefined") {
                var tds = len[a].childNodes;
                for (var b = 0; b < tds.length; b++) {
                    var td = tds[b];
                    if (td.innerText == "未上传") {
                        var fu = td.parentNode;
                        var so = fu.childNodes[3].innerText;
                        size = size + Number(so.substring(0, so.length - 2));
                        count++;
                    }
                }

            }
        }
        if (len.length - 1 == 0) {
            var mytd2 = document.getElementById("yida-tf-td2");//获取标签对象
            mytd2.setAttribute('style', 'color:black');
            mytd2.innerHTML = "总计:" + (0.00.toFixed(2)) + "kb";
            var mytdy = document.getElementById("yida-tbody");
            var mytd = document.getElementById("yida-tf-td");//获取标签对象
            mytd.setAttribute('style', 'color:black');//设置属性字体颜色
            mytd.innerHTML = 0 + '个文件(未上传)';
        } else {
            var mytd2 = document.getElementById("yida-tf-td2");//获取标签对象
            mytd2.setAttribute('style', 'color:red');
            mytd2.innerHTML = "总计:" + (size.toFixed(2)) + "kb";
            var mytdy = document.getElementById("yida-tbody");
            var mytd = document.getElementById("yida-tf-td");//获取标签对象
            mytd.setAttribute('style', 'color:red');//设置属性字体颜色
            mytd.innerHTML = count + '个文件(未上传)';
        }
    }

    //提示框
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

    /*--------------------通用方法--------------------------*/
    /*---------------------------上传按钮-------------------------------*/
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
            url: "getAjaxUploadFiles",
            type: 'POST',
            async: true,
            cache: false,//不使用缓存
            data: formData,
            processData: false,//必须false避开jQuery对formdata的默认处理,而 XMLHttpRequest会对formdata正确处理
            contentType: false,//必须false才会自动加上正确的Content-Type，formdata里指 定过了
            success: function (result, status, xhr) {//请求成功回调
                if (status == "success") {
                    var spotImages = document.getElementById("imgs");
                    spotImages.value = result.toString().substring(1, result.toString().length - 1);

                    var mytdy = document.getElementById("yida-tbody"); //获取对象
                    var len = mytdy.childNodes;//得到指定对象下的所有孩子，即所有tr
                    for (var a = 0; a < len.length; a++) {     //  遍历所有tr
                        if (len[a] != null && typeof (len[a]) != "undefined") {
                            var tds = len[a].childNodes;
                            for (var b = 0; b < tds.length; b++) {
                                if (tds[b] != null && typeof (tds[b]) != "undefined") {
                                    var td = tds[b];
                                    if (td.innerText == "未上传") {
                                        td.innerHTML = "";
                                        td.innerText = "";
                                        var sp6 = document.createElement("a");//创建标签
                                        sp6.href = "javascript:void(0)";
                                        sp6.setAttribute("class", "iconfont icon-icon1");
                                        sp6.setAttribute("style", "text-decoration:none;color:black;");
                                        sp6.onclick = function () {  //给创建的标签添加点击事件
                                            var nod = this.parentNode.parentNode; //的到当前元素的父元素，的父元素。即创建出的这个TR
                                            var t = nod.parentNode;  //得到tr的父标签table
                                            t.removeChild(nod);
                                            blackbutton();

                                        }
                                        td.appendChild(sp6);//添加子标签
                                    }
                                }
                            }
                        }
                    }
                    var mytd2 = document.getElementById("yida-tf-td2");//获取标签对象
                    mytd2.setAttribute('style', 'color:black');
                    var sts = mytd2.innerText;
                    var strings = sts.split(")");
                    mytd2.innerText = strings[1];

                    var mytd = document.getElementById("yida-tf-td");//获取标签对象
                    mytd.setAttribute('style', 'color:black');//设置属性字体颜色
                    var ins = mytd.innerText;
                    var sl = ins.split("(");
                    mytd.innerText = sl[0];

                    //上传成功后清空file
                    // var fil = document.getElementById("myfile");
                    // fil.outerHTML = fil.outerHTML;
                }
            },
            error: function (xhr) {//请求失败回调
                var str = "ERROR:" + xhr.statusText + "---" + xhr.status;
                alert("上传失败---" + str);
            }
        });

    });
    /*---------------------------上传按钮-------------------------------*/
});
/*-----------------------选择图片更改事件--------------------*/
