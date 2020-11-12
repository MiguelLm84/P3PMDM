package com.miguel_lm.app_entrenamiento_nadador.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miguel_lm.app_entrenamiento_nadador.R;
import com.miguel_lm.app_entrenamiento_nadador.modelo.*;

import java.util.List;

public class AdapterEntrenamientos extends RecyclerView.Adapter<ViewHolderEntrenamiento> {

    private List<Entrenamiento> listEntrenamientos;
    private Context context;
    private SeleccionarEntreno seleccionarEntrenamiento;

    public AdapterEntrenamientos(List<Entrenamiento> listEntrenamientos,final Context context, SeleccionarEntreno seleccionarEntrenamient) {
        this.listEntrenamientos = listEntrenamientos;
        this.context = context;
        this.seleccionarEntrenamiento = seleccionarEntrenamient;
    }

    @NonNull
    @Override
    public ViewHolderEntrenamiento onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.entrenamiento, parent, false);
        return new ViewHolderEntrenamiento(v, seleccionarEntrenamiento);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderEntrenamiento holder, int position) {

        Entrenamiento entrenamientoAPintar = listEntrenamientos.get(position);
        holder.mostrarEntrenamiento(entrenamientoAPintar,context);
    }

    @Override
    public int getItemCount() {

        return listEntrenamientos.size();
    }
}
