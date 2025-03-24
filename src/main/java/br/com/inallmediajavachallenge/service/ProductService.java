package br.com.inallmediajavachallenge.service;

import br.com.inallmediajavachallenge.exception.InvalidPriceRangeException;
import br.com.inallmediajavachallenge.model.Product;
import br.com.inallmediajavachallenge.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;


    public Product createProduct(Product productRequest) { //Logic to send to messaging or other services
        return productRepository.save(productRequest);
    }

    public List<Product> getProductsByPriceRange(Double initialRange, Double finalRange) {
        if (finalRange < initialRange) {
            log.warn("Request with invalid price range: initial range: {}, final range: {}", initialRange, finalRange);
            throw new InvalidPriceRangeException("Final range cannot be lower than initial range");
        }
        return productRepository.findByPriceBetween(initialRange, finalRange);
    }

    public List<Product> getAllProductsSortedByPrice() {
        return productRepository.findAll(Sort.by(Sort.Direction.ASC, "price"));
    }
}
