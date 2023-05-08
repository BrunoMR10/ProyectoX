package com.bmr.xproyect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.bmr.xproyect.Adapters.AdapterFotos;
import com.bmr.xproyect.DataBases.FotosManageDB;
import com.bmr.xproyect.Datos.Datos;
import com.bmr.xproyect.Entidades.DatosFoto;
import com.bmr.xproyect.Entidades.Fotos;

import java.io.File;
import java.util.ArrayList;

public class FotosExtra extends AppCompatActivity {
    String []Datos;
    Datos dt = new Datos();
    RecyclerView FotosList;
    AdapterFotos adapterFotos;
    DatosFoto Data;
    boolean Reporte;
    String ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos_extra);
        FotosList = (RecyclerView) findViewById(R.id.FotosView);
        FotosList.setLayoutManager(new LinearLayoutManager(this));
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                Datos = extras.getStringArray("Datos");
                ID = extras.getString("ID");
                Reporte = extras.getBoolean("Reporte");
            }
        }
        else {
            Datos = (String[]) savedInstanceState.getSerializable("Datos");
            ID = (String) savedInstanceState.getSerializable("ID");
            Reporte = (boolean) savedInstanceState.getSerializable("Reporte");
        }
        if (ID!=null)ActualizaEstadoReporte();
        if (Datos == null){
            Datos = new String[dt.Datos.length];
            SharedPreferences sh = getSharedPreferences("Datos"+Datos[0], MODE_PRIVATE);
            for(int i=0;i<dt.Datos.length;i++){
                Datos[i] = sh.getString("Datos"+i, "");
            }
            MostrarFotos();
        }
        else{
            //GuardaDatosSP(Datos);
            MostrarFotos();
        }
    }
    private void ActualizaEstadoReporte(){
        SharedPreferences sh = getSharedPreferences("Datos"+Datos[0], MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        myEdit.putBoolean("ReporteFoto"+(ID), Reporte);
        System.out.println(ID);
        myEdit.commit();
    }
    private void MostrarFotos(){
        ArrayList<Fotos> ListaFotos = new ArrayList<>();
        FotosManageDB base = new FotosManageDB (FotosExtra.this,Datos[0]);
        int size = base.TamañoTabla(Datos[0]);
        int i =0;
        Boolean ReporteFoto;
        SharedPreferences sh = getSharedPreferences("Datos"+Datos[0] , MODE_PRIVATE);
        while (i<size) {
            Data = base.mostrarDatos(Datos[0], i + 1);
            ReporteFoto=sh.getBoolean("ReporteFoto"+(i+1),true);
            if (Data != null) {
                Bitmap bitmapi;
                Datos[1]="ExtraEdit";
                try {
                    String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
                    Uri uri = Uri.fromFile(new File(ExternalStorageDirectory + "ProyectoX/Preventivo/" + Datos[0] + "/" + Datos[0] + "(CF)/" + "Extra"+Data.getID()+ ".png"));
                    bitmapi = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                }catch (Exception e){
                    bitmapi = null;
                    //Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
                }
                Fotos fotos = null;
                fotos = new Fotos();
                fotos.setDescripcion2(Data.getDescripcion());
                fotos.setId2(Data.getID());
                fotos.setFoto2(bitmapi);
                fotos.setDatos(Datos);
                fotos.setReporteFotos(ReporteFoto);
                ListaFotos.add(fotos);
                adapterFotos = new AdapterFotos(ListaFotos);
                FotosList.setAdapter(adapterFotos);

            }
            else{
                Toast.makeText(this,"Error al leer base",Toast.LENGTH_SHORT).show();
            }
            i++;
        }
    }
    private void GuardaDatosSP(String [] Datos){
        SharedPreferences sharedPreferences = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
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
    public void AgregaFoto(View view){
        Intent i = new Intent(this,SelectFotoExtra.class);
        Datos[1]="FotosExtra";
        i.putExtra("Datos",Datos);
        startActivity(i);
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,PreventivoRF.class);
        Datos[1]="Preventivo";
        i.putExtra("Datos",Datos);
        startActivity(i);
    }

}