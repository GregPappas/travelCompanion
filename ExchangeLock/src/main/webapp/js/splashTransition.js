function splashTransition(){
    window.setTimeout("redirect()",2000)
}

function redirect(){
    $.get( "home_page.html", function( data ) {
      $("#content_body").html( data );
    });

    $.get("welcome.html", function(data){
        $("#welcome").html(data);
        getMap();
    });

    $.get("safetynet.html", function(data)
    {
        $("#safetynet").html(data);
    })

    $.get("footer.html", function(data)
    {
        $("#footer").html(data);
    })
}

function getMap(){
$.get("map.html", function(data){
            $("#map").html(data);
        })
}

function showSafetyNet() {
    $("#safetynet").fadeIn(2000);
}

function showCurrencyLock() {

}

function showMap(){
    console.log("map initialised");
    $('#click_me').fadeOut(500);
    $('.map_canvas_container').fadeIn(500);
    initialize();
    $('#safetynet').fadeOut(500);
    $('#title_container').fadeOut(500);
}

function loadMap2(){
$.get("map2.html", function(data){
    $('#map').html(data);
});
}

function loadMap3()
{
$.get('map3.html', function(data){
    $('#map').html(data);
});
}

