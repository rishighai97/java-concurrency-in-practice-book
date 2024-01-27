package org.example;


import org.example.dto.Message;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static java.text.MessageFormat.format;
import static java.util.Objects.isNull;

@ServerEndpoint(value = "/web-socket/{userName}")
public class WebSocket {

    // stores single session per user. The latest client registered on server would receive messages
    private static Map<String, Session> userNameToSessionMap = new HashMap<>();

    private static final Logger log = Logger.getLogger(WebSocket.class.getName());

    @OnOpen
    public void onOpen(Session session, @PathParam("userName") String userName) {
        System.out.println(format("Open Connection for session id {0} and userName {1}", session.getId(), userName));
        userNameToSessionMap.put(userName, session);
    }

    @OnMessage
    public void onTextMessage(Session session, String msg) throws IOException {
        final Message message = Message.generate(msg);
        final String messageRecipient = message.getToUserName();
        System.out.println(format("Session id {0}. Message to be sent to user - {1}", session.getId(), messageRecipient));
        Session recipientUserSession = userNameToSessionMap.get(messageRecipient);
        if (isNull(recipientUserSession)) throw new IllegalStateException(format("Recipient user {0} is unavailable", messageRecipient));
        recipientUserSession.getAsyncRemote().sendText(message.getMessage());
    }

    @OnMessage
    public void onBinaryMessage(Session session, ByteBuffer msg) {
        System.out.println("On Message for Web Socket");
    }

    @OnMessage
    public void onPongMessage(Session session, PongMessage pMsg) {
        System.out.println("On Message for Web Socket");
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        final String userName = userNameToSessionMap
                .entrySet()
                .stream()
                .filter(entry->entry.getValue().getId().equals(session.getId()))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElseThrow();
        System.out.println(format("Close Connection for session id {0} and userName {1}", session.getId(), userName));
        userNameToSessionMap.remove(userName);
    }

    @OnError
    public void onError(Throwable e) {
        e.printStackTrace();
    }
}
