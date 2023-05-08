package com.bmr.xproyect.DataBases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class FavoritosHelpeDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION =1;
    public static final String TABLE_FAVORITOSANAM="t_favoritos";

    public FavoritosHelpeDB(Context context, String name) {
        super(context, name, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_FAVORITOSANAM +"("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Nombre STRING NOT NULL,"+
                "Numero STRING NOT NULL,"+
                "CorreoElectronico STRING NOT NULL,"+
                "Puesto STRING NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
