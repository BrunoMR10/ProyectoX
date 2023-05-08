package com.bmr.xproyect.ServiceHelper;

import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.bmr.xproyect.Datos.Datos;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AddSheetRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.CellData;
import com.google.api.services.sheets.v4.model.ExtendedValue;
import com.google.api.services.sheets.v4.model.GridRange;
import com.google.api.services.sheets.v4.model.RepeatCellRequest;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SheetsServiceHelper {

    Datos dt = new Datos();
    private final Sheets mSheetsService;
    private final Executor mExecutor = Executors.newSingleThreadExecutor();
    public SheetsServiceHelper(Sheets Service) {
        mSheetsService = Service;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Task<String[]> buscaTicketPreventivo(String Ticket, Boolean pasado,String [] Datos) {
        return Tasks.call(mExecutor, () -> {
            String spreadsheetId = "1F98vvpA_MUdOOvKy8PyfkUgS6fqhl2VmyKcCvfAaTD4"; //spreadsheetID;
           //String spreadsheetId ="1xWkDv_pxAsQnzGjEcQVIbXSD8TjIDw_ZDfie7tKu598";
            YearMonth thisMonth    = YearMonth.now();
            YearMonth lastMonth    = thisMonth.minusMonths(1);
            DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
            String HojaName,range;
            //MesActual
            if (pasado){
                HojaName = lastMonth.format(monthYearFormatter).toUpperCase();
                range = HojaName+"!H1:H60";
            }
            else{
                HojaName = thisMonth.format(monthYearFormatter).toUpperCase();
                range = HojaName+"!H1:H60";
            }

            //Mes Anterior


            String Fecha,DatosSitio,Estatus,HoraInicio,HoraFin,Periodicidad,FechaFin;

            List<String> results = new ArrayList<String>();

            ValueRange response = mSheetsService.spreadsheets().values().get(spreadsheetId,range).execute();

            List<List<Object>> values = response.getValues();

            int ticketubi = 0;
            if (values!=null){
                int k = 1;
                for (List row : values){
                    results.add(row.get(0).toString());
                    if(row.get(0).toString().equals(Ticket)){
                        System.out.printf("Ticket encontrado en :");
                        System.out.printf(String.valueOf(k));
                        ticketubi = k;
                        System.out.printf(String.valueOf(ticketubi));
                        String newrange = HojaName+"!A"+ticketubi+":P"+ticketubi;
                        ValueRange newresponse = mSheetsService.spreadsheets().values().get(spreadsheetId,newrange).execute();
                        try {
                            Fecha = newresponse.getValues().get(0).get(1).toString();
                            System.out.println(Fecha);
                        }catch (Exception e){
                            System.out.println("No se encontro");
                            Fecha = "Dato no encontrado";
                        }
                        try {
                            FechaFin = newresponse.getValues().get(0).get(15).toString();
                            System.out.println(FechaFin);
                        }catch (Exception e){
                            System.out.println("No se encontro");
                            FechaFin= "Dato no encontrado";
                        }
                        try {
                            DatosSitio = newresponse.getValues().get(0).get(5).toString();
                            System.out.println(DatosSitio);
                        }catch (Exception e){
                            System.out.println("No se encontro");
                            DatosSitio = "Dato no encontrado";
                        }
                        try {
                            Periodicidad= newresponse.getValues().get(0).get(6).toString();
                            System.out.println(Periodicidad);
                        }catch (Exception e){
                            System.out.println("No se encontro");
                            Periodicidad = "Dato no encontrado";
                        }
                        try {
                            Estatus =newresponse.getValues().get(0).get(9).toString();
                            System.out.println(Estatus);
                        }catch (Exception e){
                            System.out.println("No se encontro");
                            Estatus  = "Dato no encontrado";
                        }
                        try {
                            HoraInicio = newresponse.getValues().get(0).get(11).toString();
                            System.out.println(HoraInicio);
                        }catch (Exception e){
                            System.out.println("No se encontro");
                            HoraInicio  = "Dato no encontrado";
                        }
                        try {
                            HoraFin = newresponse.getValues().get(0).get(13).toString();
                            System.out.println(HoraFin);
                        }catch (Exception e){
                            System.out.println("No se encontro");
                            HoraFin = "Dato no encontrado";
                        }


                        String[] DatoSitio = dt.DatosSitio(DatosSitio,Periodicidad);

                        Datos[13]=DatoSitio[0];//Aduana
                        Datos[14]="THSCAN MT1213DE";
                        Datos[15]=DatoSitio[1];//Ubicacion
                        Datos[16]=DatoSitio[2];//Serie
                        Datos[11]=DatoSitio[3];//Periodicidad
                        Datos[17]=Fecha;
                        Datos[21]=Estatus;
                        Datos[18]=HoraInicio;
                        Datos[19]=FechaFin;
                        Datos[20]=HoraFin;
                        Datos[0] =dt.NomenclaturaPreventivo(Datos)[1];
                        Datos[10] =dt.NomenclaturaPreventivo(Datos)[0];
                        return Datos ;
                    }
                    else{

                    }
                    k++;
                }

            }
            Datos[13]="Dato no encontrado";
            Datos[15]="Dato no encontrado";
            Datos[16]="Dato no encontrado";
            Datos[11]="Dato no encontrado";
            Datos[17]="Dato no encontrado";
            Datos[21]="Dato no encontrado";
            Datos[18]="Dato no encontrado";
            Datos[19]="Dato no encontrado";
            Datos[0]="Dato no encontrado";

            return Datos;
        });

    }
    public Task<Integer> ObtenNumerototaldetickets() {
        return Tasks.call(mExecutor, () -> {
            String spreadsheetId = "19X1D9S9Ng3hAtc4GvwEHVjv1LrzrclTGmykRP53ePOA"; //spreadsheetID;
            String range = "B1:B1";
            List<String> results = new ArrayList<String>();
            ValueRange response = mSheetsService.spreadsheets().values().get(spreadsheetId,range).execute();
            List<List<Object>> values = response.getValues();

            if (values!=null){

                for (List row : values){
                    row.get(0).toString();
                    return Integer.parseInt(row.get(0).toString());
                }

            }
            return 0;
        });

    }
    public Task<Integer> buscaTicketenlista(String[]Datos)  {
        return Tasks.call(mExecutor, () -> {
            String spreadsheetId = "19X1D9S9Ng3hAtc4GvwEHVjv1LrzrclTGmykRP53ePOA"; //spreadsheetID;
            //String range = "I2:I"+(2+NumeroTickets);
            String range = "T"+Datos[10]+"!E2:H42";
            String range2 = "T"+Datos[10]+"!E43:H83";
            String range3 = "T"+Datos[10]+"!E84:H125";
            //int NumeroTicket= NumeroTickets+1;

            SimpleDateFormat parseador = new SimpleDateFormat("dd-MM-yy");
            SimpleDateFormat formateador = new SimpleDateFormat("MMMM");
            //SimpleDateFormat formateador2 = new SimpleDateFormat("MMMyy");
            Date date = null;
            try {
                date = parseador.parse(Datos[17]);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            String fecha = formateador.format(date);

            int PosInicio = Datos[16].indexOf("TFN");
            int PosFin = Datos[16].length();
            String serie = Datos[16].substring(PosInicio,PosFin);
            int ticketubi = 0;


            ValueRange response = mSheetsService.spreadsheets().values().get(spreadsheetId,range).execute();
            List<List<Object>> values = response.getValues();

            if (values!=null){
                int k = 2;
                for (List row : values){
                    System.out.printf(row.get(0).toString());
                    System.out.printf(serie);
                    if(row.get(0).toString().equals(serie)){
                        ticketubi = k;
                        String newrange = "T"+Datos[10]+"!E"+ticketubi+":H"+ticketubi;
                        ValueRange newresponse = mSheetsService.spreadsheets().values().get(spreadsheetId,newrange).execute();
                        String mes;
                        try {
                            mes = newresponse.getValues().get(0).get(3).toString();
                            System.out.println(mes);
                        }catch (Exception e){
                            System.out.println("No se encontro");
                            mes = "Dato no encontrado";
                        }
                        if (mes.equals(fecha)){
                            System.out.printf("Ticket encontrado en :");
                            //ticketubi = k;
                            System.out.printf(String.valueOf(ticketubi));
                            return ticketubi;
                        }

                    }
                    else{

                    }
                    k++;
                }
            }
            ValueRange response2 = mSheetsService.spreadsheets().values().get(spreadsheetId,range2).execute();
            values = response2.getValues();

            if (values!=null){
                int k=43;
                for (List row : values){
                    System.out.printf(row.get(0).toString());
                    if(row.get(0).toString().equals(serie)){
                        ticketubi = k;
                        String newrange = "T"+Datos[10]+"!E"+ticketubi+":H"+ticketubi;
                        ValueRange newresponse = mSheetsService.spreadsheets().values().get(spreadsheetId,newrange).execute();
                        String mes;
                        try {
                            mes = newresponse.getValues().get(0).get(3).toString();
                            System.out.println(mes);
                        }catch (Exception e){
                            System.out.println("No se encontro");
                            mes = "Dato no encontrado";
                        }
                        if (mes.equals(fecha)){
                            System.out.printf("Ticket encontrado en :");
                            //ticketubi = k;
                            System.out.printf(String.valueOf(ticketubi));
                            return ticketubi;
                        }
                    }
                    else{

                    }
                    k++;
                }
            }

            ValueRange response3 = mSheetsService.spreadsheets().values().get(spreadsheetId,range3).execute();
            values = response3.getValues();

            if (values!=null){
                int k=84;
                for (List row : values){
                    System.out.printf(row.get(0).toString());
                    if(row.get(0).toString().equals(serie)){
                        ticketubi = k;
                        String newrange = "T"+Datos[10]+"!E"+ticketubi+":H"+ticketubi;
                        ValueRange newresponse = mSheetsService.spreadsheets().values().get(spreadsheetId,newrange).execute();
                        String mes;
                        try {
                            mes = newresponse.getValues().get(0).get(3).toString();
                            System.out.println(mes);
                        }catch (Exception e){
                            System.out.println("No se encontro");
                            mes = "Dato no encontrado";
                        }
                        if (mes.equals(fecha)){
                            System.out.printf("Ticket encontrado en :");
                            //ticketubi = k;
                            System.out.printf(String.valueOf(ticketubi));
                            return ticketubi;
                        }
                    }
                    else{

                    }
                    k++;
                }
            }

            return (0);
        });
    }
    public Task<Integer> buscaSerie(String Serie,String Where) {
        return Tasks.call(mExecutor, () -> {
            String spreadsheetId = "19X1D9S9Ng3hAtc4GvwEHVjv1LrzrclTGmykRP53ePOA"; //spreadsheetID;
            String HojaName;
            if (Where.equals("Generador")) HojaName = "HG23";
            else HojaName = "CI23";
            String range = HojaName+"!B2:B40";
            List<String> results = new ArrayList<String>();
            ValueRange response = mSheetsService.spreadsheets().values().get(spreadsheetId,range).execute();
            List<List<Object>> values = response.getValues();
            System.out.printf(range);
            System.out.printf(Serie);
            int PosInicio = Serie.indexOf("TFN");
            int PosFin = Serie.length();
            String serie = Serie.substring(PosInicio,PosFin);
            int ticketubi = 0;
            if (values!=null){
                int k = 2;
                for (List row : values){
                    System.out.printf(row.get(0).toString());
                    if(row.get(0).toString().equals(serie)){
                        System.out.printf("Ticket encontrado en :");
                        ticketubi = k;
                        System.out.printf(String.valueOf(ticketubi));
                        return ticketubi;
                    }
                    else{

                    }
                    k++;
                }
            }
            return 0;
        });
    }
    public Task<Boolean> Escribeentabla(String[]Datos,Integer NumeroTickets,String[] Actualiza) {
        return Tasks.call(mExecutor, () -> {
            String spreadsheetId = "19X1D9S9Ng3hAtc4GvwEHVjv1LrzrclTGmykRP53ePOA";
            String HojaName,range,range2;
            int NumeroTicketsnew = NumeroTickets;
            range = "T"+Datos[10]+"!B"+((NumeroTicketsnew))+":D"+((NumeroTicketsnew));
            range2 = "B1:B1";
            Object b = new Object();
            b = Datos[4];
            Object c = new Object();
            c = "-";
            Object d = new Object();
            d = Datos[13];
            ValueRange valueRange = new ValueRange();
            valueRange.setValues(
                    Arrays.asList(
                            Arrays.asList(b,c,d)));

            range2 = "T"+Datos[10]+"!F"+((NumeroTicketsnew))+":N"+((NumeroTicketsnew));
            //Object f = new Object();
            //f = Datos[16];
            Object g = new Object();
            g = Datos[15];
            Object h = new Object();
            h = "T"+Datos[10];
            Object i = new Object();
            SimpleDateFormat parseador = new SimpleDateFormat("dd-MM-yy");
            SimpleDateFormat formateador = new SimpleDateFormat("MMMM");
            //SimpleDateFormat formateador2 = new SimpleDateFormat("MMMyy");
            Date date = null;
            try {
                date = parseador.parse(Datos[17]);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            String fecha = formateador.format(date);
            i = fecha;

            Object j = new Object();
            j = Datos[11];
            Object k = new Object();
            k = Datos[12];
            Object l = new Object();
            l = Datos[17];
            Object m = new Object();
            m = Datos[18];
            Object n = new Object();
            n = Datos[19];
            Object o = new Object();
            o = Datos[20];
            ValueRange valueRange2 = new ValueRange();
            valueRange2.setValues(
                    Arrays.asList(
                            Arrays.asList(g,h,i,j,k,l,m,n,o)));



            this.mSheetsService.spreadsheets().values().update(spreadsheetId, range, valueRange)
                    .setValueInputOption("RAW")
                    .execute();
            this.mSheetsService.spreadsheets().values().update(spreadsheetId, range2, valueRange2)
                    .setValueInputOption("RAW")
                    .execute();

            /*if (Actualiza==null) this.mSheetsService.spreadsheets().values().update(spreadsheetId, range2, valueRange2)
                    .setValueInputOption("RAW")
                    .execute();*/
            return true;
        });

    }
    public Task<Boolean> EscibeenTablaGenerador(String Generador,String Fecha,int Ubi,String Where) {
        return Tasks.call(mExecutor, () -> {
            String spreadsheetId = "19X1D9S9Ng3hAtc4GvwEHVjv1LrzrclTGmykRP53ePOA";
            String HojaName,range;
            if (Where.equals("Generador"))HojaName = "HG23";
            else HojaName = "CI23";
            SimpleDateFormat parseador = new SimpleDateFormat("dd-MM-yy");
            SimpleDateFormat formateador = new SimpleDateFormat("MMMM");
            //SimpleDateFormat formateador2 = new SimpleDateFormat("MMMyy");
            Date date = null;
            try {
                date = parseador.parse(Fecha);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            String fecha = formateador.format(date);
            if (fecha.equals("enero")) range = HojaName+"!C"+Ubi+":C"+Ubi;
            else if (fecha.equals("febrero")) range = HojaName+"!D"+Ubi+":D"+Ubi;
            else if (fecha.equals("marzo")) range = HojaName+"!E"+Ubi+":D"+Ubi;
            else if (fecha.equals("abril")) range = HojaName+"!F"+Ubi+":F"+Ubi;
            else if (fecha.equals("mayo")) range = HojaName+"!G"+Ubi+":G"+Ubi;
            else if (fecha.equals("junio")) range = HojaName+"!H"+Ubi+":H"+Ubi;
            else if (fecha.equals("julio")) range = HojaName+"!I"+Ubi+":I"+Ubi;
            else if (fecha.equals("agosto")) range = HojaName+"!J"+Ubi+":J"+Ubi;
            else if (fecha.equals("septiembre")) range = HojaName+"!K"+Ubi+":K"+Ubi;
            else if (fecha.equals("octubre")) range = HojaName+"!L"+Ubi+":L"+Ubi;
            else if (fecha.equals("noviembre")) range = HojaName+"!M"+Ubi+":M"+Ubi;
            else range = HojaName+"!N"+Ubi+":N"+Ubi;
            ValueRange valueRange = new ValueRange();
            valueRange.setValues(
                    Arrays.asList(
                            Arrays.asList(Generador)));
            this.mSheetsService.spreadsheets().values().update(spreadsheetId, range, valueRange)
                    .setValueInputOption("RAW")
                    .execute();
            return true;
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Task<Boolean> AñadeLink(String Link,String FechaFotografico,String FechaDigital,String FechaCF,int NumeroCorrec,int NumeroCorrec2,String[]Datos) {
        return Tasks.call(mExecutor, () -> {
            String spreadsheetId = "19X1D9S9Ng3hAtc4GvwEHVjv1LrzrclTGmykRP53ePOA";
            String range = "T0"+Datos[10]+"!J2:J42";
            String range2 = "T0"+Datos[10]+"!J43:J83";
            String range3 = "T0"+Datos[10]+"!J84:J124";
            //int NumeroTicket= NumeroTickets+1;

            int ticketubi = 0;
            int Numero =0;

            ValueRange response = mSheetsService.spreadsheets().values().get(spreadsheetId,range).execute();
            List<List<Object>> values = response.getValues();

            if (values!=null){
                int k = 2;
                System.out.println("Intentando en tabla 1");
                System.out.println(values);
                for (List row : values){
                    System.out.println(row.get(0).toString());
                    System.out.println(Datos[12]);
                    if(row.get(0).toString().equals(Datos[12])){
                        ticketubi = k;
                        Numero = ticketubi;
                    }
                    else{

                    }
                    k++;
                }
            }
            ValueRange response2 = mSheetsService.spreadsheets().values().get(spreadsheetId,range2).execute();
            values = response2.getValues();

            if (values!=null){
                System.out.println("Intentando en tabla 2");
                int k=43;
                for (List row : values){
                    System.out.println(row.get(0).toString());
                    System.out.println(Datos[12]);
                    if(row.get(0).toString().equals(Datos[12])){
                        ticketubi = k;
                        Numero = ticketubi;
                    }
                    else{

                    }
                    k++;
                }
            }

            ValueRange response3 = mSheetsService.spreadsheets().values().get(spreadsheetId,range3).execute();
            values = response3.getValues();

            if (values!=null){
                System.out.println("Intentando en tabla 3");

                int k=84;
                for (List row : values){
                    System.out.println(row.get(0).toString());
                    System.out.println(Datos[12]);
                    if(row.get(0).toString().equals(Datos[12])){
                        ticketubi = k;
                        Numero = ticketubi;
                    }
                    else{

                    }
                    k++;
                }
            }



           /*int a =Numero;
            int b =Numero+1;

            // If any request is not valid, no requests will be applied.
            List<Request> requests = new ArrayList<>(); // TODO: Update placeholder value.



                requests.add(new Request().setRepeatCell(new RepeatCellRequest()
                        .setRange(new GridRange()
                                .setSheetId(0)
                                .setStartRowIndex(a)
                                .setEndRowIndex(b)
                                .setStartColumnIndex(2)
                                .setEndColumnIndex(3))
                        .setCell(new CellData()
                                .setUserEnteredValue(new ExtendedValue()
                                        .setFormulaValue("=HIPERVINCULO(\"https://drive.google.com/drive/folders/"+Link+"\")")))
                        .setFields("userEnteredValue")));
                BatchUpdateSpreadsheetRequest body = new BatchUpdateSpreadsheetRequest()
                        .setRequests(requests);
                this.mSheetsService.spreadsheets().batchUpdate(spreadsheetId, body).execute();
*/
            String FechaHoraCierre = Datos[19]+" "+Datos[20];
            String Diferencia;
            if (FechaDigital.equals("-")||FechaFotografico.equals("-")||FechaCF.equals("-")){
                Diferencia = "Falta";
            }
            else {
                SimpleDateFormat sdf
                        = new SimpleDateFormat(
                        "dd-MM-yyyy HH:mm");

                Date d1 = sdf.parse(FechaHoraCierre);
                Date d2 = sdf.parse(FechaDigital);
                Date d3 = sdf.parse(FechaFotografico);
                Date d4 = sdf.parse(FechaCF);
                // Calucalte time difference
                // in milliseconds
                if (d2.getTime() > d3.getTime()||d2.getTime() > d4.getTime()) d2 = sdf.parse(FechaDigital);
                else if (d3.getTime() > d2.getTime()||d3.getTime() > d4.getTime()) d2 = sdf.parse(FechaFotografico);
                else d2 = sdf.parse(FechaCF);

                long difference_In_Time
                        = d2.getTime() - d1.getTime();

                long difference_In_Seconds
                        = (difference_In_Time
                        / 1000)
                        % 60;

                long difference_In_Minutes
                        = (difference_In_Time
                        / (1000 * 60))
                        % 60;

                long difference_In_Hours
                        = (difference_In_Time
                        / (1000 * 60 * 60))
                        % 24;

                long difference_In_Years
                        = (difference_In_Time
                        / (1000l * 60 * 60 * 24 * 365));

                long difference_In_Days
                        = (difference_In_Time
                        / (1000 * 60 * 60 * 24))
                        % 365;

                System.out.print(
                        "Difference "
                                + "between two dates is: ");

                System.out.println(
                        difference_In_Years
                                + " years, "
                                + difference_In_Days
                                + " days, "
                                + difference_In_Hours
                                + " hours, "
                                + difference_In_Minutes
                                + " minutes, "
                                + difference_In_Seconds
                                + " seconds");
                Diferencia = "d:"+difference_In_Days+" h:"+difference_In_Hours+" m:"+difference_In_Minutes;

            }

            Object o = new Object();
            o = FechaFotografico;
            Object p = new Object();
            p = FechaDigital;
            Object q = new Object();
            q = FechaCF;
            Object r = new Object();
            r = NumeroCorrec;
            Object s = new Object();
            s = NumeroCorrec2;
            Object t = new Object();
            t = Diferencia;
            ValueRange valueRange = new ValueRange();
            valueRange.setValues(
                    Arrays.asList(
                            Arrays.asList(o,p, q,r,s,t)));
            String range5 = "T0"+Datos[10]+"!O"+((Numero))+":T"+((Numero));
            this.mSheetsService.spreadsheets().values().update(spreadsheetId, range5, valueRange)
                    .setValueInputOption("RAW")
                    .execute();

            Object d = new Object();
            d = "=HIPERVINCULO(\"https://drive.google.com/drive/folders/"+Link+"\")";
            valueRange.setValues(
                    Arrays.asList(
                            Arrays.asList(d)));
            String range6 = "T0"+Datos[10]+"!C"+((Numero))+":C"+((Numero));
            this.mSheetsService.spreadsheets().values().update(spreadsheetId, range6, valueRange)
                    .setValueInputOption("USER_ENTERED")
                    .execute();
            return true;
        });

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////CORRECTIVO////////////////////////////////////////////////////////////
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Task<String[]> buscaTicketCorrectivo(String Ticket,String [] Datos) {
        return Tasks.call(mExecutor, () -> {
            String spreadsheetId = "1g474ApP0HzxDDrUzIO5gGmi8KR09v03uPmuC3fZdF7E"; //spreadsheetID;
            //String spreadsheetId ="1J_JtS_H7ueM56XDJMkVufZMgr1hGsUglcPOb_nYMCu0";
            String HojaName,range;
            //MesActualHojaName+"!H1:H60";

            HojaName = "Tickets Correctivos  ANAM";
            range = HojaName+"!E1:E400";

            String FechaHoraInicio,DatosSitio,Falla,FechaHorasitio,FechaHoraResuelto,llamada;
            List<String> results = new ArrayList<String>();

            ValueRange response = mSheetsService.spreadsheets().values().get(spreadsheetId,range).execute();

            List<List<Object>> values = response.getValues();

            int ticketubi = 0;
            if (values!=null){
                int k = 1;
                for (List row : values){
                    results.add(row.get(0).toString());
                    if(row.get(0).toString().contains(Ticket)){
                        System.out.printf("Ticket encontrado en :");
                        System.out.printf(String.valueOf(k));
                        ticketubi = k;
                        System.out.printf(String.valueOf(ticketubi));
                        String newrange = HojaName+"!A"+ticketubi+":O"+ticketubi;
                        ValueRange newresponse = mSheetsService.spreadsheets().values().get(spreadsheetId,newrange).execute();
                        try {
                            FechaHoraInicio = newresponse.getValues().get(0).get(7).toString();
                            FechaHoraInicio = FechaHoraInicio .replaceAll("(\n|\r)", "");
                            System.out.println(FechaHoraInicio);
                        }catch (Exception e){
                            System.out.println("Dato no encontrado");
                            FechaHoraInicio = "";
                        }
                        try {
                            DatosSitio = newresponse.getValues().get(0).get(8).toString();
                            System.out.println(DatosSitio);
                        }catch (Exception e){
                            System.out.println("Dato no encontrado");
                            DatosSitio = "";
                        }
                        try {
                            Falla= newresponse.getValues().get(0).get(9).toString();
                            System.out.println(Falla);
                        }catch (Exception e){
                            System.out.println("Dato no encontrado");
                            Falla = "";
                        }
                        try {
                            llamada =newresponse.getValues().get(0).get(10).toString();
                            System.out.println(llamada);
                        }catch (Exception e){
                            System.out.println("Dato no encontrado");
                            llamada  = "";
                        }
                        try {
                            FechaHorasitio = newresponse.getValues().get(0).get(11).toString();
                            FechaHorasitio= FechaHorasitio.replaceAll("(\n|\r)", "");
                            System.out.println(FechaHorasitio);
                        }catch (Exception e){
                            System.out.println("Dato no encontrado");
                            FechaHorasitio  = "";
                        }
                        try {
                            FechaHoraResuelto = newresponse.getValues().get(0).get(12).toString();
                            FechaHoraResuelto= FechaHoraResuelto.replaceAll("(\n|\r)", "");
                            System.out.println(FechaHoraResuelto);
                        }catch (Exception e){
                            System.out.println("Dato no encontrado");
                            FechaHoraResuelto = "";
                        }

                        //                        String[] DatoSitio = dt.DatosSitio(DatosSitio,Periodicidad);
                        //                        Datos[13]=DatoSitio[0];//Aduana
                        //                        Datos[14]="THSCAN MT1213DE";
                        //                        Datos[15]=DatoSitio[1];//Ubicacion
                        //                        Datos[16]=DatoSitio[2];//Serie
                        //                        Datos[11]=DatoSitio[3];//Periodicidad
                        //                        Datos[17]=Fecha;
                        //                        Datos[21]=Estatus;
                        //                        Datos[18]=HoraInicio;
                        //                        Datos[20]=HoraFin;
                        //
                        //                        Datos[0] =dt.NomenclaturaPreventivo(Datos);
                        //
                        //                        return Datos ;

                        String[] DatoSitio = dt.DatosSitio(DatosSitio,"");
                        Datos[13]=DatoSitio[0];//Aduana
                        Datos[14]="THSCAN MT1213DE";
                        Datos[15]=DatoSitio[1];//Ubicacion
                        Datos[16]=DatoSitio[2];//Serie
                        Datos[11]=DatoSitio[3];//Periodicidad
                        //Datos[17]=Fecha;
                        String[] DatosFecha = dt.FechasCorrectivo(FechaHoraInicio,
                                FechaHorasitio,FechaHoraResuelto,llamada);
                        Datos[17]=DatosFecha[0];
                        Datos[18]=DatosFecha[1];
                        Datos[19]=DatosFecha[2];
                        Datos[20]=DatosFecha[3];
                        Datos[21]=Falla;
                        Datos[22]=DatosFecha[4];
                        Datos[23]=DatosFecha[5];
                        Datos[24]=DatosFecha[6];
                        Datos[25]=DatosFecha[7];
                        Datos[26]=DatosFecha[8];



                        Datos[0] =dt.NomenclaturaCorrectivo(Datos)[1];
                        Datos[10] =dt.NomenclaturaCorrectivo(Datos)[0];
                        return Datos ;
                    }
                    else{

                    }
                    k++;
                }

            }
            Datos[13]="Dato no encontrado";//Aduana
            Datos[14]="Dato no encontrado";
            Datos[15]="Dato no encontrado";//Ubicacion
            Datos[16]="Dato no encontrado";;//Serie
            Datos[11]="Dato no encontrado";//Periodicidad
            Datos[17]="Dato no encontrado";
            Datos[18]="Dato no encontrado";
            Datos[19]="Dato no encontrado";
            Datos[20]="Dato no encontrado";
            Datos[21]="Dato no encontrado";
            Datos[22]="Dato no encontrado";
            Datos[23]="Dato no encontrado";
            Datos[24]="Dato no encontrado";
            Datos[25]="Dato no encontrado";
            Datos[26]="Dato no encontrado";
            Datos[0]="Dato no encontrado";
            return Datos;
        });

    }
    public Task<Integer> ObtenNumerototaldetickets2(String [] Datos) {
        return Tasks.call(mExecutor, () -> {
            String spreadsheetId = "1rMCre4jJbtXkZB4QGNSs6UeMkhH7uPM7JguH34ahOIw"; //spreadsheetID;
            String range = "T"+Datos[10]+"!B1:B1";
            List<String> results = new ArrayList<String>();
            ValueRange response = mSheetsService.spreadsheets().values().get(spreadsheetId,range).execute();
            List<List<Object>> values = response.getValues();

            if (values!=null){

                for (List row : values){
                    row.get(0).toString();
                    int UltimaColumna = Integer.parseInt(row.get(0).toString());
                    if (UltimaColumna == 0){
                        UltimaColumna = 2;
                    }


                    return UltimaColumna;
                }

            }
            return 2;
        });
    }
    public Task<Integer> buscaTicketenlista2(int NumeroTickets, String [] Datos) {
        return Tasks.call(mExecutor, () -> {
            String spreadsheetId = "1rMCre4jJbtXkZB4QGNSs6UeMkhH7uPM7JguH34ahOIw";//spreadsheetID;
            String range ="T"+Datos[10]+"!H2:H"+(NumeroTickets);
            int NumeroTicket= NumeroTickets;
            List<String> results = new ArrayList<String>();
            ValueRange response = mSheetsService.spreadsheets().values().get(spreadsheetId,range).execute();
            List<List<Object>> values = response.getValues();
            System.out.printf(range);
            System.out.printf(Datos[12]);
            int ticketubi = 0;
            if (values!=null){
                int k = 2;
                for (List row : values){
                    System.out.printf(row.get(0).toString());
                    if(row.get(0).toString().equals(Datos[12])){
                        System.out.printf("Ticket encontrado en :");
                        ticketubi = k;
                        System.out.printf(String.valueOf(ticketubi));
                        return ticketubi-1;
                    }
                    else{

                    }
                    k++;
                }
            }
            return (NumeroTicket);
        });
    }
    public Task<Boolean> Escribeentabla2(String[]Datos,Integer NumeroTickets,String[] Actualiza) {
        return Tasks.call(mExecutor, () -> {
            String spreadsheetId = "1rMCre4jJbtXkZB4QGNSs6UeMkhH7uPM7JguH34ahOIw";
            String range;
            int NumeroTicketsnew = NumeroTickets+1;
            range = "T"+Datos[10]+"!A"+((NumeroTicketsnew))+":Q"+((NumeroTicketsnew));

            Object a = new Object();
            a = String.valueOf(NumeroTicketsnew-3);
            Object b = new Object();
            b = Datos[4];
            Object c = new Object();
            c = "-";
            Object d = new Object();
            d = Datos[13];
            Object e = new Object();
            e = Datos[16];
            Object f = new Object();
            f = Datos[15];
            Object g = new Object();
            g = "T"+Datos[10];
            Object h = new Object();
            h = Datos[12];
            Object i = new Object();
            i = Datos[21];
            Object k = new Object();
            k = Datos[17];
            Object l = new Object();
            l = Datos[18];
            Object m = new Object();
            m = Datos[22];
            Object n = new Object();
            n = Datos[23];
            Object o = new Object();
            o = Datos[24];
            Object p = new Object();
            p = Datos[25];
            Object q = new Object();
            q = Datos[19];
            Object r = new Object();
            r = Datos[20];

            ValueRange valueRange = new ValueRange();
            valueRange.setValues(
                    Arrays.asList(
                            Arrays.asList(a, b,c,d,e,f,g,h,i,k,l,m,n,o,p,q,r)));



            this.mSheetsService.spreadsheets().values().update(spreadsheetId, range, valueRange)
                    .setValueInputOption("RAW")
                    .execute();

            return true;
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Task<Boolean> AñadeLink2(String Link, String Ticket,String FechaFotografico,String FechaDigital,String FechaCF,int NumeroCorrec,int NumeroCorrec2,String[]Datos,String[]EstTatus) {
        return Tasks.call(mExecutor, () -> {
            String spreadsheetId = "1rMCre4jJbtXkZB4QGNSs6UeMkhH7uPM7JguH34ahOIw";
            String range = "T0"+Datos[10]+"!B1:B1";
            int Numero = 0;
            List<String> results = new ArrayList<String>();
            ValueRange response = mSheetsService.spreadsheets().values().get(spreadsheetId,range).execute();
            List<List<Object>> values = response.getValues();

            if (values!=null){

                for (List row : values){
                    row.get(0).toString();
                    Numero = Integer.parseInt(row.get(0).toString());
                }

            }

            String range2 = "T0"+Datos[10]+"!G2:G"+(Numero);
            ValueRange response2 = mSheetsService.spreadsheets().values().get(spreadsheetId,range2).execute();
            List<List<Object>> values2 = response2.getValues();
            System.out.printf(range);
            if (values!=null){
                int k = 2;
                for (List row : values2){
                    System.out.printf(row.get(0).toString());
                    if(row.get(0).toString().equals(Ticket)){
                        System.out.printf("Ticket encontrado en :");
                        Numero = k;

                    }
                    else{

                    }
                    k++;
                }
            }
            /*int a =Numero+1;
            int b =Numero+2;

            // If any request is not valid, no requests will be applied.
           List<Request> requests = new ArrayList<>(); // TODO: Update placeholder value.

            requests.add(new Request().setRepeatCell(new RepeatCellRequest()
                    .setRange(new GridRange()
                            .setSheetId(0)
                            .setStartRowIndex(a)
                            .setEndRowIndex(b)
                            .setStartColumnIndex(2)
                            .setEndColumnIndex(3))
                    .setCell(new CellData()
                            .setUserEnteredValue(new ExtendedValue()
                                    .setFormulaValue("=HIPERVINCULO(\"https://drive.google.com/drive/folders/"+Link+"\")")))
                    .setFields("userEnteredValue")));
            BatchUpdateSpreadsheetRequest body = new BatchUpdateSpreadsheetRequest()
                    .setRequests(requests);
            this.mSheetsService.spreadsheets().batchUpdate(spreadsheetId, body).execute();*/

            String FechaHoraCierre = Datos[19]+" "+Datos[20];
            String Diferencia;
            if (FechaDigital.equals("-")||FechaFotografico.equals("-")||FechaCF.equals("-")){
                Diferencia = "Falta";
            }
            else {
                SimpleDateFormat sdf
                        = new SimpleDateFormat(
                        "dd-MM-yyyy HH:mm");

                Date d1 = sdf.parse(FechaHoraCierre);
                Date d2 = sdf.parse(FechaDigital);
                Date d3 = sdf.parse(FechaFotografico);
                Date d4 = sdf.parse(FechaCF);
                // Calucalte time difference
                // in milliseconds
                if (d2.getTime() > d3.getTime()||d2.getTime() > d4.getTime()) d2 = sdf.parse(FechaDigital);
                else if (d3.getTime() > d2.getTime()||d3.getTime() > d4.getTime()) d2 = sdf.parse(FechaFotografico);
                else d2 = sdf.parse(FechaCF);


                long difference_In_Time
                        = d2.getTime() - d1.getTime();

                long difference_In_Seconds
                        = (difference_In_Time
                        / 1000)
                        % 60;

                long difference_In_Minutes
                        = (difference_In_Time
                        / (1000 * 60))
                        % 60;

                long difference_In_Hours
                        = (difference_In_Time
                        / (1000 * 60 * 60))
                        % 24;

                long difference_In_Years
                        = (difference_In_Time
                        / (1000l * 60 * 60 * 24 * 365));

                long difference_In_Days
                        = (difference_In_Time
                        / (1000 * 60 * 60 * 24))
                        % 365;

                System.out.print(
                        "Difference "
                                + "between two dates is: ");

                System.out.println(
                        difference_In_Years
                                + " years, "
                                + difference_In_Days
                                + " days, "
                                + difference_In_Hours
                                + " hours, "
                                + difference_In_Minutes
                                + " minutes, "
                                + difference_In_Seconds
                                + " seconds");
                Diferencia = "d:"+difference_In_Days+" h:"+difference_In_Hours+" m:"+difference_In_Minutes;

            }
            Object z = new Object();
            z = EstTatus[0];
            Object aa = new Object();
            aa = EstTatus[1];
            Object ab = new Object();
            ab = "-";
            Object ac = new Object();
            ac = EstTatus[2];
            Object ad = new Object();
            ad = EstTatus[3];
            Object ae = new Object();
            ae = FechaFotografico;
            Object af = new Object();
            af = FechaDigital;
            Object ag = new Object();
            ag = FechaCF;
            Object ah = new Object();
            ah = NumeroCorrec;
            Object ai = new Object();
            ai = NumeroCorrec2;
            Object aj = new Object();
            aj = Diferencia;
            ValueRange valueRange = new ValueRange();
            valueRange.setValues(
                    Arrays.asList(
                            Arrays.asList(z,aa,ab,ac,ad,ae,af, ag,ah,ai,aj)));

            String range3 = "T0"+Datos[10]+"!Z"+((Numero))+":AJ"+((Numero));

            this.mSheetsService.spreadsheets().values().update(spreadsheetId, range3, valueRange)
                    .setValueInputOption("RAW")
                    .execute();

            Object d = new Object();
            d = "=HIPERVINCULO(\"https://drive.google.com/drive/folders/"+Link+"\")";
            valueRange.setValues(
                    Arrays.asList(
                            Arrays.asList(d)));
            String range6 = "T0"+Datos[10]+"!C"+((Numero))+":C"+((Numero));
            this.mSheetsService.spreadsheets().values().update(spreadsheetId, range6, valueRange)
                    .setValueInputOption("USER_ENTERED")
                    .execute();
            return true;
        });

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////INTERNO////////////////////////////////////////////////////////////
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Task<String[]> buscaTicketInterno(String Ticket,String [] Datos) {
        return Tasks.call(mExecutor, () -> {
            String spreadsheetId = "1g474ApP0HzxDDrUzIO5gGmi8KR09v03uPmuC3fZdF7E"; //spreadsheetID;
            //String spreadsheetId ="1rkcJAwN4I1qMy0KOnK430EhIKCmFJLe7eXVdTbAnqWk";
            String HojaName,range;
            //MesActualHojaName+"!H1:H60";

            HojaName = "INTERNOS";
            range = HojaName+"!E1:E1000";

            String FechaHoraInicio,DatosSitio,Falla,FechaHorasitio,FechaHoraResuelto;
            List<String> results = new ArrayList<String>();

            ValueRange response = mSheetsService.spreadsheets().values().get(spreadsheetId,range).execute();

            List<List<Object>> values = response.getValues();

            int ticketubi = 0;
            if (values!=null){
                int k = 1;
                for (List row : values){
                    results.add(row.get(0).toString());
                    if(row.get(0).toString().contains(Ticket)) {
                        System.out.printf("Ticket encontrado en :");
                        System.out.printf(String.valueOf(k));
                        ticketubi = k;
                        System.out.printf(String.valueOf(ticketubi));
                        String newrange = HojaName + "!A" + ticketubi + ":O" + ticketubi;
                        ValueRange newresponse = mSheetsService.spreadsheets().values().get(spreadsheetId, newrange).execute();
                        try {
                            DatosSitio = newresponse.getValues().get(0).get(6).toString();
                            System.out.println(DatosSitio);
                        } catch (Exception e) {
                            System.out.println("Dato no encontrado");
                            DatosSitio = "";
                        }
                        try {
                            Falla = newresponse.getValues().get(0).get(7).toString();
                            System.out.println(Falla);
                        } catch (Exception e) {
                            System.out.println("Dato no encontrado");
                            Falla = "";
                        }
                        try {
                            FechaHorasitio = newresponse.getValues().get(0).get(8).toString();
                            FechaHorasitio= FechaHorasitio.replaceAll("(\n|\r)", "");
                            System.out.println(FechaHorasitio);
                        }catch (Exception e){
                            System.out.println("Dato no encontrado");
                            FechaHorasitio  = "";
                        }
                        try {
                            FechaHoraResuelto = newresponse.getValues().get(0).get(9).toString();
                            FechaHoraResuelto= FechaHoraResuelto.replaceAll("(\n|\r)", "");
                            System.out.println(FechaHoraResuelto);
                        }catch (Exception e){
                            System.out.println("Dato no encontrado");
                            FechaHoraResuelto = "";
                        }
                        try {
                            FechaHoraInicio = newresponse.getValues().get(0).get(13).toString();
                            FechaHoraInicio = FechaHoraInicio .replaceAll("(\n|\r)", "");
                            System.out.println(FechaHoraInicio);
                        }catch (Exception e){
                            System.out.println("Dato no encontrado");
                            FechaHoraInicio = "";
                        }




                        //                        String[] DatoSitio = dt.DatosSitio(DatosSitio,Periodicidad);
                        //                        Datos[13]=DatoSitio[0];//Aduana
                        //                        Datos[14]="THSCAN MT1213DE";
                        //                        Datos[15]=DatoSitio[1];//Ubicacion
                        //                        Datos[16]=DatoSitio[2];//Serie
                        //                        Datos[11]=DatoSitio[3];//Periodicidad
                        //                        Datos[17]=Fecha;
                        //                        Datos[21]=Estatus;
                        //                        Datos[18]=HoraInicio;
                        //                        Datos[20]=HoraFin;
                        //
                        //                        Datos[0] =dt.NomenclaturaPreventivo(Datos);
                        //
                        //                        return Datos ;

                        String[] DatoSitio = dt.DatosSitio(DatosSitio,"");
                        Datos[13]=DatoSitio[0];//Aduana
                        Datos[14]="THSCAN MT1213DE";
                        Datos[15]=DatoSitio[1];//Ubicacion
                        Datos[16]=DatoSitio[2];//Serie
                        Datos[11]=DatoSitio[3];//Periodicidad
                        //Datos[17]=Fecha;
                        String[] DatosFecha = dt.FechasInterno(FechaHoraInicio,
                                FechaHorasitio,FechaHoraResuelto);

                        Datos[17]=DatosFecha[0];//INICIO FECHA
                        Datos[18]=DatosFecha[1];//INICIO HORA
                        Datos[19]=DatosFecha[2];//FECHA FIN
                        Datos[20]=DatosFecha[3];//HORAFIN
                        Datos[21]=Falla;
                        Datos[24]=DatosFecha[4];//FECHASITIO
                        Datos[25]=DatosFecha[5];//HORASITIO
                        Datos[0] =dt.NomenclaturaInterno(Datos)[1];
                        Datos[10] =dt.NomenclaturaInterno(Datos)[0];
                        //Datos[0]= dt.NomenclaturaCorrectivo(Datos);

                        return Datos ;
                    }
                    else{

                    }
                    k++;
                }

            }
            Datos[13]="Dato no encontrado";
            Datos[15]="Dato no encontrado";
            Datos[16]="Dato no encontrado";
            Datos[11]="Dato no encontrado";
            Datos[17]="Dato no encontrado";
            Datos[21]="Dato no encontrado";
            Datos[18]="Dato no encontrado";
            Datos[19]="Dato no encontrado";
            Datos[0]="Dato no encontrado";

            return Datos;
        });

    }
    public Task<Integer> ObtenNumerototaldetickets3(String [] Datos) {
        return Tasks.call(mExecutor, () -> {
            String spreadsheetId = "1vXlt5GGMC4TXnAMnTir78hnEtKdgHmp52Bciht5sC6k"; //spreadsheetID;
            String range = "T"+Datos[10]+"!B1:B1";
            List<String> results = new ArrayList<String>();
            ValueRange response = mSheetsService.spreadsheets().values().get(spreadsheetId,range).execute();
            List<List<Object>> values = response.getValues();

            if (values!=null){

                for (List row : values){
                    row.get(0).toString();
                    int UltimaColumna = Integer.parseInt(row.get(0).toString());
                    if (UltimaColumna == 0){
                        UltimaColumna = 2;
                    }


                    return UltimaColumna;
                }

            }
            return 2;
        });
    }
    public Task<Integer> buscaTicketenlista3(int NumeroTickets, String[] Datos) {
        return Tasks.call(mExecutor, () -> {
            String spreadsheetId = "1vXlt5GGMC4TXnAMnTir78hnEtKdgHmp52Bciht5sC6k";//spreadsheetID;
            String range ="T"+Datos[10]+"!D2:D"+(NumeroTickets);
            int NumeroTicket= NumeroTickets;
            List<String> results = new ArrayList<String>();
            ValueRange response = mSheetsService.spreadsheets().values().get(spreadsheetId,range).execute();
            List<List<Object>> values = response.getValues();
            System.out.printf(range);
            System.out.printf(Datos[12]);
            int ticketubi = 0;
            if (values!=null){
                int k = 2;
                for (List row : values){
                    System.out.printf(row.get(0).toString());
                    if(row.get(0).toString().equals(Datos[12])){
                        System.out.printf("Ticket encontrado en :");
                        ticketubi = k;
                        System.out.printf(String.valueOf(ticketubi));
                        return ticketubi-1;
                    }
                    else{

                    }
                    k++;
                }
            }
            return (NumeroTicket);
        });
    }
    public Task<Boolean> Escribeentabla3(String[]Datos,Integer NumeroTickets,String[] Actualiza) {
        return Tasks.call(mExecutor, () -> {
            String spreadsheetId = "1vXlt5GGMC4TXnAMnTir78hnEtKdgHmp52Bciht5sC6k";
            String range;
            int NumeroTicketsnew = NumeroTickets+1;
            range = "T"+Datos[10]+"!A"+((NumeroTicketsnew))+":Z"+((NumeroTicketsnew));

            Object a = new Object();
            a = String.valueOf(NumeroTicketsnew-3);
            Object b = new Object();
            b = "-";
            Object c = new Object();
            c = Datos[4];
            Object d = new Object();
            d = Datos[12];
            Object e = new Object();
            e = "T"+Datos[10];
            Object f = new Object();
            f = Datos[13];
            Object g = new Object();
            g = Datos[16];
            Object h = new Object();
            h = Datos[15];
            Object i = new Object();
            i = Datos[21];
            Object j = new Object();
            j = Datos[17];
            Object k = new Object();
            k = Datos[18];
            Object l = new Object();
            l = Datos[19];
            Object m = new Object();
            m = Datos[20];
            ValueRange valueRange = new ValueRange();
            valueRange.setValues(
                    Arrays.asList(
                            Arrays.asList(a, b,c,d,e,f,g,h,i,j,k,l,m)));


            this.mSheetsService.spreadsheets().values().update(spreadsheetId, range, valueRange)
                    .setValueInputOption("RAW")
                    .execute();

            return true;
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Task<Boolean> AñadeLink3(String Link, String Ticket,String FechaFotografico,String FechaDigital,String FechaCF,int NumeroCorrec,int NumeroCorrec2,String[]Datos) {
        return Tasks.call(mExecutor, () -> {
            String spreadsheetId = "1vXlt5GGMC4TXnAMnTir78hnEtKdgHmp52Bciht5sC6k";
            String range = "T0"+Datos[10]+"!B1:B1";
            int Numero = 0;
            List<String> results = new ArrayList<String>();
            ValueRange response = mSheetsService.spreadsheets().values().get(spreadsheetId,range).execute();
            List<List<Object>> values = response.getValues();

            if (values!=null){

                for (List row : values){
                    row.get(0).toString();
                    Numero = Integer.parseInt(row.get(0).toString());
                }

            }
            System.out.printf(String.valueOf(Numero));
            String range2 = "T0"+Datos[10]+"!D2:D"+(Numero);
            ValueRange response2 = mSheetsService.spreadsheets().values().get(spreadsheetId,range2).execute();
            List<List<Object>> values2 = response2.getValues();
            System.out.printf(range);
            if (values!=null){
                int k = 2;
                for (List row : values2){
                    System.out.printf(row.get(0).toString());
                    if(row.get(0).toString().equals(Ticket)){
                        System.out.printf("Ticket encontrado en :");
                        System.out.printf(String.valueOf(Numero));
                        Numero = k;

                    }
                    else{

                    }
                    k++;
                }
            }

            String FechaHoraCierre = Datos[19]+" "+Datos[20];
            String Diferencia;
            if (FechaDigital.equals("-")||FechaFotografico.equals("-")||FechaCF.equals("-")){
                Diferencia = "Falta";
            }
            else {
                SimpleDateFormat sdf
                        = new SimpleDateFormat(
                        "dd-MM-yyyy HH:mm");

                Date d1 = sdf.parse(FechaHoraCierre);
                Date d2 = sdf.parse(FechaDigital);
                Date d3 = sdf.parse(FechaFotografico);
                Date d4 = sdf.parse(FechaCF);
                // Calucalte time difference
                // in milliseconds
                if (d2.getTime() > d3.getTime()||d2.getTime() > d4.getTime()) d2 = sdf.parse(FechaDigital);
                else if (d3.getTime() > d2.getTime()||d3.getTime() > d4.getTime()) d2 = sdf.parse(FechaFotografico);
                else d2 = sdf.parse(FechaCF);


                long difference_In_Time
                        = d2.getTime() - d1.getTime();

                long difference_In_Seconds
                        = (difference_In_Time
                        / 1000)
                        % 60;

                long difference_In_Minutes
                        = (difference_In_Time
                        / (1000 * 60))
                        % 60;

                long difference_In_Hours
                        = (difference_In_Time
                        / (1000 * 60 * 60))
                        % 24;

                long difference_In_Years
                        = (difference_In_Time
                        / (1000l * 60 * 60 * 24 * 365));

                long difference_In_Days
                        = (difference_In_Time
                        / (1000 * 60 * 60 * 24))
                        % 365;

                System.out.print(
                        "Difference "
                                + "between two dates is: ");

                System.out.println(
                        difference_In_Years
                                + " years, "
                                + difference_In_Days
                                + " days, "
                                + difference_In_Hours
                                + " hours, "
                                + difference_In_Minutes
                                + " minutes, "
                                + difference_In_Seconds
                                + " seconds");
                Diferencia = "d:"+difference_In_Days+" h:"+difference_In_Hours+" m:"+difference_In_Minutes;

            }
            Object o = new Object();
            o = "-";
            Object p = new Object();
            p = "-";
            Object q = new Object();
            q = "-";
            Object r = new Object();
            r = "-";
            Object s = new Object();
            s = "-";
            Object t = new Object();
            t = "-";
            Object u = new Object();
            u = FechaFotografico;
            Object v = new Object();
            v = FechaDigital;
            Object w = new Object();
            w = FechaCF;
            Object x = new Object();
            x = NumeroCorrec;
            Object y = new Object();
            y = NumeroCorrec2;
            Object z = new Object();
            z = Diferencia;
            ValueRange valueRange = new ValueRange();
            valueRange.setValues(
                    Arrays.asList(
                            Arrays.asList(o,p,q,r,s,t,u,v,w,x,y,z)));

            String range3 = "T0"+Datos[10]+"!O"+((Numero))+":Z"+((Numero));

            this.mSheetsService.spreadsheets().values().update(spreadsheetId, range3, valueRange)
                    .setValueInputOption("RAW")
                    .execute();


            Object d = new Object();
            d = "=HIPERVINCULO(\"https://drive.google.com/drive/folders/"+Link+"\")";
            valueRange.setValues(
                    Arrays.asList(
                            Arrays.asList(d)));
            String range6 = "T0"+Datos[10]+"!B"+((Numero))+":B"+((Numero));
            this.mSheetsService.spreadsheets().values().update(spreadsheetId, range6, valueRange)
                    .setValueInputOption("USER_ENTERED")
                    .execute();
            return true;
        });

    }
}
