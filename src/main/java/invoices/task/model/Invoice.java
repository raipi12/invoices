package invoices.task.model;

import invoices.task.model.source.Currency;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private Long invoice_id;

    @Column(name = "invoice_name")
    private String invoiceName;

    @Column(name = "remainder")
    private Double remainder;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "time")
    @CreatedDate
    private Date time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User oneUser;

    public Invoice(String invoiceName, Double remainder, Currency currency) {
        this.invoiceName = invoiceName;
        this.remainder = remainder;
        this.currency = currency;
    }
}
/*
create table invoice(
    invoice_id bigint auto_increment primary key ,
    invoice_name varchar(255) not null ,
    remainder double not null ,
    currency varchar(20) not null ,
    time timestamp default CURRENT_TIMESTAMP,
    user_id bigint,
    foreign key (user_id) references user(id)
);
 */