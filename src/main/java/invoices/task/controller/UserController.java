package invoices.task.controller;

import invoices.task.model.User;
import invoices.task.model.source.Currency;
import invoices.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/balance")
    public String showBalance(Model model){
        List<User> users = userService.findAll();

        if (users.size() == 0){
            return "exception/balance-exception";
        }
        User user = users.get(users.size() - 1);

        Double balance = user.getBalance();

        model.addAttribute("balance", balance);
        return "balance/balance";
    }

}
