package com.miguel_lm.app_entrenamiento_nadador.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.miguel_lm.app_entrenamiento_nadador.R;
import com.miguel_lm.app_entrenamiento_nadador.modelo.Entrenamiento;
import com.miguel_lm.app_entrenamiento_nadador.modelo.RepositorioEntrenamientos;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Activity_Add_Entrenamiento extends AppCompatActivity {

    private AdapterEntrenamientos adapterEntrenamientos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__add__entrenamiento);
    }

    public void addEntrenamiento(final Entrenamiento entrenamientoAModificar){

        final Calendar cal = Calendar.getInstance();

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dialogLayout = LayoutInflater.from(this).inflate(R.layout.dialog_entreno, null);
        builder.setView(dialogLayout);
        final AlertDialog dialog = builder.create();

        final TextView textViewFecha = dialogLayout.findViewById(R.id.textViewFecha);
        final EditText editTextHoras = dialogLayout.findViewById(R.id.edTxtHoras);
        final EditText editTextMinutos = dialogLayout.findViewById(R.id.edTxtMinutos);
        final EditText editTextSegundos = dialogLayout.findViewById(R.id.edTxtSegundos);
        final EditText editTextDistancia = dialogLayout.findViewById(R.id.edTxtDistancia);
        final Button buttonAceptar = dialogLayout.findViewById(R.id.btn_Eliminar);
        final Button buttonCancelar = dialogLayout.findViewById(R.id.btn_Cancel);
        textViewFecha.setInputType(InputType.TYPE_NULL);

        if (entrenamientoAModificar != null) {
            editTextHoras.setText(String.valueOf(entrenamientoAModificar.getHoras()));
            editTextMinutos.setText(String.valueOf(entrenamientoAModificar.getMinutos()));
            editTextSegundos.setText(String.valueOf(entrenamientoAModificar.getSegundos()));
            editTextDistancia.setText(String.valueOf(entrenamientoAModificar.getDistanciaMts()));
            textViewFecha.setText(entrenamientoAModificar.getFechaFormateada());

            cal.setTime(entrenamientoAModificar.getFecha());
        }

        textViewFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        });

        buttonAceptar.setOnClickListener(new View.OnClickListener() {

            final List<Entrenamiento> listaEntrenamientos = RepositorioEntrenamientos.getInstance(Activity_Add_Entrenamiento.this).obtenerEntrenamientos();

            @Override
            public void onClick(View v) {

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

                if(horasInt > 24){

                    Toast.makeText(Activity_Add_Entrenamiento.this, "No se permite horas mayores de 24.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(minutosInt > 59){

                    Toast.makeText(Activity_Add_Entrenamiento.this, "No se permite minutos mayores de 60.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(segundosInt > 59){

                    Toast.makeText(Activity_Add_Entrenamiento.this, "No se permite segundos mayores de 60.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(textViewFecha == null){

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
                adapterEntrenamientos.actualizarListado();
                Toast.makeText(Activity_Add_Entrenamiento.this, entrenamientoAModificar == null ? "Entrenamiento creado" : "Entrenamiento modificado", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        buttonCancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}