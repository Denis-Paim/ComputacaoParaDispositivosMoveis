package denis.paim.formapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DadoDAO {

    public static void inserir(Context context, Dados dado) {

        ContentValues values = new ContentValues();
        values.put("nome", dado.getNome());
        values.put("sobrenome", dado.getSobrenome());
        values.put("telefone", dado.getTelefone());
        values.put("email", dado.getEmail());


        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();

        db.insert("dados", null, values);
    }

    public static void editar(Context context, Dados dado) {
        ContentValues values = new ContentValues();
        values.put("nome", dado.getNome());
        values.put("sobrenome", dado.getSobrenome());
        values.put("telefone", dado.getTelefone());
        values.put("email", dado.getEmail());

        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();

        db.update("dados", values, "id = " + dado.getId(), null);
    }

    public static void excluir(Context context, int idDado) {

        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();

        db.delete("dados", "id = " + idDado, null);
    }

    public static List<Dados> getDados(Context context) {
        List<Dados> lista = new ArrayList<>();

        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM dados ORDER BY nome ", null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Dados dad = new Dados();
                dad.setId(cursor.getInt(0));
                dad.setNome(cursor.getString(1));
                dad.setSobrenome(cursor.getString(2));
                dad.setTelefone(cursor.getString(3));
                dad.setEmail(cursor.getString(4));
                lista.add(dad);

            } while (cursor.moveToNext());
        }
        return lista;
    }

    public static Dados getDadoById(Context context, int idDado) {

        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM dados WHERE id =  " + idDado, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            Dados dad = new Dados();
            dad.setId(cursor.getInt(0));
            dad.setNome(cursor.getString(1));
            dad.setSobrenome(cursor.getString(2));
            dad.setTelefone(cursor.getString(3));
            dad.setEmail(cursor.getString(4));

            return dad;

        } else {
            return null;
        }
    }

}
