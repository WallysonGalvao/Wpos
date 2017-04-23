package br.com.wpos.financeirodb.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Wallyson Galvão on 23/04/2017.
 */

public class FinanceiroDAO {
    private static final String DATABASE_NAME = "financeiro.db";
    private static final int DATABASE_VERSION = 1;

    protected static class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // Criar as duas tabelas necessárias para armazenar as informações
            db.execSQL("CREATE TABLE TipoDespesaReceita (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, despesaReceita TEXT)");
            db.execSQL("CREATE TABLE DespesaReceita (id INTEGER PRIMARY KEY AUTOINCREMENT, DATA integer, nome TEXT, despesaReceita TEXT, tipoDespesaReceita INTEGER, valor REAL)");

            // Cria dois tipos de receita e dois tipos de despesas
            db.execSQL("INSERT INTO TipoDespesaReceita VALUES (1, 'Salário', 'Receita')");
            db.execSQL("INSERT INTO TipoDespesaReceita VALUES (2, 'Aluguel', 'Despesa')");
            db.execSQL("INSERT INTO TipoDespesaReceita VALUES (3, 'Almoço', 'Despesa')");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS TipoDespesaReceita");
            db.execSQL("DROP TABLE IF EXISTS DespesaReceita");
            onCreate(db);
        }
    }
}
