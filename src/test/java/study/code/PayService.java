package study.code;

public class PayService {

    private final UserRepository userRepository;
    public PayService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 잔액 충전하기
     */
    public long charge(long userId, int amount) {
        User user = userRepository.findUserById(userId);
        Pay pay = user.getPay();
        pay.setBalance(pay.getBalance() + amount); // 충전
        return pay.getBalance();
    }

    /**
     * 상품 구매하기
     */
    public void buyProduct(long userId, Product product) {
        User user = userRepository.findUserById(userId);
        Pay pay = user.getPay();
        long balance = pay.getBalance();
        long price = product.getPrice();

        if(balance < price) {
            throw new IllegalArgumentException("잔액이 충분하지 않습니다.");
        } else {
            pay.setBalance(balance - price); // 구매
        }
    }
}
