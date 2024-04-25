package com.djawnstj.mvcframework.boot.web.servlet.user;

import java.util.Collection;
import java.util.HashMap;

public class UserRepository {
    private static UserRepository instance;
    private HashMap<String, User> userRepository;
    

    private UserRepository() {
        this.userRepository = new HashMap<String, User>();
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public void registerUser(User user) {
        userRepository.put(user.getId(), user);
    }

    public Collection<User> findAllUsers() {
        return userRepository.values();
    }

}
