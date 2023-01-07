package com.example.diabetrometrov01.Interfaces.Paciente;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diabetrometrov01.BusinessObject.Paciente;
import com.example.diabetrometrov01.DataAccessObject.PacienteDAO;
import com.example.diabetrometrov01.DataTransferObject.PacienteDTO;
import com.example.diabetrometrov01.Interfaces.IngestaAlimenticia.DescriptionIngestaActivity;
import com.example.diabetrometrov01.R;
import com.example.diabetrometrov01.databinding.FragmentEditUserBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class editUser extends Fragment {

    private FragmentEditUserBinding EU;
    private PacienteDAO paciente;
    private PacienteDTO auxiliar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EU = FragmentEditUserBinding.inflate(inflater, container, false);
        View root = EU.getRoot();

        Valitions(EU.TILSexoEditPaciente, EU.txtSexoEditPaciente);
        Valitions(EU.TILNombreEditPaciente, EU.txtNombreEditPaciente);
        Valitions(EU.TILApellidosEditPaciente, EU.txtApellidosEditPaciente);
        Valitions(EU.TILFechaNacimientoEditPaciente, EU.txtFechaNacimientoEditPaciente);

        DatePickerDialog.OnDateSetListener setListener;
        setListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                DecimalFormat DF = new DecimalFormat("00");
                EU.txtFechaNacimientoEditPaciente.setText(DF.format(year) +"-"+ DF.format(month+1) +"-"+ DF.format(dayOfMonth));
            }
        };
        DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog(getContext(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());

        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EU.txtFechaNacimientoEditPaciente.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(!datePickerDialog.isShowing()){
                    datePickerDialog.show();
                }
            }
        });
        EU.txtFechaNacimientoEditPaciente.setOnFocusChangeListener((v, hasFocus) -> {
            if(EU.txtFechaNacimientoEditPaciente.isFocused()&&!datePickerDialog.isShowing()){
                datePickerDialog.show();
            }
        });
        datePickerDialog.setOnDateSetListener(setListener);

        FillComponents();

        EU.BTNRestaurarCambiosPaciente.setOnClickListener(v -> {
            FillComponents();
        });

        EU.BTNGuardarCambiosPaciente.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                Paciente asd = new Paciente();
                paciente = new PacienteDAO();
                try {
                    if(isCampoCompleted(EU.TILSexoEditPaciente, EU.txtSexoEditPaciente)
                            &&isCampoCompleted(EU.TILNombreEditPaciente, EU.txtNombreEditPaciente)
                            &&isCampoCompleted(EU.TILApellidosEditPaciente, EU.txtApellidosEditPaciente)
                            &&isCampoCompleted(EU.TILFechaNacimientoEditPaciente, EU.txtFechaNacimientoEditPaciente)) {
                        if(isGreatYears()){
                            MaterialAlertDialogBuilder Alert = new MaterialAlertDialogBuilder(getContext());
                            Alert.setMessage("¿Estas seguro de actualizar tus datos?");
                            Alert.setPositiveButton("Ok.",(dialog, which) -> {
                                auxiliar.setSexo(EU.txtSexoEditPaciente.getText().toString().equals("Masculino") ?
                                        'M': EU.txtSexoEditPaciente.getText().toString().equals("Femenino")?
                                        'F':'O');
                                auxiliar.setFechaNacimiento(LocalDate.parse(EU.txtFechaNacimientoEditPaciente.getText().toString()));
                                auxiliar.setApellidos(EU.txtApellidosEditPaciente.getText().toString());
                                auxiliar.setNombre(EU.txtNombreEditPaciente.getText().toString());
                                if(paciente.actualizar(auxiliar)){
                                    Toast.makeText(getContext(), "Cambios guardados correctamente", Toast.LENGTH_SHORT).show();
                                    Login.UsuarioAPP = auxiliar;
                                }
                            });
                            Alert.setNegativeButton(R.string.cancel, (dialog, which) -> {
                            });
                            Alert.show();
                        }
                    }
                } catch (Exception exception) {
                    Toast.makeText(getContext(), "Error: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void FillComponents(){
        auxiliar = Login.UsuarioAPP;
        EU.txtNombreEditPaciente.setText(auxiliar.getNombre());
        EU.txtApellidosEditPaciente.setText(auxiliar.getApellidos());
        EU.txtFechaNacimientoEditPaciente.setText(auxiliar.getFechaNacimiento().toString());
        EU.txtvFechaRegistroEditPaciente.setText(auxiliar.ApplyFormat(auxiliar.getRegistro()));
        EU.txtSexoEditPaciente.setText(auxiliar.getSexoString());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.select_dialog_item,
                getResources().getStringArray(R.array.reply_sexo));
        EU.txtSexoEditPaciente.setAdapter(adapter);
    }

    private boolean isCampoCompleted(TextInputLayout exs, EditText ex){
        boolean result = ex.getText().toString().trim().equals("");
        if(result){
            exs.setError(getString(R.string.REQUIRED_CAMPO));
        }
        return !result ;
    }

    @RequiresApi(api = Build.VERSION_CODES.O) private LocalDate getFechaNacimiento(){
        return LocalDate.parse(EU.txtFechaNacimientoEditPaciente.getText().toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.O) public boolean isGreatYears() {
        if(Period.between(getFechaNacimiento(), LocalDate.now()).getYears() < 5){
            EU.TILFechaNacimientoEditPaciente.setError("El paciente debe tener mas de 5 años");
        } else if(Period.between(getFechaNacimiento(), LocalDate.now()).getYears() > 120){
            EU.TILFechaNacimientoEditPaciente.setError("El paciente no debe 'tar morido");
        } else{
            return true;
        }
        return false;
    }

    private void Valitions(TextInputLayout exs, EditText ex){
        ex.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().equals("")){
                    exs.setError(null);
                } else {
                    exs.setError(getString(R.string.REQUIRED_CAMPO));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

}