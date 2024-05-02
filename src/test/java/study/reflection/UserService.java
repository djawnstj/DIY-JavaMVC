package study.reflection;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService() {
        userRepository = null;
    }
}

