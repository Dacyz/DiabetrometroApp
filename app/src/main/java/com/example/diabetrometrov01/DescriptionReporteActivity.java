package com.example.diabetrometrov01;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diabetrometrov01.DataAccessObject.PacienteDAO;
import com.example.diabetrometrov01.DataAccessObject.ReportesDAO;
import com.example.diabetrometrov01.DataTransferObject.PacienteDTO;
import com.example.diabetrometrov01.DataTransferObject.ReportesDTO;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DescriptionReporteActivity extends AppCompatActivity {

    ReportesDAO reportesDAO = new ReportesDAO();
    ReportesDTO reportesDTO;

    TextView txtvFechaInicialReporteShow, txtvFechaFinalReporteShow,
            txtvFechaRegistroReporteShow, txtvNombrePacienteReporteShow,
            txtvApellidosPacienteReporteShow, txtvFechaNacimientoReporteShow,
            txtvCorreoElectronicoReporteShow, txtvSexoReporteShow,
            txtvPromedioPorcionesReporteShow, txtvPromedioCaloriasReporteShow,
            txtvPromedioCarbohidratosReporteShow, txtvPromedioProteinasReporteShow,
            txtvPromedioGrasasReporteShow, txtvPromedioPesoReporteShow,
            txtvPromedioAlturaReporteShow, txtvPromedioGlucosaReporteShow,
            txtvFinalPesoReporteShow, txtvFinalAlturaReporteShow,
            txtvFinalGlucosaReporteShow, txtvObservacionReporteShow,
            txtvFraseMotivacionalReporteShow, txtvEdadReporteShow;

    LinearLayout Linear;

    Button BTNBackReporteShow, BTNDeleteReporteShow, BTNPrintReporteShow;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_reporte);

        //Botones
        Linear = findViewById(R.id.LinearPrint);
        txtvEdadReporteShow = findViewById(R.id.txtvEdadReporteShow);
        txtvFechaInicialReporteShow = findViewById(R.id.txtvFechaInicialReporteShow);
        txtvFechaFinalReporteShow = findViewById(R.id.txtvFechaFinalReporteShow);
        txtvFechaRegistroReporteShow = findViewById(R.id.txtvFechaRegistroReporteShow);
        txtvNombrePacienteReporteShow = findViewById(R.id.txtvNombrePacienteReporteShow);
        txtvApellidosPacienteReporteShow = findViewById(R.id.txtvApellidosPacienteReporteShow);
        txtvFechaNacimientoReporteShow = findViewById(R.id.txtvFechaNacimientoReporteShow);
        txtvCorreoElectronicoReporteShow = findViewById(R.id.txtvCorreoElectronicoReporteShow);
        txtvSexoReporteShow = findViewById(R.id.txtvSexoReporteShow);
        txtvPromedioPorcionesReporteShow = findViewById(R.id.txtvPromedioPorcionesReporteShow);
        txtvPromedioCaloriasReporteShow = findViewById(R.id.txtvPromedioCaloriasReporteShow);
        txtvPromedioCarbohidratosReporteShow = findViewById(R.id.txtvPromedioCarbohidratosReporteShow);
        txtvPromedioProteinasReporteShow = findViewById(R.id.txtvPromedioProteinasReporteShow);
        txtvPromedioGrasasReporteShow = findViewById(R.id.txtvPromedioGrasasReporteShow);
        txtvPromedioPesoReporteShow = findViewById(R.id.txtvPromedioPesoReporteShow);
        txtvPromedioAlturaReporteShow = findViewById(R.id.txtvPromedioAlturaReporteShow);
        txtvPromedioGlucosaReporteShow = findViewById(R.id.txtvPromedioGlucosaReporteShow);
        txtvFinalPesoReporteShow = findViewById(R.id.txtvFinalPesoReporteShow);
        txtvFinalAlturaReporteShow = findViewById(R.id.txtvFinalAlturaReporteShow);
        txtvFinalGlucosaReporteShow = findViewById(R.id.txtvFinalGlucosaReporteShow);
        txtvObservacionReporteShow = findViewById(R.id.txtvObservacionReporteShow);
        txtvFraseMotivacionalReporteShow = findViewById(R.id.txtvFraseMotivacionalReporteShow);
        BTNBackReporteShow = findViewById(R.id.BTNBackReporteShow);
        BTNDeleteReporteShow = findViewById(R.id.BTNDeleteReporteShow);
        BTNPrintReporteShow = findViewById(R.id.BTNPrintReporteShow);

        Valores();

        //Funciones
        BTNBackReporteShow.setOnClickListener(v -> { finish(); });
        BTNDeleteReporteShow.setOnClickListener(v -> { Eliminar(); });
        BTNPrintReporteShow.setOnClickListener(v -> { Imprimir(); });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void Valores() {
        try {
            reportesDTO = (ReportesDTO) getIntent().getSerializableExtra("ReportesDTO");

            PacienteDAO xd = new PacienteDAO();
            PacienteDTO x2 = xd.buscar(new PacienteDTO(reportesDTO.getIdPaciente()),0);

            txtvFechaInicialReporteShow.setText(reportesDTO.ApplyFormat(reportesDTO.getFechaInicio()));
            txtvFechaFinalReporteShow.setText(reportesDTO.ApplyFormat(reportesDTO.getFechaFinal()));
            txtvFechaRegistroReporteShow.setText(reportesDTO.ApplyFormat(reportesDTO.getDia()));
            txtvNombrePacienteReporteShow.setText(x2.getNombre());
            txtvApellidosPacienteReporteShow.setText(x2.getApellidos());
            txtvFechaNacimientoReporteShow.setText(x2.getFechaNacimientoString());
            txtvCorreoElectronicoReporteShow.setText(x2.getCorreo());
            txtvSexoReporteShow.setText(x2.getSexoString());
            txtvPromedioPorcionesReporteShow.setText(reportesDTO.ApplyFormat(reportesDTO.getPorcionProm()));
            txtvPromedioCaloriasReporteShow.setText(reportesDTO.ApplyFormat(reportesDTO.getCaloriasProm()));
            txtvPromedioCarbohidratosReporteShow.setText(reportesDTO.ApplyFormat(reportesDTO.getCarbohidratosProm()));
            txtvPromedioProteinasReporteShow.setText(reportesDTO.ApplyFormat(reportesDTO.getProteinas()));
            txtvPromedioGrasasReporteShow.setText(reportesDTO.ApplyFormat(reportesDTO.getGrasas()));
            txtvPromedioPesoReporteShow.setText(reportesDTO.ApplyFormat(reportesDTO.getPesoProm()));
            txtvPromedioAlturaReporteShow.setText(reportesDTO.ApplyFormat(reportesDTO.getTallaProm()));
            txtvPromedioGlucosaReporteShow.setText(reportesDTO.ApplyFormat(reportesDTO.getLvlGlucosaProm()));
            txtvFinalPesoReporteShow.setText(reportesDTO.ApplyFormat(reportesDTO.getPesoFinal()));
            txtvFinalAlturaReporteShow.setText(reportesDTO.ApplyFormat(reportesDTO.getTallaFinal()));
            txtvFinalGlucosaReporteShow.setText(reportesDTO.ApplyFormat( reportesDTO.getLvlGlucosafinal()));
            txtvObservacionReporteShow.setText(reportesDTO.getObservacion());
            txtvFraseMotivacionalReporteShow.setText( reportesDAO.FraseMotivacional(reportesDTO.getIdFraseMot()).getFraseS());
            txtvEdadReporteShow.setText(String.format("%s años", reportesDTO.ApplyFormat(x2.getEdad())));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void finish() {
        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        super.finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void Eliminar() {
        MaterialAlertDialogBuilder Alert = new MaterialAlertDialogBuilder(DescriptionReporteActivity.this);
        Alert.setTitle(" - Salir");
        Alert.setMessage("¿Estas seguro de salir sin guardar cambios?");
        Alert.setPositiveButton("Confirmo", (dialog, which) -> {
            if(reportesDAO.eliminar(reportesDTO)){
                Toast.makeText(getApplicationContext(), "Eliminado correctamente", Toast.LENGTH_SHORT).show();
                finish();
            } else{
                Toast.makeText(getApplicationContext(), "Error al eliminar: " + reportesDAO.getErrorInDB(), Toast.LENGTH_SHORT).show();
            }
        });
        Alert.setNegativeButton(R.string.cancel, (dialog, which) -> {

        });
        Alert.show();




    }

    Bitmap bitmap;

    private void Imprimir() {
        Log.d("size",Linear.getWidth()+" "+Linear.getHeight());
        bitmap=loadBitmapFromView(Linear,Linear.getWidth(),Linear.getHeight());
        createPdf();
    }

    private void createPdf() {
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics=new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float height=displayMetrics.heightPixels;
        float width=displayMetrics.widthPixels;

        int convertHeight=(int)height,
                convertWidth=(int)width;

        PdfDocument document=new PdfDocument();
        PdfDocument.PageInfo pageInfo=new PdfDocument.PageInfo.Builder(convertWidth,convertHeight,1).create();
        PdfDocument.Page page=document.startPage(pageInfo);

        Canvas canvas=page.getCanvas();
        Paint paint=new Paint();
        canvas.drawPaint(paint);
        bitmap= Bitmap.createScaledBitmap(bitmap,convertWidth,convertHeight,true);
        canvas.drawBitmap(bitmap,0,0,null);
        document.finishPage(page);

        //write document content
        @SuppressLint("SdCardPath") String targetPdf="page.pdf";
        File filepath=new File(getExternalFilesDir(null), targetPdf);
        try {
            document.writeTo(new FileOutputStream(filepath));
            //close document
            document.close();
            Toast.makeText(this, "pdf created successfully", Toast.LENGTH_SHORT).show();
            openPdf();
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, "something want wrong try again "+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void openPdf() {
        File file=new File(getExternalFilesDir(null), "page.pdf");
        if(file.exists()){
            Intent intent=new Intent(Intent.ACTION_VIEW);
           // Uri uri=Uri.fromFile(file);
            Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", file);
            intent.setDataAndType(photoURI,"application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            try {
                startActivity(intent);
            }catch (ActivityNotFoundException e){
                Toast.makeText(this, "No application found for pdf reader", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(this, "No application found for pdf reader", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap loadBitmapFromView(View linearLayout, int width, int height) {
        bitmap=Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        linearLayout.draw(canvas);
        return bitmap;
    }

}