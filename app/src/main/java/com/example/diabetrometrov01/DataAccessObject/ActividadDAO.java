package com.example.diabetrometrov01.DataAccessObject;

import static java.time.ZoneId.systemDefault;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.diabetrometrov01.DataTransferObject.ActividadDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ActividadDAO extends Conecsion implements CRUD<ActividadDTO> {
    
    java.sql.Connection cn;
    PreparedStatement ps;
    Statement pes;
    ResultSet rs;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public List<ActividadDTO> listar() {
        List<ActividadDTO> lista = new ArrayList<>();
        String sql = "Select * from Actividad";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ActividadDTO obj = new ActividadDTO();
                obj.setIdActividad(rs.getInt(1));
                obj.setIdPaciente(rs.getInt(2));
                obj.setIdActFisica(rs.getInt(3));
                obj.setRepeticiones(rs.getInt(4));
                obj.setDia(rs.getTimestamp(5).toInstant().atZone(systemDefault()).toLocalDateTime());
                obj.setTiempo(rs.getTime(6));
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean agregar(ActividadDTO obj) {
        String sql = "insert into Actividad values(" +
                "'"+ obj.getIdPaciente() +"'," +
                "'"+ obj.getIdActFisica() +"'," +
                "'"+ obj.getRepeticiones() +"'," +
                "'"+ obj.ApplyFormat(obj.getDia()) +"'," +
                "'"+ obj.ApplyFormat(obj.getTiempo()) +"')";
        try {
            cn = conectar();
            pes = cn.createStatement();
            return pes.executeUpdate(sql) == 1;
        } catch (SQLException ex) {
            setErrorInDB("Error al agregar "+ex.getMessage());
            return false;
        } finally {
            cerrar();
        }
    }

    @Override
    public boolean actualizar(ActividadDTO obj) {
        int r;
        String sql = "update Actividad set IdPaciente=?,IdActFisica=?,Repeticiones=?,Tiempo=? where idActividad=?";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, obj.getIdPaciente());
            ps.setInt(2, obj.getIdActFisica());
            ps.setInt(3, obj.getRepeticiones());
            ps.setTime(4, obj.getTiempo());
            ps.setInt(5, obj.getIdActividad());
            r = ps.executeUpdate();
            return r == 1;
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " to actualizar " + obj.getIdActividad()+ " :");
            System.out.println(obj.toString());
            System.out.println(ex.getMessage() + ex.getCause().toString());
            return false;
        } finally {
            cerrar();
        }
    }

    @Override
    public boolean eliminar(ActividadDTO obj) {
        int r;
        String sql = "delete from Actividad where idActividad=?";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, obj.getIdActividad());
            r = ps.executeUpdate();
            return r == 1;
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " to delete " + obj.getIdActividad()+ " :");
            System.out.println(obj.toString());
            System.out.println(ex.getMessage() + ex.getCause().toString());
            return false;
        } finally {
            cerrar();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public ActividadDTO buscar(ActividadDTO obj, int dato) {
        String sql = "select * from Actividad where idActividad=?";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, obj.getIdActividad());
            rs = ps.executeQuery();
            obj = null;
            while (rs.next()) {
                obj = new ActividadDTO();
                obj.setIdActividad(rs.getInt(1));
                obj.setIdPaciente(rs.getInt(2));
                obj.setIdActFisica(rs.getInt(3));
                obj.setRepeticiones(rs.getInt(4));
                obj.setDia(rs.getTimestamp(5).toInstant().atZone(systemDefault()).toLocalDateTime());
                obj.setTiempo(rs.getTime(6));
            }
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " to BuscarId " + obj.getIdActividad()+ " :");
            System.out.println(obj.toString());
            System.out.println(ex.getMessage() + ex.getCause().toString());
            obj = null;
        } finally {
            cerrar();
        }
        return obj;
    }
    
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<ActividadDTO> listarPorPaciente(int ID) {
        ActividadDTO obj;
        List<ActividadDTO> lista = new ArrayList<>();
        String sql = "Select * from Actividad where idPaciente=?";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, ID);
            rs = ps.executeQuery();
            while (rs.next()) {
                obj = new ActividadDTO();
                obj.setIdActividad(rs.getInt(1));
                obj.setIdPaciente(rs.getInt(2));
                obj.setIdActFisica(rs.getInt(3));
                obj.setRepeticiones(rs.getInt(4));
                obj.setDia(rs.getTimestamp(5).toInstant().atZone(systemDefault()).toLocalDateTime());
                obj.setTiempo(rs.getTime(6));
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
    
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<ActividadDTO> listarPorPacienteEjercicio(ActividadDTO obj) {
        List<ActividadDTO> lista = new ArrayList<>();
        String sql = "Select * from Actividad where idPaciente=?, idActFisica=?";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, obj.getIdPaciente());
            ps.setInt(2, obj.getIdActFisica());
            rs = ps.executeQuery();
            while (rs.next()) {
                obj = new ActividadDTO();
                obj.setIdActividad(rs.getInt(1));
                obj.setIdPaciente(rs.getInt(2));
                obj.setIdActFisica(rs.getInt(3));
                obj.setRepeticiones(rs.getInt(4));
                obj.setDia(rs.getTimestamp(6).toInstant().atZone(systemDefault()).toLocalDateTime());
                obj.setTiempo(rs.getTime(6));
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
