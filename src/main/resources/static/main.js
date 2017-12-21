$.ajaxSetup({
    async : false
});

$(function(){

    var req = function(){
        $.post("/page/rec",{memberId:$('#memberId').val(),start:$('#start').val(),end:$('#end').val()},function(result){
            html = "";
            for(var i=0; i<result.length; i++){
                html += "<tr>";
                $.post("http://item.51tiangou.com/front/listing/itemDetailInfo",{id:result[i]},function (r) {
                    if(r.code === 0){
                        html += "<td>"+result[i]+"</td>";
                        html += "<td><img src='https://img1.tg-img.com/"+r.data.itemImageList[0].url+"' style='height:100px'></td>";
                        html += "<td>"+r.data.productInfo.productName +"</td>";
                    }
                });
                html += "</tr>";
            }

            $('#data').empty();
            $('#data').html(html);
        });
    };

    //开启定时器
    $('#queryBtn').click(function(){
        $('#queryBtn').attr('disabled',"true");
        $('#stopBtn').removeAttr("disabled");
        starter=setInterval(req, 4000);
    });

    //清除定时器
    $('#stopBtn').click(function(){
        $('#queryBtn').removeAttr("disabled");
        $('#stopBtn').attr('disabled',"true");
        clearInterval(starter);
    });
});