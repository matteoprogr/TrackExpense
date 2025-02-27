package trackExpense.service;

import trackExpense.dto.UserExpenseDto;
import java.util.Date;

public interface ITrackExpenseService {

    void saveExpense(UserExpenseDto expense);
    void deleteExpense(String idExpense);
    void updateExpense(UserExpenseDto expense);
    UserExpenseDto getExpenses(String username, Date startDate, Date endDate);
}
