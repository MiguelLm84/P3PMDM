package com.miguel_lm.app_entrenamiento_nadador.modelo;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Entrenamiento.class}, version = 1, exportSchema = false)
public abstract class EntrenamientosDataBase extends RoomDatabase {
    public abstract DAOEntrenamiento getDAOEntrenamiento();

}
