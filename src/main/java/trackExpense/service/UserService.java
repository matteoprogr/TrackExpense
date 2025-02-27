package trackExpense.service;

import trackExpense.dto.UserDto;
import trackExpense.model.User;

public interface UserService {
    User saveUser(UserDto userDto);
    User login(String username, String password);
}
