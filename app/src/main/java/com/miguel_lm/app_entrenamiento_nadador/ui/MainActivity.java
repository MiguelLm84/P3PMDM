package com.miguel_lm.app_entrenamiento_nadador.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StatFs;
import android.text.InputType;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.miguel_lm.app_entrenamiento_nadador.R;
import com.miguel_lm.app_entrenamiento_nadador.modelo.Entrenamiento;
import com.miguel_lm.app_entrenamiento_nadador.modelo.Estadisticas;
import com.miguel_lm.app_entrenamiento_nadador.modelo.RepositorioEntrenamientos;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.miguel_lm.app_entrenamiento_nadador.ui.Activity_Add_Entrenamiento.CLAVE_ENTRENAMIENTO;

public class MainActivity extends AppCompatActivity implements SeleccionarEntreno {

    private long tiempoParaSalir = 0;
    private Entrenamiento entrenamientoAmodificar;
    private AdapterEntrenamientos adapterEntrenamientos;

    private EditText edNombre;
    private EditText edApellido;
    private EditText edApellido2;
    private EditText edEdad;
    private EditText edAltura;
    private EditText edPeso;

    private static final String PREF_FICHERO = "preferencias";
    private static final String PREF_NOMBRE = "Nombre";
    private static final String PREF_APELLIDO1 = "Primer Apellido";
    private static final String PREF_APELLIDO2 = "Segundo Apellido";
    private static final String PREF_EDIT = "Edad";
    private static final String PREF_ALTURA = "Altura";
    private static final String PREF_PESO = "Peso";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewEntrenamientos = findViewById(R.id.recyclerViewEntrenamientos);
        recyclerViewEntrenamientos.setLayoutManager(new LinearLayoutManager(this));
        adapterEntrenamientos = new AdapterEntrenamientos(this, this);
        recyclerViewEntrenamientos.setAdapter(adapterEntrenamientos);

