package com.example.diabetrometrov01.Interfaces.Paciente;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.diabetrometrov01.BusinessObject.Paciente;
import com.example.diabetrometrov01.DataTransferObject.PacienteDTO;
import com.example.diabetrometrov01.DescriptionReporteActivity;
import com.example.diabetrometrov01.R;
import com.example.diabetrometrov01.databinding.FragmentRegistroBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class RegisterFragment extends Fragment {

    private FragmentRegistroBinding vw;
    private Paciente asd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vw = FragmentRegistroBinding.inflate(inflater, container, false);
        return vw.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        asd = new Paciente();
        DatePickerDialog.OnDateSetListener setListener;

        Valitions(vw.TILCorreoElectronicoRegister, vw.txtCorreoElectronicoRegister);
        Valitions(vw.TILDNIRegister, vw.txtDNIRegister);
        Valitions(vw.TILContrasenaRegister, vw.txtContrasenaRegister);
        Valitions(vw.TILSexoRegister, vw.txtSexoRegister);
        Valitions(vw.TILNombreRegister, vw.txtNombreRegister);
        Valitions(vw.TILApellidosRegister, vw.txtApellidosRegister);
        Valitions(vw.TILFechaNacimientoRegistro, vw.txtFechaNacimientoRegistro);

        vw.BTNCUIInfoRegister.setOnClickListener(v -> {
            View view1 = getLayoutInflater().inflate(R.layout.image_layout
                    , null);
            AlertDialog infoDialog = new AlertDialog.Builder(getContext())
                    .setView(view1)
                    .create();
            view1.findViewById(R.id.dialog_button).setOnClickListener(v1 -> {
                infoDialog.dismiss();
            });
            infoDialog.getWindow().setGravity(Gravity.TOP | Gravity.END);
            infoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            infoDialog.show();
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.select_dialog_item,
                getResources().getStringArray(R.array.reply_sexo));
        vw.txtSexoRegister.setAdapter(adapter);

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                DecimalFormat DF = new DecimalFormat("00");
                vw.txtFechaNacimientoRegistro.setText(
                        DF.format(year) + "-" + DF.format(month + 1) + "-" + DF.format(dayOfMonth));
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        vw.txtFechaNacimientoRegistro.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (!datePickerDialog.isShowing()) {
                    datePickerDialog.show();
                }
            }
        });
        vw.txtFechaNacimientoRegistro.setOnFocusChangeListener((v, hasFocus) -> {
            if (vw.txtFechaNacimientoRegistro.isFocused() && !datePickerDialog.isShowing()) {
                datePickerDialog.show();
            }
        });
        datePickerDialog.setOnDateSetListener(setListener);

        vw.BTNNextRegister.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                try {
                    Process();
                } catch (Exception exception) {
                    Toast.makeText(getContext(), "Error: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        vw = null;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void Process() {
        if (Validaciones()) { // Validaciones Locales
            if (isNotRedudant()) { // Validaciones Locales
                NavHostFragment.findNavController(RegisterFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment);
                Login.UsuarioAPP = new PacienteDTO(
                        vw.txtCorreoElectronicoRegister.getText().toString(),
                        vw.txtDNIRegister.getText().toString(),
                        vw.txtContrasenaRegister.getText().toString(),
                        vw.txtNombreRegister.getText().toString(),
                        vw.txtApellidosRegister.getText().toString(),
                        getSexoChar(),
                        getFechaNacimiento()
                );
            } else {
                Toast.makeText(getContext(), "Ya se encuentra registrado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean Validaciones() {
        boolean[] result = {
                        ValidationCorreo(),
                        ValidationDNI(),
                        ValidationContrasena(),
                        isSamePassword(),
                        isGreatYears(),
                        isCampoCompleted(vw.TILCorreoElectronicoRegister, vw.txtCorreoElectronicoRegister),
                        isCampoCompleted(vw.TILDNIRegister, vw.txtDNIRegister),
                        isCampoCompleted(vw.TILContrasenaRegister, vw.txtContrasenaRegister),
                        isCampoCompleted(vw.TILSexoRegister, vw.txtSexoRegister),
                        isCampoCompleted(vw.TILNombreRegister, vw.txtNombreRegister),
                        isCampoCompleted(vw.TILApellidosRegister, vw.txtApellidosRegister),
                        isCampoCompleted(vw.TILFechaNacimientoRegistro, vw.txtFechaNacimientoRegistro)
                };
        return result[0] && result[1] && result[2] && result[3] && result[4] && result[5] && result[6] && result[7]  && result[8] && result[9]
                && result[10] && result[11];
    }

    private boolean isSamePassword() {
        boolean result = vw.txtContrasenaRegister.getText().toString().trim()
                .equals(vw.txtConfirmContrasenaRegister.getText().toString().trim());
        if (result) {
            vw.TILConfirmContrasenaRegister.setError(null);
        } else {
            vw.TILConfirmContrasenaRegister.setError("No coinciden las contraseña");
        }
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean isNotRedudant() {

        return asd.ValidarPaciente(
                vw.txtCorreoElectronicoRegister.getText().toString(),
                vw.txtDNIRegister.getText().toString() + vw.txtCodeDocumentRegister.getText().toString(),
                vw.txtContrasenaRegister.getText().toString());
    }

    private char getSexoChar() {
        return vw.txtSexoRegister.getText().toString()
                .equals("Masculino") ?
                'M' : vw.txtSexoRegister.getText().toString()
                .equals("Femenino") ?
                'F' : 'O';
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private LocalDate getFechaNacimiento() {
        return LocalDate.parse(vw.txtFechaNacimientoRegistro.getText().toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean isGreatYears() {
        if(!vw.txtFechaNacimientoRegistro.getText().toString().equals("")){
            if (Period.between(getFechaNacimiento(), LocalDate.now()).getYears() < 5) {
                vw.TILFechaNacimientoRegistro.setError("Debe ser mayor de edad");
                return false;
            } else if (Period.between(getFechaNacimiento(), LocalDate.now()).getYears() > 120) {
                vw.TILFechaNacimientoRegistro.setError("Edad muy superior");
                return false;
            } else {
                vw.TILFechaNacimientoRegistro.setError(null);
                return true;
            }
        }
        vw.TILFechaNacimientoRegistro.setError(getString(R.string.REQUIRED_CAMPO));
        return false;
    }

    private boolean isCampoCompleted(TextInputLayout exs, EditText ex) {
        boolean result = ex.getText().toString().trim().equals("");
        if (result) {
            exs.setError(getString(R.string.REQUIRED_CAMPO));
        }
        return !result;
    }

    private void Valitions(TextInputLayout exs, EditText ex) {
        ex.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().equals("")) {
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

    private boolean ValidationCorreo() {
        boolean result = asd.ValidateCorreo(vw.txtCorreoElectronicoRegister.getText().toString().trim());
        if (result) {
            vw.TILCorreoElectronicoRegister.setError(null);
        } else {
            vw.TILCorreoElectronicoRegister.setError("Ingresa un correo valido");
        }
        return result;
    }

    private boolean ValidationDNI() {
        boolean result = asd.ValidateDNI(vw.txtDNIRegister.getText().toString().trim() + vw.txtCodeDocumentRegister.getText().toString().trim());
        if (result) {
            vw.TILDNIRegister.setError(null);
        } else {
            vw.TILDNIRegister.setError("Ingresa un correo valido");
        }
        return result;
    }

    public boolean ValidationContrasena() { // ^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,}$
        boolean result = asd.ValidateContrasena(vw.txtContrasenaRegister.getText().toString());
        if (result) {
            vw.TILContrasenaRegister.setError(null);
        } else {
            vw.TILContrasenaRegister.setError("Tu contraseña necesita 8 caracteres con Mínimo: 1 Mayuscula, 1 minuscula y 1 numero");
        }
        return result;
    }


}