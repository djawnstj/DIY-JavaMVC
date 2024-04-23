package study;

import java.util.HashMap;

public class PayService {

    User pay(User user, Product product) {

        UserRepository userRepository = new UserRepository(new HashMap<>());
        ProductRepository productRepository = new ProductRepository(new HashMap<>());
        userRepository.registerUser(user);
        productRepository.registerProduct(product);

        User findUser = userRepository.findUser(user.getId());
        Product findProduct = productRepository.findProduct(product.getId());

        try {
            long balance = findUser.getPay().getBalance();
            long price = findProduct.getPrice();
                
            if (price > balance) {
                System.out.println("잔액이 부족합니다.");
            }

            if (balance >= price) {
                long balanceAfterPay = balance - price;
                findUser.getPay().setBalance(balanceAfterPay);
                return findUser;
            }

        } catch (NullPointerException ex) {
            ex.printStackTrace();
            return user;
        }

        return findUser;
    }
    
}