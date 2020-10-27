package invoices.task.controller;

import invoices.task.model.Invoice;
import invoices.task.model.User;
import invoices.task.model.source.Currency;
import invoices.task.service.InvoiceService;
import invoices.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;


@RestController
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final UserService userService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService, UserService userService) {
        this.invoiceService = invoiceService;
        this.userService = userService;
    }

    @GetMapping("/invoices")
    public String blog(Model model) {
        List<Invoice> invoices = invoiceService.findAll();

        model.addAttribute("invoices", invoices);
        return "invoice/invoices";
    }

    @GetMapping("/invoices/make")
    public String makingInvoiceForm() {
        return "invoice/make-invoice";
    }

    @PostMapping("/invoices/make")
    public String makingInvoice(@RequestParam Double amount, @RequestParam Currency currency) {

        if (amount < 0 || currency == null)
            return "exception/invoice-exception";


        Random random = new Random();
        String invoiceName = String.valueOf(random.nextInt(2147483647) + 45732);

        List<User> users = userService.findAll();

        if (users.size() == 0) {
            return "exception/balance-exception";
        }
        User user = users.get(users.size() - 1);

        double balance = user.getBalance();
        double remainder = 0.0;

        if (currency == Currency.DOLLARS){
            remainder = balance - (amount * Currency.DOLLARS.getExchangeRate());
        }else if(currency == Currency.EURO){
            remainder = balance - (amount * Currency.EURO.getExchangeRate());
        }else if (currency == Currency.MD) {
            remainder = balance - amount;
        }

        if (remainder < 0) {
            return "exception/balance-exception";
        }
        user.setBalance(remainder);

        Invoice invoice = new Invoice(invoiceName, Math.round(remainder * 100)/ 100.0, currency);
        invoice.setOneUser(user);
        invoiceService.saveInvoice(invoice);

        return "redirect:/invoices";
    }
}