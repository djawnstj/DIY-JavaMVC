package study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChargeServiceTest {
    private ChargeService service = new ChargeService();

    @Test
    @DisplayName("잔액 충전")
    void charge() {

        long userNo = 1;
        long account = 123456789;
        long initBalance = 0;
        Pay initPay = new Pay(account, initBalance);
    
        User user = new User(userNo, initPay);

        service.charge(user, 0);
    }
}
