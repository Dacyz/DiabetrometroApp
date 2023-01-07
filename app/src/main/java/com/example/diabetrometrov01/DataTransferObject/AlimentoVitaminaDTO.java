package com.example.diabetrometrov01.DataTransferObject;

public class AlimentoVitaminaDTO {
    
    int IdAlimento;
    int IdVitamina;
    float gramos;

    public AlimentoVitaminaDTO(int IdAlimento, int IdVitamina, float gramos) {
        this.IdAlimento = IdAlimento;
        this.IdVitamina = IdVitamina;
        this.gramos = gramos;
    }

    public AlimentoVitaminaDTO() {
    }

    public int getIdAlimento() {
        return IdAlimento;
    }

    public void setIdAlimento(int IdAlimento) {
        this.IdAlimento = IdAlimento;
    }

    public int getIdVitamina() {
        return IdVitamina;
    }

    public void setIdVitamina(int IdVitamina) {
        this.IdVitamina = IdVitamina;
    }

    public float getGramos() {
        return gramos;
    }

    public void setGramos(float gramos) {
        this.gramos = gramos;
    }
    
    @Override
    public String toString() {
        return this.getClass().getName() +" [\n IdAlimento: " + getIdAlimento()
                + ",\n IdVitamina: " + getIdVitamina()
                + ",\n Gramos: " + getGramos()
                + "\n]";
    }
    
}
