package code;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private static Map<Long, User> users = new HashMap<>();

    public void save(User user) {
        users.put(user.getId(), user);
    }

    public User findById(long userId) {
        return users.get(userId);
    }
}
