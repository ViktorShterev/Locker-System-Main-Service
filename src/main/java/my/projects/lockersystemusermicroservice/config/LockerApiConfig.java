package my.projects.lockersystemusermicroservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "locker.api")
public class LockerApiConfig {

    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

    public LockerApiConfig setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }
}
