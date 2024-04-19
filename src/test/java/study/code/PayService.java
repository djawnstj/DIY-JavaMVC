package study.code;

public class PayService {

    private final UserRepository userRepository;

    public PayService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void charge(long userId, long money) {
        User user = userRepository.findById(userId);

        user.getPay().add(money);
    }
}
