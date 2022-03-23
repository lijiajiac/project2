$(document).ready(function () {
    $("#uploadfile").click(function () {
        var maxSize = 10240;//最大允许上传1024kb，即：1M
        if ($("#myfile").val() == "" || $("#myfile").val() == null) {
            alert("请先上传视频");
            return false;
        }
        var reg = /\.(mp4)/;
        if (!reg.test($("#myfile").val())) {
            alert("文件类型必须是[.mp4]");
        } else {
            var file = $("#myfile")[0].files[0];
            var size = file.size;
            if (size > maxSize * 1024) {
                alert("图片大小不能超过10M");
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
                            console.log(tds.childNodes)
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
                                }
                                TD.appendChild(sp6);//添加子标签
                            }
                        }
                    }
                    filePut();
                    // newsize();
                    addtd2();
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

}