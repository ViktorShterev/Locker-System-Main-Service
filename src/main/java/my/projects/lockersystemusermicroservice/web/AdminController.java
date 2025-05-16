package my.projects.lockersystemusermicroservice.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public ModelAndView adminDashboard() {
        return new ModelAndView("admin-dashboard");
    }
}
