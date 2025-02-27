package trackExpense.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import trackExpense.model.User;
import trackExpense.repository.UserRepository;
import trackExpense.service.UserService;

@Service
@Slf4j
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (userRepository.findById(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists: " + user.getUsername());
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use: " + user.getEmail());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        log.info("Saving new user: {}", user.getUsername());
        return userRepository.save(user);
    }

    @Override
    public User login(String username, String password) {
        if (username == null || password == null) {
            throw new IllegalArgumentException("Username and password cannot be null");
        }

        User user = userRepository.findById(username)
                .orElseThrow(() -> {
                    log.error("Login attempt failed: user {} not found", username);
                    return new IllegalArgumentException("Invalid credentials");
                });

        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.error("Login attempt failed: incorrect password for user {}", username);
            throw new IllegalArgumentException("Invalid credentials");
        }

        log.info("Login successful for user: {}", username);

        return new User(
                user.getUsername(),
                null,
                user.getEmail(),
                user.getName(),
                user.getSurname(),
                user.getBirthday()
        );
    }
}
