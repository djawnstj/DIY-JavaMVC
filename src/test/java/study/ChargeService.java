package study;

import java.util.HashMap;

public class ChargeService {

    User charge(User user, long money) {

        UserRepository userRepository = new UserRepository(new HashMap<>());
        userRepository.registerUser(user);
        
        User userWhoFound = userRepository.findUser(user.getId());

        try {
            long balance = userWhoFound.getPay().getBalance() + money;
            userWhoFound.getPay().setBalance(balance);
            
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            return user;
        }

        return userWhoFound;
    }

}
