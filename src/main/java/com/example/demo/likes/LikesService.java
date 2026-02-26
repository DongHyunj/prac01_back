package com.example.demo.likes;

import com.example.demo.likes.model.Likes;
import com.example.demo.likes.model.LikesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;

    public LikesDto.RegRes reg(Long userIdx, Long boardIdx) {
        Likes likes = LikesDto.RegReq.toEntity(userIdx, boardIdx);
        likes = likesRepository.save(likes);

        return LikesDto.RegRes.from(likes);
    }
}
