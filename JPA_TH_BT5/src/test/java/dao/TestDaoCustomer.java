/**
 * @ (#) TestDaoCustomer.java      4/2/2024
 * <p>
 * Copyright (c) 2024 IUH. All rights reserved
 */

package dao;

import entities.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.Map;

/*
 * @description:
 * @author: Sinh Phan Tien
 * @date: 4/2/2024
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestDaoCustomer {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPADemo_SQL");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    private DaoCustomer daoCustomer;

    @BeforeAll
    public void setUp() {
        daoCustomer = new DaoCustomer(em, tx);
    }
    @Test
    public void testGetNumberCustomerByState() {
        Map<String, Integer> map = daoCustomer.getNumberCustomerByState();
        Assertions.assertNotNull(map);
        map.forEach((key, value) -> {
            System.out.println(key + " : " + value);
        });
    }

    @Test
    public void testGetOrdersByCustomers() {
        Map<Customer, Integer> map = daoCustomer.getOrdersByCustomers();
        Assertions.assertNotNull(map);
        map.forEach((key, value) -> {
            System.out.println(key + " : So luong: " + value);
        });
    }

    @Test
    public void testDeleteCustomerNotOrder() {
        int result = daoCustomer.deleteCustomerNotOrder();
        if(result > 0) {
            System.out.println("Delete " + result + " customers successfully");
        } else {
            System.out.println("No customer deleted");
        }
    }
}
