function panicJourney() {
    $("#welcome").fadeOut(2000);
    $("#currency_lock").fadeOut(2000);
    $("#safetynet").fadeOut(2000);

    $.get("panicjourney.html", function(data){
            $("#panicjourney").html(data);
    });
    $("#panicjourney").fadeIn(2000);


}

function hidePanicJourney() {
    $("#panicjourney").fadeOut(2000);
    $("#lostcardjourney").fadeOut(2000);
    $("#blockedcardjourney").fadeOut(2000);
    $("#endlostcardjourney").fadeOut(2000);
}

function lostDebitCard() {
    $("#fraudButton").fadeOut(2000);
    $("#debitCardButton").fadeOut(2000);
    $("#somethingElseButton").fadeOut(2000);

    $.get("lostcardjourney.html", function(data){
            $("#lostcardjourney").html(data);
    });
    $("#lostcardjourney").fadeIn(2000);
}

function yesBlockMyCard() {
    $("#yesDebitCardButton").fadeOut(2000);
    $("#noDebitCardButton").fadeOut(2000);

    $.get("blockedcardjourney.html", function(data){
            $("#blockedcardjourney").html(data);
    });
    $("#blockedcardjourney").fadeIn(2000);
}

function yesMyCardIsStolen() {
    $("#yesBlockedCardButton").fadeOut(2000);
    $("#noBlockedCardButton").fadeOut(2000);

    $.get("endlostcardjourney.html", function(data){
            $("#endlostcardjourney").html(data);
    });
    $("#endlostcardjourney").fadeIn(2000);
}