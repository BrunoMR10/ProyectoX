package com.bmr.xproyect.Entidades;

public class Aduanas {
    public String getAduana() {
        return Aduana;
    }

    public void setAduana(String aduana) {
        Aduana = aduana;
    }

    public String getEntidad() {
        return Entidad;
    }

    public void setEntidad(String entidad) {
        Entidad = entidad;
    }

    public String getMunicipio() {
        return Municipio;
    }

    public void setMunicipio(String municipio) {
        Municipio = municipio;
    }

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String calle) {
        Calle = calle;
    }

    public String getColonia() {
        return Colonia;
    }

    public void setColonia(String colonia) {
        Colonia = colonia;
    }

    public String getCP() {
        return CP;
    }

    public void setCP(String CP) {
        this.CP = CP;
    }

    String Aduana;
    String Entidad;
    String Municipio;
    String Calle;
    String Colonia;
    String CP;

    public String[] getDatos() {
        return Datos;
    }

    public void setDatos(String[] datos) {
        Datos = datos;
    }

    String [] Datos;

    public String getWhere() {
        return Where;
    }

    public void setWhere(String where) {
        Where = where;
    }

    String Where;
}
