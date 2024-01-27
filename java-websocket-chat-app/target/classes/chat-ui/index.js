var chatMessages = ["abc", "def", "ghi", "ghi", "ghi", "ghi", "ghi", "ghi", "ghi", "ghi", "ghi", "ghi", "ghi", "ghi", "ghi", "ghi", "ghi", "ghi", "ghi", "ghi"]
var username = null
var recipientUsername = null

function onPageLoad() {
    setUserName();
    listenToChatSettingsButtonClickEvent();
    createWebSocket();
    let chatWindowHtml = ``;
    chatMessages.forEach(chatMessage => {
      chatWindowHtml+= `<br />`+chatMessage
    });
    document.getElementById("chatWindow").innerHTML = chatWindowHtml;
}

function createWebSocket() {
    var webSocket = new WebSocket("ws://localhost:8085/web-socket/"+sessionStorage.getItem('username'));
    sessionStorage.setItem("webSocket", webSocket);
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
            }
        });
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




<!--            let ws = new WebSocket("ws://localhost:8085/web-socket/user1");-->

<!--            ws.onopen = function(){-->
<!--              //Subscribe to the channel-->
<!--              ws.send(JSON.stringify({"fromUserName":"user1","toUserName":"user2","message":"hi"}))-->
<!--            }-->

<!--            ws.onmessage = function(msg) {-->
<!--                console.log(msg.data);-->
<!--            }-->