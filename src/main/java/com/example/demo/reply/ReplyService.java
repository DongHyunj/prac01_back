package com.example.demo.reply;

import com.example.demo.reply.model.ReplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyRepository replyRepository;
    public void reg(ReplyDto.RegReq dto) {
        replyRepository.save(dto.toEntity());
    }
}
