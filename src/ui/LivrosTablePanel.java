package ui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import models.Livro;

public class LivrosTablePanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    public LivrosTablePanel(List<Livro> livros) {
        setLayout(new BorderLayout());
    String[] colunas = {"Título", "Autor", "Ano", "Status", "Data Término Empréstimo"};
        tableModel = new DefaultTableModel(colunas, 0);
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        carregarLivros(livros);
    }

    public void carregarLivros(List<Livro> livros) {
        tableModel.setRowCount(0);
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        for (Livro livro : livros) {
            String dataTermino = livro.getDataTerminoEmprestimo() != null ? sdf.format(livro.getDataTerminoEmprestimo()) : "";
            Object[] row = {
                livro.getTitulo(),
                livro.getAutor(),
                livro.getAno(),
                livro.getStatus(),
                dataTermino
            };
            tableModel.addRow(row);
        }
    }
}
