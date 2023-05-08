package com.bmr.xproyect;

import androidx.annotation.NonNull;
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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bmr.xproyect.Adapters.AdapterActividades;
import com.bmr.xproyect.Datos.Datos;
import com.bmr.xproyect.Documentos.Preventivo;
import com.bmr.xproyect.Entidades.ComentariosRDP;
import com.bmr.xproyect.Entidades.RFP;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PreventivoRD extends AppCompatActivity {
    public FirebaseStorage storage = FirebaseStorage.getInstance();
    public StorageReference storageReference = storage.getReferenceFromUrl("gs://proyecto-x-933f4.appspot.com");
    Datos dt = new Datos();
    Preventivo doc = new Preventivo();
    RecyclerView ComentariosView;
    TextView TrimestreRDP,TicketRDP,FechaRDP,PeriodicidadRDP,
            AduanaRDP,EquipoRDP,SerieRDP,UbicacionRDP,FechaFinRDP;
    String []Datos;
    AdapterActividades AdapterAtividades;
    LinearLayout credenciales,estatusequipo;
    ImageView Foto1,Foto2,Foto3;
    Boolean Credenciales;
    TextView NombreSeguritech,NombreANAM,PuestoANAM,PuestoSeg;
    String nombreANAM,nombreSeguritech,puestoseguritech,puestoANAM,CambioCredencial;
    EditText HorasGenerador,ConteoInspecciones,Observaciones;
    ProgressBar cargaANAM1,cargaANAM2,cargaSeguritech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preventivo_rd);
        TrimestreRDP = (TextView) findViewById(R.id.TrimestreRDP);
        TicketRDP = (TextView) findViewById(R.id.TicketRDP);
        FechaRDP = (TextView) findViewById(R.id.FechaRDP);
        FechaFinRDP = (TextView) findViewById(R.id.FechaFinRDP);
        PeriodicidadRDP = (TextView) findViewById(R.id.PeriodicidadRDP);
        AduanaRDP = (TextView) findViewById(R.id.AduanaRDP);
        EquipoRDP = (TextView) findViewById(R.id.EquipoRDP);
        SerieRDP = (TextView) findViewById(R.id.SerieRDP);
        UbicacionRDP = (TextView) findViewById(R.id.UbicacionRDP);
        ComentariosView = (RecyclerView) findViewById(R.id.ActividadesRDP);
        credenciales = (LinearLayout)findViewById(R.id.credenciales);
        estatusequipo = (LinearLayout)findViewById(R.id.estatusequipo);
        Foto1 = (ImageView)findViewById(R.id.FotoFANAM) ;
        Foto2 = (ImageView)findViewById(R.id.FTANAM) ;
        Foto3 = (ImageView)findViewById(R.id.FotoFSeguritch);
        NombreSeguritech = (TextView) findViewById(R.id.NombreSeguritech);
        NombreANAM = (TextView) findViewById(R.id.NombreANAM);
        PuestoANAM = (TextView) findViewById(R.id.PuestoANAM);
        PuestoSeg= (TextView) findViewById(R.id.PuestoSeg);
        HorasGenerador = (EditText)findViewById(R.id.HorasGenerador);
        ConteoInspecciones = (EditText)findViewById(R.id.ConteoInspecciones);
        Observaciones = (EditText)findViewById(R.id.Observaciones);
        cargaANAM1 = (ProgressBar)findViewById(R.id.cargaANAM1);
        cargaANAM2 = (ProgressBar)findViewById(R.id.cargaANAM2);
        cargaSeguritech = (ProgressBar)findViewById(R.id.cargaSeguritech);
        Credenciales = true;
        ComentariosView.setLayoutManager(new LinearLayoutManager(this));
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                Datos = extras.getStringArray("Datos");
                CambioCredencial = extras.getString("CambioCredencial");
            }
        }
        else {
            Datos = (String[]) savedInstanceState.getSerializable("Datos");
            CambioCredencial = (String) savedInstanceState.getSerializable("CambioCredencial");
        }

        if (CambioCredencial!=null){
            GuardaDatosSP();
            nombreSeguritech = Datos[4];
            puestoseguritech = Datos[5];
            nombreANAM = Datos[6];
            puestoANAM = Datos[7];
            Credenciales = true;
            CredencialesRutina();
        }
        else{
            SharedPreferences sh = getSharedPreferences("Datos" + Datos[0], MODE_PRIVATE);
            nombreSeguritech = sh.getString("Datos4", "");
            puestoseguritech = sh.getString("Datos5", "");
            nombreANAM = sh.getString("Datos6", "Seleccione personal");
            puestoANAM = sh.getString("Datos7", "");
            if (nombreSeguritech.equals("")||Actualiza()) {
                nombreSeguritech = Datos[4];
                puestoseguritech = Datos[5];
                Datos[6]=nombreANAM ;
                Datos[7]=puestoANAM ;
                GuardaDatosSP();
            }
            else{
                String Nomenclatura = Datos[0];
                Datos = new String[dt.Datos.length];
                sh = getSharedPreferences("Datos"+Nomenclatura, MODE_PRIVATE);
                for(int i=0;i<dt.Datos.length;i++){
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
    public void TORFotograficoP(View view){
        Intent intent = new Intent(this,PreventivoRF.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    private void CredencialesRutina(){
        if(Credenciales){
            Credenciales=false;
            credenciales.setVisibility(View.VISIBLE);
            ComentariosView.setVisibility(View.GONE);
            estatusequipo.setVisibility(View.GONE);
            CargaImagen(Datos[4],Foto3,"Seguritech",cargaSeguritech);
            CargaImagen(nombreANAM+" "+"Frontal",Foto1,"ANAM",cargaANAM1);
            CargaImagen(nombreANAM+" "+"Trasera",Foto2,"ANAM",cargaANAM2);
            PuestoSeg.setText(puestoseguritech);
            PuestoANAM.setText(puestoANAM);
            NombreSeguritech.setText(nombreSeguritech);
            NombreANAM.setText(nombreANAM);

        }else{
            Credenciales=true;
            credenciales.setVisibility(View.GONE);
            estatusequipo.setVisibility(View.VISIBLE);
            ComentariosView.setVisibility(View.VISIBLE);

        }
    }
    public void Credenciales(View view){
     CredencialesRutina();
    }
    private void GuardaDatosSP(){
        SharedPreferences sharedPreferences = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        for(int i=0;i<Datos.length;i++){
            myEdit.putString("Datos"+i, Datos[i]);
        }
        myEdit.commit();
    }
    private void GuardaDatosSPComentarios(){
        SharedPreferences sharedPreferences = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        for(int i=0;i<dt.ComentariosRDPDef.length;i++){
            myEdit.putString("Comentario"+i, dt.ComentariosRDPDef[i]);
        }
        myEdit.commit();
    }
    public void ConfiguraIncio(){
        TrimestreRDP.setText("0"+Datos[10]);
        PeriodicidadRDP.setText(Datos[11]);
        TicketRDP.setText(Datos[12]);
        AduanaRDP.setText(Datos[13]);
        EquipoRDP.setText(Datos[14]);
        UbicacionRDP.setText(Datos[15]);
        SerieRDP.setText(Datos[16]);
        FechaRDP.setText(Datos[17]+" "+Datos[20]);
        FechaFinRDP.setText(Datos[17]+" "+Datos[18]);
        MostrarActividades();
        Observaciones.setText(ObtenEstatusEquipo()[0]);
        HorasGenerador.setText(ObtenEstatusEquipo()[1]);
        ConteoInspecciones.setText(ObtenEstatusEquipo()[2]);
    }
    public void Guarda(View view){
        GurdarEstatusEquipo();
    }
    private void GurdarEstatusEquipo(){
        SharedPreferences sharedPreferences = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("Observaciones", Observaciones.getText().toString());
        myEdit.putString("HorasGenerador", HorasGenerador.getText().toString());
        myEdit.putString("ConteoInspecciones", ConteoInspecciones.getText().toString());
        myEdit.commit();
        Toast.makeText(this,"Datos Guardados",Toast.LENGTH_SHORT).show();
    }
    private String[] ObtenEstatusEquipo(){
        SharedPreferences sh = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
        String[]EstatusEquipo = new String[]{
                sh.getString("Observaciones", ""),
                sh.getString("HorasGenerador", ""),
                sh.getString("ConteoInspecciones", "")
        };
       return EstatusEquipo;
    }
    private boolean ValidaDatos(){
        ObtenEstatusEquipo();
        if (nombreANAM.equals("Seleccione personal")){
            Toast.makeText(this,"Seleccione credencial de ANAM",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (ConteoInspecciones.getText().toString().equals("")){
            Toast.makeText(this,"Añada el conteo de inspecciones",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (HorasGenerador.getText().toString().equals("")){
            Toast.makeText(this,"Añada las horas del generador eléctrico",Toast.LENGTH_SHORT).show();
            return false;
        }

        else{
            return true;
        }
    }
    private void MostrarActividades(){
        String[]ComentariosDef = new String[dt.ComentariosRDPDef.length];
        SharedPreferences sh = getSharedPreferences("Datos" + Datos[0], MODE_PRIVATE);
        if(sh.getString("Comentario0", "").equals("")){
            Toast.makeText(this,"First",Toast.LENGTH_SHORT).show();
            GuardaDatosSPComentarios();
        }
        ComentariosDef = ObtenComentarios();
        /*for(int i=0;i<dt.ComentariosRDPDef.length;i++){
            ComentariosDef[i] = sh.getString("Comentario"+i, "");
        }*/
        ArrayList<ComentariosRDP> ListaComen = new ArrayList<>();
        int Tamaño;
        if (Datos[11].equals("Mensual")){
            Tamaño=5;
        }else if (Datos[11].equals("Trimestral")){
            Tamaño=21;
        }
        else{
            Tamaño= dt.ComentariosRDP.length;      }
        for (int i =0;i<Tamaño;i++){
            ComentariosRDP Comen = null;
            Comen = new ComentariosRDP();
            Comen.setComentario1(dt.ComentariosRDP[i]);
            Comen.setComentario2(ComentariosDef[i]);
            Comen.setDatos(Datos);
            Comen.setID(i);
            ListaComen.add(Comen);
            AdapterAtividades = new AdapterActividades(ListaComen);
            ComentariosView.setAdapter(AdapterAtividades);
        }
    }
    private void CargaImagen(String Nombre,ImageView Foto,String Where,ProgressBar Cargando){
        Cargando.setVisibility(View.VISIBLE);
        Foto.setVisibility(View.GONE);
        String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
        File directorioImagenes = new File(ExternalStorageDirectory + "ProyectoX/Preventivo/"+Datos[0]);
        if (!directorioImagenes.exists()){
            directorioImagenes.mkdirs();
        }
        else{
        }
        File Credencial = new File(ExternalStorageDirectory + "ProyectoX/Preventivo/"+Datos[0]+"/"+Nombre+".png");
        if (!Credencial.exists()){
            StorageReference Credref = storageReference.child("Credenciales").child(Where).child(Nombre+".jpg");
            try {
                File localFile = File.createTempFile(Nombre, "jpg");
                Credref.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        Foto.setImageBitmap(bitmap);
                        GuardarenGaleria(bitmap,"ProyectoX/Preventivo/"+Datos[0],Nombre);
                        Cargando.setVisibility(View.GONE);
                        Foto.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Cargando.setVisibility(View.GONE);
                        Foto.setVisibility(View.VISIBLE);
                        //Toast.makeText(PreventivoRD.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException ioException) {
                Cargando.setVisibility(View.GONE);
                Foto.setVisibility(View.VISIBLE);
                Toast.makeText(PreventivoRD.this, ioException.toString(), Toast.LENGTH_SHORT).show();
            }

        }
        else{
            ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
            Uri uri = Uri.fromFile(new File(ExternalStorageDirectory + "ProyectoX/Preventivo/"+Datos[0]+"/"+Nombre+".png"));
            try {
                Bitmap bitmapi = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Cargando.setVisibility(View.GONE);
                Foto.setVisibility(View.VISIBLE);
                Foto.setImageBitmap(bitmapi);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void GuardarenGaleria(Bitmap bitmap,String rutacarpeta,String nombre){
        try {
            String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
            //File directorioImagenes = new File(ExternalStorageDirectory + rutacarpeta);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(ExternalStorageDirectory + rutacarpeta+"/"+ nombre+".png"));
            Toast.makeText(PreventivoRD.this, "Credencial guardada con éxito", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }

    }
    public void SeleccionaCredencialANAM(View view){
        Datos[1]="Preventivo";
        Intent intent = new Intent(this,PersonalANAM.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);

    }
    public void SeleccionaCredencialSeguritech(View view){
        Datos[1]="PreventivoSeguritech";
        Intent intent = new Intent(this,ListaUsuarios.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    private String [] ObtenComentarios(){
         String[]ComentariosDef = new String[dt.ComentariosRDPDef.length];
        SharedPreferences sh = getSharedPreferences("Datos" + Datos[0], MODE_PRIVATE);
        for(int i=0;i<dt.ComentariosRDPDef.length;i++){
            ComentariosDef[i] = sh.getString("Comentario"+i, "");
        }
         return ComentariosDef;
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,Ticketslist.class);
        SharedPreferences sh = getSharedPreferences("Datos", MODE_PRIVATE);
        String CorreoElectronico = sh.getString("CorreoElectronicoSeguritech", "");
        String Numero= sh.getString("Telefono", "");
        Datos[1]="Preventivo";
        Datos[8] =CorreoElectronico;
        Datos[9]=Numero;
        i.putExtra("Datos",Datos);
        startActivity(i);
    }
    public void CrearRDP(View view){
        if (!ValidaDatos()){

        }else{
            ImageData imageData;
            try {
                String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
                String rutacarpeta =  rutacarpeta = "ProyectoX/Preventivo/" + Datos[0] + "/" + Datos[0] + "(CF)/";;
                File directorioImagenes = new File(ExternalStorageDirectory + rutacarpeta);
                if (!directorioImagenes.exists())
                    directorioImagenes.mkdirs();
                Bitmap bitmap;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.seguritech);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bitmapData = stream.toByteArray();
                imageData = ImageDataFactory.create(bitmapData);
                GurdarEstatusEquipo();
                doc.CreaReporteDigital(Datos,imageData,ObtenComentarios(),ObtenEstatusEquipo());
                Datos[27]=ConteoInspecciones.getText().toString();
                Datos[28]=HorasGenerador.getText().toString();
                Datos[1]="Preventivo";
                Datos[29]="RD";
                Intent intent = new Intent(this,VisorPDF.class);
                intent.putExtra("Datos",Datos);
                startActivity(intent);
            }catch (Exception e){
                Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void EditaDatos(View view){
        Datos[1]="RD";
        Intent intent = new Intent(this,EditaDatos.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }

}