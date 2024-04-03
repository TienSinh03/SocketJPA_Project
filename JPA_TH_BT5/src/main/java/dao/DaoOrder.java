/**
 * @ (#) DaoOrder.java      4/2/2024
 * <p>
 * Copyright (c) 2024 IUH. All rights reserved
 */

package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
 * @description:
 * @author: Sinh Phan Tien
 * @date: 4/2/2024
 */
public class DaoOrder {
    private final EntityManager em;
    private final EntityTransaction tx;

    public DaoOrder(EntityManager em, EntityTransaction tx) {
        this.em = em;
        this.tx = em.getTransaction();
    }

    /**
     * 5) Tính tổng tiền của đơn hàng khi biết mã số đơn hàng
     *
     * @param id
     * @return
     */
    public double getTotalOrderById(int id) {
        String query = "select sum(oi.quantity * oi.listPrice*(1 - oi.discount)) from OrderItem oi " +
                "where oi.order.id = :id";
        double totalPrice = 0;
        try {
            tx.begin();
            totalPrice = em.createQuery(query, Double.class)
                    .setParameter("id", id)
                    .getSingleResult();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
        return totalPrice;
    }

    /**
     * 8) Tính tổng tiền của tất cả các hóa đơn trong một ngày nào đó.
     *
     * @param date
     * @return Native query: select sum(ord.list_price * ord.quantity * (1-ord.discount)) from orders o join order_items ord on o.order_id  = ord.order_id
     * where o.order_date = '2017-01-06'
     */
    public double getTotalOrderByDate(LocalDate date) {
        String query = "select sum(oi.quantity * oi.listPrice * (1 - oi.discount)) from OrderItem oi join oi.order o " +
                "where oi.order.orderDate = :date";
        double totalPrice = 0;
        try {
            tx.begin();
            totalPrice = em.createQuery(query, Double.class)
                    .setParameter("date", date)
                    .getSingleResult();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
        return totalPrice;
    }

    /**
     * 10) Thống kê tổng tiền của các đơn hàng theo từng tháng và năm
     *
     * @return Native query:
     * select year(o.order_date) as year, month(o.order_date) as month,sum(ord.list_price * ord.quantity * (1-ord.discount)) from orders o join order_items ord on o.order_id  = ord.order_id
     * group by year(o.order_date), month(o.order_date)
     * order by year, month asc
     */
    public Map<String, Double> statisticsTotalPriceByMonthAndYear() {
        String query = "select year(oi.order.orderDate) as year, month(oi.order.orderDate) as month,sum(oi.listPrice * oi.quantity * (1-oi.discount)) from OrderItem oi join oi.order o\n " +
                "group by year(oi.order.orderDate), month(oi.order.orderDate)\n" +
                "order by year(oi.order.orderDate), month(oi.order.orderDate) asc";
        try {
            tx.begin();
            List<Object[]> results = em.createQuery(query, Object[].class).getResultList();
            Map<String, Double> resultMap = new LinkedHashMap<>();
            results.forEach(item -> {
                String key = item[0] + "-" + item[1];
                Double value = (Double) item[2];
                resultMap.put(key, value);
            });
            tx.commit();
            return resultMap;
        } catch (Exception e) {
            tx.rollback();
        }
        return null;
    }
}