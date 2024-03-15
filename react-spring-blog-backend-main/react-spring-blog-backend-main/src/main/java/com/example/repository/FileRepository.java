package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

}
