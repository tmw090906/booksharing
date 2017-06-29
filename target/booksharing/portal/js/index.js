/**
 * Created by tmw090906 on 2017/6/25.
 */
var vm = new Vue({
    el: '#mainContainer',
    data: {
        books:[],
        isFirstPage: true,
        isLastPage: false,
        lastPage:1 ,
        pageNum : 1,
        textSearch : "",
        categoryId : 0,
        bookDetail:null,
        exchangeOnes: [
        //     {
        //     userId: 43,
        //     userName: "tmw2",
        //     exchangeUserId: 23,
        //     exchangeBookId: 82,
        //     exchangeBookName : "琼瑶全集48:水云间",
        //     bookId: 90,
        //     bookName: "张晓风散文集",
        //     bookAuthor: "张晓风"
        // }
        ],
        exchanges:[
            {userId: 23, userName: "tmw", exchangeUserId: 23, bookId: 86, bookName: "十一处特工皇妃", bookAuthor: "潇湘冬儿"}],
        selfHadBooks:[]
    },
    mounted: function () {
        _this = this;
        $.post('/book/info/get_book_list.do',null,function (flag) {
            if(flag.status == 1){
                _this.books = flag.date.list;
                _this.isLastPage = flag.date.isLastPage;
                _this.isFirstPage = flag.date.isFirstPage;
                _this.lastPage = flag.date.lastPage;
                _this.pageNum =  flag.date.pageNum;
                $(".selfControl");
                $("#pageList").show();
            }else {
                alert(flag.msg);
            }
        })
    },
    methods:{
        searchButton : function (textSearch) {
            this.textSearch = textSearch;
            this.pageNum = 1;
            this.getBookList();
        },
        pageChoice : function (pageNum) {
            this.pageNum = pageNum;
            this.getBookList();
        },
        categoryChoice : function (categoryId) {
            this.categoryId = categoryId;
            this.textSearch = "";
            this.pageNum = 1;
            this.getBookList();
        },
        getBookList : function () {
            var jsonData = "pageNum=" + this.pageNum;
            if(this.textSearch != ""){
                jsonData = jsonData + "&textSearch=" + this.textSearch;
            }
            if(this.categoryId != 0){
                jsonData = jsonData + "&categoryId=" + this.categoryId;
            }
            _this = this;
            $.post('/book/info/get_book_list.do',jsonData,function (flag) {
                if(flag.status == 1){
                    _this.books = flag.date.list;
                    _this.isLastPage = flag.date.isLastPage;
                    _this.isFirstPage = flag.date.isFirstPage;
                    _this.lastPage = flag.date.lastPage;
                    _this.pageNum =  flag.date.pageNum;
                    $(".selfControl");
                    $("#pageList").show();
                }else {
                    alert(flag.msg);
                }
            })
        },
        getBookDetail : function (bookId) {
            var jsonData = "bookId=" + bookId;
            _this = this;
            $.post('/book/info/get_book_detail.do',jsonData,function (flag) {
                if(flag.status == 1){
                    _this.bookDetail = flag.date
                    $(".selfControl");
                    $("#bookDetail").show();
                }else {
                    alert(flag.msg);
                }
            })
        },
        commitExchangeInfo : function (bookId,exchangeBookId,userId) {
            var bool = confirm("确认提交置换申请？");
            if(bool == false){
                return false;
            }
            var jsonData = "bookId=" + bookId + "&exchangeBookId=" + exchangeBookId + "&userId=" + userId;
            $.post('/book/apply/commit.do', jsonData, function (flag) {
                if (flag.status == 1) {
                    alert(flag.msg);
                } else if(flag.status == 10) {
                    alert("请登录");
                }else {
                    alert(flag.msg);
                }
            });
        },
        getExchangeOneList : function (bookId) {
            var jsonData = "bookId=" + bookId;
            _this = this;
            $.post('/book/info/exchange_one_info.do', jsonData, function (flag) {
                if (flag.status == 1) {
                    _this.exchangeOnes = flag.date;
                    $(".selfControl");
                    $("#exchangeOneList").show();
                } else if(flag.status == 10) {
                    alert("请登录");
                }else {
                    alert(flag.msg);
                }
            });
        },
        getExchangeList : function (bookId) {
            var jsonData = "bookId=" + bookId;
            _this = this;
            $.post('/book/info/exchange_info.do', jsonData, function (flag) {
                if (flag.status == 1) {
                    _this.exchanges = flag.date;
                    $(".selfControl");
                    $("#exchangeList").show();
                } else if(flag.status == 10) {
                    alert("请登录");
                }else {
                    alert(flag.msg);
                }
            });
        },
        showSelfBook : function (bookId,userId,bookName,userName) {
            var userName = "<span><h5>交换用户：" +  userName + "</h5></span>";
            var bookName = "<span><h5>交换图书：" +  bookName + "</h5></span>";
            $("#bookMsg").empty();
            $("#bookMsg").append(userName);
            $("#bookMsg").append(bookName);
            $('#exchangeAllBookId').val(bookId);
            $('#userId').val(userId);
            _this = this;
            $.post('/book/info/self_list.do', null, function (flag) {
                if (flag.status == 1) {
                    _this.selfHadBooks = flag.date;
                    centerSelfBook(550,450);
                    $("#popSelfBookList").show();
                } else if(flag.status == 10) {
                    alert("请登录");
                }else {
                    alert(flag.msg);
                }
            });

        }
    }
});

