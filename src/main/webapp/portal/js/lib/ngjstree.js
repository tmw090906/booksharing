/**
 * Created by tmw090906 on 2017/6/24.
 */
/*递归实现获取无级树数据并生成DOM结构*/
var str = "";
var forTree = function(o){
    for(var i=0;i < o.length;i++){
        var urlstr = "";
        try{
            if(typeof o[i]["url"] == "undefined"){
                urlstr = "<div><span>"+o[i]["userLevel"]+ o[i]["name"] +"</span><ul>";
            }else{
                var url = "getChoice(" + o[i]["categoryId"] + ")";
                urlstr = "<div><span>"+o[i]["userLevel"]+"<a href="+ o[i]["url"] + url +">"+ o[i]["name"] +"</a></span><ul>";
            }
            str += urlstr;
            if(o[i]["list"] != null){
                forTree(o[i]["list"]);
            }
            str += "</ul></div>";
        }catch(e){}
    }
    return str;
}
/*添加无级树*/
var addTree = function(json) {
    document.getElementById("menuTree").innerHTML = forTree(json);
    menuTree();
}


/*树形菜单*/
var menuTree = function(){
    //给有子对象的元素加
    $("#menuTree ul").each(function(index, element) {
        var ulContent = $(element).html();
        var spanContent = $(element).siblings("span").html();
        if(ulContent){
            $(element).siblings("span").html(spanContent)
        }
    });

    $("#menuTree").find("div span").click(function(){
        var ul = $(this).siblings("ul");
        var spanStr = $(this).html();
        var spanContent = spanStr.substr(3,spanStr.length);
        if(ul.find("div").html() != null){
            if(ul.css("display") == "none"){
                ul.show(300);
                // $(this).html("[-]" + spanContent);
            }else{
                ul.hide(300);
                // $(this).html("[+] " + spanContent);
            }
        }
    })
};

/*树形列表展开*/
$("#btn_open").click(function(){
    $("#menuTree ul").show(300);
    curzt("-");
})

/*收缩*/
$("#btn_close").click(function(){
    $("#menuTree ul").hide(300);
    curzt("+");
})

function curzt(v){
    $("#menuTree span").each(function(index, element) {
        var ul = $(this).siblings("ul");
        var spanStr = $(this).html();
        var spanContent = spanStr.substr(3,spanStr.length);
        if(ul.find("div").html() != null){
            $(this).html("["+ v +"] " + spanContent);
        }
    });
}