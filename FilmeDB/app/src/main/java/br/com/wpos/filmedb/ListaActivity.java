package br.com.wpos.filmedb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.com.wpos.filmedb.bean.Filme;
import br.com.wpos.filmedb.dao.FilmeDAO;

@SuppressWarnings("WrongConstant")
public class ListaActivity extends AppCompatActivity {

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        FilmeDAO filmeDAO = new FilmeDAO(this);
        List<Filme> lista = filmeDAO.selectAll();

        for (Filme oF : lista) {
            final LinearLayout linha = new LinearLayout(this);
            linha.setOrientation(0);

            final TextView colTitulo = new TextView(this);
            colTitulo.setWidth(300);
            colTitulo.setText(oF.getTitulo());

            final TextView colDiretor = new TextView(this);
            colDiretor.setWidth(300);
            colDiretor.setText(oF.getDiretor());

            final TextView AnoLancamento = new TextView(this);
            AnoLancamento.setWidth(150);
            AnoLancamento.setText(String.valueOf(oF.getAnoLancamento()));

            final TextView colGenero = new TextView(this);
            colGenero.setWidth(250);
            colGenero.setText(oF.getGenero().getNome());

            linha.addView(colTitulo);
            linha.addView(colDiretor);
            linha.addView(AnoLancamento);
            linha.addView(colGenero);
            linearLayout.addView(linha);
        }
    }
}
