package com.example.diabetrometrov01.Interfaces.Paciente;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diabetrometrov01.BusinessObject.Paciente;
import com.example.diabetrometrov01.DataTransferObject.PacienteDTO;
import com.example.diabetrometrov01.Interfaces.IngestaAlimenticia.DescriptionIngestaActivity;
import com.example.diabetrometrov01.Interfaces.home.MenuSlideActivity;
import com.example.diabetrometrov01.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    public static PacienteDTO UsuarioAPP = new PacienteDTO();
    private Paciente asd;
    private TextInputLayout Correo;
    private TextInputLayout Password;
    private EditText CorreoText;
    private EditText PasswordText;
    private Button botonLogin;
    private TextView BTNViewForgotPassLogin;
    private TextView register;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        asd = new Paciente();

        Correo = (TextInputLayout) findViewById(R.id.TextInputCorreoLogin);
        Password = (TextInputLayout) findViewById(R.id.TextInputContrasena);
        CorreoText = (EditText) findViewById(R.id.txtCorreoLogin) ;
        PasswordText = (EditText) findViewById(R.id.txtPasswordLogin) ;
        botonLogin = (Button) findViewById(R.id.buttonLogin);
        register = (TextView) findViewById(R.id.registerbutton);
        BTNViewForgotPassLogin = (TextView) findViewById(R.id.BTNViewForgotPassLogin);

        Valitions(Correo,CorreoText);
        Valitions(Password, PasswordText);

        BTNViewForgotPassLogin.setOnClickListener(v -> {
            MaterialAlertDialogBuilder Alert = new MaterialAlertDialogBuilder(Login.this);
            Alert.setTitle("!Ups¡");
            Alert.setMessage("Aún no contamos con este servicio D:");
            Alert.setPositiveButton("Ok.",(dialog, which) -> {
                //Nothing for now
            });
            Alert.show();
        });
        register.setOnClickListener(view -> {
            try {
                startActivity(new Intent(getApplicationContext(), Register.class));
            } catch (Exception exception){
                Toast.makeText(this, "Error" + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        botonLogin.setOnClickListener(view -> {
            try {
                if (isCampoCompleted(Correo, CorreoText)){
                    if (ValidationCorreo()) {
                        if (isCampoCompleted(Password, PasswordText)) {
                            if (ValidationUser()) {
                                UsuarioAPP = asd.buscar(CorreoText.getText().toString(), 2);
                                startActivity(new Intent(getApplicationContext(), MenuSlideActivity.class));
                                Toast.makeText(this, "Inicio Sesion Correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                MaterialAlertDialogBuilder Alert = new MaterialAlertDialogBuilder(Login.this);
                                Alert.setTitle("!Error al Iniciar Sesión¡");
                                Alert.setMessage("No encontramos coincidencias de la contraseña o el correo electronico, porfavor ingresa nuevamente.");
                                Alert.setPositiveButton("Ok.",(dialog, which) -> {
                                    PasswordText.setText("");
                                });
                                Alert.show();
                            }
                        }
                    }
                }
            } catch (Exception exception){
                exception.printStackTrace();
                Toast.makeText(this, asd.getError() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean ValidationUser(){
        return asd.IniciarSesion(CorreoText.getText().toString(),PasswordText.getText().toString());
    }

    private boolean isCampoCompleted(TextInputLayout exs, EditText ex){
        boolean result = ex.getText().toString().trim().equals("");
        if(result){
            exs.setError(getString(R.string.REQUIRED_CAMPO));
        }
        return !result ;
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

    private boolean ValidationCorreo(){
        boolean result = asd.ValidateCorreo(CorreoText.getText().toString());
        if(result){
            Correo.setError(null);
        } else {
            Correo.setError("Ingresa un correo valido");
        }
        return result;
    }
}