package com.food.chicken.model;

import com.food.chicken.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
public class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 테스트_멤버_등록_여부(){
        Member member = new Member();

        member.setMemberUid(1);
        member.setPassword("test");
        member.setId("test");

        Member savedMember = memberRepository.save(member);
        assertThat(savedMember.getId(), is(notNullValue()));
    }

    @Test
    public void 테스트_스프링_시큐리티_암호화(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encryptionPassword = bCryptPasswordEncoder.encode("best");
        Member member = new Member();

        member.setMemberUid(1);
        member.setId("travel");
        member.setPassword(encryptionPassword);

        Member savedMember = memberRepository.save(member);

        assertTrue(bCryptPasswordEncoder.matches("best", savedMember.getPassword()));
        assertEquals(encryptionPassword, savedMember.getPassword());
    }
}