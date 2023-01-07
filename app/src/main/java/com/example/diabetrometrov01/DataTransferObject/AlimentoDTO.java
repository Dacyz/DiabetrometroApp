package com.example.diabetrometrov01.DataTransferObject;

public class AlimentoDTO {
    
    int idAlimento;
    int idCatAlimento;
    String Nombre;
    String Descripcion;
    float TamanoPorcion;
    float Proteinas;
    float Grasas;
    float Carbohidratos;
    float Calorias;
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public AlimentoDTO(int idAlimento, int idCatAlimento, String Nombre, String Descripcion, float TamanoPorcion, float Proteinas, float Grasas, float Carbohidratos, float Calorias) {
        this.idAlimento = idAlimento;
        this.idCatAlimento = idCatAlimento;
        this.Nombre = Nombre;
        this.Descripcion = Descripcion;
        this.TamanoPorcion = TamanoPorcion;
        this.Proteinas = Proteinas;
        this.Grasas = Grasas;
        this.Carbohidratos = Carbohidratos;
        this.Calorias = Calorias;
    }

    public AlimentoDTO(int idCatAlimento, String Nombre, String Descripcion, float TamanoPorcion, float Proteinas, float Grasas, float Carbohidratos, float Calorias) {
        this.idCatAlimento = idCatAlimento;
        this.Nombre = Nombre;
        this.Descripcion = Descripcion;
        this.TamanoPorcion = TamanoPorcion;
        this.Proteinas = Proteinas;
        this.Grasas = Grasas;
        this.Carbohidratos = Carbohidratos;
        this.Calorias = Calorias;
    }

    public AlimentoDTO(int idAlimento) {
        this.idAlimento = idAlimento;
    }

    public AlimentoDTO() {
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getter and Setter code">

    public int getIdAlimento() {
        return idAlimento;
    }

    public void setIdAlimento(int idAlimento) {
        this.idAlimento = idAlimento;
    }

    public int getIdCatAlimento() {
        return idCatAlimento;
    }

    public void setIdCatAlimento(int idCatAlimento) {
        this.idCatAlimento = idCatAlimento;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public float getTamanoPorcion() {
        return TamanoPorcion;
    }

    public void setTamanoPorcion(float TamanoPorcion) {
        this.TamanoPorcion = TamanoPorcion;
    }

    public float getProteinas() {
        return Proteinas;
    }

    public void setProteinas(float Proteinas) {
        this.Proteinas = Proteinas;
    }

    public float getGrasas() {
        return Grasas;
    }

    public void setGrasas(float Grasas) {
        this.Grasas = Grasas;
    }

    public float getCarbohidratos() {
        return Carbohidratos;
    }

    public void setCarbohidratos(float Carbohidratos) {
        this.Carbohidratos = Carbohidratos;
    }

    public float getCalorias() {
        return Calorias;
    }

    public void setCalorias(float Calorias) {
        this.Calorias = Calorias;
    }
    
    
    
    // </editor-fold>
    
    @Override
    public String toString() {
        return this.getClass().getName() +" [\n Id: " + getIdAlimento()
                + ",\n IdCategoria: " + getIdCatAlimento()
                + ",\n Nombre: " + getNombre()
                + ",\n Descripcion: " + getDescripcion()
                + ",\n Grasas: " + getGrasas()
                + ",\n Carbohidratos: " + getCarbohidratos()
                + ",\n Calorias: " + getCalorias()
                + ",\n Tamano de Porcion: " + getTamanoPorcion()
                + ",\n Proteinas: " + getProteinas()
                + "\n]";
    }
    
}
