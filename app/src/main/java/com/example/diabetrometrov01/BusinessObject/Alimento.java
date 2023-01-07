package com.example.diabetrometrov01.BusinessObject;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.diabetrometrov01.DataAccessObject.AlimentoDAO;
import com.example.diabetrometrov01.DataAccessObject.AlimentoVitaminaDAO;
import com.example.diabetrometrov01.DataAccessObject.CategoriaDAO;
import com.example.diabetrometrov01.DataAccessObject.VitaminaDAO;
import com.example.diabetrometrov01.DataTransferObject.AlimentoDTO;
import com.example.diabetrometrov01.DataTransferObject.AlimentoVitaminaDTO;
import com.example.diabetrometrov01.DataTransferObject.CategoriaDTO;
import com.example.diabetrometrov01.DataTransferObject.VitaminaDTO;
import java.util.ArrayList;
import java.util.List;

public class Alimento {

    // <editor-fold defaultstate="collapsed" desc="Auto - Complete">  
    public static final int IDKEY = 0;
    public static final int NAMEKEY = 1;// </editor-fold> 

    AlimentoDAO BDAl = new AlimentoDAO();
    CategoriaDAO BDCt = new CategoriaDAO();
    VitaminaDAO BDVt = new VitaminaDAO();
    AlimentoVitaminaDAO BDAlVt = new AlimentoVitaminaDAO();
    AlimentoVitaminaDTO AlimentoVitamina;
    CategoriaDTO Categoria;
    VitaminaDTO[] Vitaminas;
    AlimentoDTO Alimento;

    public Alimento(){

    }
    
    public List<AlimentoVitaminaDTO> listarDetailVitaminas(int idAlimento) {
        List<AlimentoVitaminaDTO> lista = BDAlVt.listarPorAlimento(idAlimento);
        return lista != null ? lista : null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<VitaminaDTO> listarVitaminas(int idAlimento) {
        List<AlimentoVitaminaDTO> lista = listarDetailVitaminas(idAlimento);
        List<VitaminaDTO> listaVit = new ArrayList<>();
        lista.forEach((arg) -> {
            listaVit.add(BDVt.buscar(new VitaminaDTO(arg.getIdVitamina()), IDKEY));
        });
        return listaVit != null ? listaVit : null;
    }

    public List<AlimentoDTO> listar() {
        List<AlimentoDTO> lista = BDAl.listar();
        return lista != null ? lista : null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<String> listarCategorias() {
        List<CategoriaDTO> lista = BDCt.listar();
        List<String> list = new ArrayList<>();
        lista.forEach((arg) -> {
            list.add(arg.getNombreCat());
        });
        return list;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<String> listarxCategoria(int CategoriaID) {
        Alimento = new AlimentoDTO();
        Alimento.setIdCatAlimento(CategoriaID);
        List<AlimentoDTO> lista = BDAl.listarxCategoria(Alimento);
        List<String> list = new ArrayList<>();
        lista.forEach((arg) -> {
            list.add(arg.getNombre());
        });
        return list;
    }
    
    public CategoriaDTO buscar (int ID){
        return BDCt.buscar(new CategoriaDTO(ID), IDKEY);
    }

    public CategoriaDTO buscarNombre (String name){
        return BDCt.buscar(new CategoriaDTO(0,name,name), NAMEKEY);
    }

    public AlimentoDTO buscar(String dato, int tipodato) {
        Alimento = new AlimentoDTO();
        switch (tipodato) {
            case 1:
                Alimento.setNombre(dato);
                break; //Busca por Nombre
            default:
                try {
                    Alimento.setIdAlimento(Integer.parseInt(dato));
                } catch (NumberFormatException e) {
                    System.out.println(this.getClass().getName() + "Se envio un id erroneo: " + e.getMessage());
                }
                break; //Busca por IDKEY
            }
        return BDAl.buscar(Alimento, tipodato);
    }

}
