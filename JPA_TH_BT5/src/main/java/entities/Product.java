/**
 * @ (#) Product.java      3/30/2024
 * <p>
 * Copyright (c) 2024 IUH. All rights reserved
 */

package entities;

/*
 * @description:
 * @author: Sinh Phan Tien
 * @date: 3/30/2024
 */

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "model_year", columnDefinition = "SMALLINT")
    private int modelYear;

    @Column(name = "list_price", columnDefinition = "DECIMAL(10,2)")
    private double listPrice;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Product(int id, String name, int modelYear, double listPrice) {
        this.id = id;
        this.name = name;
        this.modelYear = modelYear;
        this.listPrice = listPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", modelYear=" + modelYear +
                ", listPrice=" + listPrice +
                ", brand=" + brand +
                ", category=" + category +
                '}';
    }
}
