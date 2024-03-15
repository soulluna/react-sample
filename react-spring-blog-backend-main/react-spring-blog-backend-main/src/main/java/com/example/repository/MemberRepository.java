package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

}
