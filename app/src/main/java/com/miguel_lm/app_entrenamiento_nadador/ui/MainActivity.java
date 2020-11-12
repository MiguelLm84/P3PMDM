package com.miguel_lm.app_entrenamiento_nadador.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.miguel_lm.app_entrenamiento_nadador.R;
import com.miguel_lm.app_entrenamiento_nadador.modelo.Entrenamiento;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SeleccionarEntreno {

    private List<Entrenamiento> listaEntrenamientos;
    private AdapterEntrenamientos adapterEntrenamientos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaEntrenamientos = new ArrayList<>();

        listaEntrenamientos.add(new Entrenamiento(new Date(), 1, 50, 20, 500));
        listaEntrenamientos.add(new Entrenamiento(new Date(), 2, 15, 50, 250));
        listaEntrenamientos.add(new Entrenamiento(new Date(), 2, 20, 40, 100));

        RecyclerView recyclerViewEntrenamientos = findViewById(R.id.recyclerViewEntrenamientos);
        recyclerViewEntrenamientos.setLayoutManager(new LinearLayoutManager(this));
        adapterEntrenamientos = new AdapterEntrenamientos(listaEntrenamientos,this, this);
        recyclerViewEntrenamientos.setAdapter(adapterEntrenamientos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.accionCrearEntrenamiento) {
            accionCrearEntrenamiento(null);
        } else if (item.getItemId() == R.id.accionVerEstadisticas) {
            accionVerEstadisticas();
        } else if (item.getItemId() == R.id.accionModificar) {
            accionModificar();
        } else if (item.getItemId() == R.id.accionEliminar) {
            accionEliminar();
        }

        return super.onOptionsItemSelected(item);
    }

    public void accionCrearEntrenamiento(View view) {

        accionCrearModificarEntrenamiento(null);
    }
    private void accionCrearModificarEntrenamiento ( final Entrenamiento entrenamientoAModificar){

        final Calendar cal = Calendar.getInstance();

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dialogLayout = LayoutInflater.from(this).inflate(R.layout.dialog_entreno, null);
        builder.setView(dialogLayout);

        final TextView textViewFecha = dialogLayout.findViewById(R.id.textViewFecha);
        final EditText editTextHoras = dialogLayout.findViewById(R.id.edTxtHoras);
        final EditText editTextMinutos = dialogLayout.findViewById(R.id.edTxtMinutos);
        final EditText editTextSegundos = dialogLayout.findViewById(R.id.edTxtSegundos);
        final EditText editTextDistancia = dialogLayout.findViewById(R.id.edTxtDistancia);
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

                final DatePickerDialog dpd = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {

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

       builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

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

                    Toast.makeText(MainActivity.this, "Datos inválidos", Toast.LENGTH_SHORT).show();
                        return;
                }

                if (horasInt == 0 && minutosInt == 0 && segundosInt == 0) {
                    Toast.makeText(MainActivity.this, "No se permite hora a 0", Toast.LENGTH_SHORT).show();
                        return;
                }

                if(horasInt > 5 || minutosInt > 60 || segundosInt > 60){

                    Toast.makeText(MainActivity.this, "Datos inválidos", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(textViewFecha == null){     // todo: no permitir no escoger fecha

                    Toast.makeText(MainActivity.this, "Debe escoger una fecha", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (distanciaInt == 0) {
                    Toast.makeText(MainActivity.this, "No se permite distancia a 0", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (entrenamientoAModificar == null) {

                    Entrenamiento nuevoEntrenamiento = new Entrenamiento(cal.getTime(), horasInt, minutosInt, segundosInt, distanciaInt);
                    listaEntrenamientos.add(nuevoEntrenamiento);

                } else {
                    entrenamientoAModificar.modificar(cal.getTime(), horasInt, minutosInt, segundosInt, distanciaInt);
                }
                adapterEntrenamientos.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, entrenamientoAModificar == null ? "Entrenamiento creado" : "Entrenamiento modificado", Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void accionVerEstadisticas() {

        Entrenamiento mostrarInfoEstadisticas = null;
        for (int i=0;i<listaEntrenamientos.size();i++) {
            mostrarInfoEstadisticas = listaEntrenamientos.get(i);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_list);
        final View dialogLayout = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_estadisticas, null);
        builder.setView(dialogLayout);

        TextView tvMinPorKm=dialogLayout.findViewById(R.id.tvMinPorKm);
        TextView tvSegPorKm=dialogLayout.findViewById(R.id.tv_segundos);
        TextView tvVelMedia=dialogLayout.findViewById(R.id.tv_DistanciaTrainning);

        tvMinPorKm.setText(mostrarInfoEstadisticas.getMinutosPorKm());
        tvSegPorKm.setText(mostrarInfoEstadisticas.getSegundosPorKm());
        tvVelMedia.setText(mostrarInfoEstadisticas.toStringVelocidadMedia());

        builder.setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void accionModificar() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setIcon(R.drawable.ic_pencil);
        builder.setTitle("Modificar");

        String[] arrayEntrenamientos = new String[listaEntrenamientos.size()];
        final boolean[] entrenamientosSeleccionados = new boolean[listaEntrenamientos.size()];
        for (int i = 0; i < listaEntrenamientos.size(); i++)
            arrayEntrenamientos[i] = listaEntrenamientos.get(i).getFechaFormateada() + ", " + listaEntrenamientos.get(i).getTiempoFormateado();
        builder.setMultiChoiceItems(arrayEntrenamientos, entrenamientosSeleccionados, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i, boolean isChecked) {
                entrenamientosSeleccionados[i] = isChecked;
            }
        });

        builder.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, final int i) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setIcon(R.drawable.ic_pencil);
                builder1.setTitle("Modificar");
                builder1.setMessage("\nOpción fuera de servicio");
                builder1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(MainActivity.this, "El entrenamiento no se ha podido modificar", Toast.LENGTH_SHORT).show();
                    }
                });
                builder1.setNegativeButton("Cancelar", null);
                builder1.create().show();
            }
        });

        builder.setNegativeButton("Cancelar", null);
        builder.create().show();
    }

    private void accionEliminar() {

        AlertDialog.Builder builderElimina = new AlertDialog.Builder(MainActivity.this);
        builderElimina.setIcon(R.drawable.ic_remove_symbol);
        builderElimina.setTitle("Eliminar elementos");

        String[] arrayEntrenamientos = new String[listaEntrenamientos.size()];
        final boolean[] entrenamientosSeleccionados = new boolean[listaEntrenamientos.size()];
        for (int i=0; i < listaEntrenamientos.size(); i++)
            arrayEntrenamientos[i] = listaEntrenamientos.get(i).getFechaFormateada() + ", " + listaEntrenamientos.get(i).getTiempoFormateado();
        builderElimina.setMultiChoiceItems(arrayEntrenamientos, entrenamientosSeleccionados, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i, boolean isChecked) {
                entrenamientosSeleccionados[i] = isChecked;
            }
        });

        builderElimina.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {

                AlertDialog.Builder builderEliminar_Confirmar = new AlertDialog.Builder(MainActivity.this);
                builderEliminar_Confirmar.setIcon(R.drawable.ic_exclamation);
                builderEliminar_Confirmar.setMessage("¿Eliminar los elementos?");
                builderEliminar_Confirmar.setNegativeButton("Cancelar", null);
                builderEliminar_Confirmar.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        for (int i = listaEntrenamientos.size()-1; i>=0; i--) {
                            if (entrenamientosSeleccionados[i]) {
                                listaEntrenamientos.remove(i);
                            }
                        }
                        MainActivity.this.adapterEntrenamientos.notifyDataSetChanged();
                    }
                });
                builderEliminar_Confirmar.create().show();
            }
        });
        builderElimina.setNegativeButton("Cancelar", null);
        builderElimina.create().show();
    }

    @Override
    public void eliminarEntrenamiento(final Entrenamiento entrenamiento) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setIcon(R.drawable.ic_remove_symbol);
        builder.setTitle("Eliminar elemento");
        builder.setMessage("\n¿Está seguro de querer eliminar esto?\n\n"+entrenamiento.toStringEntreno());
        builder.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, int which) {

                listaEntrenamientos.remove(entrenamiento);
                MainActivity.this.adapterEntrenamientos.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.create().show();
    }

    @Override
    public void modificarEntrenamiento(Entrenamiento entrenamiento) {
        accionCrearModificarEntrenamiento(entrenamiento);
    }

    @Override
    public void estadisticasEntrenamiento(Entrenamiento entrenamiento) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dialogLayout = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_estadisticas, null);
        builder.setView(dialogLayout);

        TextView tvMinPorKm=dialogLayout.findViewById(R.id.tvMinPorKm);
        TextView tvSegPorKm=dialogLayout.findViewById(R.id.tv_segundos);
        TextView tvVelMedia=dialogLayout.findViewById(R.id.tv_DistanciaTrainning);

        tvMinPorKm.setText(entrenamiento.getMinutosPorKm());
        tvSegPorKm.setText(entrenamiento.getSegundosPorKm());
        tvVelMedia.setText(entrenamiento.toStringVelocidadMedia());

        builder.setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void entrenamientoPulsado(Entrenamiento entrenamiento) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        final View dialogLayout = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_info_entreno, null);
        builder.setView(dialogLayout);

        TextView infoFecha=dialogLayout.findViewById(R.id.tv_fecha_info_entreno);
        TextView infoTiempo=dialogLayout.findViewById(R.id.tv_tiempo_info_entreno);
        TextView infoDistancia=dialogLayout.findViewById(R.id.tv_distancia_info_entreno);

        infoFecha.setText(entrenamiento.getFechaFormateada());
        infoTiempo.setText(entrenamiento.getTiempoFormateado()+" h.");
        infoDistancia.setText(entrenamiento.getDistanciaMts()+" m.");

        builder.setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}