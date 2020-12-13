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

public class Activity_Info_Entrenamiento extends AppCompatActivity {

    public static final String CLAVE_ENTRENAMIENTO = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__info__entrenamiento);

        Entrenamiento entrenamiento = (Entrenamiento) getIntent().getSerializableExtra(CLAVE_ENTRENAMIENTO);
        infoEntreno(entrenamiento);
    }

    public void infoEntreno(Entrenamiento entrenamiento){

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        final View dialogLayout = LayoutInflater.from(Activity_Info_Entrenamiento.this).inflate(R.layout.activity__info__entrenamiento, null);
        builder.setView(dialogLayout);
        final AlertDialog dialog = builder.create();

        TextView infoFecha=dialogLayout.findViewById(R.id.tv_fecha_info_entreno);
        TextView infoTiempo=dialogLayout.findViewById(R.id.tv_tiempo_info_entreno);
        TextView infoDistancia=dialogLayout.findViewById(R.id.tv_distancia_info_entreno);
        TextView infoMinPorKm=dialogLayout.findViewById(R.id.tv_minPorKm_Info);
        TextView infoVelMed=dialogLayout.findViewById(R.id.tv_velMed_info_entrno);
        Button bt_Aceptar=dialogLayout.findViewById(R.id.btn_Eliminar);

        /*TextView infoFecha = findViewById(R.id.tv_fecha_info_entreno);
        TextView infoTiempo = findViewById(R.id.tv_tiempo_info_entreno);
        TextView infoDistancia = findViewById(R.id.tv_distancia_info_entreno);
        TextView infoMinPorKm = findViewById(R.id.tv_minPorKm_Info);
        TextView infoVelMed = findViewById(R.id.tv_velMed_info_entrno);
        Button bt_Aceptar = findViewById(R.id.btn_Eliminar);*/


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
}