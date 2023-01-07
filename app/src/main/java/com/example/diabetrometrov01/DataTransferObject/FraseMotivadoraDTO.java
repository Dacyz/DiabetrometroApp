package com.example.diabetrometrov01.DataTransferObject;

public class FraseMotivadoraDTO {
    
    int IdFrase;
    String Frase;
    String Autor;

    public FraseMotivadoraDTO(int IdFrase, String Frase, String Autor) {
        this.IdFrase = IdFrase;
        this.Frase = Frase;
        this.Autor = Autor;
    }

    public FraseMotivadoraDTO() {
    }

    public int getIdFrase() {
        return IdFrase;
    }

    public void setIdFrase(int IdFrase) {
        this.IdFrase = IdFrase;
    }

    public String getFrase() {
        return Frase;
    }

    public void setFrase(String Frase) {
        this.Frase = Frase;
    }

    public String getAutor() {
        return Autor;
    }

    public void setAutor(String Autor) {
        this.Autor = Autor;
    }

    public String getFraseS(){
        return getFrase() + " - " + getAutor();
    }
    
}
