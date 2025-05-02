package org.movie;

import org.movie.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.TypedQuery;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().
                addAnnotatedClass(Principal.class)
                .addAnnotatedClass(School.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            //1. С помощью Hibernate получите любого директора, а затем получите его школу DONE
            Principal principal = session.get(Principal.class, 1);

            TypedQuery<School> query = session.createQuery("FROM School WHERE principal_id = :principal_id", School.class);
            query.setParameter("principal_id", principal.getId());
            School school = query.getResultList().getFirst();

            System.out.println("Task1. School number is: " + school.getSchool_number());


            //2. Получите любую школу, а затем получите ее директора DONE
            School school1 = session.get(School.class, 3);
            Principal principal1 = school1.getPrincipal();
            System.out.println("===================");
            System.out.println("Task2");
            System.out.println("Школа2:" + school1.getSchool_number());
            System.out.println("Директор2:" + principal1.getName());


            //3. Создайте нового директора и новую школу и свяжите эти сущности DONE
           // Principal principal2 = new Principal("Peter", 33);
           // School school2 = new School(21);
           // school2.setPrincipal(principal2);

           // session.save(principal2);
           // session.save(school2);

            //4. Смените директора у существующей школы. DONE
            /*
            School school3 = session.get(School.class, 4);
            Principal principal3 = new Principal("Mark", 50);

            school3.setPrincipal(principal3);

            session.save(principal3);
            session.update(school3);

             */

            //5.  Попробуйте добавить вторую школу для существующего директора и
            //изучите возникающую ошибку DONE

            School school4 = new School(100);
            Principal principal4 = session.get(Principal.class, 1);

            school4.setPrincipal(principal4);
            session.save(school4);

            session.getTransaction().commit();

        } finally {
            sessionFactory.close();
        }
    }


}
