package com.clone.magabox.config;

import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.clone.magabox.jwt.security.JwtAuthenticationEntryPoint;
import com.clone.magabox.jwt.exception.JwtAccessDeniedHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import com.clone.magabox.jwt.security.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import lombok.RequiredArgsConstructor;


@EnableWebSecurity
@RequiredArgsConstructor
@ConditionalOnDefaultWebSecurity
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SecurityConfig{
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return  (web) -> web.ignoring()
                .antMatchers("/h2-console/**");
    }

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // CSRF 설정 Disable
        http.csrf().disable()
                .cors().disable()
                // exception handling 할 때 우리가 만든 클래스를 추가
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // h2-console 을 위한 설정을 추가
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                // 시큐리티는 기본적으로 세션을 사용
                // 여기서는 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 로그인, 회원가입 API 는 토큰이 없는 상태에서 요청이 들어오기 때문에 permitAll 설정
                .and()
                .authorizeRequests()
                .antMatchers("/members/**").permitAll()
                .antMatchers(HttpMethod.POST,"/movies/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/movies/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/movies/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/times/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST).authenticated()
                .antMatchers(HttpMethod.DELETE).authenticated()
                .antMatchers(HttpMethod.PUT).authenticated()
                .anyRequest()
                .permitAll()
                .and()
                .apply(new JwtSecurityConfig(tokenProvider));

        return http.build();
    }

}
