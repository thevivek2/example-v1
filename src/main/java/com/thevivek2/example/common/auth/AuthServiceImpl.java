package com.thevivek2.example.common.auth;

import com.thevivek2.example.common.exception.ApiException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements TheVivek2AuthService {


    @Override
    public String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


    @Override
    public void hasAnyRole(String... roles) {
        var authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();
        for (String s : roles) {
            if (authorities.contains(s)) {
                return;
            }
        }
        throw ApiException.of("User doesn't have any of these roles " + Arrays.toString(roles));
    }


    @Override
    public void hasRole(String... roles) {
        var authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();
        for (String s : roles) {
            if (!authorities.contains(s)) {
                throw ApiException.of("User is required to have all of these roles " + Arrays.toString(roles));
            }
        }
    }

}
