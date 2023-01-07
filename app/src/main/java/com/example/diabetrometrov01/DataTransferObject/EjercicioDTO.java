package com.example.diabetrometrov01.DataTransferObject;

import java.sql.Time;

public class EjercicioDTO {
    
    int idEjercicio;
    String NombreEjercicio;
    String DescEjercicio;
    float CaloriasQuemadas;
    Time Tiempo;
    boolean TipoEjercicio;
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public EjercicioDTO(int idEjercicio, String NombreEjercicio, String DescEjercicio, float CaloriasQuemadas, Time Tiempo, boolean TipoEjercicio) {
        this.idEjercicio = idEjercicio;
        this.NombreEjercicio = NombreEjercicio;
        this.DescEjercicio = DescEjercicio;
        this.CaloriasQuemadas = CaloriasQuemadas;
        this.Tiempo = Tiempo;
        this.TipoEjercicio = TipoEjercicio;
    }

    public EjercicioDTO(String NombreEjercicio, String DescEjercicio, float CaloriasQuemadas, Time Tiempo, boolean TipoEjercicio) {
        this.NombreEjercicio = NombreEjercicio;
        this.DescEjercicio = DescEjercicio;
        this.CaloriasQuemadas = CaloriasQuemadas;
        this.Tiempo = Tiempo;
        this.TipoEjercicio = TipoEjercicio;
    }

    public EjercicioDTO(int idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public EjercicioDTO() {
    }

    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Getter and Setter code">  
    
    public int getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(int idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public String getNombreEjercicio() {
        return NombreEjercicio;
    }

    public void setNombreEjercicio(String NombreEjercicio) {
        this.NombreEjercicio = NombreEjercicio;
    }

    public String getDescEjercicio() {
        return DescEjercicio;
    }

    public void setDescEjercicio(String DescEjercicio) {
        this.DescEjercicio = DescEjercicio;
    }

    public float getCaloriasQuemadas() {
        return CaloriasQuemadas;
    }

    public void setCaloriasQuemadas(float CaloriasQuemadas) {
        this.CaloriasQuemadas = CaloriasQuemadas;
    }

    public Time getTiempo() {
        return Tiempo;
    }

    public void setTiempo(Time Tiempo) {
        this.Tiempo = Tiempo;
    }

    public boolean isTipoEjercicio() {
        return TipoEjercicio;
    }

    public void setTipoEjercicio(boolean tipoEjercicio) {
        TipoEjercicio = tipoEjercicio;
    }
    // </editor-fold>
    
    @Override
    public String toString() {
        return "[" + getIdEjercicio()
                + ", " + getNombreEjercicio()
                + ", " + getDescEjercicio()
                + ", " + getCaloriasQuemadas()
                + ", " + getTiempo()
                + ", " + isTipoEjercicio() + "]";
    }
}
