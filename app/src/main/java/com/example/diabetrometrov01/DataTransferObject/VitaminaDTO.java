package com.example.diabetrometrov01.DataTransferObject;

public class VitaminaDTO {
    
    int idVitamina;
    String NombreVitamina;
    String DescVitamina;

    public VitaminaDTO(int idVitamina, String Nombre, String Desc) {
        this.idVitamina = idVitamina;
        this.NombreVitamina = Nombre;
        this.DescVitamina = Desc;
    }

    public VitaminaDTO(int idVitamina) {
        this.idVitamina = idVitamina;
    }

    public int getIdVitamina() {
        return idVitamina;
    }

    public VitaminaDTO() {
    }

    public void setIdVitamina(int idVitamina) {
        this.idVitamina = idVitamina;
    }

    public String getNombreVitamina() {
        return NombreVitamina;
    }

    public void setNombreVitamina(String NombreVitamina) {
        this.NombreVitamina = NombreVitamina;
    }

    public String getDescVitamina() {
        return DescVitamina;
    }

    public void setDescVitamina(String DescVitamina) {
        this.DescVitamina = DescVitamina;
    }
    
    @Override
    public String toString() {
        return this.getClass().getName() +" [\n Id: " + getIdVitamina()
                + ",\n Nombre: " + getNombreVitamina()
                + ",\n Detalle: " + getDescVitamina()
                + "\n]";
    }
    
}
