package com.bmr.xproyect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bmr.xproyect.Herramientas.Firebase;

public class RegistraAduana extends AppCompatActivity {
    EditText Aduanaget,EntidadFederativaget,Municipioget,Calleget,Coloniaget,CPget;
    String Aduana,Entidad,Municipio,Calle,Colonia,CP;
    Firebase fb = new Firebase();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra_aduana);
        Aduanaget = (EditText) findViewById(R.id.Aduanaget);
        EntidadFederativaget = (EditText) findViewById(R.id.EntidadFederativaget);
        Municipioget = (EditText) findViewById(R.id.Municipioget);
        Calleget = (EditText) findViewById(R.id.Calleget);
        Coloniaget = (EditText) findViewById(R.id.Coloniaget);
        CPget = (EditText) findViewById(R.id.CPget);
    }
    private boolean ValidaDatos(){
        boolean valida = false;
        Aduana = Aduanaget.getText().toString();
        Entidad = EntidadFederativaget.getText().toString();
        Municipio = Municipioget.getText().toString();
        Calle = Calleget.getText().toString();
        Colonia = Coloniaget.getText().toString();
        CP = CPget.getText().toString();
        if (Aduana.isEmpty()){
            Aduanaget.setError("El campo es requerido");
            Aduanaget.requestFocus();
        }
        else if (Entidad.isEmpty()){
            EntidadFederativaget.setError("El campo es requerido");
            EntidadFederativaget.requestFocus();
        }
        else if (Municipio.isEmpty()){
            Municipioget.setError("El campo es requerido");
            Municipioget.requestFocus();
        }
        else if (Calle.isEmpty()){
            Calleget.setError("El campo es requerido");
            Calleget.requestFocus();
        }
        else if (Colonia.isEmpty()){
            Coloniaget.setError("El campo es requerido");
            Coloniaget.requestFocus();
        }
        else if (CP.isEmpty()){
            CPget.setError("El campo es requerido");
            CPget.requestFocus();
        }
        else return true;

        return  valida;
    }
    public void RegistraAduana(View view) {
        if (ValidaDatos()) {
            String[] DatosAduana = new String[]{Aduana, Entidad, Municipio, Calle, Colonia, CP};
            fb.RegistraAduanas(DatosAduana,this);

        }
        else Toast.makeText(this,"Complete los campos requeridos",Toast.LENGTH_SHORT).show();
    }
}