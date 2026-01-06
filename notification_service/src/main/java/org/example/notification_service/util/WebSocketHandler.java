package org.example.notification_service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.notification_service.FeignClient.OrderClient;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;
    private final OrderClient orderClient;

    private void handleDriverLocation(Map<String, Object> payload) throws IOException {
        Long orderId = ((Number) payload.get("orderId")).longValue();
        Double lat = ((Number) payload.get("lat")).doubleValue();
        Double lng = ((Number) payload.get("lng")).doubleValue();

        System.out.println("Driver location for order " + orderId + ": " + lat + ", " + lng);

        // Lookup the userId for this order
        String userId = String.valueOf(orderClient.getUserId(orderId));

        if (userId != null) {
            sendToUser(userId, payload);  // forward to the user
        }
    }

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

    public void sendToUser(String userId, Object messageObject) throws IOException {
        WebSocketSession session = sessions.get(userId);
        if (session != null && session.isOpen()) {
            String json = objectMapper.writeValueAsString(messageObject);
            session.sendMessage(new TextMessage(json));
        }
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // Parse JSON from frontend
        Map<String, Object> payload = objectMapper.readValue(message.getPayload(), Map.class);

        String type = (String) payload.get("type");

        if ("DRIVER_LOCATION".equals(type)) {
            handleDriverLocation(payload);
        }

        // You can handle other message types here later
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
