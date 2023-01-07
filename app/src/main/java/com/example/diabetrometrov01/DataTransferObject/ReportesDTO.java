package com.example.diabetrometrov01.DataTransferObject;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReportesDTO extends Format implements Serializable {

    int idReporte;
    int idPaciente;
    int idFraseMot;
    //
    LocalDate FechaInicio;
    LocalDate FechaFinal;
    String nombre;
    String Observacion;
    //
    float PesoFinal;
    float TallaFinal;
    float LvlGlucosafinal;
    //
    float PorcionProm;
    float CaloriasProm;
    float CarbohidratosProm;
    float Proteinas;
    float Grasas;
    //
    float PesoProm;
    float TallaProm;
    float LvlGlucosaProm;
    //
    LocalDateTime Dia;

    // <editor-fold defaultstate="collapsed" desc="Constructors">


    public ReportesDTO(String nombre, int idPaciente, LocalDate fechaInicio, LocalDate fechaFinal) {
        this.nombre = nombre;
        this.idPaciente = idPaciente;
        this.FechaInicio = fechaInicio;
        this.FechaFinal = fechaFinal;
    }

    public ReportesDTO(int idPaciente, LocalDate fechaInicio, LocalDate fechaFinal, int idFraseMot) {
        this.idPaciente = idPaciente;
        FechaInicio = fechaInicio;
        FechaFinal = fechaFinal;
        this.idFraseMot = idFraseMot;
    }

    public ReportesDTO(int idReporte, int idPaciente, int idFraseMot, LocalDate FechaInicio, LocalDate FechaFinal, String Observacion, float PesoProm, float PesoFinal, LocalDateTime Dia) {
        this.idReporte = idReporte;
        this.idPaciente = idPaciente;
        this.idFraseMot = idFraseMot;
        this.FechaInicio = FechaInicio;
        this.FechaFinal = FechaFinal;
        this.Observacion = Observacion;
        this.PesoProm = PesoProm;
        this.PesoFinal = PesoFinal;
        this.Dia = Dia;
    }

    public ReportesDTO(int idPaciente, int idFraseMot, LocalDate FechaInicio, LocalDate FechaFinal, String Observacion, float PesoProm, float PesoFinal, LocalDateTime Dia) {
        this.idPaciente = idPaciente;
        this.idFraseMot = idFraseMot;
        this.FechaInicio = FechaInicio;
        this.FechaFinal = FechaFinal;
        this.Observacion = Observacion;
        this.PesoProm = PesoProm;
        this.PesoFinal = PesoFinal;
        this.Dia = Dia;
    }

    public ReportesDTO(int idReporte) {
        this.idReporte = idReporte;
    }

    public ReportesDTO(int idPaciente, int idFraseMot, LocalDate FechaInicio, LocalDate FechaFinal, String Observacion, float PesoProm, float PesoFinal) {
        this.idPaciente = idPaciente;
        this.idFraseMot = idFraseMot;
        this.FechaInicio = FechaInicio;
        this.FechaFinal = FechaFinal;
        this.Observacion = Observacion;
        this.PesoProm = PesoProm;
        this.PesoFinal = PesoFinal;
    }

    public ReportesDTO() {
    }

    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Getter and Setter code">  

    public int getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdFraseMot() {
        return idFraseMot;
    }

    public void setIdFraseMot(int idFraseMot) {
        this.idFraseMot = idFraseMot;
    }

    public LocalDate getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(LocalDate FechaInicio) {
        this.FechaInicio = FechaInicio;
    }

    public LocalDate getFechaFinal() {
        return FechaFinal;
    }

    public void setFechaFinal(LocalDate FechaFinal) {
        this.FechaFinal = FechaFinal;
    }

    public String getObservacion() {
        return Observacion;
    }

    public void setObservacion(String Observacion) {
        this.Observacion = Observacion;
    }

    public float getPesoProm() {
        return PesoProm;
    }

    public void setPesoProm(float PesoProm) {
        this.PesoProm = PesoProm;
    }

    public float getPesoFinal() {
        return PesoFinal;
    }

    public void setPesoFinal(float PesoFinal) {
        this.PesoFinal = PesoFinal;
    }

    public LocalDateTime getDia() {
        return Dia;
    }

    public void setDia(LocalDateTime Dia) {
        this.Dia = Dia;
    }

    public float getCaloriasProm() {
        return CaloriasProm;
    }

    public void setCaloriasProm(float caloriasProm) {
        CaloriasProm = caloriasProm;
    }

    public float getCarbohidratosProm() {
        return CarbohidratosProm;
    }

    public void setCarbohidratosProm(float carbohidratosProm) {
        CarbohidratosProm = carbohidratosProm;
    }

    public float getProteinas() {
        return Proteinas;
    }

    public void setProteinas(float proteinas) {
        Proteinas = proteinas;
    }

    public float getGrasas() {
        return Grasas;
    }

    public void setGrasas(float grasas) {
        Grasas = grasas;
    }

    public float getPorcionProm() {
        return PorcionProm;
    }

    public void setPorcionProm(float porcionProm) {
        PorcionProm = porcionProm;
    }

    public float getTallaProm() {
        return TallaProm;
    }

    public void setTallaProm(float tallaProm) {
        TallaProm = tallaProm;
    }

    public float getLvlGlucosaProm() {
        return LvlGlucosaProm;
    }

    public void setLvlGlucosaProm(float lvlGlucosaProm) {
        LvlGlucosaProm = lvlGlucosaProm;
    }

    public float getTallaFinal() {
        return TallaFinal;
    }

    public void setTallaFinal(float tallaFinal) {
        TallaFinal = tallaFinal;
    }

    public float getLvlGlucosafinal() {
        return LvlGlucosafinal;
    }

    public void setLvlGlucosafinal(float lvlGlucosafinal) {
        LvlGlucosafinal = lvlGlucosafinal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // </editor-fold>

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public String toString() {
        return this.getClass().getName() + " [\n Id Reporte: " + getIdReporte()
                + ",\n Id Paciente: " + getIdPaciente()
                + ",\n Id Frase Motivadora: " + getIdFraseMot()
                + ",\n Fecha de Inicio: " + ApplyFormat(getFechaInicio())
                + ",\n Fecha de Final: " + ApplyFormat(getFechaFinal())
                + ",\n Observacion: " + getObservacion()
                + ",\n Peso Promedio: " + ApplyFormat(getPesoProm())
                + ",\n Ult. Peso Registrado: " + ApplyFormat(getPesoFinal())
                + "\n]";
    }

}
