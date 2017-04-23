package br.com.wpos.filmedb.bean;

/**
 * Created by Wallyson Galvão on 23/04/2017.
 */

public class Genero {
    private int id;
    private String nome;

    public Genero() {
        super();
    }

    public Genero(int id, String nome) {
        super();
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
