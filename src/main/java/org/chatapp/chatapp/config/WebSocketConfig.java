package org.chatapp.chatapp.config;

import lombok.RequiredArgsConstructor;
import org.chatapp.chatapp.common.websocket.SocketTextHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final SocketTextHandler socketTextHandler;
    private final HandshakeInterceptor ChattingHandshakeInterceptor;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketTextHandler,"/chat/rooms/*")
                .addInterceptors(ChattingHandshakeInterceptor)
                .setAllowedOrigins("*");
    }
}
