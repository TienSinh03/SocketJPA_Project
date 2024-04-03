/**
 * @ (#) Store.java      3/30/2024
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
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "stores")
public class Store implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private int id;

    @Column(name = "store_name")
    private String name;

    @Embedded
    private Contact contact;

    @Embedded
    private Address address;

    public Store(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contact=" + contact +
                ", address=" + address +
                '}';
    }
}
