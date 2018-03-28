$(function() {
    var req = function () {
        $.post("/job/list",{},function(response){
            taskListParser(response);
        });
    };

    req();

    starter=setInterval(req, 1000);

    $('#c1_body tr td button.delete').live("click",function(){
        var data = new FormData();
        data.append("id",$(this).parent().parent()[0].id);
        $.ajax({
            url: "/job/delete",
            type: "POST",
            processData: false,
            contentType: false,
            async: false,
            data: data,
            success: function(result) {
                taskListParser(result);
            }
        });

    });

    $('#c1_body tr td button.stop').live("click",function(){
        var data = new FormData();
        data.append("id",$(this).parent().parent()[0].id);
        $.ajax({
            url: "/job/stop",
            type: "POST",
            processData: false,
            contentType: false,
            async: false,
            data: data,
            success: function(result) {
                taskListParser(result);
            }
        });

    });

    $('#c1_body tr td button.restart').live("click",function(){
        var data = new FormData();
        data.append("id",$(this).parent().parent()[0].id);
        $.ajax({
            url: "/job/restart",
            type: "POST",
            processData: false,
            contentType: false,
            async: false,
            data: data,
            success: function(result) {
                taskListParser(result);
            }
        });
    });

    $('#c1_body tr td button.command').live("click",function(){
        var data = new FormData();
        data.append("id",$(this).parent().parent()[0].id);
        $.ajax({
            url: "/job/find",
            type: "POST",
            processData: false,
            contentType: false,
            async: false,
            data: data,
            success: function(result) {
                if(result.state === "success"){
                    $('#info_modal_title').text(result.data.name+"的配置：");
                    var _html = "";
                    for(var k in result.data){//遍历json数组时，这么写p为索引，0,1
                        _html+="<tr><td>"+k+"</td><td>"+result.data[k]+"</td></tr>"
                    }
                    $('#c1_config_body').html(_html);
                    //$('#info_modal_body').html(_html);
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
                html += "<td><a href='http://hnode2:8088/cluster/app/"+data[i].app_id+"' target='_blank'>"+data[i].app_id+"</a></td>";
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