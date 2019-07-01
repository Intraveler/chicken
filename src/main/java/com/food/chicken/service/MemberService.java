package com.food.chicken.service;

import com.food.chicken.model.Member;
import com.food.chicken.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // Member 존재 여부 체크
    public boolean isExistMember(String id, String password) {
        return Optional.ofNullable(memberRepository.findById(id))
                .map(Member::getPassword)
                .map(dbPassword -> comparePassword(password, dbPassword))
                .orElse(false);
    }

    // 입력 패스워드와 DB에 저장된 패스워드 비교
    public boolean comparePassword(String originPassword, String dbPassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(originPassword, dbPassword);
    }
}
