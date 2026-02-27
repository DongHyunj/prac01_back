package com.example.demo.board.model;

import com.example.demo.reply.model.Reply;
import com.example.demo.reply.model.ReplyDto;
import com.example.demo.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

public class BoardDto {

    @Getter
    public static class RegReq {
        @Schema(description = "제목, 제목은 50글자까지만 입력 가능합니다.", required = true, example = "제목01")
        private String title;
        private String contents;

        public Board toEntity(Long userIdx) {
            return Board.builder()
                    .title(this.title)
                    .contents(this.contents)
                    .user(User.builder().idx(userIdx).build())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class RegRes {
        private Long idx;
        private String title;
        private String contents;
        private Long userIdx;

        public static RegRes from(Board entity) {
            return RegRes.builder()
                    .idx(entity.getIdx())
                    .title(entity.getTitle())
                    .contents(entity.getContents())
                    .userIdx(entity.getUser().getIdx())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class ListRes {
        private Long idx;
        private String title;
        private String userName;
        private int replyCount;
        private int likeCount;

        public static ListRes from(Board entity) {
            return ListRes.builder()
                    .idx(entity.getIdx())
                    .title(entity.getTitle())
                    .userName(entity.getUser().getName())
                    .replyCount(entity.getReplyList().size())
                    .likeCount(entity.getLikesList().size())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class ReadRes {
        private Long idx;
        private String title;
        private String contents;
        private String userName;
        private List<ReplyDto.ReadRes> replyList;
        private int likeCount;

        public static ReadRes from(Board entity) {
            return ReadRes.builder()
                    .idx(entity.getIdx())
                    .title(entity.getTitle())
                    .contents(entity.getContents())
                    .userName(entity.getUser().getName())
                    .replyList(entity.getReplyList().stream().map(ReplyDto.ReadRes::from).toList())
                    .likeCount(entity.getLikesList().size())
                    .build();
        }
    }
}
