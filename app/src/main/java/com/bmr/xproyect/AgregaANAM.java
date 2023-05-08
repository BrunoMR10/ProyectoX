package com.bmr.xproyect;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bmr.xproyect.Datos.Datos;
import com.bmr.xproyect.Herramientas.Firebase;
import com.bmr.xproyect.Herramientas.Permisos;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AgregaANAM extends AppCompatActivity {
    Permisos permisos =  new Permisos();
    Datos dt = new Datos();
    Firebase fb = new Firebase();
    String CorreoElectronico,Nombre,ApellidoMa,ApellidoPa,Numero,Puesto,Where,Ticket;
    EditText correoelectronico,nombre,apellidoma,apellidopa,numero,puesto;
    ImageView FotoFronta,FotoTrasera;
    Boolean foto1,foto2;
    Bitmap bitmap,bitmap2;
    LinearLayout FotoANAM;
    Uri uri;
    String[]Datos;
    ProgressBar Subiendo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agrega_anam);
        correoelectronico = (EditText) findViewById(R.id.ANAM_Correo);
        nombre = (EditText) findViewById(R.id.ANAM_Nombre);
        apellidopa = (EditText) findViewById(R.id.ANAM_ApellidoPaterno);
        apellidoma = (EditText) findViewById(R.id.ANAM_ApellidoMaterno);
        numero = (EditText) findViewById(R.id.ANAM_Numero);
        puesto = (EditText) findViewById(R.id.ANAM_Puesto);
        FotoFronta = (ImageView) findViewById(R.id.Foto1ANAM);
        FotoTrasera = (ImageView) findViewById(R.id.Foto2ANAM);
        Subiendo = (ProgressBar)findViewById(R.id.Subiendo2);
        FotoANAM = (LinearLayout) findViewById(R.id.FotoANAM);
        foto1=false;
        foto2 = false;
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
            SharedPreferences sh = getSharedPreferences("Datos", MODE_PRIVATE);
            for(int i=0;i<dt.Datos.length;i++){
                Datos[i] = sh.getString("Datos"+i, "");
            }
        }
        else{
            GuardaDatosSP(Datos);
        }
        if(Datos[1].equals("FavoritosNuctec")||Datos[1].equals("GeneralNuctec")){
            FotoANAM.setVisibility(View.GONE);
        }
        else{

        }
    }
    public void RegistraANAM(View view){
        CorreoElectronico = correoelectronico.getText().toString();
        Nombre = nombre.getText().toString();
        ApellidoPa = apellidopa.getText().toString();
        ApellidoMa = apellidoma.getText().toString();
        Numero = numero.getText().toString() ;
        Puesto = puesto.getText().toString();
        if (TextUtils.isEmpty(Nombre)){
            nombre.setError("Escribe tu nombre");
            nombre.requestFocus();
        }
        else if (TextUtils.isEmpty(ApellidoPa)){
            apellidopa.setError("Escribe tu apellido Paterno");
            apellidopa.requestFocus();
        }else if (TextUtils.isEmpty(ApellidoMa)){
            apellidoma.setError("Escribe tu apellido Materno");
            apellidoma.requestFocus();

        }
        else if (TextUtils.isEmpty(Puesto)){
            puesto.setError("Escribe un puesto");
            puesto.requestFocus();
        }else if(!ValidaFotos()){
            Toast.makeText(this,"Porfavor sube las fotos de las credenciales",Toast.LENGTH_SHORT).show();
        }
        else{
            if (TextUtils.isEmpty(Numero)){
                Numero = "Dato no registrado";
            }
            else if (TextUtils.isEmpty(CorreoElectronico)){
                CorreoElectronico = "Dato no registrado";
            }
             String[]DatosANAM = new String[]{
                     CorreoElectronico,
                     Numero,
                     Nombre,
                     ApellidoPa,
                     ApellidoMa,
                     Puesto,
                     Nombre+" "+ApellidoPa+" "+ApellidoMa
             };
            Subiendo.setVisibility(View.VISIBLE);
            FotoFronta.setVisibility(View.GONE);
            FotoTrasera.setVisibility(View.GONE);
            if(Datos[1].equals("FavoritosNuctec")||Datos[1].equals("GeneralNuctec")){
                fb.SubeCred(FotoFronta,FotoTrasera,DatosANAM,"Nuctech",this,Datos);
            }else{
                fb.SubeCred(FotoFronta,FotoTrasera,DatosANAM,"Frontal",this,Datos);
            }

           /* Map<String, Object> UsuarioNuevo = new HashMap<>();
            UsuarioNuevo.put("CorreoElectronico",CorreoElectronico);
            UsuarioNuevo.put("Numero",Numero);
            UsuarioNuevo.put("Nombre",Nombre);
            UsuarioNuevo.put("ApellidoPaterno",ApellidoPa);
            UsuarioNuevo.put("ApellidoMaterno",ApellidoMa);
            UsuarioNuevo.put("Puesto",Puesto);
            UsuarioNuevo.put("NombreCompleto",Nombre+" "+ApellidoPa+" "+ApellidoMa);
            //UsuarioNuevo.put("Registro",Datos[0]);
            refUsuarios.child(Nombre+" "+ApellidoPa+" "+ApellidoMa).updateChildren(UsuarioNuevo);
            Toast.makeText(this,"Registrando",Toast.LENGTH_SHORT).show();

            SubeCred(FotoFronta,Nombre+" "+ApellidoPa+" "+ApellidoMa,"Frontal");
            SubeCred(FotoTrasera,Nombre+" "+ApellidoPa+" "+ApellidoMa,"Trasera");*/
            ToListadoANAM();
        }
    }
    public boolean ValidaFotos(){
        Drawable drawable = FotoFronta.getDrawable();
        Drawable drawable2 = FotoTrasera.getDrawable();
        boolean hasImage = (drawable != null);
        boolean hasImage2 = (drawable2 != null);
        if((Datos[1].equals("FavoritosNuctec")||Datos[1].equals("GeneralNuctec"))){
            if (hasImage && (drawable instanceof BitmapDrawable)){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            if (hasImage && (drawable instanceof BitmapDrawable) &&
                    hasImage2 && (drawable2 instanceof BitmapDrawable)){
                return true;
            }
            else{
                return false;
            }
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
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,ListaUsuarios.class);
        i.putExtra("Datos",Datos);
        startActivity(i);
    }
    public void ToListadoANAM(){
        Intent intent = new Intent(this, ListaUsuarios.class);;
        intent.putExtra("Datos", Datos);
        startActivity(intent);

    }
    public void FotoFrontal(View view){
        if(permisos.Permisos(this)){
            foto1 = true;
            foto2 = false;
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
            Intent intent2 = new Intent(Intent.ACTION_PICK);
            intent2.setType("image/*");
            anotheractivityResult.launch(intent2);
        }
        else{
            Toast.makeText(this, "Concede permisos",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,permisos.PERMISSIONS_STORAGE,permisos.REQUEST_PERMISSION);
        }

    }
    ActivityResultLauncher<Intent> anotheractivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        uri = result.getData().getData();
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            int ancho = 800;
                            float proporcion = ancho / (float) bitmap.getWidth();
                            bitmap2 = Bitmap.createScaledBitmap(bitmap,ancho,(int) (bitmap.getHeight() * proporcion),false);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(foto1 == true){
                            FotoFronta.setImageBitmap(bitmap2);
                        }
                        else if (foto2 == true){
                            FotoTrasera.setImageBitmap(bitmap2);
                        }
                        Toast.makeText(AgregaANAM.this,"Foto seleccionada con exito",Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(AgregaANAM.this,"Error al seleccionar foto",Toast.LENGTH_SHORT).show();
                    }
                }
            });

}