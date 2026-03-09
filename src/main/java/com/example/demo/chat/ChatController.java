package com.example.demo.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ChatController {

    // WebSocketConfig 클래스에서 setApplicationDestinationPrefixes로 설정해둔 /app 뒤에 /test로 메시지를 보내면 해당 메서드를 실행해주겠다.
    // /app/test
    @MessageMapping("/test")
    // WebSocketConfig 클래스에서 enableSimpleBroker 설정해둔 /topic 뒤에 /test라는 토픽을 구독한 사용자들에게 return 값을 보내주는 기능
    // test라는 토픽을 구독한 클라이언트들에게 메시지를 전송
    @SendTo("/topic/test")
    public String test() {
        System.out.println("test");
        return "zzzz";
    }

    // 최종 형태
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/{roomIdx}")
    public void sendChatMessage(@DestinationVariable Long roomIdx, String message) {
        simpMessagingTemplate.convertAndSend("/topic/" + roomIdx, message);
    }

    @MessageMapping("/webrtc")
    @SendTo("/topic/webrtc")
    public Map<String, Object> webrtc(Map<String, Object> message) {
        System.out.println("webrtc");
        System.out.println(message.get("type"));
        return message;
    }
}
