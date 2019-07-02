package com.food.chicken;

import com.food.chicken.model.entity.Member;
import com.food.chicken.repository.MemberRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ChickenApplication {

    @Autowired
    MemberRepository memberRepository;

    public static void main(String[] args) {
        SpringApplication.run(ChickenApplication.class, args);
    }

        @Bean
        InitializingBean save(){
        return () -> {

            Member member = new Member();
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            member.setMemberUid(1);
            member.setId("chicken");
            String encryptionPassword = bCryptPasswordEncoder.encode("leg");
            member.setPassword(encryptionPassword);

            memberRepository.save(member);
        };
    }
}
