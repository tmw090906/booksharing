/**
 * Created by tmw090906 on 2017/6/26.
 */
var vm = new Vue({
    el : "#app",
    data : {
        username : "public library",
        question : "this is fault"
    },
    mounted: function () {
        $("#inputUsername").show();
        $("#confirm").hide();
        $("#reset").hide();
    },
    methods: {
        getQuestion : function () {
            var username = $("#username").val();
            _this = this;
            var jsonData = "username=" + username;
            $.post('/book/user/forget_get_question.do',jsonData,function (flag) {
                if (flag.status == 1) {
                    _this.question=flag.date;
                    _this.username = username;
                    $("#confirm").show();
                    $("#inputUsername").hide();
                    $("#reset").hide();
                } else {
                    alert(flag.msg);
                }
            });
        },
        confirmQuestion : function () {
            var anwser = $("#anwser").val();
            var jsonData = "username=" + this.username + "&answer=" + anwser + "&question=" + this.question;
            $.post('/book/user/forget_check_answer.do',jsonData,function (flag) {
                if (flag.status == 1) {
                    $("#confirm").hide();
                    $("#inputUsername").hide();
                    $("#reset").show();
                } else {
                    alert(flag.msg);
                }
            });
        },
        resetPassword : function () {
            var passwordNew = $("#newPwd").val();
            var passwordAgain = $("#againPwd").val();
            if(passwordNew != passwordAgain){
                alert("两次输入密码不同");
                return false;
            }
            var jsonData = "username=" + this.username + "&passwordNew=" + passwordNew;
            $.post('/book/user/forget_reset_answer.do',jsonData,function (flag) {
                if (flag.status == 1) {
                    alert("重置密码成功，点击确认前往登录页");
                    window.location.href="login.html";
                } else {
                    alert(flag.msg);
                }
            });
        }
    }
});

