/**
 * Created by tmw090906 on 2017/6/26.
 */
$("#register_btn").click(function () {
    var jsonData = $("#registerForm").serialize();
    var i = 0;
    $("input").each(function(){  //遍历input标签，判断是否有内容未填写
        var vl=$(this).val();
        if(vl==""){
            i=1;
        }
    });
    if(i == 1){
        alert("信息未填写完整");
        return false;
    }
    $.post('/book/user/register.do', jsonData, function (flag) {
        if (flag.status == 1) {
            alert("注册成功，点击确定前往登录页");
            window.location.href = "login.html";
        }else {
            alert(flag.msg);
        }
    });
});
$("#username").blur(function () {
    var str = $("#username").val();
    var type = "USERNAME";
    var jsonData = "str=" + str + "&type=" + type;
    $.post('/book/user/checkValid.do', jsonData, function (flag) {
        if (flag.status == 1) {

        }else {
            alert(flag.msg);
        }
    });
});
$("#email").blur(function () {
    var str = $("#email").val();
    var type = "EMAIL";
    var jsonData = "str=" + str + "&type=" + type;
    $.post('/book/user/checkValid.do', jsonData, function (flag) {
        if (flag.status == 1) {

        }else {
            alert(flag.msg);
        }
    });
});


