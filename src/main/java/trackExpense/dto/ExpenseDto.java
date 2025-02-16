package trackExpense.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExpenseDto {
    private String description;
    private double amount;
    private String category;
}
