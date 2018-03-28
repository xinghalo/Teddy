$(function() {
    // 提交任务
    $('#c2SubmitBtn').click(function(){

        var config = "spark.driver.memory=" + $('#c2DriverMemory').val() +
            ";spark.executor.memory="+ $('#c2ExecutorMemory').val() +
            ";spark.executor.cores="+ $('#c2ExecutorCores').val() +
            ";spark.executor.instances="+ $('#c2ExecutorNums').val();

        var data = {};
        data["name"]            = $('#c2NameInput').val();
        data["app_resource"]    = $('#c2JarInput').val();
        data["main_class"]      = $('#c2ClassInput').val();
        data["config"]          = config;
        data["args"]            = $('#c2ArgsInput').val();
        data["email"]           = $('#c2EmailInput').val();
        data["send"]            = $('input.send-email:radio:checked').val();
        data["restart"]         = $('input.restart:radio:checked').val();

        data = JSON.stringify(data);

        $.ajax({
            url: "/job/submit",
            type: "POST",
            //processData: false,
            contentType: "application/json",
            async: false,
            data: data,
            success: function(result) {
                alert(result.data);
            }
        });
    });
});