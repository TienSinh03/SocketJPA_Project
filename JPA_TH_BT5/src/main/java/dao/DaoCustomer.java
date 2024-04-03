/**
 * @ (#) DaoCustomer.java      4/2/2024
 * <p>
 * Copyright (c) 2024 IUH. All rights reserved
 */

package dao;

import entities.Customer;
import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * @description:
 * @author: Sinh Phan Tien
 * @date: 4/2/2024
 */
public class DaoCustomer {
    private final EntityManager em;
    private final EntityTransaction tx;

    public DaoCustomer(EntityManager em, EntityTransaction tx) {
        this.em = em;
        this.tx = em.getTransaction();
    }

    /**
     * Insert a customer
     *
     * @param customer
     * @return
     */
    public boolean insert(Customer customer) {
        return false;
    }

    /**
     * Update a customer
     *
     * @param customer
     * @return
     */
    public boolean update(Customer customer) {
        return false;
    }

    /**
     * Thống kê số khách hàng theo từng bang
     *
     * @return
     */
    public Map<String, Integer> getNumberCustomerByState() {
//        String query = "select state, COUNT(customer_id) from customers \n" +
//                "group by state";
        String query = "SELECT c.address.state, COUNT(c.id) FROM Customer c GROUP BY c.address.state";
        try {
            tx.begin();
            Map<String, Integer> resultMap = new HashMap<>();
//            em.createNativeQuery(query).getResultList()
//                    .stream()
//                    .forEach(item -> {
//                        Object[] obj = (Object[]) item;
//                        System.out.println(obj[0] + " : " + obj[1]);
//                        resultMap.put((String) obj[0], (Integer) obj[1]);
//                    });
            List<Object[]> results = em.createQuery(query, Object[].class).getResultList();
            results.forEach(item -> {
                String state = (String) item[0];
                Long count = (Long) item[1];
                resultMap.put(state, count.intValue());
            });
            tx.commit();
            return resultMap;
        } catch (Exception e) {
            tx.rollback();
            return null;
        }
    }

    /**
     * 6) Đếm số đơn hàng của từng khách hàng.
     * @return
     *
     * NativeQuery = select c.customer_id, count(ori.order_id) from customers c join orders o on c.customer_id = o.customer_id
     * 						  join order_items ori on o.order_id = ori.order_id
     * 						  group by c.customer_id
     * 						  order by c.customer_id asc
     */
    public Map<Customer, Integer> getOrdersByCustomers() {

        String query = "select c.id, count(ord.order.id) from OrderItem ord join ord.order o join o.customer c group by c.id order by c.id asc";
        try {
            tx.begin();
            List<Object[]> results = em.createQuery(query, Object[].class).getResultList();
            Map<Customer, Integer> resultMap = new LinkedHashMap<>();
            results.forEach(item -> {
                Customer customer = em.find(Customer.class, (Integer) item[0]);
                Long count = (Long) item[1];
                resultMap.put(customer, count.intValue());
            });
            tx.commit();
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return null;
    }

    /**
     * 9) Xóa khách hàng không có đơn hàng
     * @return
     * Native query = delete from customers c where not exists (select distinct o from orders o join customers c on o.customer_id = c.customer_id)
     */
    public int deleteCustomerNotOrder() {
        String query = "delete from Customer c where not exists (select distinct o from Order o join o.customer c)";
        int n = 0;
        try {
            tx.begin();
            n = em.createQuery(query).executeUpdate();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
        return n;
    }

//    public static void main(String[] args) {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPADemo_SQL");
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//        DaoCustomer daoCustomer = new DaoCustomer(em, tx);
//        Map<String, Integer> map = daoCustomer.getNumberCustomerByState();
//        map.forEach((key, value) -> {
//            System.out.println(key + " : " + value);
//        });
//    }
}
