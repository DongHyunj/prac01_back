package com.example.demo.reply;


import com.example.demo.reply.model.ReplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/reply")
@RequiredArgsConstructor
@RestController
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("/reg")
    public ResponseEntity reg(@RequestBody ReplyDto.RegReq dto) {
        replyService.reg(dto);

        return ResponseEntity.ok("댓글 작성 성공");
    }
}
