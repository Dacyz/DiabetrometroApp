package com.example.diabetrometrov01.Interfaces.IngestaAlimenticia;

import static android.os.Build.*;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.diabetrometrov01.BusinessObject.Alimento;
import com.example.diabetrometrov01.DataAccessObject.IngestaAlimenticiaDAO;
import com.example.diabetrometrov01.DataTransferObject.AlimentoDTO;
import com.example.diabetrometrov01.DataTransferObject.CategoriaDTO;
import com.example.diabetrometrov01.DataTransferObject.IngestaDTO;
import com.example.diabetrometrov01.R;
import com.example.diabetrometrov01.databinding.FragmentIngestaAlimenticiaRegistrarBinding;
import com.example.diabetrometrov01.Interfaces.Paciente.Login;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;


public class IngestaAlimenticiaRegistrar extends Fragment {

    private FragmentIngestaAlimenticiaRegistrarBinding binding;

    Alimento alimento;
    AlimentoDTO alimentodto;
    CategoriaDTO categoriadto;
    IngestaAlimenticiaDAO ingestaDAO;

    @RequiresApi(api = VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentIngestaAlimenticiaRegistrarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Valitions(binding.TILPorcionAlimenticia,binding.txtPorcionAlimenticia);
        Valitions(binding.TILHoraAlimenticia,binding.txtHoraAlimenticia);
        binding.txtPorcionAlimenticia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(isNumber(s.toString())){
                    float number = Float.parseFloat(s.toString());
                    if(number > 0 && number <4000){
                        binding.TILPorcionAlimenticia.setError(null);
                    }else {
                        binding.TILPorcionAlimenticia.setError("Numero no valido");
                    }
                } else{
                    binding.TILPorcionAlimenticia.setError("Numero no valido");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        //Valitions(binding.TILAlimento,binding.txtAlimento);

        alimento = new Alimento();
        ingestaDAO = new IngestaAlimenticiaDAO();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.select_dialog_item, alimento.listarCategorias());
        binding.txtAlimentoCategori.setAdapter(adapter);
        binding.txtAlimentoCategori.setOnItemClickListener((parent, view, position, id) -> {
            binding.txtAlimento.setText("");
            if (!binding.txtAlimentoCategori.getText().toString().equals("")) { AlimentCatalog(); }
            binding.TILAlimentoCategori.setError(null);
        });

        binding.txtAlimento.setOnItemClickListener((parent, view, position, id) -> {
            Alimentvalues();
            binding.TILAlimento.setError(null);
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
                    binding.txtHoraAlimenticia.setText(DF.format(t1hour.get())+":"+DF.format(t1minute.get()));
                },
                12,
                0,
                true
        );
        binding.txtHoraAlimenticia.setOnClickListener((v) -> {
            if(!timePickerDialog.isShowing()){
                timePickerDialog.updateTime(t1hour.get(), t1minute.get());
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });
        binding.txtHoraAlimenticia.setOnFocusChangeListener((v, hasFocus) -> {
            if(binding.txtHoraAlimenticia.isFocused()&&!timePickerDialog.isShowing()){
                timePickerDialog.updateTime(t1hour.get(), t1minute.get());
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });
        binding.BTNCancelAlimenticio.setOnClickListener((v -> {
            LimpiarCampos();
        }));
        binding.BTNAgregarAlimenticio.setOnClickListener((v -> {
                if(validate()){
                    String s = binding.txtHoraAlimenticia.getText().toString();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    long ms = 0;
                    try {
                        ms = sdf.parse(s).getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    IngestaDTO asd = new IngestaDTO(
                            Login.UsuarioAPP.getIdPaciente(),
                            alimentodto.getIdAlimento(),
                            Float.parseFloat(binding.txtPorcionAlimenticia.getText().toString()),
                            new Time(ms));
                    if(ingestaDAO.agregar(asd)){
                        Toast.makeText(getContext(), "Se agrego correctamente" , Toast.LENGTH_SHORT).show();
                        getParentFragmentManager().setFragmentResult("XDEA",new Bundle());
                        LimpiarCampos();
                    } else{
                        Toast.makeText(getContext(), "Error: " + ingestaDAO.getErrorInDB(), Toast.LENGTH_SHORT).show();
                    }
                }


        }));
        return root;
    }

    private void LimpiarCampos(){
        binding.txtAlimento.setText("");
        binding.txtAlimentoCategori.setText("");
        binding.txtHoraAlimenticia.setText("");
        binding.txtPorcionAlimenticia.setText("");
        binding.txtAlimento.setAdapter(null);
        binding.TILPorcionAlimenticia.setError(null);
        binding.TILAlimento.setError(null);
        binding.TILAlimentoCategori.setError(null);
        binding.TILHoraAlimenticia.setError(null);
    }

    private boolean validate(){
        boolean[] result =
                {
                        isCampoCompleted(binding.TILPorcionAlimenticia, binding.txtPorcionAlimenticia),
                        isCampoCompleted(binding.TILHoraAlimenticia, binding.txtHoraAlimenticia),
                        isCampoCompleted(binding.TILAlimento, binding.txtAlimento),
                        isCampoCompleted(binding.TILAlimentoCategori, binding.txtAlimentoCategori)
                };
        return result[0] && result[1] && result[2] && result[3];
    }

    private boolean isNumber(String Number){
        try{
            Float.parseFloat(Number);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    @RequiresApi(api = VERSION_CODES.N)
    private void AlimentCatalog(){
        categoriadto = alimento.buscarNombre(binding.txtAlimentoCategori.getText().toString());
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(),
                android.R.layout.select_dialog_item, alimento.listarxCategoria(categoriadto.getIdCategoria()));
        binding.txtAlimento.setAdapter(adapter2);
    }


    private void Alimentvalues() {
        alimentodto = alimento.buscar(binding.txtAlimento.getText().toString(), 1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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