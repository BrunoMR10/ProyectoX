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
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bmr.xproyect.Datos.Datos;
import com.bmr.xproyect.Documentos.Interno;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class InternoRD extends AppCompatActivity {

     Interno interno = new Interno();
     ImageView Firma1View,Firma2View;
     TextView TicketRDI,FechaInicioRDI,FechaFinRDI,AduanaRDI,EquipoRDI,UbicacionRDI,SerieRDI,Nombre1,Nombre2,Puesto1,Puesto2;
     CheckBox Suministrode,Dieselchasis,Dieselgenerador,Adblue,
             SeguridadR,TomaNiveles,Calibracion,Curso,
             RevisionTHSCAN,Acelerador,Detector,Mecanicas,Electrico,Operacion,
             VisitaTecnica,Chasis,Generador,Aire,Extintores,CCTV,
             Otros,Documentacion,Levantamiento,Asesoramiento,Capacitacion,Electrificacion,Ubicacion,CentroServicio,otros;
     LinearLayout SuministrodeCheck,SuministrodeComen,
             SeguridadRCheck,TomaNivelesComen,
             Revision,RevisionCome,
             Servicio,ChasisCome,
             Documentaci,OtrosCome;
     EditText Dieselchasiscomen,Dieselgeneradorcomen,Adbluecomen,
             TomadeNivelesComen,CalibraciónComen,CursoComen,
             AceleadorComen,DetectorComen,MecanicasComen,ElectricoComen,OperacionComen,
             ChasisComen,GeneradorComen,AireComen,ExtintoresComen,CCTVComen,
             DocumentacionComen,LevantamientoComen,AsesoramientoComen,CapacitacionComen,ElectrificacionComen,UbicacionComen,CentroServicioComen,otrosComen,
             Obs;
     Boolean Credenciales;
     String nombreANAM,nombreSeguritech,puestoseguritech,puestoANAM,CambioCredencial;
    String []Datos;
     Datos dt = new Datos();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interno_rd);

        TicketRDI = (TextView) findViewById(R.id.TicketRDI);
        FechaInicioRDI = (TextView) findViewById(R.id.FechaInicioRDI);
        AduanaRDI = (TextView) findViewById(R.id.AduanaRDI);
        EquipoRDI = (TextView) findViewById(R.id.EquipoRDI);
        SerieRDI= (TextView) findViewById(R.id.SerieRDI);
        UbicacionRDI= (TextView) findViewById(R.id.UbicacionRDI);
        FechaInicioRDI = (TextView) findViewById(R.id.FechaInicioRDI);
        FechaFinRDI = (TextView) findViewById(R.id.FechaFinRDI);


        Nombre1= (TextView) findViewById(R.id.Nombre1);
        Nombre2= (TextView) findViewById(R.id.Nombre2);
        Puesto1 = (TextView) findViewById(R.id.Puesto1);
        Puesto2 = (TextView) findViewById(R.id.Puesto2);


        Suministrode = (CheckBox) findViewById(R.id.Suministrode);///Principal1
        Dieselchasis = (CheckBox) findViewById(R.id.Dieselchasis);///Sec1
        Dieselgenerador = (CheckBox) findViewById(R.id.Dieselgenerador);///Sec2
        Adblue = (CheckBox) findViewById(R.id.Adblue);///////Sec3

        SeguridadR = (CheckBox) findViewById(R.id.SeguridadR);///Principal2
        TomaNiveles = (CheckBox) findViewById(R.id.TomaNiveles);///Sec4
        Calibracion = (CheckBox) findViewById(R.id.Calibracion);///Sec5
        Curso = (CheckBox) findViewById(R.id.Curso);///Sec6


        RevisionTHSCAN = (CheckBox) findViewById(R.id.RevisionTHSCAN);///Principal3
        Acelerador = (CheckBox) findViewById(R.id.Acelerador);///Sec7
        Detector = (CheckBox) findViewById(R.id.Detector);///Sec8
        Mecanicas = (CheckBox) findViewById(R.id.Mecanicas);///Sec9
        Electrico = (CheckBox) findViewById(R.id.Electrico);///Sec10
        Operacion= (CheckBox) findViewById(R.id.Operacion);///Sec11

        VisitaTecnica = (CheckBox) findViewById(R.id.VisitaTecnica);///Principal4
        Chasis = (CheckBox) findViewById(R.id.Chasis);///Sec12
        Generador = (CheckBox) findViewById(R.id.Generador);///Sec13
        Aire = (CheckBox) findViewById(R.id.Aire);///Sec14
        Extintores = (CheckBox) findViewById(R.id.Extintores);///Sec15
        CCTV= (CheckBox) findViewById(R.id.CCTV);///Sec16

        Otros = (CheckBox) findViewById(R.id.Otros);///Principal5
        Documentacion = (CheckBox) findViewById(R.id.Documentacion);///Sec17
        Levantamiento = (CheckBox) findViewById(R.id.Levantamiento);///Sec18
        Asesoramiento = (CheckBox) findViewById(R.id.Asesoramiento);///Sec19
        Capacitacion = (CheckBox) findViewById(R.id.Capacitacion);///Sec20
        Electrificacion= (CheckBox) findViewById(R.id.Electrificacion);///Sec21
        Ubicacion = (CheckBox) findViewById(R.id.Ubicacion);///Sec22
        CentroServicio = (CheckBox) findViewById(R.id.CentroServicio);///Sec23
        otros= (CheckBox) findViewById(R.id.otros);///Sec24




        SuministrodeCheck = (LinearLayout) findViewById(R.id.SuministrodeCheck);//P1
        SuministrodeComen = (LinearLayout) findViewById(R.id.SuministrodeComen);//P1
        SeguridadRCheck = (LinearLayout) findViewById(R.id.SeguridadRCheck);//P2
        TomaNivelesComen = (LinearLayout) findViewById(R.id.TomaNivelesComen);//P2
        Revision = (LinearLayout) findViewById(R.id.Revision);//P3
        RevisionCome = (LinearLayout) findViewById(R.id.RevisionCome);//P3
        Servicio= (LinearLayout) findViewById(R.id.Servicio);//P4
        ChasisCome = (LinearLayout) findViewById(R.id.ChasisCome);//P4
        Documentaci= (LinearLayout) findViewById(R.id.Documentaci);//P5
        OtrosCome = (LinearLayout) findViewById(R.id.OtrosCome);//P5

        Dieselchasiscomen = (EditText) findViewById(R.id.Dieselchasiscomen); ///Sec1
        Dieselgeneradorcomen = (EditText) findViewById(R.id.Dieselgeneradorcomen);///Sec2
        Adbluecomen = (EditText) findViewById(R.id.Adbluecomen);///Sec3
        TomadeNivelesComen = (EditText) findViewById(R.id. TomadeNivelesComen);///Sec4
        CalibraciónComen = (EditText) findViewById(R.id.CalibraciónComen);///Sec5
        CursoComen = (EditText) findViewById(R.id.CursoComen );///Sec6
        AceleadorComen = (EditText) findViewById(R.id. AceleadorComen);///Sec7
        DetectorComen = (EditText) findViewById(R.id.DetectorComen);///Sec8
        MecanicasComen = (EditText) findViewById(R.id.MecanicasComen);///Sec9
        ElectricoComen = (EditText) findViewById(R.id.ElectricoComen);///Sec10
        OperacionComen = (EditText) findViewById(R.id.OperacionComen);///Sec11
        ChasisComen = (EditText) findViewById(R.id. ChasisComen);///Sec12
        GeneradorComen = (EditText) findViewById(R.id.GeneradorComen);///Sec13
        AireComen = (EditText) findViewById(R.id.AireComen);///Sec14
        ExtintoresComen = (EditText) findViewById(R.id.ExtintoresComen);///Sec15
        CCTVComen= (EditText) findViewById(R.id.CCTVComen);///Sec16
        DocumentacionComen= (EditText) findViewById(R.id.DocumentacionComen);///Sec17
        LevantamientoComen= (EditText) findViewById(R.id.LevantamientoComen);///Sec18
        AsesoramientoComen= (EditText) findViewById(R.id.AsesoramientoComen);///Sec19
        CapacitacionComen= (EditText) findViewById(R.id.CapacitacionComen);///Sec20
        ElectrificacionComen= (EditText) findViewById(R.id.ElectrificacionComen);///Sec21
        UbicacionComen= (EditText) findViewById(R.id.UbicacionComen);///Sec22
        CentroServicioComen= (EditText) findViewById(R.id.CentroServicioComen);///Sec23
        otrosComen= (EditText) findViewById(R.id.otrosComen);///Sec24

        Obs= (EditText) findViewById(R.id.Obs);///Sec24

        Firma1View = (ImageView)findViewById(R.id.Firma1View);
        Firma2View = (ImageView)findViewById(R.id.Firma2View);
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

    public void ConfiguraIncio(){
        TicketRDI.setText(Datos[12]);
        AduanaRDI.setText(Datos[13]);
        EquipoRDI.setText(Datos[14]);
        UbicacionRDI.setText(Datos[15]);
        SerieRDI.setText(Datos[16]);
        FechaInicioRDI.setText(Datos[17]+" "+Datos[18]);
        FechaFinRDI.setText(Datos[19]+" "+Datos[20]);
        Nombre1.setText(Datos[4]);
        Puesto1.setText(Datos[5]);
        Nombre2.setText(Datos[6]);
        Puesto2.setText(Datos[7]);
        SetCheckSec();
        ActualizaPant();
        GuardaCheckSecundario();
        SetCheckSec();
        SetComentarios();
        CargaImagen("firmaSeguritech", Firma1View );
        CargaImagen("firmaNuctec", Firma2View );
    }
    public void SuministrodeClick(View view){
        if (Suministrode.isChecked()){
            SharedPreferences sharedPreferences = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putBoolean("CheckP1", true);
            myEdit.commit();
            SetCheckSec();
            ActualizaPant();
            GuardaCheckSecundario();
            SetCheckSec();

        }else{
            SharedPreferences sharedPreferences = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putBoolean("CheckP1", false);
            myEdit.commit();
            SetCheckSec();
            ActualizaPant();
            GuardaCheckSecundario();
            SetCheckSec();
        }
    }
    public void SeguridadRClick(View view){
        if (SeguridadR.isChecked()){
            SharedPreferences sharedPreferences = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putBoolean("CheckP2", true);
            myEdit.commit();
            SetCheckSec();
            ActualizaPant();
            GuardaCheckSecundario();
            SetCheckSec();

        }else{
            SharedPreferences sharedPreferences = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putBoolean("CheckP2", false);
            myEdit.commit();
            SetCheckSec();
            ActualizaPant();
            GuardaCheckSecundario();
            SetCheckSec();

        }
    }
    public void RevisionClick(View view){
        if (RevisionTHSCAN.isChecked()){
            SharedPreferences sharedPreferences = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putBoolean("CheckP3", true);
            myEdit.commit();
            SetCheckSec();
            ActualizaPant();
            GuardaCheckSecundario();
            SetCheckSec();
        }else{
            SharedPreferences sharedPreferences = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putBoolean("CheckP3", false);
            myEdit.commit();
            SetCheckSec();
            ActualizaPant();
            GuardaCheckSecundario();
            SetCheckSec();
        }
    }
    public void VisitaTecnicaClick(View view){
        if (VisitaTecnica.isChecked()){
            SharedPreferences sharedPreferences = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putBoolean("CheckP4", true);
            myEdit.commit();
            SetCheckSec();
            ActualizaPant();
            GuardaCheckSecundario();
            SetCheckSec();
        }else{
            SharedPreferences sharedPreferences = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putBoolean("CheckP4", false);
            myEdit.commit();
            SetCheckSec();
            ActualizaPant();
            GuardaCheckSecundario();
            SetCheckSec();
        }
    }
    public void OtrosClick(View view){
        if (Otros.isChecked()){
            SharedPreferences sharedPreferences = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putBoolean("CheckP5", true);
            myEdit.commit();
            ActualizaPant();
            GuardaCheckSecundario();

        }else{
            SharedPreferences sharedPreferences = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putBoolean("CheckP5", false);
            myEdit.commit();
            ActualizaPant();
            GuardaCheckSecundario();
        }
    }
    private void ActualizaPant(){
        if (ObtenStatusCheck()[0]){
            SuministrodeCheck.setVisibility(View.VISIBLE);
            SuministrodeComen.setVisibility(View.VISIBLE);
            Suministrode.setChecked(true);
        }
        else{
            SuministrodeCheck.setVisibility(View.GONE);
            SuministrodeComen.setVisibility(View.GONE);
            Suministrode.setChecked(false);
            Dieselchasis.setChecked(false);///Sec1
            Dieselgenerador .setChecked(false);///Sec2
            Adblue.setChecked(false);///////Sec3
            //GuardaCheckSecundario();
        }
        if (ObtenStatusCheck()[1]){
            SeguridadRCheck.setVisibility(View.VISIBLE);
            TomaNivelesComen.setVisibility(View.VISIBLE);
            SeguridadR.setChecked(true);
        }
        else{
            SeguridadRCheck.setVisibility(View.GONE);
            TomaNivelesComen.setVisibility(View.GONE);
            SeguridadR.setChecked(false);
            TomaNiveles.setChecked(false);///Sec4
            Calibracion .setChecked(false);///Sec5
            Curso.setChecked(false);///Sec6
            //GuardaCheckSecundario();
        }
        if (ObtenStatusCheck()[2]){
            Revision.setVisibility(View.VISIBLE);
            RevisionCome.setVisibility(View.VISIBLE);
            RevisionTHSCAN.setChecked(true);
        }
        else{
            Revision.setVisibility(View.GONE);
            RevisionCome.setVisibility(View.GONE);
            RevisionTHSCAN.setChecked(false);
            Acelerador.setChecked(false);//Sec7
            Detector.setChecked(false);///Sec8
            Mecanicas.setChecked(false);///Sec9
            Electrico.setChecked(false);///Sec10
            Operacion.setChecked(false);///Sec11
            //GuardaCheckSecundario();
        }
        if (ObtenStatusCheck()[3]){
            Servicio.setVisibility(View.VISIBLE);
            ChasisCome.setVisibility(View.VISIBLE);
            VisitaTecnica.setChecked(true);
        }
        else{
            Servicio.setVisibility(View.GONE);
            ChasisCome.setVisibility(View.GONE);
            VisitaTecnica.setChecked(false);
            Chasis.setChecked(false);///Sec12
            Generador.setChecked(false);///Sec13
            Aire.setChecked(false);///Sec14
            Extintores.setChecked(false);///Sec15
            CCTV.setChecked(false);///Sec16
            //GuardaCheckSecundario();
        }
        if (ObtenStatusCheck()[4]){
            Documentaci.setVisibility(View.VISIBLE);
            OtrosCome.setVisibility(View.VISIBLE);
            Otros.setChecked(true);

        }
        else{
            Documentaci.setVisibility(View.GONE);
            OtrosCome.setVisibility(View.GONE);
            Otros.setChecked(false);
            Documentacion.setChecked(false);///Sec17
            Levantamiento.setChecked(false);///Sec18
            Asesoramiento.setChecked(false);///Sec19
            Capacitacion.setChecked(false);///Sec20
            Electrificacion.setChecked(false);///Sec21
            Ubicacion.setChecked(false);///Sec22
            CentroServicio.setChecked(false);///Sec23
            otros.setChecked(false);///Sec24
            //GuardaCheckSecundario();
        }

    }
    private Boolean [] ObtenStatusCheck(){
        Boolean [] Check = new Boolean[5];
        SharedPreferences sh = getSharedPreferences("Datos" + Datos[0], MODE_PRIVATE);
        for (int i=0;i<5;i++){
            Check[i] = sh.getBoolean("CheckP"+(i+1), false);
        }
        return Check;
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
    @Override
    public void onBackPressed() {
        SharedPreferences sh = getSharedPreferences("Datos", MODE_PRIVATE);
        String CorreoElectronico = sh.getString("CorreoElectronicoSeguritech", "");
        String Numero= sh.getString("Telefono", "");
        Datos[8] =CorreoElectronico;
        Datos[9]=Numero;
        Intent i = new Intent(this,Ticketslist.class);
        Datos[1]="Interno";
        i.putExtra("Datos",Datos);
        startActivity(i);
    }
    public void EditaDatos(View view){
        Datos[1]="RDI";
        Intent intent = new Intent(this,EditaDatos.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    public void TORFotograficoI(View view){
        Intent intent = new Intent(this,InternoRF.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    public void CrearRDI(View view){
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
                //GuardaDiagnostico();
                SharedPreferences sh = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
                interno.CreaReporteDigital(Datos,imageData,ObtenComentarios(),ObtenCheck());
                Datos[1]="Interno";
                Datos[29]="RD";
                Intent intent = new Intent(this,VisorPDF.class);
                intent.putExtra("Datos",Datos);
                startActivity(intent);
            }catch (Exception e){
                Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
            }
        }
    }
    private boolean ValidaDatos(){
        //ObtenEstatusEquipo();
        if (nombreANAM.equals("Seleccione personal")){
            Toast.makeText(this,"Seleccione credencial de ANAM",Toast.LENGTH_SHORT).show();
            return false;
        }
        else{

            return true;
        }
    }
    public void SeleccionaCredencialANAM(View view){
        Datos[1]="Interno";
        Intent intent = new Intent(this,PersonalNuctec.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    public void SeleccionaCredencialSeguritech(View view){
        Datos[1]="InternoSeguritech";
        Intent intent = new Intent(this,ListaUsuarios.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    public void ToFirmas(View view){
        Datos[1]="Interno";
        Datos[29]="Seguritech";
        Intent intent = new Intent(this,Firmas.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);

    }
    public void ToFirmas2(View view){
        Datos[1]="Interno";
        Datos[29]="Nuctec";
        Intent intent = new Intent(this,Firmas.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);

    }
    public void ClickCheckSecundaria(View view){
        GuardaCheckSecundario();
        SetCheckSec();
        GuardaComentarios();
    }
    public void ClickGuarda(View view){
        GuardaComentarios();
        SetComentarios();
    }
    private String[] ObtenComentarios(){
        String [] Comentarios = new String[]{
                Dieselchasiscomen.getText().toString(), ///Sec1
        Dieselgeneradorcomen.getText().toString(),///Sec2
        Adbluecomen.getText().toString(),///Sec3
        TomadeNivelesComen .getText().toString(),///Sec4
        CalibraciónComen.getText().toString(),///Sec5
        CursoComen .getText().toString(),///Sec6
        AceleadorComen.getText().toString(),///Sec7
        DetectorComen .getText().toString(),///Sec8
        MecanicasComen .getText().toString(),///Sec9
        ElectricoComen.getText().toString(),///Sec10
        OperacionComen .getText().toString(),///Sec11
        ChasisComen .getText().toString(),///Sec12
        GeneradorComen .getText().toString(),///Sec13
        AireComen .getText().toString(),///Sec14
        ExtintoresComen.getText().toString(),///Sec15
        CCTVComen.getText().toString(),///Sec16
        DocumentacionComen.getText().toString(),///Sec17
        LevantamientoComen.getText().toString(),///Sec18
        AsesoramientoComen.getText().toString(),//Sec19
        CapacitacionComen.getText().toString(),///Sec20
        ElectrificacionComen.getText().toString(),///Sec21
        UbicacionComen.getText().toString(),///Sec22
        CentroServicioComen.getText().toString(),///Sec23
        otrosComen.getText().toString(),///Sec24
        Obs.getText().toString()///Sec24
        };

        return Comentarios;

    }
    private Boolean[] ObtenCheck(){
        Boolean [] Check = new Boolean[]{
                Suministrode.isChecked(),///Principal1
        Dieselchasis.isChecked(),///Sec1
        Dieselgenerador .isChecked(),///Sec2
        Adblue.isChecked(),///////Sec3
        SeguridadR.isChecked(),///Principal2
        TomaNiveles .isChecked(),///Sec4
        Calibracion .isChecked(),///Sec5
        Curso .isChecked(),///Sec6
        RevisionTHSCAN .isChecked(),///Principal3
        Acelerador .isChecked(),//Sec7
        Detector .isChecked(),///Sec8
        Mecanicas .isChecked(),///Sec9
        Electrico .isChecked(),///Sec10
        Operacion.isChecked(),///Sec11
        VisitaTecnica .isChecked(),///Principal4
        Chasis.isChecked(),///Sec12
        Generador .isChecked(),///Sec13
        Aire .isChecked(),///Sec14
        Extintores .isChecked(),///Sec15
        CCTV.isChecked(),///Sec16
        Otros .isChecked(),///Principal5
        Documentacion .isChecked(),///Sec17
        Levantamiento .isChecked(),///Sec18
        Asesoramiento .isChecked(),///Sec19
        Capacitacion .isChecked(),///Sec20
        Electrificacion.isChecked(),///Sec21
        Ubicacion .isChecked(),///Sec22
        CentroServicio .isChecked(),///Sec23
        otros.isChecked()///Sec24
        };

        return Check;

    }
    private Boolean[] ObtenCheckGuardadasSec(){
        Boolean [] Check = new Boolean[ObtenCheck().length-5];

        SharedPreferences sh = getSharedPreferences("Datos" + Datos[0], MODE_PRIVATE);

        for (int i=0;i<ObtenCheck().length-5;i++){
            Check[i] = sh.getBoolean("CheckSec"+(i+1), false);
        }

        return Check;

    }
    private void GuardaCheckSecundario(){
        SharedPreferences sharedPreferences = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        int x = 1;

        for (int i=0;i<ObtenCheck().length;i++){
            if (i==0||i==4||i==8||i==14||i==20){

            }
            else{
                myEdit.putBoolean("CheckSec"+(x), ObtenCheck()[i]);
                x++;
            }
        }
        myEdit.commit();
    }
    private void GuardaComentarios() {
        SharedPreferences sharedPreferences = getSharedPreferences("Datos" + Datos[0], MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        for (int i = 0; i < ObtenComentarios().length; i++) {
            myEdit.putString("Comen" + (i + 1), ObtenComentarios()[i]);
        }
        myEdit.commit();
    }
    private void SetCheckSec(){
        Dieselchasis.setChecked(ObtenCheckGuardadasSec()[0]);///Sec1
        Dieselgenerador.setChecked(ObtenCheckGuardadasSec()[1]);///Sec2
        Adblue.setChecked(ObtenCheckGuardadasSec()[2]);///////Sec3
        TomaNiveles.setChecked(ObtenCheckGuardadasSec()[3]);///Sec4
        Calibracion.setChecked(ObtenCheckGuardadasSec()[4]);///Sec5
        Curso.setChecked(ObtenCheckGuardadasSec()[5]);///Sec6
        Acelerador.setChecked(ObtenCheckGuardadasSec()[6]);///Sec7
        Detector.setChecked(ObtenCheckGuardadasSec()[7]);///Sec8
        Mecanicas.setChecked(ObtenCheckGuardadasSec()[8]);///Sec9
        Electrico.setChecked(ObtenCheckGuardadasSec()[9]);///Sec10
        Operacion.setChecked(ObtenCheckGuardadasSec()[10]);///Sec11
        Chasis.setChecked(ObtenCheckGuardadasSec()[11]);///Sec12
        Generador.setChecked(ObtenCheckGuardadasSec()[12]);///Sec13
        Aire.setChecked(ObtenCheckGuardadasSec()[13]);///Sec14
        Extintores.setChecked(ObtenCheckGuardadasSec()[14]);///Sec15
        CCTV.setChecked(ObtenCheckGuardadasSec()[15]);///Sec16
        Documentacion.setChecked(ObtenCheckGuardadasSec()[16]);///Sec17
        Levantamiento .setChecked(ObtenCheckGuardadasSec()[17]);///Sec18
        Asesoramiento .setChecked(ObtenCheckGuardadasSec()[18]);///Sec19
        Capacitacion.setChecked(ObtenCheckGuardadasSec()[19]);///Sec20
        Electrificacion.setChecked(ObtenCheckGuardadasSec()[20]);///Sec21
        Ubicacion .setChecked(ObtenCheckGuardadasSec()[21]);///Sec22
        CentroServicio.setChecked(ObtenCheckGuardadasSec()[22]);///Sec23
        otros.setChecked(ObtenCheckGuardadasSec()[23]);///Sec24
        if (ObtenCheckGuardadasSec()[0]){
            Dieselchasiscomen.setVisibility(View.VISIBLE);///Sec1

        }else{
            Dieselchasiscomen.setVisibility(View.GONE);///Sec1
            Dieselchasiscomen.setText("");
        }
        if (ObtenCheckGuardadasSec()[1]){
            Dieselgeneradorcomen.setVisibility(View.VISIBLE);///Sec2

        }else{
            Dieselgeneradorcomen.setVisibility(View.GONE);///Sec2
            Dieselgeneradorcomen.setText("");///Sec2
        }
        if (ObtenCheckGuardadasSec()[2]){
            Adbluecomen .setVisibility(View.VISIBLE);///Sec3


        }else{
            Adbluecomen .setVisibility(View.GONE);///Sec3
            Adbluecomen.setText("");///Sec2
        }
        if (ObtenCheckGuardadasSec()[3]){
            TomadeNivelesComen.setVisibility(View.VISIBLE);//Sec4

        }else{
            TomadeNivelesComen.setVisibility(View.GONE);//Sec4
            TomadeNivelesComen.setText("");//Sec4
        }
        if (ObtenCheckGuardadasSec()[4]){
            CalibraciónComen.setVisibility(View.VISIBLE);///Sec5

        }else{
            CalibraciónComen.setVisibility(View.GONE);///Sec5
            CalibraciónComen.setText("");///Sec5
        }
        if (ObtenCheckGuardadasSec()[5]){
            CursoComen.setVisibility(View.VISIBLE);///Sec6

        }else{
            CursoComen.setVisibility(View.GONE);///Sec6
            CursoComen.setText("");///Sec6
        }
        if (ObtenCheckGuardadasSec()[6]){
            AceleadorComen.setVisibility(View.VISIBLE);///Sec7

        }else{
            AceleadorComen.setVisibility(View.GONE);///Sec7
            AceleadorComen.setText("");///Sec7
        }
        if (ObtenCheckGuardadasSec()[7]){
            DetectorComen.setVisibility(View.VISIBLE);///Sec8

        }else{
            DetectorComen.setVisibility(View.GONE);///Sec8
            DetectorComen.setText("");///Sec8
        }
        if (ObtenCheckGuardadasSec()[8]){
            MecanicasComen.setVisibility(View.VISIBLE);///Sec9

        }else{
            MecanicasComen.setVisibility(View.GONE);///Sec9
            MecanicasComen.setText("");///Sec9
        }
        if (ObtenCheckGuardadasSec()[9]){
            ElectricoComen.setVisibility(View.VISIBLE);///Sec10

        }else{
            ElectricoComen.setVisibility(View.GONE);///Sec10
            ElectricoComen.setText("");///Sec10
        }
        if (ObtenCheckGuardadasSec()[10]){
            OperacionComen.setVisibility(View.VISIBLE);///Sec11

        }else{
            OperacionComen.setVisibility(View.GONE);///Sec11
            OperacionComen.setText("");
        }
        if (ObtenCheckGuardadasSec()[11]){
            ChasisComen .setVisibility(View.VISIBLE);///Sec12

        }else{
            ChasisComen.setVisibility(View.GONE);///Sec12
            ChasisComen.setText("");
        }
        if (ObtenCheckGuardadasSec()[12]){
            GeneradorComen.setVisibility(View.VISIBLE);///Sec13

        }else{
            GeneradorComen.setVisibility(View.GONE);///Sec13
            GeneradorComen.setText("");
        }
        if (ObtenCheckGuardadasSec()[13]){
            AireComen .setVisibility(View.VISIBLE);///Sec14

        }else{
            AireComen.setVisibility(View.GONE);///Sec14
            AireComen.setText("");
        }
        if (ObtenCheckGuardadasSec()[14]){
            ExtintoresComen.setVisibility(View.VISIBLE);///Sec15

        }else{
            ExtintoresComen.setVisibility(View.GONE);///Sec15
            ExtintoresComen.setText("");
        }
        if (ObtenCheckGuardadasSec()[15]){
            CCTVComen.setVisibility(View.VISIBLE);///Sec16

        }else{
            CCTVComen.setVisibility(View.GONE);///Sec16
            CCTVComen.setText("");
        }
        if (ObtenCheckGuardadasSec()[16]){
            DocumentacionComen.setVisibility(View.VISIBLE);///Sec17

        }else{
            DocumentacionComen.setVisibility(View.GONE);///Sec17
            DocumentacionComen.setText("");///Sec17
        }
        if (ObtenCheckGuardadasSec()[17]){
            LevantamientoComen.setVisibility(View.VISIBLE);///Sec18

        }else{
            LevantamientoComen.setVisibility(View.GONE);///Sec18
            LevantamientoComen.setText("");///Sec17
        }
        if (ObtenCheckGuardadasSec()[18]){
            AsesoramientoComen.setVisibility(View.VISIBLE);///Sec19

        }else{
            AsesoramientoComen.setVisibility(View.GONE);///Sec19
            AsesoramientoComen.setText("");///Sec19
        }
        if (ObtenCheckGuardadasSec()[19]){
            CapacitacionComen.setVisibility(View.VISIBLE);///Sec20

        }else{
            CapacitacionComen.setVisibility(View.GONE);///Sec20
            CapacitacionComen.setText("");///Sec20
        }
        if (ObtenCheckGuardadasSec()[20]){
            ElectrificacionComen.setVisibility(View.VISIBLE);///Sec21

        }else{
            ElectrificacionComen.setVisibility(View.GONE);///Sec21
            ElectrificacionComen.setText("");///Sec21

        }
        if (ObtenCheckGuardadasSec()[21]){
            UbicacionComen.setVisibility(View.VISIBLE);///Sec22

        }else{
            UbicacionComen.setVisibility(View.GONE);///Sec22
            UbicacionComen.setText("");///Sec22
        }
        if (ObtenCheckGuardadasSec()[22]){
            CentroServicioComen.setVisibility(View.VISIBLE);///Sec23

        }else{
            CentroServicioComen.setVisibility(View.GONE);///Sec23
            CentroServicioComen.setText("");///Sec23
        }
        if (ObtenCheckGuardadasSec()[23]){
            otrosComen.setVisibility(View.VISIBLE);///Sec24
        }else{
            otrosComen.setVisibility(View.GONE);///Sec24
            otrosComen.setText("");///Sec24
        }


    }
    private String[] ObtenComentariosGuardados(){
        String [] Comentarios = new String[ObtenComentarios().length];

        SharedPreferences sh = getSharedPreferences("Datos" + Datos[0], MODE_PRIVATE);

        for (int i=0;i<ObtenComentarios().length;i++){
            Comentarios[i] = sh.getString("Comen"+(i+1), "");
        }

        return Comentarios;

    }
    private void SetComentarios(){
        Dieselchasiscomen.setText(ObtenComentariosGuardados()[0]); ///Sec1
        Dieselgeneradorcomen.setText(ObtenComentariosGuardados()[1]);///Sec2
        Adbluecomen.setText(ObtenComentariosGuardados()[2]);///Sec3
        TomadeNivelesComen.setText(ObtenComentariosGuardados()[3]);///Sec4
        CalibraciónComen.setText(ObtenComentariosGuardados()[4]);///Sec5
        CursoComen.setText(ObtenComentariosGuardados()[5]);///Sec6
        AceleadorComen.setText(ObtenComentariosGuardados()[6]);///Sec7
        DetectorComen.setText(ObtenComentariosGuardados()[7]);///Sec8
        MecanicasComen.setText(ObtenComentariosGuardados()[8]);///Sec9
        ElectricoComen.setText(ObtenComentariosGuardados()[9]);///Sec10
        OperacionComen .setText(ObtenComentariosGuardados()[10]);///Sec11
        ChasisComen .setText(ObtenComentariosGuardados()[11]);///Sec12
        GeneradorComen .setText(ObtenComentariosGuardados()[12]);///Sec13
        AireComen.setText(ObtenComentariosGuardados()[13]);///Sec14
        ExtintoresComen.setText(ObtenComentariosGuardados()[14]);///Sec15
        CCTVComen.setText(ObtenComentariosGuardados()[15]);///Sec16
        DocumentacionComen.setText(ObtenComentariosGuardados()[16]);///Sec17
        LevantamientoComen.setText(ObtenComentariosGuardados()[17]);///Sec18
        AsesoramientoComen.setText(ObtenComentariosGuardados()[18]);///Sec19
        CapacitacionComen.setText(ObtenComentariosGuardados()[19]);///Sec20
        ElectrificacionComen.setText(ObtenComentariosGuardados()[20]);///Sec21
        UbicacionComen.setText(ObtenComentariosGuardados()[21]);///Sec22
        CentroServicioComen.setText(ObtenComentariosGuardados()[22]);///Sec23
        otrosComen.setText(ObtenComentariosGuardados()[23]);///Sec24
        Obs.setText(ObtenComentariosGuardados()[24]);///Sec25
    }

    private void CargaImagen(String Nombre,ImageView Foto) {
        String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
        String rutacarpetaImagenes = "ProyectoX/" + "Interno/" + Datos[0] + "/" + Datos[0] + "(CF)/";

        File directorioImagenes = new File(ExternalStorageDirectory + rutacarpetaImagenes);
        if (!directorioImagenes.exists()){
            directorioImagenes.mkdirs();
        }
        else{
        }
        File Credencial = new File(ExternalStorageDirectory + rutacarpetaImagenes+Nombre+".png");
        if (!Credencial.exists()){
            /*StorageReference Credref = storageReference.child("Credenciales").child(Where).child(Nombre+".jpg");
            try {
                File localFile = File.createTempFile(Nombre, "jpg");
                Credref.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        Foto.setImageBitmap(bitmap);
                        GuardarenGaleria(bitmap,"ProyectoX/Credenciales/"+Where,Nombre);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(PreventivoRD.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException ioException) {
                Toast.makeText(CorrectivoRD.this, ioException.toString(), Toast.LENGTH_SHORT).show();
            }*/
        }
        else{
            ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
            Uri uri = Uri.fromFile(new File(ExternalStorageDirectory + rutacarpetaImagenes+Nombre+".png"));
            try {
                Bitmap bitmapi = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Foto.setImageBitmap(bitmapi);
            } catch (IOException e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}