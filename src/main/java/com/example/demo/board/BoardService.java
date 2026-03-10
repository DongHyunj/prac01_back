package com.example.demo.board;

import com.example.demo.board.model.Board;
import com.example.demo.reply.ReplyRepository;
import com.example.demo.reply.model.Reply;
import lombok.RequiredArgsConstructor;
import com.example.demo.board.model.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    public BoardDto.RegRes register(Long userIdx, BoardDto.RegReq dto) {
        Board board = dto.toEntity(userIdx);
        board = boardRepository.save(board);

        return BoardDto.RegRes.from(board);
    }

    public BoardDto.PageRes list(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        // 페이징 처리 ⭕, 페이지 번호가 필요하다. => Page 반환
        Page<Board> boardList = boardRepository.findAll(pageRequest);
        // 페이징 처리 ⭕, 페이지 번호가 필요없다. => Slice 반환
        // Slice<Board> boardList = boardRepository.findAll(pageRequest);

        return BoardDto.PageRes.from(boardList);
    }

//    @Transactional(readOnly = true)
    public BoardDto.ReadRes read(Long idx) {
        Board board = boardRepository.findById(idx).orElseThrow();
        Page<Reply> replies = replyRepository.findByBoard(board, PageRequest.of(0, 4));
        return BoardDto.ReadRes.from(board, replies.getContent());
    }

    public BoardDto.RegRes update(Long idx, BoardDto.RegReq dto) {
        Board board = boardRepository.findById(idx).orElseThrow();
        board.update(dto);

        boardRepository.save(board);

        return BoardDto.RegRes.from(board);
    }

    public void delete(Long idx) {
        boardRepository.deleteById(idx);
    }
}
