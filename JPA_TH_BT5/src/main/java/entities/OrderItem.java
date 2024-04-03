/**
 * @ (#) OrderItem.java      3/30/2024
 * <p>
 * Copyright (c) 2024 IUH. All rights reserved
 */

package entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/*
 * @description:
 * @author: Sinh Phan Tien
 * @date: 3/30/2024
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "order_items")
@EqualsAndHashCode
public class OrderItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManyToOne(cascade = CascadeType.ALL)
    @Id
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL)
    @Id
    @JoinColumn(name = "order_id")
    private Order order;

    @EqualsAndHashCode.Exclude
    @Column(nullable = false)
    private int quantity;

    @EqualsAndHashCode.Exclude
    @Column(name = "list_price", columnDefinition = "DECIMAL(10,2)")
    private double listPrice;

    @EqualsAndHashCode.Exclude
    @Column(name = "discount", columnDefinition = "DECIMAL(4,2)")
    private double discount;
}
