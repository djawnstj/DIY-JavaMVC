package study;

import code.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.code.*;
import study.code.PayService;
import study.code.User;
import study.code.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PayServiceTest {

    private UserRepository userRepository = new UserRepository();
    private ItemRepository itemRepository = new ItemRepository();
    private PayService payService = new PayService(userRepository);
    private BuyService buyService = new BuyService(itemRepository, userRepository);

    @Test
    void addMoney() {
        // given
        int original = 0;
        User user = new User(1L, new Pay(1, original));
        userRepository.save(user);
        int added = 1000;

        // when
        payService.charge(user.getId(), added);

        // then
        assertThat(user.getPay().getBalance()).isEqualTo(original + added);
    }

    @Test
    @DisplayName("구매 성공")
    void buyItemSuccess() {
        // given
        int original = 0;
        int itemPrice = 2000;
        User user = new User(1L, new Pay(1, original));
        Item item = new Item(1L, itemPrice, "test");
        userRepository.save(user);
        itemRepository.add(item);
        int added = 4000;

        // when
        payService.charge(1L, added);
        buyService.buy(user.getId(), item.getId());

        // then
        assertThat(user.getPay().getBalance()).isEqualTo(added - itemPrice);
    }

    @Test
    @DisplayName("구매 실패")
    void buyItemFail() {
        // given
        int original = 0;
        int itemPrice = 2000;
        User user = new User(1L, new Pay(1, original));
        Item item = new Item(1L, itemPrice, "test");
        userRepository.save(user);
        itemRepository.add(item);
        int added = 1000;
        payService.charge(1L, added);

        // when
        // then
        assertThrows(RuntimeException.class, () -> {
            buyService.buy(user.getId(), item.getId());
        });

    }
}
