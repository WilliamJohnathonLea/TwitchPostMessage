$(document).ready(function(){

    var eventSource = new EventSource("/messages")

    eventSource.onmessage = function(e) {
        console.log(e)
    };

});
