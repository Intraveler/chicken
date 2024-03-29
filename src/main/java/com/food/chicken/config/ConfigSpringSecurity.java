package com.food.chicken.config;

import com.food.chicken.config.provider.AuthProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class ConfigSpringSecurity extends WebSecurityConfigurerAdapter {
    private final AuthProvider authProvider;

    public ConfigSpringSecurity(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    @Override
    public void configure(WebSecurity web) {

        web.ignoring().antMatchers(
                "/h2/**"
                ,"/css/**"
                , "/script/**"
                , "image/**"
                , "/fonts/**"
                , "lib/**"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/h2/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/**").authenticated();

        http.formLogin()
                .defaultSuccessUrl("/main.html")
                .usernameParameter("username")
                .passwordParameter("password");

        http.authenticationProvider(authProvider);
    }
}