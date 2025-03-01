package com.thevivek2.example.common.auth;

public interface TheVivek2AuthService {
    String getCurrentUser();

    void hasAnyRole(String... roles);

    void hasRole(String... roles);


}
