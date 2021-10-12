package denis.paim.formapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Banco  extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "BancoFormApp";
    private static final int BANCO_VERSAO = 1;


    public Banco(Context context){
        super(context, NOME_BANCO, null, BANCO_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL( "CREATE TABLE IF NOT EXISTS dados (  " +
                "  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,  " +
                "  nome TEXT NOT NULL , " +
                "  sobrenome TEXT , " +
                "  telefone INTEGER , " +
                "  email TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
