package com.shang.zuul.service;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Created by shangzebei on 2017/7/4.
 */
@Service
@Log4j
public class SpeedWebsocket extends TextWebSocketHandler {
    private WebSocketSession _session;
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
       log.info(message.getPayload());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this._session = session;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        _session = null;
    }

    public WebSocketSession get_session() {
        return _session;
    }

}
