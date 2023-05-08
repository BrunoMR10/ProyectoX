package com.bmr.xproyect.DataBases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.bmr.xproyect.Entidades.DatosANAM;

public class FavoritosManageDB extends FavoritosHelpeDB {
    Context context;

    public FavoritosManageDB(Context context) {
        super(context, "");
        this.context = context;
    }
    public long InsertaDatos(String Ticket, String Datos[]) {
        long id = 0;

        try {
            FavoritosHelpeDB dbHelper = new FavoritosHelpeDB(context, Ticket);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("Nombre", Datos[6]);
            values.put("Numero", Datos[9]);
            values.put("CorreoElectronico", Datos[8]);
            values.put("Puesto", Datos[7]);
            id = db.insert(TABLE_FAVORITOSANAM, null, values);
        } catch (Exception ex) {
            ex.toString();
        }
        return id;

    }

    public boolean eliminarDatos( String Ticket) {

        context.deleteDatabase(Ticket);
        return true;

    }

    public int TamañoTabla(String Ticket) {
        FavoritosHelpeDB dbHelper = new FavoritosHelpeDB(context, Ticket);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_FAVORITOSANAM );
        return numRows;

    }

    public DatosANAM mostrarDatos(String Ticket, int id) {
        FavoritosHelpeDB dbHelper = new FavoritosHelpeDB(context, Ticket);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        DatosANAM datos = null;
        Cursor cursordatos = null;
        cursordatos = db.rawQuery("SELECT * FROM " + TABLE_FAVORITOSANAM  + " WHERE id = " + id + " LIMIT 1 ", null);

        if (cursordatos.moveToFirst()) {
            do {
                datos = new DatosANAM();
                datos.setNombreCompleto(cursordatos.getString(1));
                datos.setNumero(cursordatos.getString(2));
                datos.setCorreo(cursordatos.getString(3));
                datos.setPuesto(cursordatos.getString(4));
            } while (cursordatos.moveToNext());

        }
        cursordatos.close();
        return datos;
    }
}
