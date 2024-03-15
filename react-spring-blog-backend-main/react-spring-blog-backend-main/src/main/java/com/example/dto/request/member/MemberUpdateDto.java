package com.example.dto.request.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * -Request-
 * 회원 정보 변경 요청 dto
 */

@Getter
@Setter
@NoArgsConstructor
@Builder
public class MemberUpdateDto {

    private String password;
    private String passwordCheck;
    private String username;


    public MemberUpdateDto(String password, String passwordCheck, String username) {
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.username = username;
    }
}
