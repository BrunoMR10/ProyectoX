package com.bmr.xproyect.Documentos;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.bmr.xproyect.Datos.Datos;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class Preventivo {
    Datos dt = new Datos();
    ///////////////////////////////RFP/////////////////////////////////////////////////////////
    public void CreaArchivo(String[] Datos,ImageData imageData,String[]Descripcion,int id,String[]Comentarios)throws IOException {
        String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + java.io.File.separator;
        String rutacarpeta = "ProyectoX/"+"Preventivo"+"/" + Datos[0] + "/";
        String rutacarpetaImagenes = "ProyectoX/"+"Preventivo"+"/" + Datos[0] + "/" + Datos[0] + "(CF)/";;
        String Name = Datos[0]+"(RFD).pdf";
        String DEST = ExternalStorageDirectory+rutacarpeta+Name;
        PdfDocument pdf = new PdfDocument(new PdfWriter(DEST));
        PdfPage page = pdf.addNewPage();
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
        CuadroDatos(Datos,pdfCanvas,font,bold,36, 700, 530, 100,imageData);
        CuadroDatos2(Datos,pdfCanvas,font,bold,36, 615, 530, 50);
        AñadirLineas(page);
        PdfPage page2=CuadroNextPage(pdf,Datos,font,bold,imageData);
        if (Datos[11].equals("Mensual")){
            InsertaFotosMensual(page,page2,rutacarpetaImagenes,font,bold,Descripcion,Datos);
        }
        else{
            InsertaFotosTrimestralAnual(page,page2,rutacarpetaImagenes,font,bold,Descripcion,Datos);
        }
        FotosExtra2(pdf,Datos,font,bold,imageData,id,Comentarios,rutacarpetaImagenes);
        InsertaPiedePagina(pdf);
        pdf.close();
    }
    private void CuadroDatos(String[] Datos,PdfCanvas pdfCanvas,PdfFont font,PdfFont bold,int x,int y,int w,int h,ImageData imageData){
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text title = new Text("REPORTE FOTOGRÁFICO DE MANTENIMIENTO PREVENTIVO").setFont(bold).setFontColor(ColorConstants.RED).setFontSize(10);
        Text Trimestre = new Text("Trimestre de Servicio").setFont(font).setFontSize(9);
        Text NReporte = new Text("No de reporte:").setFont(font).setFontSize(9);
        Text Fecha = new Text("Fecha:").setFont(font).setFontSize(9);
        Text Periodicidad = new Text("Periodicidad del mantenimiento:").setFont(font).setFontSize(9);
        Text Trim = new Text("0"+Datos[10]).setFont(font).setUnderline().setFontSize(9);
        Text TICKET = new Text(Datos[12]).setFont(font).setFontSize(9);
        Text FECHA = new Text(Datos[17]).setFont(font).setFontSize(9);
        Text PERIODICIDAD = new Text(Datos[11]).setFont(font).setFontSize(9);

        Text SaltoLinea = new Text(" \n ").setFont(font).setFontSize(9);
        Text Space = new Text(".  .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);

        Paragraph a = new Paragraph().add(title).add(Space).setTextAlignment(TextAlignment.RIGHT);
        /*Paragraph b = new Paragraph().add(Trim).add(Space).add(Trimestre).add(Space).add(Space).add(Space).add(SaltoLinea)
                .add(NReporte).add(Space).add(TICKET).add(Space).add(Space).add(Space).add(SaltoLinea)
                .add(Fecha).add(Space).add(FECHA).add(Space).add(Space).add(Space).add(Space).add(Space).add(SaltoLinea)
                .add(Periodicidad).add(Space).add(PERIODICIDAD).add(Space).add(Space).add(Space).add(Space).add(Space).add(Space)
                .setTextAlignment(TextAlignment.RIGHT);*/
        InsertaImagenLogo(imageData,canvas,50,715,190);
        canvas.add(a);
        //canvas.add(b);
        canvas.close();

    }
    private void CuadroDatos2(String[] Datos,PdfCanvas pdfCanvas,PdfFont font,PdfFont bold,int x,int y, int w,int h){
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text Aduana = new Text("Aduana:").setFont(bold).setFontSize(9);
        Text Equipo = new Text("Equipo:").setFont(bold).setFontSize(9);
        Text Ubicacion = new Text("Ubicación del equipo:").setFont(bold).setFontSize(9);
        Text NoSerie = new Text("No de Serie:").setFont(bold).setFontSize(9);
        Text ADUANA = new Text(Datos[13]).setFont(font).setFontSize(9);
        Text EQUIPO = new Text(Datos[14]).setFont(font).setFontSize(9);
        Text UBI = new Text(Datos[15]).setFont(font).setFontSize(9);
        Text NOSERIE = new Text(Datos[16]).setFont(font).setFontSize(9);
        Text SaltoLinea = new Text(" \n ").setFont(font).setFontSize(9);
        Text Space = new Text(".  .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);
        Text Space3 = new Text(". .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);
        Text Space2 = new Text(".                                                .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);

        /*Paragraph a = new Paragraph().add(Space).add(Aduana).add(Space).add(Space).add(Space).add(Space).add(Space).add(Space).add(Space3).add(ADUANA).add(Space).add(SaltoLinea)
                .add(Space).add(Equipo).add(Space).add(Space).add(Space).add(Space).add(Space).add(Space).add(Space3).add(EQUIPO).add(Space2)
                .add(Space).add(NoSerie).add(Space).add(Space).add(NOSERIE).add(SaltoLinea)
                .add(Space).add(Ubicacion).add(Space).add(UBI).add(Space)
                .add(Space).setTextAlignment(TextAlignment.LEFT);

        canvas.add(a);*/
        Rectangle rectangle2 = new Rectangle(x, y+h, w, 15);
        pdfCanvas.rectangle(rectangle2);
        pdfCanvas.stroke();
        Canvas canvas2 = new Canvas(pdfCanvas, rectangle2);
        Text DatosEquipo = new Text("Datos del equipo").setFont(bold).setFontSize(9);
        Paragraph b = new Paragraph().add(DatosEquipo).setTextAlignment(TextAlignment.CENTER);
        canvas2.add(b);
        canvas.close();
        canvas2.close();
    }
    private PdfPage CuadroNextPage(PdfDocument pdf,String [] Datos,PdfFont font,PdfFont bold,ImageData imageData){
        PdfPage nextpage = pdf.addNewPage();
        PdfCanvas pdfCanvas = new PdfCanvas(nextpage);
        Rectangle rectangle = new Rectangle(36, 700, 530, 100);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text NReporte = new Text("No de reporte:").setFont(font).setFontSize(9);
        Text title = new Text("REPORTE FOTOGRÁFICO DE MANTENIMIENTO PREVENTIVO").setFont(bold).setFontColor(ColorConstants.RED).setFontSize(10);
        Text TICKET = new Text(Datos[12]).setFont(font).setUnderline().setFontSize(9);
        Text Space = new Text(".  .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);
        Paragraph a = new Paragraph().add(title).add(Space).setTextAlignment(TextAlignment.RIGHT);
        Paragraph b = new Paragraph().add(NReporte).add(Space).add(TICKET).add(Space).add(Space).add(Space).setTextAlignment(TextAlignment.RIGHT);
        //InsertaImagen("FotosBase/", "seguritechlogo.png",canvas,50,715,190,142);
        InsertaImagenLogo(imageData,canvas,50,715,190);
        canvas.add(a);
        canvas.add(b);
        canvas.close();
        return (nextpage);
    }
    private void AñadirLineas(PdfPage page){
        PdfCanvas canvas = new PdfCanvas(page);

        // Create a 100% Magenta colo
        canvas
                .moveTo(135, 650)
                .lineTo(565, 650)
                .moveTo(135, 637)
                .lineTo(565, 637)
                .moveTo(135, 623)
                .lineTo(565, 623)
                .moveTo(475, 722)
                .lineTo(540, 722)
                .moveTo(475, 735)
                .lineTo(540, 735)
                .moveTo(475, 748)
                .lineTo(540, 748)
                .moveTo(462, 762)
                .lineTo(470, 762)
                .setLineWidth(0).closePathStroke();

    }
    private void InsertaImagenLogo(ImageData imageData,Canvas canvas,int left,int bottom,int width){
        Image img = new Image(imageData,left,bottom,width);
        canvas.add(img);
    }
    private void InsertaPiedePagina(PdfDocument pdf ){
        int numberOfPages = pdf.getNumberOfPages();
        for (int i = 1; i <= numberOfPages; i++) {
            Document doc = new Document(pdf);
            // Write aligned text to the specified by parameters point
            doc.showTextAligned(new Paragraph(String.format("Página %s de %s", i, numberOfPages)),
                    559, 50, i, TextAlignment.RIGHT, VerticalAlignment.TOP, 0);
        }
    }
    private void InstertaDatosFotos(PdfFont font,PdfPage page,PdfFont bold,String[]Datos){
        AgregaContenidoCuadroDatos(bold, page, 370, 764, 100, 11, "0"+Datos[10],8);
        AgregaContenidoCuadroDatos(bold, page, 370, 750, 100, 11, "No. de reporte:",8);
        AgregaContenidoCuadroDatos(bold, page, 370, 736, 100, 11,"Fecha:" ,8);
        AgregaContenidoCuadroDatos(bold, page, 350, 723, 120, 11, "Periodicidad del mantenimiento:",8);
        AgregaContenido(font, page, 480, 764, 70, 11, "Trimestre de servicio",8);


        AgregaContenido(font, page, 480, 750, 70, 11, Datos[12],8);
        AgregaContenido(font, page, 480, 736, 70, 11, Datos[19],8);
        AgregaContenido(font, page, 480, 723, 70, 11, Datos[11],8);


        AgregaContenido(bold, page, 55, 650, 80, 11, "Aduana:",8);
        AgregaContenido(bold, page, 55, 638, 80, 11, "Equipo:",8);
        AgregaContenido(bold, page, 300, 638, 80, 11, "No. de serie:",8);
        AgregaContenido(bold, page, 55, 626, 80, 11, "Ubicación del equipo:",8);
        AgregaContenido(font, page, 139, 651, 400, 11, Datos[13],8);
        AgregaContenido(font, page, 139, 638, 140, 11, Datos[14],8);
        AgregaContenido(font, page, 360, 638, 199, 11, Datos[16],8);
        AgregaContenido(font, page, 139, 625, 400, 11, Datos[15],8);
    }
    private void InsertaFotosMensual(PdfPage page,PdfPage page2,String rutacarpeta,PdfFont font,PdfFont bold,String [] Descripcion,String[]Datos){
        InsertaBordeImagen(page, 50,380,230,172);
        InsertaBordeImagen(page, 320,380,230,172);
        InsertaBordeImagen(page, 50,160,230,172);
        InsertaBordeImagen(page, 320,160,230,172);

        InsertaBordeImagen(page2, 50,470,230,172);
        InsertaBordeImagen(page2, 320,470,230,172);
        InsertaBordeImagen(page2, 50,230,230,172);
        InsertaBordeImagen(page2, 320,230,230,172);

        InstertaDatosFotos(font,page,bold,Datos);

        AgregaComentarios(font,page,50, 330, 232, 45,Descripcion[0]);
        AgregaComentarios(font,page,320,330,230,45,Descripcion[1]);
        AgregaComentarios(font,page,50,110,230,45,Descripcion[2]);
        AgregaComentarios(font,page,320,110,230,45,Descripcion[3]);

        AgregaComentarios(font,page2,50, 420, 232, 45,Descripcion[4]);
        AgregaComentarios(font,page2,320,420,230,45,Descripcion[5]);
        AgregaComentarios(font,page2,50,180,230,45,Descripcion[6]);
        AgregaComentarios(font,page2,320,180,230,45,Descripcion[7]);

        InsertImagen (page, 50,380,230,172,rutacarpeta,"foto1"+".png");
        InsertImagen (page, 320,380,230,172,rutacarpeta,"foto2"+".png");
        InsertImagen (page, 50,160,230,172,rutacarpeta,"foto3"+".png");
        InsertImagen (page, 320,160,230,172,rutacarpeta,"foto4"+".png");

        InsertImagen (page2, 50,470,230,172,rutacarpeta,"foto5"+".png");
        InsertImagen (page2, 320,470,230,172,rutacarpeta,"foto6"+".png");
        InsertImagen (page2, 50,230,230,172,rutacarpeta,"foto7"+".png");
        InsertImagen (page2, 320,230,230,172,rutacarpeta,"foto8"+".png");
    }
    private void InsertaFotosTrimestralAnual(PdfPage page,PdfPage page2,String rutacarpeta,PdfFont font,PdfFont bold,String [] Descripcion,String[]Datos){


        InstertaDatosFotos(font,page,bold,Datos);

        InsertaBordeImagen(page, 50,380,230,172);
        InsertaBordeImagen(page, 320,380,230,172);
        InsertaBordeImagen(page, 50,160,230,172);
        InsertaBordeImagen(page, 320,160,230,172);

        InsertaBordeImagen(page2, 59, 509, 212, 144);
        InsertaBordeImagen(page2, 329, 509, 212, 144);
        InsertaBordeImagen(page2, 59, 309, 212, 144);
        InsertaBordeImagen(page2, 329, 309, 212, 144);
        InsertaBordeImagen(page2, 59, 109, 212, 144);
        InsertaBordeImagen(page2, 329, 109, 212, 144);


        AgregaComentarios(font,page,50, 330, 232, 45,Descripcion[0]);
        AgregaComentarios(font,page,320,330,230,45,Descripcion[1]);
        AgregaComentarios(font,page,50,110,230,45,Descripcion[2]);
        AgregaComentarios(font,page,320,110,230,45,Descripcion[3]);

        AgregaComentarios(font, page2, 60, 460, 212, 50, Descripcion[4]);
        AgregaComentarios(font, page2, 330, 460, 212, 50, Descripcion[5]);
        AgregaComentarios(font, page2, 60, 260, 212, 50, Descripcion[6]);
        AgregaComentarios(font, page2, 330, 260, 212, 50, Descripcion[7]);
        AgregaComentarios(font, page2, 60, 60, 212, 50, Descripcion[8]);
        AgregaComentarios(font, page2, 330, 60, 212, 50, Descripcion[9]);

        InsertImagen (page, 50,380,230,172,rutacarpeta,"foto1"+".png");
        InsertImagen (page, 320,380,230,172,rutacarpeta,"foto2"+".png");
        InsertImagen (page, 50,160,230,172,rutacarpeta,"foto3"+".png");
        InsertImagen (page, 320,160,230,172,rutacarpeta,"foto4"+".png");

        InsertImagen(page2, 59, 509, 212, 144, rutacarpeta, "foto5"+".png");
        InsertImagen(page2, 329, 509, 212, 144, rutacarpeta, "foto6"+".png");
        InsertImagen(page2, 59, 309, 212, 144, rutacarpeta, "foto7"+".png");
        InsertImagen(page2, 329, 309, 212, 144, rutacarpeta, "foto8"+".png");
        InsertImagen(page2, 59, 109, 212, 144, rutacarpeta, "foto9"+".png");
        InsertImagen(page2, 329, 109, 212, 144, rutacarpeta, "foto10"+".png");

    }
    private void InsertaFotosextra(PdfPage page,int Numero,String rutacarpeta,int extra,PdfFont font,String [] Descripcion) {

        int Identificador = extra * 6;
        int Foto1 = Identificador + 1;
        int Foto2 = Identificador + 2;
        int Foto3 = Identificador + 3;
        int Foto4 = Identificador + 4;
        int Foto5 = Identificador + 5;
        int Foto6 = Identificador + 6;
        if (Numero == 1) {
            InsertaBordeImagen(page, 59, 509, 212, 144);
            AgregaComentarios(font, page, 60, 457, 212, 50, Descripcion[Foto1 - 1]);
            InsertImagen(page, 59, 509, 212, 144, rutacarpeta, "Extra" + Foto1 + ".png");
        } else if (Numero == 2) {
            InsertaBordeImagen(page, 59, 509, 212, 144);
            InsertaBordeImagen(page, 329, 509, 212, 144);
            AgregaComentarios(font, page, 60, 457, 212, 50, Descripcion[Foto1 - 1]);
            AgregaComentarios(font, page, 330, 457, 212, 50, Descripcion[Foto2 - 1]);
            InsertImagen(page, 59, 509, 212, 144, rutacarpeta, "Extra" + Foto1 + ".png");
            InsertImagen(page, 329, 509, 212, 144, rutacarpeta, "Extra" + Foto2 + ".png");

        } else if (Numero == 3) {
            InsertaBordeImagen(page, 59, 509, 212, 144);
            InsertaBordeImagen(page, 329, 509, 212, 144);
            InsertaBordeImagen(page, 185, 309, 212, 144);
            AgregaComentarios(font, page, 60, 457, 212, 50, Descripcion[Foto1 - 1]);
            AgregaComentarios(font, page, 330, 457, 212, 50, Descripcion[Foto2 - 1]);
            AgregaComentarios(font, page, 185, 260, 212, 50, Descripcion[Foto3 - 1]);
            InsertImagen(page, 59, 509, 212, 144, rutacarpeta, "Extra" + Foto1 + ".png");
            InsertImagen(page, 329, 509, 212, 144, rutacarpeta, "Extra" + Foto2 + ".png");
            InsertImagen(page, 185, 309, 212, 144, rutacarpeta, "Extra" + Foto3 + ".png");
        } else if (Numero == 4) {

            InsertaBordeImagen(page, 59, 509, 212, 144);
            InsertaBordeImagen(page, 329, 509, 212, 144);
            InsertaBordeImagen(page, 59, 309, 212, 144);
            InsertaBordeImagen(page, 329, 309, 212, 144);
            AgregaComentarios(font, page, 60, 460, 212, 50, Descripcion[Foto1 - 1]);
            AgregaComentarios(font, page, 330, 460, 212, 50, Descripcion[Foto2 - 1]);
            AgregaComentarios(font, page, 60, 260, 212, 50, Descripcion[Foto3 - 1]);
            AgregaComentarios(font, page, 330, 260, 212, 50, Descripcion[Foto4 - 1]);
            InsertImagen(page, 59, 509, 212, 144, rutacarpeta, "Extra" + Foto1 + ".png");
            InsertImagen(page, 329, 509, 212, 144, rutacarpeta, "Extra" + Foto2 + ".png");
            InsertImagen(page, 59, 309, 212, 144, rutacarpeta, "Extra" + Foto3 + ".png");
            InsertImagen(page, 329, 309, 212, 144, rutacarpeta, "Extra" + Foto4 + ".png");
        } else if (Numero == 5) {

            InsertaBordeImagen(page, 59, 509, 212, 144);
            InsertaBordeImagen(page, 329, 509, 212, 144);
            InsertaBordeImagen(page, 59, 309, 212, 144);
            InsertaBordeImagen(page, 329, 309, 212, 144);
            InsertaBordeImagen(page, 185, 109, 212, 144);
            AgregaComentarios(font, page, 60, 460, 212, 50, Descripcion[Foto1 - 1]);
            AgregaComentarios(font, page, 330, 460, 212, 50, Descripcion[Foto2 - 1]);
            AgregaComentarios(font, page, 60, 260, 212, 50, Descripcion[Foto3 - 1]);
            AgregaComentarios(font, page, 330, 260, 212, 50, Descripcion[Foto4 - 1]);
            AgregaComentarios(font, page, 185, 60, 212, 50, Descripcion[Foto5 - 1]);
            InsertImagen(page, 59, 509, 212, 144, rutacarpeta, "Extra" + Foto1 + ".png");
            InsertImagen(page, 329, 509, 212, 144, rutacarpeta, "Extra" + Foto2 + ".png");
            InsertImagen(page, 59, 309, 212, 144, rutacarpeta, "Extra" + Foto3 + ".png");
            InsertImagen(page, 329, 309, 212, 144, rutacarpeta, "Extra" + Foto4 + ".png");
            InsertImagen(page, 185, 109, 212, 144, rutacarpeta, "Extra" + Foto5 + ".png");
        } else {
            InsertaBordeImagen(page, 59, 509, 212, 144);
            InsertaBordeImagen(page, 329, 509, 212, 144);
            InsertaBordeImagen(page, 59, 309, 212, 144);
            InsertaBordeImagen(page, 329, 309, 212, 144);
            InsertaBordeImagen(page, 59, 109, 212, 144);
            InsertaBordeImagen(page, 329, 109, 212, 144);
            AgregaComentarios(font, page, 60, 460, 212, 50, Descripcion[Foto1 - 1]);
            AgregaComentarios(font, page, 330, 460, 212, 50, Descripcion[Foto2 - 1]);
            AgregaComentarios(font, page, 60, 260, 212, 50, Descripcion[Foto3 - 1]);
            AgregaComentarios(font, page, 330, 260, 212, 50, Descripcion[Foto4 - 1]);
            AgregaComentarios(font, page, 60, 60, 212, 50, Descripcion[Foto5 - 1]);
            AgregaComentarios(font, page, 330, 60, 212, 50, Descripcion[Foto6 - 1]);
            InsertImagen(page, 59, 509, 212, 144, rutacarpeta, "extra" + Foto1 + ".png");
            InsertImagen(page, 329, 509, 212, 144, rutacarpeta, "extra" + Foto2 + ".png");
            InsertImagen(page, 59, 309, 212, 144, rutacarpeta, "extra" + Foto3 + ".png");
            InsertImagen(page, 329, 309, 212, 144, rutacarpeta, "extra" + Foto4 + ".png");
            InsertImagen(page, 59, 109, 212, 144, rutacarpeta, "extra" + Foto5 + ".png");
            InsertImagen(page, 329, 109, 212, 144, rutacarpeta, "extra" + Foto6 + ".png");
        }
    }
    private void FotosExtra2(PdfDocument pdf,String [] Datos,PdfFont font,PdfFont bold,ImageData imageData,int id,String[]Comentarios,String rutacarpetaImagenes) {
        String[]Fotos = new String[id];
        String[]ComentariosNew = new String[id];
        PdfPage nextpage;
        int NumerorealFotos =0;
        for (int i =0; i<id;i++){
            if (Comentarios[i].equals(""))System.out.println("FotoDescartada");
            else {
                ComentariosNew[NumerorealFotos]=Comentarios[i];
                Fotos[NumerorealFotos]="Extra"+String.valueOf(i+1);
                System.out.println(Fotos[NumerorealFotos]);
                NumerorealFotos++;
            }
        }
        System.out.println(NumerorealFotos);
        if (NumerorealFotos > 0) {
            int NumberFotos;
            int Paginasextran;
            if (NumerorealFotos % 6 == 0) {
                Paginasextran = (NumerorealFotos / 6);

            } else {
                Paginasextran = (NumerorealFotos / 6) + 1;

            }
            NumberFotos = 6;
            int ident = 0;
            for (int i = 0; i < Paginasextran; i++) {
                if (i==Paginasextran-1){
                    NumberFotos = 6-(Paginasextran*6-NumerorealFotos);
                }
                nextpage = CuadroNextPage(pdf, Datos, font, bold, imageData);
                InsertaFotosextra2(nextpage,NumberFotos,rutacarpetaImagenes,ident,font,ComentariosNew,Fotos);
                ident ++;
            }
        }
    }
    private void InsertaFotosextra2(PdfPage page,int Numero,String rutacarpeta,int extra,PdfFont font,String [] Descripcion,String[]NombresFoto) {
        String Nombre1 = null,Nombre2 = null,Nombre3=null,Nombre4=null,Nombre5=null,Nombre6=null;
        int Identificador = extra * 6;
        int Foto1 = Identificador + 1;
        int Foto2 = Identificador + 2;
        int Foto3 = Identificador + 3;
        int Foto4 = Identificador + 4;
        int Foto5 = Identificador + 5;
        int Foto6 = Identificador + 6;
        System.out.println(Numero);
        if (Numero == 1){Nombre1 = NombresFoto[Foto1-1];}
        else if(Numero ==2){Nombre1 = NombresFoto[Foto1-1];Nombre2 = NombresFoto[Foto2-1];}
        else if(Numero==3){Nombre1 = NombresFoto[Foto1-1];Nombre2 = NombresFoto[Foto2-1];Nombre3 =NombresFoto[Foto3-1];}
        else if(Numero==4){
            Nombre1 = NombresFoto[Foto1-1];Nombre2 = NombresFoto[Foto2-1];
            Nombre3 = NombresFoto[Foto3-1];Nombre4= NombresFoto[Foto4-1];
        }
        else if(Numero==5){
            Nombre1 = NombresFoto[Foto1-1];Nombre2 = NombresFoto[Foto2-1];Nombre3 = NombresFoto[Foto3-1];
            Nombre4= NombresFoto[Foto4-1];
            Nombre5= NombresFoto[Foto5-1];
        }
        else{
            Nombre1 =NombresFoto[Foto1-1];Nombre2 = NombresFoto[Foto2-1];Nombre3 = NombresFoto[Foto3-1];
            Nombre4= NombresFoto[Foto4-1];
            Nombre5= NombresFoto[Foto5-1]; Nombre6= NombresFoto[Foto6-1];
        }

        if (Numero == 1) {
            InsertaBordeImagen(page, 59, 509, 212, 144);
            AgregaComentarios(font, page, 60, 457, 212, 50, Descripcion[Foto1 - 1]);
            InsertImagen(page, 59, 509, 212, 144, rutacarpeta, Nombre1 + ".png");
        } else if (Numero == 2) {
            InsertaBordeImagen(page, 59, 509, 212, 144);
            InsertaBordeImagen(page, 329, 509, 212, 144);
            AgregaComentarios(font, page, 60, 457, 212, 50, Descripcion[Foto1 - 1]);
            AgregaComentarios(font, page, 330, 457, 212, 50, Descripcion[Foto2 - 1]);
            InsertImagen(page, 59, 509, 212, 144, rutacarpeta, Nombre1 + ".png");
            InsertImagen(page, 329, 509, 212, 144, rutacarpeta, Nombre2 + ".png");

        } else if (Numero == 3) {
            InsertaBordeImagen(page, 59, 509, 212, 144);
            InsertaBordeImagen(page, 329, 509, 212, 144);
            InsertaBordeImagen(page, 185, 309, 212, 144);
            AgregaComentarios(font, page, 60, 457, 212, 50, Descripcion[Foto1 - 1]);
            AgregaComentarios(font, page, 330, 457, 212, 50, Descripcion[Foto2 - 1]);
            AgregaComentarios(font, page, 185, 260, 212, 50, Descripcion[Foto3 - 1]);
            InsertImagen(page, 59, 509, 212, 144, rutacarpeta, Nombre1 + ".png");
            InsertImagen(page, 329, 509, 212, 144, rutacarpeta, Nombre2  + ".png");
            InsertImagen(page, 185, 309, 212, 144, rutacarpeta, Nombre3 + ".png");
        } else if (Numero == 4) {

            InsertaBordeImagen(page, 59, 509, 212, 144);
            InsertaBordeImagen(page, 329, 509, 212, 144);
            InsertaBordeImagen(page, 59, 309, 212, 144);
            InsertaBordeImagen(page, 329, 309, 212, 144);
            AgregaComentarios(font, page, 60, 460, 212, 50, Descripcion[Foto1 - 1]);
            AgregaComentarios(font, page, 330, 460, 212, 50, Descripcion[Foto2 - 1]);
            AgregaComentarios(font, page, 60, 260, 212, 50, Descripcion[Foto3 - 1]);
            AgregaComentarios(font, page, 330, 260, 212, 50, Descripcion[Foto4 - 1]);
            InsertImagen(page, 59, 509, 212, 144, rutacarpeta, Nombre1 + ".png");
            InsertImagen(page, 329, 509, 212, 144, rutacarpeta, Nombre2  + ".png");
            InsertImagen(page, 59, 309, 212, 144, rutacarpeta, Nombre3 + ".png");
            InsertImagen(page, 329, 309, 212, 144, rutacarpeta, Nombre4 + ".png");
        } else if (Numero == 5) {

            InsertaBordeImagen(page, 59, 509, 212, 144);
            InsertaBordeImagen(page, 329, 509, 212, 144);
            InsertaBordeImagen(page, 59, 309, 212, 144);
            InsertaBordeImagen(page, 329, 309, 212, 144);
            InsertaBordeImagen(page, 185, 109, 212, 144);
            AgregaComentarios(font, page, 60, 460, 212, 50, Descripcion[Foto1 - 1]);
            AgregaComentarios(font, page, 330, 460, 212, 50, Descripcion[Foto2 - 1]);
            AgregaComentarios(font, page, 60, 260, 212, 50, Descripcion[Foto3 - 1]);
            AgregaComentarios(font, page, 330, 260, 212, 50, Descripcion[Foto4 - 1]);
            AgregaComentarios(font, page, 185, 60, 212, 50, Descripcion[Foto5 - 1]);
            InsertImagen(page, 59, 509, 212, 144, rutacarpeta, Nombre1 + ".png");
            InsertImagen(page, 329, 509, 212, 144, rutacarpeta, Nombre2 + ".png");
            InsertImagen(page, 59, 309, 212, 144, rutacarpeta, Nombre3 + ".png");
            InsertImagen(page, 329, 309, 212, 144, rutacarpeta, Nombre4 + ".png");
            InsertImagen(page, 185, 109, 212, 144, rutacarpeta, Nombre5+ ".png");
        } else {
            InsertaBordeImagen(page, 59, 509, 212, 144);
            InsertaBordeImagen(page, 329, 509, 212, 144);
            InsertaBordeImagen(page, 59, 309, 212, 144);
            InsertaBordeImagen(page, 329, 309, 212, 144);
            InsertaBordeImagen(page, 59, 109, 212, 144);
            InsertaBordeImagen(page, 329, 109, 212, 144);
            AgregaComentarios(font, page, 60, 460, 212, 50, Descripcion[Foto1 - 1]);
            AgregaComentarios(font, page, 330, 460, 212, 50, Descripcion[Foto2 - 1]);
            AgregaComentarios(font, page, 60, 260, 212, 50, Descripcion[Foto3 - 1]);
            AgregaComentarios(font, page, 330, 260, 212, 50, Descripcion[Foto4 - 1]);
            AgregaComentarios(font, page, 60, 60, 212, 50, Descripcion[Foto5 - 1]);
            AgregaComentarios(font, page, 330, 60, 212, 50, Descripcion[Foto6 - 1]);
            InsertImagen(page, 59, 509, 212, 144, rutacarpeta, Nombre1+ ".png");
            InsertImagen(page, 329, 509, 212, 144, rutacarpeta, Nombre2 + ".png");
            InsertImagen(page, 59, 309, 212, 144, rutacarpeta, Nombre3 + ".png");
            InsertImagen(page, 329, 309, 212, 144, rutacarpeta, Nombre4+ ".png");
            InsertImagen(page, 59, 109, 212, 144, rutacarpeta, Nombre5+ ".png");
            InsertImagen(page, 329, 109, 212, 144, rutacarpeta, Nombre6+ ".png");
        }
    }
    private void InsertaBordeImagen(PdfPage page, int x, int y,int w,int h){
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Paragraph b = new Paragraph(".")
                .setTextAlignment(TextAlignment.CENTER);
        pdfCanvas.stroke();
        canvas.add(b);
    }
    private void AgregaComentarios(PdfFont font,PdfPage page,int x, int y,int w,int h,String Comentario){
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle).setColor(ColorConstants.WHITE,false);
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text Comen1 = new Text(Comentario).setFont(font);
        Paragraph a = new Paragraph().add(Comen1)
                .setTextAlignment(TextAlignment.CENTER);
        pdfCanvas.stroke();
        canvas.add(a);
        canvas.close();
    }
    private void InsertImagen (PdfPage page, int x, int y,int w,int h,String rutacarpeta,String nombre){
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        Canvas canvas = new Canvas(pdfCanvas, rectangle);


        String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
        ImageData data = null;
        try {
            data = ImageDataFactory.create(ExternalStorageDirectory + rutacarpeta + nombre);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Image img = new Image(data,x+1,y+1,w-2);
        img.setHeight(h-2);


        canvas.add(img);
        canvas.close();

    }
    private void FotosExtra(PdfDocument pdf,String [] Datos,PdfFont font,PdfFont bold,ImageData imageData,int id,String[]Comentarios,String rutacarpetaImagenes) {
        PdfPage nextpage;
        if (id > 0) {
            int NumberFotos;
            int Paginasextran;
            if (id % 6 == 0) {
                Paginasextran = (id / 6);

            } else {
                Paginasextran = (id / 6) + 1;

            }
            NumberFotos = 6;
            int ident = 0;
            for (int i = 0; i < Paginasextran; i++) {
                if (i==Paginasextran-1){
                    NumberFotos = 6-(Paginasextran*6-id);
                }
                nextpage = CuadroNextPage(pdf, Datos, font, bold, imageData);
                InsertaFotosextra(nextpage,NumberFotos,rutacarpetaImagenes,ident,font,Comentarios);
                ident ++;
            }
        }
    }
    ///////////////////////////////RDP//////////////////////////////////////////////////////////////////
    public void CreaReporteDigital(String[] Datos,ImageData imageData,String[]ComentariosPrev,String[]EstatusEquipo,boolean[]Sino)throws IOException {
        String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + java.io.File.separator;
        String rutacarpeta = "ProyectoX/"+"Preventivo"+"/" + Datos[0] + "/";
        String Name = Datos[0]+"(RD).pdf";
        Boolean si,no;
        SharedPreferences sh = getApplicationContext().getSharedPreferences("Datos"+Datos[0], Context.MODE_PRIVATE);
        si = Sino[0];
        no = Sino[1];

        System.out.println("Si"+si);
        System.out.println("No"+no);
        String DEST = ExternalStorageDirectory+rutacarpeta+Name;
        PdfDocument pdf = new PdfDocument(new PdfWriter(DEST));
        PdfPage page = pdf.addNewPage();
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
        CuadroDatosRD(Datos,pdfCanvas,font,bold,20, 745, 560, 82,imageData);
        CuadroDatos2(Datos,pdfCanvas,font,bold,20, 678, 560, 46);
        TablePrev(Datos[11],pdfCanvas,font,bold,20, 170, 560, 504,ComentariosPrev);
        Estatusequipo(EstatusEquipo,pdfCanvas,font,bold,20, 105,560, 46,Datos[28]);
        Aceptacion(Datos,pdfCanvas,font,bold,20, 84, 560, 15);
        //TableAceptacion(Datos,pdfCanvas,20, 14, 560, 62);
        PdfPage page2 = pdf.addNewPage();
        InsertabordesCredenciales(page2);
        InsertaImagenesCredenciales(page2,font,Datos);
        añadeCheck(Datos[11],page);
        AñadirLineas2(page,Sino);
        AñadeContenidoCuadrosDatos(font,page,bold,Datos);
        AñadeDescripciones(font,page,bold,Datos);
        InsertaPiedePagina2(pdf);
        pdf.close();

    }
    private void CuadroDatosRD(String[] Datos,PdfCanvas pdfCanvas,PdfFont font,PdfFont bold,int x ,int y , int w , int h,ImageData imageData){
        Rectangle rectangle = new Rectangle(x,y,w,h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text title = new Text("REPORTE DE MANTENIMIENTO PREVENTIVO").setFont(bold).setFontSize(9);
        Text Trimestre = new Text("Trimestre de Servicio").setFont(font).setFontSize(8);
        Text NReporte = new Text("No de reporte:").setFont(font).setFontSize(8);
        Text Fecha1 = new Text("Fecha / hora de inicio:").setFont(font).setFontSize(8);
        Text Fecha2 = new Text("Fecha / hora de terminación:").setFont(font).setFontSize(8);
        Text Periodicidad = new Text("Periodicidad del mantenimiento:").setFont(font).setFontSize(8);

        Text Trim = new Text(Datos[10]).setFont(font).setUnderline().setFontSize(8);
        Text TICKET = new Text(Datos[12]).setFont(font).setFontSize(8);
        Text FECHA = new Text(Datos[17]).setFont(font).setFontSize(8);
        Text PERIODICIDAD = new Text(Datos[11]).setFont(font).setFontSize(8);
        Text HORAINICIO = new Text(Datos[18]).setFont(font).setFontSize(8);
        Text HORAFIN = new Text(Datos[20]).setFont(font).setFontSize(8);

        Text SaltoLinea = new Text(" \n ").setFont(font).setFontSize(8);
        Text Space = new Text(".  .").setFont(font).setUnderline().setFontSize(8).setFontColor(ColorConstants.WHITE);

        Paragraph a = new Paragraph().add(title).add(Space).add(SaltoLinea)
                /*.add(Trim).add(Space).add(Trimestre).add(Space).add(Space).add(Space).add(Space).add(Space).add(SaltoLinea)
                .add(NReporte).add(Space).add(TICKET).add(Space).add(Space).add(Space).add(Space).add(Space).add(Space).add(SaltoLinea)
                .add(Fecha1).add(Space).add(FECHA).add(Space).add(HORAINICIO).add(Space).add(Space).add(SaltoLinea)
                .add(Fecha2).add(Space).add(FECHA).add(Space).add(HORAFIN).add(Space).add(Space).add(SaltoLinea)
                .add(Periodicidad).add(Space).add(PERIODICIDAD).add(Space).add(Space).add(Space).add(Space).add(Space).add(Space).add(Space).add(Space)*/
                .setTextAlignment(TextAlignment.RIGHT);
        //InsertaImagen("FotosBase/", "seguritechlogo.png",canvas,30,744,190,142);
        InsertaImagenLogo(imageData,canvas,50,746,190);
        canvas.add(a);
        canvas.close();

    }
    public void TablePrev(String Periodicidad,PdfCanvas pdfCanvas,PdfFont font,PdfFont bold,int x , int y , int w , int h,String[] ComentariosDefPrev) throws IOException {
        Table table = new Table(UnitValue.createPercentArray(9)).useAllAvailableWidth();
        Rectangle rectangle = new Rectangle(x ,y, w, h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        table.addCell(getCell(9,"Lista de actividades realizadas"));
        table.addCell(getCell(5,"Actividad mensual"));
        table.addCell(getCell(1,"Cumple"));
        table.addCell(getCell(3,"Comentarios"));
        int Numero;
        if (Periodicidad.equals("Mensual")){
            Numero = 5;
        }
        else if (Periodicidad.equals("Trimestral")){
            Numero = 20;
        }
        else{
            Numero = ComentariosDefPrev.length;
        }
        for (int i = 0; i < ComentariosDefPrev.length; i++) {
            if (i==5){
                table.addCell(getCell(5,"Actividad trimestral"));
                table.addCell(getCell(1,""));
                table.addCell(getCell(3,""));
            }
            else if (i==21){
                table.addCell(getCell(5,"Actividad anual"));
                table.addCell(getCell(1,""));
                table.addCell(getCell(3,""));
            }
            else{

                    if (i<=Numero) {
                        table.addCell(getCell(5,dt.ComentariosRDP[i]));
                        table.addCell(getCell(1,""));
                        table.addCell(getCell(3,ComentariosDefPrev[i]));
                    }
                    else{
                        table.addCell(getCell(5,dt.ComentariosRDP[i]));
                        table.addCell(getCell(1,""));
                        table.addCell(getCell(3,""));
                    }




            }



        }
        canvas.add(table);


    }
    private Cell getCell(int cm, String Text) throws IOException {
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
        if (Text.equals("Actividad trimestral")||Text.equals("Actividad mensual")||
                Text.equals("Actividad anual")||Text.equals("Cumple")||Text.equals("Comentarios")||
                Text.equals("Nombre:")|| Text.equals("Cargo:")|| Text.equals("Firma:")
                || Text.equals("Responsable por parte de seguritech")|| Text.equals("Responsable por parte del ANAM")){
            Cell cell = new Cell(1, cm);
            Paragraph p = new Paragraph(
                    String.format(Text)).setFontSize(6).setFont(bold).setFontColor(ColorConstants.WHITE);
            p.setTextAlignment(TextAlignment.CENTER);
            p.setBold();
            cell.add(p);
            return cell;

        }else if(Text.equals("Lista de actividades realizadas" )||Text.equals("Aceptación del sercivio")) {
            Cell cell = new Cell(1, cm);
            Paragraph p = new Paragraph(
                    String.format(Text)).setFontSize(8).setFont(bold).setFontColor(ColorConstants.WHITE);
            p.setTextAlignment(TextAlignment.CENTER);
            cell.add(p);
            p.setBold();
            return cell;
        }

        else {

            Cell cell = new Cell(1, cm);
            Paragraph p = new Paragraph(
                    String.format(Text)).setFontSize(5.3F).setFont(font);
            p.setTextAlignment(TextAlignment.LEFT);
            cell.add(p);
            return cell;
        }

    }
    private void Estatusequipo(String[] Datos,PdfCanvas pdfCanvas,PdfFont font,PdfFont bold,int x, int y,int w, int h,String Observaciones){
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke();

        Canvas canvas = new Canvas(pdfCanvas, rectangle);

        Text Text1 = new Text("El equipo está en condiciones de operación:").setFont(bold).setFontSize(8);
        Text Text2 = new Text("Contador de inspecciones:").setFont(bold).setFontSize(8);
        Text Text4 = new Text("Observaciones:").setFont(bold).setFontSize(8);
        //Text Text5 = new Text("Si").setFont(bold).setFontSize(6);
        //Text Text6 = new Text("No").setFont(bold).setFontSize(6);
        Text Text7 = new Text("Horas de uso del generador eléctrico.").setFont(font).setFontSize(8);


        Text INSPECCIONES = new Text(Datos[2]).setFont(font).setFontSize(8);
        Text HORAS = new Text(Datos[1]).setFont(font).setFontSize(8);
        Text OBSERVACIONES = new Text(Datos[0]).setFont(bold).setFontSize(8);


        Text SaltoLinea = new Text(" \n ").setFont(font).setFontSize(9);
        Text Space = new Text(".  .").setFont(font).setFontSize(9).setFontColor(ColorConstants.WHITE);
        Text Space3 = new Text(". .").setFont(font).setFontSize(9).setFontColor(ColorConstants.WHITE);
        Text Space2 = new Text(".                                                .").setFont(font).setFontSize(9).setFontColor(ColorConstants.WHITE);
        Text Space4 = new Text("                          Si                  No").setFont(font).setFontSize(9);

        Paragraph a = new Paragraph().add(Space).add(Text1).add(Space4).add(SaltoLinea)
                .add(Space).add(Text2).add(Space).add(Space).add(Space).add(INSPECCIONES).add(SaltoLinea)
                .add(Space).add(Text4).add(Space).add(Space).add(HORAS).add(Space).add(Text7).add(Space).add(OBSERVACIONES).add(SaltoLinea)
                .setTextAlignment(TextAlignment.LEFT);

        canvas.add(a);
        Rectangle rectangle2 = new Rectangle(x, y+h, w, 15);
        pdfCanvas.rectangle(rectangle2);
        pdfCanvas.stroke();
        Canvas canvas2 = new Canvas(pdfCanvas, rectangle2);
        Text DatosEquipo = new Text("Estatus del equipo").setFont(bold).setFontSize(9);
        Paragraph b = new Paragraph().add(DatosEquipo).setTextAlignment(TextAlignment.CENTER);
        canvas2.add(b);

        canvas.close();
        canvas2.close();
    }
    private void TableAceptacion(String[]Datos,PdfCanvas pdfCanvas, int x , int y , int w , int h) throws IOException {
        Table table = new Table(UnitValue.createPercentArray(8)).useAllAvailableWidth();
        Rectangle rectangle = new Rectangle(x,y,w,h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);

        table.addCell(getCell(8,"Aceptación del sercivio"));

        table.addCell(getCell(4,"Responsable por parte de seguritech"));
        table.addCell(getCell(4,"Responsable por parte del ANAM"));

        table.addCell(getCell(1,"Nombre:"));
        table.addCell(getCell(3,String.valueOf(Datos[4])));
        table.addCell(getCell(1,"Nombre:"));
        table.addCell(getCell(3,String.valueOf(Datos[6])));

        table.addCell(getCell(1,"Cargo:"));
        table.addCell(getCell(3,String.valueOf(Datos[5])));
        table.addCell(getCell(1,"Cargo:"));
        table.addCell(getCell(3,String.valueOf(Datos[7])));
        table.addCell(getCell(1,"Firma:"));
        table.addCell(getCell(3,""));
        table.addCell(getCell(1,"Firma:"));
        table.addCell(getCell(3,""));

        canvas.add(table);


    }
    private void Ceck(PdfPage page,int y){
        PdfCanvas canvas = new PdfCanvas(page);


        // Create a 100% Magenta color

        canvas

                .moveTo(363, y) //y 643
                .lineTo(360, y-4) //y-4
                .moveTo(360, y-4) //y-4
                .lineTo(370, y+3) //y+3
                .closePathStroke();
    }
    private void añadeCheck(String Periodicidad,PdfPage page){
        int y = 641;
        if (Periodicidad.equals("Mensual")){

            int count = 0;
            for(int i=0;i<5;i++){
                Ceck(page, y);
                if (count == 3 ) {
                    y = y - 11;
                    count = 0;
                } else{
                    y = y - 13;
                }
                count++;



            }
        }
        else if (Periodicidad.equals("Trimestral")){
            int count = 0;
            for(int i=0;i<21;i++){
                if (i==5){
                    y = y-13;
                }
                else {
                    Ceck(page, y);
                    if (count == 3 && i!=15) {
                        y = y - 11;
                        count = 0;
                    } else if(i == 15 ){
                        y = y-20;
                    }else {
                        y = y - 13;
                    }
                }
                count++;



            }
        }
        else{
            int count = 0;
            for(int i=0;i<38;i++){
                if ((i==5)){
                    y = y-13;
                }else if ((i==21)){
                    y = y-10;
                }
                else {
                    Ceck(page, y);
                    if (count == 3 && i!=15) {
                        y = y - 11;
                        count = 0;
                    } else if(i == 15){
                        y = y-17;
                    }
                    else if(i == 27||i==30){
                        y = y-9;
                    }
                        else {
                        y = y - 13;
                    }
                }
                count++;



            }

        }


    }
    private void InsertaImagenesCredenciales(PdfPage nextpage,PdfFont font,String [] Datos){
        String rutacarpetaImagenesANAM = "ProyectoX/"+"Preventivo"+"/"+Datos[0]+"/";
        String rutacarpetaImagenesSeguritech = "ProyectoX/"+"Preventivo"+"/"+Datos[0]+"/" ;
        PdfCanvas pdfCanvas = new PdfCanvas(nextpage);
        Rectangle rectangle = new Rectangle(20, 780, 560, 20);
        pdfCanvas.rectangle(rectangle).setColor(ColorConstants.WHITE,false);
        pdfCanvas.stroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);

        Text Comen1 = new Text("  Fotos de Gafete / Credencial (Responsable de la ANAM)").setFont(font).setFontSize(15);
        Text Comen2 = new Text("Foto de credencial Seguritech").setFont(font).setFontSize(15);

        Paragraph a = new Paragraph().add(Comen1)
                .setTextAlignment(TextAlignment.CENTER);

        Rectangle rectangle2 = new Rectangle(20, 410, 560, 20);
        pdfCanvas.rectangle(rectangle2).setColor(ColorConstants.WHITE,false);
        pdfCanvas.stroke();
        Canvas canvas2 = new Canvas(pdfCanvas, rectangle2);
        Paragraph b = new Paragraph().add(Comen2)
                .setTextAlignment(TextAlignment.CENTER);

        InsertaImagencredenciales(rutacarpetaImagenesANAM, Datos[6]+" "+"Frontal.png",canvas,100,450,180,280);
        InsertaImagencredenciales(rutacarpetaImagenesANAM, Datos[6]+" "+"Trasera.png",canvas,320,450,180,280);
        InsertaImagencredenciales(rutacarpetaImagenesSeguritech, Datos[4]+".png",canvas,210,100,180,280);

        canvas.add(a);
        canvas2.add(b);

    }
    private void InsertabordesCredenciales(PdfPage page){
        InsertaBordeImagen(page, 99, 449,182,282);
        InsertaBordeImagen(page, 319, 449,182,282);
        InsertaBordeImagen(page,209 , 99,182,282);
    }
    private void InsertaImagencredenciales(String rutacarpeta, String nombre,Canvas canvas,int left,int bottom,int width,int height){
        String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
        ImageData data = null;
        try {
            data = ImageDataFactory.create(ExternalStorageDirectory + rutacarpeta + nombre);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Image img = new Image(data,left,bottom,width);
        img.setHeight(height);


        canvas.add(img);


    }
    private void InsertaPiedePagina2(PdfDocument pdf ){
        Text Comen = new Text("FO-RX-01VER03").setFontSize(8);
        int numberOfPages = pdf.getNumberOfPages();
        for (int i = 1; i <= numberOfPages; i++) {
            Document doc = new Document(pdf);
            // Write aligned text to the specified by parameters point
            doc.showTextAligned(new Paragraph(Comen),
                    //559, 15, i, TextAlignment.RIGHT, VerticalAlignment.TOP, 0);
                    559, 15, 1, TextAlignment.RIGHT, VerticalAlignment.TOP, 0);
        }
    }
    private void AñadirLineas2(PdfPage page,boolean[]sino) {
        PdfCanvas canvas = new PdfCanvas(page);

        // Create a 100% Magenta color

        canvas
                .moveTo(115, 707)
                .lineTo(581, 707)
                .moveTo(115, 695)
                .lineTo(581, 695)
                .moveTo(115, 683)
                .lineTo(581, 683)

                .moveTo(475, 750)
                .lineTo(573, 750)
                .moveTo(475, 762)
                .lineTo(573, 762)
                .moveTo(475, 774)
                .lineTo(573, 774)
                .moveTo(475, 786)
                .lineTo(573, 786)
                .moveTo(460, 800)
                .lineTo(470, 800)

                .moveTo(220, 137)
                .lineTo(255, 137)

                .moveTo(220, 145)
                .lineTo(255, 145)

                .moveTo(220, 137)
                .lineTo(220, 145)

                .moveTo(255, 137)
                .lineTo(255, 145)



                .moveTo(270, 137)
                .lineTo(305, 137)

                .moveTo(270, 145)
                .lineTo(305, 145)

                .moveTo(270, 137)
                .lineTo(270, 145)

                .moveTo(305, 137)
                .lineTo(305, 145)

                .moveTo(145, 132)
                .lineTo(170, 132)
                .moveTo(145, 124)
                .lineTo(170, 124)

                .moveTo(145, 132)
                .lineTo(145, 124)
                .moveTo(170, 132)
                .lineTo(170, 124)


                .moveTo(84, 109)
                .lineTo(570, 109)


                .setLineWidth(0).closePathStroke();
               if (sino[0]){
                   canvas   .moveTo(220, 137)
                           .lineTo(255, 145)


                           .moveTo(255, 137)
                           .lineTo(220, 145)
                           .setLineWidth(0).closePathStroke();
               }else {
                           canvas.moveTo(270, 137)
                           .lineTo(305, 145)

                           .moveTo(270, 145)
                           .lineTo(305, 137).setLineWidth(0).closePathStroke();
               }
    }
    private void Aceptacion(String[] Datos,PdfCanvas pdfCanvas,PdfFont font,PdfFont bold,int x,int y, int w,int h){
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke();

        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text DatosEquipo = new Text("Aceptación del servicio").setFont(bold).setFontSize(9);
        Paragraph a = new Paragraph().add(DatosEquipo).setTextAlignment(TextAlignment.CENTER);
        canvas.add(a);

        Text Cargo = new Text("Nombre:").setFont(bold).setFontSize(9);
        Text Firma = new Text("Firma:").setFont(bold).setFontSize(9);

        Text CargoSeguritech = new Text(Datos[5]).setFont(font).setFontSize(9);

        Text CargoANAM = new Text(Datos[7]).setFont(font).setFontSize(9);

        Text TiemporReparación= new Text("Tiempo de reparación:              -96 Horas           +96 Horas").setFont(bold).setFontSize(8);
        Text FechaReparacion= new Text("              "+Datos[19]).setFont(font).setFontSize(9);
        Text HoraReparacion = new Text(Datos[20]).setFont(font).setFontSize(9);

        Text SaltoLinea = new Text(" \n ").setFont(font).setFontSize(9);
        Text Space = new Text(".  .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);
        Text Space3 = new Text(". .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);
        Text Space2 = new Text(".                                  .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);

        Rectangle rectangle2 = new Rectangle(x, y-h, (w/2), h);
        pdfCanvas.rectangle(rectangle2);
        pdfCanvas.stroke();
        Canvas canvas2 = new Canvas(pdfCanvas, rectangle2);
        Text SeguritechR = new Text("Responsable por parte de Seguritech").setFont(bold).setFontSize(9);
        Paragraph b = new Paragraph().add(SeguritechR).setTextAlignment(TextAlignment.CENTER);
        canvas2.add(b);

        Rectangle rectangle3 = new Rectangle(x+(w/2), y-h, (w/2), h);
        pdfCanvas.rectangle(rectangle3);
        pdfCanvas.stroke();
        Canvas canvas3 = new Canvas(pdfCanvas, rectangle3);
        Text SeguritechA = new Text("Responsable por parte de la ANAM").setFont(bold).setFontSize(9);
        Paragraph c = new Paragraph().add(SeguritechA).setTextAlignment(TextAlignment.CENTER);
        canvas3.add(c);


        Rectangle rectangle4 = new Rectangle(x, y-h-h, 40, h);
        pdfCanvas.rectangle(rectangle4);
        pdfCanvas.stroke();
        Canvas canvas4 = new Canvas(pdfCanvas, rectangle4);
        Text Nombre = new Text(" Nombre:").setFont(bold).setFontSize(9);
        Paragraph d = new Paragraph().add(Nombre).setTextAlignment(TextAlignment.CENTER);
        canvas4.add(d);

        Rectangle rectangle5 = new Rectangle(x+40, y-h-h, (w/2)-40, h);
        pdfCanvas.rectangle(rectangle5);
        pdfCanvas.stroke();
        Canvas canvas5 = new Canvas(pdfCanvas, rectangle5);
        Text NombreSeguritech = new Text(" "+Datos[4]).setFont(font).setFontSize(9);
        Paragraph e = new Paragraph().add(Space).add(NombreSeguritech).setTextAlignment(TextAlignment.LEFT);
        canvas5.add(e);

        Rectangle rectangle6 = new Rectangle(x+(w/2), y-h-h, 40, h);
        pdfCanvas.rectangle(rectangle6);
        pdfCanvas.stroke();
        Canvas canvas6 = new Canvas(pdfCanvas, rectangle6);
        Paragraph f = new Paragraph().add(Nombre).setTextAlignment(TextAlignment.CENTER);
        canvas6.add(f);

        Rectangle rectangle7 = new Rectangle(x+(w/2)+40, y-h-h, (w/2)-40, h);
        pdfCanvas.rectangle(rectangle7);
        pdfCanvas.stroke();
        Canvas canvas7 = new Canvas(pdfCanvas, rectangle7);
        Text NombreANAM = new Text(" "+Datos[6]).setFont(font).setFontSize(9);
        Paragraph g = new Paragraph().add(Space).add(NombreANAM).setTextAlignment(TextAlignment.LEFT);
        canvas7.add(g);


        rectangle4 = new Rectangle(x, y-h-h-h, 40, h);
        pdfCanvas.rectangle(rectangle4);
        pdfCanvas.stroke();
        canvas4 = new Canvas(pdfCanvas, rectangle4);
        Nombre = new Text(" Cargo:").setFont(bold).setFontSize(9);
        d = new Paragraph().add(Nombre).setTextAlignment(TextAlignment.CENTER);
        canvas4.add(d);

        rectangle5 = new Rectangle(x+40, y-h-h-h, (w/2)-40, h);
        pdfCanvas.rectangle(rectangle5);
        pdfCanvas.stroke();
        canvas5 = new Canvas(pdfCanvas, rectangle5);
        NombreSeguritech = new Text(" "+Datos[5]).setFont(font).setFontSize(9);
        e = new Paragraph().add(Space).add(NombreSeguritech).setTextAlignment(TextAlignment.LEFT);
        canvas5.add(e);

        rectangle6 = new Rectangle(x+(w/2), y-h-h-h, 40, h);
        pdfCanvas.rectangle(rectangle6);
        pdfCanvas.stroke();
        canvas6 = new Canvas(pdfCanvas, rectangle6);
        f = new Paragraph().add(Nombre).setTextAlignment(TextAlignment.CENTER);
        canvas6.add(f);

        rectangle7 = new Rectangle(x+(w/2)+40, y-h-h-h, (w/2)-40, h);
        pdfCanvas.rectangle(rectangle7);
        pdfCanvas.stroke();
        canvas7 = new Canvas(pdfCanvas, rectangle7);
        NombreANAM = new Text(" "+Datos[7]).setFont(font).setFontSize(9);
        g = new Paragraph().add(Space).add(NombreANAM).setTextAlignment(TextAlignment.LEFT);
        canvas7.add(g);

        rectangle4 = new Rectangle(x, y-h-h-h-h-5, 40, h+5);
        pdfCanvas.rectangle(rectangle4);
        pdfCanvas.stroke();
        canvas4 = new Canvas(pdfCanvas, rectangle4);
        Nombre = new Text(" Firma:").setFont(bold).setFontSize(9);
        d = new Paragraph().add(Nombre).setTextAlignment(TextAlignment.CENTER);
        canvas4.add(d);

        rectangle5 = new Rectangle(x+40, y-h-h-h-h-5, (w/2)-40, h+5);
        pdfCanvas.rectangle(rectangle5);
        pdfCanvas.stroke();
        canvas5 = new Canvas(pdfCanvas, rectangle5);
        e = new Paragraph().add(Space).add("").setTextAlignment(TextAlignment.LEFT);
        canvas5.add(e);

        rectangle6 = new Rectangle(x+(w/2), y-h-h-h-h-5, 40, h+5);
        pdfCanvas.rectangle(rectangle6);
        pdfCanvas.stroke();
        canvas6 = new Canvas(pdfCanvas, rectangle6);
        f = new Paragraph().add(Nombre).setTextAlignment(TextAlignment.CENTER);
        canvas6.add(f);

        rectangle7 = new Rectangle(x+(w/2)+40, y-h-h-h-h-5, (w/2)-40, h+5);
        pdfCanvas.rectangle(rectangle7);
        pdfCanvas.stroke();
        canvas7 = new Canvas(pdfCanvas, rectangle7);
        g = new Paragraph().add(Space).add("").setTextAlignment(TextAlignment.LEFT);
        canvas7.add(g);


        canvas.close();
        canvas2.close();
    }
    private void AñadeContenidoCuadrosDatos(PdfFont font,PdfPage page,PdfFont bold,String[]Datos){
        AgregaContenidoCuadroDatos(font, page, 340, 813, 130, 0, "0"+Datos[10],9);
        AgregaContenidoCuadroDatos(bold, page, 340, 801, 130, 0, "No. de reporte:",9);
        AgregaContenidoCuadroDatos(bold, page, 340, 789, 130, 0, "Fecha / hora de inicio:",9);
        AgregaContenidoCuadroDatos(bold, page, 340, 776, 130, 0, "Fecha / hora de terminación:",9);
        AgregaContenidoCuadroDatos(bold, page, 340, 763, 130, 0, "Periodicidad del mantenimiento:",9);
        AgregaContenido(font, page, 480, 813, 100, 0, "Trimestre de servicio",9);
        AgregaContenido(font, page, 480, 801, 100, 0, Datos[12],9);
        AgregaContenido(font, page, 480, 789, 110, 0, Datos[17]+" "+Datos[18],9);
        AgregaContenido(font, page, 480, 776, 110, 0, Datos[19]+" "+Datos[20],9);
        AgregaContenido(font, page, 480, 763, 100, 0, Datos[11],9);

        AgregaContenido(bold, page, 25, 722, 90, 0, "Aduana:",9);
        AgregaContenido(bold, page, 25, 709, 90, 0, "Equipo:",9);
        AgregaContenidoCuadroDatos(bold, page, 285, 709, 90, 0, "No. de serie:",9);
        AgregaContenido(bold, page, 25, 696, 90, 0, "Ubicación del equipo:",9);

        AgregaContenido(font, page, 116, 722, 400, 0, Datos[13],9);
        AgregaContenido(font, page, 116, 709, 200, 0, Datos[14],9);
        AgregaContenido(font, page, 380, 709, 199, 0, Datos[16],9);
        AgregaContenido(font, page, 116, 696, 400, 0, Datos[15],9);
    }
    private void AñadeDescripciones(PdfFont font,PdfPage page,PdfFont bold,String[]Datos){
        AgregaContenidoCenter(bold, page, 200, 673, 200, 0, "Lista de actividades realizadas",9);
        AgregaContenidoCenter(bold, page, 75, 656, 200, 0, "Actividad mensual",7);
        AgregaContenidoCenter(bold, page, 390, 656, 200, 0, "Comentarios",7);
        AgregaContenidoCenter(bold, page, 75, 582, 200, 0, "Actividad trimestral",7);
        AgregaContenidoCenter(bold, page, 75, 378, 200, 0, "Actividad anual",7);

    }
    private void AgregaContenido(PdfFont font,PdfPage page,int x, int y,int w,int h,String Comentario, int tamaño){
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text Comen1 = new Text(Comentario.replaceAll("(\n|\r)", " ")).setFont(font).setFontSize(tamaño);
        Paragraph a = new Paragraph().add(Comen1)
                .setTextAlignment(TextAlignment.LEFT);
        canvas.add(a).close();
        canvas.close();
        pdfCanvas.setColor(ColorConstants.WHITE,false).stroke().closePathStroke();

    }
    private void AgregaContenidoCuadroDatos(PdfFont font,PdfPage page,int x, int y,int w,int h,String Comentario, int tamaño){
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text Comen1 = new Text(Comentario.replaceAll("(\n|\r)", " ")).setFont(font).setFontSize(tamaño);
        Paragraph a = new Paragraph().add(Comen1)
                .setTextAlignment(TextAlignment.RIGHT);
        canvas.add(a).close();
        canvas.close();
        pdfCanvas.setColor(ColorConstants.WHITE,false).stroke().closePathStroke();


    }
    private void AgregaContenidoCenter(PdfFont font,PdfPage page,int x, int y,int w,int h,String Comentario, int tamaño){
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text Comen1 = new Text(Comentario.replaceAll("(\n|\r)", " ")).setFont(font).setFontSize(tamaño);
        Paragraph a = new Paragraph().add(Comen1)
                .setTextAlignment(TextAlignment.CENTER);
        canvas.add(a).close();
        canvas.close();
        pdfCanvas.setColor(ColorConstants.WHITE,false).stroke().closePathStroke();


    }
}
