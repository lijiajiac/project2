function quanxuan() {
    var qx = document.getElementById("quanxuan");
    var arr = document.getElementsByName("che");
    for (var a = 0; a < arr.length; a++) {
        var zf = arr[a];
        zf.checked = qx.checked;
    }
}

function danxuan() {
    var quanxuan = document.getElementById("quanxuan");
    var arr = document.getElementsByName("che");
    var count = 0;
    for (var a = 0; a < arr.length; a++) {
        var f = arr[a];
        if (f.checked == false) {
            quanxuan.checked = false;
        } else {
            count++;
        }
    }
    if (count == arr.length) {
        quanxuan.checked = true;
    }
}

/*function deleteone(th) {  //单选删除按钮
    var chi = th.parentNode.parentNode.childNodes;
    var fir = chi[3].firstChild;
    console.log(fir.checked);
    if (fir.checked == false) {
        alert("请选择删除的景点");
        return false;
    } else {
        return true;
    }
}*/
function deletes0() {//多选删除
    var ids = "";
    var arr = document.getElementsByName("che");
    var count = 0;
    for (var a = 0; a < arr.length; a++) {
        var zf = arr[a];
        if (zf.checked == true) {
            count++;
            var pre = zf.parentNode.parentNode.childNodes;
            var a1 = pre[1].value;
            ids += (a1 + ",");
        }
    }
    if (count == 0) {
        $("#show_span").text("请选择一组需要删除的视频");
        showAndHide();
        return false;
    } else {
        var vas = document.getElementById("page-count").value;///dbd
        alert(vas);
        window.location.href = "/main/videodu?" + "ids=" + ids + "&" + "page=" + parseInt(vas);//使用js传递参数到指定的controller里
    }
}


function deletes() { //景点列表
    var ids = "";
    var arr = document.getElementsByName("che");
    var count = 0;
    for (var a = 0; a < arr.length; a++) {
        var zf = arr[a];
        if (zf.checked == true) {
            count++;
            var pre = zf.parentNode.parentNode.childNodes;
            var a1 = pre[1].value;
            ids += (a1 + ",");
        }
    }
    if (count == 0) {
        $("#show_span").text("请选择一组需要删除的景点");
        showAndHide();
        return false;
    } else {
        window.location.href = "/main/delete2?" + "ids=" + ids;//使用js传递参数到指定的controller里
    }
}


function deletess() {//消息列表
    var ids = "";
    var arr = document.getElementsByName("che");
    var count = 0;
    for (var a = 0; a < arr.length; a++) {
        var zf = arr[a];
        if (zf.checked == true) {
            count++;
            var pre = zf.parentNode.parentNode.childNodes;
            var a1 = pre[1].value;
            ids += (a1 + ",");
        }
    }
    if (count == 0) {
        $("#show_span").text("请选择一组需要删除的景点");
        showAndHide();
        return false;
    } else {
        window.location.href = "/main/medelete2?" + "ids=" + ids;//使用js传递参数到指定的controller里
    }
}

function deletes2() {
    var ids = "";
    var arr = document.getElementsByName("che");
    var count = 0;
    for (var a = 0; a < arr.length; a++) {
        var zf = arr[a];
        if (zf.checked == true) {
            count++;
            var pre = zf.parentNode.parentNode.childNodes;
            var a1 = pre[1].value;
            ids += (a1 + ",");
        }
    }
    if (count == 0) {
        $("#show_span").text("请选择一组需要删除的景点");
        showAndHide();
        return false;
    } else {
        window.location.href = "/main/medelete3?" + "ids=" + ids;//使用js传递参数到指定的controller里
    }
}

function deleterim() {//周边列表
    var ids = "";
    var arr = document.getElementsByName("che");
    var count = 0;
    for (var a = 0; a < arr.length; a++) {
        var zf = arr[a];
        if (zf.checked == true) {
            count++;
            var pre = zf.parentNode.parentNode.childNodes;
            var a1 = pre[1].value;
            ids += (a1 + ",");
        }
    }
    if (count == 0) {
        $("#show_span").text("请选择一组需要删除的景点");
        showAndHide();
        return false;
    } else {
        window.location.href = "/main/rim3?" + "ids=" + ids;//使用js传递参数到指定的controller里
    }
}

//主界面的跳转按钮实现
function dian() {
    var page = document.getElementById("my-yida-page");//跳转页
    var pagemax = document.getElementById("my-yida-pagemax");//总页数
    var reh = /^[1-9]*$/;
    if (reh.test(page.value) == false) {
        $("#show_span").text("请输入正确格式的跳转页数，只能为数字.不能小于0!!!");
        showAndHide();
        return false;
    }
    if (page.value > 0) {
        if (page.value <= pagemax.value) {//当输入查找的页数小于等于总页数时才展示
            var value = page.value;
            window.location.href = "/main/scennic?" + "page=" + value;//使用js传递参数到指定的分页界面里
        } else if (page.value > pagemax.value) {
            $("#show_span").text("跳转页不能大于总页数！！！");
            showAndHide();
            return false;
        }
    }
}

