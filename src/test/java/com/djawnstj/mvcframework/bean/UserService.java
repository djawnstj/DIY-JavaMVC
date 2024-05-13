package com.djawnstj.mvcframework.bean;

import com.djawnstj.mvcframework.annotation.AutoWired;
import com.djawnstj.mvcframework.annotation.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    @AutoWired
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(final String id, final String pw) {
        this.userRepository.save(id, new User(id, pw));
    }

    public List<User> getUsers() {
        return (List<User>) this.userRepository.findAll();
    }
}
