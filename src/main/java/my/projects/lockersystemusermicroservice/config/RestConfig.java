package my.projects.lockersystemusermicroservice.config;

import my.projects.lockersystemusermicroservice.service.AuthenticationService;
import my.projects.lockersystemusermicroservice.service.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Configuration
public class RestConfig {

    @Bean("lockerRestClient")
    public RestClient lockerRestClient(LockerApiConfig lockerApiConfig,
                                       ClientHttpRequestInterceptor requestInterceptor) {
        return RestClient
                .builder()
                .baseUrl(lockerApiConfig.getBaseUrl())
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .requestInterceptor(requestInterceptor)
                .build();
    }

    @Bean("packageRestClient")
    public RestClient packageRestClient(PackageApiConfig packageApiConfig,
                                       ClientHttpRequestInterceptor requestInterceptor) {
        return RestClient
                .builder()
                .baseUrl(packageApiConfig.getBaseUrl())
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .requestInterceptor(requestInterceptor)
                .build();
    }

    @Bean
    public ClientHttpRequestInterceptor requestInterceptor(AuthenticationService authenticationService,
                                                           JwtService jwtService) {
        return (request, body, execution) -> {
            // put the logged user details into bearer token
            authenticationService
                    .getCurrentUser()
                    .ifPresent(userDetails -> {
                        String bearerToken = jwtService.generateToken(
                                userDetails.getUuid().toString(),
                                Map.of(
                                        "roles",
                                        userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
                                )
                        );
                        System.out.println("BEARER TOKEN: " + bearerToken);

                        request.getHeaders().setBearerAuth(bearerToken);
                    });

            return execution.execute(request, body);
        };
    }
}
