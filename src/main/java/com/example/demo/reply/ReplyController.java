package com.example.demo.reply;


import com.example.demo.reply.model.ReplyDto;
import com.example.demo.user.model.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/reply")
@RequiredArgsConstructor
@RestController
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("/reg{boardIdx}")
    public ResponseEntity reg(@AuthenticationPrincipal AuthUserDetails user, @PathVariable Long boardIdx, @RequestBody ReplyDto.RegReq dto) {
        replyService.reg(dto);

        return ResponseEntity.ok("댓글 작성 성공");
    }
}
