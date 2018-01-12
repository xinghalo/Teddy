$(function() {
    // 提交任务
    $('#c2SubmitBtn').click(function(){
        var data = new FormData();
        data.append("name", $('#c2NameInput').val());
        data.append("jar",  $('#c2JarInput').val());
        data.append("clazz",$('#c2ClassInput').val());
        data.append("args", $('#c2ArgsInput').val());
        data.append("email",$('#c2EmailInput').val());
        data.append("send",$('input.send-email:radio:checked').val());
        data.append("restart",$('input.restart:radio:checked').val());

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
            }
        });
    });
});