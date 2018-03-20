package com.online.shop.config;


import com.online.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private CustomLogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    public SpringSecurityConfig(UserService userService,
                                BCryptPasswordEncoder bCryptPasswordEncoder,
                                CustomLogoutSuccessHandler logoutSuccessHandler) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.logoutSuccessHandler = logoutSuccessHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userService).passwordEncoder(this.bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .authorizeRequests()
                .antMatchers("/", "/auth/register", "/auth/login", "/auth/logout").permitAll()
                .anyRequest().authenticated()
                /*.and()
                .formLogin().loginPage("/auth/login1").permitAll()
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .rememberMe()
                .rememberMeCookieName("RememberMe")
                .rememberMeParameter("rememberMe")
                .key("Taina")
                .tokenValiditySeconds(604800)*/
                .and()
                .logout().logoutSuccessHandler(this.logoutSuccessHandler).permitAll()
                .and()
                .httpBasic().and()
                .csrf().disable();

    }

}
