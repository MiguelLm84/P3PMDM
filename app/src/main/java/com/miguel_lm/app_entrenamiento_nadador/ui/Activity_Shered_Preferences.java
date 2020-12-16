package com.miguel_lm.app_entrenamiento_nadador.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.miguel_lm.app_entrenamiento_nadador.R;

public class Activity_Shered_Preferences extends AppCompatActivity {

    private long tiempoParaSalir = 0;

    private EditText edNombre;
    private EditText edApellido;
    private EditText edApellido2;
    private EditText edEdad;
    private EditText edAltura;
    private EditText edPeso;

    public static final String PREF_FICHERO = "preferencias";
    public static final String PREF_NOMBRE = "Nombre";
    public static final String PREF_APELLIDO1 = "Primer Apellido";
    public static final String PREF_APELLIDO2 = "Segundo Apellido";
    public static final String PREF_EDIT = "Edad";
    public static final String PREF_ALTURA = "Altura";
    public static final String PREF_PESO = "Peso";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__shered__preferences);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_piscina);

        edNombre = this.findViewById( R.id.edNombre_SheredPreferences);
        edApellido = this.findViewById( R.id.edApellido1_SheredPreferences);
        edApellido2 = this.findViewById( R.id.edApellido2_SheredPreferences);
        edEdad = this.findViewById( R.id.edEdad_SheredPreferences);
        edAltura = this.findViewById( R.id.edAltura_SheredPreferences);
        edPeso = this.findViewById( R.id.edPeso_SheredPreferences);

        leerDatosDesdePreferencias();
    }

    private void leerDatosDesdePreferencias() {

        SharedPreferences preferencias = this.getSharedPreferences(PREF_FICHERO, Context.MODE_PRIVATE);
        String nombre = preferencias.getString(PREF_NOMBRE, "");
        edNombre.setText(nombre);

        String apellido = preferencias.getString(PREF_APELLIDO1,"");
        edApellido.setText(apellido);

        String apellido2 = preferencias.getString(PREF_APELLIDO2,"");
        edApellido2.setText(apellido2);

        String edad = preferencias.getString(PREF_EDIT,"");
        edEdad.setText(edad);

        String altura = preferencias.getString(PREF_ALTURA, "");
        edAltura.setText(altura);

        String peso = preferencias.getString(PREF_PESO, "");
        edPeso.setText(peso);

    }

    public void escribirDatosEnPreferencias(View view) {

        SharedPreferences preferencias = this.getSharedPreferences(PREF_FICHERO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        editor.putString(PREF_NOMBRE, edNombre.getText().toString());
        editor.putString(PREF_APELLIDO1, edApellido.getText().toString());
        editor.putString(PREF_APELLIDO2, edApellido2.getText().toString());
        editor.putString(PREF_EDIT, edEdad.getText().toString());
        editor.putString(PREF_ALTURA, edAltura.getText().toString());
        editor.putString(PREF_PESO, edPeso.getText().toString());
        editor.apply();

        setResult(RESULT_OK);
        finish();
        Toast.makeText(this,"Datos guardados correctamente",Toast.LENGTH_SHORT).show();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onBackPressed(){

        long tiempo = System.currentTimeMillis();
        if (tiempo - tiempoParaSalir > 3000) {
            tiempoParaSalir = tiempo;
            Toast.makeText(this, "Presione de nuevo 'Atrás' si desea salir",
                    Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }
}