/**
 * @ (#) DaoDepartment.java      4/1/2024
 * <p>
 * Copyright (c) 2024 IUH. All rights reserved
 */

package dao;

import entities.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

/*
 * @description:
 * @author: Sinh Phan Tien
 * @date: 4/1/2024
 */
public class DaoCategory {
    private final EntityManager em;
    private final EntityTransaction tx;
    public DaoCategory(EntityManager em, EntityTransaction tx) {
        this.em = em;
        this.tx = em.getTransaction();
    }

    /**
     * Insert a category
     * @param category
     * @return
     */
    public boolean insert(Category category) {
        String query = "INSERT INTO categories(category_name) VALUES (?)";
//        String query = "INSERT INTO Category c (c.name) VALUES (:name)";

        try {
            tx.begin();
            em.createNativeQuery(query).setParameter(1, category.getName()).executeUpdate();
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            return false;
        }
    }

    /**
     * Update a category
     * @param category
     * @return
     */
    public boolean update(Category category) {
        try {
            tx.begin();
            em.merge(category);
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            return false;
        }
    }

    /**
     * Delete a category
     * @param category
     * @return
     */
    public boolean delete(Category category) {
        try {
            tx.begin();
            em.remove(category);
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            return false;
        }
    }

    /**
     * Delete a category by id
     * @param id
     * @return
     */
    public boolean deleteById(int id) {
        String query = "DELETE FROM Category c WHERE c.id = :id";
        try {
            tx.begin();
            em.createQuery(query).setParameter("id", id).executeUpdate();
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            return false;
        }
    }

    /**
     * Find a category by id
     * @param id
     * @return
     */
    public Category findById(int id) {
        return em.find(Category.class, id);
    }


}
