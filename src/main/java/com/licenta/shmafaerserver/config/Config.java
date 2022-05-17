package com.licenta.shmafaerserver.config;

import com.licenta.shmafaerserver.security.jwt.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

    @Bean
    public ModelMapper modelMapper()
    {

        return new ModelMapper();

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {

        return new BCryptPasswordEncoder();

    }

    @Bean
    public JwtUtils jwtUtils()
    {

        return new JwtUtils();

    }

    @Bean
    public RestTemplate restTemplate()
    {

        return new RestTemplate();

    }
}
