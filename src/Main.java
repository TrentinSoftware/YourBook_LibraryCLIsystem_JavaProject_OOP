import model.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static LibraryManager libraryManager;
    private static Scanner scanner;

    private static String repeatChar(char ch, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(ch);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        libraryManager = new LibraryManager();
        scanner = new Scanner(System.in);
        loadData();
        
        boolean running = true;
        while (running) {
            System.out.println("\n=== Sistema de Gerenciamento de Biblioteca ===");
            System.out.println("1. Gerenciar Livros");
            System.out.println("2. Gerenciar Estudantes");
            System.out.println("3. Gerenciar Empréstimos");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    manageBooks();
                    break;
                case 2:
                    manageStudents();
                    break;
                case 3:
                    manageBorrowings();
                    break;
                case 0:
                    running = false;
                    saveData();
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
        scanner.close();
    }

    private static void manageBooks() {
        while (true) {
            System.out.println("\n=== Gerenciamento de Livros ===");
            System.out.println("1. Adicionar Livro");
            System.out.println("2. Listar Livros");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    listBooks();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void addBook() {
        System.out.println("\n=== Adicionar Novo Livro ===");
        System.out.print("Código: ");
        String code = scanner.nextLine();
        System.out.print("Título: ");
        String title = scanner.nextLine();
        System.out.print("Autor: ");
        String author = scanner.nextLine();
        System.out.print("Ano: ");
        int year = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Editora: ");
        String publisher = scanner.nextLine();

        Book book = new Book(code, title, author, year, publisher);
        if (libraryManager.addBook(book)) {
            System.out.println("Livro adicionado com sucesso!");
        } else {
            System.out.println("Erro: Código de livro já existe!");
        }
    }

    private static void listBooks() {
        System.out.println("\n=== Lista de Livros ===");
        List<Book> books = libraryManager.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
            return;
        }

        System.out.printf("%-10s %-30s %-20s %-6s %-20s %-10s%n",
            "Código", "Título", "Autor", "Ano", "Editora", "Status");
        System.out.println(repeatChar('-', 100));

        for (Book book : books) {
            System.out.printf("%-10s %-30s %-20s %-6d %-20s %-10s%n",
                book.getCode(),
                book.getTitle(),
                book.getAuthor(),
                book.getYear(),
                book.getPublisher(),
                book.isBorrowed() ? "Emprestado" : "Disponível"
            );
        }
    }

    private static void manageStudents() {
        while (true) {
            System.out.println("\n=== Gerenciamento de Estudantes ===");
            System.out.println("1. Adicionar Estudante");
            System.out.println("2. Listar Estudantes");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    listStudents();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void addStudent() {
        System.out.println("\n=== Adicionar Novo Estudante ===");
        System.out.print("Registro Acadêmico: ");
        String registration = scanner.nextLine();
        System.out.print("Nome: ");
        String name = scanner.nextLine();
        System.out.print("Curso: ");
        String course = scanner.nextLine();
        System.out.print("Período: ");
        int period = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Student student = new Student(registration, name, course, period);
        if (libraryManager.addStudent(student)) {
            System.out.println("Estudante adicionado com sucesso!");
        } else {
            System.out.println("Erro: Registro acadêmico já existe!");
        }
    }

    private static void listStudents() {
        System.out.println("\n=== Lista de Estudantes ===");
        List<Student> students = libraryManager.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("Nenhum estudante cadastrado.");
            return;
        }

        System.out.printf("%-15s %-30s %-20s %-8s %-15s%n",
            "Registro", "Nome", "Curso", "Período", "Livros Emprest.");
        System.out.println(repeatChar('-', 90));

        for (Student student : students) {
            System.out.printf("%-15s %-30s %-20s %-8d %-15d%n",
                student.getAcademicRegistration(),
                student.getName(),
                student.getCourse(),
                student.getPeriod(),
                student.getBorrowedBooks().size()
            );
        }
    }

    private static void manageBorrowings() {
        while (true) {
            System.out.println("\n=== Gerenciamento de Empréstimos ===");
            System.out.println("1. Emprestar Livro");
            System.out.println("2. Devolver Livro");
            System.out.println("3. Listar Empréstimos do Estudante");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    borrowBook();
                    break;
                case 2:
                    returnBook();
                    break;
                case 3:
                    listStudentBorrowings();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void borrowBook() {
        System.out.println("\n=== Emprestar Livro ===");
        System.out.print("Registro Acadêmico do Estudante: ");
        String studentId = scanner.nextLine();
        System.out.print("Código do Livro: ");
        String bookCode = scanner.nextLine();

        if (libraryManager.borrowBook(studentId, bookCode)) {
            System.out.println("Livro emprestado com sucesso!");
        } else {
            System.out.println("Erro: Não foi possível realizar o empréstimo!");
            System.out.println("Verifique se:");
            System.out.println("- O estudante existe");
            System.out.println("- O livro existe e está disponível");
            System.out.println("- O estudante não atingiu o limite de 3 livros");
        }
    }

    private static void returnBook() {
        System.out.println("\n=== Devolver Livro ===");
        System.out.print("Registro Acadêmico do Estudante: ");
        String studentId = scanner.nextLine();
        System.out.print("Código do Livro: ");
        String bookCode = scanner.nextLine();

        if (libraryManager.returnBook(studentId, bookCode)) {
            System.out.println("Livro devolvido com sucesso!");
        } else {
            System.out.println("Erro: Não foi possível realizar a devolução!");
            System.out.println("Verifique se:");
            System.out.println("- O estudante existe");
            System.out.println("- O livro existe e está emprestado para este estudante");
        }
    }

    private static void listStudentBorrowings() {
        System.out.println("\n=== Listar Empréstimos do Estudante ===");
        System.out.print("Registro Acadêmico do Estudante: ");
        String studentId = scanner.nextLine();

        Student student = libraryManager.getStudent(studentId);
        if (student == null) {
            System.out.println("Erro: Estudante não encontrado!");
            return;
        }

        List<Book> borrowedBooks = libraryManager.getBorrowedBooks(studentId);
        if (borrowedBooks.isEmpty()) {
            System.out.println("O estudante não possui livros emprestados.");
            return;
        }

        System.out.println("\nLivros emprestados para " + student.getName() + ":");
        System.out.printf("%-10s %-30s %-20s%n", "Código", "Título", "Autor");
        System.out.println(repeatChar('-', 60));

        for (Book book : borrowedBooks) {
            System.out.printf("%-10s %-30s %-20s%n",
                book.getCode(),
                book.getTitle(),
                book.getAuthor()
            );
        }
    }

    private static void loadData() {
        try {
            libraryManager.addAllBooks(FileManager.loadBooks());
            libraryManager.addAllStudents(FileManager.loadStudents());
            System.out.println("Dados carregados com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao carregar os dados: " + e.getMessage());
        }
    }

    private static void saveData() {
        try {
            FileManager.saveBooks(libraryManager.getAllBooks());
            FileManager.saveStudents(libraryManager.getAllStudents());
            System.out.println("Dados salvos com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }
}