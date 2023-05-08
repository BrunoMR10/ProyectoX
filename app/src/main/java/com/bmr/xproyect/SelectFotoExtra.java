package com.bmr.xproyect;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bmr.xproyect.DataBases.FotosHelperDB;
import com.bmr.xproyect.DataBases.FotosManageDB;
import com.bmr.xproyect.Herramientas.Permisos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SelectFotoExtra extends AppCompatActivity {
    int ID;
    String Nomenclatura,Where,rutaImagen,NombreFoto,Comentario;
    String [] Datos;
    Permisos permisos = new Permisos();
    ImageView FotoPreview;
    EditText Descripcion;
    Bitmap Foto;
    String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
    String rutacarpeta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_foto_extra);
        FotoPreview = (ImageView) findViewById(R.id.FotoPreview);
        Descripcion = (EditText) findViewById(R.id.DescripcionView);
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
        Comentario = Datos[28];

        if (Where.equals("Correctivo")||Where.equals("CorrectivosEdit")){
            NombreFoto = "foto";
            rutacarpeta = "ProyectoX/"+"Correctivo"+"/" + Nomenclatura + "/" + Nomenclatura + "(CF)/";
        }
        else if (Where.equals("FotosExtra")||Where.equals("ExtraEdit")){
            rutacarpeta = "ProyectoX/"+"Preventivo"+"/" + Nomenclatura + "/" + Nomenclatura + "(CF)/";
            NombreFoto = "extra";
        }
        else if (Where.equals("Interno")||Where.equals("InternoEdit")){
            rutacarpeta = "ProyectoX/"+"Interno"+"/" + Nomenclatura + "/" + Nomenclatura + "(CF)/";
            NombreFoto = "foto";
        }
        if (Where.equals("CorrectivosEdit")||Where.equals("ExtraEdit")||Where.equals("InternoEdit")){
            String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
            Uri uri = Uri.fromFile(new File(ExternalStorageDirectory + rutacarpeta + NombreFoto+ID+".png"));
            try {
                Foto = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                FotoPreview.setImageBitmap(Foto);
                Descripcion.setText(Comentario);
            } catch (IOException e) {
                Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
            }
        }

    }

    public void SeleccionaFoto(View view){
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

    public boolean ValidaFoto(){
        Drawable drawable = FotoPreview.getDrawable();
        boolean hasImage = (drawable != null);
        if (hasImage && (drawable instanceof BitmapDrawable)){
            return true;
        }
        else{
            return false;
        }

    }

    public void GuardarCambios(View view){
        String Desc = Descripcion.getText().toString();
        if (!ValidaFoto()){
            Toast.makeText(this,"Porfavor toma o selecciona una foto",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(Desc)){
            Descripcion.setError("Añade una descripción");
            Descripcion.requestFocus();
        }else{
            Bitmap bm=((BitmapDrawable)FotoPreview.getDrawable()).getBitmap();
            SharedPreferences sh = getSharedPreferences("Datos"+Nomenclatura, MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sh.edit();
            if (Where.equals("CorrectivosEdit")||Where.equals("ExtraEdit")||Where.equals("InternoEdit")){
                FotosManageDB base = new FotosManageDB(SelectFotoExtra.this,Nomenclatura);
                myEdit.putString("ComentarioFoto"+(ID),Desc);
                if (Where.equals("ExtraEdit"))myEdit.putBoolean("ReporteFoto"+(ID), true);
                else myEdit.putBoolean("ReporteFoto"+(ID), true);
                myEdit.commit();
                if(base.editaFoto(Nomenclatura, Desc,ID)){
                    //Toast.makeText(this,"RegistroCorrecto",Toast.LENGTH_LONG).show();
                    GuardarenGaleria(bm,rutacarpeta,NombreFoto+ID+".png");
                }
            }else{
                ID = Integer.parseInt(sh.getString("idExtra", "0"));
                ID ++;
                GuardarenGaleria(bm,rutacarpeta,NombreFoto+ID+".png");
                myEdit.putString("idExtra",String.valueOf(ID));
                myEdit.putString("ComentarioFoto"+(ID),Desc);
                myEdit.putBoolean("ReporteFoto"+(ID), true);
                myEdit.putInt("ID", ID);
                myEdit.commit();

                ActualizaSQLfavoritos(Desc);
            }

            if (Where.equals("Correctivo")||Where.equals("CorrectivosEdit")){
                ToListadoCorrectivos();

            }
            else if (Where.equals("FotosExtra")||Where.equals("ExtraEdit")) {
                ToListadoFotos();
            }
            else if (Where.equals("Interno")||Where.equals("InternoEdit")) {
                ToListadoInterno();
            }


        }
    }

    public void ToListadoFotos(){
        Intent intent = new Intent(this,FotosExtra.class);
        Datos[1]="Preventivo";
        intent.putExtra("Nomenclatura",Nomenclatura);
        intent.putExtra("Datos",Datos);
        startActivity(intent);

    }
    public void ToListadoCorrectivos(){
        Intent intent = new Intent(this,CorrectivoRF.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);

    }
    public void ToListadoInterno(){
        Intent intent = new Intent(this,InternoRF.class);
        intent.putExtra("Datos",Datos);
        startActivity(intent);

    }

    private void ActualizaSQLfavoritos(String Desc){
        FotosHelperDB dbHelper = new FotosHelperDB(SelectFotoExtra.this, Nomenclatura);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if ( (db != null)) {
            FotosManageDB base = new FotosManageDB(SelectFotoExtra.this,Nomenclatura);
            long id2 = base.InsertaDatos(ID,Desc,Nomenclatura);
            if (id2 > 0) {
                //Toast.makeText(SelectFotoExtra.this, "REGISTRO CORRECTO", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SelectFotoExtra.this, "ERROR EN REGISTRO", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(SelectFotoExtra.this, "DB No encontrada", Toast.LENGTH_SHORT).show();
        }


    }
    public void Tomafoto(View view){
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

    public void GuardarenGaleria(Bitmap bitmap, String rutacarpeta, String nombre){

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


                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes

                        try {
                            Bitmap bitmap = BitmapFactory.decodeFile(rutaImagen);
                            int ancho = 800;
                            float proporcion = ancho / (float) bitmap.getWidth();
                            Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap,ancho,(int) (bitmap.getHeight() * proporcion),false);
                            FotoPreview.setImageBitmap(bitmap2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(SelectFotoExtra.this,"Foto tomada con exito",Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(SelectFotoExtra.this,"Error al tomar foto",Toast.LENGTH_SHORT).show();
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
                            FotoPreview.setImageBitmap(bitmap2);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(SelectFotoExtra.this,"Foto seleccionada con exito",Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(SelectFotoExtra.this,"Error al seleccionar foto",Toast.LENGTH_SHORT).show();
                    }
                }
            });
}