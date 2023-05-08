package com.bmr.xproyect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.View;
import android.widget.Toast;

import com.bmr.xproyect.Datos.Datos;

public class PersonalANAM extends AppCompatActivity {
    Datos dt = new Datos();
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
        for(int i=0;i<Datos.length;i++){
            myEdit.putString("Datos"+i, Datos[i]);
        }
        myEdit.commit();
    }
    public void ToFavoritos(View view){
        if (Datos[1].equals("Preventivo")){
            Datos[1]="PreventivoANAMFavoritos";
        }else if (Datos[1].equals("Correctivo")){
            Datos[1]="CorrectivoANAMFavoritos";
        }
        else if (Datos[1].equals("Interno")){
            Datos[1]="InternoANAMFavoritos";
        }
        else if (Datos[1].equals("ActaentregaANAM")){
            Datos[1]="ActaentregaANAM";
        }
        else{
            Datos[1]="Favoritos";
        }

        Intent intent = new Intent(this,ListaUsuarios.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);

    }
    public void ToGeneral(View view){
        if (Datos[1].equals("Preventivo")){
            Datos[1]="PreventivoANAMGeneral";
        }else if (Datos[1].equals("Correctivo")){
            Datos[1]="CorrectivoANAMGeneral";
        }
        else if (Datos[1].equals("Interno")){
            Datos[1]="InternoANAMGeneral";
        }
        else if (Datos[1].equals("ActaentregaANAM")){
            Datos[1]="ActaentregaANAM";
        }
        else{
            Datos[1]="General";
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
        else if (Datos[1].equals("ActaentregaANAM")){
            Intent intent = new Intent(this,ActadeEntrega.class);
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