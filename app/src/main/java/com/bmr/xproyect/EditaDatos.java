package com.bmr.xproyect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EditaDatos extends AppCompatActivity {
    String Datos[];
    EditText TrimestreRFP,TicketRFP,FechaRFP,PeriodicidadRFP,
            AduanaRFP,EquipoRFP,SerieRFP,UbicacionRFP,HoraInicioRFP,HoraTerminoRFP,FechaTerminoRFP
            ,TecnicoRFP,FallaRFP,FechaLlamadaRFP,HoraLlamadaRFP,FechaLlegadaRFP,HoraLlegadRFP,LetraRFD;
    LinearLayout Correctivo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_datos);
        TrimestreRFP = (EditText) findViewById(R.id.TrimestreRFP);
        TicketRFP = (EditText) findViewById(R.id.TicketRFP);
        FechaRFP = (EditText) findViewById(R.id.FechaRFP);
        PeriodicidadRFP = (EditText) findViewById(R.id.PeriodicidadRFP);
        AduanaRFP = (EditText) findViewById(R.id.AduanaRFP);
        EquipoRFP = (EditText) findViewById(R.id.EquipoRFP);
        SerieRFP = (EditText) findViewById(R.id.SerieRFP);
        UbicacionRFP = (EditText) findViewById(R.id.UbicacionRFP);
        HoraInicioRFP = (EditText) findViewById(R.id.HoraInicioRFP);
        HoraTerminoRFP = (EditText) findViewById(R.id.HoraTerminoRFP);
        FechaTerminoRFP = (EditText) findViewById(R.id.FechaTerminoRFP);
        TecnicoRFP = (EditText) findViewById(R.id.TecnicoRFP);
        FallaRFP = (EditText) findViewById(R.id.FallaRFP);
        FechaLlamadaRFP = (EditText) findViewById(R.id.FechaLlamadaRFP);
        HoraLlamadaRFP = (EditText) findViewById(R.id.HoraLlamadaRFP);
        FechaLlegadaRFP = (EditText) findViewById(R.id.FechaLlegadaRFP);
        HoraLlegadRFP = (EditText) findViewById(R.id.HoraLlegadRFP);
        LetraRFD = (EditText) findViewById(R.id.LetraRFD);
        Correctivo = (LinearLayout) findViewById(R.id.Correctivo);
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
        ConfiguraIncio();
    }
    private void ConfiguraIncio(){
        SharedPreferences sharedPreferences = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);

        TrimestreRFP.setText("0"+Datos[10]);
        PeriodicidadRFP.setText(Datos[11]);
        TicketRFP.setText(Datos[12]);
        AduanaRFP.setText(Datos[13]);
        EquipoRFP.setText(Datos[14]);
        UbicacionRFP.setText(Datos[15]);
        SerieRFP.setText(Datos[16]);
        FechaRFP.setText(Datos[17]);
        HoraInicioRFP.setText(Datos[18]);
        FechaTerminoRFP.setText(Datos[19]);
        HoraTerminoRFP.setText(Datos[20]);
        LetraRFD.setText(String.valueOf(sharedPreferences.getInt("TamañoLetra", 9)));

        if (Datos[1].equals("RDC")){
            Correctivo.setVisibility(View.VISIBLE);
        }
        else{
            Correctivo.setVisibility(View.GONE);
        }
        FallaRFP.setText(Datos[21]);
        FechaLlamadaRFP.setText(Datos[22]);
        HoraLlamadaRFP.setText(Datos[23]);
        FechaLlegadaRFP.setText(Datos[24]);
        HoraLlegadRFP.setText(Datos[25]);
        TecnicoRFP.setText(Datos[26]);

    }
    public void Done(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("Datos"+Datos[0],MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        Datos[10] = TrimestreRFP.getText().toString().replaceAll("0", "");
        Datos[11] = PeriodicidadRFP.getText().toString();
        Datos[12] = TicketRFP.getText().toString();
        Datos[13] = AduanaRFP.getText().toString();
        Datos[14] = EquipoRFP.getText().toString();
        Datos[15] = UbicacionRFP.getText().toString();
        Datos[16] = SerieRFP.getText().toString();
        Datos[17] = FechaRFP.getText().toString();
        Datos[18] = HoraInicioRFP.getText().toString();
        Datos[19] = FechaTerminoRFP.getText().toString();
        Datos[20] = HoraTerminoRFP.getText().toString();
        Datos[21] = FallaRFP.getText().toString();
        Datos[22] = FechaLlamadaRFP.getText().toString();
        Datos[23] = HoraLlamadaRFP.getText().toString();
        Datos[24] = FechaLlegadaRFP.getText().toString();
        Datos[25] = HoraLlegadRFP.getText().toString();
        Datos[26] = TecnicoRFP.getText().toString();

        for(int i=0;i<Datos.length;i++){
            myEdit.putString("Datos"+i, Datos[i]);
        }
        if (Datos[1].equals("RDC")){
            if (Integer.parseInt(LetraRFD.getText().toString())>9){
                Toast.makeText(this,"El valor de la letra debe ser menor o igual a 9",Toast.LENGTH_SHORT).show();
            }else {
                myEdit.putInt("TamañoLetra", Integer.parseInt(LetraRFD.getText().toString()));
                myEdit.commit();
            }

        }else{
            myEdit.commit();
        }

        if (Datos[1].equals("RF")){
            Datos[1]= "Preventivo";
            Intent intent = new Intent(this,PreventivoRF.class);
            intent.putExtra("Datos",Datos);
            startActivity(intent);
        }
        else if (Datos[1].equals("RD")){
            Datos[1]= "Preventivo";
            Intent intent = new Intent(this,PreventivoRD.class);
            intent.putExtra("Datos",Datos);
            startActivity(intent);
        }else if (Datos[1].equals("RFC")){
            Datos[1]= "Correctivo";
            Intent intent = new Intent(this,CorrectivoRF.class);
            intent.putExtra("Datos",Datos);
            startActivity(intent);
        }
        else if (Datos[1].equals("RDC")){
            Datos[1]= "Correctivo";
            Intent intent = new Intent(this,CorrectivoRD.class);
            intent.putExtra("Datos",Datos);
            startActivity(intent);
        }else if (Datos[1].equals("RFI")){
            Datos[1]= "Interno";
            Intent intent = new Intent(this,InternoRF.class);
            intent.putExtra("Datos",Datos);
            startActivity(intent);
        }
        else if (Datos[1].equals("RDI")){
            Datos[1]= "Interno";
            Intent intent = new Intent(this,InternoRD.class);
            intent.putExtra("Datos",Datos);
            startActivity(intent);
        }
        else{

        }

    }
    public void Cancelar(View view){
        if (Datos[1].equals("RF")){
            Datos[1]= "Preventivo";
            Intent intent = new Intent(this,PreventivoRF.class);
            intent.putExtra("Datos",Datos);
            startActivity(intent);
        }
        else if (Datos[1].equals("RD")){
            Datos[1]= "Preventivo";
            Intent intent = new Intent(this,PreventivoRD.class);
            intent.putExtra("Datos",Datos);
            startActivity(intent);
        } else if (Datos[1].equals("RFC")){
            Datos[1]= "Correctivo";
            Intent intent = new Intent(this,CorrectivoRF.class);
            intent.putExtra("Datos",Datos);
            startActivity(intent);
        }
        else if (Datos[1].equals("RDC")){
            Datos[1]= "Correctivo";
            Intent intent = new Intent(this,CorrectivoRD.class);
            intent.putExtra("Datos",Datos);
            startActivity(intent);
        }
        else if (Datos[1].equals("RFI")){
            Datos[1]= "Interno";
            Intent intent = new Intent(this,InternoRF.class);
            intent.putExtra("Datos",Datos);
            startActivity(intent);
        }
        else if (Datos[1].equals("RDI")){
            Datos[1]= "Interno";
            Intent intent = new Intent(this,InternoRD.class);
            intent.putExtra("Datos",Datos);
            startActivity(intent);
        }
        else{

        }
    }
    @Override
    public void onBackPressed() {
        if (Datos[1].equals("RF")){
            Datos[1]= "Preventivo";
            Intent intent = new Intent(this,PreventivoRF.class);
            intent.putExtra("Datos",Datos);
            startActivity(intent);
        }
        else if (Datos[1].equals("RD")){
            Datos[1]= "Preventivo";
            Intent intent = new Intent(this,PreventivoRD.class);
            intent.putExtra("Datos",Datos);
            startActivity(intent);
        }else if (Datos[1].equals("RFC")){
            Datos[1]= "Correctivo";
            Intent intent = new Intent(this,CorrectivoRF.class);
            intent.putExtra("Datos",Datos);
            startActivity(intent);
        }
        else if (Datos[1].equals("RDC")){
            Datos[1]= "Correctivo";
            Intent intent = new Intent(this,CorrectivoRD.class);
            intent.putExtra("Datos",Datos);
            startActivity(intent);
        }
        else if (Datos[1].equals("RFI")){
            Datos[1]= "Interno";
            Intent intent = new Intent(this,InternoRF.class);
            intent.putExtra("Datos",Datos);
            startActivity(intent);
        }
        else if (Datos[1].equals("RDI")){
            Datos[1]= "Interno";
            Intent intent = new Intent(this,InternoRD.class);
            intent.putExtra("Datos",Datos);
            startActivity(intent);
        }
        else{

        }
    }
}