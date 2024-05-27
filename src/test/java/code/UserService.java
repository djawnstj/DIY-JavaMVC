package code;

import com.djawnstj.mvcframework.annotation.Autowired;
import com.djawnstj.mvcframework.annotation.Service;

import java.util.List;

@Service
public class UserService {
    public final UserRepository userRepository;
    public final PayService payService;

    public UserService() {
        this.userRepository = new UserRepository();
        this.payService = null;
    }

    @Autowired
    public UserService(final UserRepository userRepository, final PayService payService) {
        this.userRepository = userRepository;
        this.payService = payService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void register(final String id, final String pw) {
        this.userRepository.save(id, new User(id, pw));
    }

    public List<User> getUsers() {
        return (List<User>) this.userRepository.findAll();
    }
}
