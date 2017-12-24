$(function() {
    // 菜单页面跳转
    $('#sidemenu li').live("click",function(){
        $('section.content').css("display","none");
        $('section.content').filter('#'+$(this).attr("tag")).css("display","block");
    });

    // todo ....
    $('#file_upload').uploadify({
        'formData'     : {
            'timestamp' : '123'
        },
        'swf'      : 'js/uploadify/uploadify.swf',
        'uploader' : 'js/uploadify/uploadify.php'
    });

    $("#uploadify").uploadify({
        //指定swf文件
        'swf': 'js/uploadify/uploadify.swf',
        //后台处理的页面
        'uploader': 'jar/upload',
        //按钮显示的文字
        'buttonText': '上传图片',
        //显示的高度和宽度，默认 height 30；width 120
        //'height': 15,
        //'width': 80,
        //上传文件的类型  默认为所有文件    'All Files'  ;  '*.*'
        //在浏览窗口底部的文件类型下拉菜单中显示的文本
        'fileTypeDesc': 'Image Files',
        //允许上传的文件后缀
        'fileTypeExts': '*.gif; *.jpg; *.png',
        //发送给后台的其他参数通过formData指定
        //'formData': { 'someKey': 'someValue', 'someOtherKey': 1 },
        //上传文件页面中，你想要用来作为文件队列的元素的id, 默认为false  自动生成,  不带#
        //'queueID': 'fileQueue',
        //选择文件后自动上传
        'auto': true,
        //设置为true将允许多文件上传
        'multi': true
    });

    /*$('#file_upload').uploadify({
        'buttonText': '浏  览',
        'auto': true,
        'swf'      : '/js/uploadify/uploadify.swf',
        'uploader' : '/js/uploadify/uploadify.php',
        'onQueueComplete': function (event, data) {    //所有队列完成后事件
            //ShowUpFiles(guid, type, show_div);
            alert("上传完毕！");
        },
        'onUploadError': function (event, queueId, fileObj, errorObj) {
            alert(errorObj.type + "：" + errorObj.info);
        }
    });*/

    $('#c3_menu').click(function(){
        $.post("/jar/list",{},function(result){
            html = "";
            for (var i=0; i< result.length; i++) {
                html += "<tr><td>"+result[i].name+"</td>";
                html += "<td>"+result[i].size+"</td>";
                html += "<td>"+result[i].createTime+"</td>";
                html += "<td><button type='button' class='btn btn-default'><span class='glyphicon glyphicon-remove'></span> 删除</button></td></tr>";
            }
            $('#c3_body').empty();
            $('#c3_body').html(html);
        });
    })
});