package com.bmr.xproyect.Documentos;

import android.graphics.fonts.Font;
import android.os.Environment;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class Correctivo {

    public void CreaArchivo(String[] Datos, ImageData imageData, int id, String[]Comentarios)throws IOException {
        String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
        String rutacarpeta = "ProyectoX/"+"Correctivo"+"/" + Datos[0] + "/";
        String rutacarpetaImagenes = "ProyectoX/"+"Correctivo/" + Datos[0] + "/" + Datos[0] + "(CF)/";
        String Name = Datos[0]+"(RFD).pdf";
        String DEST = ExternalStorageDirectory+rutacarpeta+Name;
        PdfDocument pdf = new PdfDocument(new PdfWriter(DEST));
        PdfPage page = pdf.addNewPage();
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
        CuadroDatos(Datos,pdfCanvas,font,bold,36, 700, 530, 100,imageData);
        CuadroDatos2(Datos,pdfCanvas,font,bold,36, 615, 530, 50);

        Fotos2(page,pdf,Datos,font,bold,imageData,id,Comentarios,rutacarpetaImagenes);
        //AñadeContenidoCuadrosDatosFotos(font,page,bold,Datos);
        InsertaPiedePagina(pdf);
        pdf.close();
    }
    private void CuadroDatos(String[] Datos,PdfCanvas pdfCanvas,PdfFont font,PdfFont bold,int x,int y,int w,int h,ImageData imageData){
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke().closePathStroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text title = new Text("REPORTE FOTOGRÁFICO DE MANTENIMIENTO CORRECTIVO").setFont(bold).setFontColor(ColorConstants.RED).setFontSize(10);
        Text NReporte = new Text("No. de reporte:").setFont(font).setFontSize(9);
        Text Fecha = new Text("Fecha:").setFont(font).setFontSize(9);

        Text TICKET = new Text(Datos[12]).setFont(font).setFontSize(9);
        Text FECHA = new Text(Datos[17]).setFont(font).setFontSize(9);

        Text SaltoLinea = new Text(" \n ").setFont(font).setFontSize(9);
        Text Space = new Text(".  .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);

        Paragraph a = new Paragraph().add(title).add(Space).setTextAlignment(TextAlignment.RIGHT);
        //Paragraph b = new Paragraph().add(SaltoLinea).add(NReporte).add(Space).add(TICKET).add(Space).add(Space).add(Space).add(SaltoLinea)
        //.add(Fecha).add(Space).add(FECHA).add(Space).add(Space).add(Space).add(Space).add(Space).add(SaltoLinea)
        //.setTextAlignment(TextAlignment.RIGHT);

        //InsertaImagen("FotosBase/", "seguritechlogo.png",canvas,50,715,190,142);
        InsertaImagenLogo(imageData,canvas,50,715,190);


        canvas.add(a);
        canvas.close();

    }
    private void CuadroDatos2(String[] Datos,PdfCanvas pdfCanvas,PdfFont font,PdfFont bold,int x,int y, int w,int h){
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke().closePathStroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Rectangle rectangle2 = new Rectangle(x, y+h, w, 15);
        pdfCanvas.rectangle(rectangle2);
        pdfCanvas.stroke().closePathStroke();
        Canvas canvas2 = new Canvas(pdfCanvas, rectangle2);
        Text DatosEquipo = new Text("Datos del equipo").setFont(bold).setFontSize(8);
        Paragraph b = new Paragraph().add(DatosEquipo).setTextAlignment(TextAlignment.CENTER);
        canvas2.add(b).close();
        canvas.close();
        canvas2.close();
    }
    private void InsertaImagenLogo(ImageData imageData,Canvas canvas,int left,int bottom,int width){

        Image img = new Image(imageData,left,bottom,width);
        canvas.add(img).close();
    }
    private void Fotos(PdfPage page,PdfDocument pdf,String [] Datos,PdfFont font,PdfFont bold,ImageData imageData,int id,String[]Comentarios,String rutacarpetaImagenes) {
        AñadirLineas(page);
        AgregaContenidoCuadroDatos(bold, page, 370, 764, 100, 11, "0"+Datos[10],8);
        AgregaContenidoCuadroDatos(bold, page, 370, 750, 100, 11, "No. de reporte:",8);
        AgregaContenidoCuadroDatos(bold, page, 370, 736, 100, 11,"Fecha" ,8);
        AgregaContenido(font, page, 480, 764, 70, 11, "Trimestre de servicio",8);
        AgregaContenido(font, page, 480, 750, 70, 11, Datos[12],8);
        AgregaContenido(font, page, 480, 736, 70, 11, Datos[19],8);

        AgregaContenido(bold, page, 55, 650, 80, 11, "Aduana:",8);
        AgregaContenido(bold, page, 55, 638, 80, 11, "Equipo:",8);
        AgregaContenido(bold, page, 300, 638, 80, 11, "No. de serie:",8);
        AgregaContenido(bold, page, 55, 626, 80, 11, "Ubicación del equipo:",8);
        AgregaContenido(font, page, 139, 651, 400, 11, Datos[13],8);
        AgregaContenido(font, page, 139, 638, 140, 11, Datos[14],8);
        AgregaContenido(font, page, 360, 638, 199, 11, Datos[16],8);
        AgregaContenido(font, page, 139, 625, 400, 11, Datos[15],8);

        PdfPage nextpage;
        if (id > 0) {

            int NumberFotos;
            int Paginasextran;
            if ((id+2) % 6 == 0) {
                Paginasextran = ((id+2) / 6);
            }
            else {
                Paginasextran = ((id+2) / 6)+1;

            }
            NumberFotos = 6;
            int ident = 0;
            int b =0;
            Boolean first;
            for (int i = 0; i < Paginasextran; i++) {
                if(i==0){
                    if (id <= 4){
                        NumberFotos = 4 - (4 - id);
                        nextpage = page;
                        first = true;
                        InsertaFotos(Datos[3], nextpage, NumberFotos, rutacarpetaImagenes, ident, font, Comentarios,first);
                    }

                    else{
                        NumberFotos = 4;
                        nextpage = page;
                        first = true;
                        InsertaFotos(Datos[3], nextpage, NumberFotos, rutacarpetaImagenes, ident, font, Comentarios,first);
                    }


                }
                else {
                    NumberFotos = 6;
                    if (i == Paginasextran - 1) {

                        //NumberFotos =  ((Paginasextran * 6) - (id-4)-6);
                        NumberFotos = id +(4-b)-(Paginasextran*4);
                    }
                    first = false;
                    nextpage = CuadroNextPage(pdf, Datos, font, bold, imageData);
                    InsertaFotos(Datos[3], nextpage, NumberFotos, rutacarpetaImagenes, ident, font, Comentarios,first);
                    b=b+2;
                }



                ident++;
            }

        }
    }
    private PdfPage CuadroNextPage(PdfDocument pdf,String [] Datos,PdfFont font,PdfFont bold,ImageData imageData){
        PdfPage nextpage = pdf.addNewPage();
        PdfCanvas pdfCanvas = new PdfCanvas(nextpage);
        Rectangle rectangle = new Rectangle(36, 700, 530, 100);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text NReporte = new Text("No. de reporte:").setFont(bold).setFontSize(9);
        Text title = new Text("REPORTE FOTOGRÁFICO DE MANTENIMIENTO CORRECTIVO").setFont(bold).setFontColor(ColorConstants.RED).setFontSize(10);
        Text TICKET = new Text(Datos[12]).setFont(font).setUnderline().setFontSize(9);

        Text Space = new Text(".  .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);

        Paragraph a = new Paragraph().add(title).add(Space).setTextAlignment(TextAlignment.RIGHT);
        Paragraph b = new Paragraph().add(NReporte).add(Space).add(TICKET).add(Space).add(Space).add(Space).setTextAlignment(TextAlignment.RIGHT);
        //InsertaImagen("FotosBase/", "seguritechlogo.png",canvas,50,715,190,142);
        InsertaImagenLogo(imageData,canvas,50,715,190);
        canvas.add(a).close();
        canvas.add(b).close();
        canvas.close();

        return (nextpage);
    }
    private void InsertaFotos(String Periodicidad, PdfPage page,int Numero,String rutacarpeta,int extra,PdfFont font,String [] Descripcion,Boolean fisrt){
        int Identificador;
        int Foto1;
        int Foto2 ;
        int Foto3;
        int Foto4 ;
        int Foto5 ;
        int Foto6;
        if (fisrt) {
            Identificador = extra * 4;
            Foto1 = Identificador + 1;
            Foto2 = Identificador + 2;
            Foto3 = Identificador + 3;
            Foto4 = Identificador + 4;
            Foto5 = Identificador + 5+4;
            Foto6 = Identificador + 6+4;
        }
        else{
            Identificador = extra * 6;
            Foto1 = Identificador -1;
            Foto2 = Identificador ;
            Foto3 = Identificador +1;
            Foto4 = Identificador + 2;
            Foto5 = Identificador + 3;
            Foto6 = Identificador + 4;
        }
        if (Numero==1){
            if (fisrt) {
                InsertaBordeImagen(page, 50,380,230,172);
                AgregaComentarios(font,page,50, 329, 232, 50,Descripcion[Foto1-1]);
                InsertImagenExtra (page, 50,380,230,172,rutacarpeta,"Foto"+Foto1+".png");

            }
            else{
                InsertaBordeImagen(page, 59, 509, 212, 144);
                AgregaComentarios(font, page, 60, 460, 212, 50, Descripcion[Foto1 - 1]);
                InsertImagenExtra(page, 59, 509, 212, 144, rutacarpeta, "Foto" + Foto1 + ".png");
            }


        }
        else if(Numero ==2){
            if (fisrt){
                InsertaBordeImagen(page, 50,380,230,172);
                InsertaBordeImagen(page, 320,380,230,172);
                AgregaComentarios(font,page,50, 329, 232, 50,Descripcion[Foto1-1]);
                AgregaComentarios(font,page,320,329,230,50,Descripcion[Foto2-1]);
                InsertImagenExtra (page, 50,380,230,172,rutacarpeta,"Foto"+Foto1+".png");
                InsertImagenExtra (page, 320,380,230,172,rutacarpeta,"Foto"+Foto2+".png");


            }

            else{
                InsertaBordeImagen(page, 59, 509, 212, 144);
                InsertaBordeImagen(page, 329, 509, 212, 144);
                AgregaComentarios(font, page, 60, 460, 212, 50, Descripcion[Foto1 - 1]);
                AgregaComentarios(font, page, 330, 460, 212, 50, Descripcion[Foto2 - 1]);
                InsertImagenExtra(page, 59, 509, 212, 144, rutacarpeta, "Foto" + Foto1 + ".png");
                InsertImagenExtra(page, 329, 509, 212, 144, rutacarpeta, "Foto" + Foto2 + ".png");
            }


        }
        else if (Numero == 3){

            if (fisrt){
                InsertaBordeImagen(page, 50,379,230,172);
                InsertaBordeImagen(page, 320,379,230,172);
                InsertaBordeImagen(page, 175,160,230,172);


                AgregaComentarios(font,page,50, 329, 232, 50,Descripcion[Foto1-1]);
                AgregaComentarios(font,page,320,329,230,50,Descripcion[Foto2-1]);
                AgregaComentarios(font,page,175,99,230,50,Descripcion[Foto3-1]);

                InsertImagenExtra (page, 50,379,230,172,rutacarpeta,"Foto"+Foto1+".png");
                InsertImagenExtra (page, 320,379,230,172,rutacarpeta,"Foto"+Foto2+".png");
                InsertImagenExtra (page, 175,160,230,172,rutacarpeta,"Foto"+Foto3+".png");


            }else {
                InsertaBordeImagen(page, 59, 509, 212, 144);
                InsertaBordeImagen(page, 329, 509, 212, 144);
                InsertaBordeImagen(page, 180, 309, 212, 144);
                AgregaComentarios(font, page, 60, 460, 212, 50, Descripcion[Foto1 - 1]);
                AgregaComentarios(font, page, 330, 460, 212, 50, Descripcion[Foto2 - 1]);
                AgregaComentarios(font, page, 180, 260, 212, 50, Descripcion[Foto3 - 1]);
                InsertImagenExtra(page, 59, 509, 212, 144, rutacarpeta, "Foto" + Foto1 + ".png");
                InsertImagenExtra(page, 329, 509, 212, 144, rutacarpeta, "Foto" + Foto2 + ".png");
                InsertImagenExtra(page, 179, 309, 212, 144, rutacarpeta, "Foto" + Foto3 + ".png");
            }

        }
        else if (Numero == 4){
            if (fisrt){
                InsertaBordeImagen(page, 50,380,230,172);
                InsertaBordeImagen(page, 320,380,230,172);
                InsertaBordeImagen(page, 50,160,230,172);
                InsertaBordeImagen(page, 320,160,230,172);

                AgregaComentarios(font,page,50, 329, 232, 50,Descripcion[Foto1-1]);
                AgregaComentarios(font,page,320,329,230,50,Descripcion[Foto2-1]);
                AgregaComentarios(font,page,50,99,230,50,Descripcion[Foto3-1]);
                AgregaComentarios(font,page,320,99,230,50,Descripcion[Foto4-1]);

                InsertImagenExtra (page, 50,380,230,172,rutacarpeta,"Foto"+Foto1+".png");
                InsertImagenExtra (page, 320,380,230,172,rutacarpeta,"Foto"+Foto2+".png");
                InsertImagenExtra (page, 50,160,230,172,rutacarpeta,"Foto"+Foto3+".png");
                InsertImagenExtra (page, 320,160,230,172,rutacarpeta,"Foto"+Foto4+".png");
            }else {
                InsertaBordeImagen(page, 59, 509, 212, 144);
                InsertaBordeImagen(page, 329, 509, 212, 144);
                InsertaBordeImagen(page, 59, 309, 212, 144);
                InsertaBordeImagen(page, 329, 309, 212, 144);
                AgregaComentarios(font, page, 60, 460, 212, 50, Descripcion[Foto1 - 1]);
                AgregaComentarios(font, page, 330, 460, 212, 50, Descripcion[Foto2 - 1]);
                AgregaComentarios(font, page, 60, 260, 212, 50, Descripcion[Foto3 - 1]);
                AgregaComentarios(font, page, 330, 260, 212, 50, Descripcion[Foto4 - 1]);
                InsertImagenExtra(page, 59, 509, 212, 144, rutacarpeta, "Foto" + Foto1 + ".png");
                InsertImagenExtra(page, 329, 509, 212, 144, rutacarpeta, "Foto" + Foto2 + ".png");
                InsertImagenExtra(page, 59, 309, 212, 144, rutacarpeta, "Foto" + Foto3 + ".png");
                InsertImagenExtra(page, 329, 309, 212, 144, rutacarpeta, "Foto" + Foto4 + ".png");
            }


        }
        else if (Numero == 5){

            InsertaBordeImagen(page, 59, 509,212,144);
            InsertaBordeImagen(page, 329, 509,212,144);
            InsertaBordeImagen(page, 59, 309,212,144);
            InsertaBordeImagen(page, 329, 309,212,144);
            InsertaBordeImagen(page, 180, 109,212,144);
            AgregaComentarios(font,page,60, 460, 212, 50,Descripcion[Foto1-1]);
            AgregaComentarios(font,page,330, 460, 212, 50,Descripcion[Foto2-1]);
            AgregaComentarios(font,page,60, 260, 212, 50,Descripcion[Foto3-1]);
            AgregaComentarios(font,page,330, 260, 212, 50,Descripcion[Foto4-1]);
            AgregaComentarios(font,page,180, 60, 212, 50,Descripcion[Foto5-1]);
            InsertImagenExtra (page, 59, 509,212,144,rutacarpeta,"Foto"+Foto1+".png");
            InsertImagenExtra (page, 329, 509,212,144,rutacarpeta,"Foto"+Foto2+".png");
            InsertImagenExtra (page, 59, 309,212,144,rutacarpeta,"Foto"+Foto3+".png");
            InsertImagenExtra (page, 329, 309,212,144,rutacarpeta,"Foto"+Foto4+".png");
            InsertImagenExtra (page, 180, 109,212,144,rutacarpeta,"Foto"+Foto5+".png");
        }

        else{

            InsertaBordeImagen(page, 59, 509,212,144);
            InsertaBordeImagen(page, 329, 509,212,144);
            InsertaBordeImagen(page, 59, 309,212,144);
            InsertaBordeImagen(page, 329, 309,212,144);
            InsertaBordeImagen(page, 59, 109,212,144);
            InsertaBordeImagen(page, 329, 109,212,144);
            AgregaComentarios(font,page,60, 460, 212, 50,Descripcion[Foto1-1]);
            AgregaComentarios(font,page,330, 460, 212, 50,Descripcion[Foto2-1]);
            AgregaComentarios(font,page,60, 260, 212, 50,Descripcion[Foto3-1]);
            AgregaComentarios(font,page,330, 260, 212, 50,Descripcion[Foto4-1]);
            AgregaComentarios(font,page,60, 60, 212, 50,Descripcion[Foto5-1]);
            AgregaComentarios(font,page,330, 60, 212, 50,Descripcion[Foto6-1]);
            InsertImagenExtra (page, 59, 509,212,144,rutacarpeta,"Foto"+Foto1+".png");
            InsertImagenExtra (page, 329, 509,212,144,rutacarpeta,"Foto"+Foto2+".png");
            InsertImagenExtra (page, 59, 309,212,144,rutacarpeta,"Foto"+Foto3+".png");
            InsertImagenExtra (page, 329, 309,212,144,rutacarpeta,"Foto"+Foto4+".png");
            InsertImagenExtra (page, 59, 109,212,144,rutacarpeta,"Foto"+Foto5+".png");
            InsertImagenExtra (page, 329, 109,212,144,rutacarpeta,"Foto"+Foto6+".png");
        }





    }
    private void AñadirLineas(PdfPage page){
        PdfCanvas canvas = new PdfCanvas(page);

        // Create a 100% Magenta color

        canvas
                .moveTo(135, 650)
                .lineTo(565, 650)
                .moveTo(135, 637)
                .lineTo(565, 637)
                .moveTo(135, 623)
                .lineTo(565, 623)
                .moveTo(475, 735)
                .lineTo(540, 735)
                .moveTo(475, 748)
                .lineTo(540, 748)
                .moveTo(462, 762)
                .lineTo(470, 762)
                .setLineWidth(0).closePathStroke();

    }
    private void InsertaBordeImagen(PdfPage page, int x, int y,int w,int h){
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.setLineWidth(0).setColor(ColorConstants.BLACK,false).stroke().closePathStroke();
    }
    private void AgregaComentarios(PdfFont font,PdfPage page,int x, int y,int w,int h,String Comentario){
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle).setColor(ColorConstants.WHITE,false);
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text Comen1 = new Text(Comentario).setFont(font);
        Paragraph a = new Paragraph().add(Comen1)
                .setTextAlignment(TextAlignment.CENTER);
        pdfCanvas.stroke().closePathStroke();
        canvas.add(a).close();
        canvas.close();
    }
    private void InsertImagenExtra (PdfPage page, int x, int y,int w,int h,String rutacarpeta,String nombre){
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
        canvas.add(img).close();
        canvas.close();

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
    private void AñadeContenidoCuadrosDatosFotos(PdfFont font,PdfPage page,PdfFont bold,String[]Datos){
        AgregaContenidoCuadroDatos(bold, page, 370, 762, 100, 11, "Fecha",8);
        AgregaContenidoCuadroDatos(bold, page, 370, 749, 100, 11, "Hora",8);
        AgregaContenidoCuadroDatos(bold, page, 370, 736, 100, 11, "No de reporte:",8);
        AgregaContenido(font, page, 480, 762, 50, 11, Datos[17],8);
        AgregaContenido(font, page, 480, 749, 50, 11, Datos[18],8);
        AgregaContenido(font, page, 480, 736, 50, 11, Datos[12],8);

        AgregaContenido(bold, page, 55, 650, 80, 11, "Aduana:",8);
        AgregaContenido(bold, page, 55, 638, 80, 11, "Equipo:",8);
        AgregaContenidoCuadroDatos(bold, page, 300, 638, 80, 11, "No de serie:",8);
        AgregaContenido(bold, page, 55, 626, 80, 11, "Ubicación del equipo:",8);

        AgregaContenido(font, page, 139, 651, 80, 11, Datos[13],8);
        AgregaContenido(font, page, 139, 638, 80, 11, Datos[14],8);
        AgregaContenidoCuadroDatos(font, page, 360, 638, 80, 11, Datos[16],8);
        AgregaContenido(font, page, 139, 625, 80, 11, Datos[15],8);
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
    private  void Fotos2(PdfPage page,PdfDocument pdf,String [] Datos,PdfFont font,PdfFont bold,ImageData imageData,int id,String[]Comentarios,String rutacarpetaImagenes){
        AñadirLineas(page);
        AgregaContenidoCuadroDatos(bold, page, 370, 764, 100, 11, "0"+Datos[10],8);
        AgregaContenidoCuadroDatos(bold, page, 370, 750, 100, 11, "No. de reporte:",8);
        AgregaContenidoCuadroDatos(bold, page, 370, 736, 100, 11,"Fecha" ,8);
        AgregaContenido(font, page, 480, 764, 70, 11, "Trimestre de servicio",8);
        AgregaContenido(font, page, 480, 750, 70, 11, Datos[12],8);
        AgregaContenido(font, page, 480, 736, 70, 11, Datos[19],8);

        AgregaContenido(bold, page, 55, 650, 80, 11, "Aduana:",8);
        AgregaContenido(bold, page, 55, 638, 80, 11, "Equipo:",8);
        AgregaContenido(bold, page, 300, 638, 80, 11, "No. de serie:",8);
        AgregaContenido(bold, page, 55, 626, 80, 11, "Ubicación del equipo:",8);
        AgregaContenido(font, page, 139, 651, 400, 11, Datos[13],8);
        AgregaContenido(font, page, 139, 638, 140, 11, Datos[14],8);
        AgregaContenido(font, page, 360, 638, 199, 11, Datos[16],8);
        AgregaContenido(font, page, 139, 625, 400, 11, Datos[15],8);
        String[]Fotos = new String[id];
        String[]ComentariosNew = new String[id];
        PdfPage nextpage;
        int NumerorealFotos =0;
        int NumberFotos;
        for (int i =0; i<id;i++){
            if (Comentarios[i].equals(""))System.out.println("FotoDescartada");
            else {
                ComentariosNew[NumerorealFotos]=Comentarios[i];
                Fotos[NumerorealFotos]="Foto"+String.valueOf(i+1);
                System.out.println(Fotos[NumerorealFotos]);
                NumerorealFotos++;
            }
        }
        System.out.println(NumerorealFotos);


        int Paginasextran;
        if ((NumerorealFotos+2) % 6 == 0) {
            Paginasextran = ((NumerorealFotos+2) / 6);
        }
        else {
            Paginasextran = ((NumerorealFotos+2) / 6)+1;

        }

        System.out.println("Paginas extra"+Paginasextran);
        NumberFotos = 6;
        int ident = 0;
        int b =0;
        Boolean first;
        for (int i = 0; i < Paginasextran; i++) {
            if(i==0){
                if (NumerorealFotos <= 4){
                    NumberFotos = 4 - (4 - NumerorealFotos);
                    nextpage = page;
                    first = true;
                    InsertaFotos2(nextpage, NumberFotos, rutacarpetaImagenes, ident, font, ComentariosNew,first,i,Fotos);
                }

                else{
                    NumberFotos = 4;
                    nextpage = page;
                    first = true;
                    InsertaFotos2(nextpage, NumberFotos, rutacarpetaImagenes, ident, font, ComentariosNew,first,i,Fotos);
                }


            }
            else {
                NumberFotos = 6;
                if (i == Paginasextran - 1) {

                    //NumberFotos =  ((Paginasextran * 6) - (id-4)-6);
                    NumberFotos = NumerorealFotos +(4-b)-(Paginasextran*4);
                }
                first = false;
                nextpage = CuadroNextPage(pdf, Datos, font, bold, imageData);
                InsertaFotos2(nextpage, NumberFotos, rutacarpetaImagenes, ident, font, ComentariosNew,first,i,Fotos);
                b=b+2;
            }



            ident++;
        }
    }
    private void InsertaFotos2(PdfPage page,int Numero,String rutacarpeta,int extra,PdfFont font,String [] Descripcion,Boolean fisrt,int i,String[]NombresFoto){
        String Nombre1 = null,Nombre2 = null,Nombre3=null,Nombre4=null,Nombre5=null,Nombre6=null;
        int base = 9;
        if (i==0){
            if (Numero == 1){Nombre1 = NombresFoto[0];}
            else if (Numero ==2){Nombre1 = NombresFoto[0];Nombre2 = NombresFoto[1];}
            else if(Numero==3){Nombre1 = NombresFoto[0];Nombre2 = NombresFoto[1];Nombre3 = NombresFoto[2];}
            else{Nombre1 = NombresFoto[0];Nombre2 = NombresFoto[1];Nombre3 = NombresFoto[2];Nombre4 = NombresFoto[3];}
        }
        else if (i==1) {

            if (Numero == 1){Nombre1 = NombresFoto[3+Numero];}
            else if(Numero ==2){Nombre1 = NombresFoto[3+Numero-1];Nombre2 = NombresFoto[(3+Numero)];}
            else if(Numero==3){Nombre1 = NombresFoto[3+Numero-2];Nombre2 = NombresFoto[3+Numero-1];Nombre3 = NombresFoto[3+Numero];}
            else if(Numero==4){
                Nombre1 = NombresFoto[3+Numero-3];Nombre2 = NombresFoto[3+Numero-2];Nombre3 = NombresFoto[3+Numero-1];Nombre4= NombresFoto[3+Numero];
            }
            else if(Numero==5){
                Nombre1 = NombresFoto[3+Numero-4];Nombre2 = NombresFoto[3+Numero-3];Nombre3 = NombresFoto[3+Numero-2];Nombre4= NombresFoto[3+Numero-1];
                Nombre5= NombresFoto[3+Numero];
            }
            else{
                Nombre1 = NombresFoto[3+Numero-5];Nombre2 = NombresFoto[3+Numero-4];Nombre3 = NombresFoto[3+Numero-3];Nombre4= NombresFoto[3+Numero-2];
                Nombre5= NombresFoto[3+Numero-1]; Nombre6= NombresFoto[3+Numero];
            }
        }
        else{
            if (Numero == 1){Nombre1 = NombresFoto[base+(6*(i-2))+Numero];}
            else if(Numero ==2){Nombre1 = NombresFoto[base+(6*(i-2))+Numero-1];Nombre2 = NombresFoto[base+(6*(i-2))+Numero];}
            else if(Numero==3){Nombre1 = NombresFoto[base+(6*(i-2))+Numero-2];Nombre2 = NombresFoto[base+(6*(i-2))+Numero-1];Nombre3 =NombresFoto[base+(6*(i-2))+Numero];}
            else if(Numero==4){
                Nombre1 = NombresFoto[base+(6*(i-2))+Numero-3];Nombre2 = NombresFoto[base+(6*(i-2))+Numero-2];
                Nombre3 = NombresFoto[base+(6*(i-2))+Numero-1];Nombre4= NombresFoto[base+(6*(i-2))+Numero];
            }
            else if(Numero==5){
                Nombre1 = NombresFoto[base+(6*(i-2))+Numero-4];Nombre2 = NombresFoto[base+(6*(i-2))+Numero-3];Nombre3 = NombresFoto[base+(6*(i-2))+Numero-2];
                Nombre4= NombresFoto[base+(6*(i-2))+Numero-1];
                Nombre5= NombresFoto[base+(6*(i-2))+Numero];
            }
            else{
                Nombre1 =NombresFoto[base+(6*(i-2))+Numero-5];Nombre2 = NombresFoto[base+(6*(i-2))+Numero-4];Nombre3 = NombresFoto[base+(6*(i-2))+Numero-3];
                Nombre4= NombresFoto[base+(6*(i-2))+Numero-2];
                Nombre5= NombresFoto[base+(6*(i-2))-1]; Nombre6= NombresFoto[base+(6*(i-2))+Numero];
            }
        }
        System.out.println("Nombre"+Nombre1);
        System.out.println("Carpeta:"+rutacarpeta);


        int Identificador;
        int Foto1;
        int Foto2 ;
        int Foto3;
        int Foto4 ;
        int Foto5 ;
        int Foto6;
        if (fisrt) {
            Identificador = extra * 4;
            Foto1 = Identificador + 1;
            Foto2 = Identificador + 2;
            Foto3 = Identificador + 3;
            Foto4 = Identificador + 4;
            Foto5 = Identificador + 5+4;
            Foto6 = Identificador + 6+4;
        }
        else{
            Identificador = extra * 6;
            Foto1 = Identificador -1;
            Foto2 = Identificador ;
            Foto3 = Identificador +1;
            Foto4 = Identificador + 2;
            Foto5 = Identificador + 3;
            Foto6 = Identificador + 4;
        }
        if (Numero==1){
            if (fisrt) {
                InsertaBordeImagen(page, 50,380,230,172);
                AgregaComentarios(font,page,50, 329, 232, 50,Descripcion[Foto1-1]);
                InsertImagenExtra (page, 50,380,230,172,rutacarpeta,Nombre1+".png");

            }
            else{
                InsertaBordeImagen(page, 59, 509, 212, 144);
                AgregaComentarios(font, page, 60, 460, 212, 50, Descripcion[Foto1 - 1]);
                InsertImagenExtra(page, 59, 509, 212, 144, rutacarpeta, Nombre1 + ".png");
            }


        }
        else if(Numero ==2){
            if (fisrt){
                InsertaBordeImagen(page, 50,380,230,172);
                InsertaBordeImagen(page, 320,380,230,172);
                AgregaComentarios(font,page,50, 329, 232, 50,Descripcion[Foto1-1]);
                AgregaComentarios(font,page,320,329,230,50,Descripcion[Foto2-1]);
                InsertImagenExtra (page, 50,380,230,172,rutacarpeta,Nombre1+".png");
                InsertImagenExtra (page, 320,380,230,172,rutacarpeta,Nombre2+".png");


            }

            else{
                InsertaBordeImagen(page, 59, 509, 212, 144);
                InsertaBordeImagen(page, 329, 509, 212, 144);
                AgregaComentarios(font, page, 60, 460, 212, 50, Descripcion[Foto1 - 1]);
                AgregaComentarios(font, page, 330, 460, 212, 50, Descripcion[Foto2 - 1]);
                InsertImagenExtra(page, 59, 509, 212, 144, rutacarpeta, Nombre1 + ".png");
                InsertImagenExtra(page, 329, 509, 212, 144, rutacarpeta, Nombre2 + ".png");
            }


        }
        else if (Numero == 3){

            if (fisrt){
                InsertaBordeImagen(page, 50,379,230,172);
                InsertaBordeImagen(page, 320,379,230,172);
                InsertaBordeImagen(page, 175,160,230,172);


                AgregaComentarios(font,page,50, 329, 232, 50,Descripcion[Foto1-1]);
                AgregaComentarios(font,page,320,329,230,50,Descripcion[Foto2-1]);
                AgregaComentarios(font,page,175,99,230,50,Descripcion[Foto3-1]);

                InsertImagenExtra (page, 50,379,230,172,rutacarpeta,Nombre1+".png");
                InsertImagenExtra (page, 320,379,230,172,rutacarpeta,Nombre2+".png");
                InsertImagenExtra (page, 175,160,230,172,rutacarpeta,Nombre3+".png");


            }else {
                InsertaBordeImagen(page, 59, 509, 212, 144);
                InsertaBordeImagen(page, 329, 509, 212, 144);
                InsertaBordeImagen(page, 180, 309, 212, 144);
                AgregaComentarios(font, page, 60, 460, 212, 50, Descripcion[Foto1 - 1]);
                AgregaComentarios(font, page, 330, 460, 212, 50, Descripcion[Foto2 - 1]);
                AgregaComentarios(font, page, 180, 260, 212, 50, Descripcion[Foto3 - 1]);
                InsertImagenExtra(page, 59, 509, 212, 144, rutacarpeta, Nombre1 + ".png");
                InsertImagenExtra(page, 329, 509, 212, 144, rutacarpeta, Nombre2 + ".png");
                InsertImagenExtra(page, 179, 309, 212, 144, rutacarpeta, Nombre3 + ".png");
            }

        }
        else if (Numero == 4){
            if (fisrt){
                InsertaBordeImagen(page, 50,380,230,172);
                InsertaBordeImagen(page, 320,380,230,172);
                InsertaBordeImagen(page, 50,160,230,172);
                InsertaBordeImagen(page, 320,160,230,172);

                AgregaComentarios(font,page,50, 329, 232, 50,Descripcion[Foto1-1]);
                AgregaComentarios(font,page,320,329,230,50,Descripcion[Foto2-1]);
                AgregaComentarios(font,page,50,99,230,50,Descripcion[Foto3-1]);
                AgregaComentarios(font,page,320,99,230,50,Descripcion[Foto4-1]);

                InsertImagenExtra (page, 50,380,230,172,rutacarpeta,Nombre1+".png");
                InsertImagenExtra (page, 320,380,230,172,rutacarpeta,Nombre2+".png");
                InsertImagenExtra (page, 50,160,230,172,rutacarpeta,Nombre3+".png");
                InsertImagenExtra (page, 320,160,230,172,rutacarpeta,Nombre4+".png");
            }else {
                InsertaBordeImagen(page, 59, 509, 212, 144);
                InsertaBordeImagen(page, 329, 509, 212, 144);
                InsertaBordeImagen(page, 59, 309, 212, 144);
                InsertaBordeImagen(page, 329, 309, 212, 144);
                AgregaComentarios(font, page, 60, 460, 212, 50, Descripcion[Foto1 - 1]);
                AgregaComentarios(font, page, 330, 460, 212, 50, Descripcion[Foto2 - 1]);
                AgregaComentarios(font, page, 60, 260, 212, 50, Descripcion[Foto3 - 1]);
                AgregaComentarios(font, page, 330, 260, 212, 50, Descripcion[Foto4 - 1]);
                InsertImagenExtra(page, 59, 509, 212, 144, rutacarpeta, Nombre1 + ".png");
                InsertImagenExtra(page, 329, 509, 212, 144, rutacarpeta, Nombre2 + ".png");
                InsertImagenExtra(page, 59, 309, 212, 144, rutacarpeta, Nombre3 + ".png");
                InsertImagenExtra(page, 329, 309, 212, 144, rutacarpeta, Nombre4 + ".png");
            }


        }
        else if (Numero == 5){

            InsertaBordeImagen(page, 59, 509,212,144);
            InsertaBordeImagen(page, 329, 509,212,144);
            InsertaBordeImagen(page, 59, 309,212,144);
            InsertaBordeImagen(page, 329, 309,212,144);
            InsertaBordeImagen(page, 180, 109,212,144);
            AgregaComentarios(font,page,60, 460, 212, 50,Descripcion[Foto1-1]);
            AgregaComentarios(font,page,330, 460, 212, 50,Descripcion[Foto2-1]);
            AgregaComentarios(font,page,60, 260, 212, 50,Descripcion[Foto3-1]);
            AgregaComentarios(font,page,330, 260, 212, 50,Descripcion[Foto4-1]);
            AgregaComentarios(font,page,180, 60, 212, 50,Descripcion[Foto5-1]);
            InsertImagenExtra (page, 59, 509,212,144,rutacarpeta,Nombre1+".png");
            InsertImagenExtra (page, 329, 509,212,144,rutacarpeta,Nombre2+".png");
            InsertImagenExtra (page, 59, 309,212,144,rutacarpeta,Nombre3+".png");
            InsertImagenExtra (page, 329, 309,212,144,rutacarpeta,Nombre4+".png");
            InsertImagenExtra (page, 180, 109,212,144,rutacarpeta,Nombre5+".png");
        }

        else{

            InsertaBordeImagen(page, 59, 509,212,144);
            InsertaBordeImagen(page, 329, 509,212,144);
            InsertaBordeImagen(page, 59, 309,212,144);
            InsertaBordeImagen(page, 329, 309,212,144);
            InsertaBordeImagen(page, 59, 109,212,144);
            InsertaBordeImagen(page, 329, 109,212,144);
            AgregaComentarios(font,page,60, 460, 212, 50,Descripcion[Foto1-1]);
            AgregaComentarios(font,page,330, 460, 212, 50,Descripcion[Foto2-1]);
            AgregaComentarios(font,page,60, 260, 212, 50,Descripcion[Foto3-1]);
            AgregaComentarios(font,page,330, 260, 212, 50,Descripcion[Foto4-1]);
            AgregaComentarios(font,page,60, 60, 212, 50,Descripcion[Foto5-1]);
            AgregaComentarios(font,page,330, 60, 212, 50,Descripcion[Foto6-1]);
            InsertImagenExtra (page, 59, 509,212,144,rutacarpeta,Nombre1+".png");
            InsertImagenExtra (page, 329, 509,212,144,rutacarpeta,Nombre2+".png");
            InsertImagenExtra (page, 59, 309,212,144,rutacarpeta,Nombre3+".png");
            InsertImagenExtra (page, 329, 309,212,144,rutacarpeta,Nombre4+".png");
            InsertImagenExtra (page, 59, 109,212,144,rutacarpeta,Nombre5+".png");
            InsertImagenExtra (page, 329, 109,212,144,rutacarpeta,Nombre6+".png");
        }

    }
    //////// REPORTE DIGITAL
    public void CreaReporteDigital(String[] Datos,ImageData imageData,String []Diagnostico,int Tamañoletra)throws IOException {
        String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
        String rutacarpeta = "ProyectoX/"+"Correctivo"+"/" + Datos[0] + "/";
        String Name = Datos[0]+"(RD).pdf";
        String DEST = ExternalStorageDirectory+rutacarpeta+Name;
        PdfDocument pdf = new PdfDocument(new PdfWriter(DEST));
        PdfPage page = pdf.addNewPage();
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
        CuadroDatosRD(Datos,pdfCanvas,font,bold,36, 720, 530, 60,imageData);
        CuadroDatos2(Datos,pdfCanvas,font,bold,36, 640, 530, 50);
        Falla(Datos,pdfCanvas,font,bold,36, 565, 530, 50);
        AtencionTelefonica(Datos,pdfCanvas,font,bold,36, 500, 530, 50);
        Sitio(Datos,pdfCanvas,font,bold,36, 410, 530, 50);
        Cierre(Datos,pdfCanvas,font,bold,36, 190, 530, 40);
        Aceptacion(Datos,pdfCanvas,font,bold,36, 170, 530, 15);
        InsertaCuadrosCheck(page,Diagnostico[4]);
        AñadirLineas2(page,Tamañoletra);
        AgregaFalla(font, page, 130, 566, 435, 48, Datos[21],Tamañoletra);
        AgregaFalla(bold, page, 340, 501, 90, 33, "¿Se atendio dentro de las dos horas?",9);
        AgregaFalla(bold, page, 450, 507, 100, 20, "Si                    No",9);
        AgregaFalla(bold, page, 170, 507, 100, 20, "Si                    No",9);
        AgregaFalla(bold, page, 170, 507, 100, 20, "Si                    No",9);
        AgregaFalla(font, page, 182, 411, 382, 48, Diagnostico[0],8);
        AgregaFalla(font, page, 182, 361, 382, 48, Diagnostico[1],8);
        AgregaFalla(font, page, 182, 311, 382, 48, Diagnostico[2],8);
        AgregaFalla(font, page, 182, 261, 382, 48, Diagnostico[3],8);
        PdfPage page2 = pdf.addNewPage();
        InsertabordesCredenciales(page2);
        InsertaImagenesCredenciales(page2,font,Datos);
        AñadeContenidoCuadrosDatos(font,page,bold,Datos);
        //Fotos(page,pdf,Datos,font,bold,imageData,id,Comentarios,rutacarpetaImagenes);
        InsertaPie(pdf);
        pdf.close();

    }
    private void CuadroDatosRD(String[] Datos,PdfCanvas pdfCanvas,PdfFont font,PdfFont bold,int x ,int y , int w , int h,ImageData imageData){
        Rectangle rectangle = new Rectangle(x,y,w,h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text title = new Text("REPORTE DE MANTENIMIENTO CORRECTIVO").setFont(bold).setFontSize(9);
        Text Fecha1 = new Text("Fecha:").setFont(font).setFontSize(9);
        Text Hora = new Text("Hora:").setFont(font).setFontSize(9);
        Text NReporte = new Text("No de reporte:").setFont(font).setFontSize(9);

        Text TICKET = new Text(Datos[12]).setFont(font).setFontSize(9);
        Text FECHA = new Text(Datos[17]).setFont(font).setFontSize(9);
        Text HORAINICIO = new Text(Datos[18]).setFont(font).setFontSize(9);

        Text SaltoLinea = new Text(" \n ").setFont(font).setFontSize(9);
        Text Space = new Text(".  .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);

        Paragraph a = new Paragraph().add(title).add(Space).add(SaltoLinea)
                //.add(Fecha1).add(Space).add(FECHA).add(Space).add(Space).add(Space).add(SaltoLinea)
                //.add(Hora).add(Space).add(HORAINICIO).add(Space).add(Space).add(Space).add(Space).add(SaltoLinea)
                //add(NReporte).add(Space).add(TICKET).add(Space).add(Space)
                .setTextAlignment(TextAlignment.RIGHT);
        InsertaImagenLogo(imageData,canvas,50,722,150);
        canvas.add(a);
        canvas.close();

    }
    private void Falla(String[] Datos,PdfCanvas pdfCanvas,PdfFont font,PdfFont bold,int x,int y, int w,int h){
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke();

        Canvas canvas = new Canvas(pdfCanvas, rectangle);

        Text Falla= new Text("Falla Reportada:").setFont(bold).setFontSize(9);
        Text Fallar= new Text(Datos[21].replaceAll("(\n|\r)", " ")).setFont(font).setFontSize(8);
        Text SaltoLinea = new Text(" \n ").setFont(font).setFontSize(9);
        Text Space = new Text(".  .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);
        Text Space3 = new Text(". .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);
        Text Space2 = new Text(".                                    .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);

        Paragraph a = new Paragraph().add(Space).add(Falla).add(Space3)
                //.add(Fallar).add(Space).add(SaltoLinea)
                .add(Space).setTextAlignment(TextAlignment.LEFT);

        canvas.add(a);
        Rectangle rectangle2 = new Rectangle(x, y+h, w, 15);
        pdfCanvas.rectangle(rectangle2);
        pdfCanvas.stroke();
        Canvas canvas2 = new Canvas(pdfCanvas, rectangle2);
        Text DatosEquipo = new Text("Datos de la falla (Reportado por personal de la Aduana)").setFont(bold).setFontSize(9);
        Paragraph b = new Paragraph().add(DatosEquipo).setTextAlignment(TextAlignment.CENTER);
        canvas2.add(b);

        canvas.close();
        canvas2.close();
    }
    private void AtencionTelefonica(String[] Datos,PdfCanvas pdfCanvas,PdfFont font,PdfFont bold,int x,int y, int w,int h){
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke();

        Canvas canvas = new Canvas(pdfCanvas, rectangle);

        Text Llamada = new Text("Fecha / Hora de la llamada:").setFont(bold).setFontSize(9);
        Text NombreTecnico= new Text("Nombre del técnico").setFont(bold).setFontSize(9);
        Text Telefónica= new Text("¿Se resolvio vía telefónica?").setFont(bold).setFontSize(9);
        Text FechaLlamada= new Text("              "+Datos[22]).setFont(font).setFontSize(9);
        Text HoraLlamada = new Text(Datos[23]).setFont(font).setFontSize(9);
        Text nombreTecnico = new Text(Datos[26]).setFont(font).setFontSize(9);
        Text SaltoLinea = new Text(" \n ").setFont(font).setFontSize(9);
        Text Space = new Text(".  .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);
        Text Space3 = new Text(". .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);
        Text Space2 = new Text(".                          .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);

        Paragraph a = new Paragraph().add(Space).add(Llamada).add(Space3).add(FechaLlamada).add(Space).add(Space).add(Space).add(HoraLlamada)
                .add(Space2).add(NombreTecnico).add(Space).add(nombreTecnico)
                .add(Space).add(SaltoLinea).add(SaltoLinea).add(Space).add(Telefónica)
                .add(Space).setTextAlignment(TextAlignment.LEFT);

        canvas.add(a);
        Rectangle rectangle2 = new Rectangle(x, y+h, w, 15);
        pdfCanvas.rectangle(rectangle2);
        pdfCanvas.stroke();
        Canvas canvas2 = new Canvas(pdfCanvas, rectangle2);
        Text DatosEquipo = new Text("Atención telefónica").setFont(bold).setFontSize(9);
        Paragraph b = new Paragraph().add(DatosEquipo).setTextAlignment(TextAlignment.CENTER);
        canvas2.add(b);

        canvas.close();
        canvas2.close();
    }
    private void Sitio(String[] Datos,PdfCanvas pdfCanvas,PdfFont font,PdfFont bold,int x,int y, int w,int h){
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke();

        Canvas canvas = new Canvas(pdfCanvas, rectangle);

        Text Diagnostico= new Text("Diagnóstico (Causa del problema):").setFont(bold).setFontSize(9);
        Text Fechallegada = new Text("              "+Datos[24]).setFont(font).setFontSize(9);
        Text Horallegada = new Text(Datos[25]).setFont(font).setFontSize(9);
        Text SaltoLinea = new Text(" \n ").setFont(font).setFontSize(9);
        Text Space = new Text(".  .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);
        Text Space3 = new Text(". .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);
        Text Space2 = new Text(".                                           .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);

        Paragraph a = new Paragraph().add(Space).add(Diagnostico)
                .add(Space).setTextAlignment(TextAlignment.LEFT);

        canvas.add(a);
        Rectangle rectangle2 = new Rectangle(x, y+h+20, w, 15);
        pdfCanvas.rectangle(rectangle2);
        pdfCanvas.stroke();
        Canvas canvas2 = new Canvas(pdfCanvas, rectangle2);
        Text DatosEquipo = new Text("Visita en sitio").setFont(bold).setFontSize(9);
        Paragraph b = new Paragraph().add(DatosEquipo).setTextAlignment(TextAlignment.CENTER);
        canvas2.add(b);

        Rectangle rectangle3 = new Rectangle(x, y+h, w, 20);
        pdfCanvas.rectangle(rectangle3);
        pdfCanvas.stroke();
        Canvas canvas3 = new Canvas(pdfCanvas, rectangle3);
        Text FechaLlegada= new Text("Fecha y hora de la llegada:").setFont(bold).setFontSize(9);
        Paragraph c = new Paragraph().add(Space).add(FechaLlegada).add(Space).add(Space3).add(Fechallegada).add(Space).add(Space).add(Space).add(Horallegada).setTextAlignment(TextAlignment.LEFT);
        canvas3.add(c);

        Rectangle rectangle4 = new Rectangle(x, y-h, w, h);
        pdfCanvas.rectangle(rectangle4);
        pdfCanvas.stroke();
        Canvas canvas4 = new Canvas(pdfCanvas, rectangle4);
        Text ACorrectivas= new Text("Acciones correctivas:").setFont(bold).setFontSize(9);
        Paragraph d = new Paragraph().add(Space).add(ACorrectivas)
                .add(Space).setTextAlignment(TextAlignment.LEFT);
        canvas4.add(d);

        Rectangle rectangle5 = new Rectangle(x, y-h-h, w, h);
        pdfCanvas.rectangle(rectangle5);
        pdfCanvas.stroke();
        Canvas canvas5 = new Canvas(pdfCanvas, rectangle5);
        Text Observaciones= new Text("Observaciones:").setFont(bold).setFontSize(9);
        Paragraph e = new Paragraph().add(Space).add(Observaciones)
                .add(Space).setTextAlignment(TextAlignment.LEFT);
        canvas5.add(e);

        Rectangle rectangle6 = new Rectangle(x, y-h-h-h, w, h);
        pdfCanvas.rectangle(rectangle6);
        pdfCanvas.stroke();
        Canvas canvas6 = new Canvas(pdfCanvas, rectangle6);
        Text Refacciones= new Text("Refacciones:").setFont(bold).setFontSize(9);
        Paragraph f = new Paragraph().add(Space).add(Refacciones)
                .add(Space).setTextAlignment(TextAlignment.LEFT);
        canvas6.add(f);

        canvas.close();
        canvas2.close();
    }
    private void Cierre(String[] Datos,PdfCanvas pdfCanvas,PdfFont font,PdfFont bold,int x,int y, int w,int h){
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke();

        Canvas canvas = new Canvas(pdfCanvas, rectangle);

        Text Reparacion = new Text("Fecha y hora de reparación:").setFont(bold).setFontSize(9);
        Text TiemporReparación= new Text("Tiempo de reparación:                       -96 Horas                   +96 Horas").setFont(bold).setFontSize(9);
        Text FechaReparacion= new Text("              "+Datos[19]).setFont(font).setFontSize(9);
        Text HoraReparacion = new Text(Datos[20]).setFont(font).setFontSize(9);

        Text SaltoLinea = new Text(" \n ").setFont(font).setFontSize(9);
        Text Space = new Text(".  .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);
        Text Space3 = new Text(". .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);
        Text Space2 = new Text(".                                                     .").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);

        Paragraph a = new Paragraph().add(Space).add(Reparacion).add(Space3).add(FechaReparacion).add(Space).add(Space).add(Space).add(HoraReparacion)
                .add(Space).add(SaltoLinea).add(Space).add(Space).add(Space).add(TiemporReparación)
                .add(Space).setTextAlignment(TextAlignment.LEFT);

        canvas.add(a);
        Rectangle rectangle2 = new Rectangle(x, y+h, w, 15);
        pdfCanvas.rectangle(rectangle2);
        pdfCanvas.stroke();
        Canvas canvas2 = new Canvas(pdfCanvas, rectangle2);
        Text DatosEquipo = new Text("Cierre del reporte").setFont(bold).setFontSize(8);
        Paragraph b = new Paragraph().add(DatosEquipo).setTextAlignment(TextAlignment.CENTER);
        canvas2.add(b);

        canvas.close();
        canvas2.close();
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
    private void InsertaPie(PdfDocument pdf ){
        int numberOfPages = pdf.getNumberOfPages();
        for (int i = 1; i <= numberOfPages; i++) {
            Document doc = new Document(pdf);
            // Write aligned text to the specified by parameters point
            doc.showTextAligned(new Paragraph("FO-RX-02VER03"),
                    559, 75, i, TextAlignment.RIGHT, VerticalAlignment.TOP, 0);
        }
    }
    private void InsertaImagenesCredenciales(PdfPage nextpage,PdfFont font,String [] Datos){
        String rutacarpetaImagenesANAM = "ProyectoX/"+"Correctivo"+"/"+Datos[0]+"/";
        String rutacarpetaImagenesSeguritech = "ProyectoX/"+"Correctivo"+"/"+Datos[0]+"/" ;
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
    private void AñadirLineas2(PdfPage page,int TamañoLetra) {
        PdfCanvas canvas = new PdfCanvas(page);
        // Create a 100% Magenta color
        if (TamañoLetra<=7){
            canvas

                    .moveTo(495, 728)
                    .lineTo(560, 728)
                    .moveTo(495, 740)
                    .lineTo(560, 740)
                    .moveTo(495, 753)
                    .lineTo(560, 753)

                    .moveTo(130, 675)
                    .lineTo(565, 675)
                    .moveTo(130, 660)
                    .lineTo(565, 660)
                    .moveTo(130, 647)
                    .lineTo(565, 647)



                    .moveTo(130, 599)
                    .lineTo(565, 599)

                    .moveTo(130, 589)
                    .lineTo(565, 589)

                    .moveTo(130, 579)
                    .lineTo(565, 579)

                    .moveTo(130, 569)
                    .lineTo(565, 569)

                    .moveTo(444, 535)
                    .lineTo(565, 535)

                    .moveTo(190, 535)
                    .lineTo(310, 535)

                    .moveTo(190, 465)
                    .lineTo(310, 465)

                    .moveTo(170, 214)
                    .lineTo(310, 214)

                    .setLineWidth(0).closePathStroke();
        }
        else{
            canvas

                    .moveTo(495, 728)
                    .lineTo(560, 728)
                    .moveTo(495, 740)
                    .lineTo(560, 740)
                    .moveTo(495, 753)
                    .lineTo(560, 753)

                    .moveTo(130, 675)
                    .lineTo(565, 675)
                    .moveTo(130, 660)
                    .lineTo(565, 660)
                    .moveTo(130, 647)
                    .lineTo(565, 647)


                    .moveTo(130, 599)
                    .lineTo(565, 599)
                    .moveTo(130, 586)
                    .lineTo(565, 586)
                    .moveTo(130, 573)
                    .lineTo(565, 573)

                    .moveTo(444, 535)
                    .lineTo(565, 535)

                    .moveTo(190, 535)
                    .lineTo(310, 535)

                    .moveTo(190, 465)
                    .lineTo(310, 465)

                    .moveTo(170, 214)
                    .lineTo(310, 214)

                    .setLineWidth(0).closePathStroke();
        }


        //                .moveTo(115, 707)
        //                .lineTo(581, 707)
        //                .moveTo(115, 694)
        //                .lineTo(581, 694)
        //                .moveTo(115, 682)
        //                .lineTo(581, 682)
        //                .moveTo(475, 753)
        //                .lineTo(573, 753)

    }
    private void chek(PdfPage page,int y, int x){
        PdfCanvas canvas = new PdfCanvas(page);

        // Create a 100% Magenta color

        canvas

                .moveTo(x, y) //y 643
                .lineTo(x+2, y-4) //y-4
                .moveTo(x+2, y-4) //y-4
                .lineTo(x+7, y+3) //y+3
                .closePathStroke();
    }
    private void square(PdfPage page,int y,int x){
        PdfCanvas canvas = new PdfCanvas(page);

        // Create a 100% Magenta color

        canvas

                .moveTo(x, y) //y 643
                .lineTo(x-7, y) //y-4

                .moveTo(x-7, y) //y 643
                .lineTo(x-7, y-7) //y-4

                .moveTo(x-7, y-7) //y-4
                .lineTo(x, y-7) //y+3

                .moveTo(x, y-7) //y-4
                .lineTo(x, y) //y+3
                .closePathStroke();
    }
    private void InsertaCuadrosCheck(PdfPage page,String Diagnostico){
        square(page,210,185);
        square(page,210,256);
        if (Diagnostico.equals("true")){
            chek(page,207,249);
        }else{
            chek(page,207,178);
        }


        square(page,520,190);
        square(page,520,250);

        square(page,520,470);
        square(page,520,530);



        chek(page,517,243);
        chek(page,517,463);
    }
    private void AgregaFalla(PdfFont font,PdfPage page,int x, int y,int w,int h,String Comentario, int tamaño){
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle).setColor(ColorConstants.WHITE,false);
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text Comen1 = new Text(Comentario.replaceAll("(\n|\r)", " ")).setFont(font).setFontSize(tamaño);
        Paragraph a = new Paragraph().add(Comen1)
                .setTextAlignment(TextAlignment.LEFT);
        pdfCanvas.stroke();
        canvas.add(a);
        canvas.close();
    }
    private void AñadeContenidoCuadrosDatos(PdfFont font,PdfPage page,PdfFont bold,String[]Datos){
        AgregaContenidoCuadroDatos(bold, page, 390, 766, 100, 0, "Fecha:",9);
        AgregaContenidoCuadroDatos(bold, page, 390, 754, 100, 0, "Hora:",9);
        AgregaContenidoCuadroDatos(bold, page, 390, 741, 100, 0, "No. de reporte:",9);
        AgregaContenido(font, page, 500, 766, 60, 0, Datos[17],9);
        AgregaContenido(font, page, 500, 754, 60, 0, Datos[18],9);
        AgregaContenido(font, page, 500, 741, 60, 0, Datos[12],9);

        AgregaContenido(bold, page, 45, 688, 90, 0, "Aduana:",9);
        AgregaContenido(bold, page, 45, 674, 90, 0, "Equipo:",9);
        AgregaContenidoCuadroDatos(bold, page, 300, 674, 90, 0, "No. de serie:",9);
        AgregaContenido(bold, page, 45, 661, 90, 0, "Ubicación del equipo:",9);

        AgregaContenido(font, page, 136, 688, 400, 0, Datos[13],9);
        AgregaContenido(font, page, 136, 674, 100, 0, Datos[14],9);
        AgregaContenidoCuadroDatos(font, page, 360, 674, 100, 0, Datos[16],9);
        AgregaContenido(font, page, 136, 661, 400, 0, Datos[15],9);
    }
}
