package invoices.task.service;

import invoices.task.model.Invoice;
import invoices.task.repository.InvoiceRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository){
        this.invoiceRepository = invoiceRepository;
    }

    public List<Invoice> findAll(){
        return invoiceRepository.findAll();
    }
    public Invoice saveInvoice(Invoice invoice){
        return invoiceRepository.save(invoice);
    }
    public void deleteById(Long id){
        invoiceRepository.deleteById(id);
    }
}