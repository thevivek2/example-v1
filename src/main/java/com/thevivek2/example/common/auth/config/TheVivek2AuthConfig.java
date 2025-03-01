package com.thevivek2.example.common.auth.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class TheVivek2AuthConfig {

    private static final Log LOGGER = LogFactory.getLog(TheVivek2AuthConfig.class);

    public static final String PREFIX_ROLE = "ROLE_";
    private final HashService hashService;

    @Autowired
    public TheVivek2AuthConfig(HashService hashService) {
        this.hashService = hashService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer
                        .configurationSource(corsConfiguration()))
                .csrf(AbstractHttpConfigurer::disable)
                .securityMatchers((matchers) -> matchers.requestMatchers("/**"))
                .authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
                .sessionManagement((session) -> session.sessionCreationPolicy(STATELESS))
                .httpBasic(withDefaults());
        var build = http.build();
        LOGGER.trace("Configuring security for /** done. Applied filters are " + build.toString());
        return build;
    }

    @Bean
    UserDetailsService detailService() {
        return username -> {
            return new User(username, Base64.getEncoder()
                    .encodeToString("0123456789".getBytes()),
                    List.of(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN"),
                            new SimpleGrantedAuthority("ROLE_ADMIN")));
        };
    }

    @Bean
    PasswordEncoder encoder() {
        LOGGER.trace("Using password encoder that does validates password...");
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return Base64.getEncoder()
                        .encodeToString(rawPassword.toString().getBytes());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return Base64.getEncoder()
                        .encodeToString(rawPassword.toString().getBytes()).equals(encodedPassword);
            }
        };
    }

    @Bean
    CorsConfigurationSource corsConfiguration() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept",
                "Authorization", "Cookie", "x-auth-token"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.setAllowCredentials(true);
        config.setExposedHeaders(List.of("Set-Cookie"));
        config.setMaxAge(1209600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    private static SimpleGrantedAuthority getDefaultRole() {
        return new SimpleGrantedAuthority(PREFIX_ROLE + "USER");
    }


}
