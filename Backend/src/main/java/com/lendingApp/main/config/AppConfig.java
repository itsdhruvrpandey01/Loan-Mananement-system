package com.lendingApp.main.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.lendingApp.main.helper.LoanCalculator;

@Configuration
public class AppConfig {

    @Bean
    ModelMapper mapper() {
        return new ModelMapper();
    }
    
    @Bean
    Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dbub9myft",
            "api_key", "822599316929669",
            "api_secret", "IWOae5drHTpWEjo3oJ2yvyzznEk"
        ));
    }
    
    @Bean
    public LoanCalculator loanCalculator() {
        return new LoanCalculator();
    }
}
