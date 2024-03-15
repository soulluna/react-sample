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
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

	@Autowired
    private BoardService boardService;

    // 페이징 목록
    @GetMapping("/list")
    public ResponseEntity<Page<ResBoardListDto>> boardList(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ResBoardListDto> listDTO = boardService.getAllBoards(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listDTO);
    }

    // 페이징 검색 , Get 요청 @RequestBody 사용할 수 없음
    @GetMapping("/search")
    public ResponseEntity<Page<ResBoardListDto>> search(
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam String writerName) {
        SearchData searchData = SearchData.createdSearchData(title, content, writerName);
        Page<ResBoardListDto> searchBoard = boardService.search(searchData, pageable);
        return  ResponseEntity.status(HttpStatus.OK).body(searchBoard);
    }

    @PostMapping("/write")
    public ResponseEntity<ResBoardWriteDto> write(
            @RequestBody BoardWriteDto boardDTO,
            @AuthenticationPrincipal Member member) {
        Thread currentThread = Thread.currentThread();
        log.info("현재 실행 중인 스레드: " + currentThread.getName());
        ResBoardWriteDto saveBoardDTO = boardService.write(boardDTO, member);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveBoardDTO);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<ResBoardDetailsDto> detail(@PathVariable("boardId") Long boardId) {
        ResBoardDetailsDto findBoardDTO = boardService.detail(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(findBoardDTO);
    }

    // 상세보기 -> 수정
    @PatchMapping("/{boardId}/update")
    public ResponseEntity<ResBoardDetailsDto> update(
            @PathVariable Long boardId,
            @RequestBody BoardUpdateDto boardDTO) {
        ResBoardDetailsDto updateBoardDTO = boardService.update(boardId, boardDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updateBoardDTO);
    }

    // 상세보기 -> 삭제
    @DeleteMapping("/{boardId}/delete")
    public ResponseEntity<Long> delete(@PathVariable Long boardId) {
        boardService.delete(boardId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
