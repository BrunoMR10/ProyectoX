package com.bmr.xproyect.Datos;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.content.Context;
import android.content.SharedPreferences;

import com.bmr.xproyect.Aduanas;

public class CuadrosActaEntrega {
    SharedPreferences sh = getApplicationContext().getSharedPreferences("ActaEntrega", Context.MODE_PRIVATE);
    String NombreSeguritech = sh.getString("Datos4", "");
    String NombreANAM = sh.getString("Datos6", "");
    String PuestoANAM = sh.getString("Datos7", "");
    String PuestoSeguritech = sh.getString("Datos5", "");
    String CredencialANAM = sh.getString("CredANAM", "");
    String CredencialSeguritech = sh.getString("CredSeguritech", "");
    String Nombreduana = sh.getString("DatosDireccion0", "");
    String Entidad = sh.getString("DatosDireccion1", "");
    String Municipio = sh.getString("DatosDireccion2", "");
    String Calle = sh.getString("DatosDireccion3", "");
    String Colonia = sh.getString("DatosDireccion4", "");
    String CP = sh.getString("DatosDireccion5", "");
    String Direccion = sh.getString("DatosDireccion6", "");
    String Equipo = sh.getString("equipo", "");
    String Fecha =  sh.getString("fecha", "");
    String Hora =  sh.getString("hora", "");
    String Servicio= sh.getString("servicio", "");
    String Texto1 = "SEGURITECH PRIVADA S.A. DE C.V.";
    String Texto2 = "Av. Bosques de Alisos 45B Int. 3 piso. Col. Bosques de las Lomas. Alcaldía Cuajimalpa de Morelos. C.P. 05120 Ciudad de México\n" +
            "R.F.C. SPR950828523 Tel. + 52 55 5083 0000 correo electrónico: atencion.gob@seguritech.com\n" +
            "www.seguritech.com\n";
    String Texto3 = Municipio+", "+Entidad+" a "+Fecha;
    String Texto4 = "La presente acta se celebra con el objeto de dejar constancia de la debida Entrega-Recepción en tiempo, forma y" +
            " a entera satisfacción del Servicio de <neg"+Servicio+"neg>, " +
            "lo anterior en cumplimiento al contrato ANAM/UAF/DRMSG/AD/010/2022, celebrado entre la Agencia Nacional de Aduanas de México (ANAM) y" +
            " Seguritech Privada, S.A. de C.V.,  relativo a la prestación del “Servicio de Atención y Solución de Fallas para equipos móviles de Revisión No Intrusiva " +
            "THSCAN”, numeral 3.1 Componentes del Servicio. ";
    String Texto5 = "En  "+Entidad+", " +Municipio+", siendo las "+Hora+" horas del día "+Fecha+", se reunieron en las instalaciones de la Aduana de "+ Nombreduana +", " +
            "con domicilio en "+Direccion+", " +
            "las personas que se refieren a continuación:";

    String Texto6 = "Por parte de la ANAM, el <negC."+" "+NombreANAM+"neg>, "+PuestoANAM+" , quien se identifica con credencial de la " +
            "ANAM con número de empleado"+" <neg"+CredencialANAM+ "neg> y por parte de la empresa Seguritech Privada, S.A. de C.V., el <negC. " +NombreSeguritech+"neg>, "+PuestoSeguritech+", quien se " +
            "identifica con credencial para votar con número INE"+" <neg"+ CredencialSeguritech+".neg> Para la debida constancia se anexa copia simple de las identificaciones señaladas en donde obran sus " +
            "fotografías y firmas, mismas que concuerdan con sus rasgos físicos. (Anexo 1).";
    String Texto7 = "En primer lugar, las “PARTES” intervinientes procedieron a hacer la inspección física del Servicio:\n";
    String Texto7_1="Y se comprueba la <neg"+Servicio+" y neg>confirman que la implementación queda operando a entera Satisfacción de la ANAM en el equipo <neg"+Equipo+"neg>, adscrito a la Aduana de<neg "+Nombreduana+"neg>\n";

    String Texto8 = "Una vez realizada la entrega y recepción y no existiendo nada más que hacer constar en la presente acta, leída y ratificada en todas y cada una de las partes por los intervinientes, se da por concluida " +
            "firmando de conformidad los que en ella intervienen al margen para su debida constancia.";
    /// 72 Pixeles por pulgada
    String[] TamañoCarta = new String[]{
            "792",//h pagina en pixeles 11 pulgadas 27.94 cm
            "612",//w pagina en pixeles 8.5 pulgadas 21.59 cm
    };


