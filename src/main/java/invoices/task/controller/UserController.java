package invoices.task.controller;

import invoices.task.model.User;
import invoices.task.model.source.Currency;
import invoices.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/balance")
    public String showBalance(Model model){
        List<User> users = userService.findAll();

        if (users.size() == 0)
            return "exception/balance-exception";

        User user = users.get(users.size() - 1);

        double balance = Math.round(user.getBalance() * 100) / 100.0;

        model.addAttribute("balance", balance);
        return "balance/balance";
    }
    @GetMapping("/balance/add")
    public String addBalanceParam(){
        return "balance/add-balance";
    }
    @PostMapping("/balance/add")
    public String addBalance(@RequestParam Double amount, @RequestParam Currency currency){

        if(amount < 0 || currency == null)
            return "exception/invoice-exception";
        else if(currency == Currency.EURO)
            amount *= Currency.EURO.getExchangeRate();
        else if (currency == Currency.DOLLARS)
            amount *= Currency.DOLLARS.getExchangeRate();
        else if (currency == Currency.MD)
            amount += Currency.MD.getExchangeRate();

        List<User> users = userService.findAll();

        if (users.size() == 0){
            User newUser = new User(amount, currency);
            users.add(newUser);
            userService.saveUser(newUser);
        }else {
            Double balance = users.get(users.size() - 1).getBalance() + amount;
            User user = new User(balance, currency);
            userService.saveUser(user);
        }
        return "redirect:/user/balance";
    }
}
