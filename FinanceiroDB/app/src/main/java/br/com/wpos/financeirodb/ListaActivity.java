package br.com.wpos.financeirodb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.com.wpos.financeirodb.bean.DespesaReceita;
import br.com.wpos.financeirodb.dao.DespesaReceitaDAO;

@SuppressWarnings("WrongConstant")
public class ListaActivity extends AppCompatActivity {

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        DespesaReceitaDAO drDAO = new DespesaReceitaDAO(this);
        List<DespesaReceita> lista = drDAO.selectAll();

        for (DespesaReceita oDr1 : lista) {
            final LinearLayout linha = new LinearLayout(this);
            linha.setOrientation(0);

            final TextView colNome = new TextView(this);
            colNome.setWidth(550);
            colNome.setText(oDr1.getNome());

            final TextView colTipo = new TextView(this);
            colTipo.setWidth(150);
            colTipo.setText(oDr1.getTipoDespesaReceita().getNome());

            final TextView colValor = new TextView(this);
            if (oDr1.getDespesaReceita().equals("Receita")) {
                colValor.setText("R$" + String.format("%.2f", oDr1.getValor()));
            } else {
                colValor.setText(" - R$ " + String.format("%.2f", oDr1.getValor()));
            }

            linha.addView(colNome);
            linha.addView(colTipo);
            linha.addView(colValor);
            linearLayout.addView(linha);


        }
    }
}
