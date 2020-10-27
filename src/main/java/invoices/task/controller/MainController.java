package invoices.task.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    public String homeA(Model model) {
        model.addAttribute("title", "Main page");
        return "main";
    }
}