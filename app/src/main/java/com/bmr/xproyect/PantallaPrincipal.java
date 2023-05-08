package com.bmr.xproyect;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bmr.xproyect.Datos.Datos;
import com.bmr.xproyect.Herramientas.Firebase;
import com.bmr.xproyect.Herramientas.ValidaInternet;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PantallaPrincipal extends AppCompatActivity {
    public FirebaseStorage storage = FirebaseStorage.getInstance();
    public StorageReference storageReference = storage.getReferenceFromUrl("gs://proyecto-x-933f4.appspot.com");
    ValidaInternet vi = new ValidaInternet();
    Firebase fb = new Firebase();
    Datos dt = new Datos();
    TextView Usuario,NombreSeguritech,NombreANAM;
    Button ToServicios,ToUsuarios,ToANAM;
    Boolean Online;
    String[]Datos;
    String Cred;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        Usuario = (TextView) findViewById(R.id.Usuario);
        ToServicios = (Button) findViewById(R.id.ToServicios);
        ToUsuarios = (Button) findViewById(R.id.toUsuarios);
        ToANAM = (Button) findViewById(R.id.toANAM);
        ToServicios.setVisibility(View.GONE);
        ToUsuarios.setVisibility(View.GONE);
        ToANAM.setVisibility(View.GONE);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                Datos = extras.getStringArray("Datos");
                Cred = extras.getString("Cred");

            }
        }
        else{
            Datos = (String[]) savedInstanceState.getSerializable("Datos");
            Cred = (String) savedInstanceState.getSerializable("Cred");
        }
        if (vi.ValidaInternet(this)){
            Online = true;
            if (Datos == null){
                fb.DatosUsuario(dt.Datos,this);
            }
            else{
                GuardaDatosSP(Datos);
                ToServicios.setVisibility(View.VISIBLE);
                ToANAM.setVisibility(View.VISIBLE);
                ToUsuarios.setVisibility(View.VISIBLE);
                Usuario.setText(Datos[4]);

            }
        }
        else{
            Toast.makeText(this,"Modo Fuera de linea",Toast.LENGTH_SHORT).show();
            Online = false;
            DatosOffline();
        }
        ObtenCredencial(Datos[4]);


    }

    private void DatosOffline(){
        Datos = dt.Datos;
        SharedPreferences sh = getSharedPreferences("Datos", MODE_PRIVATE);
        String NombreCompleto = sh.getString("NombreCompletoSeguritech", "");
        String Puesto = sh.getString("PuestoSeguritech", "");
        String CorreoElectronico = sh.getString("CorreoElectronicoSeguritech", "");
        String Numero= sh.getString("Telefono", "");
        Boolean Verificado = sh.getBoolean("Verificado", false);

        if (NombreCompleto.equals("")||
                CorreoElectronico.equals("")||
                Puesto.equals("")||
                Numero.equals("")) {
            Toast.makeText(PantallaPrincipal.this, "No se encuentran los datos", Toast.LENGTH_SHORT).show();
            ToServicios.setVisibility(View.GONE);

            Usuario.setText("No se encontraron Datos");
        }
        else if(Verificado==false){
            Toast.makeText(PantallaPrincipal.this, "Debes Verificar el Usuario", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PantallaPrincipal.this, PantallaPrincipal.class));
        }
        else{
            ToServicios.setVisibility(View.VISIBLE);
            ToANAM.setVisibility(View.VISIBLE);
            ToUsuarios.setVisibility(View.VISIBLE);
            Usuario.setText(NombreCompleto);
            Datos[4]=NombreCompleto;
            Datos[5]=Puesto;
            Datos[8]=CorreoElectronico;
            Datos[9]=Numero;
        }
    }
    private void GuardaDatosSP(String [] Datos){
        SharedPreferences sharedPreferences = getSharedPreferences("Datos",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("NombreCompletoSeguritech", Datos[4]);
        myEdit.putString("PuestoSeguritech", Datos[5]);;
        myEdit.putString("CorreoElectronicoSeguritech", Datos[8]);
        myEdit.putString("TelefonoSeguritech", Datos[9]);
        if (Cred!=null) myEdit.putString("Cred",Cred);
        for(int i=0;i<Datos.length;i++){
            myEdit.putString("Datos"+i, Datos[i]);
        }
        myEdit.commit();
    }
    public void CerrarSesion(View view){
        if(Online) {
            fb.CerrarSesion(this);
        }else{startActivity(new Intent(PantallaPrincipal.this, MainActivity.class));}
    }
    public void ToServicios(View view){
        Intent i = new Intent(this,Servicios.class);
        i.putExtra("Datos",Datos);
        startActivity(i);
    }
    public void ToANAM(View view){
        Datos[1]="PantallaPrincipal";
        Intent intent = new Intent(this,PersonalANAM.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);

    }
    public void ToNuctech(View view){
        Datos[1]="PantallaPrincipalNuctec";
        Intent intent = new Intent(this,PersonalNuctec.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }

    public void ToUsuarios(View view){
        Datos[1]="Usuarios";
        Intent intent = new Intent(this,ListaUsuarios.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    public void ToEditaUsuario(View view){
        Datos[1]="PantallaPrincipal";
        Intent intent = new Intent(this,EditaUsuario.class);
        intent.putExtra("Datos",Datos);
        intent.putExtra("Where","PantallaPrincipal");
        startActivity(intent);
    }
    public void ToAduanas(View view){
        Intent intent = new Intent(this,Aduanas.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
    }

    public void ObtenCredencial(String NombreCompleto){
        String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
        File directorioImagenes = new File(ExternalStorageDirectory + "ProyectoX/Credenciales/Seguritech");
        if (!directorioImagenes.exists()){
            directorioImagenes.mkdirs();

        }
        else{
        }
        File Credencial = new File(ExternalStorageDirectory + "ProyectoX/Credenciales/Seguritech/"+NombreCompleto+".png");
        if (!Credencial.exists()){
            StorageReference Credref = storageReference.child("Credenciales").child("Seguritech").child(NombreCompleto+".jpg");
            try {
                File localFile = File.createTempFile(NombreCompleto, "jpg");
                Credref.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        GuardarenGaleria(bitmap,"ProyectoX/Credenciales/Seguritech",NombreCompleto);


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PantallaPrincipal.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException ioException) {
                Toast.makeText(PantallaPrincipal.this, ioException.toString(), Toast.LENGTH_SHORT).show();
            }

        }
        else{

        }
    }
    public void GuardarenGaleria(Bitmap bitmap,String rutacarpeta,String nombre){

        try {
            String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
            //File directorioImagenes = new File(ExternalStorageDirectory + rutacarpeta);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(ExternalStorageDirectory + rutacarpeta+"/"+ nombre+".png"));
            Toast.makeText(PantallaPrincipal.this, "Credencial guardada con éxito", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }

    }

}