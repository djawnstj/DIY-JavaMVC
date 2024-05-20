import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.code.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PayServiceTest {
    PayService payService;
    UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        userRepository = new UserRepository();
        payService = new PayService(userRepository);
    }

    @Test
    @DisplayName("입력된 금액을 계좌에 충전한다.")
    public void charge() {
        //given
        User user = userRepository.addUser();
        long userId = user.getId();
        int amount = 1000;

        //when
        long result = payService.charge(userId, amount);

        //then
        assertThat(result).isEqualTo(amount);
    }

    @Test
    @DisplayName("잔액보다 저렴한 상품을 구매한다.")
    public void buyProduct() {
        //given
        User user = userRepository.addUser();
        long userId = user.getId();
        long balance = 1000; // 잔고
        user.getPay().setBalance(balance);

        int price = 500; // 상품 가격
        Product product = new Product(1, price, "풍선껌");

        //when
        payService.buyProduct(userId, product);

        //then
        assertThat(user.getPay().getBalance()).isEqualTo(balance - price);

    }

    @Test
    @DisplayName("잔액이 부족하여 상품을 구매할 수 없다.")
    public void buyProductException() {
        //given
        User user = userRepository.addUser();
        long userId = user.getId();
        long balance = 1000; // 잔고
        user.getPay().setBalance(balance);

        int price = 2000; // 상품 가격
        Product product = new Product(1, price, "풍선껌");

        //when then
        assertThatThrownBy(() -> payService.buyProduct(userId, product))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잔액이 충분하지 않습니다.");

    }
}
