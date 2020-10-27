package invoices.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String homeA(Model model) {
        model.addAttribute("title", "Main page");
        return "main";
    }
}