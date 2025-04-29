package my.projects.lockersystemusermicroservice.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CourierDashboard {

    @GetMapping("/courier-dashboard")
    public ModelAndView courierDashboard() {
        return new ModelAndView("courier-dashboard");
    }
}
