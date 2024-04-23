package study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PayServiceTest {
    PayService payService = new PayService();

    @Test
    @DisplayName("상품 구매")
    void pay() {
        long userNo = 2;
        long account = 123123123;
        long balance = 100000;
        Pay userPay = new Pay(account, balance);

        long productNo = 12;
        long price = 10000;
        String description = "타이레놀";

        User user = new User(userNo, userPay);
        Product product = new Product(productNo, price, description);

        User result = payService.pay(user, product);

        Assertions.assertThat(result.getPay().getBalance()).isEqualTo(balance - price);
    }
}
