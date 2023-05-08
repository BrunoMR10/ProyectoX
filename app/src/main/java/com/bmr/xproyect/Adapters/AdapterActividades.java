package com.bmr.xproyect.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bmr.xproyect.EditaTexto;
import com.bmr.xproyect.Entidades.ComentariosRDP;
import com.bmr.xproyect.Entidades.Fotos;
import com.bmr.xproyect.R;
import com.bmr.xproyect.SelectFotoExtra;

import java.util.ArrayList;

public class AdapterActividades extends RecyclerView.Adapter<AdapterActividades.ActividadesViewHolder>{
    ArrayList<ComentariosRDP> listaactividades;
    ArrayList<ComentariosRDP> listaOriginal;
    public AdapterActividades(ArrayList<ComentariosRDP>listaactividades){
        this.listaactividades= listaactividades;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaactividades);
    }

    @NonNull
    @Override
    public AdapterActividades.ActividadesViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actividades, null, false);
        return new AdapterActividades.ActividadesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterActividades.ActividadesViewHolder holder, int position) {
        holder.setOnClickListener();
        String[] Datos;
        Datos = new String[listaactividades.get(position).getDatos().length];
        for (int i=0 ; i<listaactividades.get(position).getDatos().length;i++){
            Datos[i] = listaactividades.get(position).getDatos()[i];
        }
        if (listaactividades.get(position).getComentario1().equals("")&&listaactividades.get(position).getComentario2().equals("")){
            holder.Comentario2.setBackgroundColor(Color.CYAN);
            holder.Comentario1.setBackgroundColor(Color.CYAN);
            if (listaactividades.get(position).getID()== 5){
                holder.Comentario1.setText("Actividad trimestral");
                holder.Comentario2.setText(listaactividades.get(position).getComentario2());
            }
            else{
                holder.Comentario1.setText("Actividad anual");
                holder.Comentario2.setText(listaactividades.get(position).getComentario2());
            }

        }
        else{
            if (listaactividades.get(position).getID() % 2 == 0) {
                holder.Comentario1.setBackgroundColor(Color.GRAY);
                holder.Comentario2.setBackgroundColor(Color.WHITE);
            }
            else{
                holder.Comentario1.setBackgroundColor(Color.LTGRAY);
                holder.Comentario2.setBackgroundColor(Color.WHITE);
            }
            holder.Comentario1.setText(listaactividades.get(position).getComentario1());
            holder.Comentario2.setText(listaactividades.get(position).getComentario2());
            holder.id = listaactividades.get(position).getID();
            holder.Datos = Datos;
        }


    }

    @Override
    public int getItemCount() {
        return listaactividades.size();
    }

    public class ActividadesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context context;
        TextView Comentario1,Comentario2;
        int id;
        String [] Datos;
        public ActividadesViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            Comentario1 = itemView.findViewById(R.id.Coment1);
            Comentario2 = itemView.findViewById(R.id.Coment2);

        }
        void setOnClickListener(){
            Comentario2.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.Coment2:
                    Context context = v.getContext();
                    Datos[27]=String.valueOf(id);
                    Datos[28]=Comentario2.getText().toString();
                    Intent intent = new Intent(context, EditaTexto.class);
                    intent.putExtra("Datos", Datos);
                    context.startActivity(intent);
                    break;
            }
        }
    }
}
