/**
 * @ (#) Order.java      3/30/2024
 * <p>
 * Copyright (c) 2024 IUH. All rights reserved
 */

package entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/*
 * @description:
 * @author: Sinh Phan Tien
 * @date: 3/30/2024
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int id;

    @Column(name = "order_status", columnDefinition = "TINYINT")
    private byte orderStatus;
    @Column(name = "order_date", columnDefinition = "DATE")
    private LocalDate orderDate;
    @Column(name = "required_date", columnDefinition = "DATE")
    private LocalDate requiredDate;
    @Column(name = "shipped_date", columnDefinition = "DATE")
    private LocalDate shippedDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;


}
