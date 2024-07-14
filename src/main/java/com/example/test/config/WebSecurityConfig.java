package com.example.test.config;

import com.example.test.jwt.JwtAccessDeniedHandler;
import com.example.test.jwt.JwtAuthenticationEntryPoint;
import com.example.test.jwt.JwtFilter;
import com.example.test.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Component
public class WebSecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).formLogin(Customizer.withDefaults())
                        .authorizeHttpRequests(authroizeRequest
                                -> authroizeRequest.requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/auth/login/**").permitAll()
                                .requestMatchers("/callback/**").permitAll()
                                .requestMatchers("/history/MatchHistory/**").permitAll()
                                .requestMatchers("/history/**").permitAll()
                                .requestMatchers("/history/rankGameTier").permitAll()
                                .requestMatchers("/history/ranking").permitAll()
                                .anyRequest().permitAll()
                        );

        JwtFilter jwtFilter = new JwtFilter(tokenProvider);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}
