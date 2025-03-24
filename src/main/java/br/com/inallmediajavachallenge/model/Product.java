package br.com.inallmediajavachallenge.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String barcode;
    private String item;
    private String category;
    private Double price;
    private Double discount;
    private Integer available;

    public String getAvailabilityStatus() {
        return available.equals(1) ? "Available" : "Unavailable";
    }

}
