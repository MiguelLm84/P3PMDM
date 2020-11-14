package com.miguel_lm.app_entrenamiento_nadador.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.miguel_lm.app_entrenamiento_nadador.R;
import com.miguel_lm.app_entrenamiento_nadador.modelo.DatosPersonales;
import com.miguel_lm.app_entrenamiento_nadador.modelo.Entrenamiento;
import com.miguel_lm.app_entrenamiento_nadador.modelo.RepositorioEntrenamientos;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SeleccionarEntreno {

    private List<Entrenamiento> listaEntrenamientos;
    private AdapterEntrenamientos adapterEntrenamientos;
    private DatosPersonales datosPersonales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Entrenamiento> listaEntrenamientos = RepositorioEntrenamientos.getInstance(this).obtenerEntrenamientos();

        RecyclerView recyclerViewEntrenamientos = findViewById(R.id.recyclerViewEntrenamientos);
        recyclerViewEntrenamientos.setLayoutManager(new LinearLayoutManager(this));
        adapterEntrenamientos = new AdapterEntrenamientos(this, this);
        recyclerViewEntrenamientos.setAdapter(adapterEntrenamientos);


        final EditText edNombre = this.findViewById( R.id.edNombreSheredPreferences);
        final EditText edApellido = this.findViewById( R.id.edApellido1_SheredPreferences);
        final EditText edApellido2 = this.findViewById( R.id.edApellido2_SheredPreferences);
        final EditText edEdad = this.findViewById( R.id.editTextTextPersonName);
        final EditText edAltura = this.findViewById( R.id.edAltura_SheredPreferences);
        final EditText edPeso = this.findViewById( R.id.edPeso_SheredPreferences);

        edNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                MainActivity.this.datosPersonales.setNombre(edNombre.getText().toString());
            }
        });

        edApellido.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                MainActivity.this.datosPersonales.setApellido( edApellido.getText().toString() );
            }
        });

        edApellido2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                MainActivity.this.datosPersonales.setApellido2( edApellido2.getText().toString() );
            }
        });

        edEdad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                MainActivity.this.datosPersonales.setEdad(edEdad.getText().toString());
            }
        });

        edAltura.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                MainActivity.this.datosPersonales.setPeso(edAltura.getText().toString());
            }
        });

        edPeso.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                MainActivity.this.datosPersonales.setPeso(edPeso.getText().toString());
            }
        });
    }

    @Override
    public void onResume() {

        super.onResume();

        final EditText edNombre = this.findViewById( R.id.edNombreSheredPreferences);
        final EditText edApellido = this.findViewById( R.id.edApellido1_SheredPreferences);
        final EditText edApellido2 = this.findViewById( R.id.edApellido2_SheredPreferences);
        final EditText edEdad = this.findViewById( R.id.editTextTextPersonName);
        final EditText edAltura = this.findViewById( R.id.edAltura_SheredPreferences);
        final EditText edPeso = this.findViewById( R.id.edPeso_SheredPreferences);

        String nom = edNombre.getText().toString();
        String ap = edApellido.getText().toString();
        String ap2 = edApellido2.getText().toString();
        String ed = edEdad.getText().toString();
        String alt = edAltura.getText().toString();
        String pe = edPeso.getText().toString();

        this.datosPersonales = new DatosPersonales( nom, ap, ap2, ed, alt, pe );

        SharedPreferences preferencias = this.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        String nombre = preferencias.getString("Nombre", "NULL");
        edNombre.setText(nombre);

        String apellido = preferencias.getString("Primer Apellido","NULL");
        edApellido.setText(apellido);

        String apellido2 = preferencias.getString("Segundo Apellido","NULL");
        edApellido2.setText(apellido2);

        String edad = preferencias.getString("Edad","NULL");
        edEdad.setText(edad);

        String altura = preferencias.getString("Altura", "NULL");
        edPeso.setText(altura);

        String peso = preferencias.getString("Peso", "NULL");
        edPeso.setText(peso);

    }

    @Override
    public void onPause()
    {
        super.onPause();

        final EditText edNombre = this.findViewById( R.id.edNombreSheredPreferences);
        final EditText edApellido = this.findViewById( R.id.edApellido1_SheredPreferences);
        final EditText edApellido2 = this.findViewById( R.id.edApellido2_SheredPreferences);
        final EditText edEdad = this.findViewById( R.id.editTextTextPersonName);
        final EditText edAltura = this.findViewById( R.id.edAltura_SheredPreferences);
        final EditText edPeso = this.findViewById( R.id.edPeso_SheredPreferences);

        SharedPreferences preferencias = this.getSharedPreferences("preferencias",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        editor.putString("Nombre", edNombre.getText().toString());
        editor.putString("Primer Apellido", edApellido.getText().toString());
        editor.putString("Segundo Apellido", edApellido2.getText().toString());
        editor.putString("Edad", edEdad.getText().toString());
        editor.putString("Altura", edAltura.getText().toString());
        editor.putString("Peso", edPeso.getText().toString());
        editor.apply();
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

        //todo: modificar para que al pulsar la opción modificar salte al layout de modificar y no al de nuevo entrenamiento.

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
        final Button buttonAceptar = dialogLayout.findViewById(R.id.btn_Ingresar);
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

        buttonAceptar.setOnClickListener(new View.OnClickListener() {

            List<Entrenamiento> listaEntrenamientos = RepositorioEntrenamientos.getInstance(MainActivity.this).obtenerEntrenamientos();

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

                    Toast.makeText(MainActivity.this, "Datos inválidos", Toast.LENGTH_SHORT).show();
                        return;
                }

                /*if (horasInt == 0) {
                    Toast.makeText(MainActivity.this, "No se permite horas a 0", Toast.LENGTH_SHORT).show();
                        return;
                }     //todo: permitir horas a 0 o no?,si se entrena menos de una hora?

                if (minutosInt == 0) {
                    Toast.makeText(MainActivity.this, "No se permite minutos a 0", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (segundosInt == 0) {
                    Toast.makeText(MainActivity.this, "No se permite segundos a 0", Toast.LENGTH_SHORT).show();
                    return;
                }*/

                if(horasInt > 24){

                    Toast.makeText(MainActivity.this, "No se permite horas mayores de 24.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(minutosInt > 60){

                    Toast.makeText(MainActivity.this, "No se permite minutos mayores de 60.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(segundosInt > 60){

                    Toast.makeText(MainActivity.this, "No se permite segundos mayores de 60.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(textViewFecha == null){

                    Toast.makeText(MainActivity.this, "Debe escoger una fecha", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (distanciaInt == 0) {
                    Toast.makeText(MainActivity.this, "No se permite distancia a 0", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (entrenamientoAModificar == null) {

                    Entrenamiento nuevoEntrenamiento = new Entrenamiento(cal.getTime(), horasInt, minutosInt, segundosInt, distanciaInt);

                    RepositorioEntrenamientos.getInstance(MainActivity.this).insertar(nuevoEntrenamiento);
                    listaEntrenamientos.add(nuevoEntrenamiento);

                } else {
                    entrenamientoAModificar.modificar(cal.getTime(), horasInt, minutosInt, segundosInt, distanciaInt);

                    RepositorioEntrenamientos.getInstance(MainActivity.this).actualizarEntrenamiento(entrenamientoAModificar);
                }
                // Actualizar el adapter, recargará los datos desde la bd
                adapterEntrenamientos.actualizarListado();
                Toast.makeText(MainActivity.this, entrenamientoAModificar == null ? "Entrenamiento creado" : "Entrenamiento modificado", Toast.LENGTH_SHORT).show();
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

    private void accionVerEstadisticas() {

        List<Entrenamiento> listaEntrenamientos = RepositorioEntrenamientos.getInstance(this).obtenerEntrenamientos();

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

        List<Entrenamiento> listaEntrenamientos = RepositorioEntrenamientos.getInstance(this).obtenerEntrenamientos();

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

        final List<Entrenamiento> listaEntrenamientos = RepositorioEntrenamientos.getInstance(this).obtenerEntrenamientos();

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

            List<Entrenamiento> listaEntrenamientos = RepositorioEntrenamientos.getInstance(MainActivity.this).obtenerEntrenamientos();

            @Override
            public void onClick(final DialogInterface dialog, int which) {

                AlertDialog.Builder builderEliminar_Confirmar = new AlertDialog.Builder(MainActivity.this);
                builderEliminar_Confirmar.setIcon(R.drawable.ic_exclamation);
                builderEliminar_Confirmar.setMessage("¿Eliminar los elementos?");
                builderEliminar_Confirmar.setNegativeButton("Cancelar", null);
                builderEliminar_Confirmar.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        for (int i = 0 ; i<listaEntrenamientos.size() ; i++)
                            if (entrenamientosSeleccionados[i])
                                RepositorioEntrenamientos.getInstance(MainActivity.this).eliminarEntrenamiento(listaEntrenamientos.get(i));

                        for (int i = listaEntrenamientos.size()-1; i>=0; i--) {
                            if (entrenamientosSeleccionados[i]) {
                                listaEntrenamientos.remove(i);
                            }
                        }
                        MainActivity.this.adapterEntrenamientos.notifyDataSetChanged();
                        // Actualizar el adapter, recargará los datos desde la bd
                        adapterEntrenamientos.actualizarListado();
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

            List<Entrenamiento> listaEntrenamientos = RepositorioEntrenamientos.getInstance(MainActivity.this).obtenerEntrenamientos();

            @Override
            public void onClick(final DialogInterface dialog, int which) {

                RepositorioEntrenamientos.getInstance(MainActivity.this).eliminarEntrenamiento(entrenamiento);
                listaEntrenamientos.remove(entrenamiento);

                // Actualizar el adapter, recargará los datos desde la bd
                adapterEntrenamientos.actualizarListado();
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