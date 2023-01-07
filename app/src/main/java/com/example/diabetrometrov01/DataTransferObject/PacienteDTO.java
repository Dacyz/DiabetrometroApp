package com.example.diabetrometrov01.DataTransferObject;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class PacienteDTO extends Format{

    private int IdPaciente;
    private String Correo;
    private String DNI;
    private String Contrasena;
    private String Nombre;
    private String Apellidos;
    private char Sexo;
    private LocalDateTime Registro;
    private LocalDate FechaNacimiento;

// <editor-fold defaultstate="collapsed" desc="Constructors">   

    public PacienteDTO() {
        
    }

    public PacienteDTO(int IdPaciente) {
        this.IdPaciente = IdPaciente;
    }

    public PacienteDTO(String Correo, String Contrasena) {
        this.Correo = Correo;
        this.Contrasena = Contrasena;
    }

    public PacienteDTO(int IdPaciente, String Correo, String DNI, String Contrasena, String Nombre, String Apellidos, char Sexo, LocalDateTime Registro, LocalDate FechaNacimiento) {
        this.IdPaciente = IdPaciente;
        this.Correo = Correo;
        this.DNI = DNI;
        this.Contrasena = Contrasena;
        this.Nombre = Nombre;
        this.Apellidos = Apellidos;
        this.Sexo = Sexo;
        this.Registro = Registro;
        this.FechaNacimiento = FechaNacimiento;
    }

    public PacienteDTO(String Correo, String DNI, String Contrasena, String Nombre, String Apellidos, char Sexo, LocalDateTime Registro, LocalDate FechaNacimiento) {
        this.Correo = Correo;
        this.DNI = DNI;
        this.Contrasena = Contrasena;
        this.Nombre = Nombre;
        this.Apellidos = Apellidos;
        this.Sexo = Sexo;
        this.Registro = Registro;
        this.FechaNacimiento = FechaNacimiento;
    }
    
    public PacienteDTO(String Correo, String DNI, String Contrasena, String Nombre, String Apellidos, char Sexo, LocalDate FechaNacimiento) {
        this.Correo = Correo;
        this.DNI = DNI;
        this.Contrasena = Contrasena;
        this.Nombre = Nombre;
        this.Apellidos = Apellidos;
        this.Sexo = Sexo;
        this.FechaNacimiento = FechaNacimiento;
    }
    
// </editor-fold> 

// <editor-fold defaultstate="collapsed" desc="Getter and Setter code">    

    public int getIdPaciente() {
        return IdPaciente;
    }

    public void setIdPaciente(int IdPaciente) {
        this.IdPaciente = IdPaciente;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String Contrasena) {
        this.Contrasena = Contrasena;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String Apellidos) {
        this.Apellidos = Apellidos;
    }

    public char getSexo() {
        return Sexo;
    }

    public void setSexo(char Sexo) {
        this.Sexo = Sexo;
    }

    public LocalDateTime getRegistro() {
        return Registro;
    }

    public void setRegistro(LocalDateTime Registro) {
        this.Registro = Registro;
    }

    public LocalDate getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }
    
// </editor-fold> 

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public String toString() {
        return this.getClass().getName() +" [\n Id: " + getIdPaciente()
                + ",\n Correo: " + getCorreo()
                + ",\n Contrasena: " + getContrasena()
                + ",\n Nombre: " + getNombre()
                + ",\n Apellidos: " + getApellidos()
                + ",\n DNI: " + getDNI()
                + ",\n Sexo: " + getSexoString()
                + ",\n Fecha de Registro: " + ApplyFormat(getRegistro())
                + ",\n Fecha de Nacimiento: " + ApplyFormat(getFechaNacimiento())
                + "\n]";
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getFechaNacimientoString(){
        return ApplyFormat(FechaNacimiento);
    }

    public String getSexoString() {
        return 'M' == getSexo() ? "Masculino" : 'F' == getSexo() ? "Femenino" : "Otro";
    }
    
    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getEdad() {
        return Period.between(getFechaNacimiento(), LocalDate.now()).getYears();
    }
}
