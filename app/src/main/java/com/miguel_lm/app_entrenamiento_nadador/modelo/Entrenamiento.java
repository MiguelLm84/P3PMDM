package com.miguel_lm.app_entrenamiento_nadador.modelo;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class Entrenamiento {

    private Date fecha;
    int horas;
    int minutos;
    int segundos;
    private int distanciaMts;

    private float tiempoEnMinutos;
    private int tiempoEnSegundos;
    private float minutosPorKm;
    private float segundosPorKm;

    public Entrenamiento(Date fecha, int horas, int minutos, int segundos, int distanciaMts) {  //int hora, int minutos, int segundos

        this.fecha = fecha;
        this.horas=horas;
        this.minutos=minutos;
        this.segundos=segundos;
        this.distanciaMts = distanciaMts;

        recalcularTiempos();
    }

    public void modificar(Date fecha, int horas, int minutos, int segundos, int distanciaMts) {


        this.fecha = fecha;
        this.horas = horas;
        this.minutos = minutos;
        this.segundos = segundos;
        this.distanciaMts = distanciaMts;

        recalcularTiempos();
    }

    private void recalcularTiempos() {

        tiempoEnMinutos = (horas*60)+minutos+(segundos/60);
        tiempoEnSegundos = segundos * minutos*60 * horas*3600;
        minutosPorKm = minutosPorKm();
        segundosPorKm = segundosPorKm();
    }

    public Date getFecha() {
        return fecha;
    }

    public String getFechaFormateada() {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd MMMM 'de' yyyy", Locale.getDefault());
        return formatoFecha.format(fecha);
    }

    public String getTiempoFormateado() {

        return String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }

    public int getHoras(){

        return horas;
    }

    public int getMinutos(){

        return minutos;
    }

    public int getSegundos(){

        return segundos;
    }

    public int getDistanciaMts() {

        return distanciaMts;
    }

    public String getMinutosPorKm() {

        DecimalFormat formateoDecimal1 = new DecimalFormat(",00");
        String minPorKm = formateoDecimal1.format(minutosPorKm())+" min.";

        return minPorKm;
    }

    public String getSegundosPorKm() {

        DecimalFormat formateoDecimal3 = new DecimalFormat("#.00");
        String segPorKm = formateoDecimal3.format(segundosPorKm())+" seg.";

        return segPorKm;
    }

    public int getTiempoEnSegundos() {

        return tiempoEnSegundos;
    }

    private float minutosPorKm() {

        return (1000*(tiempoEnMinutos)/distanciaMts);
    }

    private float segundosPorKm() {

        return (1000*tiempoEnSegundos/distanciaMts);
    }

    private float velocidadMedia(){

        float kms=distanciaMts/(float)1000;
        float minTotales=(segundos/(float)60)+minutos;
        float tiempoTotalHoras=(minTotales/(float)60)+horas;
        float velKmH=kms/tiempoTotalHoras;

        return velKmH;
    }

    public String toStringVelocidadMedia(){

        DecimalFormat formateoDecimal2 = new DecimalFormat("#0.00");
        String velMed=formateoDecimal2.format(velocidadMedia())+" km/h";

        return velMed;
    }

    public String toStringEntreno(){

      return "\n· FECHA:  "+getFechaFormateada()+"\n· TIEMPO: "+getTiempoFormateado()+"h.\n· DISTANCIA: "+distanciaMts+"m\n\n";
    }

    public String toStringEstadisticas(){

        DecimalFormat formateoDecimal1 = new DecimalFormat(",00");
        DecimalFormat formateoDecimal2 = new DecimalFormat("#0.00");
        DecimalFormat formateoDecimal3 = new DecimalFormat("#.00");

        return "\n· MIN. POR KM:  "+formateoDecimal1.format(minutosPorKm())+" min.\n· SEG. POR KM:  "+formateoDecimal3.format(segundosPorKm())+" seg.\n· VEL. MEDIA:     "+formateoDecimal2.format(velocidadMedia())+" km/h";
    }
}

