package com.studentcardsapi.security.config;


import com.studentcardsapi.enums.AppUserRole;
import com.studentcardsapi.security.filter.CustomAuthenticationFilter;
import com.studentcardsapi.security.filter.CustomAuthorizationFilter;
import com.studentcardsapi.service.interfaces.AppUserService;
import com.studentcardsapi.service.interfaces.TokenManagerService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static com.studentcardsapi.utils.constants.EndpointConstants.*;
import static com.studentcardsapi.utils.constants.TokenConstants.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private AppUserService userService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private TokenManagerService tokenManagerService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(), tokenManagerService);
        customAuthenticationFilter.setFilterProcessesUrl(API + LOGIN);

        http.cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        NO_TOKEN_ENDPOINTS.forEach(
                endpoint -> endpoint += PERMIT_ALL_AFTER
        );

        http.authorizeRequests().antMatchers(NO_TOKEN_ENDPOINTS.toArray(new String[0])).permitAll();

//      We need to look after this part of the http configuration. Take care!
        http.authorizeRequests().antMatchers(POST, API + SUBJECT + CREATION).hasAnyAuthority(AppUserRole.COORDINATOR.name());
        http.authorizeRequests().antMatchers(GET, API + SUBJECT + ALL_AFTER + LIST_ALL).hasAnyAuthority(
                AppUserRole.COORDINATOR.name(),
                AppUserRole.ASSISTANT.name(),
                AppUserRole.STUDENT.name(),
                AppUserRole.PROFESSOR.name()
        );

        http.authorizeRequests().anyRequest().authenticated();

        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(tokenManagerService), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(SWAGGER_ENDPOINTS.toArray(new String[0]));
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        source.registerCorsConfiguration(PERMIT_ALL_AFTER, configuration);
        return source;
    }
}
