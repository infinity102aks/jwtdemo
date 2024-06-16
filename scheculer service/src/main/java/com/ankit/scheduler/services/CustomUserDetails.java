package com.ankit.scheduler.services;

import com.ankit.scheduler.Entity.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {

    private User user;

    public CustomUserDetails(User user, String roleType) {
        super(user.getName(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority(roleType)));
        this.user = user;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    public long getUserId() {
        return this.user.getId();
    }

    public User getUser() {
        return this.user;
    }
}
