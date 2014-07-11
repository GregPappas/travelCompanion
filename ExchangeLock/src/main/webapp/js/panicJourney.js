function panicJourney() {
    $("#welcome").fadeOut(500);
    $("#safetynet").fadeOut(500);

    $.get("panicjourney.html", function(data){
            $("#panicjourney").html(data);
    });
    $("#panicjourney").fadeIn(500);


}

function hidePanicJourney() {
    $("#panicjourney").fadeOut(500);
    $("#lostcardjourney").fadeOut(500);
    $("#blockedcardjourney").fadeOut(500);
    $("#endlostcardjourney").fadeOut(500);
}

function lostDebitCard() {
    $("#fraudButton").fadeOut(500);
    $("#debitCardButton").fadeOut(500);
    $("#somethingElseButton").fadeOut(500);

    $.get("lostcardjourney.html", function(data){
            $("#lostcardjourney").html(data);
    });
    $("#lostcardjourney").fadeIn(500);
}

function yesBlockMyCard() {
    $.get("/rest-bank/changeState/checkflag/toggleflag/69003");

    $("#yesDebitCardButton").fadeOut(500);
    $("#noDebitCardButton").fadeOut(500);

    $.get("blockedcardjourney.html", function(data){
            $("#blockedcardjourney").html(data);
    });
    $("#blockedcardjourney").fadeIn(500);
}

function yesMyCardIsStolen() {
    $("#yesBlockedCardButton").fadeOut(500);
    $("#noBlockedCardButton").fadeOut(500);

    $.get("endlostcardjourney.html", function(data){
            $("#endlostcardjourney").html(data);
    });
    $("#endlostcardjourney").fadeIn(500);
}