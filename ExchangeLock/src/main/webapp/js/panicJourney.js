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
    $("#cardorderedjourney").fadeOut(500);
    $("#moneyaddedjourney").fadeOut(500);
    $("#replacementcardjourney").fadeOut(500);
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
    $("#yesDebitCardButton").fadeOut(500);
    $("#noDebitCardButton").fadeOut(500);
    $.get("/rest-bank/changeState/checkflag/toggleflag/69003");

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


    $.get("replacementcardjourney.html", function(data){
        $("#replacementcardjourney").html(data);
    });
    $("#replacementcardjourney").fadeIn(500);
}

function orderCard(){
    $("#17").fadeOut(500);
    $("#16").fadeOut(500);
    $("#18").fadeOut(500);
    $("#later").fadeOut(500);
    $("#callPolice").fadeOut(500);
    $("#viewOnMaps").fadeOut(500);


    $.get("cardorderedjourney.html", function(data){
            $("#cardorderedjourney").html(data);
    });
    $("#cardorderedjourney").fadeIn(500);
}
function addFunds(){
    $("#havemoney").fadeOut(500);
    $("#donthavecard").fadeOut(500);
    $("#addfundstocard").fadeOut(500);

    $.get("moneyaddedjourney.html", function(data){
        $("#moneyaddedjourney").html(data);
    });
    $("#moneyaddedjourney").fadeIn(500);

}

