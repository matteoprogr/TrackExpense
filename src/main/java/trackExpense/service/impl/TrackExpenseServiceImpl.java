package trackExpense.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trackExpense.constants.Constants;
import trackExpense.dto.UserExpenseDto;
import trackExpense.exception.CatchAllException;
import trackExpense.exception.ExpensesNotFoundException;
import trackExpense.model.Expense;
import trackExpense.repository.TrackExpenseRepository;
import trackExpense.service.ITrackExpenseService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class TrackExpenseServiceImpl implements ITrackExpenseService {

    TrackExpenseRepository trackExpenseRepository;

    @Autowired
    public TrackExpenseServiceImpl(TrackExpenseRepository trackExpenseRepository) {
        this.trackExpenseRepository = trackExpenseRepository;
    }

    @Override
    public void saveExpense(UserExpenseDto expense) {
        if (expense != null) {
            Expense newExpense = new Expense();
            newExpense.setIdExpense(UUID.randomUUID().toString());
            newExpense.setUsername(expense.getUsername());
            newExpense.setDescription(expense.getDescription());
            newExpense.setAmount(expense.getAmount());
            newExpense.setCategory(expense.getCategory());
            newExpense.setExpenseDate(new Date());
            trackExpenseRepository.save(newExpense);
        } else {
            throw new IllegalArgumentException(Constants.EXPENSE_CANT_BE_NULL);
        }
    }

    @Override
    public void deleteExpense(UserExpenseDto expense) {
        if (expense != null) {
            Expense newExpense = new Expense();
            newExpense.setIdExpense(expense.getIdExpense());
            newExpense.setUsername(expense.getUsername());
            newExpense.setDescription(expense.getDescription());
            newExpense.setAmount(expense.getAmount());
            newExpense.setCategory(expense.getCategory());
            newExpense.setExpenseDate(expense.getExpenseDate());
            trackExpenseRepository.delete(newExpense);
        } else {
            throw new IllegalArgumentException(Constants.EXPENSE_CANT_BE_NULL);
        }
    }

    @Override
    public void updateExpense(UserExpenseDto expense) {
        if (expense != null) {
            Expense newExpense = trackExpenseRepository.findByIdExpense(expense.getIdExpense()).orElseThrow();

            newExpense.setUsername(expense.getUsername());
            newExpense.setDescription(expense.getDescription());
            newExpense.setAmount(expense.getAmount());
            newExpense.setCategory(expense.getCategory());
            newExpense.setExpenseDate(expense.getExpenseDate());
            trackExpenseRepository.save(newExpense);
        } else {
            throw new IllegalArgumentException(Constants.EXPENSE_CANT_BE_NULL);
        }
    }

    @Override
    public UserExpenseDto getExpenses(String username, Date startDate, Date endDate) {

        try{
            List<Expense> expenses = trackExpenseRepository.findByUsernameAndExpenseDateBetween(username, startDate, endDate);
            if(expenses.isEmpty()){
                log.error("Expenses not found for user: " + username + " between " + startDate + " and " + endDate);
                ExpensesNotFoundException expensesNotFoundException = new ExpensesNotFoundException(username, startDate, endDate);
                throw new CatchAllException(expensesNotFoundException.getMessage(), expensesNotFoundException);
            }
            UserExpenseDto userExpenseDto = new UserExpenseDto();
            userExpenseDto.setUsername(username);
            userExpenseDto.setExpenses(expenses);
            userExpenseDto.setTotalExpense(expenses.stream().mapToDouble(Expense::getAmount).sum());
            userExpenseDto.setStartDate(startDate);
            userExpenseDto.setEndDate(endDate);
            return userExpenseDto;
        } catch (Exception e) {
            throw new IllegalArgumentException(Constants.USER_NOT_FOUND);
        }
    }

}
