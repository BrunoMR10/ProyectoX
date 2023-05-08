package com.bmr.xproyect;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bmr.xproyect.Herramientas.Firebase;
import com.bmr.xproyect.Herramientas.Permisos;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class EditaUsuario extends AppCompatActivity {
    public FirebaseStorage storage = FirebaseStorage.getInstance();
    public StorageReference storageReference = storage.getReferenceFromUrl("gs://proyecto-x-933f4.appspot.com");
    Firebase fb = new Firebase();
    Permisos permisos =  new Permisos();
    Boolean foto1,foto2,foto3,foto4;
EditText Puestoedit,Correoedit,Numeroedit,NumeroGet;
TextView Nombresedit,correocomen,TraseraComen,Numerodesc;
TextView CredencialElector,CredencialElectortrasera;
ImageView Frontal,Trasera,INEfrontal,INEtrasera;
ImageButton SubeTrasera,SubeINEF,SubeINET;
ProgressBar CargaFoto1,CargaFoto2;
String Cred;

String[]Datos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_usuario);
        CredencialElector = (TextView) findViewById(R.id.CredencialElector);
        CredencialElectortrasera = (TextView) findViewById(R.id.CredencialElectortrasera);
        SubeINEF = (ImageButton) findViewById(R.id.SubeINEF);
        SubeINET = (ImageButton) findViewById(R.id.SubeINET);
        INEfrontal = (ImageView) findViewById(R.id.INEfrontal);
        INEtrasera = (ImageView) findViewById(R.id.INEtrasera);

        Nombresedit = (TextView) findViewById(R.id.Nombresedit);
        Puestoedit = (EditText) findViewById(R.id.Puestoedit);
        Correoedit = (EditText) findViewById(R.id.Correoedit);
        Numeroedit = (EditText) findViewById(R.id.Numeroedit);
        NumeroGet = (EditText) findViewById(R.id.NumeroGet);
        correocomen = (TextView) findViewById(R.id.correocomen);
        TraseraComen = (TextView) findViewById(R.id.TraseraComen);
        Numerodesc = (TextView) findViewById(R.id.Numerodesc);
        Frontal = (ImageView) findViewById(R.id.Frontal);
        Trasera = (ImageView) findViewById(R.id.Trasera);
        SubeTrasera = (ImageButton) findViewById(R.id.SubeTrasera);
        CargaFoto1 = (ProgressBar) findViewById(R.id.CargaFoto1);
        CargaFoto2 = (ProgressBar) findViewById(R.id.CargaFoto2);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                Datos = extras.getStringArray("Datos");
                Cred = extras.getString("Cred");
            }
        }
        else {
            Datos = (String[]) savedInstanceState.getSerializable("Datos");
            Cred =(String) savedInstanceState.getSerializable("Cred");
        }

        Toast.makeText(this,Datos[1],Toast.LENGTH_SHORT).show();
        ConfiguraInicio();
    }

    private void ConfiguraInicio(){

        if (Datos[1].equals("PantallaPrincipal")){

            SharedPreferences sh = getSharedPreferences("Datos", MODE_PRIVATE);
            String NombreCompleto = sh.getString("NombreCompletoSeguritech", "");
            String Puesto = sh.getString("PuestoSeguritech", "");
            String CorreoElectronico = sh.getString("CorreoElectronicoSeguritech", "");
            String Numero= sh.getString("TelefonoSeguritech", "");
            String Cred2= sh.getString("Cred", "");
            Correoedit .setText(CorreoElectronico);
            Numeroedit.setText(Numero);
            Nombresedit .setText(NombreCompleto);
            Puestoedit.setText(Puesto);
            NumeroGet.setText(Cred2);
            CargaImagen2(Datos[4],Frontal,"Seguritech",CargaFoto1);
            Trasera.setVisibility(View.GONE);
            INEfrontal.setVisibility(View.VISIBLE);
            INEtrasera.setVisibility(View.VISIBLE);
            CredencialElectortrasera.setVisibility(View.VISIBLE);
            CredencialElector.setVisibility(View.VISIBLE);
            SubeINEF.setVisibility(View.VISIBLE);
            SubeINET.setVisibility(View.VISIBLE);

            TraseraComen.setVisibility(View.GONE);
            SubeTrasera.setVisibility(View.GONE);
        }else {
            Correoedit .setText(Datos[8]);
            Numeroedit.setText(Datos[9]);
            Nombresedit .setText(Datos[6]);
            Puestoedit.setText(Datos[7]);
            if (Cred!=null) NumeroGet.setText(Cred);
            if(Datos[1].equals("FavoritosNuctec")||Datos[1].equals("GeneralNuctec")){
                CargaImagen2(Datos[6],Frontal,"Nuctech",CargaFoto1);
                Trasera.setVisibility(View.GONE);
                TraseraComen.setVisibility(View.GONE);
                SubeTrasera.setVisibility(View.GONE);
                Numerodesc.setVisibility(View.GONE);
                NumeroGet.setVisibility(View.GONE);


            }else{
                Numerodesc.setText("Numero de empleado");
                CargaImagen2(Datos[6]+" "+"Frontal",Frontal,"ANAM",CargaFoto1);
                CargaImagen2(Datos[6]+" "+"Trasera",Trasera,"ANAM",CargaFoto2);
            }

        }
    }
    public void Actualizando(View view){
        String[]DatosANAM = new String[]{
                Correoedit.getText().toString(),
                Numeroedit.getText().toString(),
                "Nombre",
                "ApellidoPa",
                "ApellidoMa",
                Puestoedit.getText().toString(),
                Nombresedit.getText().toString(),
                NumeroGet.getText().toString()
        };
        if(Datos[1].equals("FavoritosNuctec")||Datos[1].equals("GeneralNuctec")){
            Frontal.setDrawingCacheEnabled(true);
            Frontal.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) Frontal.getDrawable()).getBitmap();
            GuardarenGaleria(bitmap,"ProyectoX/Credenciales/"+"Nuctech",Datos[6]);
            fb.SubeCred(Frontal,Trasera,DatosANAM,"Nuctech",this,Datos);
            fb.NombreUsuarios("Nuctech",Datos[6]);
        }else if (Datos[1].equals("PantallaPrincipal")){
            Frontal.setDrawingCacheEnabled(true);
            Frontal.buildDrawingCache();
            INEfrontal.setDrawingCacheEnabled(true);
            INEfrontal.buildDrawingCache();
            INEtrasera.setDrawingCacheEnabled(true);
            INEtrasera.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) Frontal.getDrawable()).getBitmap();
            Bitmap bitmap2 = ((BitmapDrawable) INEfrontal.getDrawable()).getBitmap();
            Bitmap bitmap3 = ((BitmapDrawable) INEtrasera.getDrawable()).getBitmap();

            GuardarenGaleria(bitmap,"ProyectoX/Credenciales/"+"Seguritech",Datos[4]);
            //fb.SubeCred(Frontal,Trasera,DatosANAM,"Seguritech",this,Datos);
            if (bitmap2!=null&&bitmap3!=null)  fb.SubeCred2(Frontal,INEfrontal,INEtrasera,DatosANAM,"Seguritech",this,Datos);
            else Toast.makeText(this,"Suba todas las fotos",Toast.LENGTH_SHORT).show();
        }
        else{
            Frontal.setDrawingCacheEnabled(true);
            Frontal.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) Frontal.getDrawable()).getBitmap();
            GuardarenGaleria(bitmap,"ProyectoX/Credenciales/"+"ANAM",Datos[6]+" "+"Frontal");
            Trasera.setDrawingCacheEnabled(true);
            Trasera.buildDrawingCache();
            Bitmap bitmap2 = ((BitmapDrawable) Trasera.getDrawable()).getBitmap();
            GuardarenGaleria(bitmap2,"ProyectoX/Credenciales/"+"ANAM",Datos[6]+" "+"Trasera");
            fb.SubeCred(Frontal,Trasera,DatosANAM,"Frontal",this,Datos);
        }
    }
    private void CargaImagen(String Nombre,ImageView Foto,String Where){
        String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
        File directorioImagenes = new File(ExternalStorageDirectory + "ProyectoX/Credenciales/"+Where);
        if (!directorioImagenes.exists()){
            directorioImagenes.mkdirs();
        }
        else{
        }
        File Credencial = new File(ExternalStorageDirectory + "ProyectoX/Credenciales/"+Where+"/"+Nombre+".png");
        if (!Credencial.exists()){
            StorageReference Credref = storageReference.child("Credenciales").child(Where).child(Nombre+".jpg");
            try {
                File localFile = File.createTempFile(Nombre, "jpg");
                Credref.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        Foto.setImageBitmap(bitmap);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditaUsuario.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException ioException) {
                Toast.makeText(EditaUsuario.this, ioException.toString(), Toast.LENGTH_SHORT).show();
            }

        }
        else{
            ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
            Uri uri = Uri.fromFile(new File(ExternalStorageDirectory + "ProyectoX/Credenciales/"+Where+"/"+Nombre+".png"));
            try {
                Bitmap bitmapi = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Foto.setImageBitmap(bitmapi);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void CargaImagen2(String Nombre,ImageView Foto,String Where,ProgressBar Cargando){
            Cargando.setVisibility(View.VISIBLE);
            Foto.setVisibility(View.GONE);
            StorageReference Credref = storageReference.child("Credenciales").child(Where).child(Nombre+".jpg");
            try {
                File localFile = File.createTempFile(Nombre, "jpg");
                Credref.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        Foto.setImageBitmap(bitmap);
                        Cargando.setVisibility(View.GONE);
                        Foto.setVisibility(View.VISIBLE);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Cargando.setVisibility(View.GONE);
                        Foto.setVisibility(View.VISIBLE);
                        Toast.makeText(EditaUsuario.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException ioException) {
                Cargando.setVisibility(View.GONE);
                Foto.setVisibility(View.VISIBLE);
                Toast.makeText(EditaUsuario.this, ioException.toString(), Toast.LENGTH_SHORT).show();
            }
    }
    public void FotoFrontal(View view){
        if(permisos.Permisos(this)){
            foto1 = true;
            foto2 = false;
            foto3 = false;
            foto4 = false;
            Intent intent2 = new Intent(Intent.ACTION_PICK);
            intent2.setType("image/*");
            anotheractivityResult.launch(intent2);
        }
        else{
            Toast.makeText(this, "Concede permisos",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,permisos.PERMISSIONS_STORAGE,permisos.REQUEST_PERMISSION);
        }
    }
    public void FotoTrasera(View view){
        if(permisos.Permisos(this)){
            foto1 = false;
            foto2 = true;
            foto3 = false;
            foto4 = false;
            Intent intent2 = new Intent(Intent.ACTION_PICK);
            intent2.setType("image/*");
            anotheractivityResult.launch(intent2);
        }
        else{
            Toast.makeText(this, "Concede permisos",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,permisos.PERMISSIONS_STORAGE,permisos.REQUEST_PERMISSION);
        }

    }

    public void FotoFrontalINE(View view){
        if(permisos.Permisos(this)){
            foto1 = false;
            foto2 = false;
            foto3 = true;
            foto4 = false;
            Intent intent2 = new Intent(Intent.ACTION_PICK);
            intent2.setType("image/*");
            anotheractivityResult.launch(intent2);
        }
        else{
            Toast.makeText(this, "Concede permisos",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,permisos.PERMISSIONS_STORAGE,permisos.REQUEST_PERMISSION);
        }

    }
    public void FotoTraseraINE(View view){
        if(permisos.Permisos(this)){
            foto1 = false;
            foto2 = false;
            foto3 = false;
            foto4 = true;
            Intent intent2 = new Intent(Intent.ACTION_PICK);
            intent2.setType("image/*");
            anotheractivityResult.launch(intent2);
        }
        else{
            Toast.makeText(this, "Concede permisos",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,permisos.PERMISSIONS_STORAGE,permisos.REQUEST_PERMISSION);
        }

    }
    private void GuardarenGaleria(Bitmap bitmap,String rutacarpeta,String nombre){
        try {
            String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
            File file = new File(ExternalStorageDirectory + rutacarpeta+"/"+ nombre+".png");
            file.delete();
            if(file.exists()){
                file.getCanonicalFile().delete();
                if(file.exists()){
                    getApplicationContext().deleteFile(file.getName());
                }
            }
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(ExternalStorageDirectory + rutacarpeta+"/"+ nombre+".png"));
            Toast.makeText(EditaUsuario.this, "Credencial guardada con éxito", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, e.toString(),Toast.LENGTH_SHORT).show();
        }

    }
    ActivityResultLauncher<Intent> anotheractivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Bitmap bitmap, bitmap2;
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            int ancho = 800;
                            float proporcion = ancho / (float) bitmap.getWidth();
                            bitmap2 = Bitmap.createScaledBitmap(bitmap,ancho,(int) (bitmap.getHeight() * proporcion),false);

                        } catch (IOException e) {
                            bitmap2 = null;
                            e.printStackTrace();
                        }
                        if(foto1 == true){
                            Frontal.setImageBitmap(bitmap2);
                        }
                        else if (foto2 == true){
                            Trasera.setImageBitmap(bitmap2);
                        }
                        else if (foto3 == true){
                            INEfrontal.setImageBitmap(bitmap2);
                        }
                        else if (foto4 == true){
                            INEtrasera.setImageBitmap(bitmap2);
                        }
                        Toast.makeText(EditaUsuario.this,"Foto seleccionada con exito",Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(EditaUsuario.this,"Error al seleccionar foto",Toast.LENGTH_SHORT).show();
                    }
                }
            });
}