package com.example.diabetrometrov01.Interfaces.IngestaAlimenticia;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.diabetrometrov01.Interfaces.VPApadter;
import com.example.diabetrometrov01.databinding.FragmentIngestaAlimenticiaBinding;

public class IngestaAlimenticia extends Fragment {

    private FragmentIngestaAlimenticiaBinding binding;
    private VPApadter vpApadter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIngestaAlimenticiaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.TabLayoutIngesta.setupWithViewPager(binding.ViewPagerAlimenticia);
        vpApadter = new VPApadter(getChildFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpApadter.addFragment(new IngestaAlimenticiaConsulta(),"Consultar");
        vpApadter.addFragment(new IngestaAlimenticiaRegistrar(),"Registrar");
        vpApadter.addFragment(new IngestaAlimenticiaListado(),"Listar");
        binding.ViewPagerAlimenticia.setAdapter(vpApadter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}