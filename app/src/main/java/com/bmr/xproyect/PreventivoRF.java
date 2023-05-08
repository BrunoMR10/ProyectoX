package com.bmr.xproyect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bmr.xproyect.Adapters.RFPAdapter;
import com.bmr.xproyect.DataBases.FotosManageDB;
import com.bmr.xproyect.Datos.Datos;
import com.bmr.xproyect.Documentos.Preventivo;
import com.bmr.xproyect.Entidades.DatosFoto;
import com.bmr.xproyect.Entidades.Fotos;
import com.bmr.xproyect.Entidades.RFP;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class PreventivoRF extends AppCompatActivity {
    Datos dt = new Datos();
    Preventivo doc = new Preventivo();
    RecyclerView FotosView;
    TextView TrimestreRFP,TicketRFP,FechaRFP,PeriodicidadRFP,
    AduanaRFP,EquipoRFP,SerieRFP,UbicacionRFP;
    String []Datos;
    RFPAdapter adapterFotos;
    ImageView FotoGenerador;
    DatosFoto Data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preventivo_rf);
        TrimestreRFP = (TextView) findViewById(R.id.TrimestreRFP);
        TicketRFP = (TextView) findViewById(R.id.TicketRFP);
        FechaRFP = (TextView) findViewById(R.id.FechaRFP);
        PeriodicidadRFP = (TextView) findViewById(R.id.PeriodicidadRFP);
        AduanaRFP = (TextView) findViewById(R.id.AduanaRFP);
        EquipoRFP = (TextView) findViewById(R.id.EquipoRFP);
        SerieRFP = (TextView) findViewById(R.id.SerieRFP);
        UbicacionRFP = (TextView) findViewById(R.id.UbicacionRFP);
        FotosView = (RecyclerView) findViewById(R.id.FotosRFP);
        FotosView.setLayoutManager(new LinearLayoutManager(this));
        FotoGenerador = (ImageView)findViewById(R.id.FotoGenerador);

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



        ConfiguraIncio();
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
    public void SubirFotos(View view){
        Toast.makeText(this,"SubiendoFotos",Toast.LENGTH_SHORT).show();
        if (ValidaFotos(Datos[0])){
            Datos[1]="Preventivo";
            Datos[29]="RFD";
            Intent intent = new Intent(this,VisorPDF.class);
            intent.putExtra("Fotos","Fotos");
            intent.putExtra("Datos",Datos);
            startActivity(intent);
        }else{

        }
    }
    public void TORDigital(View view){
        Datos[6] = "Seleccione personal";
        Datos[7] = "";
        Intent intent = new Intent(this,PreventivoRD.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    public void EditaDatos(View view){
        Datos[1]="RF";
        Intent intent = new Intent(this,EditaDatos.class);
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
        FotosManageDB base = new FotosManageDB (PreventivoRF.this,Datos[0]);
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
    private void GuardaDatosSP(){
        SharedPreferences sharedPreferences = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        for(int i=0;i<Datos.length;i++){
            myEdit.putString("Datos"+i, Datos[i]);
        }
        myEdit.commit();
    }
    private void ConfiguraIncio(){
        TrimestreRFP.setText("0"+Datos[10]);
        PeriodicidadRFP.setText(Datos[11]);
        TicketRFP.setText(Datos[12]);
        AduanaRFP.setText(Datos[13]);
        EquipoRFP.setText(Datos[14]);
        UbicacionRFP.setText(Datos[15]);
        SerieRFP.setText(Datos[16]);
        FechaRFP.setText(Datos[17]);
        MostrarFotos(Datos[0]);

    }
    public void TomaFotoGenerador(View view){
        int ID;
        if (Datos[11].equals("Mensual")) {
            ID = 9;
        }else{
            ID = 11;
        }
        Datos[27]= String.valueOf(ID);
        Intent intent = new Intent(this, SelectFoto.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    public void FotosExtra(View view){
        Datos[1]="Preventivo";
        Intent intent = new Intent(this, FotosExtra.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    public void CreaRFP(View view){
        if (ValidaFotos(Datos[0])){
            Toast.makeText(this,"Fotos Completas",Toast.LENGTH_SHORT).show();
            ImageData imageData;
            try {
                Bitmap bitmap;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.seguritech);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bitmapData = stream.toByteArray();
                imageData = ImageDataFactory.create(bitmapData);
                SharedPreferences sh = getSharedPreferences("Datos"+Datos[0], MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
                int id = Integer.parseInt(sh.getString("idExtra", "0"));
                if (Datos[11].equals("Mensual")) {
                    doc.CreaArchivo(Datos,imageData,dt.ComentariosRFPMensual,id,ObtenComentarios2());
                }else if (Datos[11].equals("Trimestral")){
                    doc.CreaArchivo(Datos,imageData,dt.ComentariosRFPTrimestral,id,ObtenComentarios2());
                }
                else{
                    doc.CreaArchivo(Datos,imageData,dt.ComentariosRFPAnual,id,ObtenComentarios2());
                }
                Datos[1]="Preventivo";
                Datos[29]="RFD";
                Intent intent = new Intent(this,VisorPDF.class);
                intent.putExtra("Datos",Datos);
                startActivity(intent);
            }catch (Exception e){
                Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this,"Complete las fotos",Toast.LENGTH_SHORT).show();
        }
    }
    private boolean ValidaFotos(String Nomenclatura){
        String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
        String rutacarpeta = "ProyectoX/Preventivo/" + Nomenclatura + "/" + Nomenclatura + "(CF)/";
        int NumeroFotos;
        ArrayList<RFP> ListaFotos = new ArrayList<>();
        if (Datos[11].equals("Mensual")) {
            NumeroFotos = 8;
        }else{
            NumeroFotos = 10;
        }

        for (int i=0;i<NumeroFotos+1;i++) {
            Bitmap bitmapi = null;
            try {
                String Foto = "foto" + (i + 1);
                Uri uri = Uri.fromFile(new File(ExternalStorageDirectory + rutacarpeta + Foto + ".png"));
                bitmapi = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (Exception e) {
               return false;
            }
        }
     return true;
    }
    private void MostrarFotos(String Nomenclatura) {
        String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
        String rutacarpeta =  rutacarpeta = "ProyectoX/Preventivo/" + Nomenclatura + "/" + Nomenclatura + "(CF)/";;
        int NumeroFotos;
        ArrayList<RFP> ListaFotos = new ArrayList<>();
        if (Datos[11].equals("Mensual")) {
            NumeroFotos = 8;
        }else{
            NumeroFotos = 10;
        }
        String Comen1;
        String Comen2;

            for (int i=0;i<NumeroFotos;i=i+2){
                Bitmap bitmapi,bitmapi2 = null;
                if (Datos[11].equals("Mensual")) {
                    Comen1 =dt.ComentariosRFPMensual[i];
                    Comen2 =dt.ComentariosRFPMensual[i+1];
                }else if (Datos[11].equals("Trimestral")){
                    Comen1 =dt.ComentariosRFPTrimestral[i];
                    Comen2 =dt.ComentariosRFPTrimestral[i+1];
                }
                else{
                   Comen1 =dt.ComentariosRFPAnual[i];
                   Comen2 =dt.ComentariosRFPAnual[i+1];
                }
                try{
                    String Foto1 = "foto"+(i+1);
                    String Foto2 = "foto"+(i+2);
                    Uri uri = Uri.fromFile(new File(ExternalStorageDirectory + rutacarpeta+Foto1+".png"));
                    bitmapi = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    try{
                        Uri uri2 = Uri.fromFile(new File(ExternalStorageDirectory + rutacarpeta+Foto2+".png"));
                        bitmapi2 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri2);
                    }catch (Exception e){
                        bitmapi2 = null;
                    }
                }catch (Exception e){
                    bitmapi = null;
                }
                RFP fotos = null;
                fotos = new RFP();
                fotos.setComentario1(Comen1);
                fotos.setComentario2(Comen2);
                fotos.setFoto1(bitmapi);
                fotos.setFoto2(bitmapi2);
                fotos.setID1(i+1);
                fotos.setID2(i+2);
                fotos.setDatos(Datos);
                ListaFotos.add(fotos);
                adapterFotos = new RFPAdapter(ListaFotos);
                FotosView.setAdapter(adapterFotos);

                try{
                    String Foto = "foto"+String.valueOf(NumeroFotos+1);
                    Uri uri = Uri.fromFile(new File(ExternalStorageDirectory + rutacarpeta+Foto+".png"));
                    bitmapi = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    FotoGenerador.setImageBitmap(bitmapi);
                }catch (Exception e){
                    bitmapi = null;
                }

            }

        }
    @Override
    public void onBackPressed() {
        SharedPreferences sh = getSharedPreferences("Datos", MODE_PRIVATE);
        String CorreoElectronico = sh.getString("CorreoElectronicoSeguritech", "");
        String Numero= sh.getString("Telefono", "");
        Datos[8] =CorreoElectronico;
        Datos[9]=Numero;
        Datos[1]="Preventivo";
        Intent i = new Intent(this,Ticketslist.class);
        i.putExtra("Datos",Datos);
        startActivity(i);
    }

}