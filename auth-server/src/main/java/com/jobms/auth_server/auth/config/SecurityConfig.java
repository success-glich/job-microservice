package com.jobms.auth_server.auth.config;


import com.jobms.auth_server.auth.filter.AuthEntryPointJwt;
import com.jobms.auth_server.auth.filter.AuthTokenFilter;
import com.jobms.auth_server.auth.filter.CustomAccessDeniedHandler;
import com.jobms.auth_server.auth.impl.UserDetailsServiceImpl;
import com.jobms.auth_server.auth.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
   private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {


        http.authorizeHttpRequests(authorizeRequests->

                authorizeRequests
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/api/auth/me").authenticated()
                        .anyRequest().authenticated());

        http.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler).accessDeniedHandler(accessDeniedHandler));

        http.cors(cors -> cors.configurationSource(request -> {
            var config = new org.springframework.web.cors.CorsConfiguration();
//            config.addAllowedOrigin("http://localhost:3000");
//            config.addAllowedOrigin("http://localhost:3001");
//            config.addAllowedOrigin("http://localhost:5173");
            config.addAllowedOriginPattern("*");
            config.addAllowedMethod("*");
            config.addAllowedHeader("*");
            config.setAllowCredentials(true);
            return config;
        }));
        http.addFilterBefore(authenticationJwtTokenFilter(),
                UsernamePasswordAuthenticationFilter.class);
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }


}
