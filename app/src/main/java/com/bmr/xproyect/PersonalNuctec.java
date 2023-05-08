package com.bmr.xproyect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.bmr.xproyect.Datos.Datos;

public class PersonalNuctec extends AppCompatActivity {
    com.bmr.xproyect.Datos.Datos dt = new Datos();
    String[]Datos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_anam);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                Datos = extras.getStringArray("Datos");
            }
        }
        else {
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
            //GuardaDatosSP(Datos);
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
    public void ToFavoritos(View view){
     if (Datos[1].equals("Interno")){
            Datos[1]="InternoNuctecFavoritos";
        }
        else{
            Datos[1]="FavoritosNuctec";
        }

        Intent intent = new Intent(this,ListaUsuarios.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);

    }
    public void ToGeneral(View view){
         if (Datos[1].equals("Interno")){
            Datos[1]="InternoNuctecGeneral";
        }
        else{
            Datos[1]="GeneralNuctec";
        }
        Intent intent = new Intent(this,ListaUsuarios.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);

    }


    @Override
    public void onBackPressed() {
        if (Datos[1].equals("Preventivo")){
            Intent intent = new Intent(this,PreventivoRD.class);
            intent.putExtra("Datos",Datos);
            startActivity(intent);
        }else if (Datos[1].equals("Correctivo")){
            Intent intent = new Intent(this,CorrectivoRD.class);
            intent.putExtra("Datos",Datos);
            startActivity(intent);
        }
        else if (Datos[1].equals("Interno")){
            Intent intent = new Intent(this,InternoRD.class);
            intent.putExtra("Datos",Datos);
            startActivity(intent);
        }

        else{
            Intent intent = new Intent(this,PantallaPrincipal.class);
            intent.putExtra("Datos",Datos);
            startActivity(intent);
        }

    }
}