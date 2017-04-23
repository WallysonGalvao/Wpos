package br.com.wpos.financeirodb.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import br.com.wpos.financeirodb.bean.TipoDespesaReceita;

/**
 * Created by Wallyson Galvão on 23/04/2017.
 */

public class TipoDespesaReceitaDAO extends FinanceiroDAO {
    // Tabela utilizada no SQLite
    private static final String TABLE_NAME = "TipoDespesaReceita";
    private SQLiteDatabase db;
    private SQLiteStatement insertStmt;

    // Comando para realizar a inserção
    private static final String INSERT = "INSERT INTO " + TABLE_NAME + "(id, nome, despesaReceita) VALUES (?, ?, ?)";

    public TipoDespesaReceitaDAO(Context context) {
        OpenHelper openHelper = new OpenHelper(context);
        this.db = openHelper.getWritableDatabase();
        this.insertStmt = this.db.compileStatement(INSERT);
    }

    // Método para inserção
    public long insert(TipoDespesaReceita tipodr) {
        this.insertStmt.bindLong(1, tipodr.getId());
        this.insertStmt.bindString(2, tipodr.getNome());
        this.insertStmt.bindString(3, tipodr.getDespesaReceita());
        return this.insertStmt.executeInsert();
    }

    /*
     * Método onde passamos o código e ele retorna um objeto TipoDespesaReceita
     * carregado com informações direto do banco de dados
     */
    public TipoDespesaReceita findById(Integer id) {
        TipoDespesaReceita tipodr = new TipoDespesaReceita();
        String selectQuery = "SELECT id, nome, despesaReceita FROM TipoDespesaReceita WHERE id=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                id.toString()
        });

        if (cursor.moveToFirst()) {
            tipodr.setId(cursor.getInt(0));
            tipodr.setNome(cursor.getString(1));
            tipodr.setDespesaReceita(cursor.getString(2));
        }
        cursor.close();
        return tipodr;
    }

    // Método apaga tudo (!)
    public void deleteAll() {
        this.db.delete(TABLE_NAME, null, null);
    }

    /*
     * Método onde passamos o NOME DA DESPESA/RECEITA e ele retorna um objeto
     * TipoDespesaReceita carregado com informaçõess direto do banco de dados
     */
    public TipoDespesaReceita findByNome(String nome) {
        TipoDespesaReceita tipodr = new TipoDespesaReceita();
        String selectQuery = "SELECT id, nome, despesaReceita FROM TipoDespesaReceita WHERE nome=?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                nome
        });

        if (cursor.moveToFirst()) {
            tipodr.setId(cursor.getInt(0));
            tipodr.setNome(cursor.getString(1));
            tipodr.setDespesaReceita(cursor.getString(2));
        }
        cursor.close();
        return tipodr;
    }

    /*
     * Neste método, passa-se se queremos "RECEITAS" ou "DEPESAS" e retorna-se
     * uma lista com todas as despesas ou receitas
     * (é este método que será usado no Spinner)
     */
    public List<TipoDespesaReceita> selectByReceitaDespesa(String despesaReceita) {
        String selectQuery = "SELECT id, nome, despesaReceita FROM TipoDespesaReceita WHERE despesaReceita=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                despesaReceita
        });
        return carregarLista(cursor);
    }

    /*
     * Traz todas as despesas/receitas presentes no banco, por uma lista de
     * objetos TipoDespesaReceita
     */
    public List<TipoDespesaReceita> selectAll() {
        Cursor cursor = this.db.query(TABLE_NAME, new String[]{
                "id", "nome", "despesaReceita"
        }, null, null, null, null, "id");
        return carregarLista(cursor);
    }

    /*
     * Este método é para carregar a lista recebendo um cursor carregado
     * com a consulta do banco.
     */
    private List<TipoDespesaReceita> carregarLista(Cursor cursor) {
        List<TipoDespesaReceita> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                TipoDespesaReceita tipodr = new TipoDespesaReceita(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)
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
