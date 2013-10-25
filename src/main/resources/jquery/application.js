$(function () {
    "use strict";

    var header = $('#header');
    var content = $('#content');
    var input = $('#input');
    var status = $('#status');
    var myName = false;
    var author = null;
    var logged = false;
    var socket = $.atmosphere;
    var subSocket;
    var transport = 'websocket';

    var request = { url: document.location.protocol + "//" + document.location.host + '/northpole',
        contentType: "application/json",
        logLevel: 'debug',
        transport: transport,
        trackMessageLength: true,
        enableProtocol: true,
        fallbackTransport: 'long-polling'
    };

    request.onOpen = function (response) {
        input.removeAttr('disabled').focus();
        status.text('Choose name:');
        transport = response.transport;
    };

    request.onTransportFailure = function (errorMsg, request) {
        if (window.EventSource) {
            request.fallbackTransport = "sse";
        }
    };

    request.onMessage = function (response) {

        var message = response.responseBody;
        try {
            var json = jQuery.parseJSON(message);
        } catch (e) {
            console.log('This doesn\'t look like a valid JSON: ', message.data);
            return;
        }

        addMessage(json.who, json.message);
    };

    request.onClose = function (response) {
        logged = false;
    };

    request.onError = function (response) {
        content.html($('<p>', { text: 'Sorry, but there\'s some problem with your '
            + 'socket or the server is down' }));
    };

    subSocket = socket.subscribe(request);

    function addMessage(who, message) {
        $('#'+who+'Msg').text(message);
    }
});
