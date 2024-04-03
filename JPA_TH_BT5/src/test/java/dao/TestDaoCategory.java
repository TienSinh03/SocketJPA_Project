/**
 * @ (#) TestDaoCategory.java      4/1/2024
 * <p>
 * Copyright (c) 2024 IUH. All rights reserved
 */

package dao;

import entities.Category;
import jakarta.persistence.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/*
 * @description:
 * @author: Sinh Phan Tien
 * @date: 4/1/2024
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestDaoCategory {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPADemo_SQL");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    private DaoCategory daoCategory;

    @BeforeAll
    public void setUp() {
        daoCategory = new DaoCategory(em, tx);
    }

    @Test
    public void testInsert() {
        Category category = new Category();
        category.setName("Young Bicycles");
        boolean checked = daoCategory.insert(category);
        Assertions.assertTrue(checked);
    }

    @Test
    public void testUpdate() {
        Category category = new Category();
        category.setId(8);
        category.setName("Old Bicycles");
        boolean checked = daoCategory.update(category);
        Assertions.assertTrue(checked);
    }

    @Test
    public void testDelete() {
        boolean checked = daoCategory.deleteById(8);
        Assertions.assertTrue(checked);
    }

    @Test
    public void testFindById() {
        Category category = daoCategory.findById(1);
        Assertions.assertNotNull(category);
        Assertions.assertEquals(true, category.getName().contains("Bicycles"));


    }



}
