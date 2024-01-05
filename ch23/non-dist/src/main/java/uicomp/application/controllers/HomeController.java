package uicomp.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
class HomeController {

    @GetMapping("/") String index(Model model) {
        model.addAttribute("productIdsInBasket", List.of("1", "2", "3"));
        return "index";
    }
}
