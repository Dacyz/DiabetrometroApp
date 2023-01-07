package com.example.diabetrometrov01.Interfaces.Mediciones;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.diabetrometrov01.DataAccessObject.IngestaAlimenticiaDAO;
import com.example.diabetrometrov01.DataAccessObject.PacienteDatosDAO;
import com.example.diabetrometrov01.DataTransferObject.IngestaDTO;
import com.example.diabetrometrov01.DataTransferObject.PacienteDatosDTO;
import com.example.diabetrometrov01.Interfaces.IngestaAlimenticia.DescriptionIngestaActivity;
import com.example.diabetrometrov01.Interfaces.IngestaAlimenticia.IngestaAlimenticiaAdapter;
import com.example.diabetrometrov01.Interfaces.Paciente.Login;
import com.example.diabetrometrov01.R;
import com.example.diabetrometrov01.databinding.FragmentIngestaAlimenticiaListadoBinding;
import com.example.diabetrometrov01.databinding.FragmentMedicionesListadoBinding;

import java.util.List;

public class MedicionesListado extends Fragment {

    private List<PacienteDatosDTO> dto;
    private FragmentMedicionesListadoBinding binding;
    private PacienteDatosDAO dao;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("XDNT",this,(requestKey, result) -> {
            Description();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMedicionesListadoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Description();
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Description(){
        dao = new PacienteDatosDAO();
        dto = dao.listarPorPaciente(Login.UsuarioAPP.getIdPaciente());
        MedicionesAdapter adapter = new MedicionesAdapter(dto);
        binding.ListMediciones.setAdapter(adapter);
    }
}