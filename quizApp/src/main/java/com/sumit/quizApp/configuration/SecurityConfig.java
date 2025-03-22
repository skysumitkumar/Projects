package com.sumit.quizApp.configuration;

import com.sumit.quizApp.service.admin.MyAdminDetailsService;
import com.sumit.quizApp.service.user.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final MyUserDetailsService userDetailsService;
    private final MyAdminDetailsService adminDetailsService;

    public SecurityConfig(MyUserDetailsService userDetailsService, MyAdminDetailsService adminDetailsService) {
        this.userDetailsService = userDetailsService;
        this.adminDetailsService = adminDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/signup").permitAll()
                        .requestMatchers("/user/signup").permitAll()
                        .requestMatchers("/admin/**").hasRole("admin")  // Admin access
                        .requestMatchers("/user/**").hasRole("user")    // User access
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())  // Enable basic auth
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        DaoAuthenticationProvider userProvider = new DaoAuthenticationProvider();
        userProvider.setUserDetailsService(userDetailsService);
        userProvider.setPasswordEncoder(passwordEncoder());

        DaoAuthenticationProvider adminProvider = new DaoAuthenticationProvider();
        adminProvider.setUserDetailsService(adminDetailsService);
        adminProvider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(List.of(userProvider, adminProvider));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();  // No encoding (Unsafe for production)
    }
}
