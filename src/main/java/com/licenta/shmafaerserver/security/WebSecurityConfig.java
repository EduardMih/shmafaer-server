package com.licenta.shmafaerserver.security;

import com.licenta.shmafaerserver.security.jwt.AuthEntryPointJwt;
import com.licenta.shmafaerserver.security.jwt.AuthTokenFilter;
import com.licenta.shmafaerserver.security.jwt.JwtUtils;
import com.licenta.shmafaerserver.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtUtils jwtUtils;

    @Qualifier("userDetailsServiceImpl") private final UserDetailsService userDetailsService;

    private final AuthEntryPointJwt unauthorizedHandler;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/login").permitAll()
                .antMatchers("/refreshToken").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/confirmAccount/**").permitAll()
                .antMatchers("/resetPassword/**").permitAll()
                //.antMatchers("/projects/**").permitAll()
                .antMatchers("/users/createUser").hasAuthority("ADMIN")
                .antMatchers("/users/update/roles").hasAuthority("ADMIN")
                .antMatchers("/users/**").permitAll()
                .antMatchers("/test/**").permitAll()
                .antMatchers(HttpMethod.POST, "/projects").hasAnyAuthority("STUDENT", "PROFESSOR", "ADMIN")
                .antMatchers("/projectsStats/**").authenticated()
                //.antMatchers("/ratings/**").permitAll()
                .anyRequest().authenticated();


        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }



    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();

    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {

        return new AuthTokenFilter(jwtUtils, userDetailsService);

    }
}
