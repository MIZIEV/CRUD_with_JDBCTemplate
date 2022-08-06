package edu.app.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {

    private int bookId;
    @NotEmpty(message = "Book name mustn't be empty!")
    @Size(min=5,max = 100,message = "Name size must be between 5 and 100 characters!")
    private String bookName;

    @NotEmpty(message = "Author name mustn't be empty!")
    @Size(min=2,max = 100,message = "Author name size must be between 2 and 100 characters!")
    private String author;

    @Min(value = 0,message = "Year of publishing must be later than 0! ")
    private int yearOfPublishing;

    public Book(){}

    public Book(int bookId,String bookName, String author, int yearOfPublishing) {
        this.bookId=bookId;
        this.bookName = bookName;
        this.author = author;
        this.yearOfPublishing = yearOfPublishing;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfPublishing() {
        return yearOfPublishing;
    }

    public void setYearOfPublishing(int yearOfPublishing) {
        this.yearOfPublishing = yearOfPublishing;
    }
}