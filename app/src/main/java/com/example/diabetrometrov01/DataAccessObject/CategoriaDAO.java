package com.example.diabetrometrov01.DataAccessObject;

import com.example.diabetrometrov01.DataTransferObject.CategoriaDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO extends Conecsion implements CRUD<CategoriaDTO>{
    
    java.sql.Connection cn;
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public List<CategoriaDTO> listar() {
        List<CategoriaDTO> lista = new ArrayList<>();
        String sql = "Select * from Categoria";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                CategoriaDTO obj = new CategoriaDTO();
                obj.setIdCategoria(rs.getInt(1));
                obj.setNombreCat(rs.getString(2));
                obj.setDescCat(rs.getString(3));
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
    public boolean agregar(CategoriaDTO obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizar(CategoriaDTO obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(CategoriaDTO obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CategoriaDTO buscar(CategoriaDTO obj, int tipodato) {
        try {
            cn = conectar();
            String sql = "";
            switch (tipodato) {
                case 1:
                    sql = "select * from Categoria where NombreCat=?";
                    ps = cn.prepareStatement(sql);
                    ps.setString(1, obj.getNombreCat());
                    break; //Busca por DNI
                default:
                    sql = "select * from Categoria where IdCatAlimento=?";
                    ps = cn.prepareStatement(sql);
                    ps.setInt(1, obj.getIdCategoria());
                    break; //Busca por ID
            }
            rs = ps.executeQuery();
            obj = null;
            while (rs.next()) {
                obj = new CategoriaDTO();
                obj.setIdCategoria(rs.getInt(1));
                obj.setNombreCat(rs.getString(2));
                obj.setDescCat(rs.getString(3));
            }
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " to BuscarId " + obj.getNombreCat()+ " :");
            System.out.println(obj.toString());
            System.out.println(ex.getMessage() + ex.getCause().toString());
            obj = null;
        } finally {
            cerrar();
        }
        return obj;
    }
    
}
