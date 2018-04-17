package com.online.shop.config;


import com.online.shop.config.handlers.CustomAccessDeniedHandler;
import com.online.shop.config.handlers.CustomLogoutSuccessHandler;
import com.online.shop.areas.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private CustomLogoutSuccessHandler logoutSuccessHandler;

    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    public SpringSecurityConfig(UserService userService,
                                BCryptPasswordEncoder bCryptPasswordEncoder,
                                CustomLogoutSuccessHandler logoutSuccessHandler,
                                CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userService).passwordEncoder(this.bCryptPasswordEncoder);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        //.access("hasRole('ADMIN')")
        //http.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());

        http
                //.cors().and()
                .exceptionHandling().accessDeniedHandler(this.customAccessDeniedHandler)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                .antMatchers("/", "/auth/register", "/auth/logout", "/unAuth/**").permitAll()
                .antMatchers("/auth/login").permitAll()
                .antMatchers("/admin/**").access("hasRole('ADMIN')")
                .anyRequest().authenticated()
                .and()
                .logout().logoutSuccessHandler(this.logoutSuccessHandler).permitAll()
                .and()
                .csrf().disable();

    }

}
