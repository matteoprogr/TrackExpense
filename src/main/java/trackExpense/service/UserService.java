package trackExpense.service;

import trackExpense.model.User;

public interface UserService {
    User saveUser(User user);
    User login(String username, String password);
}
