package my.projects.lockersystemusermicroservice.web;

import jakarta.validation.Valid;
import my.projects.lockersystemusermicroservice.dto.ReceivePackageDTO;
import my.projects.lockersystemusermicroservice.dto.restDto.PackageDTO;
import my.projects.lockersystemusermicroservice.entity.UserDetails;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerDashboardController {

    private final RestClient restClient;

    public CustomerDashboardController(@Qualifier("packageRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    @GetMapping("/dashboard")
    public ModelAndView customerDashboard() {
        return new ModelAndView("customer-dashboard");
    }

    @GetMapping("/receive-package")
    public ModelAndView receivePackage(@ModelAttribute("receivePackageDTO") ReceivePackageDTO receivePackageDTO,
                                       @AuthenticationPrincipal UserDetails principal) {

        receivePackageDTO.setRecipientEmail(principal.getUsername());

        ModelAndView modelAndView = new ModelAndView("receive-package");
        modelAndView.addObject("receivePackageDTO", receivePackageDTO);

        return modelAndView;
    }

    @PostMapping("/receive-package")
    public ModelAndView receivePackage(@ModelAttribute("receivePackageDTO") @Valid ReceivePackageDTO receivePackageDTO,
                                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("receive-package");
        }

        Boolean success = this.restClient.post()
                .uri("http://localhost:8082/packages/receive-package")
                .body(receivePackageDTO)
                .retrieve()
                .body(Boolean.class);

        if (Boolean.TRUE.equals(success)) {
            return new ModelAndView("redirect:/customer/dashboard");
        }

        return new ModelAndView("error-rest");
    }

    @GetMapping("/view-packages")
    public ModelAndView viewPackages(@AuthenticationPrincipal UserDetails principal) {

        List<PackageDTO> packageDTOList = this.restClient.get()
                .uri("http://localhost:8082/packages/view-packages?email=" + UriUtils.encode(principal.getUsername(), StandardCharsets.UTF_8))
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });


        ModelAndView modelAndView = new ModelAndView("customer-packages");
        modelAndView.addObject("packageDTOList", packageDTOList);

        return modelAndView;
    }
}
