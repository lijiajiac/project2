$('#myfile').change(function () {  //动态添加表格，显示上传的文件信息

    var maxSize = 1024;//最大允许上传1024kb，即：1M
    var reg = /\.(jpg|jpeg|png|JPG|PNG)/;
    if (!reg.test($("#myfile").val())) {
        $("#show_span").text("文件类型必须是[.gif,jpeg,jpg,png]");
        showAndHide();
        //不符合时情空file，重新选择上传的文件
        var files = document.getElementById("myfile");
        files.value = "";
        return false;
    }
    var files = document.getElementById("myfile").files;//得到所有文件的对象
    if (files.length != null) {
        //记录未上传的文件个数
        var value = document.getElementById("imgsize").value;
        value = Number(value) + files.length;
        document.getElementById("imgsize").value = value;

        for (var a = 0; a < files.length; a++) {  //遍历出每个对象
            var fi = files[a];           //得到每个对象
            var name = fi.name;          //获取文件名称
            var size = fi.size;           //获取文件大小
            if (size > maxSize * 1024) {
                $("#show_span").text("图片大小不能超过1M");
                showAndHide();
                return false;
            }
            //计算文件大小
            var sizekb = (size / 1024).toFixed(2);//小数后保留2位
            var str = sizekb + "kb"; //图片大小
            var url = window.URL.createObjectURL(fi);//图片预览
            //给每一个文件，添加对应的表格信息，创建子标签
            addfile(str, url, name);
        }
    }
    addtd();//动态显示总上传文件大小
    var tab = document.getElementById("yida-table");//获取标签对象
    tab.setAttribute("class", 'table table-striped rounded-0 h-100 m-3 p-0 w-50');


});

//动态展示总上传文件大小
function addtd() {
    var mytdy = document.getElementById("yida-tbody"); //获取对象
    var len = mytdy.getElementsByTagName("tr");    //得到指定对象下的所有孩子，即所有tr
    if (len.length != 0) {
        var size = 0;  //显示文件大小
        var count = 0;   //显示未上传个数
        for (var a = 0; a < len.length; a++) {     //  遍历所有tr
            var tr = len[a].childNodes;       //得到tr下的所有孩子。即所有td
            if (tr != null) {
                for (var b = 0; b < tr.length; b++) {      // 遍历td.
                    if (b == 2 && tr[3].childNodes.length > 1) { //只有未上传的才参与计算
                        var td = tr[b];                //拿到显示文件大小的td的值
                        var strings = td.innerText.split("k");  //分离出大小
                        var st = Number(strings[0]);       //拿到大小
                        size = Number(size) + st;
                        count = Number(count + 1);//叠加，拿到总大小
                    }
                }
            }
        }

        if (count != 0) { //当有未上传的图片时 。显示的是未上传的图片和大小
            var mytd2 = document.getElementById("yida-tf-td2");//获取标签对象
            mytd2.setAttribute('style', 'color:red');
            mytd2.innerHTML = "(未上传)总计:" + (size.toFixed(2)) + "kb";
            var mytd = document.getElementById("yida-tf-td");//获取标签对象
            mytd.setAttribute('style', 'color:red');//设置属性字体颜色
            mytd.innerHTML = count + '个文件(未上传)';

        } else if (count == 0) {    //没有未上传的图片时 显示已上传的文件大小和个数
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


            //显示文件个数
            var mytdy = document.getElementById("yida-tbody"); //获取对象
            var len = mytdy.getElementsByTagName("tr");   //得到指定对象下的所有孩子，即所有tr
            var mytd = document.getElementById("yida-tf-td");//获取标签对象
            mytd.setAttribute('style', 'color:black');//设置属性字体颜色
            mytd.innerHTML = len.length + '个文件';

        }


    }
}

//动态添加TR，文件信息展示
function addfile(str, url, name) {

    var tbd = document.getElementById("yida-tbody");//获取标签对象
    var tr = document.createElement("tr");//创建标签
    tbd.appendChild(tr);//添加子标签

    var td = document.createElement("td");//创建标签
    var spn4 = document.createElement("img");//创建标签
    spn4.setAttribute("style", "height:30px;winth:30px;");
    spn4.src = url;
    td.appendChild(spn4);//添加子标签*/

    var td1 = document.createElement("td");//创建标签
    var spn = document.createElement("span");//创建标签
    spn.innerText = name;
    td1.appendChild(spn);//添加子标签

    var td2 = document.createElement("td");//创建标签
    td2.setAttribute('class', 'yida-size');//设置文本居中展示
    var spn2 = document.createElement("span");//创建标签
    spn2.innerHTML = str;
    td2.appendChild(spn2);//添加子标签

    var td3 = document.createElement("td");//创建标签
    td3.setAttribute('class', 'text-center');//设置文本居中展示
    td3.setAttribute('style', 'color:red');//设置文本居中展示
    var spn3 = document.createElement("span");//创建标签
    spn3.style = "color:red;margin:5px;";
    var spn6 = document.createElement("a");//创建标签
    spn6.href = "javascript:void(0)";
    spn6.setAttribute("style", "text-decoration:none;color:red;");
    spn6.onclick = function () {  //给创建的标签添加点击事件
        var node = this.parentNode.parentNode; //的到当前元素的父元素，的父元素。即创建出的这个TR
        var ta = node.parentNode;  //得到tr的父标签table
        ta.removeChild(node);         //根据table删除掉当前创建出来的tr

        addtd();//从新计算总上传文件大小

        var sizeimg = document.getElementById("imgsize");
        sizeimg.value = Number(sizeimg.value) - 1;


    };
    spn3.innerHTML = "未上传";
    spn6.setAttribute("class", "iconfont icon-icon1");
    td3.appendChild(spn3);//添加子标签
    td3.appendChild(spn6);//添加子标签

    tr.appendChild(td);//添加子标签
    tr.appendChild(td1);//添加子标签
    tr.appendChild(td2);//添加子标签
    tr.appendChild(td3);//添加子标签
}
