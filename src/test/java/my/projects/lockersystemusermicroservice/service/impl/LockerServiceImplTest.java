package my.projects.lockersystemusermicroservice.service.impl;

import my.projects.lockersystemusermicroservice.dto.restDto.LockerLocationDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LockerServiceImplTest {

    @Mock
    @Qualifier("lockerRestClient")
    RestClient restClient;

    @InjectMocks
    LockerServiceImpl lockerService;

    @Mock
    RestClient.RequestHeadersUriSpec requestSpec;

    @Mock
    RestClient.RequestHeadersSpec headersSpec;

    @Mock
    RestClient.ResponseSpec responseSpec;

    @Test
    void testGetLockerLocations_success() {

        when(restClient.get()).thenReturn(requestSpec);
        when(requestSpec.uri(anyString())).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);

        List<LockerLocationDTO> mockResponse = List.of(
                new LockerLocationDTO("Location1"),
                new LockerLocationDTO("Location2")
        );
        when(responseSpec.body(any(ParameterizedTypeReference.class))).thenReturn(mockResponse);

        LockerServiceImpl service = new LockerServiceImpl(restClient);
        List<LockerLocationDTO> result = service.getLockerLocations();

        assertEquals(2, result.size());
        assertEquals("Location1", result.get(0).getLocation());
    }

    @Test
    void testGetLockerLocations_failure() {
        when(restClient.get()).thenReturn(requestSpec);
        when(requestSpec.uri("http://localhost:8081/lockers/locations")).thenReturn(requestSpec);
        when(requestSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(any(ParameterizedTypeReference.class))).thenThrow(new RuntimeException("Service Unavailable"));

        assertThrows(RuntimeException.class, () -> lockerService.getLockerLocations());
        verify(restClient).get();
    }
}
