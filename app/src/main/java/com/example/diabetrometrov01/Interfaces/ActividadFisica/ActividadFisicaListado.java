package com.example.diabetrometrov01.Interfaces.ActividadFisica;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.diabetrometrov01.DataAccessObject.ActividadDAO;
import com.example.diabetrometrov01.DataAccessObject.IngestaAlimenticiaDAO;
import com.example.diabetrometrov01.DataTransferObject.ActividadDTO;
import com.example.diabetrometrov01.Interfaces.IngestaAlimenticia.DescriptionIngestaActivity;
import com.example.diabetrometrov01.Interfaces.IngestaAlimenticia.IngestaAlimenticiaAdapter;
import com.example.diabetrometrov01.Interfaces.Paciente.Login;
import com.example.diabetrometrov01.databinding.FragmentActividadFisicaListadoBinding;

import java.util.List;

public class ActividadFisicaListado extends Fragment {

    private List<ActividadDTO> actividadDTOS;
    private ActividadDAO actividadDAO;
    private FragmentActividadFisicaListadoBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("XDE",this,(requestKey, result) -> {
            Description();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentActividadFisicaListadoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Description();
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Description(){
        actividadDAO = new ActividadDAO();
        actividadDTOS = actividadDAO.listarPorPaciente(Login.UsuarioAPP.getIdPaciente());
        ActividadFisicaAdapter adapterReportes = new ActividadFisicaAdapter(actividadDTOS);
        binding.ListActividad.setAdapter(adapterReportes);
        adapterReportes.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), DescriptionActividadFisica.class);
            intent.putExtra("ActividadDTO",actividadDTOS.get(binding.ListActividad.getChildAdapterPosition(v)));
            startActivityForResult(intent,0);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Description();
    }
}