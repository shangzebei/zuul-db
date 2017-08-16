package com.shang.zuul.service;

import com.google.gson.Gson;
import com.shang.zuul.domain.Message;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shangzebei on 2017/7/4.
 */
@Service
@Log4j
public class MessageWebsocket extends TextWebSocketHandler {
    private List<WebSocketSession> _session = new ArrayList<>();

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        log.info(message.getPayload());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this._session.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        _session.remove(session);
    }

    public void sendMessage(Message message) {
        try {
//            log.info(_session);
            _session.forEach(webSocketSession -> {
                try {
                    webSocketSession.sendMessage(new TextMessage(new Gson().toJson(message).getBytes()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
