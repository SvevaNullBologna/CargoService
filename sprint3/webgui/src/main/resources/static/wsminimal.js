var socket = connect();

function connect(){
  var host     = document.location.host;
  var pathname =  document.location.pathname;
  var addr     = "ws://" +host + pathname + "socket"  ;

  // Assicura che sia aperta un unica connessione
  if(socket!==undefined && socket.readyState!==WebSocket.CLOSED){
    alert("WARNING: Connessione WebSocket già stabilita");
  }
  socket = new WebSocket(addr); //CONNESSIONE

  socket.onopen = function (event) {
    addMessageToWindow("Connected");
  };
  socket.onmessage = function (event) { //RICEZIONE
    addMessageToWindow(""+`${event.data}`);
  };
  return socket;
}//connect

const messageWindow   = document.getElementById("messageArea");
const messageInput    = document.getElementById("inputmessage");
const sendButton      = document.getElementById("sendMessage");

sendButton.onclick = function (event) {
    sendMessage(messageInput.value);
    messageInput.value = "";
}
function sendMessage(message) {
    var jsonMsg = JSON.stringify( {'name': message});
    socket.send(message);
}
function addMessageToWindow(message) {
    messageWindow.innerHTML += message  + "\n"
}