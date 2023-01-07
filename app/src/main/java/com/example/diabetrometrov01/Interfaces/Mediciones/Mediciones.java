package com.example.diabetrometrov01.Interfaces.Mediciones;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.diabetrometrov01.Interfaces.VPApadter;
import com.example.diabetrometrov01.databinding.FragmentMedicionesBinding;

public class Mediciones extends Fragment {

    private FragmentMedicionesBinding binding;
    private VPApadter vpApadter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMedicionesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.TabLayoutMediciones.setupWithViewPager(binding.ViewPagerMedicion);
        vpApadter = new VPApadter(getChildFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpApadter.addFragment(new MedicionesRegistrar(),"Registrar");
        vpApadter.addFragment(new MedicionesListado(),"Listar");
        binding.ViewPagerMedicion.setAdapter(vpApadter);
        return  root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}