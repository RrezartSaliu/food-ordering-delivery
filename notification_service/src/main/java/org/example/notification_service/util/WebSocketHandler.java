package org.example.notification_service.util;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String userId = getUserIdFromSession(session);
        sessions.put(userId, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String userId = getUserIdFromSession(session);
        sessions.remove(userId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        // Optional: handle messages from client if needed
    }

    public void sendToUser(String userId, String message) throws IOException {
        WebSocketSession session = sessions.get(userId);
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(message));
        }
    }

    private String getUserIdFromSession(WebSocketSession session) {
        // Example: extract userId from query params: ws://host/ws?userId=123
        String query = session.getUri().getQuery();
        return query.split("=")[1];
    }
}
