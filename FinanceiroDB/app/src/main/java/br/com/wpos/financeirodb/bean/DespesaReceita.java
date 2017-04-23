package br.com.wpos.financeirodb.bean;

import java.util.Date;

/**
 * Created by Wallyson Galvão on 23/04/2017.
 */

public class DespesaReceita {
    private int id;
    private Date data;
    private String nome;
    private String despesaReceita;
    private TipoDespesaReceita tipoDespesaReceita;
    private float valor;

    public DespesaReceita() {
        super();
    }

    public DespesaReceita(int id, Date data, String nome, String despesaReceita, TipoDespesaReceita tipoDespesaReceita, float valor) {
        super();
        this.id = id;
        this.data = data;
        this.nome = nome;
        this.despesaReceita = despesaReceita;
        this.tipoDespesaReceita = tipoDespesaReceita;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
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

    public TipoDespesaReceita getTipoDespesaReceita() {
        return tipoDespesaReceita;
    }

    public void setTipoDespesaReceita(TipoDespesaReceita tipoDespesaReceita) {
        this.tipoDespesaReceita = tipoDespesaReceita;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
}
