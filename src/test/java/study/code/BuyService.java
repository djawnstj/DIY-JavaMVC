package study.code;

public class BuyService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public BuyService(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public boolean buy(long userId, long itemId){
        User user = userRepository.findById(userId);
        Item item = itemRepository.findById(itemId);

        boolean able = user.getPay().getBalance() >= item.getPrice();

        if(able){
            user.getPay().afterBuying(item.getPrice());
        } else {
            throw new RuntimeException("구매 금액 부족");
        }

        return able;
    }
}
