/**
 * @ (#) TestDaoOrder.java      4/2/2024
 * <p>
 * Copyright (c) 2024 IUH. All rights reserved
 */

package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.Map;

/*
 * @description:
 * @author: Sinh Phan Tien
 * @date: 4/2/2024
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestDaoOrder {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPADemo_SQL");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    private DaoOrder daoOrder;

    @BeforeAll
    public void setUp() {
        daoOrder = new DaoOrder(em, tx);
    }

    @Test
    public void testGetTotalOrderById() {
        int id = 640;
        double totalPrice = daoOrder.getTotalOrderById(id);
        Assertions.assertEquals(1522.1650, totalPrice);
        System.out.println("Total price of order id " + id + " is: " + totalPrice);
    }

    @Test
    public void testGetTotalOrderByDate() {
        LocalDate date = LocalDate.of(2017, 1, 6);
        double totalPrice = daoOrder.getTotalOrderByDate(date);
        Assertions.assertEquals(1522.1650, totalPrice);
        System.out.println("Total price of orders on date 2017-01-06 is: " + totalPrice);
    }


    @Test
    public void testStatisticsTotalPriceByMonthAndYear() {
        Map<String, Double> map = daoOrder.statisticsTotalPriceByMonthAndYear();
        Assertions.assertNotNull(map);
        map.forEach((key, value) -> {
            System.out.println(key + " : " + value);
        });
    }
}
