package edu.app.controllers;

import edu.app.dao.BookDAO;
import edu.app.dao.PersonDAO;
import edu.app.models.Book;
import edu.app.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/book")
public class BooksController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
    }

    @GetMapping
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookDAO.getAllBooks());
        return "booksPages/index";
    }

    @GetMapping("/{id}")
    public String getConcreteBook(@PathVariable("id") int id, Model model,
                                  @ModelAttribute("person") Person person) {

        Optional<Person> bookOwner = bookDAO.getBookOwner(id);

        if (bookOwner.isPresent()) {
            model.addAttribute("owner", bookOwner.get());
        } else {
            model.addAttribute("people",personDAO.getAllPeople());
        }

        model.addAttribute("book", bookDAO.getBook(id));
        return "booksPages/concreteBook";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.getBook(id));
        return "booksPages/editBook";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") int id,
                             @ModelAttribute("book") @Valid Book book, BindingResult error) {
        if (error.hasErrors()) {
            return "booksPages/editBook";
        } else {
            bookDAO.updateBook(id, book);
            return "redirect:/book";
        }
    }

    @GetMapping("/add")
    public String addNewBook(Model model) {
        model.addAttribute("book", new Book());
        return "booksPages/newBook";
    }

    @PostMapping
    public String add(@Valid Book book, BindingResult error) {
        if (error.hasErrors()) {
            return "booksPages/newBook";
        } else {
            bookDAO.addBook(book);
            return "redirect:/book";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookDAO.deleteBook(id);
        return "redirect:/book";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id){
        bookDAO.deleteOwner(id);
        return "redirect:/book/"+id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id,@ModelAttribute("person") Person selectedPerson){
        bookDAO.setNewOwner(id,selectedPerson);
        return "redirect:/book/"+id;
    }
}