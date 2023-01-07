package com.example.diabetrometrov01.Interfaces.ActividadFisica;

import android.app.TimePickerDialog;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diabetrometrov01.BusinessObject.Actividad;
import com.example.diabetrometrov01.BusinessObject.Ejercicio;
import com.example.diabetrometrov01.DataAccessObject.ActividadDAO;
import com.example.diabetrometrov01.DataTransferObject.ActividadDTO;
import com.example.diabetrometrov01.DataTransferObject.EjercicioDTO;
import com.example.diabetrometrov01.Interfaces.Paciente.Login;
import com.example.diabetrometrov01.R;
import com.example.diabetrometrov01.databinding.FragmentActividadFisicaRegistrarBinding;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;


public class ActividadFisicaRegistrar extends Fragment {

    private FragmentActividadFisicaRegistrarBinding binding;
    private Ejercicio ejerci;
    private EjercicioDTO ejerciciodto;
    private ActividadDAO actividadDAO;
    private ActividadDTO actividadDTO;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentActividadFisicaRegistrarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ejerci = new Ejercicio();
        actividadDAO = new ActividadDAO();

        Valitions(binding.TILEjercicioActividad,binding.txtEjercicioActividad);
        Valitions(binding.TILRepeticionesEjercicioRegister, binding.txtRepeticionesEjercicioRegister);
        Valitions(binding.TILTimeEjercicioRegister,binding.txtTimeEjercicioRegister);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.select_dialog_item, ejerci.listarNombres());
        binding.txtEjercicioActividad.setAdapter(adapter);
        binding.txtEjercicioActividad.setOnItemClickListener((parent, view, position, id) -> {
            Values();
        });

        binding.BTNCancelActividadFisica.setOnClickListener(v -> {
            Clean();
        });



        AtomicInteger t1hour = new AtomicInteger();
        AtomicInteger t1minute = new AtomicInteger();
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                (view, hour, minute) -> {
                    t1hour.set(hour);
                    t1minute.set(minute);
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(0,0,0, t1hour.get(), t1minute.get());
                    DecimalFormat DF = new DecimalFormat("00");
                    binding.txtTimeEjercicioRegister.setText(DF.format(t1hour.get())+":"+DF.format(t1minute.get()));
                },
                12,
                0,
                true
        );
        binding.txtTimeEjercicioRegister.setOnClickListener((v) -> {
            if(!timePickerDialog.isShowing()){
                timePickerDialog.updateTime(t1hour.get(), t1minute.get());
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });
        binding.txtTimeEjercicioRegister.setOnFocusChangeListener((v, hasFocus) -> {
            if(binding.txtTimeEjercicioRegister.isFocused()&&!timePickerDialog.isShowing()){
                timePickerDialog.updateTime(t1hour.get(), t1minute.get());
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });
        binding.BTNAgregarActividadFisica.setOnClickListener(v -> {
            if(validations()){
                actividadDTO = new ActividadDTO();
                actividadDTO.setIdActFisica(ejerciciodto.getIdEjercicio());
                actividadDTO.setIdPaciente(Login.UsuarioAPP.getIdPaciente());
                String s;
                if(!ejerciciodto.isTipoEjercicio()){
                    s = binding.txtTimeEjercicioRegister.getText().toString();
                    actividadDTO.setRepeticiones(0);
                } else{
                    s = "00:00";
                    actividadDTO.setRepeticiones(Integer.parseInt(binding.txtRepeticionesEjercicioRegister.getText().toString()));
                }
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                long ms = 0;
                try {
                    ms = sdf.parse(s).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                actividadDTO.setTiempo(new Time(ms));
                actividadDTO.setDia(LocalDateTime.now());
                if(actividadDAO.agregar(actividadDTO)){
                    Toast.makeText(getContext(), "Se agrego correctamente" , Toast.LENGTH_SHORT).show();
                    getParentFragmentManager().setFragmentResult("XDE",new Bundle());
                    Clean();
                }else{
                    Toast.makeText(getContext(), "Error: " + actividadDAO.getErrorInDB(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }

    private boolean validations() {
        boolean[] result =
                {
                        isCampoCompleted(binding.TILEjercicioActividad, binding.txtEjercicioActividad),
                        binding.TILRepeticionesEjercicioRegister.getVisibility() == View.VISIBLE ?
                                isCampoCompleted(binding.TILRepeticionesEjercicioRegister, binding.txtRepeticionesEjercicioRegister):
                                isCampoCompleted(binding.TILTimeEjercicioRegister, binding.txtTimeEjercicioRegister)
                };
        return result[0] && result[1] ;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void Clean() {
        binding.txtTimeEjercicioRegister.setText("");
        binding.txtEjercicioActividad.setText("");
        binding.txtRepeticionesEjercicioRegister.setText("");
        binding.TILRepeticionesEjercicioRegister.setError(null);
        binding.TILTimeEjercicioRegister.setError(null);
        binding.TILEjercicioActividad.setError(null);
        binding.DescriptionExercise.setVisibility(View.INVISIBLE);
        binding.TILRepeticionesEjercicioRegister.setVisibility(View.GONE);
        binding.TILTimeEjercicioRegister.setVisibility(View.GONE);
        binding.txtvCaloriasConsume.setVisibility(View.INVISIBLE);
        binding.txtvTiempoRecomendado.setVisibility(View.INVISIBLE);
        binding.txtvCaloriasConsumeDescp.setVisibility(View.INVISIBLE);
        binding.txtvTiempoRecomendadoDescp.setVisibility(View.INVISIBLE);
        binding.txtvDescription.setVisibility(View.INVISIBLE);
    }

    private void Values(){
        ejerciciodto = ejerci.buscar(binding.txtEjercicioActividad.getText().toString(),1);
        binding.DescriptionExercise.setText(ejerciciodto.getDescEjercicio());
        binding.txtvTiempoRecomendado.setText(ejerciciodto.getTiempo().toString());
        binding.txtvCaloriasConsume.setText(String.valueOf(ejerciciodto.getCaloriasQuemadas()));
        binding.txtvCaloriasConsume.setVisibility(View.VISIBLE);
        binding.DescriptionExercise.setVisibility(View.VISIBLE);
        binding.txtvTiempoRecomendado.setVisibility(View.VISIBLE);
        binding.txtvCaloriasConsumeDescp.setVisibility(View.VISIBLE);
        binding.txtvTiempoRecomendadoDescp.setVisibility(View.VISIBLE);
        binding.txtvDescription.setVisibility(View.VISIBLE);
        if(ejerciciodto.isTipoEjercicio()){
            binding.TILRepeticionesEjercicioRegister.setVisibility(View.VISIBLE);
            binding.TILTimeEjercicioRegister.setVisibility(View.GONE);
        } else{
            binding.TILRepeticionesEjercicioRegister.setVisibility(View.GONE);
            binding.TILTimeEjercicioRegister.setVisibility(View.VISIBLE);
        }
    }

    private boolean isCampoCompleted(TextInputLayout exs, EditText ex){
        boolean result = ex.getText().toString().trim().equals("");
        if(result){
            exs.setError(getString(R.string.REQUIRED_CAMPO));
        }
        return !result ;
    }

    private void Valitions(TextInputLayout exs, EditText ex){
        ex.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().equals("")){
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
}