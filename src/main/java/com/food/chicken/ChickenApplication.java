package com.food.chicken;

import com.food.chicken.model.entity.Member;
import com.food.chicken.repository.MemberRepository;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableEncryptableProperties
public class ChickenApplication {

    @Autowired
    MemberRepository memberRepository;

    public static void main(String[] args) {
        SpringApplication.run(ChickenApplication.class, args);
    }

    @Bean
    InitializingBean initData() {
        return this::initMember;
    }

    private void initMember(){
        Member member = new Member();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        member.setMemberUid(1);
        member.setId("swagger");
        String password1 = bCryptPasswordEncoder.encode("swagger");
        member.setPassword(password1);
        memberRepository.save(member);

        Member member2 = new Member();
        BCryptPasswordEncoder bCryptPasswordEncoder2 = new BCryptPasswordEncoder();
        member2.setMemberUid(2);
        member2.setId("chicken");
        String password2 = bCryptPasswordEncoder2.encode("leg");
        member2.setPassword(password2);
        memberRepository.save(member2);

        Member member3 = new Member();
        BCryptPasswordEncoder bCryptPasswordEncoder3 = new BCryptPasswordEncoder();
        member3.setMemberUid(3);
        member3.setId("tester");
        String password3 = bCryptPasswordEncoder3.encode("tester");
        member3.setPassword(password3);
        memberRepository.save(member3);

        Member member4 = new Member();
        BCryptPasswordEncoder bCryptPasswordEncoder4 = new BCryptPasswordEncoder();
        member4.setMemberUid(4);
        member4.setId("zip");
        String password4 = bCryptPasswordEncoder4.encode("zip");
        member4.setPassword(password4);
        memberRepository.save(member4);
    }
}
