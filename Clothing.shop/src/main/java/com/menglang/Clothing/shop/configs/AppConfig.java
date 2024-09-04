package com.menglang.Clothing.shop.configs;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // learn more about objectMapper (Deserialize and Serialize JSON to Java Object vice verse)
    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper obj=new ObjectMapper();
        obj.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        obj.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        obj.registerModule(new JavaTimeModule());
        obj.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return obj;
    }
}
