const stompClient = new StompJs.Client({
    brokerURL: 'ws://' + window.location.hostname + ':8282/api/v2/ws-connect'
});
//update above to take headers while connect & pass claims

const API_URL = `${window.location.hostname}:8282/api/v2/simulate/currentUser`;

function fetchCurrentUser() {
    try {
        const xhr = new XMLHttpRequest();
        xhr.open('GET', `http://${API_URL}`, false);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.send();

        if (xhr.status !== 200) {
            throw new Error(`HTTP error! Status: ${xhr.status}`);
        }
        return xhr.responseText;

    } catch (error) {
        console.error('Error fetching current user:', error);
    }
}

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected and listening: ' + frame);
    console.info("Subscribing " + '/topic/message')
    stompClient.subscribe('/topic/message', (dashboard) => {
        showGreeting(dashboard);
    });

    const user = fetchCurrentUser();

    console.info("Subscribing " + '/topic/user/'+user+'/message')
    stompClient.subscribe('/topic/user/'+user+'/message', (dashboard) => {
        showGreeting(dashboard);
    });

    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    if (urlParams.get('TEST_INVALID_DESTINATION')) {
        const destination = '/topic/user/TmsAdmin222/message';
        console.info("Client is subscribing destination not accessible " + destination)
        stompClient.subscribe(destination, (dashboard) => {
            showGreeting(dashboard);
        });
    }
    console.log('Connected and listening error topic: ' + frame);
    stompClient.subscribe('/topic/error', (dashboard) => {

        console.error(dashboard.body);
        $("#greetings").append("<tr bgcolor='#b22222' ><td>" + dashboard.body + "</td></tr>");
    });

};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.publish({
        destination: "/app/message",
        body: JSON.stringify({'event': $("#name").val(), 'newData': 'CLIENT CONSTANT'})

    });
}

function showGreeting(message) {
    if (document.getElementById("greetings").rows.length > 10) {
        $("#greetings").html(document.getElementById("greetings").rows[9]);
    }
    $("#greetings").append("<tr><td>" + message.body + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());
    $("#send").click(() => sendName());
});

document.onreadystatechange
