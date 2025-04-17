package my.projects.lockersystemusermicroservice.web;

import jakarta.validation.Valid;
import my.projects.lockersystemusermicroservice.dto.RegisterUserDTO;
import my.projects.lockersystemusermicroservice.service.AuthenticationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/register")
    public ModelAndView register(@ModelAttribute("registerUser") RegisterUserDTO registerUser) {
        return new ModelAndView("register");
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute("registerUser")
                                 @Valid RegisterUserDTO registerUser,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("register");
        }

        boolean isSuccessfulRegistered = this.authenticationService.register(registerUser);

        if (isSuccessfulRegistered) {
            return new ModelAndView("redirect:/login");
        }

        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("hasErrors", true);

        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @PostMapping("/login-error")
    public String onFailure(@ModelAttribute("email") String email,
                            Model model) {

        model.addAttribute("email", email);
        model.addAttribute("bad_credentials", "true");

        return "login";
    }
}
