/**
 * Created by tmw090906 on 2017/6/27.
 */
var vm = new Vue({
    el : "#mainContainer",
    data : {
        isFirstPage: true,
        isLastPage: false,
        lastPage:1 ,
        pageNum : 1,
        userInfo : {},
        shippingList : [],
        shippingDetail : {},
        qrUrl : {},
        orderNo : {},
        interval : {},
        payList : [],
        applyList : [],
        appliedList : [],
        bookDetail : {},
        appliedList : [],
        replaceList : [],
        replaceStatus : 1,
        complanList : [],
        illegalList : [],
        adviceList : [],
        adviceDetail : {}
    },
    mounted : function () {
        $(".selfControl").hide();
        _this = this;
        $.post('/book/user/get_user_detail.do',null,function (flag) {
            if(flag.status == 1){
                _this.userInfo = flag.date;
                $("#selfInfo").show();
            }else {
                alert(flag.msg);
            }
        })
    },
    methods : {
        getSelfInfo : function () {
            $(".selfControl").hide();
            _this = this;
            $.post('/book/user/get_user_detail.do',null,function (flag) {
                if(flag.status == 1){
                    _this.userInfo = flag.date;
                    $("#selfInfo").show();
                }else {
                    alert(flag.msg);
                }
            });
        },
        shippingListPageChoice : function (pageNum) {
            this.pageNum =  pageNum;
            this.getShippingList();
        },
        firstGetShippingList : function () {
            this.pageNum = 1;
            this.getShippingList();
        },
        getShippingList : function () {
            $(".selfControl").hide();
            var jsonData = "pageNum=" + this.pageNum;
            _this = this;
            $.post('/book/shipping/list.do',jsonData,function (flag) {
                if(flag.status == 1){
                    _this.shippingList = flag.date.list;
                    _this.isLastPage = flag.date.isLastPage;
                    _this.isFirstPage = flag.date.isFirstPage;
                    _this.lastPage = flag.date.lastPage;
                    _this.pageNum =  flag.date.pageNum;
                    $("#shippingList").show();
                }else if(flag == 10){
                    alert("登陆后才能查看，请登录");
                } else {
                    alert(flag.msg);
                }
            });
        },
        getShippingDetail : function (shippingId) {
            var jsonData = "shippingId=" + shippingId;
            _this = this;
            $.post('/book/shipping/select.do',jsonData,function (flag) {
                if(flag.status == 1){
                    _this.shippingDetail = flag.date;
                    shippingInfoCenter(550,550);
                    $("#shippingInfo").show();
                }else {
                    alert(flag.msg);
                }
            });
        },
        deleteShipping : function (shippingId) {
            var bool = confirm("是否确认删除该条地址信息");
            if(bool == false){
                return false;
            }
            var jsonData = "shippingId=" + shippingId;
            _this = this;
            $.post('/book/shipping/delete.do',jsonData,function (flag) {
                if(flag.status == 1){
                    alert(flag.msg);
                    _this.getShippingList();
                }else {
                    alert(flag.msg);
                }
            });
        },
        getDepositInfo : function () {
            $(".selfControl").hide();
            _this = this;
            $.post('/book/user/get_user_detail.do',null,function (flag) {
                if(flag.status == 1){
                    _this.userInfo = flag.date;
                    $("#deposit").show();
                }else {
                    alert(flag.msg);
                }
            });
        },
        pay : function () {
            var message = "确认向账户充值  " + $("#money").val() + "  元保证金？";
            var bool = confirm(message);
            if(bool == false){
                return false;
            }
            var jsonData = $("#payInput").serialize();
            _this = this;
            $.post('/book/user/pay.do',jsonData,function (flag) {
                if(flag.status == 1){
                    _this.qrUrl = flag.date.qrUrl;
                    _this.orderNo = flag.date.orderNo;
                    $("#paySuccess").hide();
                    $("#inputMoney").hide();
                    $("#binaryImage").show();
                    _this.interval = window.setInterval("_this.queryPayStatus()",1000);
                }else if(flag.status == 10){
                    alert("请登录！");
                }else {
                    alert(flag.msg);
                }
            });
        },
        queryPayStatus : function () {
            var jsonData = "orderNo=" + this.orderNo;
            _this = this;
            $.post('/book/user/query_order_pay_status.do',jsonData,function (flag) {
                if(flag.status == 1){
                    window.clearInterval(_this.interval);
                    $("#inputMoney").hide();
                    $("#binaryImage").hide();
                    $("#paySuccess").show();
                }
            });
        },
        payListPageChoice : function (pageNum) {
            this.pageNum = pageNum;
            this.getPayList();
        },
        firstGetPayList : function () {
            this.pageNum = 1;
            this.getPayList();
        },
        getPayList : function () {
            $(".selfControl").hide();
            var jsonData = "pageNum=" + this.pageNum;
            _this = this;
            $.post('/book/order/list.do',jsonData,function (flag) {
                if(flag.status == 1){
                    _this.payList = flag.date.list;
                    _this.isLastPage = flag.date.isLastPage;
                    _this.isFirstPage = flag.date.isFirstPage;
                    _this.lastPage = flag.date.lastPage;
                    _this.pageNum =  flag.date.pageNum;
                    $("#payInfoList").show();
                }else if(flag.status == 10){
                    alert("请登录！");
                }else {
                    alert(flag.msg);
                }
            });
        },
        cancelOrder : function (orderNumber) {
            _this = this;
            var jsonData = "orderNo=" + orderNumber;
            $.post('/book/order/cancel.do',jsonData,function (flag) {
                if(flag.status == 1){
                    alert("取消订单成功");
                    _this.firstGetPayList();
                }else {
                    alert(flag.msg);
                }
            });
        },
        firstGetSelfApplyList : function () {
            this.pageNum = 1;
            this.getSelfApplyList();
        },
        getSelfApplyList : function () {
            $(".selfControl").hide();
            var jsonData = "pageNum=" + this.pageNum;
            _this = this;
            $.post('/book/apply/apply_list.do',jsonData,function (flag) {
                if(flag.status == 1){
                    _this.applyList = flag.date.list;
                    _this.isLastPage = flag.date.isLastPage;
                    _this.isFirstPage = flag.date.isFirstPage;
                    _this.lastPage = flag.date.lastPage;
                    _this.pageNum =  flag.date.pageNum;
                    $("#selfApplyList").show();
                }else if(flag.status == 10){
                    alert("请登录！");
                }else {
                    alert(flag.msg);
                }
            });
        },
        applyListPageChoice : function (pageNum) {
            this.pageNum = pageNum;
            this.getSelfApplyList();
        },
        getBookDetail : function (bookId) {
            var jsonData = "bookId=" + bookId;
            _this = this;
            $.post('/book/info/get_book_detail.do',jsonData,function (flag) {
                if(flag.status == 1){
                    _this.bookDetail = flag.date;
                    bookDetailCenter(900,650);
                    $("#bookDetailPopWindow").show();
                }else if(flag.status == 10){
                    alert("请登录！");
                }else {
                    alert(flag.msg);
                }
            });
        },
        firstGetAppliedList : function () {
            this.pageNum = 1;
            this.getAppliedList();

        },
        getAppliedList : function () {
            $(".selfControl").hide();
            var jsonData = "pageNum=" + this.pageNum;
            _this = this;
            $.post('/book/apply/applied_list.do',jsonData,function (flag) {
                if(flag.status == 1){
                    _this.appliedList = flag.date.list;
                    _this.isLastPage = flag.date.isLastPage;
                    _this.isFirstPage = flag.date.isFirstPage;
                    _this.lastPage = flag.date.lastPage;
                    _this.pageNum =  flag.date.pageNum;
                    $("#appliedList").show();
                }else if(flag.status == 10){
                    alert("请登录！");
                }else {
                    alert(flag.msg);
                }
            });
        },
        appliedListPageChoice : function (pageNum) {
            this.pageNum = pageNum;
            this.getAppliedList();
        },
        accessApply : function (applyId) {
            var bool = confirm("确认接受该申请？接受申请后请前往置换管理进行操作");
            if(bool == false){
                return false;
            }
            var jsonData = "applyId=" + applyId + "&status=2";
            _this = this;
            $.post('/book/apply/manage_apply.do',jsonData,function (flag) {
                if(flag.status == 1){
                    alert("已同意申请");
                    _this.firstGetAppliedList();
                }else if(flag.status == 10){
                    alert("请登录！");
                }else {
                    alert(flag.msg);
                }
            });
        },
        refusedApply : function (applyId) {
            $("#applyId").val(applyId);
            reasonWindowCenter(350,250);
            $("#reasonPopWindow").show();
        },
        firstGetReplaceList : function (status) {
            this.pageNum = 1;
            this.replaceStatus = status;
            this.getReplaceList();
        },
        getReplaceList : function () {
            $(".selfControl").hide();
            var jsonData = "status=" + this.replaceStatus + "&pageNum=" + this.pageNum;
            _this = this;
            $.post('/book/replace/list.do',jsonData,function (flag) {
                if(flag.status == 1){
                    _this.replaceList = flag.date.list;
                    $("#replaceList").show();
                }else if(flag.status == 10){
                    alert("请登录！");
                }else {
                    alert(flag.msg);
                }
            });
        },
        replacePageChoice : function (pageNum) {
            this.pageNum = pageNum;
            this.getReplaceList();
        },
        getShippingInfo : function (shippingId) {
            var jsonData = "shippingId=" + shippingId;
            _this = this;
            $.post('/book/shipping/select.do',jsonData,function (flag) {
                if(flag.status == 1){
                    _this.shippingDetail = flag.date;
                    shippingInfoPopCenter(550,500);
                    $("#shippingInfoPop").show();
                }else if(flag.status == 10){
                    alert("请登录！");
                }else {
                    alert(flag.msg);
                }
            });
        },
        deliverNoPopWindowShow : function (replaceId) {
            $("#replaceId").val(replaceId);
            deliverNoPopWindowCenter(350,250);
            $("#deliverNoPopWindow").show();
        },
        firstGetShippingPopList : function (replaceId) {
            this.pageNum = 1;
            $("#replaceIdByShipping").val(replaceId);
            this.getShippingPopList();
        },
        getShippingPopList : function () {
            _this = this;
            var jsonData = "pageNum=" + this.pageNum;
            $.post('/book/shipping/list.do',jsonData,function (flag) {
                if(flag.status == 1){
                    _this.shippingList = flag.date.list;
                    _this.isLastPage = flag.date.isLastPage;
                    _this.isFirstPage = flag.date.isFirstPage;
                    _this.lastPage = flag.date.lastPage;
                    _this.pageNum =  flag.date.pageNum;
                    shippingListPopWindowCenter(550,800);
                    $("#shippingListPopWindow").show();
                }else if(flag == 10){
                    alert("登陆后才能查看，请登录");
                } else {
                    alert(flag.msg);
                }
            });
        },
        shippingPopListPageChoice : function (pageNum) {
            this.pageNum = pageNum;
            this.getShippingPopList();
        },
        choiceShipping : function (shippingId) {
            var replaceId = $("#replaceIdByShipping").val();
            var jsonData = "shippingId=" + shippingId + "&replaceId=" + replaceId;
            var bool = confirm("确认选择此地址？");
            if(bool == false){
                return false;
            }
            $.post('/book/replace/update_shipping.do',jsonData,function (flag) {
                if(flag.status == 1){
                   alert("已成功选择地址");
                }else if(flag == 10){
                    alert("登陆后才能查看，请登录");
                } else {
                    alert(flag.msg);
                }
            });
        },
        confirmDeliver : function (replaceId) {
            var jsonData = "replaceId=" + replaceId;
            var bool = confirm("确认已经收货？一旦确认无法更改");
            if(bool == false){
                return false;
            }
            $.post('/book/replace/deliver.do',jsonData,function (flag) {
                if(flag.status == 1){
                    alert("收货成功");
                }else if(flag == 10){
                    alert("登陆后才能查看，请登录");
                } else {
                    alert(flag.msg);
                }
            });
        },
        complanReplace : function (replaceId) {
            var jsonData = "replaceId=" + replaceId;
            var bool = confirm("确定要投诉该置换？");
            if(bool == false){
                return false;
            }
            $.post('/book/replace/complan.do',jsonData,function (flag) {
                if(flag.status == 1){
                    alert("投诉成功");
                }else if(flag == 10){
                    alert("登陆后才能查看，请登录");
                } else {
                    alert(flag.msg);
                }
            });
        },
        cancelReplace : function (replaceId) {
            var jsonData = "replaceId=" + replaceId;
            var bool = confirm("确定要取消该跳置换？？");
            if(bool == false){
                return false;
            }
            $.post('/book/replace/cancel.do',jsonData,function (flag) {
                if(flag.status == 1){
                    alert("取消成功");
                }else if(flag == 10){
                    alert("登陆后才能查看，请登录");
                } else {
                    alert(flag.msg);
                }
            });
        },
        firstGetSelfComplanList : function () {
            this.pageNum = 1;
            this.getSelfComplanList();
        },
        getSelfComplanList : function () {
            $(".selfControl").hide();
            var jsonData = "pageNum=" + this.pageNum;
            _this = this;
            $.post('/book/replace/complan_list.do',jsonData,function (flag) {
                if(flag.status == 1){
                    _this.complanList = flag.date.list;
                    _this.isLastPage = flag.date.isLastPage;
                    _this.isFirstPage = flag.date.isFirstPage;
                    _this.lastPage = flag.date.lastPage;
                    _this.pageNum =  flag.date.pageNum;
                    $("#selfComplanList").show();
                }else if(flag == 10){
                    alert("登陆后才能查看，请登录");
                } else {
                    alert(flag.msg);
                }
            });
        },
        selfComplanListPageChoice : function (pageNum) {
            this.pageNum = pageNum;
            this.getSelfComplanList();
        },
        firstGetSelfIllegalList : function () {
            this.pageNum = 1;
            this.getSelfIllegalList();
        },
        getSelfIllegalList : function () {
            $(".selfControl").hide();
            var jsonData = "pageNum=" + this.pageNum;
            _this = this;
            $.post('/book/user/illegal.do',jsonData,function (flag) {
                if(flag.status == 1){
                    _this.illegalList = flag.date.list;
                    _this.isLastPage = flag.date.isLastPage;
                    _this.isFirstPage = flag.date.isFirstPage;
                    _this.lastPage = flag.date.lastPage;
                    _this.pageNum =  flag.date.pageNum;
                    $("#selfIllegalList").show();
                }else if(flag == 10){
                    alert("登陆后才能查看，请登录");
                } else {
                    alert(flag.msg);
                }
            });
        },
        selfIllegalListPageChoice : function () {
            this.pageNum = pageNum;
            this.getSelfIllegalList();
        },
        firstGetSelfAdviceList : function () {
            this.pageNum = 1;
            this.getSelfAdviceList();
        },
        getSelfAdviceList : function () {
            $(".selfControl").hide();
            var jsonData = "pageNum=" + this.pageNum;
            _this = this;
            $.post('/book/info/get_advice_list.do',jsonData,function (flag) {
                if(flag.status == 1){
                    _this.adviceList = flag.date.list;
                    _this.isLastPage = flag.date.isLastPage;
                    _this.isFirstPage = flag.date.isFirstPage;
                    _this.lastPage = flag.date.lastPage;
                    _this.pageNum =  flag.date.pageNum;
                    $("#selfAdviceList").show();
                }else if(flag == 10){
                    alert("登陆后才能查看，请登录");
                } else {
                    alert(flag.msg);
                }
            });
        },
        selfAdviceListPageChoice : function () {
            this.pageNum = pageNum;
            this.getSelfAdviceList();
        },
        getAdviceDetail : function (adviceId) {
            var jsonData = "adviceId=" + adviceId;
            _this = this;
            $.post('/book/info/get_advice_detail.do',jsonData,function (flag) {
                if(flag.status == 1){
                    _this.adviceDetail = flag.date;
                    adviceDetailPopWindowCenter(600,250);
                    $("#adviceDetailPopWindow").show();
                }else if(flag == 10){
                    alert("登陆后才能查看，请登录");
                } else {
                    alert(flag.msg);
                }
            });
        }

    }
});

