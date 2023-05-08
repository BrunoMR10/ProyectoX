package com.bmr.xproyect.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bmr.xproyect.Entidades.RFP;
import com.bmr.xproyect.PreventivoRF;
import com.bmr.xproyect.R;
import com.bmr.xproyect.SelectFoto;

import java.io.DataOutput;
import java.util.ArrayList;

public class RFPAdapter extends RecyclerView.Adapter<RFPAdapter.RFPViewHolder> {
    ArrayList<RFP> listafotos;
    ArrayList<RFP> listaOriginal;
    public RFPAdapter(ArrayList<RFP>listafotos){
        this.listafotos= listafotos;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listafotos);
    }
    @NonNull
    @Override
    public RFPAdapter.RFPViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rfp, null, false);
        return new RFPAdapter.RFPViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RFPAdapter.RFPViewHolder holder, int position) {
        String[] Datos;
        Datos = new String[listafotos.get(position).getDatos().length];
        for (int i=0 ; i<listafotos.get(position).getDatos().length;i++){
            Datos[i] = listafotos.get(position).getDatos()[i];
        }
        holder.Datos = Datos;
        holder.setOnClickListener();
        holder.Descripcion1.setText(listafotos.get(position).getComentario1());
        holder.Descripcion2.setText(listafotos.get(position).getComentario2());
        if (listafotos.get(position).getFoto1() != null){
            holder.Foto1.setImageBitmap(listafotos.get(position).getFoto1());
        }
        if (listafotos.get(position).getFoto2() != null){
            holder.Foto2.setImageBitmap(listafotos.get(position).getFoto2());
        }
        holder.ID1= listafotos.get(position).getID1();
        holder.ID2= listafotos.get(position).getID2();
        listafotos.get(position).getFoto2();
    }

    @Override
    public int getItemCount() {
        return listafotos.size();
    }

    public class RFPViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Descripcion1,Descripcion2;
        ImageView Foto1,Foto2;
        String []Datos;
        int ID1,ID2;
        Context context;
        public RFPViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            Descripcion1 = itemView.findViewById(R.id.Comen1);
            Foto1 = itemView.findViewById(R.id.Foto1);
            Descripcion2 = itemView.findViewById(R.id.Comen2);
            Foto2 = itemView.findViewById(R.id.Foto2);
        }
        void setOnClickListener(){
            Foto1.setOnClickListener(this);
            Foto2.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.Foto1:
                     Datos[1] = "Preventivo";
                     Datos[27]= String.valueOf(ID1);
                    Context context = v.getContext();
                    Intent intent = new Intent(context, SelectFoto.class);
                    intent.putExtra("Datos",Datos);
                    context.startActivity(intent);
                    break;
                case R.id.Foto2:
                    Datos[1] = "Preventivo";
                    Datos[27]= String.valueOf(ID2);
                    Context context2 = v.getContext();
                    Intent intent2 = new Intent(context2, SelectFoto.class);
                    intent2.putExtra("Datos",Datos);
                    context2.startActivity(intent2);
                    break;
            }
        }
    }
}
