package com.example.demo.likes.model;

import com.example.demo.board.model.Board;
import com.example.demo.user.model.User;
import lombok.Builder;
import lombok.Getter;

public class LikesDto {

    @Getter
    public static class RegReq {

        public static Likes toEntity(Long userIdx, Long boardIdx) {
            return Likes.builder()
                    .user(User.builder().idx(userIdx).build())
                    .board(Board.builder().idx(boardIdx).build())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class RegRes {
        private Long idx;
        private Long boardIdx;
        private Long userIdx;

        public static LikesDto.RegRes from(Likes entity) {
            return RegRes.builder()
                    .idx(entity.getIdx())
                    .boardIdx(entity.getBoard().getIdx())
                    .userIdx(entity.getUser().getIdx())
                    .build();
        }
    }
}
