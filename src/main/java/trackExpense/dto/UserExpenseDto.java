package trackExpense.dto;

import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import trackExpense.model.Expense;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserExpenseDto {

    private String username;
    private String idExpense;
    private List<Expense> expenses;
    private double totalExpense;
    private Date startDate;
    private Date endDate;

    // Fields for a new expense
    private String description;
    private double amount;
    private String category;
    private Date expenseDate;
}
