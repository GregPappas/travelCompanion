function splashTransition(){
window.setTimeout("redirect()",2000)}

function redirect(){
$.get( "home_page.html", function( data ) {
  $("#content_body").html( data );
});

$.get("footer.html", function(data)
{
$("#footer").html(data);
})
}



function showMap(){
    $('#map-canvas').fadeIn(2000);
};
