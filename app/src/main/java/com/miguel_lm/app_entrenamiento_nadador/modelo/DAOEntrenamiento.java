package com.miguel_lm.app_entrenamiento_nadador.modelo;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DAOEntrenamiento {

    @Delete
    void eliminarEntrenamiento(Entrenamiento entrenamiento);

    @Insert
    void insertar(Entrenamiento nuevoEntrenamiento);

    @Query("SELECT * FROM Entrenamientos")
    List<Entrenamiento> obtenerEntrenamientos();

    @Update
    void actualizarEntrenamiento(Entrenamiento entrenamiento);

}
