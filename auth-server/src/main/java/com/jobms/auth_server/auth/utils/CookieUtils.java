package com.jobms.auth_server.auth.utils;

import com.jobms.auth_server.auth.dto.LoginResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.Optional;

public class CookieUtils {
    private CookieUtils() {
        // utility class
    }

    public static Cookie createCookie(
            String name,
            String value,
            int maxAge,
            boolean httpOnly,
            String path,
            boolean secure
    ) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(httpOnly);
        cookie.setSecure(secure);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        return cookie;
    }

    public static void addCookie(
            HttpServletResponse response,
            Cookie cookie
    ) {
        response.addCookie(cookie);
    }

    public static Optional<Cookie> getCookie(
            HttpServletRequest request,
            String name
    ) {
        if (request.getCookies() == null) {
            return Optional.empty();
        }
        return Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(name))
                .findFirst();
    }

    /**
     * Delete a cookie by name (sets maxAge=0 and clears value).
     */
    public static void deleteCookie(
            HttpServletRequest request,
            HttpServletResponse response,
            String name,
            String path
    ) {
        getCookie(request, name).ifPresent(cookie -> {
            cookie.setValue("");
            cookie.setPath(path);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        });
    }


}