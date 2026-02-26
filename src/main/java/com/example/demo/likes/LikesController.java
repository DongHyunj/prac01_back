package com.example.demo.likes;

import com.example.demo.common.model.BaseResponse;
import com.example.demo.likes.model.LikesDto;
import com.example.demo.user.model.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("likes")
@RestController
@RequiredArgsConstructor
public class LikesController {
    private final LikesService likesService;

    @PostMapping("/reg/{boardIdx}")
    public ResponseEntity reg(@AuthenticationPrincipal AuthUserDetails user, @PathVariable Long boardIdx) {
        LikesDto.RegRes result = likesService.reg(user.getIdx(), boardIdx);
        return ResponseEntity.ok(BaseResponse.success(result));
    }
}
