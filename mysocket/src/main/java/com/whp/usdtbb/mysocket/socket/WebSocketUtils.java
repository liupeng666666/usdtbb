package com.whp.usdtbb.mysocket.socket;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Repository;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author : 张吉伟
 * @data : 2019/1/3 1:10
 * @descrpition :
 */
@Repository
public class WebSocketUtils implements WebSocketHandler {
    private static final Map<String, WebSocketSession> users;


    static {
        users = new HashMap<>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) {
        try {
            String id = webSocketSession.getUri().toString().split("ID=")[1];
            if (id != null) {
                users.put(id, webSocketSession);
                JSONObject json = new JSONObject();
                json.put("code", 200);
                webSocketSession.sendMessage(new TextMessage(json.toString()));

            } else {
                JSONObject json = new JSONObject();
                json.put("code", 301);
                webSocketSession.sendMessage(new TextMessage(json.toString()));

            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject json = new JSONObject();
            json.put("code", 301);
            try {
                webSocketSession.sendMessage(new TextMessage(json.toString()));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        JSONObject json = new JSONObject();
        try {


        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 301);
            webSocketSession.sendMessage(new TextMessage(json.toString()));
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if (webSocketSession.isOpen()) {
            webSocketSession.close();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        System.out.println("连接已关闭：" + closeStatus);
        users.remove(getClientId(webSocketSession));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private Integer getClientId(WebSocketSession session) {
        try {
            Integer clientId = (Integer) session.getAttributes().get("WEBSOCKET_USERID");
            return clientId;
        } catch (Exception e) {
            return null;
        }
    }

    public static void SocketXunHun(JSONObject json) {
        Set<String> clientIds = users.keySet();
        WebSocketSession session = null;
        TextMessage message = new TextMessage(json.getString("message"));
        for (String clientId : clientIds) {
            try {

                session = users.get(clientId);
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
