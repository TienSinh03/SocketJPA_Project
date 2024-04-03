/**
 * @ (#) TestDaoProduct.java      4/1/2024
 * <p>
 * Copyright (c) 2024 IUH. All rights reserved
 */

package dao;

import entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

/*
 * @description:
 * @author: Sinh Phan Tien
 * @date: 4/1/2024
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestDaoProduct {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPADemo_SQL");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    private DaoProduct daoProduct;

    @BeforeAll
    public void setUp() {
        daoProduct = new DaoProduct(em, tx);
    }

    @Test
    public void testListProductsMaxPrice() {
        List<Product> products = daoProduct.listProductsMaxPrice();
        Assertions.assertNotNull(daoProduct.listProductsMaxPrice());
        products.forEach(product -> {
            Assertions.assertEquals(11999.99, product.getListPrice());
            System.out.println(product);
        });
    }

    @Test
    public void testListProductsNullOrders() {
        List<Product> products = daoProduct.listProductsNullOrders();
        Assertions.assertNotNull(daoProduct.listProductsNullOrders());
        products.forEach(System.out::println);
    }

    @Test
    public void testGetTotalProducts() {
        Map<Product, Integer> map = daoProduct.getTotalProducts();
        Assertions.assertNotNull(map);
        map.forEach((key, value) -> {
            System.out.println(key + " | So luong: " + value);
        });
    }
}
