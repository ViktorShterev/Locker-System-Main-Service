package my.projects.lockersystemusermicroservice.service.impl;

import my.projects.lockersystemusermicroservice.dto.restDto.LockerLocationDTO;
import my.projects.lockersystemusermicroservice.service.LockerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class LockerServiceImpl implements LockerService {

    private final RestClient restClient;

    public LockerServiceImpl(@Qualifier("lockerRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public List<LockerLocationDTO> getLockerLocations() {
         return this.restClient.get()
                .uri("http://localhost:8081/lockers/locations")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
}
