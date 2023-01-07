package com.example.diabetrometrov01.DataAccessObject;

import static java.time.ZoneId.systemDefault;

import android.os.Build;

import androidx.annotation.RequiresApi;
import com.example.diabetrometrov01.DataTransferObject.PacienteDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO extends Conecsion implements CRUD<PacienteDTO> {

    java.sql.Connection cn;
    Statement ps;
    PreparedStatement pse;
    ResultSet rs;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public List<PacienteDTO> listar() {
        List<PacienteDTO> lista = new ArrayList<>();
        String sql = "Select * from Paciente";
        cn = conectar();
        try {
            pse = cn.prepareStatement(sql);
            rs = pse.executeQuery();
            while (rs.next()) {
                PacienteDTO obj = new PacienteDTO();
                obj.setIdPaciente(rs.getInt(1));
                obj.setCorreo(rs.getString(2));
                obj.setDNI(rs.getString(3));
                obj.setContrasena(rs.getString(4));
                obj.setNombre(rs.getString(5));
                obj.setApellidos(rs.getString(6));
                obj.setSexo(rs.getString(7).charAt(0));
                obj.setFechaNacimiento(LocalDate.parse(rs.getDate(8).toString()));
                obj.setRegistro(LocalDateTime.parse(rs.getString(9)));
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
    public boolean agregar(PacienteDTO obj) {
        String sql = "insert into Paciente values(" +
                "'"+ obj.getCorreo() +"'," +
                "'"+ obj.getDNI() +"'," +
                "'"+ obj.getContrasena() +"'," +
                "'"+ obj.getNombre() +"'," +
                "'"+ obj.getApellidos() +"'," +
                "'"+ obj.getSexo() +"'," +
                "'"+ obj.ApplyFormat(obj.getFechaNacimiento()) +"'," +
                "'"+ obj.ApplyFormat(obj.getRegistro()) +"')";
        try {
            cn = conectar();
            ps = cn.createStatement();
            return ps.executeUpdate(sql) == 1;
        } catch (SQLException ex) {
            setErrorInDB("Error al agregar "+ex.getMessage());
            return false;
        } finally {
            cerrar();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean actualizar(PacienteDTO obj) { //DNI,Contrasena,Nombre,Apellidos,Sexo,Peso,Nivel_d_Glucosa,FechaNacimiento
        int r;
        String sql = "update Paciente set Nombre=?,Apellidos=?,Sexo=?,FechaNacimiento=? where idPaciente=?";
        cn = conectar();
        try {
            pse = cn.prepareStatement(sql);
            pse.setString(1, obj.getNombre());
            pse.setString(2, obj.getApellidos());
            pse.setString(3, String.valueOf(obj.getSexo()));
            pse.setDate(4, java.sql.Date.valueOf(String.valueOf(obj.getFechaNacimiento())));
            pse.setInt(5, obj.getIdPaciente());
            r = pse.executeUpdate();
            return r == 1;
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " to actualizar " + obj.getDNI() + " :");
            System.out.println(obj.toString());
            System.out.println(ex.getMessage() + ex.getCause());
            return false;
        } finally {
            cerrar();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean eliminar(PacienteDTO obj) {
        int r;
        String sql = "delete from Paciente where idPaciente=?";
        cn = conectar();
        try {
            pse = cn.prepareStatement(sql);
            pse.setInt(1, obj.getIdPaciente());
            r = pse.executeUpdate();
            return r == 1;
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " to delete " + obj.getDNI() + " :");
            System.out.println(obj.toString());
            System.out.println(ex.getMessage() + ex.getCause());
            return false;
        } finally {
            cerrar();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public PacienteDTO buscar(PacienteDTO obj, int caso) {
        try {
            cn = conectar();
            String sql = "";
            switch (caso) {
                case 1:
                    sql = "select * from Paciente where DNI=?";
                    pse = cn.prepareStatement(sql);
                    pse.setString(1, obj.getDNI());
                    break; //Busca por DNI
                case 2:
                    sql = "select * from Paciente where CorreoElectronico=?";
                    pse = cn.prepareStatement(sql);
                    pse.setString(1, obj.getCorreo());
                    break; //Busca por Correo Electronico
                default:
                    sql = "select * from Paciente where idPaciente=?";
                    pse = cn.prepareStatement(sql);
                    pse.setInt(1, obj.getIdPaciente());
                    break; //Busca por ID
            }
            rs = pse.executeQuery();
            obj = null;
            while (rs.next()) {
                obj = new PacienteDTO();
                obj.setIdPaciente(rs.getInt(1));
                obj.setCorreo(rs.getString(2));
                obj.setDNI(rs.getString(3));
                obj.setContrasena(rs.getString(4));
                obj.setNombre(rs.getString(5));
                obj.setApellidos(rs.getString(6));
                obj.setSexo(rs.getString(7).charAt(0));
                obj.setFechaNacimiento(LocalDate.parse(rs.getDate(8).toString()));
                obj.setRegistro(rs.getTimestamp(9).toInstant().atZone(systemDefault()).toLocalDateTime());
            }
        } catch (SQLException ex) {
            setErrorInDB("Error " + this.getClass().getName() + " to BuscarId " + " :" + ex.getMessage() + ex.getCause());
            obj = null;
        } finally {
            cerrar();
        }
        return obj;
    }
}
