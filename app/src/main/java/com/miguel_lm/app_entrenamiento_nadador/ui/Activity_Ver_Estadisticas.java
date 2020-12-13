package com.miguel_lm.app_entrenamiento_nadador.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.miguel_lm.app_entrenamiento_nadador.R;
import com.miguel_lm.app_entrenamiento_nadador.modelo.Entrenamiento;
import com.miguel_lm.app_entrenamiento_nadador.modelo.Estadisticas;
import com.miguel_lm.app_entrenamiento_nadador.modelo.RepositorioEntrenamientos;

import java.util.List;

public class Activity_Ver_Estadisticas extends AppCompatActivity {

    private Entrenamiento entrenamiento;
    public static final String CLAVE_ENTRENAMIENTO = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__ver__estadisticas);

        entrenamiento = (Entrenamiento) getIntent().getSerializableExtra(CLAVE_ENTRENAMIENTO);

        if(entrenamiento != null){
            estadisticasIndividualesEntreno(entrenamiento);
        } else{
            estadisticasEntrenos();
        }
    }

    public void estadisticasEntrenos(){

        List<Entrenamiento> listaEntrenamientos = RepositorioEntrenamientos.getInstance(this).obtenerEntrenamientos();

        Estadisticas estadisticas = new Estadisticas(listaEntrenamientos);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_list);
        final View dialogLayout = LayoutInflater.from(Activity_Ver_Estadisticas.this).inflate(R.layout.activity__ver__estadisticas, null);
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
        dialog.show();
    }

    public void estadisticasIndividualesEntreno(Entrenamiento entrenamiento){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dialogLayout = LayoutInflater.from(Activity_Ver_Estadisticas.this).inflate(R.layout.activity__ver__estadisticas, null);
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
        dialog.show();
    }
}