package com.miguel_lm.app_entrenamiento_nadador.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StatFs;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import java.util.List;

import static com.miguel_lm.app_entrenamiento_nadador.ui.Activity_Add_Entrenamiento.CLAVE_ENTRENAMIENTO;
import static com.miguel_lm.app_entrenamiento_nadador.ui.Activity_Shered_Preferences.PREF_ALTURA;
import static com.miguel_lm.app_entrenamiento_nadador.ui.Activity_Shered_Preferences.PREF_APELLIDO1;
import static com.miguel_lm.app_entrenamiento_nadador.ui.Activity_Shered_Preferences.PREF_APELLIDO2;
import static com.miguel_lm.app_entrenamiento_nadador.ui.Activity_Shered_Preferences.PREF_EDIT;
import static com.miguel_lm.app_entrenamiento_nadador.ui.Activity_Shered_Preferences.PREF_FICHERO;
import static com.miguel_lm.app_entrenamiento_nadador.ui.Activity_Shered_Preferences.PREF_NOMBRE;
import static com.miguel_lm.app_entrenamiento_nadador.ui.Activity_Shered_Preferences.PREF_PESO;

public class MainActivity extends AppCompatActivity implements SeleccionarEntreno {

    private final int REQUEST_CODE_ENTRENAMIENTOS = 1;
    private final int REQUEST_CODE_DATOS_PERSONALES = 2;
    private long tiempoParaSalir = 0;
    private Entrenamiento entrenamientoAmodificar;
    private AdapterEntrenamientos adapterEntrenamientos;

    private TextView tvNombre;
    private TextView tvApellido;
    private TextView tvApellido2;
    private TextView tvEdad;
    private TextView tvAltura;
    private TextView tvPeso;
    private ImageButton btn_registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_piscina);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        RecyclerView recyclerViewEntrenamientos = findViewById(R.id.recyclerViewEntrenamientos);
        recyclerViewEntrenamientos.setLayoutManager(new LinearLayoutManager(this));
        adapterEntrenamientos = new AdapterEntrenamientos(this, this);
        recyclerViewEntrenamientos.setAdapter(adapterEntrenamientos);

        tvNombre = this.findViewById( R.id.edNombre_SheredPreferences);
        tvApellido = this.findViewById( R.id.edApellido1_SheredPreferences);
        tvApellido2 = this.findViewById( R.id.edApellido2_SheredPreferences);
        tvEdad = this.findViewById( R.id.edEdad_SheredPreferences);
        tvAltura = this.findViewById( R.id.edAltura_SheredPreferences);
        tvPeso = this.findViewById( R.id.edPeso_SheredPreferences);
        btn_registrar = this.findViewById( R.id.btn_registrar);

        leerDatosDesdePreferencias();

    }

    @Override
    public void onResume() {

        super.onResume();

        espacioDisponibleEnMemoriaInterna();
        generarFichero();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ENTRENAMIENTOS   &&   resultCode == Activity.RESULT_OK) {

            adapterEntrenamientos.actualizarListado();

        } else if (requestCode == REQUEST_CODE_DATOS_PERSONALES   &&   resultCode == RESULT_OK) {

            leerDatosDesdePreferencias();
        }
    }

    private void leerDatosDesdePreferencias() {

        SharedPreferences preferencias = this.getSharedPreferences(PREF_FICHERO, Context.MODE_PRIVATE);
        String nombre = preferencias.getString(PREF_NOMBRE, "");
        tvNombre.setText(nombre);

        String apellido = preferencias.getString(PREF_APELLIDO1,"");
        tvApellido.setText(apellido);

        String apellido2 = preferencias.getString(PREF_APELLIDO2,"");
        tvApellido2.setText(apellido2);

        String edad = preferencias.getString(PREF_EDIT,"");
        tvEdad.setText(edad);

        String altura = preferencias.getString(PREF_ALTURA, "");
        tvAltura.setText(altura);

        String peso = preferencias.getString(PREF_PESO, "");
        tvPeso.setText(peso);

        btn_registrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Activity_Shered_Preferences.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
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
        startActivityForResult(intent, REQUEST_CODE_ENTRENAMIENTOS);

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void accionVerEstadisticas() {

        Intent intent = new Intent(this, Activity_Ver_Estadisticas.class);
        startActivity(intent);

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
    }

    @Override
    public void entrenamientoPulsado(Entrenamiento entrenamiento) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
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
        dialog.show();
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
        } else if (item.getItemId() == R.id.accionDatosPersonales) {
            accionDatosPersonales();
        }


        return super.onOptionsItemSelected(item);
    }

    private void accionDatosPersonales() {

        startActivityForResult(new Intent(this, Activity_Shered_Preferences.class), REQUEST_CODE_DATOS_PERSONALES);

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