package com.example.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.dto.request.comment.CommentDto;
import com.example.dto.response.comment.ResCommentDto;
import com.example.entity.Member;
import com.example.service.CommentService;

@RestController
@RequestMapping("/board/{boardId}/comment")
@RequiredArgsConstructor
public class CommentController {

	@Autowired
    private CommentService commentService;

    @GetMapping("/list")
    public ResponseEntity<Page<ResCommentDto>> commentList(
            @PathVariable Long boardId,
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<ResCommentDto> commentList = commentService.getAllComments(pageable, boardId);
        return ResponseEntity.status(HttpStatus.OK).body(commentList);
    }

    @PostMapping("/write")
    public ResponseEntity<ResCommentDto> write(
            @AuthenticationPrincipal Member member,
            @PathVariable Long boardId,
            @RequestBody CommentDto commentDto) {

        ResCommentDto saveCommentDTO = commentService.write(boardId, member, commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveCommentDTO);
    }

    @PatchMapping("/update/{commentId}")
    public ResponseEntity<ResCommentDto> update(
            @PathVariable Long commentId,
            @RequestBody CommentDto commentDto) {

        ResCommentDto updateCommentDTO = commentService.update(commentId, commentDto);
        return ResponseEntity.status(HttpStatus.OK).body(updateCommentDTO);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Long> delete(@PathVariable Long commentId) {

        commentService.delete(commentId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
