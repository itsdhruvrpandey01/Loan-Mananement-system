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
            "cloud_name", "your_cloud",
            "api_key", "your_api",
            "api_secret", "your_secret_key"
        ));
    }
    
    @Bean
    public LoanCalculator loanCalculator() {
        return new LoanCalculator();
    }
}
