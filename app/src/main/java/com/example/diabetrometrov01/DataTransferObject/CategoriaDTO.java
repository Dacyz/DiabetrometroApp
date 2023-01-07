package com.example.diabetrometrov01.DataTransferObject;

public class CategoriaDTO {
    
    int idCategoria;
    String NombreCat;
    String DescCat;
    
    // <editor-fold defaultstate="collapsed" desc="Constructors"> 
    public CategoriaDTO(int idCatAlimento) {
        this.idCategoria = idCatAlimento;
    }

    public CategoriaDTO(int idCatAlimento, String NombreCat, String DescCat) {
        this.idCategoria = idCatAlimento;
        this.NombreCat = NombreCat;
        this.DescCat = DescCat;
    }

    public CategoriaDTO() {
    }

// </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc="Getter and Setter code">  
    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCat() {
        return NombreCat;
    }

    public void setNombreCat(String NombreCat) {
        this.NombreCat = NombreCat;
    }

    public String getDescCat() {
        return DescCat;
    }

    public void setDescCat(String DescCat) {
        this.DescCat = DescCat;
    }// </editor-fold> 
    
    @Override
    public String toString() {
        return this.getClass().getName() +" [\n IdCategoria: " + getIdCategoria()
                + ",\n Nombre: " + getNombreCat()
                + ",\n Descripcion: " + getDescCat()
                + "\n]";
    }
}
