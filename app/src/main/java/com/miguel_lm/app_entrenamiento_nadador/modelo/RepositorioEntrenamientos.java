package com.miguel_lm.app_entrenamiento_nadador.modelo;

import android.content.Context;

import androidx.room.Room;

import java.util.List;



public class RepositorioEntrenamientos {

    private static RepositorioEntrenamientos repositorioEntrenamientos;

    private final DAOEntrenamiento daoEntrenamiento;

    public static RepositorioEntrenamientos getInstance(Context context) {

        if (repositorioEntrenamientos == null)
            repositorioEntrenamientos = new RepositorioEntrenamientos(context);

        return repositorioEntrenamientos;
    }

    private RepositorioEntrenamientos(Context context) {
        EntrenamientosDataBase dataBase = Room.databaseBuilder(context.getApplicationContext(), EntrenamientosDataBase.class, "entrenamientos").allowMainThreadQueries().build();
        daoEntrenamiento = dataBase.getDAOEntrenamiento();
    }

    public List<Entrenamiento> obtenerEntrenamientos() {
        return daoEntrenamiento.obtenerEntrenamientos();
    }

    public void eliminarEntrenamiento(Entrenamiento entrenamiento) {
        daoEntrenamiento.eliminarEntrenamiento(entrenamiento);
    }

    public void insertar(Entrenamiento nuevoEntrenamiento) {
        daoEntrenamiento.insertar(nuevoEntrenamiento);
    }

    public void actualizarEntrenamiento(Entrenamiento entrenamiento) {
        daoEntrenamiento.actualizarEntrenamiento(entrenamiento);
    }
}