        edNombre = this.findViewById( R.id.edNombre_SheredPreferences);
        edApellido = this.findViewById( R.id.edApellido1_SheredPreferences);
        edApellido2 = this.findViewById( R.id.edApellido2_SheredPreferences);
        edEdad = this.findViewById( R.id.edEdad_SheredPreferences);
        edAltura = this.findViewById( R.id.edAltura_SheredPreferences);
        edPeso = this.findViewById( R.id.edPeso_SheredPreferences);

    }

    @Override
    public void onResume() {

        super.onResume();

        leerDatosDesdePreferencias();
        espacioDisponibleEnMemoriaInterna();
        generarFichero();
    }

    @Override
    public void onPause()
    {
        super.onPause();

        escribirDatosEnPreferencias();
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

    private void escribirDatosEnPreferencias() {

        SharedPreferences preferencias = this.getSharedPreferences(PREF_FICHERO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        editor.putString(PREF_NOMBRE, edNombre.getText().toString());
        editor.putString(PREF_APELLIDO1, edApellido.getText().toString());
        editor.putString(PREF_APELLIDO2, edApellido2.getText().toString());
        editor.putString(PREF_EDIT, edEdad.getText().toString());
        editor.putString(PREF_ALTURA, edAltura.getText().toString());
        editor.putString(PREF_PESO, edPeso.getText().toString());
        editor.apply();
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

    private void generarFichero() {

        String nombreDirectorio = "Estadisticas";
        String nombreFichero = "infoEstadisticas.txt";
        File directorio = new File(MainActivity.this.getFilesDir(), nombreDirectorio);
        if (!directorio.exists()) {
            directorio.mkdir();
        }
        try {
            File fichero = new File(directorio, nombreFichero);
            FileWriter writer = new FileWriter(fichero);
            writer.append(listarEstadisticasGenerales());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            Toast.makeText(this,"Error al intentar escribir en el archivo " + nombreFichero,Toast.LENGTH_SHORT).show();
        }

    }

    public void accionCrearEntrenamiento(View view) {

        accionCrearModificarEntrenamiento(null);
    }
    private void accionCrearModificarEntrenamiento ( final Entrenamiento entrenamientoAModificar){

        Intent intent = new Intent(this, Activity_Add_Entrenamiento.class);
        intent.putExtra(CLAVE_ENTRENAMIENTO, entrenamientoAModificar);
        startActivity(intent);

        /*final Calendar cal = Calendar.getInstance();

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

                if (horasInt == 0 && minutosInt == 0 && segundosInt == 0) {
                    Toast.makeText(MainActivity.this, "No se permiten todos los valores a 0", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(horasInt > 24){

                    Toast.makeText(MainActivity.this, "No se permite horas mayores de 24.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(minutosInt > 59){

                    Toast.makeText(MainActivity.this, "No se permite minutos mayores de 60.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(segundosInt > 59){

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

        dialog.show();*/
    }

    private void accionVerEstadisticas() {

        Intent intent = new Intent(this, Activity_Ver_Estadisticas.class);
        startActivity(intent);


       /* List<Entrenamiento> listaEntrenamientos = RepositorioEntrenamientos.getInstance(this).obtenerEntrenamientos();

        Estadisticas estadisticas = new Estadisticas(listaEntrenamientos);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_list);
        final View dialogLayout = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_estadisticas, null);
        builder.setView(dialogLayout);
        final  AlertDialog dialog = builder.create();

        TextView tv_Km=dialogLayout.findViewById(R.id.tv_KmNadados);
        TextView tv_mediaMinPorKm=dialogLayout.findViewById(R.id.tv_mediaMinPorKm);
        TextView tv_velocidadMed=dialogLayout.findViewById(R.id.tv_velocidadMedia);
        Button btnAceptar=dialogLayout.findViewById(R.id.btn_Eliminar);

        tv_Km.setText(estadisticas.getDistanciaTotalKms());
        tv_mediaMinPorKm.setText(estadisticas.getMediaPorMinutosKm());
        tv_velocidadMed.setText(estadisticas.getVelocidadMediaKmH());

        btnAceptar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();*/
    }

    private void accionModificar() {

        final List<Entrenamiento> listaEntrenamientos = RepositorioEntrenamientos.getInstance(this).obtenerEntrenamientos();

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setIcon(R.drawable.ic_pencil);
        builder.setTitle("Modificar");

        String[] arrayEntrenamientos = new String[listaEntrenamientos.size()];
        final boolean[] entrenamientosSeleccionados = new boolean[listaEntrenamientos.size()];
        for (int i = 0; i < listaEntrenamientos.size(); i++)
            arrayEntrenamientos[i] = listaEntrenamientos.get(i).getFechaFormateada() + ", " + listaEntrenamientos.get(i).getTiempoFormateado();
        builder.setSingleChoiceItems(arrayEntrenamientos, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int posicionElementoSeleccionado) {
                entrenamientoAmodificar = listaEntrenamientos.get(posicionElementoSeleccionado);
            }
        });
        builder.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, final int i) {

                if (entrenamientoAmodificar == null) {
                    Toast.makeText(MainActivity.this, "Debe escoger una tarea", Toast.LENGTH_SHORT).show();
                }
                else {
                    accionCrearModificarEntrenamiento(entrenamientoAmodificar);
                }
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.create().show();
    }

    private void accionEliminar() {

        final List<Entrenamiento> listaEntrenamientos = RepositorioEntrenamientos.getInstance(this).obtenerEntrenamientos();

        AlertDialog.Builder builderElimina = new AlertDialog.Builder(MainActivity.this);
        builderElimina.setIcon(R.drawable.ic_remove_symbol);
        builderElimina.setTitle("Eliminar");

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
                        adapterEntrenamientos.actualizarListado();
                        Toast.makeText(MainActivity.this, "Entrenamiento eliminado", Toast.LENGTH_SHORT).show();
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
        final View dialogLayout = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_eliminar, null);
        builder.setView(dialogLayout);
        final AlertDialog dialog = builder.create();

        final TextView tvFecha = dialogLayout.findViewById(R.id.textViewFecha2);
        final EditText etHoras = dialogLayout.findViewById(R.id.edTxtHoras);
        final EditText etMinutos = dialogLayout.findViewById(R.id.edTxtMinutos);
        final EditText etSegundos = dialogLayout.findViewById(R.id.edTxtSegundos);
        final EditText etDistancia = dialogLayout.findViewById(R.id.edTxtDistancia2);
        final Button btnAceptar = dialogLayout.findViewById(R.id.btn_Eliminar);
        final Button btnCancelar = dialogLayout.findViewById(R.id.btn_Cancel);

        tvFecha.setText(entrenamiento.getFechaFormateada());
        etHoras.setText(String.valueOf(entrenamiento.getHoras()));
        etMinutos.setText(String.valueOf(entrenamiento.getMinutos()));
        etSegundos.setText(String.valueOf(entrenamiento.getSegundos()));
        etDistancia.setText(String.valueOf(entrenamiento.getDistanciaMts()));

        btnAceptar.setOnClickListener(new View.OnClickListener() {

            List<Entrenamiento> listaEntrenamientos = RepositorioEntrenamientos.getInstance(MainActivity.this).obtenerEntrenamientos();

            @Override
            public void onClick(View v) {
                RepositorioEntrenamientos.getInstance(MainActivity.this).eliminarEntrenamiento(entrenamiento);
                listaEntrenamientos.remove(entrenamiento);

                adapterEntrenamientos.actualizarListado();
                MainActivity.this.adapterEntrenamientos.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Entrenamiento eliminado", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void modificarEntrenamiento(Entrenamiento entrenamiento) {
        accionCrearModificarEntrenamiento(entrenamiento);
    }

    @Override
    public void estadisticasEntrenamiento(Entrenamiento entrenamiento) {

        Intent intent = new Intent(this, Activity_Ver_Estadisticas.class);
        intent.putExtra(CLAVE_ENTRENAMIENTO, entrenamiento);
        startActivity(intent);


        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dialogLayout = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_estadisticas, null);
        builder.setView(dialogLayout);
        final AlertDialog dialog = builder.create();

        TextView tvKmNadados=dialogLayout.findViewById(R.id.tv_KmNadados);
        TextView tvMediaMinPorKm=dialogLayout.findViewById(R.id.tv_mediaMinPorKm);
        TextView tvVelMed=dialogLayout.findViewById(R.id.tv_velocidadMedia);
        Button btnAceptar=dialogLayout.findViewById(R.id.btn_Eliminar);

        tvKmNadados.setText(entrenamiento.getkmNadados());
        tvMediaMinPorKm.setText(entrenamiento.getMediaMinPorKm());
        tvVelMed.setText(entrenamiento.toStringVelocidadMedia());

        btnAceptar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();*/
    }

    @Override
    public void entrenamientoPulsado(Entrenamiento entrenamiento) {

        Intent intent = new Intent(this, Activity_Info_Entrenamiento.class);
        intent.putExtra(CLAVE_ENTRENAMIENTO, entrenamiento);
        startActivity(intent);

        /*android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        final View dialogLayout = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_info_entreno, null);
        builder.setView(dialogLayout);
        final AlertDialog dialog = builder.create();

        TextView infoFecha=dialogLayout.findViewById(R.id.tv_fecha_info_entreno);
        TextView infoTiempo=dialogLayout.findViewById(R.id.tv_tiempo_info_entreno);
        TextView infoDistancia=dialogLayout.findViewById(R.id.tv_distancia_info_entreno);
        TextView infoMinPorKm=dialogLayout.findViewById(R.id.tv_minPorKm_Info);
        TextView infoVelMed=dialogLayout.findViewById(R.id.tv_velMed_info_entrno);
        Button bt_Aceptar=dialogLayout.findViewById(R.id.btn_Eliminar);

        infoFecha.setText(entrenamiento.getFechaFormateada());
        infoTiempo.setText(entrenamiento.getTiempoFormateado()+" h.");
        infoDistancia.setText(entrenamiento.getDistanciaMts()+" m.");
        infoMinPorKm.setText(entrenamiento.getMinutosPorKm());
        infoVelMed.setText(entrenamiento.toStringVelocidadMedia());

        bt_Aceptar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();*/
    }

    public String espacioDisponibleEnMemoriaInterna(){

        String espacioDisponible = null;
        File internalStorageFile=getFilesDir();
        long espacioDisponibleBytes = new StatFs(internalStorageFile.getPath()).getAvailableBytes();
        espacioDisponible = Formatter.formatShortFileSize(getApplicationContext(), espacioDisponibleBytes);

        return espacioDisponible;
    }

    public String listarEstadisticasGenerales() {

        List<Entrenamiento> listaEntrenamientos = RepositorioEntrenamientos.getInstance(this).obtenerEntrenamientos();
        Estadisticas estadisticas = new Estadisticas(listaEntrenamientos);

        return "\nESTADÍSTICAS GENERALES\n\n·DISTANCIA TOTAL (KM): " + estadisticas.getDistanciaTotalKms() +
                "\n·MEDIA MIN/KM: " + estadisticas.getMediaPorMinutosKm() +
                "\n·VELOCIDAD MEDIA (KM/H): " + estadisticas.getVelocidadMediaKmH() + "\n\n";

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
        }
    }
}