package com.example.diabetrometrov01.DataAccessObject;

import com.example.diabetrometrov01.DataTransferObject.AlimentoVitaminaDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlimentoVitaminaDAO extends Conecsion implements CRUD<AlimentoVitaminaDTO>{
    
    java.sql.Connection cn;
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public List<AlimentoVitaminaDTO> listar() {
        List<AlimentoVitaminaDTO> lista = new ArrayList<>();
        String sql = "Select * from AlimentoVitamina";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                AlimentoVitaminaDTO obj = new AlimentoVitaminaDTO();
                obj.setIdAlimento(rs.getInt(1));
                obj.setIdVitamina(rs.getInt(2));
                obj.setGramos(rs.getFloat(3));
                lista.add(obj);
            }
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " at list ");
            System.out.println(ex.getMessage() + ex.getCause().toString());
        } finally {
            cerrar();
        }
        return lista;
    }

    @Override
    public boolean agregar(AlimentoVitaminaDTO obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizar(AlimentoVitaminaDTO obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(AlimentoVitaminaDTO obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AlimentoVitaminaDTO buscar(AlimentoVitaminaDTO obj, int tipoDato) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<AlimentoVitaminaDTO> listarPorAlimento(int idAlimento) {
        List<AlimentoVitaminaDTO> lista = new ArrayList<>();
        String sql = "Select * from AlimentoVitamina where idAlimento=?";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, idAlimento);
            rs = ps.executeQuery();
            AlimentoVitaminaDTO obj;
            while (rs.next()) {
                obj = new AlimentoVitaminaDTO();
                obj.setIdAlimento(rs.getInt(1));
                obj.setIdVitamina(rs.getInt(2));
                obj.setGramos(rs.getFloat(3));
                lista.add(obj);
            }
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " at list ");
            System.out.println(ex.getMessage() + ex.getCause().toString());
        } finally {
            cerrar();
        }
        return lista;
    }
    
}
