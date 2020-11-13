package com.miguel_lm.app_entrenamiento_nadador.modelo;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Entrenamiento.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class EntrenamientosDataBase extends RoomDatabase {
    public abstract DAOEntrenamiento getDAOEntrenamiento();

}
