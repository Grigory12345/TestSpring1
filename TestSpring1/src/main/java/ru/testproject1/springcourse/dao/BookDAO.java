package ru.testproject1.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.testproject1.springcourse.models.Book;
import ru.testproject1.springcourse.models.Person;

import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index(){
        return jdbcTemplate.query("SELECT * FROM BOOK", new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Book book){
        jdbcTemplate.update("INSERT INTO BOOK(NAME, AUTHOR, YEAR) VALUES (?, ?, ?)", book.getName(),
                book.getAuthor(), book.getYear());
    }

    public Book show(int id)
    {
        return jdbcTemplate.query("SELECT * FROM BOOK WHERE ID = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void delete(int id)
    {
        jdbcTemplate.update("DELETE FROM BOOK WHERE ID = ?", id);
    }

    public void update(Book book, int id)
    {
        jdbcTemplate.update("UPDATE BOOK SET NAME = ?, AUTHOR = ?, YEAR = ? WHERE ID =?",
                book.getName(), book.getAuthor(), book.getYear(), id);
    }

    public Person getBookPerson(int bookId)
    {
        //Нужно запросом вытащить книгу
        String query = "SELECT * FROM PERSON WHERE ID IN (SELECT PERSON_ID FROM BOOK WHERE ID = ?)";
        return jdbcTemplate.query(query, new Object[]{bookId},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void deletePersonFromBook(int bookId)
    {
        String query = "UPDATE BOOK SET PERSON_ID = NULL WHERE ID = ?";

        jdbcTemplate.update(query, bookId);
    }

    public List<Person> getPeople()
    {
        return  jdbcTemplate.query("SELECT * FROM PERSON", new BeanPropertyRowMapper<>(Person.class));
    }

    public void setPersonInBook(int personId, int bookId)
    {
        String query = "UPDATE BOOK SET PERSON_ID = ? WHERE ID = ?";

        jdbcTemplate.update(query, personId, bookId);
    }


}
