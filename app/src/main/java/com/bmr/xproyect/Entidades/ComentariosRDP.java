package com.bmr.xproyect.Entidades;

public class ComentariosRDP {
    public String getComentario1() {
        return Comentario1;
    }

    public void setComentario1(String comentario1) {
        Comentario1 = comentario1;
    }

    public String getComentario2() {
        return Comentario2;
    }

    public void setComentario2(String comentario2) {
        Comentario2 = comentario2;
    }

    String Comentario1,Comentario2;

    public String[] getDatos() {
        return Datos;
    }

    public void setDatos(String[] datos) {
        Datos = datos;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    String []Datos;
    int ID;
}
