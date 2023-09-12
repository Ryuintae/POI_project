package com.example.demo.security.config;

import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final AuthenticationFailureHandler CustomAuthenticationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login", "/singUp", "/access_denied", "/resources/**").permitAll()
                .antMatchers("/userAccess").hasAnyRole("USER", "ADMIN")
                .and()
                .formLogin()
                .loginProcessingUrl("/login_proc")
                .defaultSuccessUrl("/")
                .failureHandler(CustomAuthenticationFailureHandler)//로그인 실패 시 처리하는 핸들러 등록.
                .and()
                .logout()
                .logoutUrl("/logout") // 로그아웃 처리 URL
                .logoutSuccessUrl("/") // 로그아웃 성공 후 이동할 페이지
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .csrf().disable();
    }


    @Override
    public void configure(WebSecurity web) {
        // 인증을 무시할 경로들을 설정해기.
        // static 하위 폴더 (css, js, img)는 무조건 접근이 가능해야하기 때문에 인증을 무시해야함
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }
}