package br.com.inallmediajavachallenge.repository;

import br.com.inallmediajavachallenge.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    List<Product> findByPriceBetween(Double initialRange, Double finalRange);

    List<Product> findAllByOrderByPriceAsc();

}
