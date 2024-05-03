package study.reflection.bean;

import com.djawnstj.mvcframework.bean.annotation.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
