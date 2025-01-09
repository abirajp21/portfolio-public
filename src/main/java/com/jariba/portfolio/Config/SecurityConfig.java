package com.jariba.portfolio.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(customizer -> customizer.disable())
                 .authorizeHttpRequests(customizer -> customizer.requestMatchers(HttpMethod.valueOf("GET")).permitAll().anyRequest().authenticated())
                //.authorizeHttpRequests(customizer -> customizer.anyRequest().authenticated())
                //.oauth2Login(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .logout(customizer -> customizer.logoutUrl("/logout"))
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
