package com.example.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.dto.response.file.ResFileDownloadDto;
import com.example.dto.response.file.ResFileUploadDto;
import com.example.service.FileService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/board/{boardId}/file")
@RequiredArgsConstructor
public class FileController {

	@Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<List<ResFileUploadDto>> upload (
            @PathVariable Long boardId,
            @RequestParam("file") List<MultipartFile> files) throws IOException {
        List<ResFileUploadDto> saveFile = fileService.upload(boardId, files);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveFile);
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download (
            @RequestParam("fileId") Long fileId) throws IOException {
        ResFileDownloadDto downloadDto = fileService.download(fileId);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(downloadDto.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + downloadDto.getFilename() + "\"")
                .body(new ByteArrayResource(downloadDto.getContent()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Long> delete (
            @RequestParam("fileId") Long fileId) {
        fileService.delete(fileId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}