package com.djawnstj.mvcframework.web.repository;

import com.djawnstj.mvcframework.web.domain.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserRepository {
    private final List<User> users = new ArrayList<>();

    public void addUser(String id, String pw) {
        User user = new User(id, pw);
        users.add(user);
    }

    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }
}
