package com.hw.demoweb.repository;

import com.hw.demoweb.model.Product;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class ProductRepository {

    private Map<Long, Product> products;

    @PostConstruct
    public void init() {
        products = Stream.of(Product.builder().id(1L).title("Milk").cost(10.1).build(),
                             Product.builder().id(2L).title("Bread").cost(5.1).build(),
                             Product.builder().id(3L).title("Apple").cost(17.7).build(),
                             Product.builder().id(4L).title("Chocolate").cost(33.9).build(),
                             Product.builder().id(5L).title("Tea").cost(55.0).build())
                         .collect(Collectors.toMap(Product::getId, Function.identity()));
    }

    public List<Product> getAllProducts() {
        return List.copyOf(products.values());
    }

    public synchronized Product save(Product newProduct) {
        long newId = generateId();
        newProduct.setId(newId);
        products.put(newId, newProduct);
        return newProduct;
    }

    public synchronized void delete(Long id) {
        Optional<Product> product = getProductById(id);
        if (product.isPresent()) {
            products.remove(id);
        }
    }

    public synchronized void update(Long id, Product newProduct) {
        Optional<Product> productOpt = getProductById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setTitle(newProduct.getTitle());
            product.setCost(newProduct.getCost());
        }
    }

    public Optional<Product> getProductById(Long id) {
        return Optional.ofNullable(products.get(id));
    }

    private Long generateId() {
        long id = products.size() + 1;
        while (products.containsKey(id)) {
            id++;
        }
        return id;
    }
}
