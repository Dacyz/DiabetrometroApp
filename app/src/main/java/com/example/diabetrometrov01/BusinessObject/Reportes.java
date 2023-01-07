package com.example.diabetrometrov01.BusinessObject;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.diabetrometrov01.DataTransferObject.Format;
import com.example.diabetrometrov01.DataAccessObject.ReportesDAO;
import com.example.diabetrometrov01.DataTransferObject.FraseMotivadoraDTO;
import com.example.diabetrometrov01.DataTransferObject.ReportesDTO;
import com.example.diabetrometrov01.R;

import java.time.LocalDate;
import java.util.List;

public class Reportes extends Format {

    ReportesDAO DB = new ReportesDAO();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<ReportesDTO> listar(int id) {
        List<ReportesDTO> lista = DB.listarPorPaciente(id);
        return lista;
    }

    public void imprimirEnPdf(){

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean generarReport(int IdPaciente, String Nombre, LocalDate FechaInicial, LocalDate FechaFinal){
            return DB.generateReport(new ReportesDTO(Nombre, IdPaciente, FechaInicial, FechaFinal)) != null;
    }
}
