package invoices.task.model;

import invoices.task.model.source.Currency;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "user")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "balance")
    private Double balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;

    @OneToMany(mappedBy = "oneUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Invoice> invoice_id;

    public User(Double balance, Currency currency){
        this.balance = balance;
        this.currency = currency;
    }
}
/*
create table user(
    id bigint auto_increment primary key ,
    balance double not null ,
    currency varchar(20));
 */