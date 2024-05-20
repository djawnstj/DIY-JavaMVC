package study.code;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private Map<Long, User> users = new HashMap<Long, User>();
    private static long sequence = 0L;

    // 회원 추가
    public User addUser() {
        long userId = ++sequence;
        Pay pay = new Pay(userId, 0);
        User user = new User(userId, pay);
        users.put(user.getId(), user);
        return user;
    }

    // 회원 조회
    public User findUserById(long id) {
        return users.get(id);
    }

}
