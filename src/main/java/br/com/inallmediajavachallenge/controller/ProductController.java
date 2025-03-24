package br.com.inallmediajavachallenge.controller;

import br.com.inallmediajavachallenge.model.Product;
import br.com.inallmediajavachallenge.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Slf4j
@AllArgsConstructor
public class ProductController {

    private final ProductService productService; //Best practice using constructor over autowired


    @PostMapping()
    ResponseEntity<Product> createProduct(@RequestBody Product productRequest){
        log.info("Product request: {}", productRequest);
        if(productRequest.getBarcode().isBlank() || productRequest.getPrice() == null){ //Will consider barcode and price non-nullable variables
            log.warn("Invalid product request {}", productRequest);
            return ResponseEntity.badRequest().build();
        }
        var product = productService.createProduct(productRequest);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/sort/price")
    ResponseEntity<List<Product>> getProductsSortedByPrice(){
        var products = productService.getAllProductsSortedByPrice();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/filter/price/{initial_range}/{final_range}")
    ResponseEntity<List<Product>> getProductsByPriceRange(@PathVariable("initial_range") Double initialRange,
                                                          @PathVariable("final_range") Double finalRange){
        var products = productService.getProductsByPriceRange(initialRange, finalRange);
        return ResponseEntity.ok(products);
    }

}
