/**
 * @ (#) Staff.java      3/30/2024
 * <p>
 * Copyright (c) 2024 IUH. All rights reserved
 */

package entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
 * @description:
 * @author: Sinh Phan Tien
 * @date: 3/30/2024
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "staffs")
@AttributeOverride(name="id", column=@Column(name="staff_id"))
public class Staff extends Person implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(columnDefinition = "TINYINT")
    private byte active;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Staff manager;

    public Staff(int id, String firstName, String lastName, byte active) {
        super(id, firstName, lastName);
        this.active = active;
    }

    @Override
    public String toString() {
        return "Staff{" + super.toString() +
                "active=" + active +
                ", store=" + store +
                ", manager=" + manager +
                ", id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", contact=" + contact +
                "} ";
    }
}
