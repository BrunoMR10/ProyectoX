package com.bmr.xproyect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bmr.xproyect.Adapters.AdapterFotos;
import com.bmr.xproyect.DataBases.FotosManageDB;
import com.bmr.xproyect.Datos.Datos;
import com.bmr.xproyect.Documentos.Correctivo;
import com.bmr.xproyect.Entidades.DatosFoto;
import com.bmr.xproyect.Entidades.Fotos;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class CorrectivoRF extends AppCompatActivity implements View.OnClickListener {
    Datos dt = new Datos();
    Correctivo correctivo = new Correctivo();
    String []Datos;
    RecyclerView FotosView;
    TextView TrimestreRFC,TicketRFC,FechaRFC,
            AduanaRFC,EquipoRFC,SerieRFC,UbicacionRFC;
    AdapterFotos adapterFotos;
    DatosFoto Data;
    boolean Reporte;
    String ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correctivo_rf);
        TrimestreRFC = (TextView) findViewById(R.id.TrimestreRFC);
        TicketRFC = (TextView) findViewById(R.id.TicketRFC);
        FechaRFC = (TextView) findViewById(R.id.FechaRFC);
        AduanaRFC = (TextView) findViewById(R.id.AduanaRFC);
        EquipoRFC = (TextView) findViewById(R.id.EquipoRFC);
        SerieRFC = (TextView) findViewById(R.id.SerieRFC);
        UbicacionRFC = (TextView) findViewById(R.id.UbicacionRFC);
        FotosView = (RecyclerView) findViewById(R.id.FotosRFC);
        FotosView.setLayoutManager(new LinearLayoutManager(this));
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
        if (Datos == null){
            Datos = new String[dt.Datos.length];
            SharedPreferences sh = getSharedPreferences("Datos"+Datos[0], MODE_PRIVATE);
            for(int i=0;i<dt.Datos.length;i++){
                Datos[i] = sh.getString("Datos"+i, "");
            }
        }
        else{
            SharedPreferences sh = getSharedPreferences("Datos" + Datos[0], MODE_PRIVATE);
            if (sh.getString("Datos4", "").equals("")||Actualiza()) {
                GuardaDatosSP();
            }else{
                Datos[6] = sh.getString("Datos6", "Seleccione personal");
                for (int i = 7; i<27; i++){
                    Datos[i] = sh.getString("Datos"+i, "");
                }
            }

        }
        if (ID!=null)ActualizaEstadoReporte();
        View AñadeFoto = findViewById(R.id.añadefoto);
        AñadeFoto.setOnClickListener(this);
        View SubeFotos = findViewById(R.id.Subefotos);
        SubeFotos.setOnClickListener(this);
        View CreaDocumento = findViewById(R.id.CreaDocumento);
        CreaDocumento.setOnClickListener(this);
        View RFC= findViewById(R.id.RFC);
        RFC.setOnClickListener(this);
        ConfiguraIncio();

    }
    private void ActualizaEstadoReporte(){
        SharedPreferences sh = getSharedPreferences("Datos"+Datos[0], MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        myEdit.putBoolean("ReporteFoto"+(ID), Reporte);
        System.out.println(ID);
        myEdit.commit();
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.añadefoto:
                AñadeFoto();
                break;
            case R.id.Subefotos:
                SubeFotosC();
                break;
            case R.id.CreaDocumento:
                CrearArchivo();
                break;
            case R.id.RFC:
                ToRDC();
                break;
        }
    }
    private boolean Actualiza(){
        SharedPreferences sh2 = getSharedPreferences("Datos", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh2.edit();
        if (sh2.getString("Actualiza"+Datos[12], "").equals("")){
            return false;
        }
        else{
            myEdit.putString("Actualiza"+Datos[12], "");
            myEdit.commit();
            return true;
        }

    }
    private void GuardaDatosSP(){
        SharedPreferences sharedPreferences = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        for(int i=0;i<Datos.length;i++){
            myEdit.putString("Datos"+i, Datos[i]);
        }
        myEdit.commit();
    }
    private void ConfiguraIncio(){
        TrimestreRFC.setText("0"+Datos[10]);
        TicketRFC.setText(Datos[12]);
        AduanaRFC.setText(Datos[13]);
        EquipoRFC.setText(Datos[14]);
        UbicacionRFC.setText(Datos[15]);
        SerieRFC.setText(Datos[16]);
        FechaRFC.setText(Datos[17]);
        MostrarFotos();

    }

    private void MostrarFotos(){
        ArrayList<Fotos> ListaFotos = new ArrayList<>();
        FotosManageDB base = new FotosManageDB (CorrectivoRF.this,Datos[0]);
        int size = base.TamañoTabla(Datos[0]);
        int i =0;
        Boolean ReporteFoto;
        SharedPreferences sh = getSharedPreferences("Datos"+Datos[0] , MODE_PRIVATE);
        while (i<size) {
            Data = base.mostrarDatos(Datos[0], i + 1);
            ReporteFoto=sh.getBoolean("ReporteFoto"+(i+1),true);
            if (Data != null) {
                Bitmap bitmapi;
                Datos[1]="CorrectivosEdit";
                try {
                    String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
                    Uri uri = Uri.fromFile(new File(ExternalStorageDirectory + "ProyectoX/Correctivo/" + Datos[0] + "/" + Datos[0] + "(CF)/" + "foto"+Data.getID()+ ".png"));
                    bitmapi = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                }catch (Exception e){
                    bitmapi = null;
                    Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
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
                FotosView.setAdapter(adapterFotos);

            }
            else{
                Toast.makeText(this,"Error al leer base",Toast.LENGTH_SHORT).show();
            }
            i++;
        }
    }

    private void AñadeFoto(){
            Intent i = new Intent(this,SelectFotoExtra.class);
            Datos[1]="Correctivo";
            i.putExtra("Datos",Datos);
            startActivity(i);
    }
    private void CrearArchivo(){



        try {
            Bitmap bitmap;
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.seguritech);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bitmapData = stream.toByteArray();
            ImageData imageData = ImageDataFactory.create(bitmapData);

            SharedPreferences sh = getSharedPreferences("Datos"+Datos[0], MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sh.edit();
            String id = sh.getString("idExtra", "0");
            correctivo.CreaArchivo(Datos,imageData,Integer.parseInt(id),ObtenComentarios2());



        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }
        Datos[1]="Correctivo";
        Datos[29]="RFD";
        Intent intent = new Intent(this,VisorPDF.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    private String[]  ObtenComentarios2(){

        SharedPreferences sh = getSharedPreferences("Datos"+Datos[0], MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        int ID = sh.getInt("ID", 1)+1;
        String [] Descripciones = new String[ID];
        String Desc;
        Boolean ReporteFoto;
        for (int i = 1;i<ID;i++){
            ReporteFoto=sh.getBoolean("ReporteFoto"+(i),true);
            Desc = sh.getString("ComentarioFoto"+(i),"");
            if (ReporteFoto) Descripciones[i-1] = Desc;
            else Descripciones[i-1] = "";
            System.out.println("Desc:"+Descripciones[i-1]);
        }
        return Descripciones;

    }
    private String[]  ObtenComentarios(){
        ArrayList<Fotos> ListaFotos = new ArrayList<>();
        FotosManageDB base = new FotosManageDB (CorrectivoRF.this,Datos[0]);
        int size = base.TamañoTabla(Datos[0]);
        int i =0;
        String [] Descripciones = new String[size];
        while (i<size) {
            Data = base.mostrarDatos(Datos[0], i + 1);
            if (Data != null) {

                Descripciones[i] = Data.getDescripcion();

            }
            else{
                return null;
            }
            i++;
        }
        return Descripciones;
    }
    public void EditaDatos(View view){
        Datos[1]="RFC";
        Intent intent = new Intent(this,EditaDatos.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    private void ToRDC(){
        Datos[6] = "Seleccione personal";
        Datos[7] = "";
        Intent intent = new Intent(this,CorrectivoRD.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    private void SubeFotosC(){
        Datos[1]="Correctivo";
        Datos[29]="RFD";
        Intent intent = new Intent(this,VisorPDF.class);
        intent.putExtra("Fotos","Fotos");
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        SharedPreferences sh = getSharedPreferences("Datos", MODE_PRIVATE);
        String CorreoElectronico = sh.getString("CorreoElectronicoSeguritech", "");
        String Numero= sh.getString("Telefono", "");
        Datos[8] =CorreoElectronico;
        Datos[9]=Numero;
        Datos[1]="Correctivo";
        Intent i = new Intent(this,Ticketslist.class);
        i.putExtra("Datos",Datos);
        startActivity(i);
    }
}