/**
 * @ (#) Person.java      3/30/2024
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
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public abstract class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    @Column(name = "first_name", nullable = false, columnDefinition = "nvarchar(50)")
    protected String firstName;
    @Column(name = "last_name", columnDefinition = "nvarchar(50)")
    protected String lastName;

    @Embedded
    protected Contact contact;

    public Person(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", contact=" + contact.toString() +
                '}';
    }
}
