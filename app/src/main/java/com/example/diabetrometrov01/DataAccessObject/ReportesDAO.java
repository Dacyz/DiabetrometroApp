package com.example.diabetrometrov01.DataAccessObject;

import static java.time.ZoneId.systemDefault;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.diabetrometrov01.DataTransferObject.FraseMotivadoraDTO;
import com.example.diabetrometrov01.DataTransferObject.PacienteDTO;
import com.example.diabetrometrov01.DataTransferObject.PacienteDatosDTO;
import com.example.diabetrometrov01.DataTransferObject.ReportesDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReportesDAO extends Conecsion implements CRUD<ReportesDTO> {

    java.sql.Connection cn;
    PreparedStatement ps;
    Statement pse;
    ResultSet rs;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public List<ReportesDTO> listar() {
        List<ReportesDTO> lista = new ArrayList<>();
        String sql = "Select * from Reporte";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ReportesDTO obj = new ReportesDTO();
                obj.setIdReporte(rs.getInt(1));
                obj.setIdPaciente(rs.getInt(2));
                obj.setIdFraseMot(rs.getInt(3));
                obj.setFechaInicio(LocalDate.parse(rs.getDate(4).toString()));
                obj.setFechaFinal(LocalDate.parse(rs.getDate(5).toString()));
                obj.setObservacion(rs.getString(6));
                obj.setPesoFinal(rs.getFloat(7));
                obj.setTallaFinal(rs.getFloat(8));
                obj.setLvlGlucosafinal(rs.getFloat(9));
                obj.setPorcionProm(rs.getFloat(10));
                obj.setCaloriasProm(rs.getFloat(11));
                obj.setCarbohidratosProm(rs.getFloat(12));
                obj.setProteinas(rs.getFloat(13));
                obj.setGrasas(rs.getFloat(14));
                obj.setTallaProm(rs.getFloat(15));
                obj.setPesoProm(rs.getFloat(16));
                obj.setLvlGlucosaProm(rs.getFloat(17));
                obj.setNombre(rs.getString(18));
                obj.setDia(LocalDateTime.parse(rs.getString(19)));
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
    public boolean agregar(ReportesDTO obj) {
        int r;
        //String sql = "insert into Reporte(idPaciente,idFraseMotivadora,FechaInicio,FechaFinal,Observacion,PesoFinal,TallaFinal,NivelGluFinal,PorcionProm," +
       //        "CaloriasProm,CarbohidratosProm,ProteinasProm,GrasasProm,TallaProm,PesoProm,NivelGluProm,NombreReporte,Dia)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        String sql = "insert into Reporte values(" +
                "'"+ obj.getIdPaciente() +"'," +
                "'"+ obj.getIdFraseMot() +"'," +
                "'"+ obj.ApplyFormat(obj.getFechaInicio()) +"'," +
                "'"+ obj.ApplyFormat(obj.getFechaFinal()) +"'," +
                "'"+ obj.getObservacion() +"'," +
                "'"+ obj.getPesoFinal() +"'," +
                "'"+ obj.getTallaFinal() +"'," +
                "'"+ obj.getLvlGlucosafinal() +"'," +
                "'"+ obj.getPorcionProm() +"'," +
                "'"+ obj.getCaloriasProm() +"'," +
                "'"+ obj.getCarbohidratosProm() +"'," +
                "'"+ obj.getProteinas() +"'," +
                "'"+ obj.getGrasas() +"'," +
                "'"+ obj.getTallaProm() +"'," +
                "'"+ obj.getPesoProm() +"'," +
                "'"+ obj.getLvlGlucosaProm() +"'," +
                "'"+ obj.getNombre() +"'," +
                "'"+ obj.ApplyFormat(LocalDateTime.now()) +"')";
        cn = conectar();
        try {
            /*
            ps = cn.prepareStatement(sql);
            ps.setInt(1, obj.getIdPaciente());
            ps.setInt(2, obj.getIdFraseMot());
            ps.setDate(3, java.sql.Date.valueOf(obj.getFechaInicio().toString()));
            ps.setDate(4, java.sql.Date.valueOf(obj.getFechaFinal().toString()));
            ps.setString(5, obj.getObservacion());
            ps.setFloat(6, obj.getPesoFinal());
            ps.setFloat(7, obj.getTallaFinal());
            ps.setFloat(8, obj.getLvlGlucosafinal());
            ps.setFloat(9, obj.getPorcionProm());
            ps.setFloat(10, obj.getCaloriasProm());
            ps.setFloat(11, obj.getCarbohidratosProm());
            ps.setFloat(12, obj.getProteinas());
            ps.setFloat(13, obj.getGrasas());
            ps.setFloat(14, obj.getTallaProm());
            ps.setFloat(15, obj.getPesoProm());
            ps.setFloat(16, obj.getLvlGlucosaProm());
            ps.setString(17, obj.getNombre());
            ps.setTimestamp(18, Timestamp.valueOf(obj.ApplyFormat(LocalDateTime.now())));
            r = ps.executeUpdate();
             */
            cn = conectar();
            pse = cn.createStatement();
            return pse.executeUpdate(sql) == 1;
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " to add " + obj.getIdPaciente() + " :");
            System.out.println(obj.toString());
            System.out.println(ex.getMessage() + ex.getCause());
            return false;
        } finally {
            cerrar();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean actualizar(ReportesDTO obj) {
        int r;
        String sql = "update Reporte set idPaciente=?,idFraseMotivadora=?,FechaInicio=?,FechaFinal=?,Observacion=?,Proyeccion=?,NivelGluProm=?,CaConsumidas=?,PesoProm=?,PesoFinal=? where idReporte=?";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, obj.getIdPaciente());
            ps.setInt(2, obj.getIdFraseMot());
            ps.setDate(3, java.sql.Date.valueOf(obj.getFechaInicio().toString()));
            ps.setDate(4, java.sql.Date.valueOf(obj.getFechaFinal().toString()));
            ps.setString(5, obj.getObservacion());
            ps.setFloat(8, obj.getPesoProm());
            ps.setFloat(9, obj.getPesoFinal());
            ps.setInt(10, obj.getIdReporte());
            r = ps.executeUpdate();
            return r == 1;
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " to actualizar " + obj.getIdReporte() + " :");
            System.out.println(obj.toString());
            System.out.println(ex.getMessage() + ex.getCause().toString());
            return false;
        } finally {
            cerrar();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean eliminar(ReportesDTO obj) {
        int r;
        String sql = "delete from Reporte where idReporte=?";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, obj.getIdReporte());
            r = ps.executeUpdate();
            return r == 1;
        } catch (SQLException ex) {
            setErrorInDB(ex.getMessage() + ex.getCause());
            return false;
        } finally {
            cerrar();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public ReportesDTO buscar(ReportesDTO obj, int tipodato) {
        String sql = "select * from Reporte where idReporte=?";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, obj.getIdPaciente());
            rs = ps.executeQuery();
            obj = null;
            while (rs.next()) {
                obj = new ReportesDTO();
                obj.setIdReporte(rs.getInt(1));
                obj.setIdPaciente(rs.getInt(2));
                obj.setIdFraseMot(rs.getInt(3));
                obj.setFechaInicio(LocalDate.parse(rs.getDate(4).toString()));
                obj.setFechaFinal(LocalDate.parse(rs.getDate(5).toString()));
                obj.setObservacion(rs.getString(6));
                obj.setPesoFinal(rs.getFloat(8));
                obj.setTallaFinal(rs.getFloat(9));
                obj.setLvlGlucosafinal(rs.getFloat(10));
                obj.setPorcionProm(rs.getFloat(11));
                obj.setCaloriasProm(rs.getFloat(12));
                obj.setCarbohidratosProm(rs.getFloat(13));
                obj.setProteinas(rs.getFloat(14));
                obj.setGrasas(rs.getFloat(15));
                obj.setTallaProm(rs.getFloat(16));
                obj.setPesoProm(rs.getFloat(17));
                obj.setLvlGlucosaProm(rs.getFloat(18));
                obj.setDia(LocalDateTime.parse(rs.getString(19)));
            }
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " to BuscarId " + obj.getIdReporte() + " :");
            System.out.println(obj.toString());
            System.out.println(ex.getMessage() + ex.getCause().toString());
            obj = null;
        } finally {
            cerrar();
        }
        return obj;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<ReportesDTO> listarPorPaciente(int id) {
        List<ReportesDTO> lista = new ArrayList<>();
        String sql = "Select * from Reporte where idPaciente=?";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                ReportesDTO obj = new ReportesDTO();
                obj.setIdReporte(rs.getInt(1));
                obj.setIdPaciente(rs.getInt(2));
                obj.setIdFraseMot(rs.getInt(3));
                obj.setFechaInicio(LocalDate.parse(rs.getDate(4).toString()));
                obj.setFechaFinal(LocalDate.parse(rs.getDate(5).toString()));
                obj.setObservacion(rs.getString(6));
                obj.setPesoFinal(rs.getFloat(7));
                obj.setTallaFinal(rs.getFloat(8));
                obj.setLvlGlucosafinal(rs.getFloat(9));
                obj.setPorcionProm(rs.getFloat(10));
                obj.setCaloriasProm(rs.getFloat(11));
                obj.setCarbohidratosProm(rs.getFloat(12));
                obj.setProteinas(rs.getFloat(13));
                obj.setGrasas(rs.getFloat(14));
                obj.setTallaProm(rs.getFloat(15));
                obj.setPesoProm(rs.getFloat(16));
                obj.setLvlGlucosaProm(rs.getFloat(17));
                obj.setNombre(rs.getString(18));
                obj.setDia(rs.getTimestamp(19).toInstant().atZone(systemDefault()).toLocalDateTime());
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

    public FraseMotivadoraDTO FraseMotivacional(int IdFrase) {
        String sql = "select * from FraseMotivacional where IdFraseMotivadora=?";
        FraseMotivadoraDTO obj = null;
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, IdFrase);
            rs = ps.executeQuery();
            while (rs.next()) {
                obj = new FraseMotivadoraDTO();
                obj.setIdFrase(rs.getInt(1));
                obj.setFrase(rs.getString(2));
                obj.setAutor(rs.getString(3));
            }
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " to BuscarId " + IdFrase + " :");
            System.out.println(ex.getMessage() + ex.getCause().toString());
            obj = null;
        } finally {
            cerrar();
        }
        return obj;
    }

    /*
        En general, la Organización Mundial de la Salud establece un cálculo genérico: entre 1.600
        y 2.000 calorías al día para las mujeres, y para los hombres entre 2.000 y 2.500. Pero para
        conocer la necesidad energética de cada persona de manera más exacta hay que tener en
        cuenta 2 factores: el metabolismo basal y la actividad física.

        Cuántas calorías hay que comer al día, según los expertos

            Mujeres: [655 + (9,6 × peso en kg) ] + [ (1,8 × altura en cm) – (4,7 × edad)] × Factor actividad.
            Hombres: [66 + (13,7 × peso en kg) ] + [ (5 × altura en cm) – (6,8 × edad)] × Factor actividad.

        Para añadir la cifra correspondiente al ‘factor actividad’ hay que tener en cuenta estas condiciones:

            1,2 para personas sedentarias
            1,375 para personas con poca actividad física (ejercicio de 1 a 3 veces por semana).
            1,55 para individuos que realizan actividad moderada (ejercicio de 3 a 5 veces por semana).
            1,725 para personas que hacen actividad intensa (ejercicio de 6 a 7 veces por semana).
            1,9 para atletas profesionales (entrenamientos de más de 4 horas diarias).

        Aunque esta fórmula es algo compleja, hay otras más sencillas que también darán un resultado aproximado para los adultos:

            Hombres: multiplicar el peso en kg por 25.
            Mujeres: multiplicar el peso en kg por 23.
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    private float getCaloriasRecomendadas(ReportesDTO obj) {
        float peso = 0;
        float talla = 0;
        int edad = 0;
        String sql = "SELECT TOP 1 PacienteDatos.Peso, PacienteDatos.Talla, DATEDIFF(YEAR,Paciente.FechaNacimiento,GETDATE())" +
                "FROM            Paciente INNER JOIN PacienteDatos ON Paciente.IdPaciente = PacienteDatos.IdPaciente where " +
                "PacienteDatos.IdPaciente =  '" + obj.getIdPaciente() + "' and " +
                "Día >=  '" + obj.ApplyFormat(obj.getFechaInicio()) + "' and " +
                "Día <= dateadd(day, 1, '" + obj.ApplyFormat(obj.getFechaFinal()) + "') ORDER BY IdDatos DESC ";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                peso = rs.getFloat(1);
                talla = rs.getFloat(2);
                edad = rs.getInt(3);
            }
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " at list ");
            System.out.println(ex.getMessage() + ex.getCause().toString());
        } finally {
            cerrar();
        }
        return (float) ((655 + (9.6 * peso))
                + ((1.8 * talla * 100) - (4.7 * edad))
                * getFactorActividad(obj));
    }

    /*
    SELECT
        AVG(Ingesta.Porcion) AS 'Porcion Promedio consumida',
        AVG(Ingesta.Porcion*Alimento.Calorias/Alimento.TamañoEstPorcion) as 'Calorias Promedio',
        AVG(Ingesta.Porcion*Alimento.Carbohidratos/Alimento.TamañoEstPorcion) as 'Carbohidratos Promedio',
        AVG(Ingesta.Porcion*Alimento.Proteinas/Alimento.TamañoEstPorcion) as 'Proteinas Promedio',
        AVG(Ingesta.Porcion*Alimento.Grasas/Alimento.TamañoEstPorcion) as 'Grasas Promedio'
        FROM Alimento INNER JOIN Ingesta ON Alimento.IdAlimento = Ingesta.IdAlimento where Ingesta.IdPaciente = '10' and Ingesta.Día >= '2021-11-21' and Ingesta.Día <= dateadd(day, 1,'2021-11-24');
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ReportesDTO generateReport(ReportesDTO obj) {
        if(HayRegistros(obj)){
            getDataAlimenticia(obj);
            getDataMedicion(obj);
            getDataFinal(obj);
            getObservation(obj);
            getFrase(obj);
            if(!agregar(obj)){
                obj = null;
            }
        } else {
            obj = null;
        }
        return obj;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ReportesDTO getFrase(ReportesDTO obj) {
        obj.setIdFraseMot(obj.getRandomNumber(18));
        return obj;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ReportesDTO getObservation(ReportesDTO obj) {
        String Obser = "Durante el periodo seleccionado ";
        if (obj.getPesoFinal() < obj.getPesoProm()) {
            Obser = Obser + "tu peso ha incrementado, ";
        } else if (obj.getPesoFinal() > obj.getPesoProm()){
            Obser = Obser + "tu peso ha decrementado, ";
        } else{
            Obser = Obser + "tu peso se ha mantenido, ";
        }
        if (obj.getTallaFinal() < obj.getTallaProm()) {
            Obser = Obser + "tu altura ha incrementado, ";
        } else if (obj.getTallaFinal() > obj.getTallaProm()) {
            Obser = Obser + "tu altura ha decrementado, ";
        } else{
            Obser = Obser + "tu altura se ha mantenido, ";
        }
        if (obj.getLvlGlucosafinal() < obj.getLvlGlucosaProm()) {
            Obser = Obser + "tu nivel de glucosa ha incrementado y ";
        } else if (obj.getLvlGlucosafinal() > obj.getLvlGlucosaProm()) {
            Obser = Obser + "tu nivel de glucosa ha decrementado y ";
        } else{
            Obser = Obser + "tu nivel de glucosa se ha mantenido y ";
        }
        if (getCaloriasRecomendadas(obj) < obj.getCaloriasProm()) {
            Obser = Obser + "has consumido demás de lo recomendado ";
        } else if (getCaloriasRecomendadas(obj) >= obj.getCaloriasProm()) {
            Obser = Obser + "has consumido dentro de lo recomendado";
        }
        obj.setObservacion(Obser);
        return obj;
    }



    /*
             ¿Existen registros?
            ALTER PROCEDURE HayRegistros @idUser int, @inicio date, @final date AS
            begin declare @booleam bit
        if exists (SELECT * FROM PacienteDatos where IdPaciente = @idUser and Día >= @inicio and Día <= dateadd(day, 1,@final)) and exists (SELECT * FROM Actividad where IdPaciente = @idUser and Dia >= @inicio and Dia <= dateadd(day, 1,@final)) and exists (SELECT * FROM Ingesta where IdPaciente = @idUser and Día >= @inicio and Día <= dateadd(day, 1,@final))
            begin SET @booleam = 'True' end
        else begin SET @booleam = 'False'end
            SELECT @booleam as 'Result' end
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean HayRegistros(ReportesDTO obj){
        boolean result = false;
        String sql = "execute HayRegistros @idUser = '" + obj.getIdPaciente()+"', " +
                "@inicio = '"+obj.ApplyFormat(obj.getFechaInicio())+"', " +
                "@final = '"+obj.ApplyFormat(obj.getFechaFinal())+"' ";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                result = rs.getBoolean(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " at list ");
            System.out.println(ex.getMessage() + ex.getCause().toString());
        } finally {
            cerrar();
        }
        return result;
    }

    /*
        Sentencia de Sql Server para extraer las ultimas talla, peso y nivel de glucosa del paciente hasta ese momento
        SELECT TOP 1 Talla, Peso, LvlGlucosa FROM PacienteDatos where IdPaciente = '10' and Día
        >= '2021-11-21' and Día <= dateadd(day, 1,'2021-11-24') ORDER BY IdDatos DESC
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ReportesDTO getDataFinal(ReportesDTO obj) {
        String sql = "SELECT TOP 1 Talla, Peso, LvlGlucosa FROM PacienteDatos where " +
                "IdPaciente =  '" + obj.getIdPaciente() + "' and " +
                "Día >=  '" + obj.ApplyFormat(obj.getFechaInicio()) + "' and " +
                "Día <= dateadd(day, 1, '" + obj.ApplyFormat(obj.getFechaFinal()) + "') ORDER BY IdDatos DESC ";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                obj.setTallaFinal(rs.getFloat(1));
                obj.setPesoFinal(rs.getFloat(2));
                obj.setLvlGlucosafinal(rs.getFloat(3));
            }
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " at list ");
            System.out.println(ex.getMessage() + ex.getCause().toString());
        } finally {
            cerrar();
        }
        return obj;
    }

    /*
    SELECT
        AVG(Ingesta.Porcion) AS 'Porcion Promedio consumida',
        AVG(Ingesta.Porcion*Alimento.Calorias/Alimento.TamañoEstPorcion) as 'Calorias Promedio',
        AVG(Ingesta.Porcion*Alimento.Carbohidratos/Alimento.TamañoEstPorcion) as 'Carbohidratos Promedio',
        AVG(Ingesta.Porcion*Alimento.Proteinas/Alimento.TamañoEstPorcion) as 'Proteinas Promedio',
        AVG(Ingesta.Porcion*Alimento.Grasas/Alimento.TamañoEstPorcion) as 'Grasas Promedio'
        FROM Alimento INNER JOIN Ingesta ON Alimento.IdAlimento = Ingesta.IdAlimento where Ingesta.IdPaciente = '10' and Ingesta.Día >= '2021-11-21' and Ingesta.Día <= dateadd(day, 1,'2021-11-24');
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ReportesDTO getDataAlimenticia(ReportesDTO obj) {
        String sql = "SELECT " +
                "AVG(Ingesta.Porcion), " +                                                   // Porcion Promedio consumida
                "AVG(Ingesta.Porcion*Alimento.Calorias/Alimento.TamañoEstPorcion), " +        // Calorias Promedio
                "AVG(Ingesta.Porcion*Alimento.Carbohidratos/Alimento.TamañoEstPorcion), " +   // Carbohidratos Promedio
                "AVG(Ingesta.Porcion*Alimento.Proteinas/Alimento.TamañoEstPorcion), " +       // Proteinas Promedio
                "AVG(Ingesta.Porcion*Alimento.Grasas/Alimento.TamañoEstPorcion) " +          // Grasas Promedio
                "FROM Alimento INNER JOIN Ingesta ON Alimento.IdAlimento = Ingesta.IdAlimento where " +
                "Ingesta.IdPaciente = '" + obj.getIdPaciente() + "' and " +
                "Ingesta.Día >= '" + obj.getFechaInicio() + "' and " +
                "Ingesta.Día <= dateadd(day, 1, '" + obj.getFechaFinal() + "' ) ;";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                obj.setPorcionProm(rs.getFloat(1));
                obj.setCaloriasProm(rs.getFloat(2));
                obj.setCarbohidratosProm(rs.getFloat(3));
                obj.setProteinas(rs.getFloat(4));
                obj.setGrasas(rs.getFloat(5));
            }
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " at list ");
            System.out.println(ex.getMessage() + ex.getCause().toString());
        } finally {
            cerrar();
        }
        return obj;
    }

    /*
    SELECT
        AVG(Peso),
        AVG(Talla),
        AVG(LvlGlucosa)
        FROM PacienteDatos where IdPaciente = '10' and Día >= '2021-11-21' and Día <= dateadd(day, 1,'2021-11-24');
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ReportesDTO getDataMedicion(ReportesDTO obj) {
        String sql = "SELECT " +
                "AVG(Peso), " +
                "AVG(Talla), " +
                "AVG(LvlGlucosa) " +
                "FROM PacienteDatos where " +
                "IdPaciente =  '" + obj.getIdPaciente() + "' and " +
                "Día >=  '" + obj.ApplyFormat(obj.getFechaInicio()) + "' and " +
                "Día <= dateadd(day, 1, '" + obj.ApplyFormat(obj.getFechaFinal()) + "');";
        cn = conectar();
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                obj.setPesoProm(rs.getFloat(1));
                obj.setTallaProm(rs.getFloat(2));
                obj.setLvlGlucosaProm(rs.getFloat(3));
            }
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " at list ");
            System.out.println(ex.getMessage() + ex.getCause().toString());
        } finally {
            cerrar();
        }
        return obj;
    }

    /*
    Select count(*) from Actividad where IdPaciente = '10'
						 and Dia >= dateadd(day, -7,'2021-11-24') and Actividad.Dia <= dateadd(day, 1,'2021-11-24') ;
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    private float getFactorActividad(ReportesDTO obj) {
        float FactorActividad;
        String sql = "Select count(*) from Actividad where " +
                "IdPaciente = '" + obj.getIdPaciente() + "' and" +
                "Dia >= dateadd(day, -7,'" + obj.ApplyFormat(obj.getFechaInicio()) + "') and " +
                "Dia <= dateadd(day, 1, '" + obj.ApplyFormat(obj.getFechaFinal()) + "')";
        cn = conectar();
        FactorActividad = (float) 1.2;
        try {
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int aux = rs.getInt(1);
                if (aux == 0) {
                    FactorActividad = (float) 1.2;
                } else if (aux > 0 && aux <= 3) {
                    FactorActividad = (float) 1.375;
                } else if (aux > 3 && aux <= 7) {
                    FactorActividad = (float) 1.55;
                } else if (aux > 7 && aux <= 13) {
                    FactorActividad = (float) 1.725;
                } else if (aux > 13) {
                    FactorActividad = (float) 1.9;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error " + this.getClass().getName() + " at list ");
            System.out.println(ex.getMessage() + ex.getCause());
        } finally {
            cerrar();
        }
        return FactorActividad;
    }
}
