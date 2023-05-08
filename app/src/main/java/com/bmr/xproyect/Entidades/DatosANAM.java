package com.bmr.xproyect.Entidades;

public class DatosANAM {
    public String getNombreCompleto() {
        return NombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        NombreCompleto = nombreCompleto;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getPuesto() {
        return Puesto;
    }

    public void setPuesto(String puesto) {
        Puesto = puesto;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        Numero = numero;
    }

    String NombreCompleto;
    String Correo;
    String Puesto;
    String Numero;
}
