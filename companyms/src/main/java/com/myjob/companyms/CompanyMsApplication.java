package com.myjob.companyms;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableJpaAuditing
@EnableFeignClients
@Tag(name = "Public APIS", description = "APIs for managing companies")
public class 	CompanyMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanyMsApplication.class, args);
	}

	@GetMapping("/health-check")
	public String healthCheck() {
		return "OK";
	}

}

