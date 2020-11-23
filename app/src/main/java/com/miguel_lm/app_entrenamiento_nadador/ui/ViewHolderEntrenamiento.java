package com.miguel_lm.app_entrenamiento_nadador.ui;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import com.miguel_lm.app_entrenamiento_nadador.modelo.Entrenamiento;
import com.miguel_lm.app_entrenamiento_nadador.R;

public class ViewHolderEntrenamiento extends RecyclerView.ViewHolder {

    private TextView textViewFecha;
    private TextView textViewTiempo;
    private TextView textViewDistancia;
    private final LinearLayout linearLayoutItemEntrenamiento;

    private SeleccionarEntreno seleccionarEntrenamiento;

    public ViewHolderEntrenamiento(@NonNull View itemView, SeleccionarEntreno seleccionarEntrenamiento) {
        super(itemView);

        this.seleccionarEntrenamiento = seleccionarEntrenamiento;

        textViewFecha = itemView.findViewById(R.id.textViewFecha);
        textViewTiempo = itemView.findViewById(R.id.textViewTiempo);
        textViewDistancia = itemView.findViewById(R.id.textViewDistancia);
        linearLayoutItemEntrenamiento = itemView.findViewById(R.id.linearLayoutItemEntrenamiento);
    }

    public void mostrarEntrenamiento(final Entrenamiento entrenamiento, final Context context) {

        textViewFecha.setText(entrenamiento.getFechaFormateada());
        textViewTiempo.setText(entrenamiento.getTiempoFormateado());
        textViewDistancia.setText(entrenamiento.getDistanciaMts() + " mts.");

        linearLayoutItemEntrenamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                seleccionarEntrenamiento.entrenamientoPulsado(entrenamiento);

            }
        });

        linearLayoutItemEntrenamiento.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final PopupMenu popupMenu = new PopupMenu(context, linearLayoutItemEntrenamiento);
                popupMenu.getMenuInflater().inflate(R.menu.menu_contextual, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.accionVerEstadisticas:
                                seleccionarEntrenamiento.estadisticasEntrenamiento(entrenamiento);
                                break;
                            case R.id.accionModificar:
                                seleccionarEntrenamiento.modificarEntrenamiento(entrenamiento);
                                break;
                            case R.id.accionEliminar:
                                seleccionarEntrenamiento.eliminarEntrenamiento(entrenamiento);
                                break;
                        }

                        return false;
                    }
                });
                popupMenu.show();

                return false;
            }
        });
    }
}
