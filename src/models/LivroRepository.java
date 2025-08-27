package models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LivroRepository {
    public static List<Livro> carregarLivros(String caminhoTxt) {
        List<Livro> livros = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoTxt))) {
            String linha;
            boolean primeiraLinha = true;
            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) { primeiraLinha = false; continue; } // pula cabeçalho
                String[] campos = linha.split(";", -1); // -1 para manter campos vazios
                if (campos.length == 5) {
                    String titulo = campos[0].trim();
                    String autor = campos[1].trim();
                    int ano;
                    try {
                        ano = Integer.parseInt(campos[2].trim());
                    } catch (NumberFormatException e) {
                        ano = 0;
                    }
                    String status = campos[3].trim();
                    java.util.Date dataTerminoEmprestimo = null;
                    if (!campos[4].trim().isEmpty() && !status.equals("disponivel")) {
                        try {
                            dataTerminoEmprestimo = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(campos[4].trim());
                        } catch (Exception e) {
                            dataTerminoEmprestimo = null;
                        }
                    }
                    livros.add(new Livro(titulo, autor, ano, status, dataTerminoEmprestimo));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return livros;
    }
}
