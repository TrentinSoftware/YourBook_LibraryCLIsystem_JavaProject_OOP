package model;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String academicRegistration;
    private String name;
    private String course;
    private int period;
    private List<Book> borrowedBooks;

    public Student(String academicRegistration, String name, String course, int period) {
        this.academicRegistration = academicRegistration;
        this.name = name;
        this.course = course;
        this.period = period;
        this.borrowedBooks = new ArrayList<>();
    }

    // Getters and setters
    public String getAcademicRegistration() { return academicRegistration; }
    public void setAcademicRegistration(String academicRegistration) { 
        this.academicRegistration = academicRegistration; 
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public int getPeriod() { return period; }
    public void setPeriod(int period) { this.period = period; }

    public List<Book> getBorrowedBooks() { return borrowedBooks; }

    public boolean canBorrowBooks() {
        return borrowedBooks.size() < 3;
    }

    public boolean borrowBook(Book book) {
        if (canBorrowBooks() && !book.isBorrowed()) {
            borrowedBooks.add(book);
            book.setBorrowed(true);
            book.setBorrowedBy(this.academicRegistration);
            return true;
        }
        return false;
    }

    public boolean returnBook(Book book) {
        if (borrowedBooks.remove(book)) {
            book.setBorrowed(false);
            book.setBorrowedBy(null);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%s;%d", 
            academicRegistration, name, course, period);
    }
}