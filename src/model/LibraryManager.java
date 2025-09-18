package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryManager {
    private Map<String, Book> books;
    private Map<String, Student> students;

    public LibraryManager() {
        this.books = new HashMap<>();
        this.students = new HashMap<>();
    }

    // Book management methods
    public boolean addBook(Book book) {
        if (!books.containsKey(book.getCode())) {
            books.put(book.getCode(), book);
            try {
                FileManager.logOperation("Adição de Livro - Título: " + book.getTitle() + 
                                      " (Código: " + book.getCode() + ") adicionado ao sistema");
            } catch (IOException e) {
                System.out.println("Erro ao registrar operação no log: " + e.getMessage());
            }
            return true;
        }
        return false;
    }

    public Book getBook(String code) {
        return books.get(code);
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    public List<Book> getAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();
        for (Book book : books.values()) {
            if (!book.isBorrowed()) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    // Student management methods
    public boolean addStudent(Student student) {
        if (!students.containsKey(student.getAcademicRegistration())) {
            students.put(student.getAcademicRegistration(), student);
            try {
                FileManager.logOperation("Adição de Estudante - Nome: " + student.getName() + 
                                      " (RA: " + student.getAcademicRegistration() + 
                                      ", Curso: " + student.getCourse() + ") adicionado ao sistema");
            } catch (IOException e) {
                System.out.println("Erro ao registrar operação no log: " + e.getMessage());
            }
            return true;
        }
        return false;
    }

    public Student getStudent(String academicRegistration) {
        return students.get(academicRegistration);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }

    // Borrowing operations
    public boolean borrowBook(String studentId, String bookCode) {
        Student student = students.get(studentId);
        Book book = books.get(bookCode);
        
        if (student != null && book != null) {
            boolean success = student.borrowBook(book);
            if (success) {
                try {
                    FileManager.logOperation("Empréstimo - Livro: " + book.getTitle() + " (Código: " + book.getCode() + 
                                          ") emprestado para estudante: " + student.getName() + " (RA: " + student.getAcademicRegistration() + ")");
                } catch (IOException e) {
                    System.out.println("Erro ao registrar operação no log: " + e.getMessage());
                }
            }
            return success;
        }
        return false;
    }

    public boolean returnBook(String studentId, String bookCode) {
        Student student = students.get(studentId);
        Book book = books.get(bookCode);
        
        if (student != null && book != null) {
            boolean success = student.returnBook(book);
            if (success) {
                try {
                    FileManager.logOperation("Devolução - Livro: " + book.getTitle() + " (Código: " + book.getCode() + 
                                          ") devolvido pelo estudante: " + student.getName() + " (RA: " + student.getAcademicRegistration() + ")");
                } catch (IOException e) {
                    System.out.println("Erro ao registrar operação no log: " + e.getMessage());
                }
            }
            return success;
        }
        return false;
    }

    public List<Book> getBorrowedBooks(String studentId) {
        Student student = students.get(studentId);
        return student != null ? student.getBorrowedBooks() : new ArrayList<>();
    }

    // Helper methods for file operations
    public void clearAll() {
        books.clear();
        students.clear();
    }

    public void addAllBooks(List<Book> bookList) {
        for (Book book : bookList) {
            books.put(book.getCode(), book);
        }
    }

    public void addAllStudents(List<Student> studentList) {
        for (Student student : studentList) {
            students.put(student.getAcademicRegistration(), student);
        }
    }
}