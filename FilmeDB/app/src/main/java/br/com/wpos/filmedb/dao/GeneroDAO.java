package br.com.wpos.filmedb.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import br.com.wpos.filmedb.bean.Genero;

/**
 * Created by Wallyson Galvão on 23/04/2017.
 */

public class GeneroDAO extends DBConnectionDAO {
    // Tabela utilizada no SQLite
    private static final String TABLE_NAME = "Genero";
    private SQLiteDatabase db;
    private SQLiteStatement insertStmt;

    // Comando para realizar a inserção
    private static final String INSERT = "INSERT INTO " + TABLE_NAME + "(id, nome) VALUES (?, ?)";

    public GeneroDAO(Context context) {
        OpenHelper openHelper = new OpenHelper(context);
        this.db = openHelper.getWritableDatabase();
        this.insertStmt = this.db.compileStatement(INSERT);
    }

    // Método para inserção
    public long insert(Genero genero) {
        this.insertStmt.bindLong(1, genero.getId());
        this.insertStmt.bindString(2, genero.getNome());
        return this.insertStmt.executeInsert();
    }

    /*
     * Método onde passamos o código e ele retorna um objeto Genero
     * carregado com informações direto do banco de dados
     */
    Genero findById(Integer id) {
        Genero tipodr = new Genero();
        String selectQuery = "SELECT id, nome FROM Genero WHERE id=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                id.toString()
        });

        if (cursor.moveToFirst()) {
            tipodr.setId(cursor.getInt(0));
            tipodr.setNome(cursor.getString(1));
        }
        cursor.close();
        return tipodr;
    }

    /*
     * Método onde passamos o nome do Genero e ele retorna um objeto
     * Genero carregado com informaçõess direto do banco de dados
     */
    public Genero findByNome(String nome) {
        Genero tipodr = new Genero();
        String selectQuery = "SELECT id, nome FROM Genero WHERE nome=?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                nome
        });

        if (cursor.moveToFirst()) {
            tipodr.setId(cursor.getInt(0));
            tipodr.setNome(cursor.getString(1));
        }
        cursor.close();
        return tipodr;
    }

    /*
     * Traz todas generos presentes no banco, por uma lista de
     * objetos Genero
     */
    public List<Genero> selectAll() {
        Cursor cursor = this.db.query(TABLE_NAME, new String[]{
                "id", "nome"
        }, null, null, null, null, "id");
        return carregarLista(cursor);
    }

    /*
     * Este método é para carregar a lista recebendo um cursor carregado
     * com a consulta do banco.
     */
    private List<Genero> carregarLista(Cursor cursor) {
        List<Genero> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Genero tipodr = new Genero(
                        cursor.getInt(0),
                        cursor.getString(1)
                );
                list.add(tipodr);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }
}
