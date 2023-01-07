package com.example.diabetrometrov01.DataAccessObject;

import static java.time.ZoneId.systemDefault;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.diabetrometrov01.DataTransferObject.PacienteDTO;
import com.example.diabetrometrov01.DataTransferObject.PacienteDatosDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PacienteDatosDAO extends Conecsion implements CRUD<PacienteDatosDTO>{
    
    java.sql.Connection cn;
    PreparedStatement ps;
    Statement pes;
    ResultSet rs;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public List<PacienteDatosDTO> listar() {
        List<PacienteDatosDTO> lista = new ArrayList<>();
        String sql = "Select * from PacienteDatos";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                PacienteDatosDTO obj = new PacienteDatosDTO();
                obj.setIdDatos(rs.getInt(1));
                obj.setIdPaciente(rs.getInt(2));
                obj.setTalla(rs.getFloat(3));
                obj.setPeso(rs.getFloat(4));
                obj.setLvlglucosa(rs.getFloat(5));
                obj.setDia(rs.getTimestamp(6).toInstant().atZone(systemDefault()).toLocalDate());
                lista.add(obj);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            setErrorInDB(ex.getMessage());
        } finally {
            cerrar();
        }
        return lista;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean agregar(PacienteDatosDTO obj) {
        int r;
        String sql = "insert into PacienteDatos values(" +
                "'"+ obj.getIdPaciente() +"'," +
                "'"+ obj.getTalla() +"'," +
                "'"+ obj.getPeso() +"'," +
                "'"+ obj.getLvlglucosa() +"'," +
                "'"+ obj.ApplyFormat(obj.getDia()) +"')";
        cn = conectar();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean actualizar(PacienteDatosDTO obj) {
        int r;
        String sql = "update PacienteDatos set Talla=?,Peso=?,LvlGlucosa=? where idDatos='"+obj.getIdDatos()+"'";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            ps.setFloat(1, obj.getTalla());
            ps.setFloat(2, obj.getPeso());
            ps.setFloat(3, obj.getLvlglucosa());
            r = ps.executeUpdate();
            return r == 1;
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " to actualizar " + obj.getIdPaciente()+ " :");
            System.out.println(obj.toString());
            System.out.println(ex.getMessage() + ex.getCause());
            return false;
        } finally {
            cerrar();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean eliminar(PacienteDatosDTO obj) {
        int r;
        String sql = "delete from PacienteDatos where idDatos=?";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, obj.getIdDatos());
            r = ps.executeUpdate();
            return r == 1;
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " to delete " + obj.getIdDatos()+ " :");
            System.out.println(obj.toString());
            System.out.println(ex.getMessage() + ex.getCause());
            return false;
        } finally {
            cerrar();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public PacienteDatosDTO buscar(PacienteDatosDTO obj, int tipodato) {
        String sql = "select * from Paciente where idPaciente=?";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, obj.getIdDatos());
            rs = ps.executeQuery();
            obj = null;
            while (rs.next()) {
                obj = new PacienteDatosDTO();
                obj.setIdDatos(rs.getInt(1));
                obj.setIdPaciente(rs.getInt(2));
                obj.setTalla(rs.getFloat(3));
                obj.setPeso(rs.getFloat(4));
                obj.setLvlglucosa(rs.getFloat(5));
                obj.setDia(rs.getTimestamp(6).toInstant().atZone(systemDefault()).toLocalDate());
            }
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " to BuscarId " + obj.getIdDatos()+ " :");
            System.out.println(obj.toString());
            System.out.println(ex.getMessage() + ex.getCause());
            obj = null;
        } finally {
            cerrar();
        }
        return obj;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<PacienteDatosDTO> listarPorPaciente(int ID) {
        List<PacienteDatosDTO> lista = new ArrayList<>();
        String sql = "Select * from PacienteDatos where IdPaciente = '"+ID+"'";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                PacienteDatosDTO obj = new PacienteDatosDTO();
                obj.setIdDatos(rs.getInt(1));
                obj.setIdPaciente(rs.getInt(2));
                obj.setTalla(rs.getFloat(3));
                obj.setPeso(rs.getFloat(4));
                obj.setLvlglucosa(rs.getFloat(5));
                obj.setDia(rs.getTimestamp(6).toInstant().atZone(systemDefault()).toLocalDate());
                lista.add(obj);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            setErrorInDB(ex.getMessage());
        } finally {
            cerrar();
        }
        return lista;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public PacienteDatosDTO buscaFecha(LocalDate DATE, int ID) {
        PacienteDatosDTO obj= new PacienteDatosDTO();
        String sql = "Select * from PacienteDatos where Día = '"+ obj.ApplyFormat(DATE) +"' and IdPaciente = '"+ID+"'";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            obj = null;
            while (rs.next()) {
                obj = new PacienteDatosDTO();
                obj.setIdDatos(rs.getInt(1));
                obj.setIdPaciente(rs.getInt(2));
                obj.setTalla(rs.getFloat(3));
                obj.setPeso(rs.getFloat(4));
                obj.setLvlglucosa(rs.getFloat(5));
                obj.setDia(rs.getTimestamp(6).toInstant().atZone(systemDefault()).toLocalDate());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            setErrorInDB(ex.getMessage());
            obj = null;
        } finally {
            cerrar();
        }
        return obj;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public PacienteDatosDTO buscarLast( int ID) {
        PacienteDatosDTO obj = null;
        String sql = "Select * from PacienteDatos where IdPaciente = '"+ID+"'";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                obj = new PacienteDatosDTO();
                obj.setIdDatos(rs.getInt(1));
                obj.setIdPaciente(rs.getInt(2));
                obj.setTalla(rs.getFloat(3));
                obj.setPeso(rs.getFloat(4));
                obj.setLvlglucosa(rs.getFloat(5));
                obj.setDia(rs.getTimestamp(6).toInstant().atZone(systemDefault()).toLocalDate());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            setErrorInDB(ex.getMessage());
            obj = null;
        } finally {
            cerrar();
        }
        return obj;
    }
}
