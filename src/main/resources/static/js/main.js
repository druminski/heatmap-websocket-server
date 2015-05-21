var stompClient = null;
var heat = null

function connect() {
    var socket = new SockJS('/hello');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function(greeting){
            heat.setLatLngs(latLngs(JSON.parse(greeting.body)));
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function random() {
    return Math.floor((Math.random() * 5000) + 1)
}

function httpGet(url) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", url, false );
    xmlHttp.send( null );
    return xmlHttp.responseText;
}

function latLngs(json) {
    var latLngs = []
    for (var i=0; i < json.length; i++) {
        var element = json[i]
        latLngs.push([parseFloat(element.lat), parseFloat(element.lng), element.count * 1000])
    }

    return latLngs
}

window.onload = function(e){
    disconnect()

    var map = L.map('map').setView([52.240, 19.360], 6);

    L.tileLayer('http://otile2.mqcdn.com/tiles/1.0.0/osm/{z}/{x}/{y}.png', {
        attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a></a>',
        maxZoom: 18
    }).addTo(map)

    heat = L.heatLayer([]).addTo(map);

    connect()

}
