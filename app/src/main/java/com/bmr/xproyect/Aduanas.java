package com.bmr.xproyect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.bmr.xproyect.Adapters.AdapterAduanas;
import com.bmr.xproyect.Adapters.AdapterUsuarios;
import com.bmr.xproyect.DataBases.FavoritosManageDB;
import com.bmr.xproyect.Datos.Datos;
import com.bmr.xproyect.Entidades.Users;
import com.bmr.xproyect.Herramientas.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Aduanas extends AppCompatActivity implements SearchView.OnQueryTextListener{
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference refAduanes;
    RecyclerView Aduanasview;
    SearchView SearchAduanas;
    AdapterAduanas adapterAduanas;
    String Where;
    String []Datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aduanas);
        refAduanes = database.getReference("Aduanas");
        Aduanasview = (RecyclerView) findViewById(R.id.Aduanasview);
        Aduanasview.setLayoutManager(new LinearLayoutManager(this));
        SearchAduanas = findViewById(R.id.SearchAduanas);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                Where = extras.getString("Where");
                Datos = extras.getStringArray("Datos");
            }
        }
        else {
            Where = (String) savedInstanceState.getSerializable("Where");
            Datos = (String[]) savedInstanceState.getSerializable("Datos");
        }
        MuestraAduanas();
    }

    ////MUESTRA DE LISTA
    private void MuestraAduanas(){

      refAduanes.addListenerForSingleValueEvent(new ValueEventListener() {
          ArrayList<com.bmr.xproyect.Entidades.Aduanas> listaAduanas = new ArrayList<>();
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
              String Aduana,Entidad,Municipio,Calle,Colonia,CP;
              for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                  Aduana = postSnapshot.child("Aduana").getValue(String.class);
                  Entidad = postSnapshot.child("Entidad").getValue(String.class);
                  Municipio = postSnapshot.child("Municipio").getValue(String.class);
                  Calle = postSnapshot.child("Calle").getValue(String.class);
                  Colonia = postSnapshot.child("Colonia").getValue(String.class);
                  CP = postSnapshot.child("CP").getValue(String.class);

                  System.out.println(Aduana);

                  com.bmr.xproyect.Entidades.Aduanas aduanas = null;
                  aduanas = new com.bmr.xproyect.Entidades.Aduanas();
                  aduanas.setAduana(Aduana);
                  aduanas.setEntidad(Entidad);
                  aduanas.setMunicipio(Municipio);
                  aduanas.setCalle(Calle);
                  aduanas.setColonia(Colonia);
                  aduanas.setCP(CP);
                  if (Datos!=null) aduanas.setDatos(Datos);

                  if (Where!=null){
                      aduanas.setWhere(Where);
                  }
                  listaAduanas.add(aduanas);

              }
              adapterAduanas = new AdapterAduanas(listaAduanas);
              Aduanasview.setAdapter(adapterAduanas);
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      });



    }

    public void RegistraAduana(View view){
        Intent intent = new Intent(this,RegistraAduana.class);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
    @Override
    public void onBackPressed() {
        Intent i;
        if(Where.equals("Actadeentrega")) i = new Intent(this,ActadeEntrega.class);
        else {i = new Intent(this,PantallaPrincipal.class);
               i.putExtra("Datos",Datos);}
        startActivity(i);
    }
}