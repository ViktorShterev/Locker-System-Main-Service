package my.projects.lockersystemusermicroservice.web.restControllers;

import jakarta.validation.Valid;
import my.projects.lockersystemusermicroservice.dto.restDto.CreateLockerDTO;
import my.projects.lockersystemusermicroservice.dto.restDto.LockerLocationDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Controller
@RequestMapping("/lockers")
public class LockerController {

    private final RestClient restClient;

    public LockerController(@Qualifier("lockerRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    @GetMapping("/create-locker")
    public ModelAndView showCreateLockerForm() {
        return new ModelAndView("create-locker", "createLockerDTO", new CreateLockerDTO());
    }

    @PostMapping("/create-locker")
    public ModelAndView submitCreateLockerForm(
            @ModelAttribute("createLockerDTO") @Valid CreateLockerDTO createLockerDTO,
            BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("create-locker");
            return modelAndView;
        }

        Boolean success = this.restClient
                .post()
                .uri("http://localhost:8081/lockers/create-locker")
                .body(createLockerDTO)
                .retrieve()
                .body(Boolean.class);

        if (Boolean.TRUE.equals(success)) {
            modelAndView.setViewName("redirect:/admin/dashboard");

        } else {
            modelAndView.setViewName("error-rest");
        }
        return modelAndView;
    }

    @GetMapping("/locations")
    public ResponseEntity<LockerLocationDTO> getLockersLocations() {
        return this.restClient.get()
                .uri("http://localhost:8081/lockers/locations")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
    }

    @GetMapping("/availability")
    public ResponseEntity<Map<String, Integer>> getAvailableLockers(@RequestParam String location) {
        // Forward the request to the locker microservice
        return this.restClient.get()
                .uri("http://localhost:8081/lockers/availability?location=" + UriUtils.encode(location, StandardCharsets.UTF_8))
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
    }
}

