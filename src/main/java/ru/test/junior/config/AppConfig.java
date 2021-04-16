package ru.test.junior.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.test.junior.dto.criteria.Criteria;

import java.util.List;
import java.util.Map;

@Configuration
@EnableConfigurationProperties
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JSR310Module());
        return objectMapper;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    @ConfigurationProperties(prefix = "criterias")
    public Properties properties() {
        return new Properties();
    }

    @Data
    public static class Properties {
        Map<Criteria, List<String>> values;
    }
}
