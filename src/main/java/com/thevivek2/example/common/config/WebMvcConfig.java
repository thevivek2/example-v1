package com.thevivek2.example.common.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import static com.thevivek2.example.common.constant.SystemConstant.DATE_FORMATTER;
import static com.thevivek2.example.common.constant.SystemConstant.DATE_TIME_FORMATTER;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return configure -> configure
                .serializersByType(Map.of(LocalDate.class, new LocalDateSerializer(DATE_FORMATTER),
                        LocalDateTime.class, new LocalDateTimeSerializer(DATE_TIME_FORMATTER)))
                .deserializersByType(Map.of(LocalDate.class, new LocalDateDeserializer(DATE_FORMATTER),
                        LocalDateTime.class, new LocalDateTimeDeserializer(DATE_TIME_FORMATTER)));
    }

}
