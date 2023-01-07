package com.example.diabetrometrov01.Interfaces.ActividadFisica;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diabetrometrov01.DataAccessObject.EjercicioDAO;
import com.example.diabetrometrov01.DataTransferObject.ActividadDTO;
import com.example.diabetrometrov01.DataTransferObject.EjercicioDTO;
import com.example.diabetrometrov01.R;

import java.util.List;

public class ActividadFisicaAdapter extends RecyclerView.Adapter<ActividadFisicaAdapter.ViewHolderDatos> implements View.OnClickListener{


    private List<ActividadDTO> mData;
    private View.OnClickListener listener;

    public ActividadFisicaAdapter(List<ActividadDTO> mData){
        this.mData = mData;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null) listener.onClick(v);
    }

    @NonNull
    @Override
    public ActividadFisicaAdapter.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fisica_row,null,false);
        view.setOnClickListener(this);
        return new ActividadFisicaAdapter.ViewHolderDatos(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ActividadFisicaAdapter.ViewHolderDatos holder, int position) {
        holder.asignarDatos(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        private final TextView RowtxtvDiaActividad, RowtxtvActividadNombre,  RowtxtvActividadRepeticiones;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            RowtxtvDiaActividad = (TextView) itemView.findViewById(R.id.RowtxtvDiaActividad);
            RowtxtvActividadNombre = (TextView) itemView.findViewById(R.id.RowtxtvActividadNombre);
            RowtxtvActividadRepeticiones = (TextView) itemView.findViewById(R.id.RowtxtvActividadRepeticiones);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void asignarDatos(ActividadDTO reportesDTO) {
            EjercicioDTO ejercio = new EjercicioDAO().buscar(new EjercicioDTO(reportesDTO.getIdActFisica()),0);
            RowtxtvDiaActividad.setText(reportesDTO.ApplyFormat(reportesDTO.getDia()));
            RowtxtvActividadNombre.setText(ejercio.getNombreEjercicio());
            if(ejercio.isTipoEjercicio()){
                RowtxtvActividadRepeticiones.setText(reportesDTO.ApplyFormat(reportesDTO.getRepeticiones()) + " repeticiones.");
            } else {
                RowtxtvActividadRepeticiones.setText(reportesDTO.ApplyFormatS(reportesDTO.getTiempo()));
            }
        }
    }
}
