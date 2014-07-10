function splashTransition(){
window.setTimeout("redirect()",2000)}

function redirect(){
$.get( "home_page.html", function( data ) {
  $("#content_body").html( data );
});

$.get("safetynet.html", function(data)
{
    $("#safetynet").html(data);
})
$.get("currency_lock.html", function(data){
    $("#currency_lock").html(data);
})

$.get("footer.html", function(data)
{
    $("#footer").html(data);
})

}



function showMap(){
    $('#map-canvas').fadeIn(2000);
    initialize();
};

