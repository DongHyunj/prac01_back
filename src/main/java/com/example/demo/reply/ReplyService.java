package com.example.demo.reply;

import com.example.demo.reply.model.Reply;
import com.example.demo.reply.model.ReplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyRepository replyRepository;
    public ReplyDto.RegRes reg(Long userIdx, Long boardIdx, ReplyDto.RegReq dto) {
        Reply reply = dto.toEntity(userIdx, boardIdx);

        reply = replyRepository.save(reply);

        return ReplyDto.RegRes.from(reply);
    }
}
