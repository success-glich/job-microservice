package com.jobms.auth_server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AuthServerApplication {



	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}

	@GetMapping("/public/ping")
	public String ping() {

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		System.out.println("Password encoded: " + passwordEncoder.encode("admin"));
		return "pong";
	}
}
