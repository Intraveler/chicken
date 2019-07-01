package com.food.chicken.config.provider;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class UserAuthentication extends UsernamePasswordAuthenticationToken {

    public UserAuthentication(String id, String pw, List<GrantedAuthority> grantedAuthorityList) {
        super(id, pw, grantedAuthorityList);
    }
}