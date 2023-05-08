package com.bmr.xproyect.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bmr.xproyect.CorrectivoRD;
import com.bmr.xproyect.CorrectivoRF;
import com.bmr.xproyect.Entidades.Ticket;
import com.bmr.xproyect.InternoRD;
import com.bmr.xproyect.InternoRF;
import com.bmr.xproyect.PreventivoRD;
import com.bmr.xproyect.PreventivoRF;
import com.bmr.xproyect.R;
import com.bmr.xproyect.Ticketslist;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketsViewHolder>{
    ArrayList<Ticket> listaTickets;
    ArrayList<Ticket> listaOriginal;
    public TicketAdapter(ArrayList<Ticket>listaTickets){
        this.listaTickets= listaTickets;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaTickets);

    }

    @NonNull
    @Override
    public TicketAdapter.TicketsViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tickets, null, false);
        return new TicketsViewHolder(view);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull TicketAdapter.TicketsViewHolder holder, int position) {

        String[]Datos = new String[listaTickets.get(position).getDatos().length];
        String[]DatosEntrada = listaTickets.get(position).getDatos();
        String Ticket = listaTickets.get(position).getTicket();
        holder.Ticket.setText(Ticket);
        holder.setOnClickListener();
        Datos[0]=listaTickets.get(position).getNomenclatura();
        Datos[1]=DatosEntrada[1];
        Datos[2]=listaTickets.get(position).getTipoServicio();
        Datos[4]=listaTickets.get(position).getNombreSeg();
        Datos[5]=listaTickets.get(position).getPuestoSeg();
        Datos[6]=DatosEntrada[6];
        Datos[7]=DatosEntrada[7];
        Datos[8]=DatosEntrada[8];
        Datos[9]=DatosEntrada[9];
        Datos[10]=listaTickets.get(position).getTrimestre();
        Datos[11]=listaTickets.get(position).getPeriodicidad();
        Datos[12]=listaTickets.get(position).getTicket();
        Datos[13]=listaTickets.get(position).getAduana();
        Datos[14]=listaTickets.get(position).getEquipo();
        Datos[15]=listaTickets.get(position).getUbicacion();
        Datos[16]=listaTickets.get(position).getSerie();
        Datos[17]=listaTickets.get(position).getFechaInicio();
        Datos[18]=listaTickets.get(position).getHoraInicio();
        Datos[19]=listaTickets.get(position).getFechaFin();
        Datos[20]=listaTickets.get(position).getHoraFin();
        Datos[21]=listaTickets.get(position).getFalla();
        Datos[22]=listaTickets.get(position).getFechaLLamada();
        Datos[23]=listaTickets.get(position).getHoraLLamada();
        Datos[24]=listaTickets.get(position).getFechaSitio();
        Datos[25]=listaTickets.get(position).getHoraSitio();
        Datos[26]=listaTickets.get(position).getNombreTecnico();
        Datos[27]=listaTickets.get(position).getId();
        holder.tipoServicio = listaTickets.get(position).getTipoServicio();
        holder.Datos = Datos;
        holder.Estatus.setText(listaTickets.get(position).getFalla());
        holder.ID=listaTickets.get(position).getId();
        if (Datos[1].equals("Preventivo")){
            holder.FechaHora1Edit.setText(listaTickets.get(position).getFechaInicio());
            holder.FechaHora2Edit.setText(listaTickets.get(position).getHoraInicio());
            holder.FechaHora3Edit.setText(listaTickets.get(position).getHoraFin());
        }
        else if (Datos[1].equals("Correctivo")){

            holder.FechaHora1view.setText("Fecha y hora de inicio");
            holder.FechaHora2view.setText("Fecha y hora en sitio");
            holder.FechaHora3view.setText("Fecha y hora de resuelto");
            holder.FechaHora1Edit.setText(listaTickets.get(position).getFechaInicio()+" "+listaTickets.get(position).getHoraInicio());
            holder.FechaHora2Edit.setText(listaTickets.get(position).getFechaSitio()+" "+listaTickets.get(position).getHoraSitio());
            holder.FechaHora3Edit.setText(listaTickets.get(position).getFechaFin()+" "+listaTickets.get(position).getHoraFin());
        }

        else if (Datos[1].equals("Interno")){

            holder.FechaHora1view.setText("Fecha y hora de inicio");
            holder.FechaHora2view.setText("Fecha y hora en sitio");
            holder.FechaHora3view.setText("Fecha y hora de resuelto");
            holder.FechaHora1Edit.setText(listaTickets.get(position).getFechaInicio()+" "+listaTickets.get(position).getHoraInicio());
            holder.FechaHora2Edit.setText(listaTickets.get(position).getFechaSitio()+" "+listaTickets.get(position).getHoraSitio());
            holder.FechaHora3Edit.setText(listaTickets.get(position).getFechaFin()+" "+listaTickets.get(position).getHoraFin());
        }

    }
    public void Filtrado(String txtBuscar){
        int longitud = txtBuscar.length();
        if (longitud==0){
            listaTickets.clear();
            listaTickets.addAll(listaOriginal);

        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                List<Ticket> collection = listaTickets.stream()
                        .filter(i -> i.getTicket().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listaTickets.clear();
                listaTickets.addAll(collection);
            }else{
                for (Ticket c :listaOriginal){
                    if (c.getTicket().toLowerCase().contains(txtBuscar.toLowerCase())){
                        listaTickets.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return listaTickets.size();
    }

    public class TicketsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView FechaHora1view,FechaHora2view,FechaHora3view,
                FechaHora1Edit,FechaHora2Edit,FechaHora3Edit,Ticket,Estatus;
        Button RDigital,RFotografico;
        String tipoServicio,ID;
        ImageButton Actualiza,Delete;
        String[] Datos;

        public TicketsViewHolder(@NonNull View itemView) {
            super(itemView);
            FechaHora1view = itemView.findViewById(R.id.FechaHora1view);
            FechaHora2view = itemView.findViewById(R.id.FechaHora2view);
            FechaHora3view = itemView.findViewById(R.id.FechaHora3view);
            FechaHora1Edit = itemView.findViewById(R.id.FechaHora1Edit);
            FechaHora2Edit = itemView.findViewById(R.id.FechaHora2Edit);
            FechaHora3Edit = itemView.findViewById(R.id.FechaHora3Edit);
            Ticket = itemView.findViewById(R.id.TicketName_View);
            Estatus =itemView.findViewById(R.id.Estatus);
            RDigital = itemView.findViewById(R.id.RDigital);
            RFotografico = itemView.findViewById(R.id.RFotografico);
            Actualiza = itemView.findViewById(R.id.Actualiza);
            Delete = itemView.findViewById(R.id.Delete);
        }

        void setOnClickListener(){
            RDigital.setOnClickListener(this);
            RFotografico.setOnClickListener(this);
            Actualiza.setOnClickListener(this);
            Delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id. RDigital:
                    if (tipoServicio.equals("Preventivo")){
                        Context context = v.getContext();
                        Intent intent = new Intent(context, PreventivoRD.class);
                        intent.putExtra("Datos",Datos);
                        context.startActivity(intent);
                    }else if (tipoServicio.equals("Correctivo")) {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, CorrectivoRD.class);
                        intent.putExtra("Datos",Datos);
                        context.startActivity(intent);
                    }
                    else if (tipoServicio.equals("Interno")) {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, InternoRD.class);
                        intent.putExtra("Datos",Datos);
                        context.startActivity(intent);
                    }

                    break;
                case R.id. RFotografico:
                    if (tipoServicio.equals("Preventivo")){
                        Context context = v.getContext();
                        Intent intent = new Intent(context, PreventivoRF.class);
                        intent.putExtra("Datos",Datos);
                        context.startActivity(intent);

                    }else if (tipoServicio.equals("Correctivo")) {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, CorrectivoRF.class);
                        intent.putExtra("Datos",Datos);
                        context.startActivity(intent);
                    }
                    else if (tipoServicio.equals("Interno")) {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, InternoRF.class);
                        intent.putExtra("Datos",Datos);
                        context.startActivity(intent);
                    }

                    break;
                case R.id. Actualiza:
                        String[]Actualiza = new String[]{
                                Ticket.getText().toString(),
                                ID
                        };
                        Context context = v.getContext();
                        Intent intent = new Intent(context, Ticketslist.class);
                        intent.putExtra("Actualiza", Actualiza);
                        intent.putExtra("Datos",Datos);
                        context.startActivity(intent);
                    break;

                case R.id. Delete:
                    String[]Elimina = new String[]{
                            Ticket.getText().toString(),
                            ID
                    };
                    context = v.getContext();
                    Intent intent2 = new Intent(context, Ticketslist.class);
                    intent2.putExtra("Elimina", Elimina);
                    intent2.putExtra("Datos",Datos);
                    context.startActivity(intent2);
                    break;

            }
        }
    }
}
