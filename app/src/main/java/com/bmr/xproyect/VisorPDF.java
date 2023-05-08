package com.bmr.xproyect;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bmr.xproyect.DataBases.FotosManageDB;
import com.bmr.xproyect.DataBases.TicketManageDB;
import com.bmr.xproyect.Datos.Datos;
import com.bmr.xproyect.Entidades.DatosFoto;
import com.bmr.xproyect.Entidades.Fotos;
import com.bmr.xproyect.ServiceHelper.DriveServiceHelper;
import com.bmr.xproyect.ServiceHelper.SheetsServiceHelper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class VisorPDF extends AppCompatActivity {
    private DriveServiceHelper mDriveServiceHelper;
    private SheetsServiceHelper mSheetServiceHelper;
    private static final int REQUEST_CODE_SIGN_IN = 1;
    private static final String TAG = "DrivePruebas";
    private final String storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
    Datos dt = new Datos();
    String []Datos;
    String name,rutacarpeta,outputPDF,Re;
    TextView Estatus,pdf,todo;
    ProgressBar Subiendo;
    String Fotos;
    Boolean Todo;
    ImageButton PDF,TODO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visor_pdf);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                Datos = extras.getStringArray("Datos");
                Fotos = extras.getString("Fotos");
            }
        }
        else {
            Datos = (String[]) savedInstanceState.getSerializable("Datos");
            Fotos = (String) savedInstanceState.getSerializable("Fotos");
        }
        if (Datos == null){
            Datos = new String[dt.Datos.length];
            SharedPreferences sh = getSharedPreferences("Datos"+Datos[0], MODE_PRIVATE);
            for(int i=0;i<dt.Datos.length;i++){
                Datos[i] = sh.getString("Datos"+i, "");
            }
        }
        else{
            GuardaDatosSP(Datos);
        }
        if (Datos[0].equals("Actadenetrega"))name = "Actadeentrega.pdf";
        else name =Datos[0]+"("+Datos[29]+").pdf";
        rutacarpeta = "ProyectoX/"+Datos[1]+"/" + Datos[0] + "/" ;
        outputPDF = storageDir + rutacarpeta + name;
        requestSignIn();
        Estatus = (TextView)findViewById(R.id.EstatusView) ;
        pdf = (TextView)findViewById(R.id.TextPDF) ;
        todo = (TextView)findViewById(R.id.TextTodo) ;
        PDF = (ImageButton)findViewById(R.id.SubePDF) ;
        TODO = (ImageButton)findViewById(R.id.SubeTodo) ;
        Subiendo = (ProgressBar)findViewById(R.id.Subiendo) ;

        if (Fotos!=null){
            Estatus.setText("SubiendoFotos");
            Estatus.setVisibility(View.VISIBLE);
            Subiendo.setVisibility(View.VISIBLE);
            TODO.setVisibility(View.INVISIBLE);
            todo.setVisibility(View.INVISIBLE);
            PDF.setVisibility(View.INVISIBLE);
            pdf.setVisibility(View.INVISIBLE);
        }
        else{
            if (Datos[29].equals("RFD")){
                TODO.setVisibility(View.VISIBLE);
                todo.setVisibility(View.VISIBLE);
              }
            else{
                TODO.setVisibility(View.INVISIBLE);
                todo.setVisibility(View.INVISIBLE);
            }
            viewPDFFile();
            Estatus.setVisibility(View.GONE);
            Subiendo.setVisibility(View.GONE);
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
    public void CompartirPDF(View view){
        File file;
        if (Datos[0].equals("Actadeneetrga")){
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File directory = cw.getDir("Actadeentrga", Context.MODE_PRIVATE);
            file = new File(directory, name);

        }else {
            file = new File(outputPDF);
        }
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            Uri outputPdfUri = FileProvider.getUriForFile(this, VisorPDF.this.getPackageName() + ".fileprovider", file);
            //Uri screenshotUri = Uri.parse(file.getAbsolutePath());
            sharingIntent.setType("application/pdf");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, outputPdfUri);
            startActivity(Intent.createChooser(sharingIntent, "Share using"));

    }
    private void viewPDFFile() {
        com.github.barteksc.pdfviewer.PDFView pdfView = (com.github.barteksc.pdfviewer.PDFView) findViewById(R.id.pdfView);
        if (Datos[0].equals("Actadenetrega")){
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File directory = cw.getDir("Actadeentrga", Context.MODE_PRIVATE);
            File file = new File(directory, name);
            pdfView.fromFile(file).load();
        }
        else pdfView.fromFile(new File(outputPDF)).load();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void SubePDF(View view){
        Todo = false;
        Estatus.setVisibility(View.VISIBLE);
        Subiendo.setVisibility(View.VISIBLE);
        SharedPreferences sh = getSharedPreferences("Datos"+Datos[0], MODE_PRIVATE);
        String IDCarpeta = sh.getString("IDCarpeta", "");
        String Link = sh.getString("Link", "");
        if (IDCarpeta.equals("")){
            Estatus.setText("Creando carpeta");
            mDriveServiceHelper.createFolder(Datos[0],Datos[1],Datos[17]).addOnSuccessListener(fileId -> SubeDocumento(fileId[0],fileId[1]));
        }else{
            SubeDocumento(IDCarpeta,Link);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Todo(View view){
        Todo = true;
        Estatus.setVisibility(View.VISIBLE);
        Subiendo.setVisibility(View.VISIBLE);
        SharedPreferences sh = getSharedPreferences("Datos"+Datos[0], MODE_PRIVATE);
        String IDCarpeta = sh.getString("IDCarpeta", "");
        String Link = sh.getString("Link", "");
        if (IDCarpeta.equals("")){
            Estatus.setText("Creando carpeta");
            mDriveServiceHelper.createFolder(Datos[0],Datos[1],Datos[17]).addOnSuccessListener(fileId -> SubeDocumento(fileId[0],fileId[1]));
        }else{
            SubeDocumento(IDCarpeta,Link);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void SubeDocumento(String ID, String link){
        Estatus.setText("Subiendo Reporte");
        SharedPreferences sharedPreferences = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("IDCarpeta", ID);
        myEdit.putString("Link", link);
        myEdit.commit();
        Toast.makeText(this, Datos[16],Toast.LENGTH_SHORT).show();
        ///////////////////////////////PREVENTIVO///
        if (Datos[1].equals("Preventivo"))mSheetServiceHelper.buscaSerie(Datos[16],"Generador").addOnSuccessListener(fileId -> Escribeentabla(fileId,"Generador"))
                .addOnFailureListener(Fail ->Problemas(Fail.toString()));
        ///////////
        mDriveServiceHelper.createFile(ID,name,rutacarpeta).addOnSuccessListener(fileId -> DocumentoArriba())
                .addOnFailureListener(Fail ->Problemas(Fail.toString()));
    }
        ///////PREVENTIVO//////
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void Escribeentabla(Integer fileID, String Where){
        if (Where.equals("Generador")){
            mSheetServiceHelper.EscibeenTablaGenerador(Datos[28],Datos[17], fileID,Where)
                    .addOnSuccessListener(Fail -> mSheetServiceHelper.buscaSerie(Datos[16],"Generador").addOnSuccessListener(fileId -> Escribeentabla(fileId,"ConteoInspecciones"))
                            .addOnFailureListener(Failed ->Problemas(Failed.toString()))
                            .addOnFailureListener(Fail2 ->Toast.makeText(this,Fail2.toString(),Toast.LENGTH_SHORT).show()));
        }
        else{
            mSheetServiceHelper.EscibeenTablaGenerador(Datos[27],Datos[17], fileID,Where).addOnSuccessListener(bool -> System.out.println(bool.toString()))
                    .addOnFailureListener(Failed ->Problemas(Failed.toString()));
        }


    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void AñadeLink(String FechaRF,String FechaRD,String FechaRCF,int NumeroCorrec,int NumeroCorrec2){
        SharedPreferences sh = getSharedPreferences("Datos"+Datos[0], MODE_PRIVATE);
        String Link = sh.getString("IDCarpeta", "");
        /*mSheetServiceHelper.ObtenNumerototaldetickets().addOnSuccessListener(Numero -> mSheetServiceHelper.AñadeLink(Link,Datos[12],FechaRF,FechaRD,NumeroCorrec,NumeroCorrec2,Datos).addOnSuccessListener(bool ->  Toast.makeText(this,"Se logro",Toast.LENGTH_SHORT).show())
                .addOnFailureListener(Failed ->Problemas(Failed.toString())) )
                .addOnFailureListener(Failed ->Problemas(Failed.toString()));*/
        mSheetServiceHelper.AñadeLink(Link,FechaRF,FechaRD,FechaRCF,NumeroCorrec,NumeroCorrec2,Datos)
                .addOnSuccessListener(bool ->  Toast.makeText(this,"Se logro",Toast.LENGTH_SHORT).show())
                .addOnFailureListener(Failed ->Problemas(Failed.toString()));
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void AñadeLinkCorrectivo(String FechaRF,String FechaRD,String FechaRCF,int NumeroCorrec,int NumeroCorrec2){
        SharedPreferences sh = getSharedPreferences("Datos"+Datos[0], MODE_PRIVATE);
            String[]EstatusEquipo = new String[]{
                    sh.getString("Diagnostico", ""),
                    sh.getString("ACorrectivas", ""),
                    sh.getString("Observaciones", "Sin observaciones."),
                    sh.getString("Refacciones", "Sin refacciones."),
            };

        String Link = sh.getString("IDCarpeta", "");
        /*mSheetServiceHelper.ObtenNumerototaldetickets2(Datos).addOnSuccessListener(Numero -> mSheetServiceHelper.AñadeLink2(Link,Datos[12],FechaRF,FechaRD,FechaRCF,NumeroCorrec,NumeroCorrec2,Datos,EstatusEquipo).addOnSuccessListener(bool ->  Toast.makeText(this,"Se logro",Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(Failed ->Problemas(Failed.toString())) )
                .addOnFailureListener(Failed ->Problemas(Failed.toString()));*/
        mSheetServiceHelper.AñadeLink2(Link,Datos[12],FechaRF,FechaRD,FechaRCF,NumeroCorrec,NumeroCorrec2,Datos,EstatusEquipo).addOnSuccessListener(bool ->  Toast.makeText(this,"Se logro",Toast.LENGTH_SHORT).show())
                .addOnFailureListener(Failed ->Problemas(Failed.toString())) ;

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void AñadeLinkInterno(String FechaRF,String FechaRD,String FechaRCF,int NumeroCorrec,int NumeroCorrec2){
        SharedPreferences sh = getSharedPreferences("Datos"+Datos[0], MODE_PRIVATE);
        String Link = sh.getString("IDCarpeta", "");
    mSheetServiceHelper.AñadeLink3(Link,Datos[12],FechaRF,FechaRD,FechaRCF,NumeroCorrec,NumeroCorrec2,Datos).addOnSuccessListener(bool ->  Toast.makeText(this,"Se logro",Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(Failed ->Problemas(Failed.toString()));
        Toast.makeText(this,"AñadiendoLinkInterno",Toast.LENGTH_SHORT).show();
    }
    private void Problemas(String Fail){
        Estatus.setVisibility(View.GONE);
        Subiendo.setVisibility(View.GONE);
        Toast.makeText(this,Fail,Toast.LENGTH_SHORT).show();

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void DocumentoArriba(){

        SharedPreferences sharedPreferences = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        Toast.makeText(this,"Se ha subido el documento con éxito",Toast.LENGTH_SHORT).show();
        if (Datos[29].equals("RFD")){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            System.out.println("yyyy-MM-dd HH:mm:-> "+dtf.format(LocalDateTime.now()));
            String FechaRD=sharedPreferences.getString("FechaRD", "-");
            String FechaRF=sharedPreferences.getString("FechaRF", "-");
            String FechaRCF=sharedPreferences.getString("FechaRCF", "-");
            int NumeroCorrec=sharedPreferences.getInt("NumeroCorrecRF", 0);
            int NumeroCorrec2=sharedPreferences.getInt("NumeroCorrecRD", 0);
            if (FechaRF.equals("-")){
                myEdit.putString("FechaRF", dtf.format(LocalDateTime.now()));
                myEdit.commit();
            }
            if (NumeroCorrec2>0)NumeroCorrec2=NumeroCorrec2-1;
            FechaRF=sharedPreferences.getString("FechaRF", "-");
            if (Datos[1].equals("Preventivo"))AñadeLink(FechaRF,FechaRD,FechaRCF,NumeroCorrec,NumeroCorrec2);
            else if (Datos[1].equals("Correctivo"))AñadeLinkCorrectivo(FechaRF,FechaRD,FechaRCF,NumeroCorrec,NumeroCorrec2);
            else  AñadeLinkInterno(FechaRF,FechaRD,FechaRCF,NumeroCorrec,NumeroCorrec2);
            myEdit.putInt("NumeroCorrecRF", NumeroCorrec+1);
            myEdit.commit();
            if (Todo){
                SubeFotos();
            }
            else{
                Estatus.setVisibility(View.GONE);
                Subiendo.setVisibility(View.GONE);
            }

        }else{
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            System.out.println("yyyy-MM-dd HH:mm:ss-> "+dtf.format(LocalDateTime.now()));
            String FechaRD=sharedPreferences.getString("FechaRD", "-");
            String FechaRF=sharedPreferences.getString("FechaRF", "-");
            String FechaRCF=sharedPreferences.getString("FechaRCF", "-");
            int NumeroCorrec=sharedPreferences.getInt("NumeroCorrecRF", 0);
            int NumeroCorrec2=sharedPreferences.getInt("NumeroCorrecRD", 0);
            if (FechaRD.equals("-")){
                myEdit.putString("FechaRD", dtf.format(LocalDateTime.now()));
                myEdit.commit();
            }
            if (NumeroCorrec>0)NumeroCorrec=NumeroCorrec-1;
            FechaRD=sharedPreferences.getString("FechaRD", "-");
            if (Datos[1].equals("Preventivo"))AñadeLink(FechaRF,FechaRD,FechaRCF,NumeroCorrec,NumeroCorrec2);
            else if (Datos[1].equals("Correctivo"))AñadeLinkCorrectivo(FechaRF,FechaRD,FechaRCF,NumeroCorrec,NumeroCorrec2);
            else  AñadeLinkInterno(FechaRF,FechaRD,FechaRCF,NumeroCorrec,NumeroCorrec2);
            myEdit.putInt("NumeroCorrecRD", NumeroCorrec2+1);
            myEdit.commit();
            Estatus.setVisibility(View.GONE);
            Subiendo.setVisibility(View.GONE);
        }

    }





    @RequiresApi(api = Build.VERSION_CODES.O)
    public void SubeFotos(){
        SharedPreferences sh = getSharedPreferences("Datos"+Datos[0], MODE_PRIVATE);
        String IDCarpeta = sh.getString("IDCarpeta", "");
        String Link = sh.getString("Link", "");
        if (IDCarpeta.equals("")){
            mDriveServiceHelper.createFolder(Datos[0],Datos[1],Datos[17]).addOnSuccessListener(fileId -> ObtenID2 (fileId[0],fileId[1]) );

        }else{
            ObtenID2 (IDCarpeta,Link);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void ObtenID2(String IDCarpeta, String Link){
        SharedPreferences sh = getSharedPreferences("Datos"+Datos[0], MODE_PRIVATE);
        String IDCarpeta2 = sh.getString("IDCarpetaFotos", "");
        SharedPreferences.Editor myEdit = sh.edit();
        myEdit.putString("IDCarpeta", IDCarpeta);
        myEdit.putString("Link", Link);
        Toast.makeText(this,Link,Toast.LENGTH_SHORT).show();
        myEdit.commit();
        if (IDCarpeta2.equals("")){
            Estatus.setText("Creando carpeta de fotos");
            if (Datos[1].equals("Preventivo")){
                mDriveServiceHelper.createFolderFotos(IDCarpeta,Datos[0]).addOnSuccessListener(fileId -> SubeFotosP(fileId,0) );
            }else if (Datos[1].equals("Correctivo")){
                mDriveServiceHelper.createFolderFotos(IDCarpeta,Datos[0]).addOnSuccessListener(fileId -> SubeFotosC(fileId,0) );
            }else{
                mDriveServiceHelper.createFolderFotos(IDCarpeta,Datos[0]).addOnSuccessListener(fileId -> SubeFotosC(fileId,0) );
            }


        }else{
            if (Datos[1].equals("Preventivo")){
                SubeFotosP(IDCarpeta2,0);
            }else if (Datos[1].equals("Correctivo")){
                SubeFotosC(IDCarpeta2,0);
            }else{
                SubeFotosC(IDCarpeta2,0);
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void SubeFotosP(String ID, int id){
        SharedPreferences sharedPreferences = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        if (id==0){
            myEdit.putString("IDCarpetaFotos", ID);
            myEdit.commit();
        }
        else{

        }
        Datos dt = new Datos();
        String ComentariosRF[];
        int NumeroFotos;
        int NumerFotosTotal;
        if (Datos[11].equals("Mensual")) {
            NumeroFotos = 9;
            if (sharedPreferences.getString("idExtra", "").equals("")){
                NumerFotosTotal = NumeroFotos;
            }
            else{
                NumerFotosTotal = NumeroFotos+Integer.parseInt(sharedPreferences.getString("idExtra", ""));
            }
            ComentariosRF = dt.ComentariosRFPMensual;
        }else {
            NumeroFotos = 11;
            if (Datos[11].equals("Trimestral")){
                ComentariosRF= dt.ComentariosRFPTrimestral;
            }else{
                ComentariosRF= dt.ComentariosRFPAnual;
            }
            if (sharedPreferences.getString("idExtra", "").equals("")){
                NumerFotosTotal = NumeroFotos;
            }
            else{
                NumerFotosTotal = NumeroFotos+Integer.parseInt(sharedPreferences.getString("idExtra", ""));
            }
        }
        id ++;
        int finalId = id;

        if (finalId==NumeroFotos){
            String IDCarpeta = sharedPreferences.getString("IDCarpeta", "");
            Estatus.setText("Subiendo Foto: "+"Horas del generador eléctrico");
            if (NumerFotosTotal>NumeroFotos){
                mDriveServiceHelper.SubirFoto(IDCarpeta, "foto"+(finalId)+".png",Datos[0]+"(HG)",Datos).addOnSuccessListener(idfoto ->SubeFotosP(ID, finalId))
                        .addOnFailureListener(exeption -> Toast.makeText(VisorPDF.this, exeption.toString(), Toast.LENGTH_LONG).show());
            }else{
                mDriveServiceHelper.SubirFoto(IDCarpeta, "foto"+(finalId)+".png",Datos[0]+"(HG)",Datos).addOnSuccessListener(idfoto ->FotosSubidasCorrectamente())
                        .addOnFailureListener(exeption -> Toast.makeText(VisorPDF.this, exeption.toString(), Toast.LENGTH_LONG).show());
            }

        }
        else if (finalId==NumeroFotos-1){
            Estatus.setText("Subiendo Foto: "+"Conteo de Inspecciones");
            String IDFotos = sharedPreferences.getString("IDCarpetaFotos", "");
            String IDCarpeta = sharedPreferences.getString("IDCarpeta", "");
            if (ID.equals(IDFotos)){
                mDriveServiceHelper.SubirFoto(ID, "foto"+(finalId)+".png",ComentariosRF[finalId-1],Datos).addOnSuccessListener(idfoto ->SubeFotosP(IDCarpeta, finalId-1))
                        .addOnFailureListener(exeption -> Toast.makeText(VisorPDF.this, exeption.toString(), Toast.LENGTH_LONG).show());
            }
            else{
                mDriveServiceHelper.SubirFoto(ID, "foto"+(finalId)+".png",Datos[0]+"(CI)",Datos).addOnSuccessListener(idfoto ->SubeFotosP(IDFotos, finalId))
                        .addOnFailureListener(exeption -> Toast.makeText(VisorPDF.this, exeption.toString(), Toast.LENGTH_LONG).show());
            }
        }
        else if ((finalId>NumeroFotos)&&(finalId<NumerFotosTotal)){
            Estatus.setText("Subiendo foto extra"+ObtenComentarios()[finalId-NumeroFotos-1]);
            mDriveServiceHelper.SubirFoto(ID, "extra"+(finalId-NumeroFotos)+".png",ObtenComentarios()[finalId-NumeroFotos-1],Datos).addOnSuccessListener(idfoto ->SubeFotosP(ID, finalId))
                    .addOnFailureListener(exeption -> Toast.makeText(VisorPDF.this, exeption.toString(), Toast.LENGTH_LONG).show());
        }
        else if (finalId==NumerFotosTotal){
            Estatus.setText("Subiendo foto extra"+ObtenComentarios()[finalId-NumeroFotos-1]);
            mDriveServiceHelper.SubirFoto(ID, "extra"+(finalId-NumeroFotos)+".png",ObtenComentarios()[finalId-NumeroFotos-1],Datos).addOnSuccessListener(idfoto ->FotosSubidasCorrectamente())
                    .addOnFailureListener(exeption -> Toast.makeText(VisorPDF.this, exeption.toString(), Toast.LENGTH_LONG).show());
        }
        else{
            Estatus.setText("Subiendo Foto: "+ComentariosRF[finalId-1]);
            mDriveServiceHelper.SubirFoto(ID, "foto"+(finalId)+".png",ComentariosRF[finalId-1],Datos).addOnSuccessListener(idfoto ->SubeFotosP(ID, finalId))
                    .addOnFailureListener(exeption -> Toast.makeText(VisorPDF.this, exeption.toString(), Toast.LENGTH_LONG).show());
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void SubeFotosC(String ID, int id){
        SharedPreferences sharedPreferences = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        FotosManageDB base = new FotosManageDB (VisorPDF.this,Datos[0]);
        int NumeroFotos = base.TamañoTabla(Datos[0]);
        if (id==0){
            myEdit.putString("IDCarpetaFotos", ID);
            myEdit.commit();
        }
        else{

        }
        id ++;
        int finalId = id;
        if (finalId==NumeroFotos){
            Estatus.setText("Subiendo Foto: "+ObtenComentarios()[finalId-1]);
            mDriveServiceHelper.SubirFoto(ID, "foto"+(finalId)+".png",ObtenComentarios()[finalId-1],Datos).addOnSuccessListener(idfoto ->FotosSubidasCorrectamente())
                    .addOnFailureListener(exeption -> Toast.makeText(VisorPDF.this, exeption.toString(), Toast.LENGTH_LONG).show());
        }else{
            mDriveServiceHelper.SubirFoto(ID, "foto"+(finalId)+".png",ObtenComentarios()[finalId-1],Datos).addOnSuccessListener(idfoto ->SubeFotosC(ID, finalId))
                    .addOnFailureListener(exeption -> Toast.makeText(VisorPDF.this, exeption.toString(), Toast.LENGTH_LONG).show());
        }
    }
    private String[]  ObtenComentarios(){
        DatosFoto Data;

        ArrayList<Fotos> ListaFotos = new ArrayList<>();
        FotosManageDB base = new FotosManageDB (VisorPDF.this,Datos[0]);
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void FotosSubidasCorrectamente(){
        Toast.makeText(this,"Fotos subidas Correctamente",Toast.LENGTH_LONG).show();
        Estatus.setVisibility(View.GONE);
        Subiendo.setVisibility(View.GONE);
        SharedPreferences sharedPreferences = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        System.out.println("yyyy-MM-dd HH:mm:-> "+dtf.format(LocalDateTime.now()));
        String FechaRD=sharedPreferences.getString("FechaRD", "-");
        String FechaRF=sharedPreferences.getString("FechaRF", "-");
        String FechaRCF=sharedPreferences.getString("FechaRCF", "-");
        int NumeroCorrec=sharedPreferences.getInt("NumeroCorrecRF", 0);
        int NumeroCorrec2=sharedPreferences.getInt("NumeroCorrecRD", 0);
        if (FechaRCF.equals("-")){
            myEdit.putString("FechaRCF", dtf.format(LocalDateTime.now()));
            myEdit.commit();
        }
        FechaRCF=sharedPreferences.getString("FechaRCF", "-");

        if (Datos[1].equals("Preventivo"))AñadeLink(FechaRF,FechaRD,FechaRCF,NumeroCorrec,NumeroCorrec2);
        else if (Datos[1].equals("Correctivo"))AñadeLinkCorrectivo(FechaRF,FechaRD,FechaRCF,NumeroCorrec,NumeroCorrec2);
        else  AñadeLinkInterno(FechaRF,FechaRD,FechaRCF,NumeroCorrec,NumeroCorrec2);

        if (Fotos!=null){
            if (Datos[1].equals("Preventivo")){
                Intent intent = new Intent(this,PreventivoRF.class);
                intent.putExtra("Datos",Datos);
                startActivity(intent);
            }else if (Datos[1].equals("Correctivo")){
                Intent intent = new Intent(this,CorrectivoRF.class);
                intent.putExtra("Datos",Datos);
                startActivity(intent);
            }else{
                Intent intent = new Intent(this,InternoRF.class);
                intent.putExtra("Datos",Datos);
                startActivity(intent);
            }
        }
    }
    ///////////////////////////SERVICIOS GOOGLE//////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        switch (requestCode) {
            case REQUEST_CODE_SIGN_IN:
                if (resultCode == Activity.RESULT_OK && resultData != null) {
                    handleSignInResult(resultData);
                }
                break;

        }

        super.onActivityResult(requestCode, resultCode, resultData);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleSignInResult(Intent result) {
        GoogleSignIn.getSignedInAccountFromIntent(result)
                .addOnSuccessListener(googleAccount -> {
                    // Use the authenticated account to sign in to the Drive service.
                    GoogleAccountCredential credential =
                            GoogleAccountCredential.usingOAuth2(
                                    this, Collections.singleton(DriveScopes.DRIVE_FILE));
                    credential.setSelectedAccount(googleAccount.getAccount());
                    Drive googleDriveService =
                            new Drive.Builder(
                                    AndroidHttp.newCompatibleTransport(),
                                    new GsonFactory(),
                                    credential)
                                    .setApplicationName("Drive API Migration")
                                    .build();

                    mDriveServiceHelper = new DriveServiceHelper(googleDriveService);
                    GoogleAccountCredential credential2 = GoogleAccountCredential.usingOAuth2(
                            this, Arrays.asList(SheetsScopes.SPREADSHEETS)).setBackOff(new ExponentialBackOff());
                    credential2.setSelectedAccount(googleAccount.getAccount());
                    Sheets googleSheetsService = new Sheets.Builder(
                            AndroidHttp.newCompatibleTransport(),
                            JacksonFactory.getDefaultInstance(), credential2)
                            .setApplicationName("Sheets API Migration")
                            .build();
                    mSheetServiceHelper = new SheetsServiceHelper(googleSheetsService);
                    if (Fotos!=null){
                        SubeFotos();
                    }
                    // The DriveServiceHelper encapsulates all REST API and SAF functionality.
                    // Its instantiation is required before handling any onClick actions

                })
                .addOnFailureListener(exception -> Toast.makeText(this,exception.toString(),Toast.LENGTH_SHORT).show());
    }
    private void requestSignIn() {

        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .requestScopes(new Scope(DriveScopes.DRIVE_FILE))
                        .requestScopes(new Scope(SheetsScopes.SPREADSHEETS))
                        .requestScopes(new Scope(SheetsScopes.SPREADSHEETS_READONLY))
                        .build();
        GoogleSignInClient client = GoogleSignIn.getClient(this, signInOptions);

        // The result of the sign-in Intent is handled in onActivityResult.
        startActivityForResult(client.getSignInIntent(), REQUEST_CODE_SIGN_IN);
    }
}