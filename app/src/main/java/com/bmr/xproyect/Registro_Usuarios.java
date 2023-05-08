package com.bmr.xproyect;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.bmr.xproyect.Herramientas.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Registro_Usuarios extends AppCompatActivity {
    String CorreoElectronico,Nombre,ApellidoMa,ApellidoPa,Numero,Puesto,NombreCompleto,Contraseña1,Contraseña2;
    EditText correoelectronico,nombre,apellidoma,apellidopa,numero,contraseña1,contraseña2;
    Spinner puestoselec;
    ImageView Credencial;
    Firebase fb = new Firebase();
    String[] DatosUsuario;
    ProgressBar Subiendo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);
        correoelectronico = (EditText) findViewById(R.id.Usuario_Correo);
        nombre = (EditText)findViewById(R.id.Usuario_Nombre);
        apellidoma = (EditText)findViewById(R.id.Usuario_ApellidoMaterno);
        apellidopa = (EditText)findViewById(R.id.Usuario_ApellidoPaterno);
        numero = (EditText)findViewById(R.id.Usuario_Numero);
        contraseña1 = (EditText)findViewById(R.id.Usuario_Contraseña);
        contraseña2 = (EditText)findViewById(R.id.Usuario_Contraseña2);
        puestoselec = (Spinner) findViewById(R.id.PuestoSelec);
        Credencial = (ImageView)findViewById(R.id.Credencial_View);
        Subiendo = (ProgressBar)findViewById(R.id.Subiendo);
        String [] opciones = {"Seleccione Puesto","Gerente operativo","Ingeniero de soporte","Coordinador operativo","Director de proyecto"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opciones);
        puestoselec.setAdapter(adapter);

    }
    public void SeleccionaCredencial(View view){
        Intent intent2 = new Intent(Intent.ACTION_PICK);
        intent2.setType("image/*");
        activityResult.launch(intent2);
    }
    public void RegistraUsuario(View view){
            if (ValidaDatos()){
                Subiendo.setVisibility(View.VISIBLE);
                Toast.makeText(this,"Registrando Usuario",Toast.LENGTH_SHORT).show();
                RegistraenFirebase();

            }else{
                Toast.makeText(this,"Complete los datos",Toast.LENGTH_SHORT).show();
            }
    }
    private boolean ValidaDatos() {
        CorreoElectronico = correoelectronico.getText().toString();
        Nombre = nombre.getText().toString();
        ApellidoPa = apellidopa.getText().toString();
        ApellidoMa = apellidoma.getText().toString();
        Numero = numero.getText().toString();
        Puesto = puestoselec.getSelectedItem().toString();
        Contraseña1 = contraseña1.getText().toString();
        Contraseña2 = contraseña2.getText().toString();
        if (TextUtils.isEmpty(ApellidoPa)) {
            apellidopa.setError("Escribe tu apellido Paterno");
            apellidopa.requestFocus();
            return false;
        }

        else if (TextUtils.isEmpty(ApellidoMa)) {
            apellidoma.setError("Escribe tu apellido Materno");
            apellidoma.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(Nombre)) {
            nombre.setError("Escribe tu nombre");
            nombre.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(CorreoElectronico)) {
            correoelectronico.setError("Escribe tu Correo");
            correoelectronico.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(Numero)) {
            numero.setError("Escribe tu Numero");
            numero.requestFocus();
            return false;
        }
        else if (Puesto.equals("Seleccione Puesto")) {
            Toast.makeText(this, "Seleccione el puesto porfavor", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if (TextUtils.isEmpty(Contraseña1)) {
            contraseña1.setError("Escribe tu contraseña");
            contraseña1.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(Contraseña2)) {
            contraseña2.setError("Confirma tu contraseña");
            contraseña2.requestFocus();
            return false;
        }
        else if (!ValidaFotos()) {
            Toast.makeText(this, "Porfavor sube las fotos de las credenciales", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            if (Contraseña1.equals(Contraseña2)){
                NombreCompleto = Nombre+" "+ApellidoPa+" "+ApellidoMa;
                DatosUsuario = new String[]{
                        NombreCompleto,ApellidoPa,ApellidoMa,Nombre,
                        CorreoElectronico,Numero,Puesto,Contraseña1,Contraseña2
                };
                return true;
            }
            else{
                contraseña2.setError("Las contraseñas no coinciden");
                contraseña2.requestFocus();
                return false;
            }


        }
    }
    private boolean ValidaFotos(){
        Drawable drawable = Credencial.getDrawable();

        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)){
            return true;
        }
        else{
            return false;
        }

    }

    private void RegistraenFirebase(){
        fb.SubeCred(Credencial,Credencial,DatosUsuario,"Seguritech",this,DatosUsuario);
    }


    ActivityResultLauncher<Intent> activityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            int ancho = 800;
                            float proporcion = ancho / (float) bitmap.getWidth();
                            Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, ancho, (int) (bitmap.getHeight() * proporcion), false);
                            Credencial.setImageBitmap(bitmap2);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });


}