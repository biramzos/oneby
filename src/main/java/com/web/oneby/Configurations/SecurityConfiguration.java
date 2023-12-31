package com.web.oneby.Configurations;

import com.web.oneby.Enums.HTTPMessage;
import com.web.oneby.Enums.Language;
import com.web.oneby.Filters.JWTFilter;
import com.web.oneby.Handlers.HTTPMessageHandler;
import com.web.oneby.Handlers.RestExceptionHandler;
import com.web.oneby.Services.UserService;
import com.web.oneby.Utils.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.Properties;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
    private static UserService userService;

    @Autowired
    public SecurityConfiguration(
            @Lazy UserService userService
    ){
        SecurityConfiguration.userService = userService;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(false);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("*/*", configuration);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService((UserDetailsService) SecurityConfiguration.userService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("b.ramis.2002@gmail.com");
        mailSender.setPassword("uhykyohzafgygmav");
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return mailSender;
    }

    @Bean
    public static JWTFilter authTokenFilter(){
        return new JWTFilter(SecurityConfiguration.userService);
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.cors().and().csrf().disable()
                .authenticationManager(authManager())
                .authorizeRequests().anyRequest().permitAll()
                .and()
                .userDetailsService(userService)
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", HttpMethod.GET.toString()))
                .deleteCookies("Authorization")
                .clearAuthentication(true)
                .permitAll()
                .and()
                .addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
