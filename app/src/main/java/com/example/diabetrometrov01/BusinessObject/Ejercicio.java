package com.example.diabetrometrov01.BusinessObject;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.diabetrometrov01.DataAccessObject.EjercicioDAO;
import com.example.diabetrometrov01.DataTransferObject.CategoriaDTO;
import com.example.diabetrometrov01.DataTransferObject.EjercicioDTO;

import java.util.ArrayList;
import java.util.List;

public class Ejercicio {
    
    EjercicioDAO DB = new EjercicioDAO();
    EjercicioDTO Ejercicio = new EjercicioDTO();
    
    public List<EjercicioDTO> listar() {
        List<EjercicioDTO> lista = DB.listar();
        return lista != null ? lista : null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<String> listarNombres() {
        List<EjercicioDTO> lista = DB.listar();
        List<String> list = new ArrayList<>();
        lista.forEach((arg) -> {
            list.add(arg.getNombreEjercicio());
        });
        return list;
    }
    
    public EjercicioDTO buscar(String dato,  int tipodato) {
        Ejercicio = new EjercicioDTO();
        switch (tipodato) {
            case 1:
                Ejercicio.setNombreEjercicio(dato);
                break; //Busca por Nombre
            default:
                try {
                    Ejercicio.setIdEjercicio(Integer.parseInt(dato));
                } catch (NumberFormatException e) {
                    System.out.println(this.getClass().getName() + "Se envio un id erroneo: " + e.getMessage());
                }
                break; //Busca por IDKEY
            }
        return DB.buscar(Ejercicio, tipodato);
    }
    
}
