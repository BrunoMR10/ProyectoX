package com.bmr.xproyect.Datos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Datos {
    ///////Aduanas
    public String[]Aduanas = new String[]{
            "Altamira",//0
            "Ciudad Juárez",//1
            "Reynosa",//2
            "Reynosa 2",//3
            "Coatzacoalcos",//4
            "Colombia",//5
            "DosBocas",//6
            "Ensenada",//7
            "Guaymas",//8
            "Lázaro Cárdenas",//9
            "Manzanillo",//10
            "Matamoros",//11
            "Mazatlán",//12
            "Mexicali",//13
            "México",//14
            "Nogales",//15
            "Nuevo Laredo",//16
            "Ojinaga",//17
            "Progreso",//18
            "Progreso 2",//19
            "Puerto Palomas",//20
            "Salina Cruz",//21
            "Salina Cruz 2",//22
            "San Luis Río Colorado",//23
            "Sonoyta",//24
            "Subteniente Lopez",//25
            "Tampico",//26
            "Tijuana",//27
            "Toluca",//28
            "Tuxpan",//29
            "Veracruz",//30

    };
    public String[] Direcciones = new String[]{
            "Carretera Rios Tamesí Km. 0+720, Puerto Industrial de Altamira, Altamira Tamaulipas C.P. 89600", //Altamira 0
            "Puente Libre de Córdova s/n, Área Chamizal, Ciudad Juárez, Chihuahua, C.P 32310",//Ciudad Juarez 1
            "Puente Fronterizo Río Bravo-Donna, ubicado en Kilómetro 4.87 del Camino Río Bravo-Donna entronque Kilómetro 62+968 de la Carretera Reynosa-Matamoros, Colonia Ejido La Reforma Municipio de Río Bravo, Tamaulipas",//Reynosa2
            "Av Puente Pharr 19, Sin Nombre de Col 21, Reynosa, Tamps.",//Reynosa 3
            "Interior del Recinto Portuario Fiscal s/n Zona Franca, Col. Centro, Coatzacoalcos Veracruz, C.P 96400",//Coatzacoalcos 4
            "Carretera Nuevo Laredo - Piedras Negras Km. 34.5, C.P 65000, Colombia, Nuevo León.",//Colombia 5
            "Carretera Federal Puerto Ceiba - Paraíso 414 Quintín Arauz, 86600 Paraíso, Tabasco",//DosBocas6
            "Boulevard Teniente Azueta, Interior del recinto Portuario s/n, Col. Centro Ensenada B.C. C.P 22800",//Ensenada7
            "Interior Recinto Portuario S/N, Col. Punta de Arena, C.P. 85430, Guaymas Sonora",//Guaymas8
            "Prolongación Av. Lázaro Cárdenas N° 1 Col. Centro Ciudad Lázaro Cárdenas Michoacán, C.P 60950 Terminal de Contenedores APM Terminal",//Lázaro Cardenas 9
            "Avenida Central L-1 Manzana E, Col. Fondeport, C.P. 28219, Manzanillo, Colima",//Manzanillo10
            "Puente Internacional Ignacio Zaragoza Los Tomates Av. Acción Cívica y Av Division del norte s/n col. Doctores C.P 87440 Heroica Matamoros Tamaulipas.",//Matamoros11
            "Avenida Emilio Barragán S/N Colonia Lázaro Cárdenas, CP 82040, Mazatlán, Sinaloa.",//Mazatlan12
            "Blvd. Abelardo L. Rodriguez s/n Col. Los Alamitos, CP 21210, Administracion de la Aduana de Mexicali, B.C.",//Mexicali13
            "Av. Cuitláhuac y Ferrocarril Central s/n , Col. Cosmopolita, Del. Azcapotzalco C.P 02670",//México14
            "Puerto fronterizo Nogales III Nuevo Corredor fiscal Km12",//Nogales15
            "Puente III Carretera Nuevo Laredo-Piedras Negras Km. 12.5 Puente Comercio Mundial Sector Centro C.P. 88000, Nuevo Laredo, Tamps.",//NuevoLaredo16
            "Boulevard Libre Comercio #2401,(Entre Av. Fronteriza y Margen del Río Bravo) Col. Río Bravo, C.P. 32882, Ojinaga, Chih.",//Ojinaga17
            "Cadenamiento 3+200,Muelle Fiscal,Progreso Yucatán, C.P 97320",//Progreso18
            "Rafael Manglar s/n, Muelle Fiscal, Puerto Morelos, Cancun, Q. R., C.P. 77580",//Progreso19
            "Calle Internacional y Ave. 5 de Mayo S/N Col. Centro Puerto Palomas  Municipio de Ascensión Chihuahua C.P 31830",//PuertoPalomas20
            "Av. Tampico 108, Col. Cantarranas C.P 70680 Salina Cruz, Oaxaca.",//SalinaCruz21
            "Calle mesoamericana, lote 3, manzana 2, polígono 2, parque industrial Puerto Chiapas, municipio de Tapachula, C.P 30830",//SalinaCruz22
            "Carretera Federal #2, Km. 12+200, Tramo San Luis Río Colorado - Sonoyta Col. Parque Industrial, Puerto Fronterizo San Luis II C.P. 83455 San Luis Río Colorado, Son.",//San Luis Rio Colorado23
            "Carretera Federal #2 Km.26, Tramo carretero Sonoyta Caborca, Municipio Plutarco Elías Calles.",//Sonoyta24
            "Carretera a Belice km 2, Puente Chactemal, Municipio de Othón P. Blanco, C.P. 77900, Quintana Roo, México.",//SubteninenteLopez25
            "Calzada Blanca s/n, Interior Recinto Fiscal Col. Morelos, Tampico, Tamaulipas, C.P 89290",//Tampico26
            "Avenida Línea Internacional S/N fraccionamiento Mesa de Otay, C.P. 22430, Tijuana,",//Tijuana27
            "Sección San Cayetano Carretera Toluca Atlacomulco",//Toluca28
            "Carretera a la Barra Sur km 8, S/N Recinto Fiscal, C.P 92770, Tuxpan, Veracruz.",//Tuxpan29
            "Carretera a San Juan de Ulúa Km.3.5 Interior del Recinto Fiscal. Col. Centro C.P. 91700, Veracruz, Ver."//Veracruz30
                };

    public String[]Ubicaciones = new String[]{
             "Aduana",
            "Puente Córdoba",
            "Puente Fronterizo Río Bravo - Donna",
            "Reynosa Phar",
            "Recinto Fiscalizado",
            "Punto de Resguardo ",
            "Puente III",
            "Puente II",
            "Puerto Chiapas",
            "San Emeterio",
            "Otay",
            "Seccion aduanera de San Cayetano Morelos."
    };

    public String[] NumerosSerieTHSCAN= new String[]{
            "TFN CK-11067",//Altamira
            "TFN CK-11040",//CiudadJuarez
            "TFN CK-11074",//CiudadJuarez
            "TFN CK-11035",//Reynosa1
            "TFN CK-10945",////Reynosa2
            "TFN CK-11039",//Coatzacoalcos
            "TFN CK-11064",//Colombia
            "TFN CK-11038",//DosBocas
            "TFN CK-11037",//Ensenada
            "TFN CK-11041",//Guaymas
            "TFN CK-11054",//Guaymas
            "TFN CK-11036",//LazaroCardenas
            "TFN CK-11033",//Manzanillo
            "TFN CK-11053",//Manzanillo
            "TFN CK-11060",//Manzanillo
            "TFN CK-11075",//Matamoros
            "TFN CK-11032",//Mazatlán
            "TFN CK-11069",//Mexicalli
            "TFN CK-10941",//México
            "TFN CK-11070",//Nogales
            "TFN CK-10944",//NuevoLaredo
            "TFN CK-11076",//NuevoLaredo
            "TFN CK-11061",//Ojinaga
            "TFN CK-11034",//Progreso
            "TFN CK-11062",//Progreso2
            "TFN CK-11058",//PuertoPalomas
            "TFN CK-11057",//SalinaCruz1
            "TFN CK-11073",//SalinaCruz2
            "TFN CK-11068",//SanRioColorado
            "TFN CK-11063",//Sonoyta
            "TFN CK-10729",//Sonoyta
            "TFN CK-11071",//Subteniente Lopez
            "TFN CK-10943",//Tampico
            "TFN CK-11056",//Tijuana
            "TFN CK-11065",//Toluca
            "TFN CK-10942",//Tuxpan
            "TFN CK-11066",//Tuxpan
            "TFN CK-11059",//Veracruz
            "TFN CK-11072",//Veracruz
    };


    public String[] Datos = new String[]{
            "Nomenclatura",//0
            "Where",//1
            "TipoServicio",//2
            "UID",//3
            "NombreCompleto",//4 (Usuario)
            "Cargo",//5(Usuario)
            "NombreCompletoANAM",//6(ANAM)
            "CargoANAM",//7(ANAM)
            "CorreoElectronico",//8(Usuario,ANAM)
            "Numero",//9(Usuario,ANAM)
            "Trimestre",//10(Preventivo)
            "Periodicidad",//11(Preventivo)
            "Ticket",//12(P,C,I)
            "Aduana",//13(P,C,I)
            "Equipo",//14(P,C,I)
            "Ubicacion",//15(P,C,I)
            "Serie",//16(P,C,I)
            "Fecha Inicio",//17(P,C,I)
            "Hora Incio",//18(P,C,I)
            "Fecha Fin",//19(P,C,I)
            "Hora Fin",//20(P,C,I)
            "Falla",//21(Correctivo)
            "Fecha llamada",//22(Correctivo)
            "Hora de llamada",//23(Correctivo)
            "Fecha Sitio",//24(Correctivo)
            "Hora Sitio",//25(Correctivo)
            "Nombre tecnico",//26(Correctivo)
            "ID",//27(AgregarFoto)
            "Comentario",//28(AgregarFoto)
            "Identificador Nomenclatura"//29(PDF View)
    };

    //////// REPORTE FOTOGRAFICO PREVENTIVO/////
    public String[] ComentariosRFPMensual = new String[]{
            "Panorámica, brazo replegado.",
            "Panorámica, brazo desplegado.",
            "Verificar el funcionamiento de operación del sistema.",
            "Limpieza de sensores.",
            "Nivel de aceite hidráulico.",
            "Indicadores del panel del modulador.",
            "Verificación del sistema de aire acondicionado.",
            "Conteo de inspecciones."
    };
    public String[] ComentariosRFPTrimestral = new String[]{
            "Panorámica, brazo replegado.",
            "Panorámica, brazo desplegado.",
            "Limpieza de lentes de las cámaras.",
            "Limpieza de luces de iluminación.",
            "Limpieza de sensor de cortina.",
            "Verificar luces de cabina de control.",
            "Revisión de niveles de aceite del vehículo VOLVO.",
            "Revisión del nivel de agua del vehículo VOLVO.",
            "Revisión del generador eléctrico.",
            "Conteo de inspecciones."
    };
    public String[] ComentariosRFPAnual = new String[]{
            "Panorámica, brazo replegado.",
            "Panorámica, brazo desplegado.",
            "Revisión de niveles al chasis VOLVO.",
            "Pruebas de sistema de bloqueo de seguridad.",
            "Verificación del subsistema del acelerador.",
            "Verificación del subsistema del modulador.",
            "Revisión y calibración del sistema hidráulico.",
            "Mantenimiento al generador eléctrico.",
            "Limpieza y pruebas a los módulos de los detectores.",
            "Conteo de inspecciones."
    };


    /////////REPORTE DIGITAL PREVENTIVO//////
    public String[] ComentariosRDP= new String[]{
            "(1) Verificar todas las funciones de operación del sistema y asegurarse que esten en condiciones normales.",
            "(2) Verificar el funcionamiento del sistema de aire acondicionado, asegurarse de que esté en condiciones normales.",
            "(3) Verificar si hay fuga de aceite del sistema hidráulico.",
            "(4) Probar los indicadores en el panel del modulador.",
            "(5) Registrar las operaciones antes mencionadas.",
            "",
            "(1) Limpiar el lente de las cámaras.",
            "(2) Limpiar las luces de iluminación del área de trabajo.",
            "(3) Limpiar el lente de los sensores de infrarrojos para la detección de intrusos a la zona controlada.",
            "(4) Verificar las luces de alumbrado en la cabina de control.",
            "(5) Verificar si la presión del SF6 es menor de 0.2 Mpa.",
            "(6) Verificar que el transformador de impulsos no presente fugas de aceite.",
            "(7) Verificar que los condensadores de la PFN no presenten fugas de aceite.",
            "(8) Verificar que el funcionamiento del sistema de refrigeración de agua y que el nivel de agua sea el correcto.",
            "(9) Verificar el funcionamiento del sistema hidráulico y que el nivel de aceite sea el correcto.",
            "(10) Verificar los factores de la calibración de los detectores de radiación y respardarlas.",
            "(11) Verificar el espacio libre en discos OIS. Si se utilizó el 80 porciento del espacio disponible, por favor haga copia de seguridad de la información útil y elimine los archivos inutiles.",
            "(12) Verificar los cables de la fuente de alimentación del vehiculo de trabajo con la perspectiva de envejecimiento y daños.",
            "(13) Revisar niveles de aceite y agua del camión VOLVO.",
            "(14) Verificar el correcto funcionamiento del generador (planta de emergencia).",
            "(15) Registrar las operaciones antes mencionadas.",
            "",
            "(1) Realizar pruebas del subsistema del Acelerador y del modulador.",
            "(2) Fijar adecuadamente todos los accesorios de los cables de alta tensión en el subsistema del acelerador.",
            "(3) Realizar pruebas del sistema de bloqueo de seguridad.",
            "(4) Realizar pruebas de la simulación de error en la temperatura del agua en el subsitema agua en el subsistema del acelerador.",
            "(5) Realizar pruebas de la simulación de error en la presión de gas en el subsistema del acelerador.",
            "(6) Limpiar y probar los módulos de los detectores.",
            "(7) Limpiar y probar el subsistema de adquisición de datos de imagen.",
            "(8) Limpiar y probar el subsistema de control de escaneado.",
            "(9) Verificar que el gabinete del acelerador y el brazo de los detectores verticales y horizontales no tengan fisura y/o daño.",
            "(10) Limpiar y calibrar el sistema hidáhulico.",
            "(11) Calibrar el disco del \"clutch\".",
            "(12) Verificar la conexión entre el disco del \"clutch\" y el acoplamiento reductor.",
            "(13) Lubricar conexiones y juntas en los bastidores del vehículo de escaneado.",
            "(14) Revisar niveles de aceite y agua del camión VOLVO.",
            "(15) Verificar el correcto funcionamiento del generador (planta de emergencia).",
            "(16) Registrar las operaciones antes mencionadas."
    };

    public String[]ComentariosRDPDef = new String[]{

            "Funciones verificadas.",//0
            "Verificado y en condiciones normales.",//1
            "No presenta fugas.",//2
            "Indicadores probados.",//3
            "Registradas.",//4
            "",//5
            "Lentes limpias.",//6
            "Luces limpias.",//7
            "Sensores limpios.",//8
            "Luces verificadas.",//9
            "Presión verificada.",//10
            "Sin fugas.",//11
            "Sin fugas.",//12
            "Nivel de agua correcto.",//13
            "Nivel de aceite correcto.",//14
            "Verificado.",//15
            "Espacio verificado.",//16
            "Verificados y en buen estado.",//17
            "Niveles revisados.",//18
            "Verificado.",//19
            "Registradas.",//20
            "",//21
            "Pruebas realizadas.",//21
            "Cables fijos.",//22
            "Pruebas realizadas.",//23
            "Pruebas realizadas.",//24
            "Pruebas realizadas.",//25
            "Modulos limpios.",//26
            "Prueba realizada.",//27
            "Prueba realizada.",//28
            "Verificado.",//29
            "Limpio y calibrado.",//30
            "Calibrado.",//31
            "Conexión verificada.",//32
            "Conexiones lubricadas.",//33
            "Revisados.",//34
            "Verificado.",//35
            "Registradas."//36


    };

    public String[] DatosSitio(String DatosSitio,String Periodicidad){
        String[]Datos;
        String Serie,Aduana,Ubicacion,DatosSitioNew;
        int PosIn,PosFin;
        if (DatosSitio.equals("Dato no encontrado")){
            Aduana = "Dato no encontrado";
            Serie = "Dato no encontrado";
            Ubicacion = "Dato no encontrado";
            Periodicidad = "Dato no encontrado";
        }
        else{
            DatosSitioNew = DatosSitio.replaceAll("(\n|\r)", "");
            PosIn = DatosSitioNew.indexOf("a:");
            PosFin =DatosSitioNew.indexOf("Ubica");
            Aduana = DatosSitioNew.substring(PosIn+2,PosFin);
            PosIn = DatosSitioNew.indexOf("n:");
            PosFin =DatosSitioNew.indexOf("Serie");
            Ubicacion = DatosSitioNew.substring(PosIn+2,PosFin-3);
            PosIn = DatosSitioNew.indexOf("e:");
            PosFin =DatosSitioNew.length();
            Serie = DatosSitioNew.substring(PosIn+2,PosFin);
            if (Periodicidad.equals("")){
                 Periodicidad="";
            }else{
                PosFin = Periodicidad.indexOf("al");
                Periodicidad = Periodicidad.substring(14, PosFin + 2).replaceAll("\\s", "");
            }


        }
        Datos = new String[]{
                Aduana,
                Ubicacion,
                Serie,
                Periodicidad
        };
        return Datos;
    }
    public String[] FechasCorrectivo(String FechaInicio,String FechaSitio,String FechaFin,String Llamada){
        String[] Fechas;
        String Fechainicio,HoraInicio,Fechasitio,HoraSitio,Fechafin,HoraFin,FechaLLamada,HoraLLamada,Tecnico;

        int PosInicio;
        int PosFin;
        try{
            //PosFin = FechaInicio.indexOf("\\s");
            Fechainicio = FechaInicio.substring(0,11);
            //PosInicio = FechaInicio.indexOf("\\s");
            PosFin = FechaInicio.length();
            HoraInicio=FechaInicio.substring(11,PosFin);
            System.out.println(Fechainicio);

        }catch (Exception e){
            Fechainicio = "Dato no encontrado";
            HoraInicio = "Dato no encontrado";
        }
        try{
            //PosFin = FechaSitio.indexOf("\\s");
            Fechasitio = FechaSitio.substring(0,11);
            //PosInicio = FechaSitio.indexOf("\\s");
            PosFin = FechaSitio.length();
            HoraSitio=FechaSitio.substring(11,PosFin);
            System.out.println(Fechasitio);
        }catch (Exception e){
            Fechasitio = "Dato no encontrado";
            HoraSitio = "Dato no encontrado";
        }
        try{
            //PosFin = FechaFin.indexOf("\\s");
            Fechafin = FechaFin.substring(0,11);
            //PosInicio = FechaFin.indexOf("\\s");
            PosFin = FechaFin.length();
            HoraFin=FechaFin.substring(11,PosFin);
            System.out.println(HoraFin);
        }catch (Exception e){
            Fechafin = "Dato no encontrado";
            HoraFin = "Dato no encontrado";
        }

        try{
            Llamada = Llamada.replaceAll("(\n|\r)", "");
            String FechaHoraLLamada;
            PosInicio = Llamada.indexOf("a:");
            PosFin = Llamada.indexOf("Hrs");
            FechaHoraLLamada = Llamada.substring(PosInicio+2,PosFin+3)+".";
            /////
            PosInicio = Llamada.indexOf("co:");
            PosFin = Llamada.indexOf("Con q");
            Tecnico = Llamada.substring(PosInicio+3,PosFin);
            /////
            //PosFin = FechaHoraLLamada.indexOf("\\s");
            FechaLLamada = FechaHoraLLamada.substring(0,11);
            //PosInicio = FechaHoraLLamada.indexOf("\\s");
            PosFin = FechaHoraLLamada.length();
            HoraLLamada=FechaHoraLLamada.substring(11,PosFin);
            System.out.println(HoraLLamada);
            System.out.println(Tecnico);
            System.out.println(FechaLLamada);
        }catch (Exception e){
                    FechaLLamada="Dato no encontrado";
                    HoraLLamada="Dato no encontrado";
                    Tecnico="Dato no encontrado";
        }
        ///


        Fechas = new String[]{
                Fechainicio,
                HoraInicio,
                Fechafin,
                HoraFin,
                FechaLLamada,
                HoraLLamada,
                Fechasitio,
                HoraSitio,
                Tecnico
        };




        return Fechas;
    }
    public String[] FechasInterno(String FechaInicio,String FechaSitio,String FechaFin){
        String[] Fechas;
        String Fechainicio,HoraInicio,Fechasitio,HoraSitio,Fechafin,HoraFin,FechaLLamada,HoraLLamada,Tecnico;

        int PosInicio;
        int PosFin;
        try{
            //PosFin = FechaInicio.indexOf("\\s");
            Fechainicio = FechaInicio.substring(0,11);
            //PosInicio = FechaInicio.indexOf("\\s");
            PosFin = FechaInicio.length();
            HoraInicio=FechaInicio.substring(11,PosFin);
        }catch (Exception e){
            Fechainicio = "Dato no encontrado";
            HoraInicio = "Dato no encontrado";
        }
        try{
            //PosFin = FechaSitio.indexOf("\\s");
            Fechasitio = FechaSitio.substring(0,11);
            //PosInicio = FechaSitio.indexOf("\\s");
            PosFin = FechaSitio.length();
            HoraSitio=FechaSitio.substring(11,PosFin);
        }catch (Exception e){
            Fechasitio = "Dato no encontrado";
            HoraSitio = "Dato no encontrado";
        }
        try{
            //PosFin = FechaFin.indexOf("\\s");
            Fechafin = FechaFin.substring(0,11);
            //PosInicio = FechaFin.indexOf("\\s");
            PosFin = FechaFin.length();
            HoraFin=FechaFin.substring(11,PosFin);
        }catch (Exception e){
            Fechafin = "Dato no encontrado";
            HoraFin = "Dato no encontrado";
        }



        Fechas = new String[]{
                Fechainicio,
                HoraInicio,
                Fechafin,
                HoraFin,
                Fechasitio,
                HoraSitio,
        };




        return Fechas;
    }
    public String[] NomenclaturaPreventivo(String[] Datos){;
        String Nomenclatura;
        int PosIn,PosFin;
        SimpleDateFormat parseador = new SimpleDateFormat("dd-MM-yy");
        SimpleDateFormat formateador = new SimpleDateFormat("MM");
        SimpleDateFormat formateador2 = new SimpleDateFormat("yy");
        SimpleDateFormat formateador3 = new SimpleDateFormat("MMMyy");

        Date date = null;
        try {
            date = parseador.parse(Datos[17]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int fecha = Integer.parseInt(formateador.format(date));
        int año = Integer.parseInt(formateador2.format(date));
        String Clave = String.valueOf(fecha)+String.valueOf(año);
        System.out.println(año);
        System.out.println(fecha);
        System.out.println(Clave);
        String Trimestre = "";
        /*if ((fecha>11 || fecha<3)){
            Trimestre = "03";
        }
        else if (fecha>2 && fecha<6){
            Trimestre = "04";
        }
        else if (fecha>5 && fecha<9){
            Trimestre = "01";
        }
        else if (fecha>8 && fecha<12){
            Trimestre = "02";
        }*/

        if (Clave.equals("622") || Clave.equals("722")||Clave.equals("822")){
            Trimestre = "01";
        }
        else if (Clave.equals("922") || Clave.equals("1022")||Clave.equals("1122")){
            Trimestre = "02";
        }
        else if (Clave.equals("1222") || Clave.equals("123")||Clave.equals("223")){
            Trimestre = "03";
        }
        else if (Clave.equals("323") || Clave.equals("423")||Clave.equals("523")){
            Trimestre = "04";
        }
        else if (Clave.equals("623") || Clave.equals("723")||Clave.equals("823")){
            Trimestre = "05";
        }
        else if (Clave.equals("923") || Clave.equals("1023")||Clave.equals("1123")){
            Trimestre = "06";
        }
        else if (Clave.equals("1223") || Clave.equals("124")||Clave.equals("224")){
            Trimestre = "07";
        }
        else if (Clave.equals("324") || Clave.equals("424")||Clave.equals("524")){
            Trimestre = "08";
        }

        String Perio = Datos[11].substring(0,1);
        Nomenclatura = Datos[13]+Datos[16]+"-Mtto"+Perio+"-T"+Trimestre+"-"+formateador3.format(date);
        Nomenclatura=Nomenclatura.replaceAll("TFN CK-", "-");
        String[] DatosNomenclatura = new String[]{
                Trimestre,
                Nomenclatura
        };

        return DatosNomenclatura;
    }
    public String[] NomenclaturaCorrectivo(String[] Datos){;
        String Nomenclatura;
        int PosIn,PosFin;
        SimpleDateFormat parseador = new SimpleDateFormat("dd-MM-yy");
        SimpleDateFormat formateador = new SimpleDateFormat("MM");
        SimpleDateFormat formateador2 = new SimpleDateFormat("yy");
        SimpleDateFormat formateador3 = new SimpleDateFormat("MMMyy");

        Date date = null;
        try {
            date = parseador.parse(Datos[17]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int fecha = Integer.parseInt(formateador.format(date));
        int año = Integer.parseInt(formateador2.format(date));
        String Clave = String.valueOf(fecha)+String.valueOf(año);
        System.out.println(año);
        System.out.println(fecha);
        System.out.println(Clave);
        String Trimestre = "";


        if (Clave.equals("622") || Clave.equals("722")||Clave.equals("822")){
            Trimestre = "01";
        }
        else if (Clave.equals("922") || Clave.equals("1022")||Clave.equals("1122")){
            Trimestre = "02";
        }
        else if (Clave.equals("1222") || Clave.equals("123")||Clave.equals("223")){
            Trimestre = "03";
        }
        else if (Clave.equals("323") || Clave.equals("423")||Clave.equals("523")){
            Trimestre = "04";
        }
        else if (Clave.equals("623") || Clave.equals("723")||Clave.equals("823")){
            Trimestre = "05";
        }
        else if (Clave.equals("923") || Clave.equals("1023")||Clave.equals("1123")){
            Trimestre = "06";
        }
        else if (Clave.equals("1223") || Clave.equals("124")||Clave.equals("224")){
            Trimestre = "07";
        }
        else if (Clave.equals("324") || Clave.equals("424")||Clave.equals("524")){
            Trimestre = "08";
        }

        Nomenclatura = Datos[13]+Datos[16]+"-"+Datos[12]+"-T"+Trimestre+"-"+formateador3.format(date);
        Nomenclatura=Nomenclatura.replaceAll("TFN CK-", "-");
        if (Nomenclatura.contains("*")){
            Nomenclatura=Nomenclatura.replaceAll("\\*", "");
        }
        String[] DatosNomenclatura = new String[]{
                Trimestre,
                Nomenclatura
        };

        return DatosNomenclatura;
    }
    public String[] NomenclaturaInterno(String[] Datos){;
        String Nomenclatura;
        int PosIn,PosFin;
        SimpleDateFormat parseador = new SimpleDateFormat("dd-MM-yy");
        SimpleDateFormat formateador = new SimpleDateFormat("MM");
        SimpleDateFormat formateador2 = new SimpleDateFormat("yy");
        SimpleDateFormat formateador3 = new SimpleDateFormat("MMMyy");

        Date date = null;
        try {
            date = parseador.parse(Datos[17]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int fecha = Integer.parseInt(formateador.format(date));
        int año = Integer.parseInt(formateador2.format(date));
        String Clave = String.valueOf(fecha)+String.valueOf(año);
        System.out.println(año);
        System.out.println(fecha);
        System.out.println(Clave);
        String Trimestre = "";


        if (Clave.equals("622") || Clave.equals("722")||Clave.equals("822")){
            Trimestre = "01";
        }
        else if (Clave.equals("922") || Clave.equals("1022")||Clave.equals("1122")){
            Trimestre = "02";
        }
        else if (Clave.equals("1222") || Clave.equals("123")||Clave.equals("223")){
            Trimestre = "03";
        }
        else if (Clave.equals("323") || Clave.equals("423")||Clave.equals("523")){
            Trimestre = "04";
        }
        else if (Clave.equals("623") || Clave.equals("723")||Clave.equals("823")){
            Trimestre = "05";
        }
        else if (Clave.equals("923") || Clave.equals("1023")||Clave.equals("1123")){
            Trimestre = "06";
        }
        else if (Clave.equals("1223") || Clave.equals("124")||Clave.equals("224")){
            Trimestre = "07";
        }
        else if (Clave.equals("324") || Clave.equals("424")||Clave.equals("524")){
            Trimestre = "08";
        }

        Nomenclatura = Datos[12]+"-"+Datos[13]+Datos[16]+"-T"+Trimestre+"-"+formateador3.format(date);
        Nomenclatura=Nomenclatura.replaceAll("TFN CK-", "-");
        String[] DatosNomenclatura = new String[]{
                Trimestre,
                Nomenclatura
        };

        return DatosNomenclatura;
    }
}
