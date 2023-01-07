package com.example.diabetrometrov01.Interfaces.Paciente;

import android.os.Build;
import android.os.Bundle;

import com.example.diabetrometrov01.R;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.diabetrometrov01.databinding.ActivityRegisterBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class Register extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityRegisterBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_register);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_register);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void finish() {
        MaterialAlertDialogBuilder Alert = new MaterialAlertDialogBuilder(Register.this);
        Alert.setTitle("Salir");
        Alert.setMessage("¿Estas seguro de salir sin registrar?");
        Alert.setPositiveButton("Ok.", (dialog, which) -> {
                super.finish();
        });
        Alert.setNegativeButton(R.string.cancel, (dialog, which) -> { });
        Alert.show();
    }
}