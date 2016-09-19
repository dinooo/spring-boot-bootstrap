package com.piksel.movies.service;

import com.piksel.movies.representation.MemberPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.inject.Named;

@Named
public class SecurityService {
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public void setAuthentication(Authentication auth) {
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public void invalidateAuthentication() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    public long getCurrentMemberId() {
        return ((MemberPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
    }
}
