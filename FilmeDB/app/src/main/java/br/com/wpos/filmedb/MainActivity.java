package br.com.wpos.filmedb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import br.com.wpos.filmedb.bean.Filme;
import br.com.wpos.filmedb.bean.Genero;
import br.com.wpos.filmedb.dao.FilmeDAO;
import br.com.wpos.filmedb.dao.GeneroDAO;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {

    EditText txtTitulo;
    EditText txtDiretor;
    Spinner spiGenero;
    EditText txtData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTitulo = (EditText) findViewById(R.id.txtTitulo);
        txtDiretor = (EditText) findViewById(R.id.txtDiretor);
        spiGenero = (Spinner) findViewById(R.id.spiGenero);
        txtData = (EditText) findViewById(R.id.txtData);

        carregarGenero();
    }

    public void carregarGenero() {

        GeneroDAO generoDAO = new GeneroDAO(this);
        List<Genero> lista = generoDAO.selectAll();
        List<String> labels = new ArrayList<>();
        for (Genero tdp : lista) {
            labels.add(tdp.getNome());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Anexando os Adapters carregados ao Spinner
        spiGenero.setAdapter(dataAdapter);
    }

    public void cadastrarFilme(View view) {
        GeneroDAO generoDAO = new GeneroDAO(this);
        Genero oGenero = generoDAO.findByNome(spiGenero.getSelectedItem().toString());

        Filme oFilme = new Filme(0, txtTitulo.getText().toString(), txtDiretor.getText().toString(), parseInt(txtData.getText().toString()), oGenero);

        FilmeDAO filmeDAO = new FilmeDAO(this);
        filmeDAO.insert(oFilme);

        // Chama a outra atividade
        Intent intent = new Intent(this, ListaActivity.class);
        startActivity(intent);
    }
}
