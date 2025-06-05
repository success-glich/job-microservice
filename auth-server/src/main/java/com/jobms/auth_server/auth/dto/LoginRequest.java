package com.jobms.auth_server.auth.dto;

public record LoginRequest(
    String username,
    String password
) {
    // No additional methods or fields needed, as this is a simple record class
}
