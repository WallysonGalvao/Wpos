package br.com.wpos.filmedb.bean;

/**
 * Created by wallyson.cunha on 23/04/2017.
 */

public class Filme {
    private int id;
    private String titulo;
    private String diretor;
    private int anoLancamento;
    private Genero genero;

    public Filme() {
        super();
    }

    public Filme(int id, String titulo, String diretor, int anoLancamento, Genero genero) {
        super();
        this.id = id;
        this.titulo = titulo;
        this.diretor = diretor;
        this.anoLancamento = anoLancamento;
        this.genero = genero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }
}
