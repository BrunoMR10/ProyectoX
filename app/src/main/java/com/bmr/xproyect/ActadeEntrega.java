package com.bmr.xproyect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SymbolTable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bmr.xproyect.Datos.Datos;
import com.bmr.xproyect.Documentos.ActaEntrega;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ActadeEntrega extends AppCompatActivity {
    String []Datos,DatosDirección;
    String documento,servicio,nombreANAM,NombreSeguritech,puestoseguritech,puestoANAM,CambioCredencial,numerocredencialANAM,NumerocredencialSeguritech,direccionaduana,nombreaduana,fecha,hora,equipo;
    Boolean Credenciales;
    ActaEntrega actaEntrega = new ActaEntrega();
    com.bmr.xproyect.Datos.Datos dt = new Datos();
    TextView Servicio,Equipo,Fecha,Hora,NombreANAMvi,PuestoANAMvi,NumeroempleadoANAM,nombreSeguritech,Puestoseguritechvi,EmpleadoSeguritech,DireccionAduana,nombreAduana;
    Bitmap Foto1,Foto2,Foto3,Foto4;
    ImageView CredFrontalANAM,CredTraseraANAM,INEFrontal,INETrasero;
    ProgressBar Chargin1,Chargin2,Chargin3,Chargin4;
    public FirebaseStorage storage = FirebaseStorage.getInstance();
    public StorageReference storageReference = storage.getReferenceFromUrl("gs://proyecto-x-933f4.appspot.com");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actade_entrega);
        Chargin1 = (ProgressBar)findViewById(R.id.Chargin1) ;
        Chargin2 = (ProgressBar)findViewById(R.id.Chargin2) ;
        Chargin3 = (ProgressBar)findViewById(R.id.Chargin3) ;
        Chargin4 = (ProgressBar)findViewById(R.id.Chargin4) ;

        CredFrontalANAM = (ImageView) findViewById(R.id.CredFrontalANAM);
        CredTraseraANAM = (ImageView) findViewById(R.id.CredTraseraANAM);
        INEFrontal = (ImageView) findViewById(R.id.INEFrontal);
        INETrasero = (ImageView) findViewById(R.id.INETrasero);

        Equipo = (TextView) findViewById(R.id.Equipo);
        Fecha = (TextView) findViewById(R.id.Fecha);
        Hora = (TextView) findViewById(R.id.Hora);
        Servicio = (TextView) findViewById(R.id.Servicio);

        NombreANAMvi = (TextView) findViewById(R.id.NombreANAMvi);
        PuestoANAMvi = (TextView) findViewById(R.id.PuestoANAMvi);
        NumeroempleadoANAM = (TextView) findViewById(R.id.NumeroempleadoANAM);
        nombreSeguritech = (TextView) findViewById(R.id.nombreSeguritech);
        Puestoseguritechvi = (TextView) findViewById(R.id.Puestoseguritechvi);
        EmpleadoSeguritech = (TextView) findViewById(R.id.EmpleadoSeguritech);
        DireccionAduana = (TextView) findViewById(R.id.DireccionAduana);
        nombreAduana = (TextView) findViewById(R.id.nombreAduana);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                Datos = extras.getStringArray("Datos");
                CambioCredencial = extras.getString("CambioCredencial");
                numerocredencialANAM= extras.getString("CredANAM");
                NumerocredencialSeguritech= extras.getString("CredSeguritech");
                DatosDirección = extras.getStringArray("DatosDirección");
                documento= extras.getString("GeneraDocumento");
            }
        }
        else {
            Datos = (String[]) savedInstanceState.getSerializable("Datos");
            DatosDirección = (String[]) savedInstanceState.getSerializable("DatosDirección");
            CambioCredencial = (String) savedInstanceState.getSerializable("CambioCredencial");
            documento = (String) savedInstanceState.getSerializable("GeneraDocumento");
            numerocredencialANAM = (String) savedInstanceState.getSerializable("CredANAM");
            NumerocredencialSeguritech = (String) savedInstanceState.getSerializable("CredSeguritech");

        }

            if (CambioCredencial != null) {
                SharedPreferences sh = getSharedPreferences("ActaEntrega", MODE_PRIVATE);
                if (CambioCredencial.contains("Seguri")) {
                    System.out.println("Seguritech seleccionado");
                    NombreSeguritech = Datos[4];
                    puestoseguritech = Datos[5];
                    nombreANAM = sh.getString("Datos6", "Seleccione personal");
                    puestoANAM = sh.getString("Datos7", "");
                    System.out.println("Nombre ANAM"+nombreANAM);
                    System.out.println("Nombre Seguritech"+NombreSeguritech);
                    if (NumerocredencialSeguritech == null)NumerocredencialSeguritech = "";
                        numerocredencialANAM = sh.getString("CredANAM", "");

                } else {
                    System.out.println("ANAM seleccionado");
                    nombreANAM = Datos[6];
                    puestoANAM = Datos[7];
                    NombreSeguritech = sh.getString("Datos4", "Seleccione personal");
                    puestoseguritech = sh.getString("Datos5", "");
                    System.out.println("Nombre ANAM"+nombreANAM);
                    System.out.println("Nombre Seguritech"+NombreSeguritech);
                    if (numerocredencialANAM == null) numerocredencialANAM = "";
                        NumerocredencialSeguritech = sh.getString("CredSeguritech", "");
                }
                System.out.println("CredencialANAM " + numerocredencialANAM);


                Credenciales = true;
                GuardaDatosSP();
                //CredencialesRutina();
            } else {
                SharedPreferences sh = getSharedPreferences("ActaEntrega", MODE_PRIVATE);

                NombreSeguritech = sh.getString("Datos4", "");
                puestoseguritech = sh.getString("Datos5", "");
                nombreANAM = sh.getString("Datos6", "Seleccione personal");
                puestoANAM = sh.getString("Datos7", "");
                if (NombreSeguritech.equals("")) {
                    SharedPreferences sh2 = getSharedPreferences("Datos", MODE_PRIVATE);
                    NombreSeguritech = sh2.getString("NombreCompletoSeguritech", "");
                    puestoseguritech = sh2.getString("PuestoSeguritech", "");
                    NumerocredencialSeguritech = sh2.getString("Cred", "");
                    System.out.println(NumerocredencialSeguritech);
                    System.out.println("Nombre Seguritech: " + NombreSeguritech);
                    Datos[4] = NombreSeguritech;
                    Datos[5] = puestoseguritech;
                    Datos[6] = nombreANAM;
                    Datos[7] = puestoANAM;

                    GuardaDatosSP();
                }
                if (DatosDirección == null) {
                    direccionaduana = sh.getString("direccionaduana", "");
                    nombreaduana = sh.getString("nombreaduana", "");
                } else {
                    System.out.println(DatosDirección[0]);
                    System.out.println(DatosDirección[6]);
                    direccionaduana = DatosDirección[0];
                    nombreaduana = DatosDirección[6];
                    GuardaDatosSP();
                }
            }
            ConfiguraIncio();

    }
    private void CredencialesRutina(){

        CredFrontalANAM = (ImageView) findViewById(R.id.CredFrontalANAM);
        CredTraseraANAM = (ImageView) findViewById(R.id.CredTraseraANAM);
        INEFrontal = (ImageView) findViewById(R.id.INEFrontal);
        INETrasero = (ImageView) findViewById(R.id.INETrasero);

        Foto3=ValidaFotointernal(NombreSeguritech+" "+"INEFrontal");
        Foto4=ValidaFotointernal(NombreSeguritech+" "+"INETrasera");
        Foto1=ValidaFotointernal(nombreANAM+" "+"Frontal");
        Foto2=ValidaFotointernal(nombreANAM+" "+"Trasera");

        if (Foto3==null) {INEFrontal.setVisibility(View.GONE);ObtenCredencialesfirebase("Seguritech",NombreSeguritech+" "+"INEFrontal",INEFrontal,Chargin1);}
        else INEFrontal.setImageBitmap(Foto3);
        if (Foto4==null) {INETrasero.setVisibility(View.GONE);ObtenCredencialesfirebase("Seguritech",NombreSeguritech+" "+"INETrasera",INETrasero,Chargin2);}
        else INETrasero.setImageBitmap(Foto4);
        if (Foto1==null) {CredFrontalANAM.setVisibility(View.GONE);ObtenCredencialesfirebase("ANAM",nombreANAM+" "+"Frontal",CredFrontalANAM,Chargin3);}
        CredFrontalANAM.setImageBitmap(Foto1);
        if (Foto2==null) {CredTraseraANAM.setVisibility(View.GONE);ObtenCredencialesfirebase("ANAM",nombreANAM+" "+"Trasera",CredTraseraANAM,Chargin4);}
        CredTraseraANAM.setImageBitmap(Foto2);

    }
    private void ObtenCredencialesfirebase(String Where,String Nombre,ImageView Foto,ProgressBar chargin) {
        StorageReference Credref = storageReference.child("Credenciales").child(Where).child(Nombre + ".jpg");
        chargin.setVisibility(View.VISIBLE);
        System.out.println("Obteniendo de Firebase: "+Nombre);
        try {
            File localFile = File.createTempFile(Nombre, "jpg");
            Credref.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    Foto.setImageBitmap(bitmap);
                    Foto.setVisibility(View.VISIBLE);
                    chargin.setVisibility(View.GONE);
                    GuardaenInternal(bitmap,"Actadeentrga",Nombre);
                    System.out.println("Foto Obtenida con exito");
                    //GuardarenGaleria(bitmap,"ProyectoX/Preventivo/"+Datos[0],Nombre);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println("Fallo la foto"+e);
                    chargin.setVisibility(View.GONE);
                    Foto.setVisibility(View.VISIBLE);
                    //Toast.makeText(PreventivoRD.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException ioException) {
            chargin.setVisibility(View.GONE);
            Foto.setVisibility(View.VISIBLE);
            //Toast.makeText(ActadeEntrega.this, ioException.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    private Bitmap ValidaFotointernal(String Nombre){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("Actadeentrga", Context.MODE_PRIVATE);
        File file;
        System.out.println("Obteniendo en internal: "+Nombre);
        file = new File(directory, Nombre+".png");
        System.out.println("Obteniendo Credencial");
        if (file.exists()){
            System.out.println("Se encontro e internal");
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            return bitmap;
        }
        else {
            System.out.println("No se encontro e internal");
            return null;
        }

    }
    public void ConfiguraIncio(){
        SharedPreferences sh = getSharedPreferences("ActaEntrega",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        NombreSeguritech = sh.getString("Datos4", "");
        puestoseguritech = sh.getString("Datos5", "");
        nombreANAM = sh.getString("Datos6", "Seleccione personal");
        puestoANAM = sh.getString("Datos7", "");
        NumerocredencialSeguritech=sh.getString("CredSeguritech", "");
        numerocredencialANAM=sh.getString("CredANAM", "");
        direccionaduana = sh.getString("direccionaduana", "");
        nombreaduana = sh.getString("nombreaduana", "");
        equipo = sh.getString("equipo", "TFN CK-");
        servicio = sh.getString("servicio", "Servicio");
        DateFormat dateFormat = new SimpleDateFormat("dd/MMMM/yyyy");
        DateFormat dateFormat2 = new SimpleDateFormat("H:mm");

        fecha = dateFormat.format(Calendar.getInstance().getTime());
        fecha = fecha.replace("/"," de ");
        hora =  dateFormat2.format(Calendar.getInstance().getTime());






        if (NombreSeguritech.equals(""))nombreSeguritech.setVisibility(View.GONE);
        if (puestoseguritech.equals(""))Puestoseguritechvi.setVisibility(View.GONE);
        if (nombreANAM.equals(""))NombreANAMvi.setVisibility(View.GONE);
        if (puestoANAM.equals(""))PuestoANAMvi.setVisibility(View.GONE);
        if (NumerocredencialSeguritech.equals(""))EmpleadoSeguritech.setVisibility(View.GONE);
        if (numerocredencialANAM.equals(""))NumeroempleadoANAM.setVisibility(View.GONE);
        if (direccionaduana.equals(""))DireccionAduana.setVisibility(View.GONE);
        if (nombreaduana.equals(""))nombreAduana.setVisibility(View.GONE);

        CredencialesRutina();


        Equipo.setText(equipo);
        NombreANAMvi.setText(nombreANAM);
        PuestoANAMvi .setText(puestoANAM);
        NumeroempleadoANAM .setText(numerocredencialANAM);
        nombreSeguritech .setText(NombreSeguritech);
        Puestoseguritechvi.setText(puestoseguritech);
        EmpleadoSeguritech.setText(NumerocredencialSeguritech);
        DireccionAduana.setText(direccionaduana);
        nombreAduana.setText(nombreaduana);
        Fecha.setText(fecha);
        Hora.setText(hora);
        Servicio.setText(servicio);

        if (documento!=null){
            rutinaCreaDocumento();
        }
        else {
        }
    }

    private void GuardaSharededit(){
        SharedPreferences sharedPreferences = getSharedPreferences("ActaEntrega",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("Datos4",nombreSeguritech.getText().toString());
        myEdit.putString("Datos5",Puestoseguritechvi.getText().toString());
        myEdit.putString("Datos6",NombreANAMvi.getText().toString());
        myEdit.putString("Datos7",PuestoANAMvi.getText().toString());
        myEdit.putString("CredSeguritech",EmpleadoSeguritech.getText().toString());
        myEdit.putString("CredANAM",NumeroempleadoANAM.getText().toString());
        myEdit.putString("direccionaduana",DireccionAduana.getText().toString());
        myEdit.putString("nombreaduana",nombreAduana.getText().toString());
        myEdit.putString("equipo",Equipo.getText().toString());
        myEdit.putString("fecha",Fecha.getText().toString());
        myEdit.putString("hora",Hora.getText().toString());
        myEdit.putString("servicio",Servicio.getText().toString());
        myEdit.commit();
    }
    private void GuardaenInternal(Bitmap bitmap, String rutacarpeta, String nombre){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(rutacarpeta, Context.MODE_PRIVATE);
        File file = new File(directory, nombre+ ".png");
        Log.d("path", file.toString());
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

    }
    public void CredencialSeguritech(View view){
        Datos[1]="ActaentregaSeguritech";
        Intent intent = new Intent(this,ListaUsuarios.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    public void CredencialANAM(View view){
        Datos[1]="ActaentregaANAM";
        Intent intent = new Intent(this,PersonalANAM.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    public void Aduana(View view){
        Intent intent = new Intent(this,Aduanas.class);
        intent.putExtra("Where","Actadeentrega");
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    public void CreaDocumento(View view){
        GuardaSharededit();
        try{actaEntrega.CreaDocumento();}
        catch (IOException e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }
        //rutinaCreaDocumento();
        Intent intent = new Intent(this,ActadeEntrega.class);
        intent.putExtra("GeneraDocumento","true");
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    private void rutinaCreaDocumento(){
        //GuardaSharededit();
        try {
            actaEntrega.CreaDocumento();
            Intent i = new Intent(this,VisorPDF.class);
            Datos[0]="Actadenetrega";
            i.putExtra("Datos",Datos);
            startActivity(i);

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void GuardaDatosSP(){

        SharedPreferences sharedPreferences = getSharedPreferences("ActaEntrega",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        if (DatosDirección!=null){
            myEdit.putString("direccionaduana",DatosDirección[6]);
            myEdit.putString("nombreaduana",DatosDirección[0]);
            for(int i=0;i<DatosDirección.length;i++){
                myEdit.putString("DatosDireccion"+i, DatosDirección[i]);
            }
        }else{
            myEdit.putString("direccionaduana",direccionaduana);
            myEdit.putString("nombreaduana",nombreaduana);
            myEdit.putString("CredSeguritech",NumerocredencialSeguritech);
            myEdit.putString("CredANAM",numerocredencialANAM);
            for(int i=0;i<Datos.length;i++){
                if (CambioCredencial==null){
                    myEdit.putString("Datos" + i, Datos[i]);
                }else {
                    if (CambioCredencial.contains("Seguri")) {
                        if (i == 6) {
                            myEdit.putString(sharedPreferences.getString("Datos6", "Seleccione personal"), Datos[i]);
                        } else if (i == 7) {
                            myEdit.putString(sharedPreferences.getString("Datos7", ""), Datos[i]);
                        } else {
                            myEdit.putString("Datos" + i, Datos[i]);
                        }

                    } else {
                        if (i == 4) {
                            myEdit.putString(sharedPreferences.getString("Datos4", "Seleccione personal"), Datos[i]);
                        } else if (i == 5) {
                            myEdit.putString(sharedPreferences.getString("Datos5", ""), Datos[i]);
                        } else {
                            myEdit.putString("Datos" + i, Datos[i]);
                        }
                    }
                }
            }
        }
        myEdit.commit();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,Servicios.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }

}