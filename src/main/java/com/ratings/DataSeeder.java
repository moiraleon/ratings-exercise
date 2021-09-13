package com.ratings;

import org.hibernate.HibernateException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;

public class DataSeeder {

    private static EntityManagerFactory factory;

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");


    public static void seedData() throws Exception {
        factory = Persistence.createEntityManagerFactory("hibernatefundamentals.m04.ex07");
        seedUsers();

        //TODO: seed movies and ratings

        factory.close();
    }


    private static void seedUsers() throws Exception {
        EntityManager entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin();
        try (BufferedReader br = new BufferedReader(new FileReader("seed_data/u.user"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] columnValues = line.split("\\|");
                int id = Integer.parseInt(columnValues[0]);
                Integer age = Integer.parseInt(columnValues[1]);
                String zipCode = columnValues[4];

                try {
                    User user = new User();
                    user.setId(id);
                    user.setAge(age);
                    user.setZipCode(zipCode);
                    entityManager.persist(user);
                } catch (HibernateException e) {
                    e.printStackTrace();
                }
            }
        }

        entityManager.getTransaction().commit();
    }
}
