package study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class ChargeService {

    @Test
    @DisplayName("잔액 충전")
    void charge() {

        long money = 20000;

        long userNo = 1;
        final long account = 123456789;
        final long initBalance = 0;
        final Pay initPay = new Pay(account, initBalance);
        final HashMap<Long, User> userHashMap = new HashMap<>();

        userHashMap.put(userNo, new User(userNo, initPay));
        UserRepository userRepository = new UserRepository(userHashMap);

        User user = userRepository.findUser(userNo);

        long balance = user.getPay().getBalance() + money;
        user.getPay().setBalance(balance);

        System.out.println("user balance after charge: " + user.getPay().getBalance());
    }

}
