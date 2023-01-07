package com.example.diabetrometrov01.Interfaces.Mediciones;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diabetrometrov01.DataTransferObject.PacienteDatosDTO;
import com.example.diabetrometrov01.R;

import org.w3c.dom.Text;

import java.util.List;


public class MedicionesAdapter  extends RecyclerView.Adapter<MedicionesAdapter.ViewHolderDatos> implements View.OnClickListener{

    private List<PacienteDatosDTO> mData;
    private View.OnClickListener listener;

    public MedicionesAdapter(List<PacienteDatosDTO> mData) {
        this.mData = mData;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null) listener.onClick(v);
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicion_row,null,false);
        view.setOnClickListener(this);
        return new MedicionesAdapter.ViewHolderDatos(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.asignarDatos(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        private final TextView RowtxtvTallaMedicion, RowtxtvPesoMedicion,  RowtxtvNivelGlucosaMedicion, RowtxtvDiaMedicion;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            RowtxtvDiaMedicion = (TextView) itemView.findViewById(R.id.RowtxtvDiaMedicion);
            RowtxtvTallaMedicion = (TextView) itemView.findViewById(R.id.RowtxtvTallaMedicion);
            RowtxtvPesoMedicion = (TextView) itemView.findViewById(R.id.RowtxtvPesoMedicion);
            RowtxtvNivelGlucosaMedicion = (TextView) itemView.findViewById(R.id.RowtxtvNivelGlucosaMedicion);
        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        public void asignarDatos(PacienteDatosDTO reporte) {
            RowtxtvDiaMedicion.setText(reporte.ApplyFormat(reporte.getDia()));
            RowtxtvTallaMedicion.setText(reporte.ApplyFormat(reporte.getTalla()));
            RowtxtvPesoMedicion.setText(reporte.ApplyFormat(reporte.getPeso()));
            RowtxtvNivelGlucosaMedicion.setText(reporte.ApplyFormat(reporte.getLvlglucosa()));
        }
    }
}
