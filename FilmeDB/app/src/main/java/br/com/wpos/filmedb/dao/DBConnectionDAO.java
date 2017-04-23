package br.com.wpos.filmedb.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Wallyson Galvão on 23/04/2017.
 */

class DBConnectionDAO {
    private static final String DATABASE_NAME = "filme.db";
    private static final int DATABASE_VERSION = 1;

    static class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // Criar as duas tabelas necessárias para armazenar as informações
            db.execSQL("CREATE TABLE Genero (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL)");
            db.execSQL("CREATE TABLE Filme (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, titulo TEXT NOT NULL, diretor TEXT, anoLancamento INTEGER, genero INTEGER NOT NULL)");

            // Cria cinco generos de filmes
            db.execSQL("INSERT INTO Genero VALUES (1, 'Terror')");
            db.execSQL("INSERT INTO Genero VALUES (2, 'Suspense')");
            db.execSQL("INSERT INTO Genero VALUES (3, 'Comédia')");
            db.execSQL("INSERT INTO Genero VALUES (4, 'Ação')");
            db.execSQL("INSERT INTO Genero VALUES (5, 'Drama')");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS Genero");
            db.execSQL("DROP TABLE IF EXISTS Filme");
            onCreate(db);
        }
    }
}
