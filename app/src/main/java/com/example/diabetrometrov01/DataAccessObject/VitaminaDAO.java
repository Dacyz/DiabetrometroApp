package com.example.diabetrometrov01.DataAccessObject;

import com.example.diabetrometrov01.DataTransferObject.VitaminaDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VitaminaDAO extends Conecsion implements CRUD<VitaminaDTO>{
    
    java.sql.Connection cn;
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public List<VitaminaDTO> listar() {
        List<VitaminaDTO> lista = new ArrayList<>();
        String sql = "Select * from Alimento";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                VitaminaDTO obj = new VitaminaDTO();
                obj.setIdVitamina(rs.getInt(1));
                obj.setNombreVitamina(rs.getString(2));
                obj.setDescVitamina(rs.getString(3));
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
    public boolean agregar(VitaminaDTO obj) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public boolean actualizar(VitaminaDTO obj) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public boolean eliminar(VitaminaDTO obj) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public VitaminaDTO buscar(VitaminaDTO obj, int tipodato) {
        String sql = "select * from Vitamina where IdVitamina=?";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, obj.getIdVitamina());
            rs = ps.executeQuery();
            obj = null;
            while (rs.next()) {
                obj = new VitaminaDTO();
                obj.setIdVitamina(rs.getInt(1));
                obj.setNombreVitamina(rs.getString(2));
                obj.setDescVitamina(rs.getString(3));
            }
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " to BuscarId " + obj.getIdVitamina()+ " :");
            System.out.println(obj.toString());
            System.out.println(ex.getMessage() + ex.getCause().toString());
            obj = null;
        } finally {
            cerrar();
        }
        return obj;
    }
    
}
