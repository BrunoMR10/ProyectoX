package com.bmr.xproyect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bmr.xproyect.Datos.Datos;

import java.sql.DatabaseMetaData;

public class Servicios extends AppCompatActivity {
    String[]Datos;
    Datos dt = new Datos();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                Datos = extras.getStringArray("Datos");
            }
        }
        else{
            Datos = (String[]) savedInstanceState.getSerializable("Datos");
        }
        if (Datos == null){
            Datos = new String[dt.Datos.length];
            SharedPreferences sh = getSharedPreferences("Datos", MODE_PRIVATE);
            for(int i=0;i<dt.Datos.length;i++){
                Datos[i] = sh.getString("Datos"+i, "");
            }

        }
        else{
            GuardaDatosSP(Datos);
        }

    }
    private void GuardaDatosSP(String [] Datos){
        SharedPreferences sharedPreferences = getSharedPreferences("Datos",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("NombreCompleto", Datos[4]);
        myEdit.putString("Puesto", Datos[5]);;
        myEdit.putString("CorreoElectronico", Datos[8]);
        myEdit.putString("Telefono", Datos[9]);
        for(int i=0;i<Datos.length;i++){
            myEdit.putString("Datos"+i, Datos[i]);
        }
        myEdit.commit();
    }
    public void ToTicketListPrev(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("Datos",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        Intent i = new Intent(this,Ticketslist.class);
        Datos[1]="Preventivo";
        myEdit.putString("Datos1", Datos[1]);
        i.putExtra("Datos",Datos);
        startActivity(i);
    }
    public void ToTicketListCorrect(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("Datos",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        Intent i = new Intent(this,Ticketslist.class);
        Datos[1]="Correctivo";
        myEdit.putString("Datos1", Datos[1]);
        i.putExtra("Datos",Datos);
        startActivity(i);
    }
    public void ToTicketListIntero(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("Datos",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        Intent i = new Intent(this,Ticketslist.class);
        Datos[1]="Interno";
        myEdit.putString("Datos1", Datos[1]);
        i.putExtra("Datos",Datos);
        startActivity(i);
    }
    public void ToActadeEntrega(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("Datos",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        Intent i = new Intent(this,ActadeEntrega.class);
        Datos[1]="ActaEntrega";
        myEdit.putString("Datos1", Datos[1]);
        i.putExtra("Datos",Datos);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        SharedPreferences sh = getSharedPreferences("Datos", MODE_PRIVATE);
        String CorreoElectronico = sh.getString("CorreoElectronicoSeguritech", "");
        String Numero= sh.getString("Telefono", "");
        Datos[8] =CorreoElectronico;
        Datos[9]=Numero;
        Intent i = new Intent(this,PantallaPrincipal.class);
        i.putExtra("Datos",Datos);
        startActivity(i);
    }
}