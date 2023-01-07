package com.example.diabetrometrov01;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.diabetrometrov01.Interfaces.Mediciones.MedicionesListado;
import com.example.diabetrometrov01.Interfaces.Mediciones.MedicionesRegistrar;
import com.example.diabetrometrov01.Interfaces.Reportes.GalleryFragment;
import com.example.diabetrometrov01.Interfaces.VPApadter;
import com.example.diabetrometrov01.databinding.FragmentMedicionesBinding;
import com.example.diabetrometrov01.databinding.FragmentReporteBinding;

public class reporte extends Fragment {

    private FragmentReporteBinding binding;
    private VPApadter vpApadter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentReporteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.TabLayoutReportes.setupWithViewPager(binding.ViewPagerReportes);
        vpApadter = new VPApadter(getChildFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpApadter.addFragment(new ReporteGenerar(),"Registrar");
        vpApadter.addFragment(new GalleryFragment(),"Listar");
        binding.ViewPagerReportes.setAdapter(vpApadter);
        return  root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}