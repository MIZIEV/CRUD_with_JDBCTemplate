package edu.app.controllers;

import edu.app.dao.BookDAO;
import edu.app.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/book")
public class BooksController {

    private final BookDAO bookDAO;

    @Autowired
    public BooksController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @GetMapping
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookDAO.getAllBooks());
        return "booksPages/index";
    }

    @GetMapping("/{id}")
    public String getConcreteBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.getBook(id));
        return "booksPages/concreteBook";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.getBook(id));
        return "booksPages/editBook";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") int id,@ModelAttribute("book") Book book){
        bookDAO.updateBook(id,book);
        return "redirect:/book";
    }

    @GetMapping("/add")
    public String addNewBook(Model model) {
        model.addAttribute("book", new Book());
        return "booksPages/newBook";
    }

    @PostMapping
    public String add(Book book) {
        bookDAO.addBook(book);
        return "redirect:/book";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id){
        bookDAO.deleteBook(id);
        return "redirect:/book";
    }
}