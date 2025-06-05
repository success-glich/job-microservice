package com.jobms.auth_server.auth;

import com.jobms.auth_server.auth.dto.LoginRequest;
import com.jobms.auth_server.auth.dto.LoginResponse;
import com.jobms.auth_server.auth.dto.UserDto;
import com.jobms.auth_server.auth.utils.ApiResponse;
import com.jobms.auth_server.auth.utils.CookieUtils;
import com.jobms.auth_server.auth.utils.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/auth")
public class AuthenticationController {
    // Add your authentication endpoints here


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    // Example endpoint
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {

        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));

        } catch (Exception ex) {
            log.error("Authentication failed: {}", ex.getMessage());
            return ResponseEntity.status(401).body(new ApiResponse<>(false, "Invalid credentials", null));
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails.getUsername());

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        LoginResponse payload = new LoginResponse(userDetails.getUsername(), roles.getFirst(), jwtToken);

//        * set cookies

        Cookie jwtCookie = CookieUtils.createCookie(
                "JWT",                // name
                jwtToken,             // value
                60 * 60,              // maxAge = 1h in seconds
                true,                 // httpOnly
                "/",                  // path
                false                  // secure (set false in local/dev if not on HTTPS)
        );

        CookieUtils.addCookie(response, jwtCookie);
        return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", payload));

    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDto>> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(401).body(new ApiResponse<>(false, "Unauthorized", null));
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        UserDto userDto = new UserDto(userDetails.getUsername(), roles.get(0));

        return ResponseEntity.ok(new ApiResponse<>(true, "User profile retrieved successfully", userDto));
    }
}