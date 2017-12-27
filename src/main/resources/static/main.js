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
                if(result.state === "success"){
                    $('#alertDiv').css("display","block");
                    $('#alertP').text('上传成功');
                }
                fileListParser(result);
            }
        });
    });

    $('#alertBtn').click(function(){
        $('#alertDiv').css("display","none");
    });

    // 第一次请求
    $('#c3_menu').click(function(){
        $.post("/jar/list",{},function(result){
            fileListParser(result);
        });
    });

    $('#c3_body tr td button.delete').live("click",function(){
        var data = new FormData();
        data.append("jar",$(this).parent().parent().children()[0].innerText);
        $.ajax({
            url: "/jar/delete",
            type: "POST",
            processData: false,
            contentType: false,
            data: data,
            success: function(result) {
                if(result.state === "success"){
                    $('#alertDiv').css("display","block");
                    $('#alertP').text('删除成功');
                }
                fileListParser(result);
            }
        });

    });

    //刷新表格数据
    function fileListParser(response){
        if(response.state === "success"){
            html = "";
            var data = response.data;
            for (var i=0; i< data.length; i++) {
                html += "<tr><td>"+data[i]+"</td>";
                html += "<td>"+1+"</td>";
                html += "<td>"+1+"</td>";
                html += "<td><button type='button' class='btn btn-default delete'><span class='glyphicon glyphicon-remove'></span> 删除</button></td></tr>";
            }
            $('#c3_body').empty();
            $('#c3_body').html(html);
        }else{
            alert(response.data);
        }

    }

    $('#c1_menu').click(function(){
        $.post("/task/list",{},function(response){
            taskListParser(response);
        });
    });

    $('#c1_body tr td button.delete').live("click",function(){
        var data = new FormData();
        data.append("id",$(this).parent().parent()[0].id);
        $.ajax({
            url: "/task/delete",
            type: "POST",
            processData: false,
            contentType: false,
            data: data,
            success: function(result) {
                if(result.state === "success"){
                    $('#alertDiv').css("display","block");
                    $('#alertP').text('删除任务成功');
                }
                taskListParser(result);
            }
        });

    });


    function taskListParser(response){
        if(response.state === "success"){
            html = "";
            var data = response.data;
            for (var i=0; i< data.length; i++) {
                html += "<tr id='"+data[i].id+"'><td><button type='button' class='btn btn-default delete'>删除</button></td>";
                html += "<td>"+data[i].name+"</td>";
                html += "<td>"+data[i].command+"</td>";
                /*html += "<td>"+data[i].create_time+"</td>";*/
                html += "<td><a href='"+data[i].web_url+"'>"+data[i].application_id+"</a></td>";
                html += "<td>"+data[i].state+"</td>";
                /*html += "<td>"+data[i].modify_time+"</td>";*/
                html += "</tr>";
            }
            $('#c1_body').empty();
            $('#c1_body').html(html);
        }else{
            alert(response.data);
        }
    }


    // 提交任务
    $('#c2SubmitBtn').click(function(){
        var data = new FormData();
        data.append("name", $('#c2NameInput').val());
        data.append("jar",  $('#c2JarInput').val());
        data.append("clazz",$('#c2ClassInput').val());
        data.append("args", $('#c2ArgsInput').val());
        data.append("email",$('#c2EmailInput').val());
        data.append("is_send_email",$('input.send-email:radio:checked').val());

        $.ajax({
            url: "/task/start",
            type: "POST",
            processData: false,
            contentType: false,
            data: data,
            success: function(result) {
                if(result.state === "success"){
                    $('#alertDiv').css("display","block");
                    $('#alertP').text('任务提交成功');
                }
                //taskListParser(result);
            }
        });
    });
});