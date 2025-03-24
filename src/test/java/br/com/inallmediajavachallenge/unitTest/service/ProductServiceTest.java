package br.com.inallmediajavachallenge.unitTest.service;

import br.com.inallmediajavachallenge.exception.InvalidPriceRangeException;
import br.com.inallmediajavachallenge.model.Product;
import br.com.inallmediajavachallenge.repository.ProductRepository;
import br.com.inallmediajavachallenge.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product ballGown;
    private Product shawl;
    
    private final Double initialPrice = 700.0;
    private final Double finalPrice = 4000.0;


    @BeforeEach
    void setUp() {
        // Setup test data
        ballGown = new Product();
        ballGown.setId(1L);
        ballGown.setBarcode("74001755");
        ballGown.setItem("Ball Gown");
        ballGown.setCategory("Full Body Outfits");
        ballGown.setPrice(3548.00);
        ballGown.setDiscount(7.00);
        ballGown.setAvailable(1);

        shawl = new Product();
        shawl.setId(2L);
        shawl.setBarcode("74002423");
        shawl.setItem("Shawl");
        shawl.setCategory("Accessories");
        shawl.setPrice(758.00);
        shawl.setDiscount(12.00);
        shawl.setAvailable(1);
    }

    @Test
    void getProductsByPriceRange_ValidRange_ReturnsFilteredProducts() {
        // Arrange
        when(productRepository.findByPriceBetween(initialPrice, finalPrice))
                .thenReturn(Arrays.asList(ballGown, shawl));

        // Act
        List<Product> result = productService.getProductsByPriceRange(initialPrice, finalPrice);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(ballGown));
        assertTrue(result.contains(shawl));
        verify(productRepository).findByPriceBetween(initialPrice, finalPrice);
    }

    @Test
    void getProductsByPriceRange_InvalidRange_ThrowsException() {
        // Arrange & Act & Assert
        InvalidPriceRangeException exception = assertThrows(
                InvalidPriceRangeException.class,
                () -> productService.getProductsByPriceRange(finalPrice, initialPrice)
        );

        assertEquals("Final range cannot be lower than initial range", exception.getMessage());
        verify(productRepository, never()).findByPriceBetween((double) anyInt(), (double) anyInt());
    }

    @Test
    void getProductsByPriceRange_EqualRange_ReturnsProducts() {
        Double price = 758.0;

        // Arrange
        when(productRepository.findByPriceBetween(price, price))
                .thenReturn(List.of(shawl));

        // Act
        List<Product> result = productService.getProductsByPriceRange(price, price);

        // Assert
        assertEquals(1, result.size());
        assertEquals(shawl, result.getFirst());
        verify(productRepository).findByPriceBetween(price, price);
    }

    @Test
    void getProductsSortedByPrice_ReturnsSortedProducts() {
        // Arrange
        when(productRepository.findByPriceBetween(initialPrice, finalPrice))
                .thenReturn(Arrays.asList(shawl, ballGown));

        // Act
        List<Product> result = productService.getProductsByPriceRange(initialPrice, finalPrice);

        // Assert
        assertEquals(2, result.size());
        assertEquals(shawl, result.get(0));
        assertEquals(ballGown, result.get(1));
        verify(productRepository).findByPriceBetween(initialPrice, finalPrice);
    }
}
