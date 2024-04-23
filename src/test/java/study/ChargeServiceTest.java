package study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChargeServiceTest {
    ChargeService chargeService = new ChargeService();

    @Test
    @DisplayName("잔액 충전")
    void chargeTest() {
        long userNo = 1;
        final long account = 123456789;
        final long initBalance = 0;
        final Pay initPay = new Pay(account, initBalance);

        long money = 20000;
        
        User user = chargeService.charge(new User(userNo, initPay), money);
        
        Assertions.assertThat(user.getPay().getBalance()).isEqualTo(money);
    }
}
