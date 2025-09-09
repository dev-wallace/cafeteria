package com.senac.cafeteria.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/css/**", "/js/**", "/images/**", "/images_homapage/**", 
                               "/webjars/**", "/favicon.ico").permitAll()
                .requestMatchers("/", "/menu", "/cadastro", "/login", "/about").permitAll()
                .requestMatchers("/carrinho/**", "/perfil").hasAuthority("ROLE_CLIENTE") // Alterado para hasAuthority
                .requestMatchers("/admin/**").hasAuthority("ROLE_FUNCIONARIO") // Alterado para hasAuthority
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(successHandler()) // Usando o successHandler personalizado
                .failureUrl("/login?error=true") // URL para falha de login
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/?logout=true")
                .invalidateHttpSession(true) // Invalida a sessão
                .deleteCookies("JSESSIONID") // Remove cookies
                .permitAll()
            )
            .csrf(csrf -> csrf.disable()); // Desabilitado para desenvolvimento

        return http.build();
    }

    // Handler personalizado para redirecionamento após login
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
        handler.setUseReferer(false);
        handler.setDefaultTargetUrl("/redirecionarPorRole");
        return handler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}