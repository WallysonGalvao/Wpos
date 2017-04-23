package br.com.wpos.filmedb.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import br.com.wpos.filmedb.bean.Filme;


/**
 * Created by Wallyson Galvão on 23/04/2017.
 */

public class FilmeDAO extends DBConnectionDAO {
    // Tabela utilizada no SQLite
    private static final String TABLE_NAME = "Filme";
    private SQLiteDatabase db;
    private GeneroDAO genero;

    public FilmeDAO(Context context) {
        OpenHelper openHelper;
        openHelper = new OpenHelper(context);
        this.db = openHelper.getWritableDatabase();
        genero = new GeneroDAO(context);
    }

    // Método para inserção
    public long insert(Filme filme) {
        SQLiteStatement cmdInsert;
        String INSERT = "INSERT INTO " + TABLE_NAME + " (titulo, diretor, anoLancamento, genero) VALUES (?,?,?,?)";
        cmdInsert = this.db.compileStatement(INSERT);

        cmdInsert.bindString(1, filme.getTitulo());
        cmdInsert.bindString(2, filme.getDiretor());
        cmdInsert.bindLong(3, filme.getAnoLancamento());
        cmdInsert.bindLong(4, filme.getGenero().getId());

        return cmdInsert.executeInsert();
    }

    // Traz todas os filmes presentes no banco, por uma lista de objetos Filme
    public List<Filme> selectAll() {
        List<Filme> list = new ArrayList<>();
        Cursor cursor = this.db.query(TABLE_NAME, new String[]{
                "id", "titulo", "diretor", "anoLancamento", "genero"
        }, null, null, null, null, "id");
        if (cursor.moveToFirst()) {
            do {
                Filme tipodr = new Filme(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        genero.findById(cursor.getInt(4))
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
