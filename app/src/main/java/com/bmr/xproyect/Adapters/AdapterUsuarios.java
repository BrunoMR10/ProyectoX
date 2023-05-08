package com.bmr.xproyect.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bmr.xproyect.ActadeEntrega;
import com.bmr.xproyect.CorrectivoRD;
import com.bmr.xproyect.EditaUsuario;
import com.bmr.xproyect.Entidades.Users;
import com.bmr.xproyect.InternoRD;
import com.bmr.xproyect.ListaUsuarios;
import com.bmr.xproyect.PreventivoRD;
import com.bmr.xproyect.R;
import com.bmr.xproyect.SelectFotoExtra;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdapterUsuarios extends RecyclerView.Adapter<AdapterUsuarios.UsuariosViewHolder>{
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReferenceFromUrl("gs://proyecto-x-933f4.appspot.com");
    ArrayList<Users> listaUsuarios;
    ArrayList<Users> listaOriginal;

    public AdapterUsuarios(ArrayList<Users>listaUsuarios){
        this.listaUsuarios= listaUsuarios;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaUsuarios);
    }


    @NonNull
    @Override
    public UsuariosViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuarios, null, false);
        return new UsuariosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull AdapterUsuarios.UsuariosViewHolder holder, int position) {
        String[] Datos;
        StorageReference Credref;
        holder.setOnClickListener();
        Datos = new String[listaUsuarios.get(position).getDatos().length];
        for (int i=0 ; i< listaUsuarios.get(position).getDatos().length;i++){
            Datos[i] =  listaUsuarios.get(position).getDatos()[i];
        }
        holder.nombre = listaUsuarios.get(position).getNombreCompleto();
        holder.puesto = listaUsuarios.get(position).getPuesto();
        holder.nombre2 = listaUsuarios.get(position).getNombreCompleto2();
        holder.puesto2 = listaUsuarios.get(position).getPuesto2();
        holder.Correo.setText(listaUsuarios.get(position).getCorreo());
        holder.Telefono.setText(listaUsuarios.get(position).getNumero());
        holder.cred = listaUsuarios.get(position).getNoCred();
        holder.Datos = Datos;
        if (Datos[1].contains("Interno")||Datos[1].contains("Correctivo")||Datos[1].contains("Preventivo")) holder.Favoritos.setVisibility(View.GONE);
        if (Datos[1].equals("Usuarios")||Datos[1].equals("CorrectivoSeguritech")||
                Datos[1].equals("InternoSeguritech")||Datos[1].equals("PreventivoSeguritech")||Datos[1].equals("ActaentregaSeguritech")){
                holder.Nombre.setText(listaUsuarios.get(position).getNombreCompleto());
                holder.Puesto.setText(listaUsuarios.get(position).getPuesto());
                holder.Edita.setVisibility(View.GONE);
                holder.Favoritos.setVisibility(View.GONE);
                Credref = storageReference.child("Credenciales").child("Seguritech").child(listaUsuarios.get(position).getNombreCompleto()+".jpg");
            ///Seguritech
        }else{
            //ANAM NUCTECH
            if (listaUsuarios.get(position).getFavorito().equals("NoFavorito")) holder.Favoritos.setChecked(false);
            else holder.Favoritos.setChecked(true);
            holder.Nombre.setText(listaUsuarios.get(position).getNombreCompleto2());
            holder.Puesto.setText(listaUsuarios.get(position).getPuesto2());
            if (Datos[1].contains("Nuctec")) Credref = storageReference.child("Credenciales").child("Nuctech").child(listaUsuarios.get(position).getNombreCompleto2()+".jpg");
            else Credref = storageReference.child("Credenciales").child("ANAM").child(listaUsuarios.get(position).getNombreCompleto2()+" "+"Frontal.jpg");
        }

        if (listaUsuarios.get(position).getCredencial()==null) {
            holder.Cargafoto.setVisibility(View.VISIBLE);
            holder.Credencial.setVisibility(View.GONE);
            try {
                File localFile;
                if (Datos[1].equals("Favoritos")||Datos[1].equals("General")||Datos[1].equals("PreventivoANAM")||Datos[1].equals("CorrectivoANAM")){
                    localFile = File.createTempFile(listaUsuarios.get(position).getNombreCompleto2()+" "+"Frontal", "jpg");
                }else{
                    localFile = File.createTempFile(listaUsuarios.get(position).getNombreCompleto(), "jpg");
                }

                Credref.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        holder.Cargafoto.setVisibility(View.GONE);
                        holder.Credencial.setVisibility(View.VISIBLE);
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        holder.Credencial.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(Usuarios.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException ioException) {
                //ioException.printStackTrace();
            }
        }
        else {
            holder.Credencial.setImageBitmap(listaUsuarios.get(position).getCredencial());
        }

    }
    public void Filtrados(String txtBuscar){
        int longitud = txtBuscar.length();
        if (longitud==0){
            listaUsuarios.clear();
            listaUsuarios.addAll(listaOriginal);

        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                List<Users> collection = listaUsuarios.stream()
                        .filter(i -> i.getNombreCompleto().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listaUsuarios.clear();
                listaUsuarios.addAll(collection);
            }else{
                for (Users c :listaOriginal){
                    if (c.getNombreCompleto().toLowerCase().contains(txtBuscar.toLowerCase())){
                        listaUsuarios.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public class UsuariosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Nombre,Correo,Telefono,Puesto;
        String nombre,puesto,nombre2,puesto2,cred;
        Switch Favoritos;
        ImageButton Edita;
        ImageView Credencial;
        String[] Datos;
        ProgressBar Cargafoto;
        public UsuariosViewHolder(@NonNull View itemView) {
            super(itemView);
            Nombre = itemView.findViewById(R.id.NameView);
            Correo = itemView.findViewById(R.id.CorreoView);
            Telefono = itemView.findViewById(R.id.NumeroView);
            Puesto = itemView.findViewById(R.id.PuestoView);
            Credencial = itemView.findViewById(R.id.CredencialView);
            Favoritos = itemView.findViewById(R.id.Favorito);
            Edita = itemView.findViewById(R.id.Edita);
            Cargafoto = itemView.findViewById(R.id.Cargafoto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                     if (Datos[1].equals("PreventivoANAMFavoritos")||
                             Datos[1].equals("PreventivoANAMGeneral"))
                             {
                                 Datos[1]="Preventivo";
                                 Datos[4] = nombre;
                                 Datos[5] = puesto;
                                 Datos[6]= nombre2;
                                 Datos[7]= puesto2;
                                 Datos[8]=Correo.getText().toString();
                                 Datos[9]=Telefono.getText().toString();
                         Context context = v.getContext();
                        Intent intent = new Intent(context, PreventivoRD.class);
                        intent.putExtra("Datos", Datos);
                        intent.putExtra("CambioCredencial","ANAM");
                        context.startActivity(intent);
                    }else if (Datos[1].equals("CorrectivoANAMFavoritos")||
                             Datos[1].equals("CorrectivoANAMGeneral"))
                     {
                         Datos[1]="Correctivo";
                         Datos[4] = nombre;
                         Datos[5] = puesto;
                         Datos[6]= nombre2;
                         Datos[7]= puesto2;
                         Datos[8]=Correo.getText().toString();
                         Datos[9]=Telefono.getText().toString();
                         Context context = v.getContext();
                         Intent intent = new Intent(context, CorrectivoRD.class);
                         intent.putExtra("Datos", Datos);
                         intent.putExtra("CambioCredencial","ANAM");
                         context.startActivity(intent);
                     }
                     else if (Datos[1].equals("InternoNuctecFavoritos")||
                             Datos[1].equals("InternoNuctecGeneral"))
                     {
                         Datos[1]="Interno";
                         Datos[4] = nombre;
                         Datos[5] = puesto;
                         Datos[6]= nombre2;
                         Datos[7]= puesto2;
                         Datos[8]=Correo.getText().toString();
                         Datos[9]=Telefono.getText().toString();
                         Context context = v.getContext();
                         Intent intent = new Intent(context, InternoRD.class);
                         intent.putExtra("Datos", Datos);
                         intent.putExtra("CambioCredencial","Nutech");
                         context.startActivity(intent);
                     }
                     else if(Datos[1].equals("PreventivoSeguritech")) {
                         Datos[1]="Preventivo";
                         Datos[4] = nombre;
                         Datos[5] = puesto;
                         Datos[6]= nombre2;
                         Datos[7]= puesto2;
                         Datos[8]=Correo.getText().toString();
                         Datos[9]=Telefono.getText().toString();
                         Context context = v.getContext();
                         Intent intent = new Intent(context, PreventivoRD.class);
                         intent.putExtra("Datos", Datos);
                         intent.putExtra("CambioCredencial","Seguritech");
                         context.startActivity(intent);
                    }
                     else if(Datos[1].equals("CorrectivoSeguritech")) {
                         Datos[1]="Correctivo";
                         Datos[4] = nombre;
                         Datos[5] = puesto;
                         Datos[6]= nombre2;
                         Datos[7]= puesto2;
                         Datos[8]=Correo.getText().toString();
                         Datos[9]=Telefono.getText().toString();
                         Context context = v.getContext();
                         Intent intent = new Intent(context, CorrectivoRD.class);
                         intent.putExtra("Datos", Datos);
                         intent.putExtra("CambioCredencial","Seguritech");
                         context.startActivity(intent);
                     }
                     else if(Datos[1].equals("InternoSeguritech")) {
                         Datos[1]="Interno";
                         Datos[4] = nombre;
                         Datos[5] = puesto;
                         Datos[6]= nombre2;
                         Datos[7]= puesto2;
                         Datos[8]=Correo.getText().toString();
                         Datos[9]=Telefono.getText().toString();
                         Context context = v.getContext();
                         Intent intent = new Intent(context, InternoRD.class);
                         intent.putExtra("Datos", Datos);
                         intent.putExtra("CambioCredencial","Seguritech");
                         context.startActivity(intent);
                     }
                     else if(Datos[1].equals("ActaentregaSeguritech")) {
                         Datos[1]="ActaEntrega";
                         Datos[4] = nombre;
                         Datos[5] = puesto;
                         Datos[6]= nombre2;
                         Datos[7]= puesto2;
                         Datos[8]=Correo.getText().toString();
                         Datos[9]=Telefono.getText().toString();
                         Context context = v.getContext();
                         Intent intent = new Intent(context, ActadeEntrega.class);
                         intent.putExtra("Datos", Datos);
                         intent.putExtra("CambioCredencial","Seguritech");
                         intent.putExtra("CredSeguritech",cred);
                         context.startActivity(intent);
                     }
                     else if(Datos[1].equals("ActaentregaANAM")) {
                         Datos[1]="ActaEntrega";
                         Datos[4] = nombre;
                         Datos[5] = puesto;
                         Datos[6]= nombre2;
                         Datos[7]= puesto2;
                         Datos[8]=Correo.getText().toString();
                         Datos[9]=Telefono.getText().toString();
                         Context context = v.getContext();
                         Intent intent = new Intent(context, ActadeEntrega.class);
                         intent.putExtra("Datos", Datos);
                         intent.putExtra("CambioCredencial","ANAM");
                         intent.putExtra("CredANAM",cred);
                         context.startActivity(intent);
                     }
                    else{

                    }

                }

            });

        }

        void setOnClickListener(){
            Favoritos.setOnClickListener(this);
            Edita.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.Edita:
                    Datos[4] = nombre;
                    Datos[5] = puesto;
                    Datos[6]= nombre2;
                    Datos[7]= puesto2;
                    Datos[8]=Correo.getText().toString();
                    Datos[9]=Telefono.getText().toString();
                    Context context = v.getContext();
                    Intent intent = new Intent(context, EditaUsuario.class);
                    intent.putExtra("Datos",Datos);
                    intent.putExtra("Cred",cred);
                    context.startActivity(intent);
                    break;
                case R.id.Favorito:
                    Datos[4] = nombre;
                    Datos[5] = puesto;
                    Datos[6]= nombre2;
                    Datos[7]= puesto2;
                    Datos[8]=Correo.getText().toString();
                    Datos[9]=Telefono.getText().toString();
                    Boolean estado;
                    if (Favoritos.isChecked()){
                        estado = false;
                    }else{
                        estado = true;
                    }
                    context = v.getContext();
                    intent = new Intent(context, ListaUsuarios.class);
                    intent.putExtra("Datos",Datos);
                    intent.putExtra("Favoritos","");
                    intent.putExtra("Elimina",estado);
                    context.startActivity(intent);
                    break;

            }
        }
    }


    }

