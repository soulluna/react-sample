package com.example.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.common.exception.ResourceNotFoundException;
import com.example.repository.MemberRepository;

/**
 * DaoAuthenticationProvider 구현
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.memberRepo.findByEmail(username).orElseThrow(
                () -> new ResourceNotFoundException("Member", "Member Email : ", username));
    }
}
