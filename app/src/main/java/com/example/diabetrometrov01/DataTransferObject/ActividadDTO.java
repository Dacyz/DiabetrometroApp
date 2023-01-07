package com.example.diabetrometrov01.DataTransferObject;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ActividadDTO extends Format implements Serializable {
    
    int idActividad;
    int IdPaciente;
    int IdActFisica;
    int Repeticiones;
    Time Tiempo; 
    LocalDateTime dia;

    // <editor-fold defaultstate="collapsed" desc="Constructors">   

    public ActividadDTO(int idActividad, int IdPaciente, int IdActFisica, int Repeticiones, Time Tiempo, LocalDateTime dia) {
        this.idActividad = idActividad;
        this.IdPaciente = IdPaciente;
        this.IdActFisica = IdActFisica;
        this.Repeticiones = Repeticiones;
        this.Tiempo = Tiempo;
        this.dia = dia;
    }

    public ActividadDTO(int IdPaciente, int IdActFisica, int Repeticiones, Time Tiempo, LocalDateTime dia) {
        this.IdPaciente = IdPaciente;
        this.IdActFisica = IdActFisica;
        this.Repeticiones = Repeticiones;
        this.Tiempo = Tiempo;
        this.dia = dia;
    }

    public ActividadDTO() {
    }

// </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc="Getter and Setter code">  

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public int getIdPaciente() {
        return IdPaciente;
    }

    public void setIdPaciente(int IdPaciente) {
        this.IdPaciente = IdPaciente;
    }

    public int getIdActFisica() {
        return IdActFisica;
    }

    public void setIdActFisica(int IdActFisica) {
        this.IdActFisica = IdActFisica;
    }

    public int getRepeticiones() {
        return Repeticiones;
    }

    public void setRepeticiones(int Repeticiones) {
        this.Repeticiones = Repeticiones;
    }

    public Time getTiempo() {
        return Tiempo;
    }

    public void setTiempo(Time Tiempo) {
        this.Tiempo = Tiempo;
    }

    public LocalDateTime getDia() {
        return dia;
    }

    public void setDia(LocalDateTime dia) {
        this.dia = dia;
    }
    
    // </editor-fold> 

    @Override
    public String toString() {
        return this.getClass().getName() +" [\n Id: " + getIdActividad()
                + ",\n Paciente: " + getIdPaciente()
                + ",\n Ejercicio: " + getIdActFisica()
                + ",\n Repeticiones: " + getRepeticiones()
                + ",\n Duracion: " + getTiempo()
                + ",\n Fecha: " + getDia()
                + "\n]";
    }
    
    /*
    public float getCaloriasConsm(){
        ActFisicaDTO act = new ActFisicaDTO(IdActFisica);
        return act.getCaloriasQuemadas() * getRepeticiones();
    }
    */
}
