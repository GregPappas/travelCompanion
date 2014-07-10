function splashTransition(){
window.setTimeout("redirect()",2000)}

function redirect(){
$.get( "index2.html", function( data ) {
  $("#content_body").html( data );
});
return}
function showMap(){
    $('#map-canvas').fadeIn(2000);
};