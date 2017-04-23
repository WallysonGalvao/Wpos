package br.com.wpos.financeirodb.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import br.com.wpos.financeirodb.bean.DespesaReceita;

/**
 * Created by Wallyson Galvão on 23/04/2017.
 */

public class DespesaReceitaDAO extends FinanceiroDAO {
    // Tabela utilizada no SQLite
    private static final String TABLE_NAME = "DespesaReceita";
    private Context context;
    private SQLiteDatabase db;
    private TipoDespesaReceitaDAO tdrDAO;

    public DespesaReceitaDAO(Context context) {
        this.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        this.db = openHelper.getWritableDatabase();
        tdrDAO = new TipoDespesaReceitaDAO(context);
    }

    // Método para inserção
    public long insert(DespesaReceita oDr) {
        SQLiteStatement cmdInsert;
        String INSERT = "INSERT INTO " + TABLE_NAME + " (data, nome, despesaReceita, tipoDespesaReceita, valor) VALUES (strftime('%s',?),?,?,?,?)";
        cmdInsert = this.db.compileStatement(INSERT);

        String data = oDr.getData().getYear() + "-" + (oDr.getData().getMonth() < 10 ? "0" + oDr.getData().getMonth() : oDr.getData().getMonth()) + "-"
                + (oDr.getData().getDay() < 10 ? "0" + oDr.getData().getDay() : oDr.getData().getDay());
        cmdInsert.bindString(1, data);
        cmdInsert.bindString(2, oDr.getNome());
        cmdInsert.bindString(3, oDr.getDespesaReceita());
        cmdInsert.bindLong(4, oDr.getTipoDespesaReceita().getId());
        cmdInsert.bindDouble(5, oDr.getValor());

        // Método útil - podemos logar informações que aparecem no LogCat durante a execução
        Log.v("SQL", "strftime('%s','" + oDr.getData().getYear() + "-" + (oDr.getData().getMonth() < 10 ? "0" + oDr.getData().getMonth() : oDr.getData().getMonth()) + "-"
                + (oDr.getData().getDay() < 10 ? "0" + oDr.getData().getDay() : oDr.getData().getDay()) + "')");

        return cmdInsert.executeInsert();
    }

    // Método apaga tudo(!)
    public void deleteAll() {
        this.db.delete(TABLE_NAME, null, null);
    }

    // Traz todas as despesas/receitas presentes no banco, por uma lista de objetos DespesaReceita
    public List<DespesaReceita> selectAll() {
        List<DespesaReceita> list = new ArrayList<>();
        Cursor cursor = this.db.query(TABLE_NAME, new String[]{
                "id", "data", "nome", "despesaReceita", "tipoDespesaReceita", "valor"
        }, null, null, null, null, "id");
        if (cursor.moveToFirst()) {
            do {
                DespesaReceita tipodr = new DespesaReceita(
                        cursor.getInt(0),
                        new Date(cursor.getLong(1)),
                        cursor.getString(2),
                        cursor.getString(3),
                        tdrDAO.findById(cursor.getInt(4)),
                        (float) cursor.getDouble(5)
                );
                list.add(tipodr);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }

    // Encerrar conexão com o banco
    public void encerrarDB() {
        this.db.close();
    }
}
