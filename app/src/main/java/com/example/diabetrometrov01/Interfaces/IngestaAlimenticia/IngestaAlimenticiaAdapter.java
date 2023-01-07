package com.example.diabetrometrov01.Interfaces.IngestaAlimenticia;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diabetrometrov01.BusinessObject.Alimento;
import com.example.diabetrometrov01.DataTransferObject.IngestaDTO;
import com.example.diabetrometrov01.R;

import java.text.MessageFormat;
import java.util.List;

public class IngestaAlimenticiaAdapter extends RecyclerView.Adapter<IngestaAlimenticiaAdapter.ViewHolderDatos> implements View.OnClickListener {

    private List<IngestaDTO> mData;
    private View.OnClickListener listener;

    public IngestaAlimenticiaAdapter(List<IngestaDTO> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public IngestaAlimenticiaAdapter.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alimenticia_row,null,false);
        view.setOnClickListener(this);
        return new ViewHolderDatos(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull IngestaAlimenticiaAdapter.ViewHolderDatos holder, int position) {
        holder.asignarDatos(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null) listener.onClick(v);
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        private final TextView RowtxtvDateGenerateAl, RowtxtvAlimento,  RowtxtvPorcion;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            RowtxtvDateGenerateAl = (TextView) itemView.findViewById(R.id.RowtxtvDateGenerateAl);
            RowtxtvAlimento = (TextView) itemView.findViewById(R.id.RowtxtvAlimento);
            RowtxtvPorcion = (TextView) itemView.findViewById(R.id.RowtxtvPorcion);
        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        public void asignarDatos(IngestaDTO reportesDTO) {
            RowtxtvDateGenerateAl.setText(reportesDTO.ApplyFormat(reportesDTO.getDia()));
            RowtxtvAlimento.setText(new Alimento().buscar(String.valueOf(reportesDTO.getIdAlimento()),0).getNombre());
            RowtxtvPorcion.setText(reportesDTO.ApplyFormatS(reportesDTO.getHoraConsumo()));
        }
    }

}
