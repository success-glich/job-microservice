package com.jobApplication.jobMs.job.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {


/*
@LoadBalanced
When you use @LoadBalanced on a RestTemplate bean, it allows the application to use a logical service name instead of a hardcoded URL,
and it automatically distributes requests across multiple instances of a service registered with a service discovery tool (like Eureka, Consul, etc.).
 */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