var hideShippingInfo = function () {
    $("#shippingInfo").hide();
};

var saveUserInfo = function () {
    var jsonData = $("#selfInfoForm").serialize();
    $.post('/book/user/update_user_info.do',jsonData,function (flag) {
        if(flag.status == 1){
            alert(flag.msg);
        }else {
            alert(flag.msg);
        }
    });
};

var updateShippingInfo = function () {
    var jsonData = $("#updateShippingForm").serialize();
    $.post('/book/shipping/update.do',jsonData,function (flag) {
        if(flag.status == 1){
            alert(flag.msg);
            $("#shippingInfo").hide();
        }else {
            alert(flag.msg);
        }
    });
};


var addShippingInfo = function () {
    var jsonData = $("#addShippingForm").serialize();
    $.post('/book/shipping/add.do',jsonData,function (flag) {
        if(flag.status == 1){
            alert(flag.msg);
        }else {
            alert(flag.msg);
        }
    });
};

var confirmRefused = function () {
    var applyId = $("#applyId").val();
    var reason = $("#reason").val();
    var jsonData = "applyId=" + applyId + "&status=3";
    if(reason != undefined && reason != "" && reason !=null){
        jsonData = jsonData + "&reason=" + reason;
    }
    _this = this;
    $.post('/book/apply/manage_apply.do',jsonData,function (flag) {
        if(flag.status == 1){
            alert("成功拒绝申请");
            $("#reasonPopWindow").hide();
            _this.firstGetAppliedList();
        }else if(flag.status == 10){
            alert("请登录！");
        }else {
            alert(flag.msg);
        }
    });

};

