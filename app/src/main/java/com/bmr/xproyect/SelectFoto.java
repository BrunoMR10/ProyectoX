package com.bmr.xproyect;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bmr.xproyect.Adapters.RFPAdapter;
import com.bmr.xproyect.Herramientas.Permisos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SelectFoto extends AppCompatActivity {
    int ID;
    String Nomenclatura,Where,rutaImagen;;
    String [] Datos;
    Permisos permisos = new Permisos();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_foto);
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
        Nomenclatura = Datos[0];
        Where = Datos[1];
        ID = Integer.parseInt(Datos[27]);
    }
    public void SeleccionarFoto(View view){
        if(permisos.Permisos(this)){
            Intent intent2 = new Intent(Intent.ACTION_PICK);
            intent2.setType("image/*");
            anotheractivityResult.launch(intent2);
        }
        else{
            Toast.makeText(this, "Concede permisos",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,permisos.PERMISSIONS_STORAGE,permisos.REQUEST_PERMISSION);
        }

    }

    public void AbreCamara(View view){
        if(permisos.Permisos(this)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File imagenarchivo = null;
            try{
                imagenarchivo = crearImagen();
            }catch (IOException ex){
                Log.e("Error",ex.toString());
            }

            if (imagenarchivo != null) {
                Uri fotoUri = FileProvider.getUriForFile(this,"com.bmr.xproyect.fileprovider",imagenarchivo);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,fotoUri);
                someActivityResultLauncher.launch(intent);
            }
        }
        else{
            Toast.makeText(this, "Concede permisos",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,permisos.PERMISSIONS_STORAGE,permisos.REQUEST_PERMISSION);
        }

    }
    private File crearImagen() throws IOException {
        String nombeImagen = "foto_";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_DCIM);
        File imagen = File.createTempFile(nombeImagen,".jpg",directorio);
        rutaImagen=imagen.getAbsolutePath();
        return imagen;
    }

    public void GuardarenGaleria(Bitmap bitmap,String rutacarpeta,String nombre){

        try {
            String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
            File directorioImagenes = new File(ExternalStorageDirectory + rutacarpeta);
            if (!directorioImagenes.exists())
                directorioImagenes.mkdirs();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(ExternalStorageDirectory + rutacarpeta+"/"+ nombre));
            Intent intent = new Intent(this, PreventivoRF.class);
            intent.putExtra("Datos",Datos);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
            Log.e("ERROR", e.getMessage());
        }

    }
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
                    String rutacarpeta =  rutacarpeta = "ProyectoX/"+Where+"/" + Nomenclatura + "/" + Nomenclatura + "(CF)/";;
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes

                        try {
                            Bitmap bitmap = BitmapFactory.decodeFile(rutaImagen);
                            int ancho = 800;
                            float proporcion = ancho / (float) bitmap.getWidth();
                            Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap,ancho,(int) (bitmap.getHeight() * proporcion),false);
                            GuardarenGaleria(bitmap2,rutacarpeta,"foto"+ID+".png");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(SelectFoto.this,"Foto tomada con exito",Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(SelectFoto.this,"Error al tomar foto",Toast.LENGTH_SHORT).show();
                    }
                }
            });


    ActivityResultLauncher<Intent> anotheractivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
                    String rutacarpeta =  rutacarpeta = "ProyectoX/"+Where+"/" + Nomenclatura + "/" + Nomenclatura + "(CF)/";;
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            int ancho = 800;
                            float proporcion = ancho / (float) bitmap.getWidth();
                            Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap,ancho,(int) (bitmap.getHeight() * proporcion),false);
                            GuardarenGaleria(bitmap2,rutacarpeta,"foto"+ID+".png");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(SelectFoto.this,"Foto seleccionada con exito",Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(SelectFoto.this,"Error al seleccionar foto",Toast.LENGTH_SHORT).show();
                    }
                }
            });
}