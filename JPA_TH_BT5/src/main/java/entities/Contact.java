/**
 * @ (#) Contact.java      3/30/2024
 * <p>
 * Copyright (c) 2024 IUH. All rights reserved
 */

package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/*
 * @description:
 * @author: Sinh Phan Tien
 * @date: 3/30/2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Contact implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Column(columnDefinition = "nvarchar(25)")
    private String phone;
    private String email;
}
