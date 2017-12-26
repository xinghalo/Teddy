$(function() {
    $.post("/task/list",{},function(response){
        taskListParser(response);
    });

    // 菜单页面跳转
    $('#sidemenu li').live("click",function(){
        $('section.content').css("display","none");
        $('section.content').filter('#'+$(this).attr("tag")).css("display","block");
    });

    // 上传文件
    $('#uploadFileBtn').on('click', function() {
        var fd = new FormData();
        fd.append("file", $("#inputfile").get(0).files[0]);
        $.ajax({
            url: "/jar/upload",
            type: "POST",
            processData: false,
            contentType: false,
            data: fd,
            success: function(result) {
                fileListParser(result);
            }
        });
    });

    // 第一次请求
    $('#c3_menu').click(function(){
        $.post("/jar/list",{},function(result){
            fileListParser(result);
        });
    })

    //刷新表格数据
    function fileListParser(result){
        html = "";
        for (var i=0; i< result.length; i++) {
            html += "<tr><td>"+result[i].name+"</td>";
            html += "<td>"+result[i].size+"</td>";
            html += "<td>"+result[i].createTime+"</td>";
            html += "<td><button type='button' class='btn btn-default'><span class='glyphicon glyphicon-remove'></span> 删除</button></td></tr>";
        }
        $('#c3_body').empty();
        $('#c3_body').html(html);
    }

    $('#c1_menu').click(function(){
        $.post("/task/list",{},function(response){
            taskListParser(response);
        });
    })

    function taskListParser(response){
        if(response.state === "success"){
            html = "";
            var data = response.data;
            for (var i=0; i< data.length; i++) {
                html += "<tr><td>"+data[i].id+"</td>";
                html += "<td>"+data[i].name+"</td>";
                html += "<td>"+data[i].command+"</td>";
                html += "<td>"+data[i].create_time+"</td>";
                html += "<td>"+data[i].application_id+"</td>";
                html += "<td>"+data[i].state+"</td>";
                html += "<td>"+data[i].web_url+"</td>";
                html += "<td>"+data[i].modify_time+"</td>";
                html += "<td><button type='button' class='btn btn-default'><span class='glyphicon glyphicon-remove'></span> 停止</button></td></tr>";
            }
            $('#c1_body').empty();
            $('#c1_body').html(html);
        }else{

            alert(response.data);
        }
    }
});