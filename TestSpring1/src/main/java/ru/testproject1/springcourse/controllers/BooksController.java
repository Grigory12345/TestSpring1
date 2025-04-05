package ru.testproject1.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.testproject1.springcourse.dao.BookDAO;
import ru.testproject1.springcourse.models.Book;
import ru.testproject1.springcourse.models.Person;

@Controller
@RequestMapping("/books")
public class BooksController
{

    private final BookDAO bookDAO;

    @Autowired
    public BooksController(BookDAO bookDAO)
    {
        this.bookDAO = bookDAO;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book)
    {
        return "books/new";
    }


    @PostMapping
    public String create(@ModelAttribute("book") Book book, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            return  "books/new";
        }

        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person")Person personBook)
    {
        model.addAttribute(bookDAO.show(id));
        //Заполнить
        model.addAttribute("personBook", bookDAO.getBookPerson(id));
        model.addAttribute("people", bookDAO.getPeople());
        
        return "books/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id)
    {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") Book book, BindingResult bindingResult,
                         @PathVariable("id") int id)
    {
        if (bindingResult.hasErrors())
            return "books/edit";

        System.out.println("id "+ id);
        bookDAO.update(book, id);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@ModelAttribute("book") Book book, Model model)
    {
        model.addAttribute(bookDAO.show(book.getId()));
        return "books/edit";
    }

    @PatchMapping("/{id}/clear")
    public String deletePersonFromBook(@PathVariable("id") int id)
    {
        bookDAO.deletePersonFromBook(id);
        return "redirect:/books";
    }

    @PostMapping("/{id}/setPerson")
    public String setPersonInBook(@PathVariable("id") int bookId, @ModelAttribute("person") Person person)
    {
        bookDAO.setPersonInBook(person.getId(), bookId);
        return "redirect:/books";
    }

}
