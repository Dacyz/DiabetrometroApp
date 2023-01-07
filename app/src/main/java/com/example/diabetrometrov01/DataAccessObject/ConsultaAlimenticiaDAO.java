package com.example.diabetrometrov01.DataAccessObject;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.diabetrometrov01.DataTransferObject.ConsultaAlimenticiaDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultaAlimenticiaDAO extends Conecsion implements CRUD<ConsultaAlimenticiaDTO>{
    
    java.sql.Connection cn;
    PreparedStatement ps;
    ResultSet rs;      

    @Override
    public List<ConsultaAlimenticiaDTO> listar() {
        List<ConsultaAlimenticiaDTO> lista = new ArrayList<>();
        String sql = "Select * from ConsultaAlimenticia";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ConsultaAlimenticiaDTO obj = new ConsultaAlimenticiaDTO();
                obj.setIdConsultaAlimenticia(rs.getInt(1));
                obj.setIdPaciente(rs.getInt(2));
                obj.setIdAlimento(rs.getInt(3));
                obj.setPorcion(rs.getFloat(4));
                obj.setHour(rs.getTime(5));
                obj.setConsultaDia(rs.getTimestamp(6));
                lista.add(obj);
            }
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " at list ");
            System.out.println(ex.getMessage() + ex.getCause());
        } finally {
            cerrar();
        }
        return lista;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean agregar(ConsultaAlimenticiaDTO obj) {
        int r;
        String sql = "insert into ConsultaAlimenticia(IdPaciente,IdAlimento,Porcion,HoraConsumo,Día)values(?,?,?,?,?)";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, obj.getIdPaciente());
            ps.setInt(2, obj.getIdAlimento());
            ps.setFloat(3, obj.getPorcion());
            ps.setTime(4, obj.getHour());
            ps.setTimestamp(5, java.sql.Timestamp.valueOf(String.valueOf(LocalDateTime.now())));
            r = ps.executeUpdate();
            return r == 1;
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " to add " + obj.getIdPaciente()+ " :");
            System.out.println(obj.toString());
            System.out.println(ex.getMessage() + ex.getCause());
            return false;
        } finally {
            cerrar();
        }
    }

    @Override
    public boolean actualizar(ConsultaAlimenticiaDTO obj) {
        int r;
        String sql = "update ConsultaAlimenticia set IdPaciente=?,IdAlimento=?,Porcion=?,HoraConsumo=? where IdConsultaAlimentaria=?";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, obj.getIdPaciente());
            ps.setInt(2, obj.getIdAlimento());
            ps.setFloat(3, obj.getPorcion());
            ps.setTime(4, obj.getHour());
            ps.setInt(6, obj.getIdConsultaAlimenticia());
            r = ps.executeUpdate();
            return r == 1;
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " to actualizar " + obj.getIdConsultaAlimenticia()+ " :");
            System.out.println(obj.toString());
            System.out.println(ex.getMessage() + ex.getCause());
            return false;
        } finally {
            cerrar();
        }
    }

    @Override
    public boolean eliminar(ConsultaAlimenticiaDTO obj) {
        int r;
        String sql = "delete from ConsultaAlimenticia where IdConsultaAlimentaria=?";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, obj.getIdConsultaAlimenticia());
            r = ps.executeUpdate();
            return r == 1;
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " to delete " + obj.getIdConsultaAlimenticia()+ " :");
            System.out.println(obj.toString());
            System.out.println(ex.getMessage() + ex.getCause());
            return false;
        } finally {
            cerrar();
        }
    }

    @Override
    public ConsultaAlimenticiaDTO buscar(ConsultaAlimenticiaDTO obj, int tipodato) {
        String sql = "select * from ConsultaAlimenticia where IdConsultaAlimentaria=?";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, obj.getIdConsultaAlimenticia());
            rs = ps.executeQuery();
            obj = null;
            while (rs.next()) {
                obj = new ConsultaAlimenticiaDTO();
                obj.setIdConsultaAlimenticia(rs.getInt(1));
                obj.setIdPaciente(rs.getInt(2));
                obj.setIdAlimento(rs.getInt(3));
                obj.setPorcion(rs.getFloat(4));
                obj.setHour(rs.getTime(5));
                obj.setConsultaDia(rs.getTimestamp(6));
            }
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " to BuscarId " + obj.getIdConsultaAlimenticia()+ " :");
            System.out.println(obj.toString());
            System.out.println(ex.getMessage() + ex.getCause());
            obj = null;
        } finally {
            cerrar();
        }
        return obj;
    }
    
    public List<ConsultaAlimenticiaDTO> listarPorPaciente(ConsultaAlimenticiaDTO obj) {
        List<ConsultaAlimenticiaDTO> lista = new ArrayList<>();
        String sql = "Select * from ConsultaAlimenticia where idPaciente=?";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, obj.getIdPaciente());
            rs = ps.executeQuery();
            while (rs.next()) {
                obj = new ConsultaAlimenticiaDTO();
                obj.setIdConsultaAlimenticia(rs.getInt(1));
                obj.setIdPaciente(rs.getInt(2));
                obj.setIdAlimento(rs.getInt(3));
                obj.setPorcion(rs.getFloat(4));
                obj.setHour(rs.getTime(5));
                obj.setConsultaDia(rs.getTimestamp(6));
                lista.add(obj);
            }
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " at list ");
            System.out.println(ex.getMessage() + ex.getCause());
        } finally {
            cerrar();
        }
        return lista;
    }
    
    public List<ConsultaAlimenticiaDTO> listarPorPacienteAlimento(ConsultaAlimenticiaDTO obj) {
        List<ConsultaAlimenticiaDTO> lista = new ArrayList<>();
        String sql = "Select * from ConsultaAlimenticia where idPaciente=?, idAlimento=?";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, obj.getIdPaciente());
            ps.setInt(2, obj.getIdAlimento());
            rs = ps.executeQuery();
            while (rs.next()) {
                obj = new ConsultaAlimenticiaDTO();
                obj.setIdConsultaAlimenticia(rs.getInt(1));
                obj.setIdPaciente(rs.getInt(2));
                obj.setIdAlimento(rs.getInt(3));
                obj.setPorcion(rs.getFloat(4));
                obj.setHour(rs.getTime(5));
                obj.setConsultaDia(rs.getTimestamp(6));
                lista.add(obj);
            }
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " at list ");
            System.out.println(ex.getMessage() + ex.getCause());
        } finally {
            cerrar();
        }
        return lista;
    }
    
}
