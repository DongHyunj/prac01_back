package com.example.demo.config;

import com.example.demo.config.interceptor.JwtHandshakeInterceptor;
import com.example.demo.config.websocket.WebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final WebSocketHandler webSocketHandler;
    private final JwtHandshakeInterceptor jwtHandshakeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/ws")
                // 웹 소켓에 보안이 따로 있기 때문에 Spring security에서 허용해주는 코드는 다 해놔도 이걸 설정해줘야 한다.
                .setAllowedOrigins("*")
                .addInterceptors(jwtHandshakeInterceptor);
    }
}
