package com.example.diabetrometrov01.DataTransferObject;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class IngestaDTO extends Format implements Serializable {
    
    int idIngesta;
    int idPaciente;
    int idAlimento;
    float Porcion;
    Time HoraConsumo;
    LocalDateTime Dia;

    // <editor-fold defaultstate="collapsed" desc="Constructors">  

    public IngestaDTO(int idAlimentaria, int idPaciente, int idAlimento, float Porcion, Time HoraConsumo, LocalDateTime Dia) {
        this.idIngesta = idAlimentaria;
        this.idPaciente = idPaciente;
        this.idAlimento = idAlimento;
        this.Porcion = Porcion;
        this.HoraConsumo = HoraConsumo;
        this.Dia = Dia;
    }

    public IngestaDTO(int idPaciente, int idAlimento, float Porcion, Time HoraConsumo, LocalDateTime Dia) {
        this.idPaciente = idPaciente;
        this.idAlimento = idAlimento;
        this.Porcion = Porcion;
        this.HoraConsumo = HoraConsumo;
        this.Dia = Dia;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public IngestaDTO(int idPaciente, int idAlimento, float porcion, Time horaConsumo) {
        this.idPaciente = idPaciente;
        this.idAlimento = idAlimento;
        Porcion = porcion;
        HoraConsumo = horaConsumo;
        this.Dia = LocalDateTime.now();
    }

    public IngestaDTO(int idAlimentaria) {
        this.idIngesta = idAlimentaria;
    }

    public IngestaDTO() {
    }

// </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Getter and Setter code"> 

    public int getIdIngesta() {
        return idIngesta;
    }

    public void setIdIngesta(int idIngesta) {
        this.idIngesta = idIngesta;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdAlimento() {
        return idAlimento;
    }

    public void setIdAlimento(int idAlimento) {
        this.idAlimento = idAlimento;
    }

    public float getPorcion() {
        return Porcion;
    }

    public void setPorcion(float Porcion) {
        this.Porcion = Porcion;
    }

    public Time getHoraConsumo() {
        return HoraConsumo;
    }

    public void setHoraConsumo(Time HoraConsumo) {
        this.HoraConsumo = HoraConsumo;
    }

    public LocalDateTime getDia() {
        return Dia;
    }

    public void setDia(LocalDateTime Dia) {
        this.Dia = Dia;
    }

    // </editor-fold> 
    
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public String toString() {
        return this.getClass().getName() +" [\n Id Alimentaria: " + getIdIngesta()
                + ",\n Id Paciente: " + getIdPaciente()
                + ",\n Id Alimento: " + getIdAlimento()
                + ",\n Porcion: " + ApplyFormat(getPorcion())
                + ",\n Hora Consumo: " + ApplyFormat(getHoraConsumo())
                + ",\n Dia de Registro: " + ApplyFormat(getDia()) 
                + "\n]";
    }
    
}
