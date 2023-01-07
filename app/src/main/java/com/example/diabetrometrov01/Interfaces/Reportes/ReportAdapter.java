package com.example.diabetrometrov01.Interfaces.Reportes;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diabetrometrov01.DataTransferObject.ReportesDTO;
import com.example.diabetrometrov01.R;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolderDatos> implements View.OnClickListener {

    private List<ReportesDTO> mData;
    private View.OnClickListener listener;

    public ReportAdapter(List<ReportesDTO> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public ReportAdapter.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_row, null, false);
        view.setOnClickListener(this);
        return new ViewHolderDatos(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ViewHolderDatos holder, int position) {
        holder.asignarDatos(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) listener.onClick(v);
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        private TextView RowtxtvDateGenerate, RowtxtvDateInicio, RowtxtvDateFinal, TitleRow;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            RowtxtvDateGenerate = (TextView) itemView.findViewById(R.id.RowtxtvDateGenerate);
            RowtxtvDateInicio = (TextView) itemView.findViewById(R.id.RowtxtvDateInicio);
            RowtxtvDateFinal = (TextView) itemView.findViewById(R.id.RowtxtvDateFinal);
            TitleRow = (TextView) itemView.findViewById(R.id.TitleRow);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void asignarDatos(ReportesDTO reportesDTO) {
            TitleRow.setText(reportesDTO.getNombre());
            RowtxtvDateGenerate.setText(reportesDTO.ApplyFormat(reportesDTO.getDia()));
            RowtxtvDateInicio.setText(reportesDTO.ApplyFormat(reportesDTO.getFechaInicio()));
            RowtxtvDateFinal.setText(reportesDTO.ApplyFormat(reportesDTO.getFechaFinal()));
        }
    }
}
