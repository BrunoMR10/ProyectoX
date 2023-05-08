package com.bmr.xproyect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bmr.xproyect.Datos.Datos;
import com.bmr.xproyect.Documentos.Correctivo;
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

public class CorrectivoRD extends AppCompatActivity {
    Correctivo correctivo = new Correctivo();
    public FirebaseStorage storage = FirebaseStorage.getInstance();
    public StorageReference storageReference = storage.getReferenceFromUrl("gs://proyecto-x-933f4.appspot.com");
    Datos dt = new Datos();
    TextView TicketRDC,FechaInicioRDC,AduanaRDC,EquipoRDC,SerieRDC,UbicacionRDC,FallaRDC,
            FechaLLamadaRDC,HoraLlamadaRDC,NombreTecnicoRDC,FechaSitioRDC,HoraSitioRDC,
            FechaFinRDC,HoraFinRDC;
    EditText DiagnosticoRDC,ACorrectivasRDC,ObservacionesRDC,RefaccionesRDC;
    CheckBox Menor,Mayor;
    String nombreANAM,nombreSeguritech,puestoseguritech,puestoANAM,CambioCredencial;
    String []Datos;
    LinearLayout credenciales,falla,atencion,sitio,diagnostico,cierre;
    ImageView Foto1,Foto2,Foto3;
    TextView NombreSeguritech,NombreANAM,PuestoANAM,PuestoSeg;
    Boolean Credenciales;
    ProgressBar CargaANAM1,CargaANAM2,CargaSeguritech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correctivo_rd);
        TicketRDC = (TextView) findViewById(R.id.TicketRDC);
        FechaInicioRDC = (TextView) findViewById(R.id.FechaInicioRDC);
        AduanaRDC = (TextView) findViewById(R.id.AduanaRDC);
        EquipoRDC = (TextView) findViewById(R.id.EquipoRDC);
        SerieRDC= (TextView) findViewById(R.id.SerieRDC);
        UbicacionRDC= (TextView) findViewById(R.id.UbicacionRDC);
        FallaRDC= (TextView) findViewById(R.id.FallaRDC);
        FechaLLamadaRDC= (TextView) findViewById(R.id.FechaLLamadaRDC);
        HoraLlamadaRDC= (TextView) findViewById(R.id.HoraLlamadaRDC);
        NombreTecnicoRDC= (TextView) findViewById(R.id.NombreTecnicoRDC);
        FechaSitioRDC= (TextView) findViewById(R.id.FechaSitioRDC);
        HoraSitioRDC= (TextView) findViewById(R.id.HoraSitioRDC);
        FechaFinRDC= (TextView) findViewById(R.id.FechaFinRDC);
        HoraFinRDC= (TextView) findViewById(R.id.HoraFinRDC);
        DiagnosticoRDC = (EditText)findViewById(R.id.DiagnosticoRDC);
        ACorrectivasRDC = (EditText)findViewById(R.id.ACorrectivasRDC);
        ObservacionesRDC = (EditText)findViewById(R.id.ObservacionesRDC);
        RefaccionesRDC = (EditText)findViewById(R.id.RefaccionesRDC);
        Menor = (CheckBox) findViewById(R.id.Menor);
        Mayor = (CheckBox) findViewById(R.id.Mayor);
        NombreSeguritech = (TextView) findViewById(R.id.NombreSeguritechC);
        NombreANAM = (TextView) findViewById(R.id.NombreANAMC);
        PuestoANAM = (TextView) findViewById(R.id.PuestoANAMC);
        PuestoSeg= (TextView) findViewById(R.id.PuestoSegC);
        Foto1 = (ImageView)findViewById(R.id.FotoFANAMC) ;
        Foto2 = (ImageView)findViewById(R.id.FTANAMC) ;
        Foto3 = (ImageView)findViewById(R.id.FotoFSeguritchC);
        credenciales = (LinearLayout)findViewById(R.id.credenciales2);
        falla = (LinearLayout)findViewById(R.id.Falla);
        atencion= (LinearLayout)findViewById(R.id.Atencion);
        sitio = (LinearLayout)findViewById(R.id.Sitio);
        diagnostico= (LinearLayout)findViewById(R.id.Diagnostico);
        cierre= (LinearLayout)findViewById(R.id.CierreReporte);
        CargaANAM1 = (ProgressBar)findViewById(R.id.CargaANAM1) ;
        CargaANAM2 = (ProgressBar)findViewById(R.id.CargaANAM2) ;
        CargaSeguritech = (ProgressBar)findViewById(R.id.CargaSeguritech) ;
        Credenciales = true;
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
                SharedPreferences sharedPreferences = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putInt("TamañoLetra", 9);
                myEdit.commit();
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
    public void ConfiguraIncio(){
        TicketRDC.setText(Datos[12]);
        AduanaRDC.setText(Datos[13]);
        EquipoRDC.setText(Datos[14]);
        UbicacionRDC.setText(Datos[15]);
        SerieRDC.setText(Datos[16]);
        FechaInicioRDC.setText(Datos[17]+" "+Datos[18]);
        FechaFinRDC.setText(Datos[19]+" "+Datos[20]);
        FallaRDC.setText(Datos[21]);
        FechaLLamadaRDC.setText(Datos[22]);
        HoraLlamadaRDC.setText(Datos[23]);
        FechaSitioRDC.setText(Datos[24]);
        HoraSitioRDC.setText(Datos[25]);
        NombreTecnicoRDC.setText(Datos[26]);
        DiagnosticoRDC.setText(ObtenEstatusEquipo()[0]);
        ACorrectivasRDC.setText(ObtenEstatusEquipo()[1]);
        ObservacionesRDC.setText(ObtenEstatusEquipo()[2]);
        RefaccionesRDC.setText(ObtenEstatusEquipo()[3]);
        TiempoReparacion();
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

    private void TiempoReparacion(){
        SharedPreferences sh = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
        if (sh.getString("ChecBox1", "true").equals("true")){
            Mayor.setChecked(false);
            Menor.setChecked(true);
        }
        else{
            Mayor.setChecked(true);
            Menor.setChecked(false);
        }
    }
    public void Checkboxclick(View view){
        SharedPreferences sh = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        if (sh.getString("ChecBox1", "true").equals("true")){
            myEdit.putString("ChecBox1",  "false");
            myEdit.commit();
            TiempoReparacion();

        }
        else{
            myEdit.putString("ChecBox1",  "true");
            myEdit.commit();
            TiempoReparacion();

        }
    }
    public void Guarda(View view){
        GuardaDiagnostico();
    }
    private String[] ObtenEstatusEquipo(){
        SharedPreferences sh = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
        String CheckBox;
        if (Mayor.isChecked()){
            CheckBox = "true";
        }
        else{
            CheckBox = "false";
        }
        String[]EstatusEquipo = new String[]{
                sh.getString("Diagnostico", ""),
                sh.getString("ACorrectivas", ""),
                sh.getString("Observaciones", "Sin observaciones."),
                sh.getString("Refacciones", "Sin refacciones."),
                CheckBox
        };
        return EstatusEquipo;
    }
    private void GuardaDiagnostico(){
        SharedPreferences sharedPreferences = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("Diagnostico",  DiagnosticoRDC.getText().toString());
        myEdit.putString("ACorrectivas", ACorrectivasRDC.getText().toString());
        myEdit.putString("Observaciones", ObservacionesRDC.getText().toString());
        myEdit.putString("Refacciones", RefaccionesRDC.getText().toString());
        myEdit.commit();
        Toast.makeText(this,"Datos Guardados",Toast.LENGTH_SHORT).show();
    }
    private void GuardaDatosSP(){
        SharedPreferences sharedPreferences = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        for(int i=0;i<Datos.length;i++){
            myEdit.putString("Datos"+i, Datos[i]);
        }
        myEdit.commit();
    }
    private void CredencialesRutina(){
        if(Credenciales){
            Credenciales=false;
            credenciales.setVisibility(View.VISIBLE);
            falla .setVisibility(View.GONE);
            atencion.setVisibility(View.GONE);
            sitio.setVisibility(View.GONE);
            diagnostico.setVisibility(View.GONE);
            cierre.setVisibility(View.GONE);
            CargaImagen(Datos[4],Foto3,"Seguritech",CargaSeguritech);
            CargaImagen(nombreANAM+" "+"Frontal",Foto1,"ANAM",CargaANAM1);
            CargaImagen(nombreANAM+" "+"Trasera",Foto2,"ANAM",CargaANAM2);
            PuestoSeg.setText(puestoseguritech);
            PuestoANAM.setText(puestoANAM);
            NombreSeguritech.setText(nombreSeguritech);
            NombreANAM.setText(nombreANAM);

        }else{
            Credenciales=true;
            credenciales.setVisibility(View.GONE);
            falla .setVisibility(View.VISIBLE);
            atencion.setVisibility(View.VISIBLE);
            sitio.setVisibility(View.VISIBLE);
            diagnostico.setVisibility(View.VISIBLE);
            cierre.setVisibility(View.VISIBLE);

        }
    }
    public void Credenciales(View view){
       CredencialesRutina();
    }
    private void CargaImagen(String Nombre,ImageView Foto,String Where,ProgressBar Cargando){
        Cargando.setVisibility(View.VISIBLE);
        Foto.setVisibility(View.GONE);
        String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
        File directorioImagenes = new File(ExternalStorageDirectory + "ProyectoX/Correctivo/"+Datos[0]);
        if (!directorioImagenes.exists()){
            directorioImagenes.mkdirs();
        }
        else{
        }
        File Credencial = new File(ExternalStorageDirectory +"ProyectoX/Correctivo/"+Datos[0]+"/"+Nombre+".png");
        if (!Credencial.exists()){
            StorageReference Credref = storageReference.child("Credenciales").child(Where).child(Nombre+".jpg");
            try {
                File localFile = File.createTempFile(Nombre, "jpg");
                Credref.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        Foto.setImageBitmap(bitmap);
                        GuardarenGaleria(bitmap,"ProyectoX/Correctivo/"+Datos[0],Nombre);
                        Cargando.setVisibility(View.GONE);
                        Foto.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(PreventivoRD.this, e.toString(), Toast.LENGTH_SHORT).show();
                        Cargando.setVisibility(View.GONE);
                        Foto.setVisibility(View.VISIBLE);
                    }
                });
            } catch (IOException ioException) {
                Toast.makeText(CorrectivoRD.this, ioException.toString(), Toast.LENGTH_SHORT).show();
                Cargando.setVisibility(View.GONE);
                Foto.setVisibility(View.VISIBLE);
            }

        }
        else{
            ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
            Uri uri = Uri.fromFile(new File(ExternalStorageDirectory + "ProyectoX/Correctivo/"+Datos[0]+"/"+Nombre+".png"));
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
            Toast.makeText(CorrectivoRD.this, "Credencial guardada con éxito", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }

    }
    public void SeleccionaCredencialANAM(View view){
        Datos[1]="Correctivo";
        Intent intent = new Intent(this,PersonalANAM.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);

    }
    public void SeleccionaCredencialSeguritech(View view){
        Datos[1]="CorrectivoSeguritech";
        Intent intent = new Intent(this,ListaUsuarios.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,Ticketslist.class);
        SharedPreferences sh = getSharedPreferences("Datos", MODE_PRIVATE);
        String CorreoElectronico = sh.getString("CorreoElectronicoSeguritech", "");
        String Numero= sh.getString("Telefono", "");
        Datos[8] =CorreoElectronico;
        Datos[9]=Numero;
        Datos[1]="Correctivo";
        i.putExtra("Datos",Datos);
        startActivity(i);
    }
    public void CrearRDP(View view){
        if (!ValidaDatos()){

        }else{
            ImageData imageData;
            try {
                String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
                String rutacarpeta =  rutacarpeta = "ProyectoX/Correctivo/" + Datos[0] + "/" + Datos[0] + "(CF)/";;
                File directorioImagenes = new File(ExternalStorageDirectory + rutacarpeta);
                if (!directorioImagenes.exists())
                    directorioImagenes.mkdirs();
                Bitmap bitmap;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.seguritech);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bitmapData = stream.toByteArray();
                imageData = ImageDataFactory.create(bitmapData);
                GuardaDiagnostico();
                SharedPreferences sh = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
                correctivo.CreaReporteDigital(Datos,imageData,ObtenEstatusEquipo(),sh.getInt("TamañoLetra", 9));
                Datos[1]="Correctivo";
                Datos[29]="RD";
                Intent intent = new Intent(this,VisorPDF.class);
                intent.putExtra("Datos",Datos);
                startActivity(intent);
            }catch (Exception e){
                Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void TORFotograficoP(View view){
        Intent intent = new Intent(this,CorrectivoRF.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    public void EditaDatos(View view){
        Datos[1]="RDC";
        Intent intent = new Intent(this,EditaDatos.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    private boolean ValidaDatos(){
        DiagnosticoRDC = (EditText)findViewById(R.id.DiagnosticoRDC);
        ACorrectivasRDC = (EditText)findViewById(R.id.ACorrectivasRDC);
        ObservacionesRDC = (EditText)findViewById(R.id.ObservacionesRDC);
        RefaccionesRDC = (EditText)findViewById(R.id.RefaccionesRDC);
        //ObtenEstatusEquipo();
        if (nombreANAM.equals("Seleccione personal")){
            Toast.makeText(this,"Seleccione credencial de ANAM",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (DiagnosticoRDC.getText().toString().equals("")){
            Toast.makeText(this,"Escriba el diagnostico",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (ACorrectivasRDC.getText().toString().equals("")){
            Toast.makeText(this,"Escriba las acciones correctivas",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (ObservacionesRDC.getText().toString().equals("")){
            Toast.makeText(this,"Sin observaciones",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (RefaccionesRDC.getText().toString().equals("")){
            Toast.makeText(this,"Sin Refacciones",Toast.LENGTH_SHORT).show();
            return false;
        }
        else{

            return true;
        }
    }
}