/**
 * Created by tmw090906 on 2017/6/29.
 */
var vm = new Vue({
    el : "#app",
    data : {
        isFirstPage: true,
        isLastPage: false,
        lastPage:1 ,
        pageNum : 1,
        textSearch : "",
        bookList : [],
        trueName : "test",
        categoryList : [],
        category : {},
        parentCategory : {},
        bookDetail : {},
        categoryList : [],
        AdviceList : [],
        adviceDetail : {},
        selfAdviceList : [],
        complanStatus : {},
        complanList : [],
        managedComplanList : [],
        orderNo : "",
        orderList : []
    },
    mounted : function () {
        $(".selfControl").hide();
        this.pageNum = 1;
        var jsonData = "pageNum=" + this.pageNum;
        _this = this;
        $.post('/book/manage/info/get_book_list.do',jsonData,function (flag) {
            if(flag.status == 1){
                _this.bookList = flag.date.list;
                _this.isLastPage = flag.date.isLastPage;
                _this.isFirstPage = flag.date.isFirstPage;
                _this.lastPage = flag.date.lastPage;
                _this.pageNum =  flag.date.pageNum;
                _this.trueName = sessionStorage.trueName;
                $("#bookList").show();
            }else if(flag.status == 10){
                alert("未登录，即将返回登录页");
                window.location.href = "login.html";
            }else {
                alert(flag.msg);
            }
        });
    },
    methods : {
        getCategoryList : function (categoryId) {
            $(".selfControl").hide();
            var jsonData = "categoryId=" + categoryId;
            _this = this;
            $.post('/book/manage/category/get_category.do',jsonData,function (flag) {
                if(flag.status == 1){
                    _this.categoryList = flag.date.list;
                    _this.category = flag.date.category;
                    _this.parentCategory = flag.date.parentCategory;
                    $("#category").show();
                }else if(flag.status == 10){
                    alert("未登录，即将返回登录页");
                    //  window.location.href = "login.html";
                }else {
                    alert(flag.msg);
                }
            });
        },
        addCategory : function (parentId) {
            $("#parentId").val(parentId);
            addCategoryPopWindowCenter(350,250);
            $("#addCategoryPopWindow").show()
        },
        updateCategory : function (categoryId) {
            $("#categoryId").val(categoryId);
            updateCategoryPopWindowCenter(350,250);
            $("#updateCategoryPopWindow").show()
        },
        deleteCategory : function (categoryId,parentId) {
            var bool = confirm("确认删除该分类？删除后该分类下所有书籍分类都将改变为该分类的父分类");
            if(bool == false){
                return false;
            }
            var jsonData = "categoryId=" + categoryId;
            $.post('/book/manage/category/delete.do',jsonData,function (flag) {
                if(flag.status == 1){
                    alert("删除分类成功");
                    vm.getCategoryList(parentId);
                }else if(flag.status == 10){
                    alert("未登录，即将返回登录页");
                    //  window.location.href = "login.html";
                }else {
                    alert(flag.msg);
                }
            });
        },
        getBookList : function () {
            $(".selfControl").hide();
            var jsonData = "pageNum=" + this.pageNum;
            if(this.textSearch != null && this.textSearch != "" && this.textSearch != undefined){
                jsonData = jsonData + "&textSearch=" + this.textSearch;
            }
            _this = this;
            $.post('/book/manage/info/get_book_list.do',jsonData,function (flag) {
                if(flag.status == 1){
                    _this.bookList = flag.date.list;
                    _this.isLastPage = flag.date.isLastPage;
                    _this.isFirstPage = flag.date.isFirstPage;
                    _this.lastPage = flag.date.lastPage;
                    _this.pageNum =  flag.date.pageNum;
                    $(".selfControl").hide();
                    $("#bookList").show();
                }else if(flag.status == 10){
                    alert("未登录，即将返回登录页");
                    //  window.location.href = "login.html";
                }else {
                    alert(flag.msg);
                }
            });
        },
        firstGetBookList : function () {
            this.pageNum = 1;
            this.textSearch = "";
            this.getBookList();
        },
        bookListPageChoice : function (pageNum) {
            this.pageNum  = pageNum;
            this.getBookList();
        },
        textSearchBookList : function (textSearch) {
          this.pageNum = 1;
          this.textSearch = textSearch;
          this.getBookList();
        },
        getBookDetail : function (bookId) {
            var jsonData = "bookId=" + bookId;
            _this = this;
            $.post('/book/manage/info/detail.do',jsonData,function (flag) {
                if(flag.status == 1){
                    saveBookInfoPopWindowCenter(900,600);
                    __this = _this;
                    $.post('/book/manage/category/all_list.do',null,function (flag) {
                        if(flag.status == 1){
                            __this.categoryList = flag.date;
                        }
                    });
                    $("#categoryIdSelector").val(flag.date.categoryId);
                    _this.bookDetail = flag.date;
                    $("#saveBookInfoPopWindow").show();
                }else if(flag.status == 10){
                    alert("未登录，即将返回登录页");
                    //  window.location.href = "login.html";
                }else {
                    alert(flag.msg);
                }
            });
        },
        firstGetAdviceList : function () {
            this.pageNum = 1;
            this.getAdviceList();
        },
        getAdviceList : function () {
            $(".selfControl").hide();
            var jsonData = "pageNum=" + this.pageNum;
            _this = this;
            $.post('/book/manage/info/advice_list.do',jsonData,function (flag) {
                if(flag.status == 1){
                    _this.adviceList = flag.date.list;
                    _this.isLastPage = flag.date.isLastPage;
                    _this.isFirstPage = flag.date.isFirstPage;
                    _this.lastPage = flag.date.lastPage;
                    _this.pageNum =  flag.date.pageNum;
                    $("#adviceList").show();
                }else if(flag == 10){
                    alert("登陆后才能查看，请登录");
                } else {
                    alert(flag.msg);
                }
            });
        },
        adviceListPageChoice : function () {
            this.pageNum = pageNum;
            this.getAdviceList();
        },
        getAdviceDetail : function (adviceId) {
            var jsonData = "adviceId=" + adviceId;
            _this = this;
            $.post('/book/manage/info/advice_detail.do',jsonData,function (flag) {
                if(flag.status == 1){
                    _this.adviceDetail = flag.date;
                    adviceDetailPopWindowCenter(600,300);
                    $("#adviceDetailPopWindow").show();
                }else if(flag == 10){
                    alert("登陆后才能查看，请登录");
                } else {
                    alert(flag.msg);
                }
            });
        },
        firstGetSelfAdviceList : function () {
            this.pageNum = 1;
            this.getSelfAdviceList();
        },
        getSelfAdviceList : function () {
            $(".selfControl").hide();
            var jsonData = "pageNum=" + this.pageNum;
            _this = this;
            $.post('/book/manage/info/self_advice_list.do',jsonData,function (flag) {
                if(flag.status == 1){
                    _this.selfAdviceList = flag.date.list;
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
        showManageAdvice : function (adviceId) {
            $("#adviceId").val(adviceId);
            manageAdvicePopWindowCenter(350,250);
            $("#manageAdvicePopWindow").show();
        },
        firstGetComplanList : function () {
            this.pageNum = 1 ;
            this.complanStatus = 1;
            this.getComplanList();
        },
        getComplanList : function () {
            var status = this.complanStatus;
            var pageNum = this.pageNum;
            var jsonData = "status=" + status + "&pageNum=" + pageNum;
            _this = this;
            $(".selfControl").hide();
            $.post('/book/manage/complan/list.do',jsonData,function (flag) {
                if(flag.status == 1){
                    _this.complanList = flag.date.list;
                    _this.isLastPage = flag.date.isLastPage;
                    _this.isFirstPage = flag.date.isFirstPage;
                    _this.lastPage = flag.date.lastPage;
                    _this.pageNum =  flag.date.pageNum;
                    $("#complanList").show();
                }else if(flag == 10){
                    alert("登陆后才能查看，请登录");
                } else {
                    alert(flag.msg);
                }
            });
            
        },
        complanListPageChoice : function (pageNum) {
            this.pageNum = pageNum;
            this.getComplanList();
        },
        firstGetManagedComplanList : function () {
            this.pageNum = 1 ;
            this.complanStatus = 2;
            this.getManagedComplanList();
        },
        getManagedComplanList : function () {
            var status = this.complanStatus;
            var pageNum = this.pageNum;
            var jsonData = "status=" + status + "&pageNum=" + pageNum;
            _this = this;
            $(".selfControl").hide();
            $.post('/book/manage/complan/list.do',jsonData,function (flag) {
                if(flag.status == 1){
                    _this.managedComplanList = flag.date.list;
                    _this.isLastPage = flag.date.isLastPage;
                    _this.isFirstPage = flag.date.isFirstPage;
                    _this.lastPage = flag.date.lastPage;
                    _this.pageNum =  flag.date.pageNum;
                    $("#managedComplanList").show();
                }else if(flag == 10){
                    alert("登陆后才能查看，请登录");
                } else {
                    alert(flag.msg);
                }
            });
        },
        managedComplanListPageChoice : function (pageNum) {
            this.pageNum = pageNum;
            this.getManagedComplanList();
        },
        showManageComplanPopWindow : function (complanId) {
            $("#complanId").val(complanId);
            manageComplanPopWindowCenter(350,250);
            $("#manageComplanPopWindow").show();
        },
        firstGetOrderList : function () {
            this.pageNum = 1;
            this.getOrderList();
        },
        getOrderList : function () {
            var jsonData = "pageNum=" + this.pageNum;
            _this = this;
            $(".selfControl").hide();
            $.post('/book/manage/order/list.do',jsonData,function (flag) {
                if(flag.status == 1){
                    _this.orderList = flag.date.list;
                    _this.isLastPage = flag.date.isLastPage;
                    _this.isFirstPage = flag.date.isFirstPage;
                    _this.lastPage = flag.date.lastPage;
                    _this.pageNum =  flag.date.pageNum;
                    $("#orderList").show();
                }else if(flag == 10){
                    alert("登陆后才能查看，请登录");
                } else {
                    alert(flag.msg);
                }
            });
            
        },
        orderListPageChoice : function (pageNum) {
            this.pageNum = pageNum;
            this.getOrderList();
        },
        orderNoSearch : function (orderNo) {
            var jsonData = "orderNo=" + orderNo;
            $.post('/book/manage/order/search.do',jsonData,function (flag) {
                if(flag.status == 1){
                    _this.orderList = flag.date.list;
                    _this.isLastPage = flag.date.isLastPage;
                    _this.isFirstPage = flag.date.isFirstPage;
                    _this.lastPage = flag.date.lastPage;
                    _this.pageNum =  flag.date.pageNum;
                    $("#orderList").show();
                }else if(flag == 10){
                    alert("登陆后才能查看，请登录");
                } else {
                    alert(flag.msg);
                }
            });
        }
    }

});


var setTrueName = function (trueName) {
    vm.trueName =  trueName;
};

var logout =  function () {
    $.post('/book/user/logout.do', null, function (flag) {
        if (flag.status == 1) {
            alert("成功注销");
            sessionStorage.clear();
            window.location.href = "../portal/index.html";
        } else if(flag.status == 10) {
            alert("请登录");
        }else {
            alert(flag.msg);
        }
    });
};


//show
var addBook = function () {
    $.post('/book/manage/category/all_list.do',null,function (flag) {
        if(flag.status == 1){
            vm.categoryList = flag.date;
        }
    });
    vm.bookDetail = {};
    saveBookInfoPopWindowCenter(900,600);
    $("#saveBookInfoPopWindow").show();
};


//center
var addCategoryPopWindowCenter = function(width,height)
{
    return $("#addCategoryPopWindow").css("left", ($(window).width()-width)/2+$(window).scrollLeft()).
    css("top", ($(window).height()-height)/2+$(window).scrollTop()).
    css("width",width).
    css("height",height);
};
var updateCategoryPopWindowCenter = function (width,height) {
    return $("#updateCategoryPopWindow").css("left", ($(window).width()-width)/2+$(window).scrollLeft()).
    css("top", ($(window).height()-height)/2+$(window).scrollTop()).
    css("width",width).
    css("height",height);
};

var saveBookInfoPopWindowCenter = function (width,height) {
    return $("#saveBookInfoPopWindow").css("left", ($(window).width()-width)/2+$(window).scrollLeft()).
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
var manageAdvicePopWindowCenter = function (width,height) {
    return $("#manageAdvicePopWindow").css("left", ($(window).width()-width)/2+$(window).scrollLeft()).
    css("top", ($(window).height()-height)/2+$(window).scrollTop()).
    css("width",width).
    css("height",height);
};

var manageComplanPopWindowCenter = function (width,height) {
    return $("#manageComplanPopWindow").css("left", ($(window).width()-width)/2+$(window).scrollLeft()).
    css("top", ($(window).height()-height)/2+$(window).scrollTop()).
    css("width",width).
    css("height",height);
};


//hide
var hideAddCategoryPopWindow = function () {
    $("#addCategoryPopWindow").hide();
};
var hideUpdateCategoryPopWindow = function () {
    $("#updateCategoryPopWindow").hide();
};
var hideSaveBookDetailPopWindow = function () {
    $("#saveBookInfoPopWindow").hide();
};
var hideAdviceDetailPopWindow = function () {
    $("#adviceDetailPopWindow").hide();
};
var hideManageAdvicePopWindow = function () {
    $("#manageAdvicePopWindow").hide();
};
var hideManageComplanPopWindow = function () {
    $("#manageComplanPopWindow").hide();
};

//confirm
var confirmAddCategory = function () {
    var categoryName = $("#categoryName").val();
    var parentId = $("#parentId").val();
    var jsonData = "categoryName=" + categoryName + "&parentId=" + parentId;
    $.post('/book/manage/category/add_category.do', jsonData, function (flag) {
        if (flag.status == 1) {
            alert("添加子节点成功");
            hideAddCategoryPopWindow();
            vm.getCategoryList(parentId);
        } else if(flag.status == 10) {
            alert("请登录");
        }else {
            alert(flag.msg);
        }
    });
};

var confirmUpdateCategory = function () {
    var categoryName = $("#newCategoryName").val();
    var categoryId = $("#categoryId").val();
    var jsonData = "categoryName=" + categoryName + "&categoryId=" + categoryId;
    $.post('/book/manage/category/set_category_name.do', jsonData, function (flag) {
        if (flag.status == 1) {
            alert("修改分类名称成功");
            hideUpdateCategoryPopWindow();
            vm.getCategoryList(categoryId);
        } else if(flag.status == 10) {
            alert("请登录");
        }else {
            alert(flag.msg);
        }
    });

};

var confirmSaveBookDetail = function () {
    var jsonData = $("#saveBookDetailForm").serialize();
    var bookId = $("#bookId").val();
    if(bookId != "" && bookId != undefined && bookId != null){
        jsonData = jsonData + "&bookId=" + bookId;
    }
    $.post('/book/manage/info/save.do', jsonData, function (flag) {
        if (flag.status == 1) {
            alert("图书保存成功");
            $("#saveBookInfoPopWindow").hide();
            vm.firstGetBookList();
        } else if(flag.status == 10) {
            alert("请登录");
        }else {
            alert(flag.msg);
        }
    });
};

var confirmManageAdvice = function () {
    var adviceId = $("#adviceId").val();
    var status = $("#adviceStatus").val();
    var jsonData = "adviceId=" + adviceId + "&status=" + status;
    $.post('/book/manage/info/manage_advice.do', jsonData, function (flag) {
        if (flag.status == 1) {
            alert("成功处理该申请");
            hideManageAdvicePopWindow();
            vm.firstGetAdviceList();
        } else if(flag.status == 10) {
            alert("请登录");
        }else {
            alert(flag.msg);
        }
    });
};

var confirmManageComplan = function () {
    var complanId = $("#complanId").val();
    var status = $("#complanStatus").val();
    var jsonData = "complanId=" + complanId + "&status=" + status;
    $.post('/book/manage/complan/manage.do', jsonData, function (flag) {
        if (flag.status == 1) {
            alert("成功处理该申请");
            hideManageComplanPopWindow();
            vm.firstGetComplanList();
        } else if(flag.status == 10) {
            alert("请登录");
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


var imageChange=function(event){
    var files = event.target.files, file;
    if (files && files.length > 0) {
        // 获取目前上传的文件
        file = files[0];// 文件大小校验的动作
        if(file.size > 1024 * 1024 * 2) {
            alert('图片大小不能超过 2MB!');
            return false;
        }
        // 获取 window 的 URL 工具
        var URL = window.URL || window.webkitURL;
        // 通过 file 生成目标 url
        var imgURL = URL.createObjectURL(file);
        $.ajaxFileUpload({
            url:'/book/manage/info/upload.do',
            securityuri: false,
            fileElementId:'file',
            dataType:'json',
            success:function(flag){
                if(flag.status == 1){
                    $("#bookImage").val(flag.date.uri);
                    $("#img").attr("src",imgURL);
                }else {
                    alert(flag.msg);
                }
            },
            error: function (data, status) {
                alert('error');
            }
        })
    }
};

