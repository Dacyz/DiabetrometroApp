package com.example.diabetrometrov01.Interfaces.ActividadFisica;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diabetrometrov01.DataAccessObject.ActividadDAO;
import com.example.diabetrometrov01.DataAccessObject.EjercicioDAO;
import com.example.diabetrometrov01.DataTransferObject.ActividadDTO;
import com.example.diabetrometrov01.DataTransferObject.AlimentoDTO;
import com.example.diabetrometrov01.DataTransferObject.EjercicioDTO;
import com.example.diabetrometrov01.DataTransferObject.IngestaDTO;
import com.example.diabetrometrov01.Interfaces.IngestaAlimenticia.DescriptionIngestaActivity;
import com.example.diabetrometrov01.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

public class DescriptionActividadFisica extends AppCompatActivity {

    ActividadDAO actividadDAO = new ActividadDAO();

    TextInputLayout TILRepeticionesEditActividad;
    TextInputLayout TILTimeEjercicioEditActividad;

    EditText txtEjercicioEditActividad;
    EditText txtRepeticionesEditActividad;
    EditText txtTimeEditActividad;

    TextView txtvFechaRegistroEditActividad;

    Button BTNBackEditActividadFisica;
    Button BTNModifyEditActividadFisica;
    Button BTNDeleteEditActividadFisica;
    Button BTNSaveEditActividadFisica;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_actividad_fisica);

        txtvFechaRegistroEditActividad = findViewById(R.id.txtvFechaRegistroEditActividad);

        TILRepeticionesEditActividad = findViewById(R.id.TILRepeticionesEditActividad);
        TILTimeEjercicioEditActividad =  findViewById(R.id.TILTimeEjercicioEditActividad);
        txtEjercicioEditActividad =  findViewById(R.id.txtEjercicioEditActividad);
        txtRepeticionesEditActividad =  findViewById(R.id.txtRepeticionesEditActividad);
        txtTimeEditActividad =  findViewById(R.id.txtTimeEditActividad);
        BTNBackEditActividadFisica = findViewById(R.id.BTNBackEditActividadFisica);
        BTNModifyEditActividadFisica =  findViewById(R.id.BTNModifyEditActividadFisica);
        BTNDeleteEditActividadFisica =  findViewById(R.id.BTNDeleteEditActividadFisica);
        BTNSaveEditActividadFisica =  findViewById(R.id.BTNSaveEditActividadFisica);

        BTNBackEditActividadFisica.setOnClickListener(v -> {
            finish();
        });

        AtomicInteger t1hour = new AtomicInteger();
        AtomicInteger t1minute = new AtomicInteger();
        TimePickerDialog timePickerDialog = new TimePickerDialog(DescriptionActividadFisica.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                (view, hour, minute) -> {
                    t1hour.set(hour);
                    t1minute.set(minute);
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(0,0,0, t1hour.get(), t1minute.get());
                    DecimalFormat DF = new DecimalFormat("00");
                    txtTimeEditActividad.setText(DF.format(t1hour.get())+":"+DF.format(t1minute.get()));
                },
                12,
                0,
                true
        );

        txtTimeEditActividad.setOnClickListener((v) -> {
            if(!timePickerDialog.isShowing()){
                timePickerDialog.updateTime(t1hour.get(), t1minute.get());
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });
        txtTimeEditActividad.setOnFocusChangeListener((v, hasFocus) -> {
            if(txtTimeEditActividad.isFocused()&& !timePickerDialog.isShowing()){
                timePickerDialog.updateTime(t1hour.get(), t1minute.get());
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });

        try {
            ActividadDTO ingestaDTO = (ActividadDTO) getIntent().getSerializableExtra("ActividadDTO");

            EjercicioDTO ejercio = new EjercicioDAO().buscar(new EjercicioDTO(ingestaDTO.getIdActFisica()),0);

            txtEjercicioEditActividad.setText(ejercio.getNombreEjercicio());
            txtvFechaRegistroEditActividad.setText(ingestaDTO.ApplyFormat(ingestaDTO.getDia()));
            if(ejercio.isTipoEjercicio()){
                TILRepeticionesEditActividad.setVisibility(View.VISIBLE);
                txtRepeticionesEditActividad.setText(ingestaDTO.ApplyFormat(ingestaDTO.getRepeticiones()));
            } else{
                TILTimeEjercicioEditActividad.setVisibility(View.VISIBLE);
                txtTimeEditActividad.setText(ingestaDTO.ApplyFormatS(ingestaDTO.getTiempo()));
            }

            if(Period.between(LocalDate.from(ingestaDTO.getDia()), LocalDate.now()).getDays() >= 1){
                BTNModifyEditActividadFisica.setVisibility(View.INVISIBLE);
            }

            BTNModifyEditActividadFisica.setOnClickListener(v -> {
                if(!BTNModifyEditActividadFisica.getText().toString().equals("Cancelar")){
                    BTNDeleteEditActividadFisica.setVisibility(View.VISIBLE);

                        BTNSaveEditActividadFisica.setVisibility(View.VISIBLE);
                        txtRepeticionesEditActividad.setEnabled(true);
                        txtTimeEditActividad.setEnabled(true);
                    BTNModifyEditActividadFisica.setText("Cancelar");
                    BTNModifyEditActividadFisica.setTextColor(Color.parseColor("#FF0000"));
                } else{
                    txtTimeEditActividad.setText(ingestaDTO.ApplyFormatS(ingestaDTO.getTiempo()));
                    txtRepeticionesEditActividad.setText(ingestaDTO.ApplyFormat(ingestaDTO.getRepeticiones()));
                    BTNSaveEditActividadFisica.setVisibility(View.INVISIBLE);
                    BTNDeleteEditActividadFisica.setVisibility(View.INVISIBLE);
                    txtRepeticionesEditActividad.setEnabled(false);
                    txtTimeEditActividad.setEnabled(false);
                    BTNModifyEditActividadFisica.setText("Modificar");
                    BTNModifyEditActividadFisica.setTextColor(Color.parseColor("#00E1FF"));
                }
            });

            BTNDeleteEditActividadFisica.setOnClickListener(v -> {
                MaterialAlertDialogBuilder Alert = new MaterialAlertDialogBuilder(DescriptionActividadFisica.this);
                Alert.setMessage("¿Estas seguro de eliminar esta Actividad de tu lista?");
                Alert.setPositiveButton("Ok.",(dialog, which) -> {
                    if(actividadDAO.eliminar(ingestaDTO)){
                        Toast.makeText(getApplicationContext(),"Eliminado correctamente", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"Error al eliminar" + actividadDAO.getErrorInDB() , Toast.LENGTH_SHORT).show();
                    }
                });
                Alert.setNegativeButton(R.string.cancel, (dialog, which) -> { });
                Alert.show();
            });

            BTNSaveEditActividadFisica.setOnClickListener(v -> {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                long ms = 0;
                try {
                    ms = sdf.parse(txtTimeEditActividad.getText().toString()).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(validations()){
                    if(ejercio.isTipoEjercicio()){
                        ingestaDTO.setRepeticiones(Integer.parseInt(txtRepeticionesEditActividad.getText().toString()));
                    } else{
                        ingestaDTO.setTiempo(new Time(ms));
                    }
                    if(actividadDAO.actualizar(ingestaDTO)){
                        Toast.makeText(getApplicationContext(),"Actualizado correctamente", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"Error al actualizar" + actividadDAO.getErrorInDB() , Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"wtf correctamente", Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void finish() {
        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        super.finish();
    }

    private boolean isNumber(String Number){
        try{
            Float.parseFloat(Number);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    private boolean validations() {
        boolean[] result =
                {
                        TILRepeticionesEditActividad.getVisibility() == View.VISIBLE ?
                                isCampoCompleted(TILRepeticionesEditActividad, txtRepeticionesEditActividad):
                                isCampoCompleted(TILTimeEjercicioEditActividad, txtTimeEditActividad)
                };
        return result[0]  ;
    }

    private boolean isCampoCompleted(TextInputLayout exs, EditText ex){
        boolean result = ex.getText().toString().trim().equals("");
        if(result){
            exs.setError(getString(R.string.REQUIRED_CAMPO));
        }
        return !result ;
    }
}