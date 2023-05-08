package com.bmr.xproyect;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bmr.xproyect.Datos.Datos;
import com.bmr.xproyect.Herramientas.Firebase;
import com.bmr.xproyect.Herramientas.Permisos;
import com.bmr.xproyect.Herramientas.ValidaInternet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
     Datos dt = new Datos();
     ValidaInternet vi = new ValidaInternet();
     Permisos permisos = new Permisos();
     Firebase fb = new Firebase();
     EditText correo,contreseña;
     Boolean Online;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        correo = (EditText)findViewById(R.id.Email_set);
        contreseña = (EditText)findViewById(R.id.Password_set);
        if (vi.ValidaInternet(this)){
            Online = true;
            //fb.DatosUsuario(dt.Datos,this);
        }
        else{
            Toast.makeText(this,"Sin acceso a internet, entrando en modo fuera de linea",Toast.LENGTH_SHORT).show();
            Online = false;
        }

        if(permisos.Permisos(this)){
        }
        else{
            Toast.makeText(this, "Concede permisos",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,permisos.PERMISSIONS_STORAGE,permisos.REQUEST_PERMISSION);
        }

    }
    private boolean ValidaDatos(){
        String CorreoElectronico = correo.getText().toString();
        String Contraseña = contreseña.getText().toString();
        if (TextUtils.isEmpty(CorreoElectronico)){
            correo.setError("Escribe un Email");
            correo.requestFocus();
            return false;
        }else if (TextUtils.isEmpty(Contraseña)){
            contreseña.setError("Escribe una Contreseña");
            contreseña.requestFocus();
            return false;
        }else if (Online == true){
            Toast.makeText(this,"Online",Toast.LENGTH_SHORT).show();
            return IngresoOnline(CorreoElectronico,Contraseña);
        }
        else if (Online == false){
            Toast.makeText(this,"Offline",Toast.LENGTH_SHORT).show();
            return IngresoOffline(CorreoElectronico,Contraseña);
        }
        return false;
    }
    private boolean IngresoOnline(String CorreoElectronico,String Contraseña){
       fb.Ingresa(this,CorreoElectronico,Contraseña,this);
       return true;
    }
    private boolean IngresoOffline(String CorreoElectronico,String Contraseña){
        Boolean Contraseñacorrecta = false;
        Boolean CorreoCorrecto= false;
        Toast.makeText(this,"Intentando acceder sin conexión",Toast.LENGTH_SHORT).show();
        SharedPreferences sh = getSharedPreferences("UsuarioPasword", MODE_PRIVATE);
        String Usuario2 = sh.getString("Usuario", "");
        String Contraseña2 = sh.getString("Contraseña", "");

        if (Usuario2.equals("")||Contraseña2.equals("")) {
            Toast.makeText(MainActivity.this, "No se puede acceder", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            if (Usuario2.equals(CorreoElectronico)){
                CorreoCorrecto= true;
            }else{
                correo.setError("Correo incorrecto");
                correo.requestFocus();
            }
            if (Contraseña2.equals(Contraseña)){
                Contraseñacorrecta = true;
            }else{
                contreseña.setError("Contraseña incorrecta");
                contreseña.requestFocus();
            }

            if(Contraseñacorrecta == true && CorreoCorrecto == true){
                return true;
            }
            else{
                return true;
            }

        }

    }
    public void ToPantallaRegistra(View v){
        Intent i =  new Intent(this,Registro_Usuarios.class);
        startActivity(i);
    }
    public void ToPantallaPrincipal(View v){
        //ValidaDatos();
        if (!ValidaDatos()){
            Toast.makeText(this,"No se puedo acceder",Toast.LENGTH_SHORT).show();
        }else{

        }
    }


}