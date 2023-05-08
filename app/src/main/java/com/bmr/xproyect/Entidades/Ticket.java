package com.bmr.xproyect.Entidades;

public class Ticket {
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomenclatura() {
        return Nomenclatura;
    }

    public void setNomenclatura(String nomenclatura) {
        Nomenclatura = nomenclatura;
    }

    public String getNombreSeg() {
        return NombreSeg;
    }

    public void setNombreSeg(String nombreSeg) {
        NombreSeg = nombreSeg;
    }

    public String getPuestoSeg() {
        return PuestoSeg;
    }

    public void setPuestoSeg(String puestoSeg) {
        PuestoSeg = puestoSeg;
    }

    public String getTipoServicio() {
        return TipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        TipoServicio = tipoServicio;
    }

    public String getTicket() {
        return Ticket;
    }

    public void setTicket(String ticket) {
        Ticket = ticket;
    }

    public String getTrimestre() {
        return Trimestre;
    }

    public void setTrimestre(String trimestre) {
        Trimestre = trimestre;
    }

    public String getPeriodicidad() {
        return Periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        Periodicidad = periodicidad;
    }

    public String getAduana() {
        return Aduana;
    }

    public void setAduana(String aduana) {
        Aduana = aduana;
    }

    public String getEquipo() {
        return Equipo;
    }

    public void setEquipo(String equipo) {
        Equipo = equipo;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
    }

    public String getSerie() {
        return Serie;
    }

    public void setSerie(String serie) {
        Serie = serie;
    }

    public String getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        FechaInicio = fechaInicio;
    }

    public String getHoraInicio() {
        return HoraInicio;
    }

    public void setHoraInicio(String horaInicio) {
        HoraInicio = horaInicio;
    }

    public String getFechaFin() {
        return FechaFin;
    }

    public void setFechaFin(String fechaFin) {
        FechaFin = fechaFin;
    }

    public String getHoraFin() {
        return HoraFin;
    }

    public void setHoraFin(String horaFin) {
        HoraFin = horaFin;
    }

    public String getFalla() {
        return Falla;
    }

    public void setFalla(String falla) {
        Falla = falla;
    }

    public String getFechaLLamada() {
        return FechaLLamada;
    }

    public void setFechaLLamada(String fechaLLamada) {
        FechaLLamada = fechaLLamada;
    }

    public String getHoraLLamada() {
        return HoraLLamada;
    }

    public void setHoraLLamada(String horaLLamada) {
        HoraLLamada = horaLLamada;
    }

    public String getFechaSitio() {
        return FechaSitio;
    }

    public void setFechaSitio(String fechaSitio) {
        FechaSitio = fechaSitio;
    }

    public String getHoraSitio() {
        return HoraSitio;
    }

    public void setHoraSitio(String horaSitio) {
        HoraSitio = horaSitio;
    }

    public String getNombreTecnico() {
        return NombreTecnico;
    }

    public void setNombreTecnico(String nombreTecnico) {
        NombreTecnico = nombreTecnico;
    }
    String Nomenclatura;
    String NombreSeg;
    String PuestoSeg;
    String TipoServicio;
    String Ticket;
    String Trimestre;
    String Periodicidad;
    String Aduana;
    String Equipo;
    String Ubicacion;
    String Serie;
    String FechaInicio;
    String HoraInicio;
    String FechaFin;
    String HoraFin;
    String Falla;
    String FechaLLamada;
    String HoraLLamada;
    String FechaSitio;
    String HoraSitio;
    String NombreTecnico;

    public String[] getDatos() {
        return Datos;
    }

    public void setDatos(String[] datos) {
        Datos = datos;
    }

    String[] Datos;
}
