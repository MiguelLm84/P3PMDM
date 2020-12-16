package com.miguel_lm.app_entrenamiento_nadador.ui;

import com.miguel_lm.app_entrenamiento_nadador.modelo.Entrenamiento;

public interface SeleccionarEntreno {

    void eliminarEntrenamiento(Entrenamiento entrenamiento);
    void modificarEntrenamiento(Entrenamiento entrenamiento);
    void estadisticasEntrenamiento(Entrenamiento entrenamiento);
    void entrenamientoPulsado(Entrenamiento entrenamiento);
}
