$(function() {
    // Basic
    $('.dropify').dropify();

    // Translated
    $('.dropify-fr').dropify({
        messages: {
            'default': '点击或拖拽文件到这里',
            'replace': '点击或拖拽文件到这里来替换文件',
            'remove':  '移除文件',
            'error':   '对不起，你上传的文件太大了'
        }
    });

    // Used events
    var drEvent = $('.dropify-event').dropify();

    drEvent.on('dropify.beforeClear', function(event, element){
        return confirm("Do you really want to delete \"" + element.filename + "\" ?");
    });

    drEvent.on('dropify.afterClear', function(event, element){
        alert('File deleted');
    });

    $.post("/jar/list",{},function(result){
        fileListParser(result);
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
});