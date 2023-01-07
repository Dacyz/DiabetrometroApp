package com.example.diabetrometrov01.DataTransferObject;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PacienteDatosDTO extends Format{
    
    int idDatos;
    int idPaciente;
    float talla;
    float peso;
    float lvlglucosa;
    LocalDate Dia;
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public PacienteDatosDTO(int idDatos, int idPaciente, float talla, float peso, float lvlglucosa, LocalDate Dia) {
        this.idDatos = idDatos;
        this.idPaciente = idPaciente;
        this.talla = talla;
        this.peso = peso;
        this.lvlglucosa = lvlglucosa;
        this.Dia = Dia;
    }

    public PacienteDatosDTO() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public PacienteDatosDTO(int idPaciente, float talla, float peso, float lvlglucosa) {
        this.idPaciente = idPaciente;
        this.talla = talla;
        this.peso = peso;
        this.lvlglucosa = lvlglucosa;
        this.Dia = LocalDate.now();
    }
    
    // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc="Getter and Setter code">  

    public int getIdDatos() {
        return idDatos;
    }

    public void setIdDatos(int idDatos) {
        this.idDatos = idDatos;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public float getTalla() {
        return talla;
    }

    public void setTalla(float talla) {
        this.talla = talla;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getLvlglucosa() {
        return lvlglucosa;
    }

    public void setLvlglucosa(float lvlglucosa) {
        this.lvlglucosa = lvlglucosa;
    }

    public LocalDate getDia() {
        return Dia;
    }

    public void setDia(LocalDate Dia) {
        this.Dia = Dia;
    }    // </editor-fold> 
    
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public String toString() {
        return this.getClass().getName() +" [\n Id: " + getIdDatos()
                + ",\n idPaciente: " + getIdPaciente()
                + ",\n Altura: " + ApplyFormat(getTalla())
                + ",\n Peso: " + ApplyFormat(getPeso())
                + ",\n Nivel de Glucosa: " + ApplyFormat(getLvlglucosa())
                + ",\n Fecha: " + ApplyFormat(getDia())
                + "\n]";
    }
    
}
