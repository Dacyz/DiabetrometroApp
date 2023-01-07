package com.example.diabetrometrov01.Interfaces.IngestaAlimenticia;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.diabetrometrov01.BusinessObject.Alimento;
import com.example.diabetrometrov01.DataAccessObject.IngestaAlimenticiaDAO;
import com.example.diabetrometrov01.DataTransferObject.AlimentoDTO;
import com.example.diabetrometrov01.DataTransferObject.CategoriaDTO;
import com.example.diabetrometrov01.databinding.FragmentIngestaAlimenticiaConsultaBinding;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;

public class IngestaAlimenticiaConsulta extends Fragment {

    private FragmentIngestaAlimenticiaConsultaBinding binding;

    Alimento alimento;
    AlimentoDTO alimentodto;
    CategoriaDTO categoriadto;
    IngestaAlimenticiaDAO ingestaDAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIngestaAlimenticiaConsultaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        alimento = new Alimento();
        ingestaDAO = new IngestaAlimenticiaDAO();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.select_dialog_item, alimento.listarCategorias());
        binding.txtAlimentoCategori.setAdapter(adapter);
        binding.txtAlimentoCategori.setOnItemClickListener((parent, view, position, id) -> {
            binding.txtAlimento.setText("");
            if (!binding.txtAlimentoCategori.getText().toString().equals("")) { AlimentCatalog(); }
        });

        binding.txtAlimento.setOnItemClickListener((parent, view, position, id) -> {
            Alimentvalues();
        });
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void AlimentCatalog(){
        categoriadto = alimento.buscarNombre(binding.txtAlimentoCategori.getText().toString());
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(),
                android.R.layout.select_dialog_item,
                alimento.listarxCategoria(categoriadto.getIdCategoria()));
        binding.txtvCategoriaIngestaConsulta.setText(categoriadto.getDescCat());
        binding.txtAlimento.setAdapter(adapter2);
    }

    private void Alimentvalues() {
        alimentodto = alimento.buscar(binding.txtAlimento.getText().toString(), 1);
        binding.txtvCalorias.setText(String.valueOf(alimentodto.getCalorias()));
        binding.txtvCarbohidratos.setText(String.valueOf(alimentodto.getCarbohidratos()));
        binding.txtvGrasas.setText(String.valueOf(alimentodto.getGrasas()));
        binding.txtvProteinas.setText(String.valueOf(alimentodto.getProteinas()));
        binding.txtvAlimentoDescriptionIngestaConsulta.setText(alimentodto.getDescripcion());

        List values = new ArrayList<>();
        values.add(new SliceValue(alimentodto.getProteinas()/100, Color.parseColor("#008EFF")).setLabel("Proteina"));
        values.add(new SliceValue(alimentodto.getCalorias(), Color.parseColor("#FF0000")).setLabel("Calorias"));
        values.add(new SliceValue(alimentodto.getGrasas()/100, Color.parseColor("#00FF0A")).setLabel("Grasas"));
        values.add(new SliceValue(alimentodto.getCarbohidratos(), Color.parseColor("#FFEB3B")).setLabel("Carbohidratos"));

        PieChartData data = new PieChartData(values);
        data.setHasLabels(true);
        binding.PieChartDetallesConsulta.setPieChartData(data);
    }
}