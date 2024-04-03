/**
 * @ (#) DaoProduct.java      4/1/2024
 * <p>
 * Copyright (c) 2024 IUH. All rights reserved
 */

package dao;

import entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
 * @description:
 * @author: Sinh Phan Tien
 * @date: 4/1/2024
 */
public class DaoProduct {
    private final EntityManager em;
    private final EntityTransaction tx;
    public DaoProduct(EntityManager em, EntityTransaction tx) {
        this.em = em;
        this.tx = em.getTransaction();
    }

    /**
     * 2) Tìm danh sách sản phẩm có giá cao nhất.
     * @return list products
     * SELECT * FROM products WHERE list_price = (SELECT MAX(list_price) FROM products);
     */
    public List<Product> listProductsMaxPrice() {
//        String query = "SELECT * FROM products WHERE list_price = (SELECT MAX(list_price) FROM products)";
        String query = "select p from Product p where p.listPrice = (select max(p.listPrice) from Product p)";
        try {
            tx.begin();
            List<Product> products = em.createQuery(query, Product.class).getResultList();
            tx.commit();
            return products;
        } catch (Exception e) {
            tx.rollback();
            return null;
        }
    }

    public List<Product> listProductsNullOrders() {
//        String query = "SELECT p.product_id, p.product_name, p.list_price, p.model_year, p.brand_id, p.category_id FROM products p \n" +
//                "LEFT JOIN order_items s ON p.product_id = s.product_id \n" +
//                "WHERE s.product_id IS NULL";
      String query = "select p from Product p where p.id not in (select oi.product.id from OrderItem oi)";
        try {
            tx.begin();
            List<Product> products = em.createQuery(query, Product.class).getResultList();
            tx.commit();
            return products;
        } catch (Exception e) {
            tx.rollback();
            return null;
        }
    }

    /**
     *7) Tính tổng số lượng của từng sản phẩm đã bán ra.
     * @return
     * Native query = SELECT s.product_id, sum(s.quantity) FROM products p Join order_items s ON p.product_id = s.product_id
     *                  group by s.product_id
     *                  order by s.product_id asc
     */
    public Map<Product, Integer> getTotalProducts() {
        String query = "select oi.product.id, sum(oi.quantity) from OrderItem oi join oi.product p group by oi.product.id order by oi.product.id asc";
        try {
            tx.begin();
            Map<Product, Integer> resultMap = new LinkedHashMap<>();
            List<Object[]> results = em.createQuery(query, Object[].class).getResultList();
            results.forEach(item ->{
                Product product = em.find(Product.class, (Integer) item[0]);
                Long total = (Long) item[1];
                resultMap.put(product, total.intValue());
            });
            tx.commit();
            return resultMap;
        } catch (Exception e) {
            tx.rollback();
            return null;
        }
    }
}
