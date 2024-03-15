package com.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.dto.request.board.BoardUpdateDto;
import com.example.dto.request.board.BoardWriteDto;
import com.example.dto.request.board.SearchData;
import com.example.dto.response.board.ResBoardDetailsDto;
import com.example.dto.response.board.ResBoardListDto;
import com.example.dto.response.board.ResBoardWriteDto;
import com.example.entity.Member;
import com.example.service.BoardService;

@RestController
@RequestMapping("/board/{boardId}/reply")
@RequiredArgsConstructor
@Slf4j
public class ReplyController {

	@Autowired
    private BoardService boardService;

    @PostMapping("/write")
    public ResponseEntity<ResBoardWriteDto> write(
            @RequestBody BoardWriteDto boardDTO,
            @AuthenticationPrincipal Member member) {
        Thread currentThread = Thread.currentThread();
        log.info("현재 실행 중인 스레드: " + currentThread.getName());
        ResBoardWriteDto saveBoardDTO = boardService.write(boardDTO, member);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveBoardDTO);
    }
}
