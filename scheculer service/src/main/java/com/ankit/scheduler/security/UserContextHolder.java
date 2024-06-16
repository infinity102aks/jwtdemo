package com.ankit.scheduler.security;

import com.ankit.scheduler.services.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserContextHolder {
    private static ThreadLocal<CustomUserDetails> threadLocal = new ThreadLocal<>();

    public static void setUserDetail(CustomUserDetails customUserDetails) {
        UserContextHolder.threadLocal.set(customUserDetails);
    }

    public static CustomUserDetails getUserDetails() {
        return UserContextHolder.threadLocal.get();
    }

    public static void clearContext() {
        UserContextHolder.threadLocal.remove();
    }
}
