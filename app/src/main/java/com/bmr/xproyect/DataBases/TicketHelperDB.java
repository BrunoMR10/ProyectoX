package com.bmr.xproyect.DataBases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TicketHelperDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION =1;
    public static final String TABLE_TICKETS="t_tickets";


    public TicketHelperDB(Context context, String name) {
        super(context, name, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+ TABLE_TICKETS +"("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT," + //0
                "id2 STRING NOT NULL,"+          //1
                "Nomenclatura STRING NOT NULL,"+          //2
                "NombreSeg STRING NOT NULL,"+             //3
                "PuestoSeg STRING NOT NULL,"+             //4
                "TipoServicio STRING NOT NULL,"+          //5
                "Trimestre STRING NOT NULL,"+          //6
                "Periodicidad STRING NOT NULL,"+          //7
                "Ticket STRING NOT NULL,"+          //8
                "Aduana STRING NOT NULL,"+          //9
                "Equipo STRING NOT NULL,"+          //10
                "Ubicacion STRING NOT NULL,"+          //11
                "Serie STRING NOT NULL,"+          //12
                "FechaInicio STRING NOT NULL,"+          //13
                "HoraInicio STRING NOT NULL,"+          //14
                "FechaFin STRING NOT NULL,"+          //15
                "HoraFin STRING NOT NULL,"+          //16
                "Falla STRING NOT NULL,"+          //17
                "FechaLLamada STRING NOT NULL,"+          //18
                "HoraLLamada STRING NOT NULL,"+          //19
                "FechaSitio STRING NOT NULL,"+          //20
                "HoraSitio STRING NOT NULL,"+          //21
                "NombreTecnico STRING NOT NULL)");        //22
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
