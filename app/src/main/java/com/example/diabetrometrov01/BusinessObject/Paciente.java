package com.example.diabetrometrov01.BusinessObject;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.diabetrometrov01.DataAccessObject.PacienteDAO;
import com.example.diabetrometrov01.DataTransferObject.PacienteDTO;
import com.example.diabetrometrov01.DataTransferObject.PacienteDatosDTO;
import com.example.diabetrometrov01.Interfaces.Paciente.Login;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Paciente {

    public String getError(){
        return DB.getErrorInDB();
    }

    public static final int DNIKEY = 1;
    public static final int CORREOELECTRONICOKEY = 2;

    PacienteDAO DB = new PacienteDAO();
    PacienteDatosDTO[] Datos;
    PacienteDTO Paciente;
    
    // <editor-fold defaultstate="collapsed" desc="Metodos solo para exponer">  
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void imprimirLista() {
        for (PacienteDTO arg : listar()) {
            System.out.println(arg.toString() + "\n");
        }
    }// </editor-fold> 

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<PacienteDTO> listar() {
        List<PacienteDTO> lista = DB.listar();
        return lista != null ? lista : null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean agregar(String Correo, String DNI, String Contrasena, String Nombre, String Apellidos, char Sexo, String FechaNacimiento){
        Paciente = new PacienteDTO(
                Correo,
                DNI,
                Contrasena,
                Nombre,
                Apellidos,
                Sexo,
                LocalDateTime.now(),
                LocalDate.parse(FechaNacimiento));
        return DB.agregar(Paciente);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public PacienteDTO buscar(String dato, int tipodato) {
        Paciente = new PacienteDTO();
        switch (tipodato) {
            case 1:
                Paciente.setDNI(dato);
                break; //Busca por DNIKEY
            case 2:
                Paciente.setCorreo(dato);
                break; //Busca por Correo Electronico
            default:
                try {
                    Paciente.setIdPaciente(Integer.parseInt(dato));
                } catch (NumberFormatException e) {
                    System.out.println(this.getClass().getName() + "Se envio un id erroneo: " + e.getMessage());
                }
                break; //Busca por IDKEY
            }
        return DB.buscar(Paciente, tipodato);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String eliminar(String DNI) {
        Paciente = buscar(DNI, DNIKEY);
        if (Paciente == null) {
            return "no se encontro el DNI";
        }
        return DB.eliminar(Paciente) ? "Se elimino correctamente" : "error papa";
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean Existe(String dato, int tipodato) {
        return buscar(dato, tipodato) != null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean IniciarSesion(String Correo, String Contrasena) {
        Paciente = buscar(Correo, CORREOELECTRONICOKEY);
        if (Paciente != null) {
            return Paciente.getContrasena().equals(Contrasena);
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean RegistrarPaciente(String Correo, String DNI, String Contrasena, String Nombre, String Apellidos, char Sexo, String FechaNacimiento) {
        try {
            if(agregar(Correo, DNI, Contrasena, Nombre, Apellidos, Sexo, FechaNacimiento)) {
                DB.setErrorInDB("Se agrego correctamente");
                return true;
            }
        } catch (Exception e) {
            DB.setErrorInDB("Error al agregar a la base de datos" + e.getMessage());
            return false;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean ValidarPaciente(String Correo, String DNI, String Contrasena) {
        if (ValidateDNI(DNI) && ValidateContrasena(Contrasena) && ValidateCorreo(Correo)) {
            return !(Existe(Correo, CORREOELECTRONICOKEY) || Existe(DNI, DNIKEY));
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean ValidarEditPaciente(String Correo, String Contrasena) {
        if ( ValidateContrasena(Contrasena) && ValidateCorreo(Correo)) {
            PacienteDTO aux = buscar(Correo, CORREOELECTRONICOKEY);
            if (aux != null ) {
                if(aux.getIdPaciente() == Login.UsuarioAPP.getIdPaciente()){
                    return true;
                }else {
                    DB.setErrorInDB("El correo ya esta en uso");
                }
            } else {
                return true;
            }
        } else {
            DB.setErrorInDB("El dni o contrasena ingresado son invalidos");
        }
        return false;
    }

    public boolean ValidateDNI(String DNI) {
        if (!(DNI.isEmpty())) {
            int addition = 0;
            int[] hash = {3, 2, 7, 6, 5, 4, 3, 2};
            int IDL = DNI.length() - 1;
            for (int i = 0; i < IDL; i++) {
                addition += Integer.parseInt(DNI.substring(i, i + 1)) * hash[i];
            }
            addition = 11 - (addition % 11);
            addition = addition == 11 ? 0 : addition;
            char last = Character.toUpperCase(DNI.charAt(IDL));
            if (IDL == 11) {
                return addition == Integer.parseInt(String.valueOf(last));
            } else if (Character.isDigit(last)) {
                char[] hashNumbers = {'6', '7', '8', '9', '0', '1', '1', '2', '3', '4', '5'};
                System.out.println(hashNumbers[addition]);
                return last == hashNumbers[addition];
            } else if (Character.isLetter(last)) {
                char[] hashLetters = {'K', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
                return last == (hashLetters[addition]);
            }
        }
        return false;
    }
    
    public boolean ValidateCorreo(String email) {
           String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
           java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
           java.util.regex.Matcher m = p.matcher(email);
           return m.matches();
    }

    public boolean ValidateContrasena(String Contrasena) { // ^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,}$
        if (Contrasena.length() >= 8 && Contrasena.length() <= 20) {
            char clave;
            byte contNumero = 0, contLetraMay = 0, contLetraMin = 0;
            for (byte i = 0; i < Contrasena.length(); i++) {
                clave = Contrasena.charAt(i);
                String passValue = String.valueOf(clave);
                if (passValue.matches("[A-Z]")) {
                    contLetraMay++;
                } else if (passValue.matches("[a-z]")) {
                    contLetraMin++;
                } else if (passValue.matches("[0-9]")) {
                    contNumero++;
                }
            }
            return contLetraMay >= 1 && contLetraMin >= 1 && contNumero >= 1;
        }
        return false;
    }
}