var sendBook = function () {
    var replaceId = $("#replaceId").val();
    var deliverNo = $("#deliverNo").val();
    if(deliverNo == "" || deliverNo == null){
        alert("请输入正确的快运单号,全部为数字");
        return false;
    }
    var jsonData = "replaceId=" + replaceId + "&deliverNo=" + deliverNo;
    $.post('/book/replace/send.do',jsonData,function (flag) {
        if(flag.status == 1){
            alert("发货成功");
            $("#deliverNoPopWindow").hide();
            vm.firstGetReplaceList(1);
        }else if(flag.status == 10){
            alert("请登录！");
        }else {
            alert(flag.msg);
        }
    });
};



var cancelRefused = function () {
    $("#reasonPopWindow").hide();
};


//使弹出框在屏幕中间
var deliverNoPopWindowCenter = function(width,height)
{
    return $("#deliverNoPopWindow").css("left", ($(window).width()-width)/2+$(window).scrollLeft()).
    css("top", ($(window).height()-height)/2+$(window).scrollTop()).
    css("width",width).
    css("height",height);
};

var shippingInfoCenter = function(width,height)
{
    return $("#shippingInfo").css("left", ($(window).width()-width)/2+$(window).scrollLeft()).
    css("top", ($(window).height()-height)/2+$(window).scrollTop()).
    css("width",width).
    css("height",height);
};

