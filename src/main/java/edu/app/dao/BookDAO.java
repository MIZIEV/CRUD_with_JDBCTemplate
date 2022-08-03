package edu.app.dao;

import edu.app.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAllBooks(){
        return jdbcTemplate.query("SELECT * FROM Book",new BeanPropertyRowMapper<>(Book.class));
    }

    public void addBook(Book book){
        jdbcTemplate.update("INSERT INTO Book (book_name, author, year_of_publishing) values (?,?,?)",
                book.getBookName(),book.getAuthor(),book.getYearOfPublishing());
    }

    public Book getBook(int bookId){
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?",
                new Object[]{bookId},new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void updateBook(int bookId, Book updatedBook){
        jdbcTemplate.update("UPDATE Book SET book_name=?,author=?,year_of_publishing=? WHERE book_id=?",
                updatedBook.getBookName(),updatedBook.getAuthor(),updatedBook.getYearOfPublishing(), bookId);
    }
}