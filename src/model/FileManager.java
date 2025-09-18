package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String DATA_DIR = "data";
    private static String dataPath;

    static {
        // Inicializa o diretório de dados
        File currentDir = new File(System.getProperty("user.dir"));
        dataPath = currentDir.getAbsolutePath() + File.separator + DATA_DIR;
        File dataDir = new File(dataPath);
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
    }

    private static final String BOOKS_FILE = dataPath + File.separator + "books.txt";
    private static final String STUDENTS_FILE = dataPath + File.separator + "students.txt";
    private static final String LOG_FILE = dataPath + File.separator + "library_log.txt";

    public static void logOperation(String operation) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            String timestamp = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            writer.write(timestamp + " - " + operation);
            writer.newLine();
        }
    }

    public static void saveBooks(List<Book> books) throws IOException {
        File file = new File(BOOKS_FILE);
        if (!file.exists()) {
            file.createNewFile();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
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
            System.out.println("Arquivo de livros não encontrado em: " + BOOKS_FILE);
            return books;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue; // Pula linhas vazias
                
                try {
                    String[] parts = line.split(";");
                    if (parts.length >= 5) {  // Modificado para 5 campos obrigatórios
                        Book book = new Book(
                            parts[0].trim(),  // código
                            parts[1].trim(),  // título
                            parts[2].trim(),  // autor
                            Integer.parseInt(parts[3].trim()),  // ano
                            parts[4].trim()   // editora
                        );
                        
                        // Campos opcionais de empréstimo
                        if (parts.length > 5) {
                            book.setBorrowed(Boolean.parseBoolean(parts[5].trim()));
                        }
                        if (parts.length > 6 && !parts[6].trim().isEmpty()) {
                            book.setBorrowedBy(parts[6].trim());
                        }
                        
                        books.add(book);
                        System.out.println("Livro carregado: " + book.getTitle());
                    } else {
                        System.out.println("Aviso: Linha " + lineNumber + " ignorada - formato inválido: " + line);
                    }
                } catch (Exception e) {
                    System.out.println("Erro ao processar linha " + lineNumber + ": " + line);
                    System.out.println("Erro: " + e.getMessage());
                }
            }
        }
        return books;
    }

    public static void saveStudents(List<Student> students) throws IOException {
        File file = new File(STUDENTS_FILE);
        if (!file.exists()) {
            file.createNewFile();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
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