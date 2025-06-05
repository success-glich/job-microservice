package com.jobms.auth_server.auth.dto;

public record LoginResponse( String jwtToken,
                            String username,
                            String  roles) {
    // No additional methods or fields needed, as this is a simple record class) {
}
