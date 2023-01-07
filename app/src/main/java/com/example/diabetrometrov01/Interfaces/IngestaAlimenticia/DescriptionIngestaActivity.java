package com.example.diabetrometrov01.Interfaces.IngestaAlimenticia;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diabetrometrov01.BusinessObject.Alimento;
import com.example.diabetrometrov01.DataAccessObject.IngestaAlimenticiaDAO;
import com.example.diabetrometrov01.DataTransferObject.AlimentoDTO;
import com.example.diabetrometrov01.DataTransferObject.IngestaDTO;
import com.example.diabetrometrov01.DescriptionReporteActivity;
import com.example.diabetrometrov01.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

public class DescriptionIngestaActivity extends AppCompatActivity {

    IngestaAlimenticiaDAO ingestaAlimenticiaDAO = new IngestaAlimenticiaDAO();
    private final Alimento alimento = new Alimento();

    TextInputLayout TILPorcionEditIngesta;
    TextInputLayout TILHoraConsumoEditIngesta;
    EditText txtAlimentoEdit;
    EditText txtCategoriaAlimentoEdit;
    EditText txtHoraConsumoEdit;
    EditText txtPorcionEdit;
    TextView txtvFechaRegistroEditAlimento;
    Button BTNBackEditIngesta;
    Button BTNModifyEditIngesta;
    Button BTNDeleteEditIngesta;
    Button BTNSaveEditIngesta;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_ingesta);

        TILPorcionEditIngesta = findViewById(R.id.TILPorcionEditIngesta);
        TILHoraConsumoEditIngesta = findViewById(R.id.TILHoraConsumoEditIngesta);
        txtAlimentoEdit = findViewById(R.id.txtAlimentoEditIngesta);
        txtCategoriaAlimentoEdit = findViewById(R.id.txtCategoriaAlimentoEditIngesta);
        txtHoraConsumoEdit = findViewById(R.id.txtHoraConsumoEditIngesta);
        txtPorcionEdit = findViewById(R.id.txtPorcionEditIngesta);
        txtvFechaRegistroEditAlimento = findViewById(R.id.txtvFechaRegistroReporteShow);
        BTNBackEditIngesta = findViewById(R.id.BTNBackReporteShow);
        BTNModifyEditIngesta = findViewById(R.id.BTNModifyEditIngesta);
        BTNDeleteEditIngesta = findViewById(R.id.BTNDeleteReporteShow);
        BTNSaveEditIngesta = findViewById(R.id.BTNPrintReporteShow);



        AtomicInteger t1hour = new AtomicInteger();
        AtomicInteger t1minute = new AtomicInteger();
        TimePickerDialog timePickerDialog = new TimePickerDialog(DescriptionIngestaActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                (view, hour, minute) -> {
                    t1hour.set(hour);
                    t1minute.set(minute);
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(0, 0, 0, t1hour.get(), t1minute.get());
                    DecimalFormat DF = new DecimalFormat("00");
                    txtHoraConsumoEdit.setText(DF.format(t1hour.get()) + ":" + DF.format(t1minute.get()));
                },
                12,
                0,
                true
        );

        txtHoraConsumoEdit.setOnClickListener((v) -> {
            if (!timePickerDialog.isShowing()) {
                timePickerDialog.updateTime(t1hour.get(), t1minute.get());
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });
        txtHoraConsumoEdit.setOnFocusChangeListener((v, hasFocus) -> {
            if (txtHoraConsumoEdit.isFocused() && !timePickerDialog.isShowing()) {
                timePickerDialog.updateTime(t1hour.get(), t1minute.get());
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });

        txtPorcionEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isNumber(s.toString())) {
                    float number = Float.parseFloat(s.toString());
                    if (number > 0 && number <= 4000) {
                        TILPorcionEditIngesta.setError(null);
                    } else {
                        TILPorcionEditIngesta.setError("Numero no valido");
                    }
                } else {
                    TILPorcionEditIngesta.setError("Numero no valido");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        try {
            IngestaDTO ingestaDTO = (IngestaDTO) getIntent().getSerializableExtra("IngestaDTO");

            AlimentoDTO alimentoDTO = alimento.buscar(String.valueOf(ingestaDTO.getIdAlimento()), 0);

            txtAlimentoEdit.setText(alimentoDTO.getNombre());
            txtCategoriaAlimentoEdit.setText(alimento.buscar(alimentoDTO.getIdCatAlimento()).getNombreCat());
            txtHoraConsumoEdit.setText(ingestaDTO.ApplyFormatS(ingestaDTO.getHoraConsumo()));
            txtPorcionEdit.setText(ingestaDTO.ApplyFormat(ingestaDTO.getPorcion()));
            txtvFechaRegistroEditAlimento.setText(ingestaDTO.ApplyFormat(ingestaDTO.getDia()));

            if (Period.between(LocalDate.from(ingestaDTO.getDia()), LocalDate.now()).getDays() >= 1) {
                BTNModifyEditIngesta.setVisibility(View.INVISIBLE);
            }

            BTNBackEditIngesta.setOnClickListener(v -> { finish(); });
            BTNModifyEditIngesta.setOnClickListener(v -> { Editar(ingestaDTO); });
            BTNDeleteEditIngesta.setOnClickListener(v -> { Eliminar(ingestaDTO); });
            BTNSaveEditIngesta.setOnClickListener(v -> { Guardar(ingestaDTO); });

        } catch (Exception exception) {
            exception.printStackTrace();
        }



    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void Editar(IngestaDTO ingestaDTO) {
        if (!BTNModifyEditIngesta.getText().toString().equals("Cancelar")) {
            BTNDeleteEditIngesta.setVisibility(View.VISIBLE);
            BTNSaveEditIngesta.setVisibility(View.VISIBLE);
            txtPorcionEdit.setEnabled(true);
            txtHoraConsumoEdit.setEnabled(true);
            BTNModifyEditIngesta.setText("Cancelar");
            BTNModifyEditIngesta.setTextColor(Color.parseColor("#FF0000"));
        } else {
            BTNModifyEditIngesta.setText("Modificar");
            txtHoraConsumoEdit.setText(ingestaDTO.ApplyFormatS(ingestaDTO.getHoraConsumo()));
            txtPorcionEdit.setText(ingestaDTO.ApplyFormat(ingestaDTO.getPorcion()));
            BTNSaveEditIngesta.setVisibility(View.INVISIBLE);
            BTNModifyEditIngesta.setTextColor(Color.parseColor("#00E1FF"));
            BTNDeleteEditIngesta.setVisibility(View.INVISIBLE);
            txtPorcionEdit.setEnabled(false);
            txtHoraConsumoEdit.setEnabled(false);
        }
    }

    private void Eliminar(IngestaDTO ingestaDTO) {
        MaterialAlertDialogBuilder Alert = new MaterialAlertDialogBuilder(DescriptionIngestaActivity.this);
        //Alert.setTitle("Cuadro de Confirmacion");
        Alert.setMessage("¿Estas seguro de eliminar este Alimento?");
        Alert.setPositiveButton("Ok.", (dialog, which) -> {
            if (ingestaAlimenticiaDAO.eliminar(ingestaDTO)) {
                Toast.makeText(getApplicationContext(), "Eliminado correctamente", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Error al eliminar" + ingestaAlimenticiaDAO.getErrorInDB(), Toast.LENGTH_SHORT).show();
            }
        });
        Alert.setNegativeButton(R.string.cancel, (dialog, which) -> {
        });
        Alert.show();
    }

    private void Guardar(IngestaDTO ingestaDTO) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        long ms = 0;
        try {
            ms = sdf.parse(txtHoraConsumoEdit.getText().toString()).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (validate()) {
            ingestaDTO.setPorcion(Float.parseFloat(txtPorcionEdit.getText().toString()));
            ingestaDTO.setHoraConsumo(new Time(ms));
            if (ingestaAlimenticiaDAO.actualizar(ingestaDTO)) {
                Toast.makeText(getApplicationContext(), "Actualizado correctamente", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Error al actualizar" + ingestaAlimenticiaDAO.getErrorInDB(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "wtf correctamente", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void finish() {
        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        super.finish();
    }

    private boolean isNumber(String Number) {
        try {
            Float.parseFloat(Number);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private boolean validate() {
        boolean[] result =
                {
                        isCampoCompleted(TILPorcionEditIngesta, txtPorcionEdit),
                        isCampoCompleted(TILHoraConsumoEditIngesta, txtHoraConsumoEdit),
                        isValid()
                };
        return result[0] && result[1] && result[2];
    }

    private boolean isCampoCompleted(TextInputLayout exs, EditText ex) {
        boolean result = ex.getText().toString().trim().equals("");
        if (result) {
            exs.setError(getString(R.string.REQUIRED_CAMPO));
        }
        return !result;
    }

    private boolean isValid() {
        if (isNumber(txtPorcionEdit.getText().toString())) {
            float number = Float.parseFloat(txtPorcionEdit.getText().toString());
            if (number > 0 && number <= 4000) {
                TILPorcionEditIngesta.setError(null);
                return true;
            }
        }
        TILPorcionEditIngesta.setError("Numero no valido");
        return false;
    }

}