package com.bmr.xproyect.Herramientas;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bmr.xproyect.Aduanas;
import com.bmr.xproyect.DataBases.FavoritosHelpeDB;
import com.bmr.xproyect.DataBases.FavoritosManageDB;
import com.bmr.xproyect.DataBases.TicketHelperDB;
import com.bmr.xproyect.DataBases.TicketManageDB;
import com.bmr.xproyect.Datos.Datos;
import com.bmr.xproyect.ListaUsuarios;
import com.bmr.xproyect.MainActivity;
import com.bmr.xproyect.PantallaPrincipal;
import com.bmr.xproyect.Ticketslist;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Firebase {
    Datos dt = new Datos();
    //Storage
    public FirebaseStorage storage = FirebaseStorage.getInstance();
    public StorageReference storageReference = storage.getReferenceFromUrl("gs://proyecto-x-933f4.appspot.com");
    //RealtimeDatabase
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference refANAM = database.getReference("ANAM");
    public DatabaseReference refNuctech = database.getReference("Nuctech");
    public DatabaseReference refTickets = database.getReference("Tickets");
    public DatabaseReference refUsuarios = database.getReference("Usuarios");
    public DatabaseReference refAduanas = database.getReference("Aduanas");
    public DatabaseReference refUID;
    //Auth
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    //// Ingresa
    public void Ingresa(Context context, String CorreoElectronico, String Contraseña, Activity activity) {
        mAuth.signInWithEmailAndPassword(CorreoElectronico, Contraseña)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            DatosUsuario(dt.Datos, context);
                        } else {
                            Toast.makeText(context, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }

                    }

                });

    }

    //// RegistraUsuario
    public void SubeCred(ImageView Foto, ImageView Foto2, String[] DatosUsuario, String Iden, Context context, String[] Datos) {
        StorageReference Ticketsref, NombreUs;
        Foto.setDrawingCacheEnabled(true);
        Foto.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) Foto.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        if (Iden.equals("Seguritech")) {

            if (Datos[1].equals("PantallaPrincipal")){
                Ticketsref = storageReference.child("Credenciales").child(Iden);
                NombreUs = Ticketsref.child(DatosUsuario[6] + ".jpg");
            }
            else{
                Ticketsref = storageReference.child("Credenciales").child(Iden);
                NombreUs = Ticketsref.child(DatosUsuario[0] + ".jpg");
            }

        } else if (Iden.equals("Nuctech")) {
            Ticketsref = storageReference.child("Credenciales").child(Iden);
            NombreUs = Ticketsref.child(DatosUsuario[6] + ".jpg");
        }else {
            Ticketsref = storageReference.child("Credenciales").child("ANAM");
            NombreUs = Ticketsref.child(DatosUsuario[6] + " " + Iden + ".jpg");
        }

        UploadTask uploadTask = NombreUs.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Foto.getContext(), "Fail", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if (Iden.equals("Seguritech")) {
                    if (Datos[1].equals("PantallaPrincipal")){
                        FirebaseUser user = mAuth.getCurrentUser();
                        RegistraUsuarioDatabase2(DatosUsuario, user.getUid(), context);
                    }
                    else{
                        RegistraUsuarioAuth(DatosUsuario, context);
                    }

                } else if (Iden.equals("Nuctech")){
                    RegistraNuctechDatabase(DatosUsuario, context, Datos);
                }else if (Iden.equals("Frontal")) {
                    SubeCred(Foto2, Foto, DatosUsuario, "Trasera", context, Datos);
                } else {
                    RegistraANAMDatabase(DatosUsuario, context, Datos);
                }
                //Toast.makeText(Foto.getContext(), "Credencial subida con éxtio", Toast.LENGTH_SHORT).show();
            }
        })
        ;
    }

    //// RegistraUsuario
    public void SubeCred2(ImageView Foto, ImageView Foto2,ImageView Foto3, String[] DatosUsuario, String Iden, Context context, String[] Datos) {
        StorageReference Ticketsref, NombreUs;
        Foto.setDrawingCacheEnabled(true);
        Foto.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) Foto.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();



            if (Iden.equals("Seguritech")){
                Ticketsref = storageReference.child("Credenciales").child("Seguritech");
                NombreUs = Ticketsref.child(DatosUsuario[6] + ".jpg");
            }
            else{
                Ticketsref = storageReference.child("Credenciales").child("Seguritech");
                NombreUs = Ticketsref.child(DatosUsuario[6] + " " + Iden + ".jpg");
            }

        UploadTask uploadTask = NombreUs.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Foto.getContext(), "Fail", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        if (Iden.equals("Seguritech")){
                            SubeCred2(Foto2, Foto,Foto3, DatosUsuario, "INEFrontal", context, Datos);
                        }
                        else if (Iden.equals("INEFrontal")){
                            SubeCred2(Foto3, Foto,Foto3, DatosUsuario, "INETrasera", context, Datos);
                        }
                        else{
                            FirebaseUser user = mAuth.getCurrentUser();
                            RegistraUsuarioDatabase2(DatosUsuario, user.getUid(), context);
                        }

                    }
                //Toast.makeText(Foto.getContext(), "Credencial subida con éxtio", Toast.LENGTH_SHORT).show()
        })
        ;
    }
    public void RegistraUsuarioAuth(String[] DatosUsuario, Context context) {
        mAuth.createUserWithEmailAndPassword(DatosUsuario[4], DatosUsuario[8])
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(DatosUsuario[1]).build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User profile updated.");
                                            }
                                        }
                                    });
                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "Email sent.");
                                                Toast.makeText(context, "Te hemos mandado un correo de verificacion, verifica tu bandeja de entrada, si no aparece verifica en spam", Toast.LENGTH_SHORT).show();
                                                RegistraUsuarioDatabase(DatosUsuario, user.getUid(), context);
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(context, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    public void RegistraUsuarioDatabase(String[] DatosUsuario, String Database, Context context) {
        Map<String, Object> UsuarioNuevo = new HashMap<>();
        UsuarioNuevo.put("NombreCompleto", DatosUsuario[0]);
        UsuarioNuevo.put("ApellidoPaterno", DatosUsuario[1]);
        UsuarioNuevo.put("ApellidoMaterno", DatosUsuario[2]);
        UsuarioNuevo.put("Nombre", DatosUsuario[3]);
        UsuarioNuevo.put("CorreoElectronico", DatosUsuario[4]);
        UsuarioNuevo.put("Numero", DatosUsuario[5]);
        UsuarioNuevo.put("Puesto", DatosUsuario[6]);
        //UsuarioNuevo.put("Registro",Datos[0]);
        refUsuarios.child(Database).updateChildren(UsuarioNuevo, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null && ref != null) {
                    Intent i = new Intent(context, MainActivity.class);
                    context.startActivity(i);
                    // there was no error and the value is modified
                } else {
                    Toast.makeText(context, "Error al registrarUsuario", Toast.LENGTH_SHORT).show();
                    // there was an error. try to update again
                }
            }
        });


    }
    public void RegistraUsuarioDatabase2(String[] DatosUsuario, String Database, Context context) {
        Map<String, Object> UsuarioNuevo = new HashMap<>();
        UsuarioNuevo.put("CorreoElectronico", DatosUsuario[0]);
        UsuarioNuevo.put("Numero", DatosUsuario[1]);
        UsuarioNuevo.put("Nombre", DatosUsuario[2]);
        UsuarioNuevo.put("ApellidoPaterno", DatosUsuario[3]);
        UsuarioNuevo.put("ApellidoMaterno", DatosUsuario[4]);
        UsuarioNuevo.put("Puesto", DatosUsuario[5]);
        UsuarioNuevo.put("NombreCompleto", DatosUsuario[6]);
        UsuarioNuevo.put("Cred", DatosUsuario[7]);
        //UsuarioNuevo.put("Registro",Datos[0]);
        refUsuarios.child(Database).updateChildren(UsuarioNuevo, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null && ref != null) {
                    Intent i = new Intent(context, MainActivity.class);
                    context.startActivity(i);
                    // there was no error and the value is modified
                } else {
                    Toast.makeText(context, "Error al registrarUsuario", Toast.LENGTH_SHORT).show();
                    // there was an error. try to update again
                }
            }
        });


    }
    public void RegistraANAMDatabase(String[] DatosUsuario, Context context, String[] Datos) {
        Map<String, Object> UsuarioNuevo = new HashMap<>();
        UsuarioNuevo.put("CorreoElectronico", DatosUsuario[0]);
        UsuarioNuevo.put("Numero", DatosUsuario[1]);
        UsuarioNuevo.put("Nombre", DatosUsuario[2]);
        UsuarioNuevo.put("ApellidoPaterno", DatosUsuario[3]);
        UsuarioNuevo.put("ApellidoMaterno", DatosUsuario[4]);
        UsuarioNuevo.put("Puesto", DatosUsuario[5]);
        UsuarioNuevo.put("NombreCompleto", DatosUsuario[6]);
        UsuarioNuevo.put("Cred", DatosUsuario[7]);
        UsuarioNuevo.put("Favorito", "NoFavorito");
        refANAM.child(DatosUsuario[6]).updateChildren(UsuarioNuevo, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null && ref != null) {
                    Intent i = new Intent(context, ListaUsuarios.class);
                    i.putExtra("Datos", Datos);
                    context.startActivity(i);
                    // there was no error and the value is modified
                } else {
                    Toast.makeText(context, "Error al registrar ANAM", Toast.LENGTH_SHORT).show();
                    // there was an error. try to update again
                }
            }
        });


    }
    public void RegistraNuctechDatabase(String[] DatosUsuario, Context context, String[] Datos) {
        Map<String, Object> UsuarioNuevo = new HashMap<>();
        UsuarioNuevo.put("CorreoElectronico", DatosUsuario[0]);
        UsuarioNuevo.put("Numero", DatosUsuario[1]);
        UsuarioNuevo.put("Nombre", DatosUsuario[2]);
        UsuarioNuevo.put("ApellidoPaterno", DatosUsuario[3]);
        UsuarioNuevo.put("ApellidoMaterno", DatosUsuario[4]);
        UsuarioNuevo.put("Puesto", DatosUsuario[5]);
        UsuarioNuevo.put("NombreCompleto", DatosUsuario[6]);
        UsuarioNuevo.put("Favorito", "NoFavorito");
        refNuctech.child(DatosUsuario[6]).updateChildren(UsuarioNuevo, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null && ref != null) {
                    Intent i = new Intent(context, ListaUsuarios.class);
                    i.putExtra("Datos", Datos);
                    context.startActivity(i);
                    // there was no error and the value is modified
                } else {
                    Toast.makeText(context, "Error al registrar ANAM", Toast.LENGTH_SHORT).show();
                    // there was an error. try to update again
                }
            }
        });


    }

    ///PantallaPrincipal
    public void CerrarSesion(Context context) {
        mAuth.signOut();
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
    }
    public void DatosUsuario(String[] Datos, Context context) {

        FirebaseUser user = mAuth.getCurrentUser();
        refUsuarios.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    String UID = user.getUid();
                    String Nombre = dataSnapshot.child("NombreCompleto").getValue(String.class);
                    String Puesto = dataSnapshot.child("Puesto").getValue(String.class);
                    String CorreoElectronico = dataSnapshot.child("CorreoElectronico").getValue(String.class);
                    String Numero = dataSnapshot.child("Numero").getValue(String.class);
                    String Cred = dataSnapshot.child("Cred").getValue(String.class);
                    Datos[3] = UID;
                    Datos[4] = Nombre;
                    Datos[5] = Puesto;
                    Datos[8] = CorreoElectronico;
                    Datos[9] = Numero;

                    Intent i = new Intent(context, PantallaPrincipal.class);
                    i.putExtra("Datos", Datos);
                    i.putExtra("Cred",Cred);
                    context.startActivity(i);
                    //Toast.makeText(context,Datos[4]+" "+Datos[5]+" "+Datos[8]+" "+Datos[9],Toast.LENGTH_SHORT).show();

                } else {

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }
    ///Tickets
    public void RegistraTicket(String[] Datos, Context context) {

        Map<String, Object> UsuarioNuevo = new HashMap<>();
        UsuarioNuevo.put("Nomenclatura", Datos[0]);
        UsuarioNuevo.put("Ingeniero", Datos[4]);
        UsuarioNuevo.put("Puesto", Datos[5]);
        UsuarioNuevo.put("Trimestre", Datos[10]);
        UsuarioNuevo.put("Periodicidad", Datos[11]);
        UsuarioNuevo.put("Ticket", Datos[12]);
        UsuarioNuevo.put("Aduana", Datos[13]);
        UsuarioNuevo.put("Equipo", Datos[14]);
        UsuarioNuevo.put("Ubicacion", Datos[15]);
        UsuarioNuevo.put("Serie", Datos[16]);
        UsuarioNuevo.put("FechaInicio", Datos[17]);
        UsuarioNuevo.put("HoraInicio", Datos[18]);
        UsuarioNuevo.put("FechaFin", Datos[17]);
        UsuarioNuevo.put("HoraFin", Datos[20]);
        UsuarioNuevo.put("Falla", Datos[21]);
        UsuarioNuevo.put("Fecha llamada", "");
        UsuarioNuevo.put("Hora de llamada", "");
        UsuarioNuevo.put("Fecha Sitio", "");
        UsuarioNuevo.put("Hora Sitio", "");
        UsuarioNuevo.put("Nombre tecnico", "");

        //UsuarioNuevo.put("Registro",Datos[0]);
        refTickets.child("Preventivo").child(Datos[12]).updateChildren(UsuarioNuevo, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null && ref != null) {

                    FirebaseUser user = mAuth.getCurrentUser();
                    refUsuarios.child(user.getUid());
                    Map<String, Object> User = new HashMap<>();
                    User.put(Datos[12], Datos[1]);
                    refUsuarios.child(user.getUid()).child("Tickets").updateChildren(User);

                    Toast.makeText(context, "RegistroCorrectoenFirebase", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, Ticketslist.class);
                    i.putExtra("Datos", Datos);
                    context.startActivity(i);
                    // there was no error and the value is modified

                } else {
                    Toast.makeText(context, "Error al Registrar", Toast.LENGTH_SHORT).show();
                    // there was an error. try to update again
                }
            }
        });


    }
    public void RegistraTicketCorrectivo(String[] Datos, Context context) {
        Map<String, Object> UsuarioNuevo = new HashMap<>();

        UsuarioNuevo.put("Nomenclatura", Datos[0]);
        UsuarioNuevo.put("Ingeniero", Datos[4]);
        UsuarioNuevo.put("Puesto", Datos[5]);
        UsuarioNuevo.put("Trimestre", "");
        UsuarioNuevo.put("Periodicidad", "");
        UsuarioNuevo.put("Ticket", Datos[12]);
        UsuarioNuevo.put("Aduana", Datos[13]);
        UsuarioNuevo.put("Equipo", Datos[14]);
        UsuarioNuevo.put("Ubicacion", Datos[15]);
        UsuarioNuevo.put("Serie", Datos[16]);
        UsuarioNuevo.put("FechaInicio", Datos[17]);
        UsuarioNuevo.put("HoraInicio", Datos[18]);
        UsuarioNuevo.put("FechaFin", Datos[19]);
        UsuarioNuevo.put("HoraFin", Datos[20]);
        UsuarioNuevo.put("Falla", Datos[21]);
        UsuarioNuevo.put("Fecha llamada", Datos[22]);
        UsuarioNuevo.put("Hora de llamada", Datos[23]);
        UsuarioNuevo.put("Fecha Sitio", Datos[24]);
        UsuarioNuevo.put("Hora Sitio", Datos[25]);
        UsuarioNuevo.put("Nombre tecnico", Datos[26]);
        //UsuarioNuevo.put("Registro",Datos[0]);
        refTickets.child("Correctivo").child(Datos[12]).updateChildren(UsuarioNuevo, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null && ref != null) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    refUsuarios.child(user.getUid());
                    Map<String, Object> User = new HashMap<>();
                    User.put(Datos[12], Datos[1]);
                    refUsuarios.child(user.getUid()).child("Tickets").updateChildren(User);

                    Toast.makeText(context, "RegistroCorrectoenFirebase", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, Ticketslist.class);
                    i.putExtra("Datos", Datos);
                    context.startActivity(i);
                    // there was no error and the value is modified

                } else {
                    Toast.makeText(context, "Error al Registrar", Toast.LENGTH_SHORT).show();
                    // there was an error. try to update again
                }
            }
        });


    }
    public void RegistraTicketInterno(String[] Datos, Context context) {
        Map<String, Object> UsuarioNuevo = new HashMap<>();
        UsuarioNuevo.put("Nomenclatura", Datos[0]);
        UsuarioNuevo.put("Ingeniero", Datos[4]);
        UsuarioNuevo.put("Puesto", Datos[5]);
        UsuarioNuevo.put("Trimestre", "");
        UsuarioNuevo.put("Periodicidad", "");
        UsuarioNuevo.put("Ticket", Datos[12]);
        UsuarioNuevo.put("Aduana", Datos[13]);
        UsuarioNuevo.put("Equipo", Datos[14]);
        UsuarioNuevo.put("Ubicacion", Datos[15]);
        UsuarioNuevo.put("Serie", Datos[16]);
        UsuarioNuevo.put("FechaInicio", Datos[17]);
        UsuarioNuevo.put("HoraInicio", Datos[18]);
        UsuarioNuevo.put("FechaFin", Datos[17]);
        UsuarioNuevo.put("HoraFin", Datos[20]);
        UsuarioNuevo.put("Falla", Datos[21]);
        UsuarioNuevo.put("Fecha llamada", "");
        UsuarioNuevo.put("Hora de llamada","");
        UsuarioNuevo.put("Fecha Sitio", Datos[24]);
        UsuarioNuevo.put("Hora Sitio", Datos[25]);
        UsuarioNuevo.put("Nombre tecnico", "");
        //UsuarioNuevo.put("Registro",Datos[0]);
        refTickets.child("Interno").child(Datos[12]).updateChildren(UsuarioNuevo, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                FirebaseUser user = mAuth.getCurrentUser();
                refUsuarios.child(user.getUid());
                Map<String, Object> User = new HashMap<>();
                User.put(Datos[12], Datos[1]);
                refUsuarios.child(user.getUid()).child("Tickets").updateChildren(User);

                if (error == null && ref != null) {
                    Toast.makeText(context, "RegistroCorrectoenFirebase", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, Ticketslist.class);
                    i.putExtra("Datos", Datos);
                    context.startActivity(i);
                    // there was no error and the value is modified

                } else {
                    Intent i = new Intent(context, Ticketslist.class);
                    i.putExtra("Datos", Datos);
                    context.startActivity(i);
                    Toast.makeText(context, "Error al Registrar", Toast.LENGTH_SHORT).show();
                    // there was an error. try to update again
                }
            }
        });


    }
    public void EliminaTicket(String DBiden, String[] Datos, Context context,String DBIden) {
        refTickets.child(DBiden).child(Datos[12]).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null && ref != null) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    refUsuarios.child(user.getUid()).child("Tickets").child(Datos[12]).removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            ObtenListaTickets(Datos, context,DBIden);
                        }
                    });
                    //Toast.makeText(context, "Ticket Eliminado Correctamente", Toast.LENGTH_SHORT).show();
                    /*Intent i = new Intent(context, Ticketslist.class);
                    i.putExtra("Datos", Datos);
                    context.startActivity(i);*/

                    // there was no error and the value is modified


                } else {
                    Toast.makeText(context, "Error al Elimar", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, Ticketslist.class);
                    i.putExtra("Datos", Datos);
                    context.startActivity(i);// there was an error. try to update again
                }
            }
        });


    }
    public void ObtenListaTickets(String[] Datos, Context context,String DBIden) {
        FirebaseUser user = mAuth.getCurrentUser();
        refUID = refUsuarios.child(user.getUid());
        refUID.child("Tickets").addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String [] Tickets = new String[(int) snapshot.getChildrenCount()];
                int i = 0;
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    if (postSnapshot.getValue().toString().equals(Datos[1])){
                        Tickets[i] = postSnapshot.getKey();
                        i++;
                    }else{

                    }


                }
                ActualizalistaTickets(Datos,Tickets,context,0,DBIden);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Intent intent = new Intent(context, Ticketslist.class);
                intent.putExtra("Datos", Datos);
                //intent.putExtra("Tickets", Tickets);
                context.startActivity(intent);
            }
        });
    }
    public void ActualizalistaTickets(String[] Datos,String[] Tickets,Context context,int id,String DBIden) {
        refTickets.child(Datos[1]).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (Tickets[id] == null) {
                        Intent intent = new Intent(context, Ticketslist.class);
                        intent.putExtra("Datos", Datos);
                        context.startActivity(intent);
                    }
                    else{
                        refTickets.child(Datos[1]).child(Tickets[id]).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    Datos[0] = snapshot.child("Nomenclatura").getValue(String.class);
                                    Datos[4] = snapshot.child("Ingeniero").getValue(String.class);
                                    Datos[5] = snapshot.child("Puesto").getValue(String.class);
                                    Datos[10] = snapshot.child("Trimestre").getValue(String.class);
                                    Datos[11] = snapshot.child("Periodicidad").getValue(String.class);
                                    Datos[12] = Tickets[id];
                                    Datos[13] = snapshot.child("Aduana").getValue(String.class);
                                    Datos[14] = snapshot.child("Equipo").getValue(String.class);
                                    Datos[15] = snapshot.child("Ubicacion").getValue(String.class);
                                    Datos[16] = snapshot.child("Serie").getValue(String.class);
                                    Datos[17] = snapshot.child("FechaInicio").getValue(String.class);
                                    Datos[18] = snapshot.child("HoraInicio").getValue(String.class);
                                    Datos[19] = snapshot.child("FechaFin").getValue(String.class);
                                    Datos[20] = snapshot.child("HoraFin").getValue(String.class);
                                    Datos[21] = snapshot.child("Falla").getValue(String.class);
                                    Datos[22] = snapshot.child("Fecha llamada").getValue(String.class);
                                    Datos[23] = snapshot.child("Hora de llamada").getValue(String.class);

                                    Datos[24] = snapshot.child("Fecha Sitio").getValue(String.class);
                                    Datos[25] = snapshot.child("Hora Sitio").getValue(String.class);
                                    Datos[26] = snapshot.child("Nombre tecnico").getValue(String.class);
                                    Datos[27] = String.valueOf(id + 1);
                                    TicketHelperDB dbHelper = new TicketHelperDB(context, "Tickets" + DBIden);
                                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                                    if ((db != null)) {
                                        TicketManageDB DB = new TicketManageDB(context, "Tickets" + DBIden);
                                        long id2 = DB.InsertaDatos(Datos, "Tickets" + DBIden);
                                        if (id2 > 0) {
                                            //Toast.makeText(context, "REGISTRO CORRECTO", Toast.LENGTH_SHORT).show();
                                            if (id == Tickets.length - 1) {
                                                Intent intent = new Intent(context, Ticketslist.class);
                                                intent.putExtra("Datos", Datos);
                                                context.startActivity(intent);
                                            } else {
                                                ActualizalistaTickets(Datos, Tickets, context, id + 1, DBIden);
                                            }
                                        } else {
                                            Toast.makeText(context, "ERROR EN REGISTRO", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(context, "NO EXISTE DB", Toast.LENGTH_SHORT).show();
                                    }


                                } else {
                                    Toast.makeText(context, "NO EXISTE SNAPSHOT 2", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(context, Ticketslist.class);
                                    intent.putExtra("Datos", Datos);
                                    //intent.putExtra("Tickets", Tickets);
                                    context.startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(context, "ERROR 2", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, Ticketslist.class);
                                intent.putExtra("Datos", Datos);
                                //intent.putExtra("Tickets", Tickets);
                                context.startActivity(intent);
                            }
                        });

                    }}else{
                        Toast.makeText(context, "NO EXISTE SNAPSHOT 1", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, Ticketslist.class);
                        intent.putExtra("Datos", Datos);
                        //intent.putExtra("Tickets", Tickets);
                        context.startActivity(intent);
                    }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "ERROR 1", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, Ticketslist.class);
                intent.putExtra("Datos", Datos);
                //intent.putExtra("Tickets", Tickets);
                context.startActivity(intent);
            }
        });

    }
    public void AñadeFavoritos(String[] Datos, Context context) {
        refUID = refUsuarios.child(mAuth.getCurrentUser().getUid());
        Map<String, Object> NuevoFavorito = new HashMap<>();
        NuevoFavorito.put("NombreCompleto", Datos[6]);
        NuevoFavorito.put("CorreoElectronico", Datos[8]);
        NuevoFavorito.put("Numero", Datos[9]);
        NuevoFavorito.put("Puesto", Datos[7]);
        refUID.child("Favoritos").child(Datos[6]).updateChildren(NuevoFavorito, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null && ref != null) {

                    Datos[1]="Favoritos";
                    Toast.makeText(context, "RegistroCorrectoenFirebase", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, ListaUsuarios.class);
                    intent.putExtra("Datos", Datos);
                    context.startActivity(intent);
                    // there was no error and the value is modified

                } else {
                    Toast.makeText(context, "Error al Registrar", Toast.LENGTH_SHORT).show();
                    // there was an error. try to update again
                }
            }
        });

    }
    public void AñadeFavoritosNuctec(String[] Datos, Context context) {
        refUID = refUsuarios.child(mAuth.getCurrentUser().getUid());
        Map<String, Object> NuevoFavorito = new HashMap<>();
        NuevoFavorito.put("NombreCompleto", Datos[6]);
        NuevoFavorito.put("CorreoElectronico", Datos[8]);
        NuevoFavorito.put("Numero", Datos[9]);
        NuevoFavorito.put("Puesto", Datos[7]);
        refUID.child("FavoritosNuctech").child(Datos[6]).updateChildren(NuevoFavorito, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null && ref != null) {

                    Datos[1]="FavoritosNuctec";
                    Toast.makeText(context, "RegistroCorrectoenFirebase", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, ListaUsuarios.class);
                    intent.putExtra("Datos", Datos);
                    context.startActivity(intent);
                    // there was no error and the value is modified

                } else {
                    Toast.makeText(context, "Error al Registrar", Toast.LENGTH_SHORT).show();
                    // there was an error. try to update again
                }
            }
        });

    }
    public void EliminaFavorito(String[] Datos, Context context) {
        refUID = refUsuarios.child(Datos[3]);
        Map<String, Object> NuevoFavorito = new HashMap<>();
        NuevoFavorito.put("NombreCompleto", Datos[6]);
        NuevoFavorito.put("CorreoElectronico", Datos[8]);
        NuevoFavorito.put("Numero", Datos[9]);
        NuevoFavorito.put("Puesto", Datos[7]);
        refUID.child("Favoritos").child(Datos[6]).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null && ref != null) {
                    Datos[1]="Favoritos";
                    ActualizaListaFavoritos(Datos,context);
                    // there was no error and the value is modified

                } else {
                    Toast.makeText(context, "Error al Registrar", Toast.LENGTH_SHORT).show();
                    // there was an error. try to update again
                }
            }
        });

    }
    public void EliminaFavoritoNuctec(String[] Datos, Context context) {
        refUID = refUsuarios.child(Datos[3]);
        Map<String, Object> NuevoFavorito = new HashMap<>();
        NuevoFavorito.put("NombreCompleto", Datos[6]);
        NuevoFavorito.put("CorreoElectronico", Datos[8]);
        NuevoFavorito.put("Numero", Datos[9]);
        NuevoFavorito.put("Puesto", Datos[7]);
        refUID.child("FavoritosNuctech").child(Datos[6]).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null && ref != null) {
                    Datos[1]="FavoritosNuctec";
                    ActualizaListaFavoritosNuctec(Datos,context);
                    // there was no error and the value is modified

                } else {
                    Toast.makeText(context, "Error al Registrar", Toast.LENGTH_SHORT).show();
                    // there was an error. try to update again
                }
            }
        });

    }
    public void ActualizaListaFavoritos(String[] Datos, Context context) {
        FavoritosHelpeDB base = new FavoritosHelpeDB(context, "Favoritos");
        SQLiteDatabase db = base.getWritableDatabase();
        if ((db != null)) {
            FavoritosManageDB DB = new FavoritosManageDB(context);
            refUID.child("Favoritos").addListenerForSingleValueEvent(new ValueEventListener() {

                @RequiresApi(api = Build.VERSION_CODES.O)
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        String CorreoElectronico = postSnapshot.child("CorreoElectronico").getValue(String.class);
                        String Nombre = postSnapshot.child("NombreCompleto").getValue(String.class);
                        String Numero = postSnapshot.child("Numero").getValue(String.class);
                        String Puesto = postSnapshot.child("Puesto").getValue(String.class);
                        Datos[8] = CorreoElectronico;
                        Datos[9] = Numero;
                        Datos[6] = Nombre;
                        Datos[7] = Puesto;
                        Datos[1]="Favoritos";
                        long id = DB.InsertaDatos("Favoritos", Datos);
                        if (id > 0) {
                        } else {
                            Toast.makeText(context, "ERROR EN REGISTRO", Toast.LENGTH_SHORT).show();
                        }
                    }

                    Toast.makeText(context, "RegistroCorrectoenFirebase", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, ListaUsuarios.class);
                    intent.putExtra("Datos", Datos);
                    context.startActivity(intent);
                }



                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(context, "Error en Firebase", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, ListaUsuarios.class);
                    intent.putExtra("Datos", Datos);
                    context.startActivity(intent);
                }
            });
        }



        }
    public void ActualizaListaFavoritosNuctec(String[] Datos, Context context) {
        FavoritosHelpeDB base = new FavoritosHelpeDB(context, "Favoritos");
        SQLiteDatabase db = base.getWritableDatabase();
        if ((db != null)) {
            FavoritosManageDB DB = new FavoritosManageDB(context);
            refUID.child("FavoritosNuctech").addListenerForSingleValueEvent(new ValueEventListener() {

                @RequiresApi(api = Build.VERSION_CODES.O)
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        String CorreoElectronico = postSnapshot.child("CorreoElectronico").getValue(String.class);
                        String Nombre = postSnapshot.child("NombreCompleto").getValue(String.class);
                        String Numero = postSnapshot.child("Numero").getValue(String.class);
                        String Puesto = postSnapshot.child("Puesto").getValue(String.class);
                        Datos[8] = CorreoElectronico;
                        Datos[9] = Numero;
                        Datos[6] = Nombre;
                        Datos[7] = Puesto;
                        Datos[1]="Favoritos";
                        long id = DB.InsertaDatos("Favoritos", Datos);
                        if (id > 0) {
                        } else {
                            Toast.makeText(context, "ERROR EN REGISTRO", Toast.LENGTH_SHORT).show();
                        }
                    }
                    Toast.makeText(context, "RegistroCorrectoenFirebase", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, ListaUsuarios.class);
                    intent.putExtra("Datos", Datos);
                    context.startActivity(intent);
                }



                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(context, "Error en Firebase", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, ListaUsuarios.class);
                    intent.putExtra("Datos", Datos);
                    context.startActivity(intent);
                }
            });
        }



    }
    public void NombreUsuarios(String Where,String Who) {
        refUsuarios.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String [] Nombres = new String[(int) snapshot.getChildrenCount()];
                int x = 0;
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    System.out.println(postSnapshot.getKey());
                    Map<String, Object> Actualiza = new HashMap<>();
                    Actualiza.put(Who, true);
                    //UsuarioNuevo.put("Registro",Datos[0]);
                    refUsuarios.child(postSnapshot.getKey()).child("Actualizaciones").child(Where).updateChildren(Actualiza);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    public void ValidaActualización(String Where,String Who){

    }
    public void RegistraAduanas(String[]Aduana,Context context){
            Map<String, Object> UsuarioNuevo = new HashMap<>();
            UsuarioNuevo.put("Aduana", Aduana[0]);
            UsuarioNuevo.put("Entidad", Aduana[1]);
            UsuarioNuevo.put("Municipio", Aduana[2]);
            UsuarioNuevo.put("Calle", Aduana[3]);
            UsuarioNuevo.put("Colonia", Aduana[4]);
            UsuarioNuevo.put("CP", Aduana[5]);
            //UsuarioNuevo.put("Registro",Datos[0]);
            refAduanas.child(Aduana[0]).updateChildren(UsuarioNuevo, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    if (error == null && ref != null) {
                        // there was no error and the value is modified
                        Toast.makeText(context,"Aduana registrada con éxito",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(context, Aduanas.class);
                        i.putExtra("Where","Registra");
                        context.startActivity(i);
                    } else {
                        Toast.makeText(context,"Error al registrar la Aduana",Toast.LENGTH_SHORT).show();
                        // there was an error. try to update again
                    }
                }
            });

        }


}
