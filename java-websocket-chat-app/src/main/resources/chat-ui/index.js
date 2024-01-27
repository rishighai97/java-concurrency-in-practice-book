var chatMessages = []
var username = null
var recipientUsername = null
var webSocket = null

function onPageLoad() {
    setUserName();
    listenToChatSettingsButtonClickEvent();
    createWebSocket();
    listenToChatReceptionEvent();
    listenToSendTextMessageButtonClickEvent();
}

function createWebSocket() {
    webSocket = new WebSocket("ws://localhost:8085/web-socket/"+username);
}

function listenToChatSettingsButtonClickEvent() {
    document.getElementById("settingsSubmit").addEventListener("click", function(){
            var newRecipientUsername = document.getElementById("recipientUsername").value;
            if (newRecipientUsername === username) {
                alert("Username and Recipient Username cannot be same");
            } else {
                recipientUsername = newRecipientUsername
                console.log("Recipient's Username: "+recipientUsername);
                document.getElementById("chatWindowRecipientUsername").innerHTML = "Recipient: "+recipientUsername;
                document.getElementById("chatWindow").innerHTML = ""
                document.getElementById("sendText").value = ""
                document.getElementById("recipientUsername").innerHTML = ""
                chatMessages = []
            }
        });
}

function listenToChatReceptionEvent() {
    webSocket.onmessage = (msg) => {
        chatMessages.push(recipientUsername+" : "+msg.data);
        updateChatWindow();
    }
}

function listenToSendTextMessageButtonClickEvent() {
    document.getElementById("sendTextSubmit").addEventListener("click", function(){
                if (recipientUsername === null) {
                    alert("Please enter recipient username");
                } else {
                    let msg = document.getElementById("sendText").value;
                    webSocket.send(JSON.stringify({"fromUserName":username,"toUserName":recipientUsername,"message":document.getElementById("sendText").value}))
                    chatMessages.push(username+" : "+msg);
                    document.getElementById("sendText").value = '';
                    updateChatWindow();
                }
            });
}

function updateChatWindow() {
    let chatWindowHtml = ``;
    chatMessages.forEach(chatMessage => {
      chatWindowHtml+= `<br />`+chatMessage
    });
    document.getElementById("chatWindow").innerHTML = chatWindowHtml;
}

function setUserName() {
    if (username === null) {
        username = prompt("Please enter your username");
    }
    if (username != null) {
        document.getElementById("navbarUsername").innerHTML = "Username: "+username;
//        sessionStorage.setItem('username', username);
    } else {
        document.getElementById("noUser").innerHTML = "Welcome, Stranger!";
    }
}