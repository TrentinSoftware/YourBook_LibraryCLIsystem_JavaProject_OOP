package ui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import models.Livro;

public class MainView extends JFrame {
    public MainView() {
        super("YourBook - Livraria");
        try {
            setIconImage(new ImageIcon("src/public/logo.jpg").getImage());
        } catch (Exception e) {
            System.err.println("Não foi possivel editar a imagem do Java");
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(700, 400));
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Logo
        JLabel logoLabel = new JLabel();
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setVerticalAlignment(SwingConstants.CENTER);
        try {
            String logoPath = "src/public/logo.png";
            java.net.URL logoURL = getClass().getClassLoader().getResource(logoPath);
            ImageIcon logoIcon;
            if (logoURL != null) {
                logoIcon = new ImageIcon(logoURL);
            } else {
                logoIcon = new ImageIcon(logoPath);
            }
            Image img = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            logoLabel.setText("Logo não encontrada");
        }

        JLabel titleLabel = new JLabel("YourBook - Livraria");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(33, 33, 33));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(logoLabel, BorderLayout.WEST);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Tabela de livros usando LivroRepository
        List<Livro> livros = models.LivroRepository.carregarLivros("src/mockdata/livros.txt");
        LivrosTablePanel livrosTablePanel = new LivrosTablePanel(livros);
        mainPanel.add(livrosTablePanel, BorderLayout.CENTER);

        // Botão Adicionar
        JButton btnAdicionar = new JButton("Adicionar Livro");
        btnAdicionar.addActionListener(e -> {
            JTextField tituloField = new JTextField();
            JTextField autorField = new JTextField();
            JTextField anoField = new JTextField();
            JComboBox<String> statusBox = new JComboBox<>(new String[]{"disponivel", "emprestado"});
            JTextField dataTerminoField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0, 2));
            panel.add(new JLabel("Título:")); panel.add(tituloField);
            panel.add(new JLabel("Autor:")); panel.add(autorField);
            panel.add(new JLabel("Ano:")); panel.add(anoField);
            panel.add(new JLabel("Status:")); panel.add(statusBox);
            panel.add(new JLabel("Data Término Empréstimo (yyyy-MM-dd):")); panel.add(dataTerminoField);

            int result = JOptionPane.showConfirmDialog(this, panel, "Adicionar Livro", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String titulo = tituloField.getText().trim();
                String autor = autorField.getText().trim();
                int ano = 0;
                try { ano = Integer.parseInt(anoField.getText().trim()); } catch (Exception ex) {}
                String status = (String) statusBox.getSelectedItem();
                java.util.Date dataTermino = null;
                if (status.equals("emprestado") && !dataTerminoField.getText().trim().isEmpty()) {
                    try {
                        dataTermino = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(dataTerminoField.getText().trim());
                    } catch (Exception ex) { dataTermino = null; }
                }
                Livro novoLivro = new Livro(titulo, autor, ano, status, dataTermino);
                livros.add(novoLivro);
                livrosTablePanel.carregarLivros(livros);
                // Opcional: salvar no TXT
                salvarLivros("src/mockdata/livros.txt", livros);
            }
        });
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(btnAdicionar);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        pack();
        setVisible(true);
    }

    private void salvarLivros(String caminhoTxt, List<Livro> livros) {
        try (java.io.PrintWriter pw = new java.io.PrintWriter(caminhoTxt)) {
            pw.println("titulo;autor;ano;status;dataTerminoEmprestimo");
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            for (Livro livro : livros) {
                String data = livro.getDataTerminoEmprestimo() != null ? sdf.format(livro.getDataTerminoEmprestimo()) : "";
                pw.println(livro.getTitulo() + ";" + livro.getAutor() + ";" + livro.getAno() + ";" + livro.getStatus() + ";" + data);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar livros: " + e.getMessage());
        }
    }

}
