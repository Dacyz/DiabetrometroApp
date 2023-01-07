package com.example.diabetrometrov01.DataTransferObject;

import java.sql.Time;
import java.sql.Timestamp;

public class ConsultaAlimenticiaDTO {
    
    int idConsultaAlimenticia;
    int idPaciente;
    int idAlimento;
    float Porcion;
    Time Hour;
    Timestamp ConsultaDia;
    

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public ConsultaAlimenticiaDTO(int idConsultaAlimenticia, int idPaciente, int idAlimento, Time Hour, Timestamp ConsultaDia, float CantAlimento) {
        this.idConsultaAlimenticia = idConsultaAlimenticia;
        this.idPaciente = idPaciente;
        this.idAlimento = idAlimento;
        this.Hour = Hour;
        this.ConsultaDia = ConsultaDia;
        this.Porcion = CantAlimento;
    }

    public ConsultaAlimenticiaDTO(int idPaciente, int idAlimento, Time Hour, Timestamp ConsultaDia, float CantAlimento) {
        this.idPaciente = idPaciente;
        this.idAlimento = idAlimento;
        this.Hour = Hour;
        this.ConsultaDia = ConsultaDia;
        this.Porcion = CantAlimento;
    }

    public ConsultaAlimenticiaDTO(int idConsultaAlimenticia) {
        this.idConsultaAlimenticia = idConsultaAlimenticia;
    }

    public ConsultaAlimenticiaDTO() {
    }

    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Getter and Setter code">  
    public int getIdConsultaAlimenticia() {
        return idConsultaAlimenticia;
    }

    public void setIdConsultaAlimenticia(int idConsultaAlimenticia) {
        this.idConsultaAlimenticia = idConsultaAlimenticia;
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

    public Time getHour() {
        return Hour;
    }

    public void setHour(Time Hour) {
        this.Hour = Hour;
    }

    public float getPorcion() {
        return Porcion;
    }

    public void setPorcion(float Porcion) {
        this.Porcion = Porcion;
    }

    public Timestamp getConsultaDia() {
        return ConsultaDia;
    }

    public void setConsultaDia(Timestamp ConsultaDia) {
        this.ConsultaDia = ConsultaDia;
    }// </editor-fold> 
    
    @Override
    public String toString() {
        return "[" + getIdAlimento()
                + ", " + getIdConsultaAlimenticia()
                + ", " + getIdPaciente()
                + ", " + getIdAlimento()
                + ", " + getHour()
                + ", " + getPorcion()
                + ", " + getConsultaDia()+ "]";
    }
    
}
