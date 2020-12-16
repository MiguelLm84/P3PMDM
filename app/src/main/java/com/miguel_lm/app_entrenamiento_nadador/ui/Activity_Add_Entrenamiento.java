package com.miguel_lm.app_entrenamiento_nadador.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.miguel_lm.app_entrenamiento_nadador.R;
import com.miguel_lm.app_entrenamiento_nadador.modelo.Entrenamiento;
import com.miguel_lm.app_entrenamiento_nadador.modelo.RepositorioEntrenamientos;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Activity_Add_Entrenamiento extends AppCompatActivity {

    private long tiempoParaSalir = 0;
    private Entrenamiento entrenamientoAModificar;
    public static final String CLAVE_ENTRENAMIENTO = "1234";

    // Controles
    private TextView textViewFecha;
    private EditText editTextHoras;
    private EditText editTextMinutos;
    private EditText editTextSegundos;
    private EditText editTextDistancia;

    private Calendar cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__add__entrenamiento);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_piscina);

        textViewFecha = findViewById(R.id.textViewFecha);
        editTextHoras = findViewById(R.id.edTxtHoras);
        editTextMinutos = findViewById(R.id.edTxtMinutos);
        editTextSegundos = findViewById(R.id.edTxtSegundos);
        editTextDistancia = findViewById(R.id.edTxtDistancia);

        cal = Calendar.getInstance();

        textViewFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                escogerFecha();
            }
        });

        entrenamientoAModificar = (Entrenamiento) getIntent().getSerializableExtra(CLAVE_ENTRENAMIENTO);

        if (entrenamientoAModificar != null)
            mostrarEntrenamiento(entrenamientoAModificar);
    }

    public void mostrarEntrenamiento(final Entrenamiento entrenamientoAModificar) {

        editTextHoras.setText(String.valueOf(entrenamientoAModificar.getHoras()));
        editTextMinutos.setText(String.valueOf(entrenamientoAModificar.getMinutos()));
        editTextSegundos.setText(String.valueOf(entrenamientoAModificar.getSegundos()));
        editTextDistancia.setText(String.valueOf(entrenamientoAModificar.getDistanciaMts()));
        textViewFecha.setText(entrenamientoAModificar.getFechaFormateada());

        cal.setTime(entrenamientoAModificar.getFecha());

    }

    public void onClickButtonAceptar(View view) {

        final List<Entrenamiento> listaEntrenamientos = RepositorioEntrenamientos.getInstance(Activity_Add_Entrenamiento.this).obtenerEntrenamientos();

        String horas = editTextHoras.getText().toString();
        String minutos = editTextMinutos.getText().toString();
        String segundos = editTextSegundos.getText().toString();
        String distancia = editTextDistancia.getText().toString();

        int horasInt;
        int minutosInt;
        int segundosInt;
        int distanciaInt;
        try {
            horasInt = Integer.parseInt(horas);
            minutosInt = Integer.parseInt(minutos);
            segundosInt = Integer.parseInt(segundos);
            distanciaInt = Integer.parseInt(distancia);
        } catch (NumberFormatException exception) {

            Toast.makeText(Activity_Add_Entrenamiento.this, "Datos inválidos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (horasInt == 0 && minutosInt == 0 && segundosInt == 0) {
            Toast.makeText(Activity_Add_Entrenamiento.this, "No se permiten todos los valores a 0", Toast.LENGTH_SHORT).show();
            return;
        }

        if (horasInt > 24) {

            Toast.makeText(Activity_Add_Entrenamiento.this, "No se permite horas mayores de 24.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (minutosInt > 59) {

            Toast.makeText(Activity_Add_Entrenamiento.this, "No se permite minutos mayores de 60.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (segundosInt > 59) {

            Toast.makeText(Activity_Add_Entrenamiento.this, "No se permite segundos mayores de 60.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (textViewFecha == null) {

            Toast.makeText(Activity_Add_Entrenamiento.this, "Debe escoger una fecha", Toast.LENGTH_SHORT).show();
            return;
        }

        if (distanciaInt == 0) {
            Toast.makeText(Activity_Add_Entrenamiento.this, "No se permite distancia a 0", Toast.LENGTH_SHORT).show();
            return;
        }
        if (entrenamientoAModificar == null) {

            Entrenamiento nuevoEntrenamiento = new Entrenamiento(cal.getTime(), horasInt, minutosInt, segundosInt, distanciaInt);

            RepositorioEntrenamientos.getInstance(Activity_Add_Entrenamiento.this).insertar(nuevoEntrenamiento);
            listaEntrenamientos.add(nuevoEntrenamiento);

        } else {
            entrenamientoAModificar.modificar(cal.getTime(), horasInt, minutosInt, segundosInt, distanciaInt);

            RepositorioEntrenamientos.getInstance(Activity_Add_Entrenamiento.this).actualizarEntrenamiento(entrenamientoAModificar);
        }
        Toast.makeText(Activity_Add_Entrenamiento.this, entrenamientoAModificar == null ? "Entrenamiento creado" : "Entrenamiento modificado", Toast.LENGTH_SHORT).show();

        setResult(RESULT_OK);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }

    public void onClickButtonCancelar(View view) {

        setResult(RESULT_CANCELED);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    private void escogerFecha() {

        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        final DatePickerDialog dpd = new DatePickerDialog(Activity_Add_Entrenamiento.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd MMMM 'de' yyyy", Locale.getDefault());
                textViewFecha.setText(formatoFecha.format(cal.getTime()));
            }
        }, year, month, day);
        dpd.show();
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