package com.djawnstj.mvcframework.boot.web.servlet;

import com.djawnstj.mvcframework.MvcApplicationMain;
import com.djawnstj.mvcframework.boot.web.servlet.user.User;
import com.djawnstj.mvcframework.boot.web.servlet.user.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class UserServletTest {

    @Test
    void FIND_ALL_USERS() {
        MvcApplicationMain.main(new String[0]);

        User user1 = new User("user1", "1234");
        User user2 = new User("user2", "1234");
        User user3 = new User("user3", "1234");
        UserRepository.save(user1);
        UserRepository.save(user2);
        UserRepository.save(user3);
        Collection<User> allUsers = UserRepository.findAll();

        int i = 1;
        for (User user : allUsers) {
            String userId = "user" + i;
            assertThat(user.getId()).isEqualTo(userId);
            assertThat(user.getPw()).isEqualTo("1234");
            i++;
        }
    }
}