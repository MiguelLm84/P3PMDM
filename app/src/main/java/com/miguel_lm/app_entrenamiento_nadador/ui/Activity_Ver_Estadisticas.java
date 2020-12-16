package com.miguel_lm.app_entrenamiento_nadador.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.miguel_lm.app_entrenamiento_nadador.R;
import com.miguel_lm.app_entrenamiento_nadador.modelo.Entrenamiento;
import com.miguel_lm.app_entrenamiento_nadador.modelo.Estadisticas;
import com.miguel_lm.app_entrenamiento_nadador.modelo.RepositorioEntrenamientos;

import java.util.List;

public class Activity_Ver_Estadisticas extends AppCompatActivity {

    private Entrenamiento entrenamiento;
    public static final String CLAVE_ENTRENAMIENTO = "1234";
    private long tiempoParaSalir = 0;

    //TextView tituloInfoFecha;
    //TextView tituloInfoTiempo;
    private TextView infoFecha;
    private TextView infoTiempo;
    private TextView tv_Km;
    private TextView tv_mediaMinPorKm;
    private TextView tv_velocidadMed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__ver__estadisticas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_piscina);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        TextView tituloInfoFecha = findViewById(R.id.tv_fecha_titulo_infoEstadisticas);
        TextView tituloInfoTiempo = findViewById(R.id.tv_tiempo_titulo_infoEstadisticas);
        infoFecha = findViewById(R.id.tv_fecha_info_entrenoEstadisticas);
        infoTiempo = findViewById(R.id.tv_tiempo_info_entrenoEstadisticas);
        tv_Km = findViewById(R.id.tv_KmNadados);
        tv_mediaMinPorKm = findViewById(R.id.tv_mediaMinPorKm);
        tv_velocidadMed = findViewById(R.id.tv_velocidadMedia);

        entrenamiento = (Entrenamiento) getIntent().getSerializableExtra(CLAVE_ENTRENAMIENTO);

        if (entrenamiento != null) {
            estadisticasIndividualesEntreno(entrenamiento);
        } else {
            estadisticasEntrenos();
            tituloInfoFecha.setVisibility(View.VISIBLE);
            tituloInfoFecha.setVisibility(View.GONE);

            infoFecha.setVisibility(View.VISIBLE);
            infoFecha.setVisibility(View.GONE);

            tituloInfoTiempo.setVisibility(View.VISIBLE);
            tituloInfoTiempo.setVisibility(View.GONE);

            infoTiempo.setVisibility(View.VISIBLE);
            infoTiempo.setVisibility(View.GONE);
        }
    }

    public void cerrarActivity(View view) {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void estadisticasEntrenos() {

        List<Entrenamiento> listaEntrenamientos = RepositorioEntrenamientos.getInstance(this).obtenerEntrenamientos();

        Estadisticas estadisticas = new Estadisticas(listaEntrenamientos);

        tv_Km.setText(estadisticas.getDistanciaTotalKms());
        tv_mediaMinPorKm.setText(estadisticas.getMediaPorMinutosKm());
        tv_velocidadMed.setText(estadisticas.getVelocidadMediaKmH());

    }

    public void estadisticasIndividualesEntreno(Entrenamiento entrenamiento) {

        infoFecha.setText(entrenamiento.getFechaFormateada());
        infoTiempo.setText(entrenamiento.getTiempoFormateado()+" h.");
        tv_Km.setText(entrenamiento.getkmNadados());
        tv_mediaMinPorKm.setText(entrenamiento.getMediaMinPorKm());
        tv_velocidadMed.setText(entrenamiento.toStringVelocidadMedia());
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