package com.djawnstj.common;

import com.djawnstj.mvcframework.annotation.Repository;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Repository
public class UserRepository {
    private final Map<String, User> users = new LinkedHashMap<>();

    public void save(final String id, final User user) {
        users.put(id, user);
    }

    public Collection<User> findAll() {
        return users.values();
    }
}
