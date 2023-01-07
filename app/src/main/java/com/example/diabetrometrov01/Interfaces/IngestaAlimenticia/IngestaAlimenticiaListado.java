package com.example.diabetrometrov01.Interfaces.IngestaAlimenticia;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.diabetrometrov01.DataAccessObject.IngestaAlimenticiaDAO;
import com.example.diabetrometrov01.DataTransferObject.IngestaDTO;
import com.example.diabetrometrov01.Interfaces.Paciente.Login;
import com.example.diabetrometrov01.databinding.FragmentIngestaAlimenticiaListadoBinding;

import java.util.List;

public class IngestaAlimenticiaListado extends Fragment {

    private List<IngestaDTO> ingestaDTOS;
    private FragmentIngestaAlimenticiaListadoBinding binding;
    private IngestaAlimenticiaDAO ingestaAlimenticiaDAO;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("XDEA",this,(requestKey, result) -> {
            Description();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIngestaAlimenticiaListadoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Description();
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Description(){
        ingestaAlimenticiaDAO = new IngestaAlimenticiaDAO();
        ingestaDTOS = ingestaAlimenticiaDAO.listarxPaciente(Login.UsuarioAPP.getIdPaciente());
        IngestaAlimenticiaAdapter adapterReportes = new IngestaAlimenticiaAdapter(ingestaDTOS);
        binding.ListIngesta.setAdapter(adapterReportes);
        adapterReportes.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), DescriptionIngestaActivity.class);
            intent.putExtra("IngestaDTO",ingestaDTOS.get(binding.ListIngesta.getChildAdapterPosition(v)));
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