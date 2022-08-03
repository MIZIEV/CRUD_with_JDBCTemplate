package edu.app.controllers;

import edu.app.dao.BookDAO;
import edu.app.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/book")
public class BooksController {

    private final BookDAO bookDAO;
    @Autowired
    public BooksController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @GetMapping
    public String getAllBooks(Model model){
        model.addAttribute("books",bookDAO.getAllBooks());
        return "booksPages/index";
    }

    @GetMapping("/add")
    public String addNewBook(Model model){
        model.addAttribute("book",new Book());
        return "booksPages/newBook";
    }

    @PostMapping
    public String add(Book book){
        bookDAO.addBook(book);
        return "redirect:/book";
    }
}