//搜索按钮的分页跳转按钮实现
function dian2() {
    var page = document.getElementById("my-yida-page");
    var pagemax = document.getElementById("my-yida-pagemax2");
    var title = document.getElementById("title").value;
    var scenic_id = document.getElementById("scenic.id").value;
    var begin = document.getElementById("begin").value;
    var end = document.getElementById("end").value;

    var reh = /^[1-9]*$/;
    if (reh.test(page.value) == false) {
        $("#show_span").text("请输入正确格式的跳转页数，只能为数字.不能小于0!!!");
        showAndHide();
        return false;
    }
    if (page.value > 0) {
        if (page.value <= pagemax.value) {//当输入查找的页数小于等于总页数时才展示
            var value = page.value;
            window.location.href = "/main/readall2?title=" + title + "&scenic.id=" + scenic_id + "&begin=" + begin + "&end=" + end + "&page=" + value;//使用js传递参数到指定的分页界面里
        } else if (page.value > pagemax.value) {
            $("#show_span").text("跳转页不能大于总页数！！！");
            showAndHide();
            return false;
        }
    }
}

//焦点消息
function dian3() {
    var page = document.getElementById("my-yida-page");//跳转页
    var pagemax = document.getElementById("my-yida-pagemax");//总页数
    var reh = /^[1-9]*$/;
    if (reh.test(page.value) == false) {
        $("#show_span").text("请输入正确格式的跳转页数，只能为数字.不能小于0!!!");
        showAndHide();
        return false;
    }
    if (page.value > 0) {
        if (page.value <= pagemax.value) {//当输入查找的页数小于等于总页数时才展示
            var value = page.value;
            window.location.href = "/main/message?" + "page=" + value;//使用js传递参数到指定的分页界面里
        } else if (page.value > pagemax.value) {
            $("#show_span").text("跳转页不能大于总页数！！！");
            showAndHide();
            return false;
        }
    }
}

//焦点消息
function dian4() {
    var page = document.getElementById("my-yida-page");//跳转页
    var pagemax = document.getElementById("my-yida-pagemax2");//总页数
    var title = document.getElementById("title").value;
    var begin = document.getElementById("begin").value;
    var end = document.getElementById("end").value;
    var reh = /^[1-9]*$/;
    if (reh.test(page.value) == false) {
        $("#show_span").text("请输入正确格式的跳转页数，只能为数字.不能小于0!!!");
        showAndHide();
        return false;
    }
    if (page.value > 0) {
        if (page.value <= pagemax.value) {//当输入查找的页数小于等于总页数时才展示
            var value = page.value;
            window.location.href = "/main/message?title=" + title + "&begin=" + begin + "&end=" + end + "&page=" + value;//使用js传递参数到指定的分页界面里
        } else if (page.value > pagemax.value) {
            $("#show_span").text("跳转页不能大于总页数！！！");
            showAndHide();
            return false;
        }
    }
}

//消息列表
function dian5() {
    var page = document.getElementById("my-yida-page");//跳转页
    var pagemax = document.getElementById("my-yida-pagemax");//总页数
    var reh = /^[1-9]*$/;
    if (reh.test(page.value) == false) {
        $("#show_span").text("请输入正确格式的跳转页数，只能为数字.不能小于0!!!");
        showAndHide();
        return false;
    }
    if (page.value > 0) {
        if (page.value <= pagemax.value) {//当输入查找的页数小于等于总页数时才展示
            var value = page.value;
            window.location.href = "/main/message2?" + "page=" + value;//使用js传递参数到指定的分页界面里
        } else if (page.value > pagemax.value) {
            $("#show_span").text("跳转页不能大于总页数！！！");
            showAndHide();
            return false;
        }
    }
}

//消息列表
function dian6() {
    var page = document.getElementById("my-yida-page");//跳转页
    var pagemax = document.getElementById("my-yida-pagemax2");//总页数
    var title = document.getElementById("title").value;
    var begin = document.getElementById("begin").value;
    var end = document.getElementById("end").value;
    var reh = /^[1-9]*$/;
    if (reh.test(page.value) == false) {
        $("#show_span").text("请输入正确格式的跳转页数，只能为数字.不能小于0!!!");
        showAndHide();
        return false;
    }
    if (page.value > 0) {
        if (page.value <= pagemax.value) {//当输入查找的页数小于等于总页数时才展示
            var value = page.value;
            window.location.href = "/main/mes2?title=" + title + "&begin=" + begin + "&end=" + end + "&page=" + value;//使用js传递参数到指定的分页界面里
        } else if (page.value > pagemax.value) {
            $("#show_span").text("跳转页不能大于总页数！！！");
            showAndHide();
            return false;
        }
    }
}


