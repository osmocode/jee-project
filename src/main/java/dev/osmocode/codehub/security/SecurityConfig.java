package dev.osmocode.codehub.security;


import dev.osmocode.codehub.entity.Authority;
import dev.osmocode.codehub.entity.User;
import dev.osmocode.codehub.service.AuthorityService;
import dev.osmocode.codehub.utils.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.stream.IntStream;

@Configuration
@EnableWebSecurity(/*debug = true*/)
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChainDev(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http
                .authorizeHttpRequests(requests -> {
                    // PUBLIC ROUTE
                    requests.requestMatchers(
                            "/question/ask"
                    ).authenticated();
                    requests.requestMatchers(
                            "/",
                            "/index",
                            "/error",
                            "/favicon.ico",
                            "/console",
                            "/dist/**",
                            "/images/**",
                            "/home",
                            "/profile/**",
                            "/register",
                            "/profiles",
                            "/tags**",
                            "/question/**"
                            ).permitAll();
                    // ADMIN ROUTE
                    requests.requestMatchers(
                            "/admin"
                    ).hasAuthority(Role.ADMIN.toString());
                    // OTHER ROUTE ARE SECURED BY DEFAULT
                    requests.anyRequest().authenticated();
                })
                .formLogin(form ->
                        form
                                .loginPage("/login")
                                .defaultSuccessUrl("/dashboard")
                                .failureUrl("/login?error")
                                .permitAll()
                )
                .logout(form ->
                        form.logoutSuccessUrl("/"));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(
            PasswordEncoder passwordEncoder,
            CustomUserDetailsService customUserDetailsService,
            AuthorityService authorityService
    ) {
        Authority roleUser = authorityService.saveAuthority(new Authority(Role.USER.toString()));
        Authority roleAdmin = authorityService.saveAuthority(new Authority(Role.ADMIN.toString()));

        User admin = new User(
                "admin",
                passwordEncoder.encode("admin"),
                "admin@uge-overflow.com",
                roleAdmin
        );

        User ypicker = new User(
                "ypicker",
                passwordEncoder.encode("password"),
                "ypicker@uge-overflow.com",
                roleUser
        );

        User user = new User(
                "user",
                passwordEncoder.encode("password"),
                "user@uge-overflow.com",
                roleUser
        );

        customUserDetailsService.saveUser(admin);
        customUserDetailsService.saveUser(ypicker);
        customUserDetailsService.saveUser(user);

        IntStream.range(1, 121).forEach(i -> {
            User user_i = new User(
                    "user_" + i,
                    passwordEncoder.encode("password"),
                    "user_" + i + "@uge-overflow.com",
                    roleUser
            );
            customUserDetailsService.saveUser(user_i);
        });

        return customUserDetailsService;
    }

}
