package com.example.diabetrometrov01.Interfaces.Mediciones;

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
import android.widget.EditText;
import android.widget.Toast;

import com.example.diabetrometrov01.DataAccessObject.PacienteDatosDAO;
import com.example.diabetrometrov01.DataTransferObject.PacienteDatosDTO;
import com.example.diabetrometrov01.Interfaces.Paciente.Login;
import com.example.diabetrometrov01.R;
import com.example.diabetrometrov01.databinding.FragmentMedicionesRegistrarBinding;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;

public class MedicionesRegistrar extends Fragment {

    private FragmentMedicionesRegistrarBinding binding;
    private PacienteDatosDAO datospaciente;
    private PacienteDatosDTO dto;

    public MedicionesRegistrar() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        datospaciente = new PacienteDatosDAO();
        dto = new PacienteDatosDTO();
        binding.FechaPacienteDatos.setText(dto.ApplyFormat(LocalDate.now()));
        dto = datospaciente.buscarLast(Login.UsuarioAPP.getIdPaciente());
        if(dto!= null){
            binding.txtTallaDatosPacientes.setText(dto.ApplyFormat(dto.getTalla()));
            binding.txtPesoDatosPacientes.setText(dto.ApplyFormat(dto.getPeso()));
            binding.txtNivelGlucosaDatosPacientes.setText(dto.ApplyFormat(dto.getLvlglucosa()));
        }

        binding.BTNAgregarDatosPacientes.setOnClickListener(v -> {
            if(validar()){
                dto = datospaciente.buscaFecha(LocalDate.now(),Login.UsuarioAPP.getIdPaciente());
                if(dto != null){
                    dto.setPeso(Float.parseFloat(binding.txtPesoDatosPacientes.getText().toString()));
                    dto.setTalla(Float.parseFloat(binding.txtTallaDatosPacientes.getText().toString()));
                    dto.setLvlglucosa(Float.parseFloat(binding.txtNivelGlucosaDatosPacientes.getText().toString()));
                    if(datospaciente.actualizar(dto)){
                        Toast.makeText(getContext(), "Se actualizo correctamente" , Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(getContext(), "Error al actualizar: " + datospaciente.getErrorInDB() , Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dto = new PacienteDatosDTO();
                    dto.setPeso(Float.parseFloat(binding.txtPesoDatosPacientes.getText().toString()));
                    dto.setTalla(Float.parseFloat(binding.txtTallaDatosPacientes.getText().toString()));
                    dto.setLvlglucosa(Float.parseFloat(binding.txtNivelGlucosaDatosPacientes.getText().toString()));
                    dto.setDia(LocalDate.now());
                    dto.setIdPaciente(Login.UsuarioAPP.getIdPaciente());
                    if(datospaciente.agregar(dto)){
                        Toast.makeText(getContext(), "Se agrego correctamente" , Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), "Error al agregar: " + datospaciente.getErrorInDB() , Toast.LENGTH_SHORT).show();
                    }
                }
                if(dto.getDia().compareTo(LocalDate.now())== 0){

                }
                getParentFragmentManager().setFragmentResult("XDNT",new Bundle());
            } else{
                Toast.makeText(getContext(), "Comprueba que los campos esten llenados correctamente" , Toast.LENGTH_SHORT).show();
            }

        });
        binding.BTNCancelDatosPacientes.setOnClickListener(v->{
            LimpiarCampos();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void LimpiarCampos(){
        PacienteDatosDTO dtoaux = datospaciente.buscarLast(Login.UsuarioAPP.getIdPaciente());
        if(dtoaux != null){
            binding.txtTallaDatosPacientes.setText(dtoaux.ApplyFormat(dtoaux.getTalla()));
            binding.txtPesoDatosPacientes.setText(dtoaux.ApplyFormat(dtoaux.getPeso()));
            binding.txtNivelGlucosaDatosPacientes.setText(dtoaux.ApplyFormat(dtoaux.getLvlglucosa()));
        } else{
            binding.txtTallaDatosPacientes.setText("");
            binding.txtPesoDatosPacientes.setText("");
            binding.txtNivelGlucosaDatosPacientes.setText("");
        }
    }

    private boolean validar(){
        boolean[] result =
                {
                        isCampoCompleted(binding.TILNivelGlucosaDatosPacientes, binding.txtNivelGlucosaDatosPacientes),
                        isCampoCompleted(binding.TILPesoDatosPacientes, binding.txtPesoDatosPacientes),
                        isCampoCompleted(binding.TILTallaDatosPacientes, binding.txtTallaDatosPacientes),
                        isNumberValid(binding.txtNivelGlucosaDatosPacientes.getText().toString(), 300, 50),
                        isNumberValid(binding.txtPesoDatosPacientes.getText().toString(),350 ,10 ),
                        isNumberValid(binding.txtTallaDatosPacientes.getText().toString(), (float) 2.70, (float) 0.3),
                };
        // -- Mensajito :v 400> <60
        return result[0] && result[1] && result[2] && result[3] && result[4] && result[5];

    }

    private boolean isNumberValid(String Number, float max, float min){
        try{
            float number = Float.parseFloat(Number.trim());
            if(number>max || number<min){
                return false;
            }
            return true;
        }catch (Exception es){
            return false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = FragmentMedicionesRegistrarBinding.inflate(inflater, container, false);
        Valitions(binding.TILNivelGlucosaDatosPacientes, binding.txtNivelGlucosaDatosPacientes, 300,50);
        Valitions(binding.TILPesoDatosPacientes, binding.txtPesoDatosPacientes,350,10);
        Valitions(binding.TILTallaDatosPacientes, binding.txtTallaDatosPacientes,(float) 2.70, (float) 0.3);
        return binding.getRoot();
    }

    private boolean isCampoCompleted(TextInputLayout exs, EditText ex){
        boolean result = ex.getText().toString().trim().equals("");
        if(result){
            exs.setError(getString(R.string.REQUIRED_CAMPO));
        }
        return !result ;
    }

    private void Valitions(TextInputLayout exs, EditText ex, float max, float min){
        ex.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if(!s.toString().trim().equals("")){
                        exs.setError(null);
                        float number = Float.parseFloat(s.toString().trim());
                        if(number>max || number<min){
                            exs.setError("Numero no se encuentra en el intervalo de medidas");
                        }
                    } else {
                        exs.setError(getString(R.string.REQUIRED_CAMPO));
                    }
                }catch (Exception xd){
                    exs.setError("Numero no se encuentra en el intervalo de medidas");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}