package com.bmr.xproyect.DataBases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.bmr.xproyect.Entidades.Ticket;
import com.bmr.xproyect.Entidades.TicketDB;

import java.io.DataOutput;

public class TicketManageDB extends TicketHelperDB {
    Context context;

    public TicketManageDB(Context context, String name) {
        super(context, name);
        this.context = context;
    }

    public long InsertaDatos(String[] Datos,String DB){
        long id = 0;

        try {
            TicketHelperDB dbHelper = new TicketHelperDB(context,DB);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("id2", Datos[27]);
            values.put("Nomenclatura", Datos[0]);
            values.put("NombreSeg", Datos[4]);
            values.put("PuestoSeg", Datos[5]);
            values.put("TipoServicio", Datos[1]);
            values.put("Trimestre", Datos[10]);
            values.put("Periodicidad", Datos[11]);
            values.put("Ticket", Datos[12]);
            values.put("Aduana", Datos[13]);
            values.put("Equipo", Datos[14]);
            values.put("Ubicacion", Datos[15]);
            values.put("Serie", Datos[16]);
            values.put("FechaInicio", Datos[17]);
            values.put("HoraInicio", Datos[18]);
            values.put("FechaFin", Datos[19]);
            values.put("HoraFin", Datos[20]);
            values.put("Falla", Datos[21]);
            values.put("FechaLLamada", Datos[22]);
            values.put("HoraLLamada", Datos[23]);
            values.put("FechaSitio", Datos[24]);
            values.put("HoraSitio", Datos[25]);
            values.put("NombreTecnico", Datos[26]);
            id = db.insert(TABLE_TICKETS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }
        return id;
    }


    public boolean editaTicket(String Datos[],String Nombre) {

        boolean correcto = false;

        TicketHelperDB dbHelper = new TicketHelperDB(context, Nombre);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_TICKETS + " SET " +
                    "id2 = '" + Datos[27] +
                    "', Nomenclatura = '" + Datos[0] +
                    "', NombreSeg = '" + Datos[4] +
                    "', PuestoSeg = '" + Datos[5] +
                    "', Trimestre = '" + Datos[10] +
                    "', Periodicidad = '" + Datos[11] +
                    "', Ticket = '" + Datos[12] +
                    "', Aduana = '" + Datos[13] +
                    "', Equipo = '" + Datos[14] +
                    "', Ubicacion = '" + Datos[15] +
                    "', Serie = '" + Datos[16] +
                    "', FechaInicio = '" + Datos[17] +
                    "', HoraInicio = '" + Datos[18] +
                    "', FechaFin = '" + Datos[19] +
                    "', HoraFin = '" + Datos[20] +
                    "', Falla = '" + Datos[21] +
                    "', FechaLLamada = '" + Datos[22] +
                    "', HoraLLamada = '" + Datos[23] +
                    "', FechaSitio = '" + Datos[24] +
                    "', HoraSitio = '" + Datos[25] +
                    "', NombreTecnico = '" + Datos[26] +
                    "' WHERE id='" + Integer.parseInt(Datos[27]) + "' ");
            correcto = true;
        } catch (Exception ex) {
            System.out.printf(ex.toString());
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }


    public TicketDB mostrarDatos(String Ticket, int id) {
        TicketHelperDB dbHelper = new TicketHelperDB(context, Ticket);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        TicketDB datos = null;
        Cursor cursordatos = null;
        cursordatos = db.rawQuery("SELECT * FROM " + TABLE_TICKETS + " WHERE id = " + id + " LIMIT 1 ", null);

        if (cursordatos.moveToFirst()) {
            do {
                datos = new TicketDB();
                datos.setId(cursordatos.getString(1));
                datos.setNomenclatura(cursordatos.getString(2));
                datos.setNombreSeg(cursordatos.getString(3));
                datos.setPuestoSeg(cursordatos.getString(4));
                datos.setTipoServicio(cursordatos.getString(5));
                datos.setTrimestre(cursordatos.getString(6));
                datos.setPeriodicidad(cursordatos.getString(7));
                datos.setTicket(cursordatos.getString(8));
                datos.setAduana(cursordatos.getString(9));
                datos.setEquipo(cursordatos.getString(10));
                datos.setUbicacion(cursordatos.getString(11));
                datos.setSerie(cursordatos.getString(12));
                datos.setFechaInicio(cursordatos.getString(13));
                datos.setHoraInicio(cursordatos.getString(14));
                datos.setFechaFin(cursordatos.getString(15));
                datos.setHoraFin(cursordatos.getString(16));
                datos.setFalla(cursordatos.getString(17));
                datos.setFechaLLamada(cursordatos.getString(18));
                datos.setHoraLLamada(cursordatos.getString(19));
                datos.setFechaSitio(cursordatos.getString(20));
                datos.setHoraSitio(cursordatos.getString(21));
                datos.setNombreTecnico(cursordatos.getString(22));

            } while (cursordatos.moveToNext());

        }
        cursordatos.close();
        return datos;
    }


    public int TamañoTabla(String Ticket) {
        TicketHelperDB dbHelper = new TicketHelperDB(context, Ticket);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_TICKETS);
        return numRows;

    }

    public boolean eliminarTicket(int id, String Ticket) {

        boolean correcto = false;
        TicketHelperDB dbHelper = new TicketHelperDB(context, Ticket);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_TICKETS + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }
        context.deleteDatabase(Ticket);

        return correcto;
    }
}
