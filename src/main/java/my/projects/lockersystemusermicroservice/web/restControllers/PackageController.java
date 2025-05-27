package my.projects.lockersystemusermicroservice.web.restControllers;

import jakarta.validation.Valid;
import my.projects.lockersystemusermicroservice.dto.restDto.CreatePackageDTO;
import my.projects.lockersystemusermicroservice.dto.restDto.LockerLocationDTO;
import my.projects.lockersystemusermicroservice.entity.UserDetails;
import my.projects.lockersystemusermicroservice.service.LockerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/packages")
public class PackageController {

    private final RestClient restClient;
    private final LockerService lockerService;

    public PackageController(@Qualifier("packageRestClient") RestClient restClient, LockerService lockerService) {
        this.restClient = restClient;
        this.lockerService = lockerService;
    }

    @GetMapping("/create-package")
    public ModelAndView createPackage(@ModelAttribute("createPackageDTO") CreatePackageDTO createPackageDTO) {

        List<LockerLocationDTO> lockerLocations = this.lockerService.getLockerLocations();

        ModelAndView modelAndView = new ModelAndView("create-package");
        modelAndView.addObject("createPackageDTO", createPackageDTO);
        modelAndView.addObject("locations", lockerLocations);

        return modelAndView;
    }

    @PostMapping("/create-package")
    public ModelAndView placePackage(@ModelAttribute("createPackageDTO") @Valid CreatePackageDTO createPackageDTO,
                                     BindingResult bindingResult,
                                     @AuthenticationPrincipal UserDetails principal) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("create-package");
        }

        createPackageDTO.setCourierEmail(principal.getUsername());

        Boolean success = this.restClient.post()
                .uri("http://localhost:8082/packages/create-package")
                .body(createPackageDTO)
                .retrieve()
                .body(Boolean.class);

        if (Boolean.TRUE.equals(success)) {
            return new ModelAndView("redirect:/courier/dashboard");
        }

        return new ModelAndView("error-rest");
    }
}
