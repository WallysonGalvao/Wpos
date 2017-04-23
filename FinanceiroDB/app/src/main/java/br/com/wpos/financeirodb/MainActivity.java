package br.com.wpos.financeirodb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.wpos.financeirodb.bean.DespesaReceita;
import br.com.wpos.financeirodb.bean.TipoDespesaReceita;
import br.com.wpos.financeirodb.dao.DespesaReceitaDAO;
import br.com.wpos.financeirodb.dao.TipoDespesaReceitaDAO;

public class MainActivity extends AppCompatActivity {

    EditText txtNome;
    EditText txtValor;
    Spinner spiTipoDespesaReceita;
    RadioGroup radDespesaReceita;
    EditText txtData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNome = (EditText) findViewById(R.id.txtNome);
        txtValor = (EditText) findViewById(R.id.txtValor);
        spiTipoDespesaReceita = (Spinner) findViewById(R.id.spiTipoDespesaReceita);
        radDespesaReceita = (RadioGroup) findViewById(R.id.radDespesaReceita);
        txtData = (EditText) findViewById(R.id.txtData);
    }

    public void carregarTipo(View v) {
        RadioButton radioButton = (RadioButton) v;

        TipoDespesaReceitaDAO tdrDAO = new TipoDespesaReceitaDAO(this);
        List<TipoDespesaReceita> lista = tdrDAO.selectByReceitaDespesa(radioButton.getText().toString());
        List<String> labels = new ArrayList<>();
        for (TipoDespesaReceita tdp : lista) {
            labels.add(tdp.getNome());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Anexando os Adapters carregados ao Spinner
        spiTipoDespesaReceita.setAdapter(dataAdapter);
    }

    public void cadastrarDespesaReceita(View v) {
        RadioButton radButton = (RadioButton) findViewById(radDespesaReceita.getCheckedRadioButtonId());
        TipoDespesaReceitaDAO tdrDAO = new TipoDespesaReceitaDAO(this);
        TipoDespesaReceita oTdr = tdrDAO.findByNome(spiTipoDespesaReceita.getSelectedItem().toString());
        Date data = new Date(0);
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy");
            data = (Date) df.parse(txtData.getText().toString());
            Log.v("data", txtData.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DespesaReceita oDr = new DespesaReceita(0, data, txtNome.getText().toString(), radButton.getText().toString(), oTdr, Float.parseFloat(txtValor.getText().toString()));

        DespesaReceitaDAO drDAO = new DespesaReceitaDAO(this);
        drDAO.insert(oDr);

        // Chama a outra atividade
        Intent intent = new Intent(this, ListaActivity.class);
        startActivity(intent);

    }
}
