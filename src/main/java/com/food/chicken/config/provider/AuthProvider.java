package com.food.chicken.config.provider;

import com.food.chicken.service.MemberService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthProvider implements AuthenticationProvider {

    private final MemberService memberService;

    public AuthProvider(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String id = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (!memberService.isExistMember(id, password)) {
            return null;
        }

        List<GrantedAuthority> authList = new ArrayList<>();

        authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new UserAuthentication(authentication.getName(), authentication.getCredentials().toString(), authList);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
