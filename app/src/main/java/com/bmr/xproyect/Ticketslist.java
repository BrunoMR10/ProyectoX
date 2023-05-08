package com.bmr.xproyect;

import static com.firebase.ui.auth.AuthUI.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bmr.xproyect.Adapters.TicketAdapter;
import com.bmr.xproyect.DataBases.TicketHelperDB;
import com.bmr.xproyect.DataBases.TicketManageDB;
import com.bmr.xproyect.Datos.Datos;
import com.bmr.xproyect.Entidades.Ticket;
import com.bmr.xproyect.Entidades.TicketDB;
import com.bmr.xproyect.Herramientas.Firebase;
import com.bmr.xproyect.Herramientas.ValidaInternet;
import com.bmr.xproyect.ServiceHelper.DriveServiceHelper;
import com.bmr.xproyect.ServiceHelper.SheetsServiceHelper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.protobuf.StringValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Ticketslist extends AppCompatActivity implements SearchView.OnQueryTextListener,View.OnClickListener{
    ValidaInternet vi = new ValidaInternet();
    Firebase fb = new Firebase();
    Datos dt = new Datos();
    String []Datos,Actualiza,Elimina;
    private DriveServiceHelper mDriveServiceHelper;
    private SheetsServiceHelper mSheetServiceHelper;
    private static final int REQUEST_CODE_SIGN_IN = 1;
    TicketAdapter adapterTickets;
    RecyclerView Ticketsview;
    SearchView buscarTickets;
    EditText NuevoTicketName;
    TextView TipoServicioView,fosi;
    ProgressBar Actualizando;
    String Ticket,DBIden;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticketslist);
        buscarTickets = findViewById(R.id.SearchTicket);
        NuevoTicketName = (EditText)findViewById(R.id.Nuevo_Ticket);
        TipoServicioView = (TextView)findViewById(R.id.TipoServicioView);
        fosi = (TextView)findViewById(R.id.fosi);
        Actualizando = (ProgressBar)findViewById(R.id.Actualizando);
        Ticketsview = (RecyclerView) findViewById(R.id.Tickets_View);
        Ticketsview.setLayoutManager(new LinearLayoutManager(this));
        View AñadeTicket = findViewById(R.id.AñadeTicket);
        AñadeTicket.setOnClickListener(this);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                Datos = extras.getStringArray("Datos");
                Actualiza = extras.getStringArray("Actualiza");
                Elimina= extras.getStringArray("Elimina");

            }
        }
        else {
            Datos = (String[]) savedInstanceState.getSerializable("Datos");
            Actualiza = (String[]) savedInstanceState.getSerializable("Actualiza");
            Elimina = (String[]) savedInstanceState.getSerializable("Elimina");
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
            GuardaDatosSP(Datos);
        }
        else{
            GuardaDatosSP(Datos);
        }
        if (Datos[1].equals("Preventivo")){
            DBIden = "Preventivo";

        }
        else if  (Datos[1].equals("Correctivo")){
            DBIden = "Correctivo";
        }

        else if  (Datos[1].equals("Interno")){
            DBIden = "Interno";
            fosi.setText("WO");
        }
        requestSignIn();
        if ((Actualiza==null)&&(Elimina==null)){
            MostrarTickets();
        }
        TipoServicioView.setText(Datos[1]);
        }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.AñadeTicket:
                AñadeTicket();
                break;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void GuardaDatosSP(String [] Datos){
        SharedPreferences sharedPreferences = getSharedPreferences("Datos",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        for(int i=0;i<Datos.length;i++){
            myEdit.putString("Datos"+i, Datos[i]);
        }
        myEdit.commit();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void AñadeTicket() {

        Ticket = NuevoTicketName.getText().toString();
        if (vi.ValidaInternet(this)) {
            if (TextUtils.isEmpty(Ticket)) {
                NuevoTicketName.setError("Escribe el numero de ticket");
                NuevoTicketName.requestFocus();
            } else {
                Actualizando.setVisibility(View.VISIBLE);
                fosi.setVisibility(View.GONE);
                if (Datos[1].equals("Preventivo")){
                    Datos[12]="FO-SI-"+Ticket;
                    mSheetServiceHelper.buscaTicketPreventivo(Datos[12],false,Datos).addOnSuccessListener(Datos -> ValidaTicketPreventivo(Datos, true))
                            .addOnFailureListener(exception2 -> mSheetServiceHelper.buscaTicketPreventivo(Datos[12],true,Datos)
                                    .addOnSuccessListener(Datos2 -> ValidaTicketPreventivo(Datos2,false))
                                    .addOnFailureListener(exception3 -> Toast.makeText(Ticketslist.this,exception3.toString(),Toast.LENGTH_SHORT).show()));
                }else if (Datos[1].equals("Correctivo")){
                    Datos[12]="FO-SI-"+Ticket;
                    mSheetServiceHelper.buscaTicketCorrectivo(Datos[12],Datos).addOnSuccessListener(Datos -> ValidaTicketCorrectivoIntero(Datos))
                            .addOnFailureListener(exception2 -> Toast.makeText(Ticketslist.this, exception2.toString(), Toast.LENGTH_SHORT).show());
                }
                else if (Datos[1].equals("Interno")){
                    Datos[12]="WO"+Ticket;
                    mSheetServiceHelper.buscaTicketInterno(Datos[12],Datos).addOnSuccessListener(Datos -> ValidaTicketCorrectivoIntero(Datos))
                            .addOnFailureListener(exception2 -> Toast.makeText(Ticketslist.this, exception2.toString(), Toast.LENGTH_SHORT).show());
                }
            }
        }
    }
    public void Actualizalista() {

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void ValidaTicketPreventivo(String Datos[], Boolean primera){
        if(Datos[0].equals("Dato no encontrado")){
            if (primera){

                mSheetServiceHelper.buscaTicketPreventivo(Datos[12],true,Datos)
                        .addOnSuccessListener(Datos2 -> ValidaTicketPreventivo(Datos2,false))
                        .addOnFailureListener(exception2 -> Toast.makeText(Ticketslist.this,exception2.toString(),Toast.LENGTH_SHORT).show());
            }

            else{
                Toast.makeText(Ticketslist.this,
                        "Ticket no encontrado, por favor corrobore el numero de Ticket y vuelva a intentarlo",
                        Toast.LENGTH_SHORT).show();
                fosi.setVisibility(View.VISIBLE);
                Actualizando.setVisibility(View.GONE);
            }


        }
        else{

            if (!primera){
                //Toast.makeText(this,"Ticket Encontrado el mes pasado",Toast.LENGTH_SHORT).show();
            }
            else{
                //Toast.makeText(this,"Ticket Encontrado este mes",Toast.LENGTH_SHORT).show();
            }

            AgregaSQL(Datos);

            //AñadeFirebase(Datos,Where, false, false);
        }
    }
    private void ValidaTicketRepetido(){
        mSheetServiceHelper.buscaTicketenlista(Datos).addOnSuccessListener(Done -> Escribeenlista(Datos,Done))
                .addOnFailureListener(exception2 -> Toast.makeText(Ticketslist.this,exception2.toString(),Toast.LENGTH_SHORT).show());
    }
    private void ValidaTicketRepetido2(String Ticket,Integer Numero){
        Toast.makeText(this,"Correctivo",Toast.LENGTH_SHORT).show();
        //Escribeenlista2(Datos,Done)
        mSheetServiceHelper.buscaTicketenlista2(Numero,Datos).addOnSuccessListener(Done -> Escribeenlista2(Datos,Done))
                .addOnFailureListener(exception2 -> Toast.makeText(Ticketslist.this,exception2.toString(),Toast.LENGTH_SHORT).show());
    }
    private void ValidaTicketRepetido3(String Ticket,Integer Numero){
        Toast.makeText(this,"Interno",Toast.LENGTH_SHORT).show();
        //Escribeenlista2(Datos,Done)
        mSheetServiceHelper.buscaTicketenlista3(Numero,Datos).addOnSuccessListener(Done -> Escribeenlista3(Datos,Done))
                .addOnFailureListener(exception2 -> Toast.makeText(Ticketslist.this,exception2.toString(),Toast.LENGTH_SHORT).show());
    }
    private void Escribeenlista(String[] Datos,Integer Numero){
      mSheetServiceHelper.Escribeentabla(Datos,Numero,Actualiza).addOnSuccessListener(Done ->  Toast.makeText(Ticketslist.this,Done.toString(),Toast.LENGTH_SHORT))
               .addOnFailureListener(exception2 -> Toast.makeText(Ticketslist.this,exception2.toString(),Toast.LENGTH_SHORT).show());
    }
    private void Escribeenlista2(String[] Datos,Integer Numero){
        mSheetServiceHelper.Escribeentabla2(Datos,Numero,Actualiza).addOnSuccessListener(Done ->  Toast.makeText(Ticketslist.this,Done.toString(),Toast.LENGTH_SHORT))
                .addOnFailureListener(exception2 -> Toast.makeText(Ticketslist.this,exception2.toString(),Toast.LENGTH_SHORT).show());
    }
    private void Escribeenlista3(String[] Datos,Integer Numero){
        mSheetServiceHelper.Escribeentabla3(Datos,Numero,Actualiza).addOnSuccessListener(Done ->  Toast.makeText(Ticketslist.this,Done.toString(),Toast.LENGTH_SHORT))
                .addOnFailureListener(exception2 -> Toast.makeText(Ticketslist.this,exception2.toString(),Toast.LENGTH_SHORT).show());
    }
    private void ValidaTicketCorrectivoIntero(String Datos[]){
        if(Datos[0].equals("Dato no encontrado")){
            Toast.makeText(Ticketslist.this,
                    "Ticket no encontrado, por favor corrobore el numero de Ticket y vuelva a intentarlo",
                    Toast.LENGTH_SHORT).show();
            MostrarTickets();
            fosi.setVisibility(View.VISIBLE);
            Actualizando.setVisibility(View.GONE);
        }
        else{
            //Toast.makeText(this,"Ticket Encontrado",Toast.LENGTH_SHORT).show();
            AgregaSQL(Datos);
        }
    }
    public void AgregaSQL(String[]Datos){
        TicketHelperDB dbHelper = new TicketHelperDB(this,"Tickets"+DBIden);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if ( (db != null)) {
            TicketManageDB DB= new TicketManageDB(this,"Tickets"+DBIden);
            Datos[27] = String.valueOf(DB.TamañoTabla("Tickets"+DBIden)+1);

            if (Actualiza!=null){
                Datos[27] = Actualiza[1];
                Boolean registro = DB.editaTicket(Datos,"Tickets"+DBIden);
                if (registro) {
                    if (Datos[1].equals("Preventivo")){
                        /*mSheetServiceHelper.ObtenNumerototaldetickets().addOnSuccessListener(Numero -> ValidaTicketRepetido())
                                .addOnFailureListener(exception2 -> Toast.makeText(Ticketslist.this,exception2.toString(),Toast.LENGTH_SHORT).show());*/
                        ValidaTicketRepetido();
                        fb.RegistraTicket(Datos,this);
                    }
                    else if  (Datos[1].equals("Correctivo")){
                        mSheetServiceHelper.ObtenNumerototaldetickets2(Datos).addOnSuccessListener(Numero -> ValidaTicketRepetido2(Datos[12],Numero))
                                .addOnFailureListener(exception2 -> Toast.makeText(Ticketslist.this,exception2.toString(),Toast.LENGTH_SHORT).show());
                        fb.RegistraTicketCorrectivo(Datos,this);
                    }
                    else if (Datos[1].equals("Interno")){
                        mSheetServiceHelper.ObtenNumerototaldetickets3(Datos).addOnSuccessListener(Numero -> ValidaTicketRepetido3(Datos[12],Numero))
                                .addOnFailureListener(exception2 -> Toast.makeText(Ticketslist.this,exception2.toString(),Toast.LENGTH_SHORT).show());
                        fb.RegistraTicketInterno(Datos,this);
                    }

                    //Toast.makeText(this, "REGISTRO CORRECTO", Toast.LENGTH_SHORT).show();
                } else {
                    MostrarTickets();
                    fosi.setVisibility(View.VISIBLE);
                    Actualizando.setVisibility(View.GONE);
                    Toast.makeText(this, "ERROR EN REGISTRO", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                long id = DB.InsertaDatos(Datos,"Tickets"+DBIden);
                if (id > 0) {

                    if (Datos[1].equals("Preventivo")){
                        /*mSheetServiceHelper.ObtenNumerototaldetickets().addOnSuccessListener(Numero -> ValidaTicketRepetido(Datos[12],Numero))
                                .addOnFailureListener(exception2 -> Toast.makeText(Ticketslist.this,exception2.toString(),Toast.LENGTH_SHORT).show());*/
                        ValidaTicketRepetido();
                        fb.RegistraTicket(Datos,this);
                    }
                    else if  (Datos[1].equals("Correctivo")){

                        mSheetServiceHelper.ObtenNumerototaldetickets2(Datos).addOnSuccessListener(Numero -> ValidaTicketRepetido2(Datos[12],Numero))
                                .addOnFailureListener(exception2 -> Toast.makeText(Ticketslist.this,exception2.toString(),Toast.LENGTH_SHORT).show());
                        fb.RegistraTicketCorrectivo(Datos,this);
                    }
                    else if (Datos[1].equals("Interno")){
                        mSheetServiceHelper.ObtenNumerototaldetickets3(Datos).addOnSuccessListener(Numero -> ValidaTicketRepetido3(Datos[12],Numero))
                                .addOnFailureListener(exception2 -> Toast.makeText(Ticketslist.this,exception2.toString(),Toast.LENGTH_SHORT).show());
                        fb.RegistraTicketInterno(Datos,this);
                    }

                    //Toast.makeText(this, "REGISTRO CORRECTO", Toast.LENGTH_SHORT).show();
                } else {
                    MostrarTickets();
                    fosi.setVisibility(View.VISIBLE);
                    Actualizando.setVisibility(View.GONE);
                    Toast.makeText(this, "ERROR EN REGISTRO", Toast.LENGTH_SHORT).show();
                }
            }

        } else {
            MostrarTickets();
            fosi.setVisibility(View.VISIBLE);
            Actualizando.setVisibility(View.GONE);
            Toast.makeText(this, "ERROR AL CREAR DB", Toast.LENGTH_SHORT).show();
        }
    }
    public void MostrarTickets(){
        ArrayList<Ticket> listatickets = new ArrayList<>();
        TicketManageDB DB= new TicketManageDB(this,"Tickets"+DBIden);
        int size = DB.TamañoTabla("Tickets"+DBIden);
        int i =0;
        while (i<size) {
            TicketDB db=DB.mostrarDatos("Tickets"+DBIden,i+1);
            if (db != null){
                Ticket ticket = null;
                ticket = new Ticket();
                ticket.setId(db.getId());
                ticket.setNomenclatura(db.getNomenclatura());
                ticket.setNombreSeg(db.getNombreSeg());
                ticket.setPuestoSeg(db.getPuestoSeg());
                ticket.setTipoServicio(db.getTipoServicio());
                ticket.setTrimestre(db.getTrimestre());
                ticket.setPeriodicidad(db.getPeriodicidad());
                ticket.setTicket(db.getTicket());
                ticket.setAduana(db.getAduana());
                ticket.setEquipo(db.getEquipo());
                ticket.setUbicacion(db.getUbicacion());
                ticket.setSerie(db.getSerie());
                ticket.setFechaInicio(db.getFechaInicio());
                ticket.setHoraInicio(db.getHoraInicio());
                ticket.setFechaFin(db.getFechaFin());
                ticket.setHoraFin(db.getHoraFin());
                ticket.setFalla(db.getFalla());
                ticket.setFechaLLamada(db.getFechaLLamada());
                ticket.setHoraLLamada(db.getHoraLLamada());
                ticket.setFechaSitio(db.getFechaSitio());
                ticket.setHoraSitio(db.getHoraSitio());
                ticket.setNombreTecnico(db.getNombreTecnico());
                ticket.setDatos(Datos);
                listatickets.add(ticket);
                adapterTickets = new TicketAdapter(listatickets);
                Ticketsview.setAdapter(adapterTickets);
            }
            else{
                Toast.makeText(this,"No se puede accder",Toast.LENGTH_SHORT).show();
            }
            i++;



        }
        buscarTickets.setOnQueryTextListener(this);
        }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,PantallaPrincipal.class);
        i.putExtra("Datos",Datos);
        startActivity(i);
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String s) {
        adapterTickets.Filtrado(s);
        return false;
    }

        ///////////////////////////SERVICIOS GOOGLE//////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        switch (requestCode) {
            case REQUEST_CODE_SIGN_IN:
                if (resultCode == Activity.RESULT_OK && resultData != null) {
                    handleSignInResult(resultData);
                }
                break;

        }

        super.onActivityResult(requestCode, resultCode, resultData);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleSignInResult(Intent result) {
        GoogleSignIn.getSignedInAccountFromIntent(result)
                .addOnSuccessListener(googleAccount -> {
                    // Use the authenticated account to sign in to the Drive service.
                    GoogleAccountCredential credential =
                            GoogleAccountCredential.usingOAuth2(
                                    this, Collections.singleton(DriveScopes.DRIVE_FILE));
                    credential.setSelectedAccount(googleAccount.getAccount());
                    Drive googleDriveService =
                            new Drive.Builder(
                                    AndroidHttp.newCompatibleTransport(),
                                    new GsonFactory(),
                                    credential)
                                    .setApplicationName("Drive API Migration")
                                    .build();

                    mDriveServiceHelper = new DriveServiceHelper(googleDriveService);
                    GoogleAccountCredential credential2 = GoogleAccountCredential.usingOAuth2(
                            this, Arrays.asList(SheetsScopes.SPREADSHEETS)).setBackOff(new ExponentialBackOff());
                    credential2.setSelectedAccount(googleAccount.getAccount());
                    Sheets googleSheetsService = new Sheets.Builder(
                            AndroidHttp.newCompatibleTransport(),
                            JacksonFactory.getDefaultInstance(), credential2)
                            .setApplicationName("Sheets API Migration")
                            .build();
                    mSheetServiceHelper = new SheetsServiceHelper(googleSheetsService);

                    // The DriveServiceHelper encapsulates all REST API and SAF functionality.
                    // Its instantiation is required before handling any onClick actions
                    if (Actualiza !=null){
                        SharedPreferences sharedPreferences = getSharedPreferences("Datos",MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putString("Actualiza"+Actualiza[0], "Actualiza");
                        myEdit.commit();
                        if  (Datos[1].equals("Interno")){
                            NuevoTicketName.setText(Actualiza[0].replaceAll("WO", ""));
                        }
                        else{
                            NuevoTicketName.setText(Actualiza[0].replaceAll("FO-SI-", ""));
                        }

                        Toast.makeText(this,Actualiza[0],Toast.LENGTH_SHORT).show();
                        AñadeTicket();
                    }
                    if (Elimina !=null) {
                        Datos[27] = Elimina[1];
                        fosi.setVisibility(View.GONE);
                        Actualizando.setVisibility(View.VISIBLE);
                        Toast.makeText(this, "Eliminando", Toast.LENGTH_SHORT).show();
                        TicketManageDB DB= new TicketManageDB(this,"Tickets"+DBIden);
                        Boolean elimina = DB.eliminarTicket(Integer.parseInt(Datos[27]),"Tickets"+DBIden);
                        if (elimina) {
                             fb.EliminaTicket(DBIden,Datos,this,DBIden);
                        }else{
                            fosi.setVisibility(View.VISIBLE);
                            Actualizando.setVisibility(View.GONE);
                            Toast.makeText(this, "No se pudo Eliminar", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .addOnFailureListener(exception -> Toast.makeText(this,exception.toString(),Toast.LENGTH_SHORT).show());
    }
    private void requestSignIn() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .requestScopes(new Scope(DriveScopes.DRIVE_FILE))
                        .requestScopes(new Scope(SheetsScopes.SPREADSHEETS))
                        .requestScopes(new Scope(SheetsScopes.SPREADSHEETS_READONLY))
                        .build();
        GoogleSignInClient client = GoogleSignIn.getClient(this, signInOptions);

        // The result of the sign-in Intent is handled in onActivityResult.
        startActivityForResult(client.getSignInIntent(), REQUEST_CODE_SIGN_IN);
    }


}