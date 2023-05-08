package com.bmr.xproyect.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bmr.xproyect.ActadeEntrega;
import com.bmr.xproyect.EditaUsuario;
import com.bmr.xproyect.Entidades.Aduanas;

import com.bmr.xproyect.R;

import java.util.ArrayList;

public class AdapterAduanas extends RecyclerView.Adapter<AdapterAduanas.AduanasViewHolder> {
    ArrayList<Aduanas> listaAduanas;
    ArrayList<Aduanas> listaOriginal;
    public AdapterAduanas(ArrayList<Aduanas>listaAduanas){
        this.listaAduanas= listaAduanas;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaAduanas);
    }
    @NonNull
    @Override
    public AdapterAduanas.AduanasViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aduanas, null, false);
        return new AdapterAduanas.AduanasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAduanas.AduanasViewHolder holder, int position) {
        String Aduana,Entidad,Municipio,Calle,Colonia,CP,Direccion;
        Aduana = listaAduanas.get(position).getAduana();
        Entidad = listaAduanas.get(position).getEntidad();
        Municipio = listaAduanas.get(position).getMunicipio();
        Calle = listaAduanas.get(position).getCalle();
        Colonia = listaAduanas.get(position).getColonia();
        CP = listaAduanas.get(position).getCP();
        holder.Datos = listaAduanas.get(position).getDatos();
        System.out.println("ObteniendoDatos");
        Direccion = Entidad+" "+Municipio+" "+Calle+" "+Colonia+" CP. "+CP;
        holder.Aduana.setText(Aduana);
        holder.Direccion.setText(Direccion);
        holder.Where = listaAduanas.get(position).getWhere();
        holder.DatosDirección = new String[]{
                Aduana,//0
                Entidad ,//1
                Municipio,//2
                Calle,//3
                Colonia,//4
                CP,//5
                Direccion//6
        };
    }

    @Override
    public int getItemCount() {
        return listaAduanas.size();
    }

    public class AduanasViewHolder  extends RecyclerView.ViewHolder{
        TextView Aduana, Direccion;
        String Where;
        String[] DatosDirección,Datos;
        public AduanasViewHolder(@NonNull View itemView) {
            super(itemView);
            Aduana = itemView.findViewById(R.id.ANAM);
            Direccion = itemView.findViewById(R.id.Direccion);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  if (Where!=null){
                      if (Where.equals("Actadeentrega")){
                          Context context = v.getContext();
                          Intent intent = new Intent(context, ActadeEntrega.class);
                          intent.putExtra("Datos",Datos);
                          intent.putExtra("DatosDirección",DatosDirección);
                          context.startActivity(intent);
                      }

                  }
                }
            });

        }
    }
}
