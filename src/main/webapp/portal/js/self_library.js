/**
 * Created by tmw090906 on 2017/6/28.
 */
var vm = new Vue({
    el: "#mainContainer",
    data: {
        isFirstPage: true,
        isLastPage: false,
        lastPage: 1,
        pageNum: 1,
        status : 0,
        selfBookList: [],
        bookDetail : {}
    },
    mounted : function () {
        _this = this;
        $.post('/book/library/list.do',null,function (flag) {
            if(flag.status == 1){
                _this.selfBookList = flag.date.list;
                _this.isLastPage = flag.date.isLastPage;
                _this.isFirstPage = flag.date.isFirstPage;
                _this.lastPage = flag.date.lastPage;
                _this.pageNum =  flag.date.pageNum;
            }else {
                alert(flag.msg);
            }
        })
    },
    methods : {
        pageChoice : function (pageNum) {
            this.pageNum = pageNum;
            this.getSelfBookList();
        },
        getSelfBookList :function (status) {
            this.status = status;
            _this = this;
            var jsonData = "pageNum=" + this.pageNum;
            if(status != 0){
                jsonData = jsonData + "&status=" + status;
            }
            $.post('/book/library/list.do',jsonData,function (flag) {
                if(flag.status == 1){
                    _this.selfBookList = flag.date.list;
                    _this.isLastPage = flag.date.isLastPage;
                    _this.isFirstPage = flag.date.isFirstPage;
                    _this.lastPage = flag.date.lastPage;
                    _this.pageNum =  flag.date.pageNum;
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
                    _this.bookDetail = flag.date;
                    bookDetailCenter(900,650);
                    $("#bookDetailPopWindow").show();
                }else if(flag.status == 10){
                    alert("请登录！");
                }else {
                    alert(flag.msg);
                }
            })
        },
        getStatusList : function (id) {
            $("#id").val(id);
            bookStatusPopWindowCenter(350,250);
            $("#updateSelfBookStatusPopWindow").show();
        }
    }

});

var hideBookDetailPop = function () {
    $("#bookDetailPopWindow").hide();
};

var bookDetailCenter = function(width,height)
{
    return $("#bookDetailPopWindow").css("left", ($(window).width()-width)/2+$(window).scrollLeft()).
    css("top", ($(window).height()-height)/2+$(window).scrollTop()).
    css("width",width).
    css("height",height);
};

var hideBookStatusPopWindow = function () {
    $("#updateSelfBookStatusPopWindow").hide();
};

var bookStatusPopWindowCenter = function(width,height)
{
    return $("#updateSelfBookStatusPopWindow").css("left", ($(window).width()-width)/2+$(window).scrollLeft()).
    css("top", ($(window).height()-height)/2+$(window).scrollTop()).
    css("width",width).
    css("height",height);
};

var saveSelfBookStatus = function () {
    var status = $("#selfBookStatus").val();
    var id = $("#id").val();
    var jsonData = "status=" + status + "&id=" + id;
    $.post('/book/library/update_status.do',jsonData,function (flag) {
        if(flag.status == 1){
            alert("修改成功");
            $("#updateSelfBookStatusPopWindow").hide();
            vm.getSelfBookList(0);
        }else if(flag.status == 10){
            alert("请登录！");
        }else {
            alert(flag.msg);
        }
    })
};

var locationHref = function () {
    var textSearch = $("#textSearch").val();
    window.location.href = "index.html?textSearch=" + encodeURIComponent(textSearch);
};