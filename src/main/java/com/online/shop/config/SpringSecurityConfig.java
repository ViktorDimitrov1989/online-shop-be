package com.online.shop.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] allowedPaths =
            new String[]{"/",
            "/register",
            "/login"};

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TODO: source - https://github.com/kamalber/spring-boot-angular4-authentication
        http
                .cors().and()
                .authorizeRequests()
                .antMatchers(allowedPaths).permitAll()
                .anyRequest().fullyAuthenticated()
                .and().logout()
                .permitAll().logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                .and()
                .httpBasic().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
                .csrf().disable();

    }

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
