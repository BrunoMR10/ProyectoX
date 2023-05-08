package com.bmr.xproyect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bmr.xproyect.Datos.Datos;

public class EditaTexto extends AppCompatActivity {
    Datos dt = new Datos();
    EditText Texto;
    String [] Datos;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_texto);
        Texto = (EditText) findViewById(R.id.EditaActividad);
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
            SharedPreferences sh = getSharedPreferences("Datos"+Datos[0], MODE_PRIVATE);
            for(int i=0;i<dt.Datos.length;i++){
                Datos[i] = sh.getString("Datos"+i, "");
            }

        }
        else{
            //GuardaDatosSP(Datos);
            //MostrarFotos();
        }
        i= Integer.parseInt(Datos[27]);
        Texto.setText(Datos[28]);
    }
    public void Cancel(View view){
        Intent intent = new Intent(this,PreventivoRD.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    public void Hecho (View view){
        SharedPreferences sh = getSharedPreferences("Datos" + Datos[0], MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        myEdit.putString("Comentario"+i, Texto.getText().toString());
        myEdit.commit();
        Intent intent = new Intent(this,PreventivoRD.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
}