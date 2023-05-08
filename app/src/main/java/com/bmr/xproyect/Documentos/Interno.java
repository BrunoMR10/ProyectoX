package com.bmr.xproyect.Documentos;

import android.os.Environment;

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
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class Interno {
    public void CreaArchivo(String[] Datos, ImageData imageData, int id, String[]Comentarios)throws IOException {
        String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
        String rutacarpeta = "ProyectoX/"+"Interno"+"/" + Datos[0] + "/";
        String rutacarpetaImagenes = "ProyectoX/"+"Interno/" + Datos[0] + "/" + Datos[0] + "(CF)/";
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
        Text title = new Text("REPORTE FOTOGRÁFICO DE REPORTE INTERNO").setFont(bold).setFontColor(ColorConstants.RED).setFontSize(10);
        Text NReporte = new Text("No de reporte:").setFont(font).setFontSize(9);
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
        Text DatosEquipo = new Text("Datos del equipo").setFont(bold).setFontSize(9);
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
        AgregaContenidoCuadroDatos(bold, page, 370, 750, 100, 11, "No. de reporte:",8);
        AgregaContenidoCuadroDatos(bold, page, 370, 736, 100, 11,"Fecha" ,8);
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
        Text title = new Text("REPORTE FOTOGRÁFICO DE REPORTE INTERNO").setFont(bold).setFontColor(ColorConstants.RED).setFontSize(10);
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
        AgregaContenidoCuadroDatos(bold, page, 370, 750, 100, 11, "Fecha",8);
        AgregaContenidoCuadroDatos(bold, page, 370, 736, 100, 11, "No. de reporte:",8);
        AgregaContenido(font, page, 480, 750, 50, 11, Datos[17],8);
        AgregaContenido(font, page, 480, 736, 50, 11, Datos[12],8);

        AgregaContenido(bold, page, 55, 650, 80, 11, "Aduana:",8);
        AgregaContenido(bold, page, 55, 638, 80, 11, "Equipo:",8);
        AgregaContenidoCuadroDatos(bold, page, 300, 638, 80, 11, "No. de serie:",8);
        AgregaContenido(bold, page, 55, 626, 80, 11, "Ubicación del equipo:",8);

        AgregaContenido(font, page, 139, 651, 80, 11, Datos[13],8);
        AgregaContenido(font, page, 139, 638, 80, 11, Datos[14],8);
        AgregaContenidoCuadroDatos(font, page, 360, 638, 80, 11, Datos[16],8);
        AgregaContenido(font, page, 139, 625, 80, 11, Datos[15],8);
    }
    private  void Fotos2(PdfPage page,PdfDocument pdf,String [] Datos,PdfFont font,PdfFont bold,ImageData imageData,int id,String[]Comentarios,String rutacarpetaImagenes){
        AñadirLineas(page);
        AgregaContenidoCuadroDatos(bold, page, 370, 750, 100, 11, "No. de reporte:",8);
        AgregaContenidoCuadroDatos(bold, page, 370, 736, 100, 11,"Fecha" ,8);
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

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////Reporte Digital/////////////////////////////////////////////////////////

    public void CreaReporteDigital(String[] Datos,ImageData imageData,String []Comentarios,Boolean[]Cheks)throws IOException {
        String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
        String rutacarpeta = "ProyectoX/"+"Interno"+"/" + Datos[0] + "/";
        String rutacarpetaImagenes = "ProyectoX/"+"Interno/" + Datos[0] + "/" + Datos[0] + "(CF)/";
        String Name = Datos[0]+"(RD).pdf";
        String DEST = ExternalStorageDirectory+rutacarpeta+Name;
        PdfDocument pdf = new PdfDocument(new PdfWriter(DEST));
        PdfPage page = pdf.addNewPage();
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
        CuadroDatosRD(Datos,pdfCanvas,font,bold,36, 730, 530, 60,imageData);
        CuadroDatos2(Datos,pdfCanvas,font,bold,36, 660, 530, 50);
        TableActividades(Comentarios,pdfCanvas,font,bold,36, 640, 530, 15);
        Estatus(pdfCanvas,bold,36, 180, 530, 15);
        Aceptacion(Datos,pdfCanvas,font,bold,36, 110, 530, 15);
        AñadirLineas2(page);
        AñadeTodoContenido(font,page,Comentarios[24]);
        AñadeContenidoCuadrosDatos(font,page,bold,Datos);
        AñadeSquare(page,Cheks);
        InsertaPie(pdf);
        pdf.close();

    }
    private void CuadroDatosRD(String[] Datos,PdfCanvas pdfCanvas,PdfFont font,PdfFont bold,int x ,int y , int w , int h,ImageData imageData){
        Rectangle rectangle = new Rectangle(x,y,w,h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text title = new Text("REPORTE INTERNO SEGURITECH").setFont(bold).setFontSize(9);
        Text Fecha1 = new Text("Fecha/hora de inicio:").setFont(font).setFontSize(8);
        Text Fecha2 = new Text("Fecha/hora de terminación:").setFont(font).setFontSize(8);
        Text NReporte = new Text("No. de reporte:").setFont(font).setFontSize(8);

        Text TICKET = new Text(Datos[12]).setFont(font).setFontSize(8);
        Text FECHA = new Text(Datos[17]).setFont(font).setFontSize(8);
        Text HORAINICIO = new Text(Datos[18]).setFont(font).setFontSize(8);
        Text FECHA2 = new Text(Datos[19]).setFont(font).setFontSize(8);
        Text HORAFIN = new Text(Datos[20]).setFont(font).setFontSize(8);

        Text SaltoLinea = new Text(" \n ").setFont(font).setFontSize(8);
        Text Space = new Text(".  .").setFont(font).setUnderline().setFontSize(8).setFontColor(ColorConstants.WHITE);
        Text Space2 = new Text(".                           .").setFont(font).setUnderline().setFontSize(8).setFontColor(ColorConstants.WHITE);
        Paragraph a = new Paragraph().add(title).add(Space).add(SaltoLinea)
                //.add(NReporte).add(Space).add(TICKET).add(Space).add(Space2).add(SaltoLinea)
                //.add(Fecha1).add(Space).add(FECHA).add(Space).add(HORAINICIO).add(Space).add(Space).add(SaltoLinea)
                //.add(Fecha2).add(Space).add(FECHA2).add(Space).add(HORAFIN).add(Space).add(Space)

                .setTextAlignment(TextAlignment.RIGHT);
        InsertaImagenLogo(imageData,canvas,50,732,150);
        canvas.add(a);
        canvas.close();

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

        Text TiemporReparación= new Text("Tiempo de reparación:                 -96 Horas               +96 Horas").setFont(bold).setFontSize(9);
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
        Text SeguritechA = new Text("Responsable por parte de Nuctech").setFont(bold).setFontSize(9);
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
        String rutacarpetaImagenes = "ProyectoX/"+"Interno/" + Datos[0] + "/" + Datos[0] + "(CF)/";
        String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
        ImageData data = null;

        try {
            data = ImageDataFactory.create(ExternalStorageDirectory + rutacarpetaImagenes + "firmaSeguritech.png");
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        Image img = new Image(data,x+40,y-h-h-h-h-h-h,(w/2)-40);
        canvas5.add(img);
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
        data = null;

        try {
            data = ImageDataFactory.create(ExternalStorageDirectory + rutacarpetaImagenes + "firmaNuctec.png");
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        img = new Image(data,x+(w/2)+40,y-h-h-h-h-h-h,(w/2)-40);
        canvas7.add(img);
        canvas7.add(g);


        canvas.close();
        canvas2.close();
    }
    private void TableActividades(String[] Datos,PdfCanvas pdfCanvas,PdfFont font,PdfFont bold,int x,int y, int w,int h){
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text Text = new Text("Lista de actividades realizadas").setFont(bold).setFontSize(9);
        Text SaltoLinea = new Text(" \n ").setFont(font).setFontSize(9);
        Paragraph a = new Paragraph().add(Text).setTextAlignment(TextAlignment.CENTER);
        canvas.add(a);
        canvas.close();
        Rectangle rectangle2 = new Rectangle(x, y-h, w/2, h);
        pdfCanvas.rectangle(rectangle2);
        pdfCanvas.stroke();
        canvas = new Canvas(pdfCanvas, rectangle2);
        Text = new Text("Seleccione la(s) actividades a realizar:").setFont(bold).setFontSize(9);
        a = new Paragraph().add(Text).setTextAlignment(TextAlignment.CENTER);
        canvas.add(a);
        canvas.close();
        Rectangle rectangle3 = new Rectangle(x+(w/2), y-h, w/2, h);
        pdfCanvas.rectangle(rectangle3);
        pdfCanvas.stroke();
        canvas = new Canvas(pdfCanvas, rectangle3);
        Text = new Text("Comentarios:").setFont(bold).setFontSize(9);
        a = new Paragraph().add(Text).setTextAlignment(TextAlignment.CENTER);
        canvas.add(a);
        canvas.close();
        Rectangle rectangle4 = new Rectangle(x, y-h-h-(27*h), w, h+(27*h));
        pdfCanvas.rectangle(rectangle4);
        pdfCanvas.stroke();

        Rectangle rectangle5 = new Rectangle(x+(w/2), y-(5*h), (w/2)-10, (h*3)+1);
        pdfCanvas.rectangle(rectangle5).setLineWidth(0);
        pdfCanvas.stroke();
        canvas = new Canvas(pdfCanvas, rectangle5);
        Text Space = new Text(". ").setFont(font).setUnderline().setFontSize(9).setFontColor(ColorConstants.WHITE);
        Text Text1 = new Text(Datos[0]).setFont(bold).setFontSize(8);
        Text Text2 = new Text(Datos[1]).setFont(bold).setFontSize(8);
        Text Text3 = new Text(Datos[2]).setFont(bold).setFontSize(8);
        a = new Paragraph().add(Space).add(Text1).add(SaltoLinea)
                .add(Space).add(Text2).add(SaltoLinea)
                .add(Space).add(Text3).add(SaltoLinea)
                .setTextAlignment(TextAlignment.LEFT);
        canvas.add(a);
        canvas.close();
        Rectangle rectangle6 = new Rectangle(x+(w/2), y-((8*h))-11, (w/2)-10, (h*3)+1);
        pdfCanvas.rectangle(rectangle6);
        pdfCanvas.stroke();
        canvas = new Canvas(pdfCanvas, rectangle6);
        Text1 = new Text(Datos[3]).setFont(bold).setFontSize(8);
        Text2 = new Text(Datos[4]).setFont(bold).setFontSize(8);
        Text3 = new Text(Datos[5]).setFont(bold).setFontSize(8);
        a = new Paragraph().add(Space).add(Text1).add(SaltoLinea)
                .add(Space).add(Text2).add(SaltoLinea)
                .add(Space).add(Text3).add(SaltoLinea)
                .setTextAlignment(TextAlignment.LEFT);
        canvas.add(a);
        canvas.close();
        Rectangle rectangle7 = new Rectangle(x+(w/2), y-((13*h))-20, (w/2)-10, (h*5)-2);
        pdfCanvas.rectangle(rectangle7);
        pdfCanvas.stroke();
        canvas = new Canvas(pdfCanvas, rectangle7);
        Text1 = new Text(Datos[6]).setFont(bold).setFontSize(8);
        Text2 = new Text(Datos[7]).setFont(bold).setFontSize(8);
        Text3 = new Text(Datos[8]).setFont(bold).setFontSize(8);
        Text Text4 = new Text(Datos[9]).setFont(bold).setFontSize(8);
        Text Text5 = new Text(Datos[10]).setFont(bold).setFontSize(8);
        a = new Paragraph().add(Space).add(Text1).add(SaltoLinea)
                .add(Space).add(Text2).add(SaltoLinea)
                .add(Space).add(Text3).add(SaltoLinea)
                .add(Space).add(Text4).add(SaltoLinea)
                .add(Space).add(Text5).add(SaltoLinea)
                .setTextAlignment(TextAlignment.LEFT);
        canvas.add(a);
        Rectangle rectangle8 = new Rectangle(x+(w/2), y-((18*h))-27, (w/2)-10, (h*5)-2);
        pdfCanvas.rectangle(rectangle8);
        pdfCanvas.stroke();
        canvas = new Canvas(pdfCanvas, rectangle8);
        Text1 = new Text(Datos[11]).setFont(bold).setFontSize(8);
        Text2 = new Text(Datos[12]).setFont(bold).setFontSize(8);
        Text3 = new Text(Datos[13]).setFont(bold).setFontSize(8);
        Text4 = new Text(Datos[14]).setFont(bold).setFontSize(8);
        Text5 = new Text(Datos[15]).setFont(bold).setFontSize(8);
        a = new Paragraph().add(Space).add(Text1).add(SaltoLinea)
                .add(Space).add(Text2).add(SaltoLinea)
                .add(Space).add(Text3).add(SaltoLinea)
                .add(Space).add(Text4).add(SaltoLinea)
                .add(Space).add(Text5).add(SaltoLinea)
                .setTextAlignment(TextAlignment.LEFT);
        canvas.add(a);
        canvas.close();
        Rectangle rectangle9 = new Rectangle(x+(w/2), y-((26*h))-34, (w/2)-10, (h*8)-6);
        pdfCanvas.rectangle(rectangle9);
        pdfCanvas.stroke();
        canvas = new Canvas(pdfCanvas, rectangle9);
        Text1 = new Text(Datos[16]).setFont(bold).setFontSize(8);
        Text2 = new Text(Datos[17]).setFont(bold).setFontSize(8);
        Text3 = new Text(Datos[18]).setFont(bold).setFontSize(8);
        Text4 = new Text(Datos[19]).setFont(bold).setFontSize(8);
        Text5 = new Text(Datos[20]).setFont(bold).setFontSize(8);
        Text Text6 = new Text(Datos[21]).setFont(bold).setFontSize(8);
        Text Text7 = new Text(Datos[22]).setFont(bold).setFontSize(8);
        Text Text8 = new Text(Datos[23]).setFont(bold).setFontSize(8);
        a = new Paragraph().add(Space).add(Text1).add(SaltoLinea)
                .add(Space).add(Text2).add(SaltoLinea)
                .add(Space).add(Text3).add(SaltoLinea)
                .add(Space).add(Text4).add(SaltoLinea)
                .add(Space).add(Text5).add(SaltoLinea)
                .add(Space).add(Text6).add(SaltoLinea)
                .add(Space).add(Text7).add(SaltoLinea)
                .add(Space).add(Text8).add(SaltoLinea)
                .setTextAlignment(TextAlignment.LEFT);
        canvas.add(a);
        canvas.close();
    }
    private void Estatus(PdfCanvas pdfCanvas,PdfFont bold,int x,int y, int w,int h){
        Rectangle rectangle = new Rectangle(x, y, w, h);
        pdfCanvas.rectangle(rectangle).setLineWidth(1);
        pdfCanvas.stroke();
        Canvas canvas = new Canvas(pdfCanvas, rectangle);
        Text Text = new Text("Estatus del equipo").setFont(bold).setFontSize(9);
        Paragraph a = new Paragraph().add(Text).setTextAlignment(TextAlignment.CENTER);
        canvas.add(a);
        canvas.close();

        rectangle = new Rectangle(x, y-h-h-h, w, h+h+h);
        pdfCanvas.rectangle(rectangle);
        pdfCanvas.stroke();
        canvas = new Canvas(pdfCanvas, rectangle);
        Text = new Text("  Observaciones:").setFont(bold).setFontSize(8);
        a = new Paragraph().add(Text).setTextAlignment(TextAlignment.LEFT);
        canvas.add(a);
        canvas.close();
    }
    private void AñadirLineas2(PdfPage page) {
        PdfCanvas canvas = new PdfCanvas(page);
        // Create a 100% Magenta color
        canvas

                .moveTo(460, 737)
                .lineTo(560, 737)
                .moveTo(460, 749)
                .lineTo(560, 749)
                .moveTo(460, 764)
                .lineTo(560, 764)

                .moveTo(130, 695)
                .lineTo(565, 695)
                .moveTo(130, 682)
                .lineTo(565, 682)
                .moveTo(130, 669)
                .lineTo(565, 669)

                .moveTo(302, 594)
                .lineTo(555, 594)
                .moveTo(302, 581)
                .lineTo(555, 581)

                .moveTo(302, 539)
                .lineTo(555, 539)
                .moveTo(302, 526)
                .lineTo(555, 526)

                .moveTo(302, 482)
                .lineTo(555, 482)
                .moveTo(302, 469)
                .lineTo(555, 469)
                .moveTo(302, 456)
                .lineTo(555, 456)
                .moveTo(302, 443)
                .lineTo(555, 443)

                .moveTo(302, 397)
                .lineTo(555, 397)
                .moveTo(302, 384)
                .lineTo(555, 384)
                .moveTo(302, 371)
                .lineTo(555, 371)
                .moveTo(302, 358)
                .lineTo(555, 358)

                .moveTo(302, 313)
                .lineTo(555, 313)
                .moveTo(302, 300)
                .lineTo(555, 300)
                .moveTo(302, 287)
                .lineTo(555, 287)
                .moveTo(302, 274)
                .lineTo(555, 274)
                .moveTo(302, 261)
                .lineTo(555, 261)
                .moveTo(302, 248)
                .lineTo(555, 248)
                .moveTo(302, 233)
                .lineTo(555, 233)
                .moveTo(160, 213)
                .lineTo(280, 213)


                .moveTo(100, 165)
                .lineTo(565, 165)
                .moveTo(100, 153)
                .lineTo(565, 153)
                .moveTo(100, 141)
                .lineTo(565, 141)


                .setLineWidth(0).closePathStroke();



    }
    private void InsertaPie(PdfDocument pdf ){
        int numberOfPages = pdf.getNumberOfPages();
        Text Text = new Text("FO-RX-07VER01").setFontSize(7);
        for (int i = 1; i <= numberOfPages; i++) {
            Document doc = new Document(pdf);
            // Write aligned text to the specified by parameters point
            doc.showTextAligned(new Paragraph(Text),
                    559, 45, i, TextAlignment.RIGHT, VerticalAlignment.TOP, 0);
        }
    }
    private void AñadeTodoContenido(PdfFont font,PdfPage page,String Comentario){
        AgregaContenido(font, page, 60, 605, 80, 15, "Suministro de: ",8);
          AgregaContenido(font, page, 160, 599, 120, 15, "Diésel al chasis. ",8);
          AgregaContenido(font, page, 160, 584, 120, 15, "Diésel al generador eléctrico. ",8);
          AgregaContenido(font, page, 160, 569, 120, 15, "Adblue. ",8);
        //
        AgregaContenido(font, page, 60, 560, 80, 15, "Seguridad radiológica:",8);
          AgregaContenido(font, page, 160, 549, 120, 15, "Toma de niveles. ",8);
          AgregaContenido(font, page, 160, 534, 120, 15, "Calibración. ",8);
          AgregaContenido(font, page, 160, 519, 120, 15, "Curso. ",8);
        //
        AgregaContenido(font, page, 60, 500, 80, 15, "Revisión del THSCAN:",8);
          AgregaContenido(font, page, 160, 489, 120, 15, "En el subsistema de acelerador. ",8);
          AgregaContenido(font, page, 160, 474, 120, 15, "En el subsistema de detector. ",8);
          AgregaContenido(font, page, 160, 454, 120, 15, "En el subsitema de partes mecánicas. ",8);
          AgregaContenido(font, page, 160, 444, 120, 15, "En el subsitema de control eléctrico. ",8);
          AgregaContenido(font, page, 160, 429, 140, 15, "En el subsitema de operación e inspección. ",8);
        //
        AgregaContenido(font, page, 60, 415, 80, 15, "Visita técnica:",8);
          AgregaContenido(font, page, 160, 489-85, 120, 15, "Servicio a chasis Volvo. ",8);
          AgregaContenido(font, page, 160, 474-85, 120, 15, "Servicio a generador eléctrico. ",8);
          AgregaContenido(font, page, 160, 459-85, 120, 15, "Servicio a aire acondicionado. ",8);
          AgregaContenido(font, page, 160, 444-85, 120, 15, "Servicio a extintores. ",8);
          AgregaContenido(font, page, 160, 429-85, 140, 15, "Servicio a CCTV. ",8);
        //
        AgregaContenido(font, page, 60, 335, 80, 15, "Otros:",8);
          AgregaContenido(font, page, 160, 489-85-85, 120, 15, "Documentación. ",8);
          AgregaContenido(font, page, 160, 474-85-85, 120, 15, "Levantamiento. ",8);
          AgregaContenido(font, page, 160, 459-85-85, 120, 15, "Asesoramiento técnico. ",8);
          AgregaContenido(font, page, 160, 444-85-85, 120, 15, "Capacitación. ",8);
          AgregaContenido(font, page, 160, 429-85-85, 140, 15, "Electrificación. ",8);
          AgregaContenido(font, page, 160, 429-85-100, 140, 15, "Cambio de ubicación de unidad THSCAN. ",8);
          AgregaContenido(font, page, 160, 429-85-115, 140, 15, "Cambio de centro de servicio. ",8);

        AgregaContenido(font, page, 100, 136, 463, 43, Comentario,8);
    }
    private void AñadeContenidoCuadrosDatos(PdfFont font,PdfPage page,PdfFont bold,String[]Datos){
        AgregaContenidoCuadroDatos(bold, page, 350, 763, 100, 13, "Fecha/hora de inicio:",8);
        AgregaContenidoCuadroDatos(bold, page, 350, 750, 100, 13, "Fecha/hora de terminación:",8);
        AgregaContenidoCuadroDatos(bold, page, 350, 737, 100, 13, "No. de reporte:",8);
        AgregaContenido(font, page, 460, 763, 100, 13, Datos[17]+Datos[18],8);
        AgregaContenido(font, page, 460, 750, 100, 12, Datos[19]+Datos[20],8);
        AgregaContenido(font, page, 460, 738, 100, 12, Datos[12],8);

        AgregaContenido(bold, page, 50, 695, 80, 12, "Aduana:",8);
        AgregaContenido(bold, page, 50, 683, 80, 11, "Equipo:",8);
        AgregaContenidoCuadroDatos(bold, page, 270, 683, 80, 11, "No. de serie:",8);
        AgregaContenido(bold, page, 50, 671, 80, 12, "Ubicación del equipo:",8);

       AgregaContenido(font, page, 130, 696, 400, 11, Datos[13],8);
        AgregaContenido(font, page, 130, 683, 150, 11, Datos[14],8);
        AgregaContenido(font, page, 365, 683, 199, 11, Datos[16],8);
        AgregaContenido(font, page, 130, 670, 400, 11, Datos[15],8);
    }
    private void AñadeSquare(PdfPage page,Boolean[]Cheks) {
        if (Cheks[0]){
            squarelleno(page,609,48);
        }else{
            square(page, 616, 55);
        }
        if (Cheks[1]){
            squarelleno(page,603,148);
        }else{
            square(page, 610, 155);
        }
        if (Cheks[2]){
            squarelleno(page,588,148);
        }else{
            square(page, 595, 155);
        }
        if (Cheks[3]){
            squarelleno(page,573,148);
        }else{
            square(page, 580, 155);
        }
        //
        if (Cheks[4]){
            squarelleno(page, 564, 48);
        }else{
            square(page, 571, 55);
        }
        if (Cheks[5]){
            squarelleno(page, 553, 148);
        }else{
            square(page, 560, 155);
        }
        if (Cheks[6]){
            squarelleno(page, 538, 148);
        }else{
            square(page, 545, 155);
        }
        if (Cheks[7]){
            squarelleno(page, 523, 148);
        }else{
            square(page, 530, 155);
        }

        //
        if (Cheks[8]){
            squarelleno(page, 504, 48);
        }else{
            square(page, 511, 55);
        }
        if (Cheks[9]){
            squarelleno(page, 493, 148);
        }else{
            square(page, 500, 155);
        }
        if (Cheks[10]){
            squarelleno(page, 478, 148);
        }else{
            square(page, 485, 155);
        }
        if (Cheks[11]){
            squarelleno(page, 463, 148);
        }else{
            square(page, 470, 155);
        }
        if (Cheks[12]){
            squarelleno(page, 448, 148);
        }else{
            square(page, 455, 155);
        }
        if (Cheks[13]){
            squarelleno(page, 433, 148);
        }else{
            square(page, 440, 155);
        }


        //
        if (Cheks[14]){
            squarelleno(page, 419, 48);
        }else{
            square(page, 426, 55);
        }
        if (Cheks[15]){
            squarelleno(page, 408, 148);
        }else{
            square(page, 415, 155);
        }
        if (Cheks[16]){
            squarelleno(page, 393, 148);
        }else{
            square(page, 400, 155);
        }
        if (Cheks[17]){
            squarelleno(page, 378, 148);
        }else{
            square(page, 385, 155);
        }
        if (Cheks[18]){
            squarelleno(page, 363, 148);
        }else{
            square(page, 370, 155);
        }
        if (Cheks[19]){
            squarelleno(page, 348, 148);
        }else{
            square(page, 355, 155);
        }

        //
        if (Cheks[20]){
            squarelleno(page, 339, 48);
        }else{
            square(page,346,55);
        }
        if (Cheks[21]){
            squarelleno(page, 323, 148);
        }else{
            square(page, 330, 155);
        }
        if (Cheks[22]){
            squarelleno(page, 309, 148);
        }else{
            square(page, 315, 155);
        }
        if (Cheks[23]){
            squarelleno(page, 293, 148);
        }else{
            square(page, 300, 155);
        }
        if (Cheks[24]){
            squarelleno(page, 278, 148);
        }else{
            square(page, 285, 155);
        }
        if (Cheks[25]){
            squarelleno(page, 263, 148);
        }else{
            square(page, 270, 155);
        }
        if (Cheks[26]){
            squarelleno(page, 248, 148);
        }else{
            square(page, 255, 155);
        }
        if (Cheks[27]){
            squarelleno(page, 233, 148);
        }else{
            square(page, 240, 155);
        }
        if (Cheks[28]){
            squarelleno(page, 218, 148);
        }else{
            square(page, 225, 155);
        }


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
                .setColor(ColorConstants.BLACK,false).setLineWidth(0).closePathStroke();
    }
    private void squarelleno(PdfPage page,int y,int x) {

        PdfCanvas canvas = new PdfCanvas(page);

        Rectangle rectangle = new Rectangle(x, y, 7, 7);
        canvas.setFillColor(ColorConstants.GRAY);
        canvas.rectangle(rectangle).setColor(ColorConstants.BLACK,false).setLineWidth(1);
        canvas.fillStroke();
        // Create a 100% Magenta color
        y=y+4;
        canvas.closePathFillStroke();
        canvas

                .moveTo(x, y) //y 643
                .lineTo(x+2, y-4) //y-4
                .moveTo(x+2, y-4) //y-4
                .lineTo(x+7, y+3) //y+3
                .setColor(ColorConstants.WHITE,false).setLineWidth(1).closePathStroke();

    }



}