var reasonWindowCenter = function(width,height)
{
    return $("#reasonPopWindow").css("left", ($(window).width()-width)/2+$(window).scrollLeft()).
    css("top", ($(window).height()-height)/2+$(window).scrollTop()).
    css("width",width).
    css("height",height);
};

var bookDetailCenter = function(width,height)
{
    return $("#bookDetailPopWindow").css("left", ($(window).width()-width)/2+$(window).scrollLeft()).
    css("top", ($(window).height()-height)/2+$(window).scrollTop()).
    css("width",width).
    css("height",height);
};


var shippingInfoPopCenter = function(width,height)
{
    return $("#shippingInfoPop").css("left", ($(window).width()-width)/2+$(window).scrollLeft()).
    css("top", ($(window).height()-height)/2+$(window).scrollTop()).
    css("width",width).
    css("height",height);
};

var shippingListPopWindowCenter = function (width,height) {
    return $("#shippingListPopWindow").css("left", ($(window).width()-width)/2+$(window).scrollLeft()).
    css("top", ($(window).height()-height)/2+$(window).scrollTop()).
    css("width",width).
    css("height",height);
};

var adviceDetailPopWindowCenter = function (width,height) {
    return $("#adviceDetailPopWindow").css("left", ($(window).width()-width)/2+$(window).scrollLeft()).
    css("top", ($(window).height()-height)/2+$(window).scrollTop()).
    css("width",width).
    css("height",height);
};




