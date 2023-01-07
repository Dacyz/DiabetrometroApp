package com.example.diabetrometrov01;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diabetrometrov01.BusinessObject.Paciente;
import com.example.diabetrometrov01.BusinessObject.Reportes;
import com.example.diabetrometrov01.DataTransferObject.PacienteDTO;
import com.example.diabetrometrov01.Interfaces.Paciente.Login;
import com.example.diabetrometrov01.databinding.FragmentRegistroBinding;
import com.example.diabetrometrov01.databinding.FragmentReporteGenerarBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class ReporteGenerar extends Fragment {

    private FragmentReporteGenerarBinding vw;

    /*
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
     */

    Paciente pac = new Paciente();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatePickerDialog.OnDateSetListener setListener1;
        PacienteDTO paciente = pac.buscar(String.valueOf(Login.UsuarioAPP.getIdPaciente()),0);
        LocalDateTime registro = paciente.getRegistro();
        setListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                DecimalFormat DF = new DecimalFormat("00");
                vw.txtFechaInicioReporte.setText(
                        DF.format(year) + "-" + DF.format(month + 1) + "-" + DF.format(dayOfMonth));
            }
        };

        DatePickerDialog datePickerDialog1 = new DatePickerDialog(
                getContext(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth);
        datePickerDialog1.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog1.getDatePicker().setMinDate(registro.atZone(ZoneId.of("America/Los_Angeles")).toInstant().toEpochMilli());
        datePickerDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        vw.txtFechaInicioReporte.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (!datePickerDialog1.isShowing()) {
                    datePickerDialog1.show();
                }
            }
        });
        vw.txtFechaInicioReporte.setOnFocusChangeListener((v, hasFocus) -> {
            if (vw.txtFechaInicioReporte.isFocused() && !datePickerDialog1.isShowing()) {
                datePickerDialog1.show();
            }
        });
        datePickerDialog1.setOnDateSetListener(setListener1);

        DatePickerDialog.OnDateSetListener setListener2;

        setListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                DecimalFormat DF = new DecimalFormat("00");
                vw.txtFechaFinalReporte.setText(
                        DF.format(year) + "-" + DF.format(month + 1) + "-" + DF.format(dayOfMonth));
            }
        };
        DatePickerDialog datePickerDialog2 = new DatePickerDialog(
                getContext(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth);
        datePickerDialog2.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog2.getDatePicker().setMinDate(registro.atZone(ZoneId.of("America/Los_Angeles")).toInstant().toEpochMilli());
        datePickerDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        vw.txtFechaFinalReporte.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (!datePickerDialog2.isShowing()) {
                    datePickerDialog2.show();
                }
            }
        });
        vw.txtFechaFinalReporte.setOnFocusChangeListener((v, hasFocus) -> {
            if (vw.txtFechaFinalReporte.isFocused() && !datePickerDialog2.isShowing()) {
                datePickerDialog2.show();
            }
        });
        datePickerDialog2.setOnDateSetListener(setListener2);
    }

    Reportes report = new Reportes();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vw = FragmentReporteGenerarBinding.inflate(inflater, container, false);

        vw.BTNGenerarReporte.setOnClickListener(v -> {
            if(isCampoCompleted(vw.TILFechaFinalReporte,vw.txtFechaFinalReporte)
                    && isCampoCompleted(vw.TILFechaInicioReporte,vw.txtFechaInicioReporte)){
                if(vw.txtNombreReporte.getText().toString().equals("")){
                    MaterialAlertDialogBuilder Alert = new MaterialAlertDialogBuilder(getContext());
                    Alert.setTitle("!Recuerda¡");
                    Alert.setMessage("Estas enviando el reporte sin un nombre, esto no afecta en nada pero luego no se podrá modificar");
                    Alert.setPositiveButton("Ok.",(dialog, which) -> {
                        if(report.generarReport(
                                Login.UsuarioAPP.getIdPaciente(),
                                "Reporte manual",
                                getInicioReport(),
                                getFinalReport())){
                            Toast.makeText(getContext(), "Reporte generado con exito" , Toast.LENGTH_SHORT).show();
                            getParentFragmentManager().setFragmentResult("PICHACOLADA",new Bundle());
                            CleanComponents();
                        } else {
                            Toast.makeText(getContext(), "No hay suficientes registros" , Toast.LENGTH_SHORT).show();
                        }
                    });
                    Alert.show();
                } else{
                    if(report.generarReport(
                            Login.UsuarioAPP.getIdPaciente(),
                            vw.txtNombreReporte.getText().toString(),
                            getInicioReport(),
                            getFinalReport())){
                        Toast.makeText(getContext(), "Reporte generado con exito" , Toast.LENGTH_SHORT).show();
                        getParentFragmentManager().setFragmentResult("PICHACOLADA",new Bundle());
                        CleanComponents();
                    } else {
                        Toast.makeText(getContext(), "No hay suficientes registros" , Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        vw.BTNLimpiarReporte.setOnClickListener(v -> {
            CleanComponents();
        });

        Valitions(vw.TILFechaFinalReporte,vw.txtFechaFinalReporte);
        Valitions(vw.TILFechaInicioReporte,vw.txtFechaInicioReporte);

        return vw.getRoot();
    }

    private void CleanComponents() {
        vw.txtNombreReporte.setText("");
        vw.txtFechaInicioReporte.setText("");
        vw.txtFechaFinalReporte.setText("");
        vw.TILFechaInicioReporte.setError(null);
        vw.TILFechaFinalReporte.setError(null);
        vw.TILNombreReporte.setError(null);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private LocalDate getInicioReport() {
        return LocalDate.parse(vw.txtFechaInicioReporte.getText().toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private LocalDate getFinalReport() {
        return LocalDate.parse(vw.txtFechaFinalReporte.getText().toString());
    }

    private void Valitions(TextInputLayout exs, EditText ex) {
        ex.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().equals("")) {
                    exs.setError(null);
                } else {
                    exs.setError(getString(R.string.REQUIRED_CAMPO));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private boolean isCampoCompleted(TextInputLayout exs, EditText ex) {
        boolean result = ex.getText().toString().trim().equals("");
        if (result) {
            exs.setError(getString(R.string.REQUIRED_CAMPO));
        }
        return !result;
    }

}