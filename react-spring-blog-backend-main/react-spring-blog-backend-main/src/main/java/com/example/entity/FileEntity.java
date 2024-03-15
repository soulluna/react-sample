package com.example.entity;

import com.example.common.BaseTimeEntity;
import com.example.dto.request.member.MemberLoginDto;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Table(name = "FILE")
@Getter
@NoArgsConstructor
public class FileEntity extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "FILE_ID")
    private Long id;

    @Column(name = "ORIGIN_FILE_NAME")
    private String originFileName;

    @Column(name = "FILE_TYPE")
    private String fileType;

    @Column(name = "FILE_PATH")
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    public Board board;

    
    public FileEntity(Long id, String originFileName, String filePath, String fileType, Board board) {
        this.id = id;
        this.originFileName = originFileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.board = board;
    }

    public void setMappingBoard(Board board) {
        this.board = board;
    }
}