var openPayWindow = function () {
    $(".selfControl").hide();
    $("#pay").show();
    $("#binaryImage").hide();
    $("#paySuccess").hide();
    $("#inputMoney").show();
};

var showResetPwd = function () {
    $(".selfControl").hide();
    $("#resetPassword").show();
};

var showAddShipping =  function () {
    $(".selfControl").hide();
    $("#shippingAdd").show();
};

var hideBookDetailPop = function () {
    $("#bookDetailPopWindow").hide();
};

var hideShippingInfoPop = function () {
    $("#shippingInfoPop").hide();
};

var deliverNoPopWindowHide = function () {
    $("#deliverNoPopWindow").hide();
};

var hideShippingListInfo = function () {
    $("#shippingListPopWindow").hide();
};

var hideAdviceDetailPopWindow = function () {
    $("#adviceDetailPopWindow").hide();
};


var resetPwd = function () {
    var passwordNew = $("#passwordNew").val();
    var passwordAgain = $("#passwordAgain").val();
    var passwordOld = $("#passwordNow").val();
    if(passwordAgain != passwordNew){
        alert("重复输入新密码不相同，请重新输入");
        return false;
    }
    var jsonData = "passwordOld=" + passwordOld + "&passwordNew=" + passwordAgain;
    $.post('/book/user/reset_password.do',jsonData,function (flag) {
        if(flag.status == 1){
            alert(flag.msg);
        }else {
            alert(flag.msg);
        }
    });

};









$(function() {
    var Accordion = function(el, multiple) {
        this.el = el || {};
        this.multiple = multiple || false;

        // Variables privadas
        var links = this.el.find('.link');
        // Evento
        links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown)
    }

    Accordion.prototype.dropdown = function(e) {
        var $el = e.data.el;
        $this = $(this),
            $next = $this.next();

        $next.slideToggle();
        $this.parent().toggleClass('open');

        if (!e.data.multiple) {
            $el.find('.submenu').not($next).slideUp().parent().removeClass('open');
        };
    }

    var accordion = new Accordion($('#accordion'), false);
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