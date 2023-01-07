package com.example.diabetrometrov01.DataAccessObject;

import android.os.StrictMode;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class Conecsion {

    protected Connection conexion;
    private String Error;

    public Connection conectar() {
        try {
            StrictMode.ThreadPolicy politica = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(politica);
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.1.100:1433;databaseName=Adan;user=sa;password=12345;");
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            Error = ex.getMessage();
        }
        return conexion;
    }

    public void cerrar() {
        if (conexion != null) {
            try {
                if (!conexion.isClosed()) {
                    conexion.close();
                }
            } catch (SQLException ex) {
                Error = ex.getMessage();
            }
        }
    }

    public String getErrorInDB(){
        return Error;
    }
    public void setErrorInDB(String Error){
        this.Error = Error;
    }
}
