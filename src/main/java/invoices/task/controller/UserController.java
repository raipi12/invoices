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
    @GetMapping("/user/balance/add")
    public String addBalanceParam(){
        return "balance/add-balance";
    }
    @PostMapping("/user/balance/add")
    public String addBalance(@RequestParam Double amount, @RequestParam Currency currency){

        if(amount < 0 || currency == null)
            return "redirect:/user/balance";
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
