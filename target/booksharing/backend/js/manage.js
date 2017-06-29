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
        parentCategory : {}
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
            if(this.textSearch != null && this.textSearch != ""){
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
        }
    }

});

$("#login_btn").click(
    function () {
        $.post('/book/manage/user/login.do',$("#login").serialize(),function (flag) {
            if(flag.status == 1){
                var customerId = flag.date.userId;
                vm.trueName = flag.date.trueName;
                sessionStorage.customerId = customerId;
                window.location.href = "manage.html";
            }else {
                alert(flag.msg);
            }
    });
});

var logout =  function () {
    $.post('/book/user/logout.do', null, function (flag) {
        if (flag.status == 1) {
            alert("成功注销");
            sessionStorage.clear();
            window.location.href = "index.html";
        } else if(flag.status == 10) {
            alert("请登录");
        }else {
            alert(flag.msg);
        }
    });
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

//hide
var hideAddCategoryPopWindow = function () {
    $("#addCategoryPopWindow").hide();
};
var hideUpdateCategoryPopWindow = function () {
    $("#updateCategoryPopWindow").hide();
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