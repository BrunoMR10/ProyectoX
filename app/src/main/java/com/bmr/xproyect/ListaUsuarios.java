package com.bmr.xproyect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.bmr.xproyect.Adapters.AdapterUsuarios;
import com.bmr.xproyect.DataBases.FavoritosHelpeDB;
import com.bmr.xproyect.DataBases.FavoritosManageDB;
import com.bmr.xproyect.DataBases.TicketHelperDB;
import com.bmr.xproyect.DataBases.TicketManageDB;
import com.bmr.xproyect.Datos.Datos;
import com.bmr.xproyect.Entidades.DatosANAM;
import com.bmr.xproyect.Entidades.Users;
import com.bmr.xproyect.Herramientas.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ListaUsuarios extends AppCompatActivity implements SearchView.OnQueryTextListener {
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference refUsuarios;
    public FirebaseStorage storage = FirebaseStorage.getInstance();
    public StorageReference storageReference = storage.getReferenceFromUrl("gs://proyecto-x-933f4.appspot.com");
    Datos dt = new Datos();
    Firebase fb = new Firebase();
    String[]Datos;
    ImageButton Agrega;
    AdapterUsuarios adapterUsuarios;
    RecyclerView UsuariosView;
    SearchView BuscarUsuario;
    String ruta,db;
    DatosANAM Data;
    Boolean Elimina;
    String Favorito,Cred;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);
        BuscarUsuario = findViewById(R.id.Busca_ANAM);
        UsuariosView = (RecyclerView) findViewById(R.id.ANAM_V);
        UsuariosView.setLayoutManager(new LinearLayoutManager(this));
        Agrega = (ImageButton) findViewById(R.id.AgregaANAM);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                Datos = extras.getStringArray("Datos");
                Favorito = extras.getString("Favoritos");
                Elimina = extras.getBoolean("Elimina");

            }
        }
        else {
            Datos = (String[]) savedInstanceState.getSerializable("Datos");
            Favorito = (String) savedInstanceState.getSerializable("Favoritos");
            Elimina = (Boolean) savedInstanceState.getSerializable("Elimina");
        }
        if (Datos == null){
            Datos = new String[dt.Datos.length];
            SharedPreferences sh = getSharedPreferences("Datos", MODE_PRIVATE);
            for(int i=0;i<dt.Datos.length;i++){
                if (i==8||i==9){

                }else{
                    Datos[i] = sh.getString("Datos"+i, "");
                }

            }
        }
        else{
            //GuardaDatosSP(Datos);
        }
        System.out.println("Datos[1]"+Datos[1]);
        if (Datos[1].equals("Favoritos")||Datos[1].equals("General")||
                Datos[1].equals("PreventivoANAMFavoritos")||
                Datos[1].equals("PreventivoANAMGeneral")||
                Datos[1].equals("CorrectivoANAMFavoritos")||
                Datos[1].equals("CorrectivoANAMGeneral")||
                Datos[1].equals("ActaentregaANAM")){
            //////ANAM
                ruta = "ANAM";
                refUsuarios = database.getReference(ruta);
                db="Favoritos";
        }

        else if (Datos[1].equals("InternoNuctecFavoritos")||
                Datos[1].equals("InternoNuctecGeneral")||
                Datos[1].equals("FavoritosNuctec")||
                Datos[1].equals("GeneralNuctec")){
            //////NUCTEC
                 ruta = "Nuctech";
                 refUsuarios = database.getReference(ruta);
                 db="FavoritosNuctec";

        }
        else {

            //////SEGURITECH
                 ruta = "Seguritech";
                 refUsuarios = database.getReference("Usuarios");

        }
        //fb.Recorretodoslosusuarios();
        ValidaInicio(Favorito,Elimina,Datos);

    }
    /////RUTINA INICIO
    private void ValidaInicio(String Favorito,Boolean Elimina,String []Datos){
        if (ValidaSeguritech()||ValidaFavorito(Datos[1])||ValidaServicios(Datos[1])) Agrega.setVisibility(View.GONE);
        if (ValidaFavoritoClick(Favorito)){
            if (Elimina) EliminaFavorito();
            else AñadeFavorito();
        }else{
            if (ValidalistadoFavorito(Datos[1])) MuestraFavoritos();
            else MuestraGeneral();

        }
    }
    /////VALIDACIONES Y FILTROS
    private boolean ValidaServicios (String Where){
        if (Where.contains("Correctivo")||Where.contains("Interno")||Where.contains("Preventivo")) return true;
        else return false;
    }
    private boolean ValidaFavoritoClick(String Favorito){
        if (Favorito == null) return false;
        else return true;
    }
    private boolean ValidalistadoFavorito(String Where){
        if (Where.contains("Favorito")) return true;
        else return false;
    }
    private Bitmap ValidaFotoenGaleriaANAM(String NombreCompleto){
        try{
            String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
            Uri uri = Uri.fromFile(new File(ExternalStorageDirectory + "ProyectoX/Credenciales/"+ruta+"/"+NombreCompleto+" "+"Frontal.png"));
            Bitmap bitmapi = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            //return bitmapi;
            return null;
        }catch (Exception e){
            return null;
        }
    }
    private Bitmap ValidaFotoenGaleria(String NombreCompleto){
        try{
            String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
            Uri uri = Uri.fromFile(new File(ExternalStorageDirectory + "ProyectoX/Credenciales/"+ruta+"/"+NombreCompleto+".png"));
            Bitmap bitmapi = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            //return bitmapi;
            return null;
        }catch (Exception e){
            return null;
        }
    }
    private Boolean ValidaFavorito(String Nombre){
        FavoritosManageDB base = new FavoritosManageDB (ListaUsuarios.this);
        int size = base.TamañoTabla(db);
        for (int i =0;i<size;i++){
            Data = base.mostrarDatos(db, i + 1);
            if (Data != null) {
                if (Data.getNombreCompleto().equals(Nombre)){
                    return true;
                }
            }
        }
        return false;
    }
    private Boolean ValidaSeguritech(){
        if (ruta.equals("Seguritech")){
            return true;
        }else{
            return false;
        }
    }
    private Boolean ValidaANAM(){
        if (ruta.equals("ANAM")){
            return true;
        }else{
            return false;
        }
    }
    ////MUESTRA DE LISTA
    private void MuestraFavoritos(){
        FavoritosManageDB base = new FavoritosManageDB (ListaUsuarios.this);
        int size = base.TamañoTabla(db);
        ArrayList<Users> ListaANAM = new ArrayList<>();
        for (int i=0; i<size;i++){
            Data = base.mostrarDatos(db, i + 1);
            if (Data != null) {
                Users users= null;
                users= new Users();
                users.setDatos(Datos);
                users.setCorreo(Data.getCorreo());
                users.setNumero(Data.getNumero());
                users.setNombreCompleto(Datos[4]);
                users.setNombreCompleto2(Data.getNombreCompleto());
                users.setPuesto(Datos[5]);
                users.setPuesto2(Data.getPuesto());
                if (ValidaANAM()) users.setCredencial(ValidaFotoenGaleriaANAM(Data.getNombreCompleto()));
                else users.setCredencial(ValidaFotoenGaleria(Datos[4]));
                users.setFavorito("Favorito");
                ListaANAM.add(users);
            }
        }
        adapterUsuarios = new AdapterUsuarios(ListaANAM);
        UsuariosView.setAdapter(adapterUsuarios);
        BuscarUsuario.setOnQueryTextListener(this);

    }
    private void MuestraGeneral() {
        refUsuarios.addListenerForSingleValueEvent(new ValueEventListener() {
            ArrayList<Users> ListaANAM = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    String Numero = postSnapshot.child("Numero").getValue(String.class);
                    String Correo = postSnapshot.child("CorreoElectronico").getValue(String.class);
                    String Puesto = postSnapshot.child("Puesto").getValue(String.class);
                    String NombreCompleto = postSnapshot.child("NombreCompleto").getValue(String.class);
                    String Cred = postSnapshot.child("Cred").getValue(String.class);
                    String Favorito;
                    if (ValidaFavorito(NombreCompleto)) Favorito = "Favorito";
                    else Favorito = "NoFavorito";
                    Users users= null;
                    users= new Users();
                    users.setDatos(Datos);
                    users.setCorreo(Correo);
                    users.setNumero(Numero);
                    users.setNoCred(Cred);
                    if (ValidaSeguritech()){
                        System.out.println("Seguritech");
                        System.out.println("NombreCompleto: "+NombreCompleto);
                        System.out.println("Cred: "+Cred);
                        users.setNombreCompleto2(Datos[4]);
                        users.setNombreCompleto(NombreCompleto);
                        users.setPuesto2(Datos[5]);
                        users.setPuesto(Puesto);
                    }else {
                        System.out.println("------------");
                        System.out.println("ANAM");
                        users.setNombreCompleto(Datos[4]);
                        users.setNombreCompleto2(NombreCompleto);
                        System.out.println("NombreCompleto: "+NombreCompleto);
                        System.out.println("Cred: "+Cred);
                        users.setPuesto(Datos[5]);
                        users.setPuesto2(Puesto);
                    }
                    if (ValidaANAM()) users.setCredencial(ValidaFotoenGaleriaANAM(NombreCompleto));
                    else users.setCredencial(ValidaFotoenGaleria(NombreCompleto));

                    users.setFavorito(Favorito);
                    ListaANAM.add(users);
                }
                adapterUsuarios = new AdapterUsuarios(ListaANAM);
                UsuariosView.setAdapter(adapterUsuarios);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
        BuscarUsuario.setOnQueryTextListener(this);
    }
    ////ACCIONES
    private void EliminaFavorito(){
        FavoritosManageDB DB= new FavoritosManageDB(this);
        if (DB.eliminarDatos(db)){
            if (Datos[1].equals("FavoritosNuctec")|| Datos[1].equals("GeneralNuctec")){
                fb.EliminaFavoritoNuctec(Datos,this);
            }else{
                fb.EliminaFavorito(Datos,this);
            }
        }
        else{
            Toast.makeText(this, "ERROR AL ELIMINAR DB", Toast.LENGTH_SHORT).show();
        }
    }
    private void AñadeFavorito() {
        FavoritosHelpeDB base = new FavoritosHelpeDB(ListaUsuarios.this,db);
        SQLiteDatabase dba = base.getWritableDatabase();
        if ( (dba != null)) {
            FavoritosManageDB DB= new FavoritosManageDB(this);
            Datos[27] = String.valueOf(DB.TamañoTabla(db));
            long id = DB.InsertaDatos(db,Datos);
            if (id > 0) {
                if (Datos[1].contains("Nuctec")){
                    fb.AñadeFavoritosNuctec(Datos,this);
                    ObtenFoto(false);
                }else{
                    fb.AñadeFavoritos(Datos,this);
                    ObtenFoto(false);
                }

            } else {

            }

        } else {

            Toast.makeText(this, "ERROR AL CREAR DB", Toast.LENGTH_SHORT).show();
        }
    }
    private void ObtenFoto(Boolean Otra){
        String Nombre;
        if (ruta.equals("ANAM")){
            if (Otra)Nombre = Datos[6]+" "+"Trasera";
            else Nombre = Datos[6]+" "+"Frontal";
        }
        else if (ruta.equals("Nuctech")) Nombre = Datos[6];
        else Nombre = Datos[4];


            StorageReference Credref = storageReference.child("Credenciales").child(ruta).child(Nombre+".jpg");
            try {
                File localFile = File.createTempFile(ruta, "jpg");
                Credref.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        if (ruta.equals("ANAM")){
                            if (!Otra){
                                GuardarenGaleria(bitmap,"ProyectoX/Credenciales/"+ruta,Nombre);
                                ObtenFoto(true);
                            }else  GuardarenGaleria(bitmap,"ProyectoX/Credenciales/"+ruta,Nombre);
                        }else GuardarenGaleria(bitmap,"ProyectoX/Credenciales/"+ruta,Nombre);


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ListaUsuarios.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException ioException) {
                Toast.makeText(ListaUsuarios.this, ioException.toString(), Toast.LENGTH_SHORT).show();
            }


    }
    private void GuardarenGaleria(Bitmap bitmap,String rutacarpeta,String nombre){
        try {
            String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
            //File directorioImagenes = new File(ExternalStorageDirectory + rutacarpeta);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(ExternalStorageDirectory + rutacarpeta+"/"+ nombre+".png"));
            Toast.makeText(ListaUsuarios.this, "Credencial guardada con éxito", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }

    }
    ////BOTONES
    public void Back(View view){
        Regresa();
    }
    public void AgregaANAM(View view){
        Intent i = new Intent(this,AgregaANAM.class);
        SharedPreferences sh = getSharedPreferences("Datos", MODE_PRIVATE);
        Datos[4] = sh.getString("Datos4", "");
        Datos[5] = sh.getString("Datos5", "");
        i.putExtra("Datos",Datos);
        startActivity(i);
    }
    //////////RUTINAS BOTONES
    private void Regresa(){
        SharedPreferences sh = getSharedPreferences("Datos", MODE_PRIVATE);
        Datos[4] = sh.getString("Datos4", "");
        Datos[5] = sh.getString("Datos5", "");
        if (Datos[1].equals("Usuarios")){
            Intent i = new Intent(this,PantallaPrincipal.class);
            i.putExtra("Datos",Datos);
            startActivity(i);

        }else if (Datos[1].equals("PreventivoANAMFavoritos")||
                Datos[1].equals("PreventivoANAMGeneral")||
                Datos[1].equals("PreventivoSeguritech")){
            Intent i = new Intent(this,PreventivoRD.class);
            i.putExtra("Datos",Datos);

            startActivity(i);
        }
        else if (Datos[1].equals("CorrectivoANAMFavoritos")||
                Datos[1].equals("CorrectivoANAMGeneral")||
                Datos[1].equals("CorrectivoSeguritech")){
            Intent i = new Intent(this,CorrectivoRD.class);
            i.putExtra("Datos",Datos);

            startActivity(i);
        }
        else if (Datos[1].equals("InternoNuctecFavoritos")||
                Datos[1].equals("InternoNuctecGeneral")||
                Datos[1].equals("InternoSeguritech")){
            Intent i = new Intent(this,InternoRD.class);
            i.putExtra("Datos",Datos);

            startActivity(i);
        }
        else if (Datos[1].equals("General")||Datos[1].equals("Favoritos")){
            Intent i = new Intent(this,PersonalANAM.class);
            i.putExtra("Datos",Datos);
            startActivity(i);
        }
        else if (Datos[1].equals("ActaentregaANAM")||Datos[1].equals("ActaentregaSeguritech")){
            Intent i = new Intent(this,ActadeEntrega.class);
            i.putExtra("Datos",Datos);
            startActivity(i);
        }
        else{
            Intent i = new Intent(this,PersonalNuctec.class);
            i.putExtra("Datos",Datos);
            startActivity(i);
        }
    }
    @Override
    public void onBackPressed() {
        Regresa();
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        adapterUsuarios.Filtrados(newText);
        return false;
    }
}