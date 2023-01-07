package com.example.diabetrometrov01.Interfaces.Reportes;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.diabetrometrov01.DataAccessObject.IngestaAlimenticiaDAO;
import com.example.diabetrometrov01.DataAccessObject.ReportesDAO;
import com.example.diabetrometrov01.DataTransferObject.ReportesDTO;
import com.example.diabetrometrov01.DescriptionReporteActivity;
import com.example.diabetrometrov01.Interfaces.IngestaAlimenticia.DescriptionIngestaActivity;
import com.example.diabetrometrov01.Interfaces.IngestaAlimenticia.IngestaAlimenticiaAdapter;
import com.example.diabetrometrov01.Interfaces.Paciente.Login;
import com.example.diabetrometrov01.R;
import com.example.diabetrometrov01.databinding.FragmentGalleryBinding;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    List<ReportesDTO> reportesDTOS;
    private ReportesDAO reportes;
    private FragmentGalleryBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("PICHACOLADA",this,(requestKey, result) -> {
            Description();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Description();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void Description() {
        reportes = new ReportesDAO();
        reportesDTOS = reportes.listarPorPaciente(Login.UsuarioAPP.getIdPaciente());
        ReportAdapter adapterReportes = new ReportAdapter(reportesDTOS);
        binding.ListReportes.setAdapter(adapterReportes);
        adapterReportes.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), DescriptionReporteActivity.class);
            intent.putExtra("ReportesDTO", reportesDTOS.get(binding.ListReportes.getChildAdapterPosition(v)));
            startActivityForResult(intent, 0);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Description();
    }
}