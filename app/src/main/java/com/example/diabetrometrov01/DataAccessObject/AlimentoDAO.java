package com.example.diabetrometrov01.DataAccessObject;

import com.example.diabetrometrov01.DataTransferObject.AlimentoDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlimentoDAO extends Conecsion implements CRUD<AlimentoDTO>{
    
    java.sql.Connection cn;
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public List<AlimentoDTO> listar() {
        List<AlimentoDTO> lista = new ArrayList<>();
        String sql = "Select * from Alimento";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                AlimentoDTO obj = new AlimentoDTO();
                obj.setIdAlimento(rs.getInt(1));
                obj.setIdCatAlimento(rs.getInt(2));
                obj.setNombre(rs.getString(3));
                obj.setDescripcion(rs.getString(4));
                obj.setTamanoPorcion(rs.getFloat(5));
                obj.setProteinas(rs.getFloat(6));
                obj.setGrasas(rs.getFloat(7));
                obj.setCarbohidratos(rs.getFloat(8));
                obj.setCalorias(rs.getFloat(9));
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
    public boolean agregar(AlimentoDTO obj) {
        int r;
        String sql = "insert into Alimento(idCatAlimento,Nombre,DescripAlimento,TamanoEstPorcion,Calorias,Carbohidratos,Proteinas,Grasas)values(?,?,?,?,?,?,?,?)";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, obj.getIdCatAlimento());
            ps.setString(2, obj.getNombre());
            ps.setString(3, obj.getDescripcion());
            ps.setFloat(4, obj.getTamanoPorcion());
            ps.setFloat(5, obj.getCalorias());
            ps.setFloat(6, obj.getCarbohidratos());
            ps.setFloat(7, obj.getProteinas());
            ps.setFloat(8, obj.getGrasas());
            r = ps.executeUpdate();
            return r == 1;
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " to add " + obj.getNombre()+ " :");
            System.out.println(obj.toString());
            System.out.println(ex.getMessage() + ex.getCause().toString());
            System.out.println(ex);
            return false;
        } finally {
            cerrar();
        }
    }

    @Override
    public boolean actualizar(AlimentoDTO obj) {
        int r;
        String sql = "update Alimento set idCatAlimento=?,Nombre=?,DescripAlimento=?,TamanoEstPorcion=?,Calorias=?,Carbohidratos=?,Proteinas=?,Grasas=? where idAlimento=?";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, obj.getIdCatAlimento());
            ps.setString(2, obj.getNombre());
            ps.setString(3, obj.getDescripcion());
            ps.setFloat(4, obj.getTamanoPorcion());
            ps.setFloat(5, obj.getCalorias());
            ps.setFloat(6, obj.getCarbohidratos());
            ps.setFloat(7, obj.getProteinas());
            ps.setFloat(8, obj.getGrasas());
            ps.setInt(9, obj.getIdAlimento());
            r = ps.executeUpdate();
            return r == 1;
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " to actualizar " + obj.getNombre()+ " :");
            System.out.println(obj.toString());
            System.out.println(ex.getMessage() + ex.getCause().toString());
            return false;
        } finally {
            cerrar();
        }
    }

    @Override
    public boolean eliminar(AlimentoDTO obj) {
        int r;
        String sql = "delete from Alimento where idAlimento=?";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, obj.getIdAlimento());
            r = ps.executeUpdate();
            return r == 1;
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " to delete " + obj.getNombre()+ " :");
            System.out.println(obj.toString());
            System.out.println(ex.getMessage() + ex.getCause().toString());
            return false;
        } finally {
            cerrar();
        }
    }

    @Override
    public AlimentoDTO buscar(AlimentoDTO obj, int dato) {
        try {
            cn = conectar();
            String sql = "";
            switch (dato) {
                case 1:
                    sql = "select * from Alimento where Nombre=?";
                    ps = cn.prepareStatement(sql);
                    ps.setString(1, obj.getNombre());
                    break; //Busca por DNI
                default:
                    sql = "select * from Alimento where idAlimento=?";
                    ps = cn.prepareStatement(sql);
                    ps.setInt(1, obj.getIdAlimento());
                    break; //Busca por ID
            }
            rs = ps.executeQuery();
            obj = null;
            while (rs.next()) {
                obj = new AlimentoDTO();
                obj.setIdAlimento(rs.getInt(1));
                obj.setIdCatAlimento(rs.getInt(2));
                obj.setNombre(rs.getString(3));
                obj.setDescripcion(rs.getString(4));
                obj.setTamanoPorcion(rs.getFloat(5));
                obj.setProteinas(rs.getFloat(6));
                obj.setGrasas(rs.getFloat(7));
                obj.setCarbohidratos(rs.getFloat(8));
                obj.setCalorias(rs.getFloat(9));
            }
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " to BuscarId " + obj.getNombre()+ " :");
            System.out.println(obj.toString());
            System.out.println(ex.getMessage() + ex.getCause().toString());
            obj = null;
        } finally {
            cerrar();
        }
        return obj;
    }
    
    public List<AlimentoDTO> listarxCategoria(AlimentoDTO obj) {
        List<AlimentoDTO> lista = new ArrayList<>();
        String sql = "Select * from Alimento where idCatAlimento=?";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, obj.getIdCatAlimento());
            rs = ps.executeQuery();
            while (rs.next()) {
                obj = new AlimentoDTO();
                obj.setIdAlimento(rs.getInt(1));
                obj.setIdCatAlimento(rs.getInt(2));
                obj.setNombre(rs.getString(3));
                obj.setDescripcion(rs.getString(4));
                obj.setTamanoPorcion(rs.getFloat(5));
                obj.setProteinas(rs.getFloat(6));
                obj.setGrasas(rs.getFloat(7));
                obj.setCarbohidratos(rs.getFloat(8));
                obj.setCalorias(rs.getFloat(9));
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
