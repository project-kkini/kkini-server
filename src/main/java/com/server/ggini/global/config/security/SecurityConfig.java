package com.server.ggini.global.config.security;

import com.server.ggini.domain.member.service.MemberService;
import com.server.ggini.global.security.filter.JwtAuthenticationFilter;
import com.server.ggini.global.security.handler.EmailPasswordSuccessHandler;
import com.server.ggini.global.security.filter.EmailPasswordAuthenticationFilter;
import com.server.ggini.global.security.provider.EmailPasswordAuthenticationProvider;
import com.server.ggini.global.security.provider.JwtTokenProvider;
import com.server.ggini.global.security.utils.JwtUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberService memberService;
    private final EmailPasswordSuccessHandler emailPasswordSuccessHandler;
    private final JwtUtil jwtUtil;

    private String[] allowUrls = {"/", "/favicon.ico",
        "/api/v1/auth/oauth/**", "/swagger-ui/**", "/v3/**"};

    @Value("${cors-allowed-origins}")
    private List<String> corsAllowedOrigins;

    // 1. Password encoder
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. AuthenticationProvider
    @Bean
    public EmailPasswordAuthenticationProvider emailPasswordAuthenticationProvider() {
        return new EmailPasswordAuthenticationProvider(memberService);
    }

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider(jwtUtil, memberService);
    }

    // 3. AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .authenticationProvider(emailPasswordAuthenticationProvider())
                .authenticationProvider(jwtTokenProvider());
        authenticationManagerBuilder.parentAuthenticationManager(null);
        return authenticationManagerBuilder.build();
    }

    // 4. CORS Configuration
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(corsAllowedOrigins);
        configuration.addAllowedMethod("*");
        configuration.setAllowedHeaders(List.of("*")); // 허용할 헤더
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 적용
        return source;
    }

    // 5. Web Security Customizer
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().requestMatchers(allowUrls);
    }

    // 6. SecurityFilterChain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .cors(customizer -> customizer.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(allowUrls).permitAll()
                        .anyRequest().authenticated());

        http
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint((request, response, authException) ->
                                response.setStatus(HttpStatus.UNAUTHORIZED.value())));

        http.authenticationManager(authenticationManager(http));

        http
                .addFilterAt(emailPasswordAuthenticationFilter(authenticationManager(http)), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(authenticationManager(http)), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 7. EmailPasswordAuthenticationFilter
    @Bean
    public EmailPasswordAuthenticationFilter emailPasswordAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception {
        EmailPasswordAuthenticationFilter filter = new EmailPasswordAuthenticationFilter(authenticationManager);
        filter.setFilterProcessesUrl("/api/v1/auth/admin/login");
        filter.setAuthenticationSuccessHandler(emailPasswordSuccessHandler);
        filter.afterPropertiesSet();
        return filter;
    }

    // 8. JwtAuthenticationFilter
    public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(authenticationManager);
        filter.afterPropertiesSet();
        return filter;
    }
}
