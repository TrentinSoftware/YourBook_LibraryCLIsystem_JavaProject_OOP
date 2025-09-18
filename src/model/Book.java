package model;

public class Book {
    private String code;
    private String title;
    private String author;
    private int year;
    private String publisher;
    private boolean isBorrowed;
    private String borrowedBy;

    public Book(String code, String title, String author, int year, String publisher) {
        this.code = code;
        this.title = title;
        this.author = author;
        this.year = year;
        this.publisher = publisher;
        this.isBorrowed = false;
        this.borrowedBy = null;
    }

    // Getters and setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public boolean isBorrowed() { return isBorrowed; }
    public void setBorrowed(boolean borrowed) { isBorrowed = borrowed; }

    public String getBorrowedBy() { return borrowedBy; }
    public void setBorrowedBy(String borrowedBy) { this.borrowedBy = borrowedBy; }

    @Override
    public String toString() {
        return String.format("%s;%s;%s;%d;%s;%b;%s", 
            code, title, author, year, publisher, isBorrowed, 
            borrowedBy != null ? borrowedBy : "");
    }
}