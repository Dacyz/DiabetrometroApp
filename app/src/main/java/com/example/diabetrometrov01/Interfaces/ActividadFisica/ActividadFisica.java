package com.example.diabetrometrov01.Interfaces.ActividadFisica;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.diabetrometrov01.databinding.FragmentActividadFisicaBinding;
import com.example.diabetrometrov01.Interfaces.VPApadter;

public class ActividadFisica extends Fragment {

    private FragmentActividadFisicaBinding binding;
    private VPApadter vpApadter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentActividadFisicaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.TabLayoutActividad.setupWithViewPager(binding.ViewPagerEjercicio);
        vpApadter = new VPApadter(getChildFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT );
        vpApadter.addFragment(new ActividadFisicaRegistrar(),"Registrar");
        vpApadter.addFragment(new ActividadFisicaListado(),"Listar");
        binding.ViewPagerEjercicio.setAdapter(vpApadter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}