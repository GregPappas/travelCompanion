function getName()
{
    $.post("/rest-bank/session",{username:69003,password:69003}, function(data){
        $.get("/rest-bank/customer", function(data2){
            $("#userName").html("&nbsp;&nbsp;"+data2.firstname);
        });
     });
}