package br.com.wpos.financeirodb.bean;

/**
 * Created by Wallyson Galvão on 23/04/2017.
 */

public class TipoDespesaReceita {

    private int id;
    private String nome;
    private String despesaReceita;

    public TipoDespesaReceita() {
        super();
    }

    public TipoDespesaReceita(int id, String nome, String despesaReceita) {
        super();
        this.id = id;
        this.nome = nome;
        this.despesaReceita = despesaReceita;
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

    public String getDespesaReceita() {
        return despesaReceita;
    }

    public void setDespesaReceita(String despesaReceita) {
        this.despesaReceita = despesaReceita;
    }
}
