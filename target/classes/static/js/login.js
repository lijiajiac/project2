$(document).ready(function () {
    $('#login_btn').click(function () {
        var name = $('#name').val();
        var reg = /^([0-9]|[A-Z]|[a-z]){2,6}$/;
        var reg2 = /[^\x00-\x80]/; //中文校验
        if (name == "" || name == null) {  //验证用户名
            $("#show_tips").attr("class", "ml-4 mt-4 mr-4 border border-1 p-1 pl-2 d-block");
            $("#name_div").attr("class", "form-group pl-4 pr-4 pt-1");
            $("#show_tips").text("用户名不能为空");
            return false;
        }/*else {
            //if(reg2.test(name)==false) {if(name.length>6){alert("中文不能超过6位数"); return false;}}

            if (reg.test(name) == false) {
                $("#show_tips").attr("class", "ml-4 mt-4 mr-4 border border-1 p-1 pl-2 d-block");
                $("#name_div").attr("class", "form-group pl-4 pr-4 pt-1");
                $("#show_tips").text("用户名的格式不正确，最少2位，最多6数 格式为A-Z,a-z,0-9或者汉字");
                return false;
            }

        }*/
        var pwd = $('#password').val();   //验证密码
        var reg = /^[0-9]{1,6}$/;
        if (pwd == "" || pwd == null) {
            $("#show_tips").attr("class", "ml-4 mt-4 mr-4 border border-1 p-1 pl-2 d-block");
            $("#name_div").attr("class", "form-group pl-4 pr-4 pt-1");
            $("#show_tips").text("密码不能为空");
            return false;
        } /*else {
                if (reg.test(pwd) == false) {
                    $("#show_tips").attr("class", "ml-4 mt-4 mr-4 border border-1 p-1 pl-2 d-block");
                    $("#name_div").attr("class", "form-group pl-4 pr-4 pt-1");
                    $("#show_tips").text("密码的格式不正确，最少6位数 格式为A-Z,a-z,0-9");
                    return false;
                }
            }*/

        return true;
    });

});