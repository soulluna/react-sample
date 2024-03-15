package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Comment;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT c FROM Comment c JOIN FETCH c.member JOIN FETCH c.board b WHERE b.id = :boardId")
    Page<Comment> findAllWithMemberAndBoard(@Param("boardId") Long boardId, Pageable pageable);

    @Query(value = "SELECT c FROM Comment c JOIN FETCH c.member m JOIN FETCH c.board b WHERE c.id = :commentId")
    Optional<Comment> findByIdWithMemberAndBoard( @Param("commentId")Long commentId);
}
