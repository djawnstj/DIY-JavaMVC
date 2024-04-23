package study;

import java.util.HashMap;

public class ProductRepository {
    
    private final HashMap<Long, Product> productRepository;

    public ProductRepository(HashMap<Long, Product> productRepository) {
        this.productRepository = productRepository;
    }

    Product findProduct(Long id) {
        return productRepository.get(id);
    }

    void registerProduct(Product product) {
        this.productRepository.put(product.getId(), product);
    }

}
