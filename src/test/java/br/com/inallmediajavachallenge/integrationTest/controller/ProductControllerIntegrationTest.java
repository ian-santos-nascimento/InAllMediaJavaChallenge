package br.com.inallmediajavachallenge.integrationTest.controller;

import br.com.inallmediajavachallenge.model.Product;
import br.com.inallmediajavachallenge.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    private Product ballGown;
    private Product shawl;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();

        ballGown = new Product();
        ballGown.setBarcode("74001755");
        ballGown.setItem("Ball Gown");
        ballGown.setCategory("Full Body Outfits");
        ballGown.setPrice(3548.00);
        ballGown.setDiscount(7.00);
        ballGown.setAvailable(1);

        shawl = new Product();
        shawl.setBarcode("74002423");
        shawl.setItem("Shawl");
        shawl.setCategory("Accessories");
        shawl.setPrice(758.00);
        shawl.setDiscount(12.00);
        shawl.setAvailable(1);

        productRepository.saveAll(Arrays.asList(ballGown, shawl));
    }

    @Test
    void getPriceFilterEndpoint_ValidRange_ReturnsFilteredProducts() throws Exception {
        mockMvc.perform(get("/products/filter/price/700/4000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].barcode").value("74001755"))
                .andExpect(jsonPath("$[0].item").value("Ball Gown"))
                .andExpect(jsonPath("$[1].barcode").value("74002423"))
                .andExpect(jsonPath("$[1].item").value("Shawl"));
    }

    @Test
    void getPriceFilterEndpoint_InvalidRange_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/products/filter/price/4000/700")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getPriceFilterEndpoint_NoProductsInRange_ReturnsEmptyArray() throws Exception {
        mockMvc.perform(get("/products/filter/price/5000/10000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getPriceSortEndpoint_ReturnsSortedProductNames() throws Exception {
        mockMvc.perform(get("/products/sort/price")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].item").value("Shawl"));
    }

    @Test
    void getPriceSortEndpoint_EmptyDatabase_ReturnsEmptyArray() throws Exception {
        // Clear the database
        productRepository.deleteAll();

        mockMvc.perform(get("/products/sort/price")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}