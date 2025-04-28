package org.movie;

import org.movie.model.Director;
import org.movie.model.Movie;
import org.movie.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
                addAnnotatedClass(Director.class)
                .addAnnotatedClass(Movie.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            //1. Получение режиссера (done)
            Director director = session.get(Director.class,1);
            System.out.println("Director: " + director.getName());

            List<Movie> movies = director.getMovies();

            System.out.println("==========================");
            System.out.println("Movies:");
            for (Movie movie: movies)
            {
                System.out.println(movie.getName());
            }


            //2. Получите любой фильм, а затем получите его режиссера. DONE
            System.out.println("================================");
            System.out.println("task2");

            Movie movie = session.get(Movie.class, 3);
            System.out.println("Movie: " + movie.getName());

            Director director1 = movie.getDirector();
            System.out.println("Director: " + director1.getName());


            //3. Добавьте еще один фильм для любого режиссера. DONE

            Director director2 = session.get(Director.class, 3);
            Movie newMovie = new Movie("Operation Fortune: Ruse de Guerre", 2022);
            director2.getMovies().add(newMovie);
            newMovie.setDirector(director2);

            session.save(newMovie);


            //4. Создайте нового режиссера и новый фильм и свяжите эти сущности. DONE

            /*
            Director director3 = new Director("Lars von Trier", 68);
            Movie movie1 = new Movie("Melancholia", 2011);

            movie1.setDirector(director3);
            director3.setMovies(new ArrayList<>(Collections.singletonList(movie1)));


            session.save(director3);
            session.save(movie1);
            */

            //5.  Смените режиссера у существующего фильма DONE
            /*
            Movie movie4 = session.get(Movie.class, 4);
            Director testDirector = new Director("testDirector", 2000);

            movie4.setDirector(testDirector);
            testDirector.setMovies(new ArrayList<>(Collections.singletonList(movie4)));

            session.save(testDirector);
            session.save(movie4);
            */

            //6.  Удалите фильм у любого режиссера DONE
            System.out.println("================================");
            System.out.println("Delete movie: 1");
            Movie movie1 = session.get(Movie.class, 1);

            movie1.getDirector().getMovies().remove(movie1);
            session.delete(movie1);

            session.getTransaction().commit();

        } finally {
            sessionFactory.close();
        }
    }


}
