function splashTransition(){
window.setTimeout("redirect()",2000)}

function redirect(){
$.get( "home_page.html", function( data ) {
  $("#content_body").html( data );
});


$.get("map.html", function(data){
    $("#map").html(data);
})

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
    console.log("map initialised");
    $('#click_me').fadeOut(2000);
    $('.map_canvas_container').fadeIn(2000);
    initialize();
    $('#currency_lock').fadeOut(2000);
    $('#safetynet').fadeOut(2000);
};

