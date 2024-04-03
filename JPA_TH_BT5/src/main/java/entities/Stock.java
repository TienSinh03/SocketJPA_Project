/**
 * @ (#) Stock.java      3/30/2024
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
@Table(name = "stocks")
@EqualsAndHashCode
public class Stock implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @Id
    private Product product;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    @Id
    private Store Store;

    @EqualsAndHashCode.Exclude
    @Column(name = "quantity", nullable = false)
    private int quantity;
}
