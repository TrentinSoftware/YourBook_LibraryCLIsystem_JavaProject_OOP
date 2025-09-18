package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String BOOKS_FILE = "books.txt";
    private static final String STUDENTS_FILE = "students.txt";
    private static final String LOG_FILE = "library_log.txt";

    public static void logOperation(String operation) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            String timestamp = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            writer.write(timestamp + " - " + operation);
            writer.newLine();
        }
    }

    public static void saveBooks(List<Book> books) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKS_FILE))) {
            for (Book book : books) {
                writer.write(book.toString());
                writer.newLine();
            }
        }
    }

    public static List<Book> loadBooks() throws IOException {
        List<Book> books = new ArrayList<>();
        File file = new File(BOOKS_FILE);
        
        if (!file.exists()) {
            return books;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 7) {
                    Book book = new Book(parts[0], parts[1], parts[2], 
                                       Integer.parseInt(parts[3]), parts[4]);
                    book.setBorrowed(Boolean.parseBoolean(parts[5]));
                    book.setBorrowedBy(parts[6].isEmpty() ? null : parts[6]);
                    books.add(book);
                }
            }
        }
        return books;
    }

    public static void saveStudents(List<Student> students) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STUDENTS_FILE))) {
            for (Student student : students) {
                writer.write(student.toString());
                writer.newLine();
            }
        }
    }

    public static List<Student> loadStudents() throws IOException {
        List<Student> students = new ArrayList<>();
        File file = new File(STUDENTS_FILE);
        
        if (!file.exists()) {
            return students;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 4) {
                    students.add(new Student(parts[0], parts[1], parts[2], 
                                          Integer.parseInt(parts[3])));
                }
            }
        }
        return students;
    }
}