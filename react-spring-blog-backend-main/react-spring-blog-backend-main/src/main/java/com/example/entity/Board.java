package com.example.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import com.example.common.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Builder
public class Board extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "BOARD_ID")
    private Long id;

    @Column(nullable = false)
    private String title;

    private String content;

    @Column(name = "VIEW_COUNT")
    private int viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    public Member member;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    public List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    public List<FileEntity> files = new ArrayList<>();

    
    public Board(Long id, String title, String content, int viewCount, Member member, List<Comment> comments, List<FileEntity> files) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.member = member;
        this.comments = comments;
        this.files = files;
    }

    //== 조회수 증가 ==//
    public void upViewCount() {
        this.viewCount++;
    }

    //== 수정 Dirty Checking ==//
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    } 
 
    //== Member & Board 연관관계 편의 메소드 ==//
    public void setMappingMember(Member member) {
        this.member = member;
        member.getBoards().add(this);
    }
}
