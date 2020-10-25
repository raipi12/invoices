package invoices.task.controller;

import invoices.task.model.Invoice;
import invoices.task.service.InvoiceService;
import invoices.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
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
        Iterable<Invoice> invoices = invoiceService.findAll();

        model.addAttribute("invoices", invoices);
        return "invoice/invoices";
    }

}