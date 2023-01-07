package com.example.diabetrometrov01.DataAccessObject;

import com.example.diabetrometrov01.DataTransferObject.EjercicioDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EjercicioDAO extends Conecsion implements CRUD<EjercicioDTO> {

    java.sql.Connection cn;
    PreparedStatement ps;
    ResultSet rs;
    
    @Override
    public List<EjercicioDTO> listar() {
        List<EjercicioDTO> lista = new ArrayList<>();
        String sql = "Select * from Ejercicio";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                EjercicioDTO obj = new EjercicioDTO();
                obj.setIdEjercicio(rs.getInt(1));
                obj.setNombreEjercicio(rs.getString(2));
                obj.setCaloriasQuemadas(rs.getFloat(3));
                obj.setDescEjercicio(rs.getString(4));
                obj.setTiempo(rs.getTime(5));
                obj.setTipoEjercicio(rs.getBoolean(6));
                lista.add(obj);
            }
        } catch (SQLException ex) {
            setErrorInDB("Error " + this.getClass().getName() + " at list ");
            ex.printStackTrace();
        } finally {
            cerrar();
        }
        return lista;
    }

    @Override
    public boolean agregar(EjercicioDTO obj) {
        setErrorInDB("Metodo sin Soporte");
        return false;
    }

    @Override
    public boolean actualizar(EjercicioDTO obj) {
        setErrorInDB("Metodo sin Soporte");
        return false;
    }

    @Override
    public boolean eliminar(EjercicioDTO obj) {
        setErrorInDB("Metodo sin Soporte");
        return false;
    }

    @Override
    public EjercicioDTO buscar(EjercicioDTO obj, int tipodato) {
        try {
            cn = conectar();
            String sql = "";
            switch (tipodato) {
                case 1:
                    sql = "select * from Ejercicio where Nombre=?";
                    ps = cn.prepareStatement(sql);
                    ps.setString(1, obj.getNombreEjercicio());
                    break; //Busca por DNI
                default:
                    sql = "select * from Ejercicio where IdActFisica=?";
                    ps = cn.prepareStatement(sql);
                    ps.setInt(1, obj.getIdEjercicio());
                    break; //Busca por ID
            }
            rs = ps.executeQuery();
            obj = null;
            while (rs.next()) {
                obj = new EjercicioDTO();
                obj.setIdEjercicio(rs.getInt(1));
                obj.setNombreEjercicio(rs.getString(2));
                obj.setCaloriasQuemadas(rs.getFloat(3));
                obj.setDescEjercicio(rs.getString(4));
                obj.setTiempo(rs.getTime(5));
                obj.setTipoEjercicio(rs.getBoolean(6));
            }
        } catch (SQLException ex) {
            setErrorInDB("Error " + this.getClass().getName() + " at search ");
            ex.printStackTrace();
        } finally {
            cerrar();
        }
        return obj;
    }
    
}
