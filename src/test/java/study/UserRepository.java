package study;

import java.util.HashMap;

public class UserRepository {

    private final HashMap<Long, User> userRepository;

    public UserRepository(HashMap<Long, User> userRepository) {
        this.userRepository = userRepository;
    }

    User findUser(Long id) {
        return userRepository.get(id);
    }

    void registerUser(User user) {
        this.userRepository.put(user.getId(), user);
    }

}
