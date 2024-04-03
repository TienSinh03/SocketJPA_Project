/**
 * @ (#) Customer.java      3/30/2024
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
@Table(name = "customers")
@AttributeOverride(name="id", column=@Column(name="customer_id"))
public class Customer extends Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Embedded
    private Address address;

    public Customer(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }

    @Override
    public String toString() {
        return "Customer{" + super.toString() +
                ", id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", contact=" + contact +
                " address=" + address +
                "} " ;
    }
}
