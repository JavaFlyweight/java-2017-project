package pl.lodz.p.java.flyweight.model;

import java.util.Date;
import org.springframework.data.annotation.Id;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Budget {

    @Id
    private long id;
    private Set<Permission> permissions = new HashSet<Permission>(0);

    private Set<Expense> expenses = new HashSet<Expense>(0);
    private Set<Income> incomes = new HashSet<Income>(0);
    
    private double balance;
    private double plannedAmount;
    private Date dateFrom;
    private Date dateTo;
    
}
