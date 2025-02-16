package trackExpense.service;

import trackExpense.dto.UserExpenseDto;
import java.util.Date;

public interface ITrackExpenseService {

    public void saveExpense(UserExpenseDto expense);
    public void deleteExpense(String idExpense);
    public void updateExpense(UserExpenseDto expense);
    public UserExpenseDto getExpenses(String username, Date startDate, Date endDate);
}
