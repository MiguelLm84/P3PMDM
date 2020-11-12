package com.miguel_lm.app_entrenamiento_nadador.modelo;

import android.content.Context;

import androidx.room.Room;

import java.util.List;


/**
 * Clase que usa la BD y se comunica con la app
 */
public class RepositorioEntrenamientos {

    // Se creará una sola instancia del repositorio, que se almacena aquí
    private static RepositorioEntrenamientos repositorioEntrenamientos;

    // Guardar el dao para usarlo para acceder a la BD
    private DAOEntrenamiento daoEntrenamiento;

    /**
     * Patrón SINGLETON, permite usar una sola instancia del objeto y evita tener que inicializarlo cada vez que se crea (solo se crea una vez)
     */
    public static RepositorioEntrenamientos getInstance(Context context) {

        if (repositorioEntrenamientos == null)
            repositorioEntrenamientos = new RepositorioEntrenamientos(context);

        return repositorioEntrenamientos;
    }

    /**
     * Constructor del repositorio, crea la BD y obtiene el DAO
     */
    private RepositorioEntrenamientos(Context context) {
        EntrenamientosDataBase dataBase = Room.databaseBuilder(context.getApplicationContext(), EntrenamientosDataBase.class, "entrenamientos").build();
        daoEntrenamiento = dataBase.getDAOEntrenamiento();
    }


    ////////////////////////////////////////////////////////////////////
    // MÉTODOS QUE USAN EL DAO PARA INTERACTUAR CON LA BD
    ////////////////////////////////////////////////////////////////////

    public List<Entrenamiento> obtenerEntrenamientos() {
        return daoEntrenamiento.obtenerEntrenamientos();
    }

    public void eliminarEntrenamiento() {
        daoEntrenamiento.eliminarEntrenamiento();
    }

    public void insertar(Entrenamiento nuevoEntrenamiento) {
        daoEntrenamiento.insertar(nuevoEntrenamiento);
    }

    public void actualizarEntrenamiento(Entrenamiento entrenamiento) {
        daoEntrenamiento.actualizarEntrenamiento(entrenamiento);
    }


}


//   RepositorioEntrenamientos objeto = new RepositorioEntrenamientos(context);
// objeto.


//   RepositorioEntrenamientos objeto = RepositorioEntrenamiento.getInstance(context);
//   RepositorioEntrenamientos objeto = RepositorioEntrenamiento.getInstance(context);
//   RepositorioEntrenamientos objeto = RepositorioEntrenamiento.getInstance(context);
//   RepositorioEntrenamientos objeto = RepositorioEntrenamiento.getInstance(context);