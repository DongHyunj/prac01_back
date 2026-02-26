package com.example.demo.relation.controller;

import com.example.demo.common.model.BaseResponse;
import com.example.demo.relation.model.BDto;
import com.example.demo.relation.service.BService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/b")
@RequiredArgsConstructor
@RestController
public class BController {
    private final BService bService;

    @PostMapping("/reg/{aIdx}")
    public ResponseEntity reg(@PathVariable Long aIdx, @RequestBody BDto.BReq dto) {
        BDto.BRes result =  bService.req(aIdx, dto);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

    @GetMapping("/read/{idx}")
    public ResponseEntity read(@PathVariable Long idx) {
        bService.read(idx);
        return ResponseEntity.ok("B 단건 조회");
    }

    @GetMapping("/list")
    public ResponseEntity readList() {
        bService.readList();
        return ResponseEntity.ok("B 목록 조회");
    }
}
