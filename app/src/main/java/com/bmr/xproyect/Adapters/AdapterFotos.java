package com.bmr.xproyect.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bmr.xproyect.CorrectivoRF;
import com.bmr.xproyect.Entidades.Fotos;
import com.bmr.xproyect.FotosExtra;
import com.bmr.xproyect.InternoRF;
import com.bmr.xproyect.PreventivoRF;
import com.bmr.xproyect.R;
import com.bmr.xproyect.SelectFotoExtra;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdapterFotos extends RecyclerView.Adapter<AdapterFotos.FotosViewHolder>{
    ArrayList<Fotos> listafotos;
    ArrayList<Fotos> listaOriginal;
    public AdapterFotos(ArrayList<Fotos>listafotos){
        this.listafotos= listafotos;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listafotos);
    }

    @NonNull
    @Override
    public FotosViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fotosextra, null, false);
        return new FotosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FotosViewHolder holder, int position) {
        holder.setOnClickListener();
        String[] Datos;
        Datos = new String[listafotos.get(position).getDatos().length];
        for (int i=0 ; i<listafotos.get(position).getDatos().length;i++){
            Datos[i] = listafotos.get(position).getDatos()[i];
        }
        if (listafotos.get(position).getFoto2()!=null){
            holder.Foto.setImageBitmap(listafotos.get(position).getFoto2());
        }else{

        }
        holder.Descripcion.setText(listafotos.get(position).getDescripcion());
        holder.id = listafotos.get(position).getId2();
        holder.Datos = Datos;
        holder.Reporte.setChecked(listafotos.get(position).getReporteFotos());
        holder.Estado =!listafotos.get(position).getReporteFotos();
    }

    public void Filtrados(String txtBuscar){
        int longitud = txtBuscar.length();
        if (longitud==0){
            listafotos.clear();
            listafotos.addAll(listaOriginal);

        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                List<Fotos> collection = listafotos.stream()
                        .filter(i -> i.getDescripcion().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listafotos.clear();
                listafotos.addAll(collection);
            }else{
                for (Fotos c :listaOriginal){
                    if (c.getDescripcion().toLowerCase().contains(txtBuscar.toLowerCase())){
                        listafotos.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return listafotos.size();
    }

    public class FotosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context context;
        TextView Descripcion;
        ImageView Foto;
        String[] Datos;
        Switch Reporte;
        Boolean Estado;
        int id;
        public FotosViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            Descripcion = itemView.findViewById(R.id.Descripcion);
            Foto = itemView.findViewById(R.id.FotoPreview);
            Reporte = itemView.findViewById(R.id.Reporte);
        }
        void setOnClickListener(){
            Foto.setOnClickListener(this);
            Descripcion.setOnClickListener(this);
            Reporte.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.FotoPreview:
                    Datos[28] = Descripcion.getText().toString();
                    Datos[27]= String.valueOf(id);
                    Context context = v.getContext();
                    Intent intent = new Intent(context, SelectFotoExtra.class);
                    intent.putExtra("Datos",Datos);
                    context.startActivity(intent);
                    break;

                case R.id.Reporte:

                    Context context2 = v.getContext();
                    Intent intent2;
                    if (Datos[1].equals("CorrectivosEdit")||Datos[1].equals("Correctivo"))intent2 = new Intent(context2, CorrectivoRF.class);
                    else if (Datos[1].equals("ExtraEdit")||Datos[1].equals("Preventivo"))intent2 = new Intent(context2, FotosExtra.class);
                    else intent2 = new Intent(context2, InternoRF.class);
                    intent2.putExtra("Datos",Datos);
                    intent2.putExtra("Reporte",Estado);
                    intent2.putExtra("ID",String.valueOf(id));
                    context2.startActivity(intent2);
                    break;




            }
        }
    }
}