var getChoice = function (categoryId) {
    vm.categoryChoice(categoryId);
};

$(document).ready(function()
{
    var customerId = sessionStorage.customerId;
    if(customerId != undefined){
        $("#noLogin").show();
        $("#login").hide();
    }else {
        $("#login").show();
        $("#noLogin").hide();
    }
    $.post('/book/category/get_tree_category.do', null, function (flag) {
        if (flag.status == 1) {
            addTree(flag.date);
        } else {
            alert(flag.msg);
        }
    });
});

var center = function(width,height)
{
    return $("#popWindow").css("left", ($(window).width()-width)/2+$(window).scrollLeft()).
    css("top", ($(window).height()-height)/2+$(window).scrollTop()).
    css("width",width).
    css("height",height);
};

var centerSelfBook = function (width,height) {
    return $("#popSelfBookList").css("left", ($(window).width()-width)/2+$(window).scrollLeft()).
    css("top", ($(window).height()-height)/2+$(window).scrollTop()).
    css("width",width).
    css("height",height);
};
var showPopWindow = function () {
    center(350,250);
    $("#popWindow").show();
};


$("#addCancel").click(function () {
    $("#popWindow").hide();
});


$("#addSelfBook").click(function () {
    var status = $('#selfBookStatus').val();
    var bookId = $('#bookId').val();
    var jsonData = "bookId=" + bookId + "&status=" + status;
    $.post('/book/library/add.do', jsonData, function (flag) {
        if (flag.status == 1) {
            alert("添加成功");
            $("#popWindow").hide();
        } else if(flag.status == 10) {
            alert("请登录");
        }else {
            alert(flag.msg);
        }
    });
});

var hideSelfList = function () {
    $("#popSelfBookList").hide();
};

$("#choiceBook").click(function () {
    var bookId = $('#exchangeAllBookId').val();
    var userId = $('#userId').val();
    var exchangeBookId = $('#selfBookList').val();
    var jsonData = "bookId=" + bookId + "&userId=" + userId + "&exchangeBookId=" + exchangeBookId;
    $.post('/book/apply/commit.do', jsonData, function (flag) {
        if (flag.status == 1) {
            alert(flag.msg);
            $("#popSelfBookList").hide();
        } else if(flag.status == 10) {
            alert("请登录");
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

var showAdviceForm = function () {
    $(".selfControl").hide();
    $("#adviceAddBook").show();
};

var addBookAdvice = function () {
    var bool = confirm("确认提交添加图书申请？");
    if(bool == false){
        return false;
    }
    var jsonData = $("#addBookAdviceForm").serialize();
    $.post('/book/info/advice.do', jsonData, function (flag) {
        if (flag.status == 1) {
            alert("成功提交申请");
        } else if(flag.status == 10) {
            alert("请登录");
        }else {
            alert(flag.msg);
        }
    });
    
};