    String[] TamañoOficio = new String[]{
            "1008",//h pagina en pixeles 14 pulgadas 35.56 cm
            "612",//w pagina en pixeles  8.5 pulgadas 21.59 cm
    };
    public String[] CaracPagina = new String[]{
            //Datos Generales
            TamañoCarta[0],//0 h pagina
            TamañoCarta[1],//1 w pagina
            "3",//           2 separación entre cuadros en pixeles
            "30",//          3 margen en pixeles
    };
    public String[] Encabezado = new String[]{
            //Elemento1
            //Parametros de cuadro
            "Logo",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "198",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "80",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "imagen",//Type       13 Tipo de contenido (imagen,text)
            "Logo",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            "Logo",//Text-Nima  15 Texto o nombre de la imagen
            "",//LeterAlig    16 Alineacion de letra
            "",//Tipoletra    17 Tipo de letra

            //Elemento2
            //Parametros de cuadro
            "Text1",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "198",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "10",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,text)
            "7",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            Texto1,//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra
            "bold",//Tipoletra    17 Tipo de letra

            //Elemento3
            //Parametros de cuadro
            "Text2",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "512",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "40",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,text)
            "7",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            Texto2,//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra
            "italic",//Tipoletra    17 Tipo de letra
    };

    public String[] Cuerpo = new String[]{
            //Elemento1
            //Parametros de cuadro
            "Text1",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "right",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "612",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "20",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "12",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            Texto3,//Text-Nima  15 Texto o nombre de la imagen
            "right",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "normal",//Tipoletra    17 Tipo de letra,normal bold, italic

            //Elemento2
            //Parametros de cuadro
            "Text2",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "612",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "20",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "12",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            "ACTA ENTREGA DE SERVICIO",//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra,normal bold, italic

            //Elemento3
            //Parametros de cuadro
            "Text3",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "612",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "115",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "12",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            Texto4,//Text-Nima  15 Texto o nombre de la imagen
            "justified",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "auto",//Tipoletra    17 Tipo de letra,normal bold, italic

            //Elemento4
            //Parametros de cuadro
            "Lineas",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "612",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "41",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "12",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            "----------------------------------------------------------------------------------------------" +
                    "-----------------------------------------------------------------------------------------" +
                    "-------------LUGAR Y FECHA---------------------------------------------------------------------",//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra,normal bold, italic

            //Elemento5
            //Parametros de cuadro
            "Text5",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "612",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "72",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "12",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            Texto5,//Text-Nima  15 Texto o nombre de la imagen
            "justified",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "normal",//Tipoletra    17 Tipo de letra,normal bold, italic

            //Elemento6
            //Parametros de cuadro
            "Lineas2",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "612",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "41",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "12",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            "----------------------------------------------------------------------------------------------" +
                    "---------------------------------------------------------------------------------------" +
                    "----------------INTERVIENEN-------------------------------------------------------------------",//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra,normal bold, italic

            //Elemento7
            //Parametros de cuadro
            "Text7",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "612",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "110",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "12",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            Texto6,//Text-Nima  15 Texto o nombre de la imagen
            "justified",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "auto",//Tipoletra    17 Tipo de letra,normal bold, italic

            //Elemento8
            //Parametros de cuadro
            "Lineas3",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "612",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "41",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "12",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            "----------------------------------------------------------------------------------------------" +
                    "------------------------------------------------------------------------------------------" +
                    "----------------HECHOS------------------------------------------------------------------------",//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra,normal bold, italic

            //Elemento9
            //Parametros de cuadro
            "Text8",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "612",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "20",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "12",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            Texto7,//Text-Nima  15 Texto o nombre de la imagen
            "justified",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "auto",//Tipoletra    17 Tipo de letra,normal bold, italic
            //Elemento9-1
            //Parametros de cuadro
            "Text8_1",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "612",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "20",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "12",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            Servicio,//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra,normal bold, italic

            //Elemento9-2
            //Parametros de cuadro
            "Text8_2",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "612",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "80",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "12",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            Texto7_1,//Text-Nima  15 Texto o nombre de la imagen
            "justified",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "auto",//Tipoletra    17 Tipo de letra,normal bold, italic

            //Elemento10
            //Parametros de cuadro
            "Lineas3",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "612",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "41",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "12",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            "----------------------------------------------------------------------------------------------" +
                    "-------------------------------------------------------------------------------------" +
                    "----------------CIERRE DE ACTA-------------------------------------------------------------------",//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra,normal bold, italic

            //Elemento11
            //Parametros de cuadro
            "Text9",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "612",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "60",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "12",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            Texto8,//Text-Nima  15 Texto o nombre de la imagen
            "justified",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "normal",//Tipoletra    17 Tipo de letra,normal bold, italic

            //Elemento12
            //Parametros de cuadro
            "Lineas4",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "612",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "41",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "12",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            "----------------------------------------------------------------------------------------------" +
                    "------------------------------------------------------------------------------------------" +
                    "-------------LAS PARTES-------------------------------------------------------------------",//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra,normal bold, italic

            //Elemento13
            //Parametros de cuadro
            "Texto10",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "right",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "250",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "300",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "12",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            "\n <neg RECIBE DE ENTERA CONFORMIDAD neg> \n " +
                    "Persona que valida a nombre de la ANAM" +
                    "<neg\n\n\n\n\n\n\n___________________________________\n" +
                    "C. "+NombreANAM+".\n" +PuestoANAM+
                    ".\nANAM neg>",//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "auto",//Tipoletra    17 Tipo de letra,normal bold, italic

            //Elemento14
            //Parametros de cuadro
            "Texto10",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "250",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "300",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "45",//SpaceHori 10 Disntancia en horizontal entre elementos
            "true",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "12",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            "\n ENTREGA"+
                    "\n\n\n\n\n\n\n\n___________________________________\n" +
                    "C. "+NombreSeguritech+".\n" +PuestoSeguritech+
                    ".\nSeguritech Privada, S.A. de C.V.",//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra,normal bold, italic

            //Elemento15
            //Parametros de cuadro
            "space",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "1",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "130",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "12",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            "",//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra,normal bold, italic

            //Elemento16
            //Parametros de cuadro
            "Titulo",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "250",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "60",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "12",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            "Anexo 1\n Credenciales \n\n ANAM",//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra,normal bold, italic

            //Elemento17
            //Parametros de cuadro
            "CuadroFotos",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "500",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "250",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "12",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            "",//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra,normal bold, italic

            //Elemento18
            //Parametros de cuadro
            "space2",//Label      0 Indentificador
            "CuadroFotos",//Parent     1 Si va dentro de algun otro cuadro
            "left",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "60",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "600",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "12",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            "",//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra,normal bold, italic

            //Elemento19
            //Parametros de cuadro
            "Foto1",//Label      0 Indentificador
            "CuadroFotos",//Parent     1 Si va dentro de algun otro cuadro
            "left",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "170",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "320",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "2",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "true",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "true",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "imagen",//Type       13 Tipo de contenido (imagen,texto)
            "Actadeentrga",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            NombreANAM+" Frontal.png",//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra,normal bold, italic

            //Elemento20
            //Parametros de cuadro
            "Foto2",//Label      0 Indentificador
            "CuadroFotos",//Parent     1 Si va dentro de algun otro cuadro
            "left",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "170",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "320",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "50",//Marginint  7 MargenInterno de cuadro
            "50",//Spaceint   8 Espacio entre elementos internos
            "2",//GrosorM    9 Grosor de marco
            "50",//SpaceHori 10 Disntancia en horizontal entre elementos
            "true",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "true",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "imagen",//Type       13 Tipo de contenido (imagen,texto)
            "Actadeentrga",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            NombreANAM+" Trasera.png",//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra,normal bold, italic

            //Elemento21
            //Parametros de cuadro
            "Space3",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "250",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "20",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "12",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            "",//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra,normal bold, italic



            //Elemento23
            //Parametros de cuadro
            "Space4",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "250",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "20",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "0",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "12",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            "",//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra,normal bold, italic

            //Elemento24
            //Parametros de cuadro
            "CuadroFotos2",//Label      0 Indentificador
            "",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "500",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "200",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "30",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "false",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "texto",//Type       13 Tipo de contenido (imagen,texto)
            "12",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            "",//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra,normal bold, italic

            //Elemento25
            //Parametros de cuadro
            "Foto3",//Label      0 Indentificador
            "CuadroFotos2",//Parent     1 Si va dentro de algun otro cuadro
            "left",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "220",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "140",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "0",//Marginint  7 MargenInterno de cuadro
            "0",//Spaceint   8 Espacio entre elementos internos
            "2",//GrosorM    9 Grosor de marco
            "0",//SpaceHori 10 Disntancia en horizontal entre elementos
            "false",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "true",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "imagen",//Type       13 Tipo de contenido (imagen,texto)
            "Actadeentrga",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            NombreSeguritech+" INEFrontal.png",//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra,normal bold, italic

            //Elemento26
            //Parametros de cuadro
            "Foto4",//Label      0 Indentificador
            "CuadroFotos2",//Parent     1 Si va dentro de algun otro cuadro
            "center",//Alignament 2 center,right,left
            "0",//PosX       3 Si no se añadio alineacion
            "0",//PosY       4 Si no se añadio alineacion
            "220",//Weight     5 Ancho de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "140",//Height     6 Altura de cuadro Max (para Maximoposible) Auto(ParaAutomatico en desarrollo no habilitado)
            "10",//Marginint  7 MargenInterno de cuadro
            "50",//Spaceint   8 Espacio entre elementos internos
            "2",//GrosorM    9 Grosor de marco
            "10",//SpaceHori 10 Disntancia en horizontal entre elementos
            "true",//SaltoHori  12 true o false Si es false hace el salto en vertical
            "true",//Marco      12 true o false Si es false oculta el marco
            //Parametros de contenido
            "imagen",//Type       13 Tipo de contenido (imagen,texto)
            "Actadeentrga",//LSize-Ncar 14 Tamaño de letra o ruta de carpeta
            NombreSeguritech+" INETrasera.png",//Text-Nima  15 Texto o nombre de la imagen
            "center",//LeterAlig    16 Alineacion de letra justified,center,left,right
            "bold",//Tipoletra    17 Tipo de letra,normal bold, italic
    };
}
