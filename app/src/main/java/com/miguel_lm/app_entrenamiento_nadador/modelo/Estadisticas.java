package com.miguel_lm.app_entrenamiento_nadador.modelo;

import java.text.DecimalFormat;
import java.util.List;

public class Estadisticas {

    private float distanciaTotalKms;
    private float mediaPorMinutosKm;
    private float velocidadMediaKmH;

    private final DecimalFormat formateoDecimal = new DecimalFormat("#0.00");

    public Estadisticas(List<Entrenamiento> listadoEntrenamientos) {

        distanciaTotalKms = 0;
        mediaPorMinutosKm = 0;
        velocidadMediaKmH = 0;
        float tiempoEnMinutosTotal = 0;
        float distanciaTotalMts = 0;
        for (Entrenamiento entrenamiento : listadoEntrenamientos) {


            distanciaTotalMts += entrenamiento.getDistanciaMts();
            tiempoEnMinutosTotal += entrenamiento.getTiempoEnMinutos();

        }

        distanciaTotalKms = distanciaTotalMts / 1000;
        mediaPorMinutosKm = tiempoEnMinutosTotal / distanciaTotalKms;
        velocidadMediaKmH = distanciaTotalKms / (tiempoEnMinutosTotal / 60);
    }

    public String getDistanciaTotalKms() {

        return formateoDecimal.format(distanciaTotalKms)+" Kms.";

    }

    public String getMediaPorMinutosKm() {

        return formateoDecimal.format(mediaPorMinutosKm)+" min/Km";

    }

    public String getVelocidadMediaKmH() {

        return formateoDecimal.format(velocidadMediaKmH)+" km/h";
    }
}
