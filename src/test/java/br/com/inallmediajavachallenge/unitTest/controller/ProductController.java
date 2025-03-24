package br.com.inallmediajavachallenge.unitTest.controller;
import br.com.inallmediajavachallenge.exception.GlobalExceptionHandler;
import br.com.inallmediajavachallenge.exception.InvalidPriceRangeException;
import br.com.inallmediajavachallenge.model.Product;
import br.com.inallmediajavachallenge.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class ProductController {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @Mock
    private WebApplicationContext webApplicationContext;

    @InjectMocks
    private ProductController productController;

    private Product ballGown;
    private Product shawl;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(productController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        // Setup test data
        ballGown = new Product();
        ballGown.setId(1L);
        ballGown.setBarcode("74001755");
        ballGown.setItem("Ball Gown");
        ballGown.setCategory("Full Body Outfits");
        ballGown.setPrice(3548.00);
        ballGown.setDiscount(7.0);
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
    void getProductsByPriceRange_ValidRange_ReturnsProductsSortedByPrice() throws Exception {
        // Arrange
        List<Product> products = Arrays.asList(ballGown, shawl);
        when(productService.getProductsByPriceRange(500.0, 4000.0)).thenReturn(products);

        // Act & Assert
        mockMvc.perform(get("/api/products/price-range")
                        .param("initialRange", "500.0")
                        .param("finalRange", "4000.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].barcode").value("74001755"))
                .andExpect(jsonPath("$[0].item").value("Ball Gown"))
                .andExpect(jsonPath("$[1].barcode").value("74002423"))
                .andExpect(jsonPath("$[1].item").value("Shawl"));
    }

    @Test
    void getProductsSortedByPriceByPriceRange_InvalidRange_ReturnsBadRequest() throws Exception {
        // Arrange
        when(productService.getProductsByPriceRange(anyDouble(), anyDouble()))
                .thenThrow(new InvalidPriceRangeException("Final range cannot be lower than initial range"));

        // Act & Assert
        mockMvc.perform(get("/api/products/price-range")
                        .param("initialRange", "4000.0")
                        .param("finalRange", "500.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Final range cannot be lower than initial range"));
    }

    @Test
    void getProductsSortedByPrice_ReturnsSortedProductsSortedByPrice() throws Exception {
        // Arrange
        List<Product> sortedProducts = Arrays.asList(shawl, ballGown);
        when(productService.getProductsByPriceRange(400.0, 8000.0)).thenReturn(sortedProducts);

        // Act & Assert
        mockMvc.perform(get("/api/products/sorted-by-price")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].item").value("Shawl"))
                .andExpect(jsonPath("$[0].price").value(758))
                .andExpect(jsonPath("$[1].item").value("Ball Gown"))
                .andExpect(jsonPath("$[1].price").value(3548));
    }
}
