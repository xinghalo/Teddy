$(function() {
    // 菜单页面跳转
    $('#sidemenu li').live("click",function(){
        $('section.content').css("display","none");
        $('section.content').filter('#'+$(this).attr("tag")).css("display","block");
    });

    // todo ....
});