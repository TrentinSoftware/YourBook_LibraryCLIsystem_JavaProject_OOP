package models;

import java.util.Date;

public class Livro {
    private String titulo;
    private String autor;
    private int ano;
    private String status; // "disponivel" ou "emprestado"
    private Date dataTerminoEmprestimo; // null se disponivel

    public Livro(String titulo, String autor, int ano, String status, Date dataTerminoEmprestimo) {
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
        this.status = status;
        this.dataTerminoEmprestimo = dataTerminoEmprestimo;
    }

    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getAno() { return ano; }
    public String getStatus() { return status; }
    public Date getDataTerminoEmprestimo() { return dataTerminoEmprestimo; }

    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setAutor(String autor) { this.autor = autor; }
    public void setAno(int ano) { this.ano = ano; }
    public void setStatus(String status) { this.status = status; }
    public void setDataTerminoEmprestimo(Date dataTerminoEmprestimo) { this.dataTerminoEmprestimo = dataTerminoEmprestimo; }
}
