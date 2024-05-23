package com.djawnstj.mvcframework.boot.web.servlet.user;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private static final Map<Long, User> users = new HashMap<>();
    static long userCount = 1L;

    public static void save(User user) {
        users.put(userCount++, user);
    }

    public static Collection<User> findAll() {
        return users.values();
    }

    public static User findById(long id) {
        return users.get(id);
    }
}
