package com.example.diabetrometrov01.DataAccessObject;

import static java.time.ZoneId.systemDefault;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.diabetrometrov01.DataTransferObject.IngestaDTO;
import com.example.diabetrometrov01.DataTransferObject.PacienteDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class IngestaAlimenticiaDAO extends Conecsion implements CRUD<IngestaDTO>{
    
    java.sql.Connection cn;
    Statement pes;
    PreparedStatement ps;
    ResultSet rs;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public List<IngestaDTO> listar() {
        List<IngestaDTO> lista = new ArrayList<>();
        String sql = "Select * from Ingesta";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                IngestaDTO obj = new IngestaDTO();
                obj.setIdIngesta(rs.getInt(1));
                obj.setIdPaciente(rs.getInt(2));
                obj.setIdAlimento(rs.getInt(3));
                obj.setPorcion(rs.getFloat(4));
                obj.setHoraConsumo(rs.getTime(5));
                obj.setDia(rs.getTimestamp(6).toInstant().atZone(systemDefault()).toLocalDateTime());
                lista.add(obj);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error " + this.getClass().getName() + " at list ");
        } finally {
            cerrar();
        }
        return lista;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<IngestaDTO> listarxPaciente(int idPaciente) {
        List<IngestaDTO> lista = new ArrayList<>();
        String sql = "Select * from Ingesta where IdPaciente="+idPaciente+"";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                IngestaDTO obj = new IngestaDTO();
                obj.setIdIngesta(rs.getInt(1));
                obj.setIdPaciente(rs.getInt(2));
                obj.setIdAlimento(rs.getInt(3));
                obj.setPorcion(rs.getFloat(4));
                obj.setHoraConsumo(rs.getTime(5));
                obj.setDia(rs.getTimestamp(6).toInstant().atZone(systemDefault()).toLocalDateTime());
                lista.add(obj);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error " + this.getClass().getName() + " at list ");
        } finally {
            cerrar();
        }
        return lista;
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean agregar(IngestaDTO obj) {
        String sql = "insert into Ingesta values(" +
                "'"+ obj.getIdPaciente() +"'," +
                "'"+ obj.getIdAlimento() +"'," +
                "'"+ obj.getPorcion() +"'," +
                "'"+ obj.ApplyFormat(obj.getHoraConsumo()) +"'," +
                "'"+ obj.ApplyFormat(obj.getDia()) +"')";
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
    public boolean actualizar(IngestaDTO obj) {
        int r;
        String sql = "update Ingesta set Porcion=?,Hora=? where IdAlimentaria=?";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            ps.setFloat(1, obj.getPorcion());
            ps.setTime(2, obj.getHoraConsumo());
            ps.setInt(3, obj.getIdIngesta());
            r = ps.executeUpdate();
            return r == 1;
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " to actualizar " + obj.getIdAlimento()+ " :");
            System.out.println(obj);
            System.out.println(ex.getMessage() + ex.getCause());
            return false;
        } finally {
            cerrar();
        }
    }

    @Override
    public boolean eliminar(IngestaDTO obj) {
        int r;
        String sql = "delete from Ingesta where IdAlimentaria=?";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, obj.getIdIngesta());
            r = ps.executeUpdate();
            return r == 1;
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " to delete " + obj.getIdAlimento()+ " :");
            System.out.println(obj);
            System.out.println(ex.getMessage() + ex.getCause());
            return false;
        } finally {
            cerrar();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public IngestaDTO buscar(IngestaDTO obj, int tipodato) {
        String sql = "select * from Ingesta where IdAlimentaria=?";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, obj.getIdPaciente());
            rs = ps.executeQuery();
            obj = null;
            while (rs.next()) {
                obj = new IngestaDTO();
                obj.setIdIngesta(rs.getInt(1));
                obj.setIdAlimento(rs.getInt(2));
                obj.setIdPaciente(rs.getInt(3));
                obj.setPorcion(rs.getInt(4));
                obj.setHoraConsumo(rs.getTime(5));
                obj.setDia(LocalDateTime.parse(rs.getString(9)));
            }
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " to BuscarId " + obj.getIdAlimento()+ " :");
            System.out.println(obj.toString());
            System.out.println(ex.getMessage() + ex.getCause().toString());
            obj = null;
        } finally {
            cerrar();
        }
        return obj;
    }
    
}
