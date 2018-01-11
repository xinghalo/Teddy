$(function() {
    var req = function () {
        $.post("/task/list",{},function(response){
            taskListParser(response);
        });
    };

    req();

    starter=setInterval(req, 2500);

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
                    taskListParser(result);
                }
                taskListParser(result);
            }
        });

    });

    $('#c1_body tr td button.stop').live("click",function(){
        var data = new FormData();
        data.append("id",$(this).parent().parent()[0].id);
        $.ajax({
            url: "/task/stop",
            type: "POST",
            processData: false,
            contentType: false,
            data: data,
            success: function(result) {
                if(result.state === "success"){
                    $('#alertDiv').css("display","block");
                    $('#alertP').text('停止任务成功');

                }
                taskListParser(result);
            }
        });

    });

    $('#c1_body tr td button.restart').live("click",function(){
        var data = new FormData();
        data.append("id",$(this).parent().parent()[0].id);
        $.ajax({
            url: "/task/restart",
            type: "POST",
            processData: false,
            contentType: false,
            data: data,
            success: function(result) {
                if(result.state === "success"){
                    $('#alertDiv').css("display","block");
                    $('#alertP').text('重启任务成功');
                    taskListParser(result);
                }
                taskListParser(result);
            }
        });
    });

    $('#c1_body tr td button.command').live("click",function(){
        var data = new FormData();
        data.append("id",$(this).parent().parent()[0].id);
        $.ajax({
            url: "/task/find",
            type: "POST",
            processData: false,
            contentType: false,
            data: data,
            success: function(result) {
                if(result.state === "success"){
                    $('#info_modal_title').text(result.data.name+"的配置：");
                    $('#info_modal_body').text(result.data.command);
                    $('#info_modal').modal('show');
                }
            }
        });
    });


    function taskListParser(response){
        if(response.state === "success"){
            html = "";
            var data = response.data;
            for (var i=0; i< data.length; i++) {
                html += "<tr id='"+data[i].id+"' "+taskStyle(data[i].state)+">";
                html += "<td>"+data[i].name+"</td>";
                html += "<td><button type='button' class='btn btn-default command'>查看配置</button></td>";
                html += "<td><a href='"+data[i].web_url+"' target='_blank'>"+data[i].application_id+"</a></td>";
                html += "<td>"+data[i].state+"</td>";
                html += "<td><button type='button' class='btn btn-default delete'>删除</button></td>";
                html += "<td><button type='button' class='btn btn-default stop'>停止</button></td>";
                html += "<td><button type='button' class='btn btn-default restart'>重启</button></td>";
                html += "</tr>";
            }
            $('#c1_body').empty();
            $('#c1_body').html(html);
        }else{
            alert(response.data);
        }
    }

    function taskStyle(state){
        if(state === "RUNNING"){
            return " class='success' ";
        }else{
            return "class='warning'";
        }
    }
});