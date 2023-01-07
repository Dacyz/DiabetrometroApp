package com.example.diabetrometrov01.Interfaces.Paciente;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.diabetrometrov01.BusinessObject.Paciente;
import com.example.diabetrometrov01.Interfaces.home.MenuSlideActivity;
import com.example.diabetrometrov01.R;
import com.example.diabetrometrov01.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.txtvSexoConfirm.setText(Login.UsuarioAPP.getSexoString());
        binding.txtvFechaNacimientoConfirm.setText(Login.UsuarioAPP.getFechaNacimientoString());
        binding.txtvApellidosConfirm.setText(Login.UsuarioAPP.getApellidos());
        binding.txtvNombresConfirm.setText(Login.UsuarioAPP.getNombre());
        binding.txtvCorreoConfirm.setText(Login.UsuarioAPP.getCorreo());
        binding.txtvDNIConfirm.setText(Login.UsuarioAPP.getDNI());
        binding.txtvContrasenaConfirm.setText("PepitoCamels");

        binding.BTNBackRegisterFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getActivity().onBackPressed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                //NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
        binding.BTNRegisterFinish.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                Paciente asd = new Paciente();
                if(binding.CBXTerminosConfirm.isChecked()){
                    if(asd.RegistrarPaciente(
                            binding.txtvCorreoConfirm.getText().toString(),
                            binding.txtvDNIConfirm.getText().toString(),
                            Login.UsuarioAPP.getContrasena(),
                            binding.txtvNombresConfirm.getText().toString(),
                            binding.txtvApellidosConfirm.getText().toString(),
                            binding.txtvSexoConfirm.getText().toString().equals("Masculino") ?
                                    'M': binding.txtvSexoConfirm.getText().toString().equals("Femenino")?
                                    'F':'O',
                            binding.txtvFechaNacimientoConfirm.getText().toString())){
                        Toast.makeText(getContext(), "Registrado Correctamente" , Toast.LENGTH_SHORT).show();
                        Intent intencion = new Intent(getContext(), MenuSlideActivity.class);
                        Login.UsuarioAPP = asd.buscar(binding.txtvCorreoConfirm.getText().toString(),2);
                        startActivity(intencion);
                    }else{
                        Toast.makeText(getContext(), "Registrado mal: " + asd.getError() , Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(getContext(), "Debes aceptar los terminos y condiciones" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}