package com.example.demo.reply.model;

import com.example.demo.board.model.Board;
import com.example.demo.user.model.User;
import lombok.Builder;
import lombok.Getter;

public class ReplyDto {

    @Getter
    public static class RegReq {
        private String contents;
        private Long userIdx;
        private Long boardIdx;

        public Reply toEntity() {
            return Reply.builder()
                    .contents(this.contents)
                    .user(User.builder().idx(this.userIdx).build())
                    .board(Board.builder().idx(this.boardIdx).build())
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
