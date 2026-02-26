package com.example.demo.reply.model;

import com.example.demo.board.model.Board;
import com.example.demo.user.model.User;
import lombok.Builder;
import lombok.Getter;

public class ReplyDto {

    @Getter
    public static class RegReq {
        private String contents;

        public Reply toEntity(Long userIdx, Long boardIdx) {
            return Reply.builder()
                    .contents(this.contents)
                    .user(User.builder().idx(userIdx).build())
                    .board(Board.builder().idx(boardIdx).build())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class RegRes {
        private Long idx;
        private String contents;
        private Long boardIdx;
        private Long userIdx;

        public static ReplyDto.RegRes from(Reply entity) {
            return RegRes.builder()
                    .idx(entity.getIdx())
                    .contents(entity.getContents())
                    .boardIdx(entity.getBoard().getIdx())
                    .userIdx(entity.getUser().getIdx())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class ReadRes {
        private Long idx;
        private String contents;
        private String writer;

        public static ReplyDto.ReadRes from(Reply entity) {
            return ReadRes.builder()
                    .idx(entity.getIdx())
                    .contents(entity.getContents())
                    .writer(entity.getUser().getName())
                    .build();
        }
    }
}
