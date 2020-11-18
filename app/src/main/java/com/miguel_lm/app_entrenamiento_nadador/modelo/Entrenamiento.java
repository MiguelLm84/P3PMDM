package com.miguel_lm.app_entrenamiento_nadador.modelo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.miguel_lm.app_entrenamiento_nadador.ui.MainActivity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "Entrenamientos")
public class Entrenamiento {

    @PrimaryKey(autoGenerate = true)
    protected int key;
    @NonNull
    private Date fecha;
    @NonNull
    int horas;
    @NonNull
    int minutos;
    @NonNull
    int segundos;
    @NonNull
    private int distanciaMts;

    // Campos calculados, no se guardan en la bd
    @Ignore
    private float tiempoEnMinutos;
    @Ignore
    private int tiempoEnSegundos;
    @Ignore
    private float minutosPorKm;
    @Ignore
    private float segundosPorKm;

    @Ignore
    private ArrayList<Integer> listaDistancia=new ArrayList<>();

    @Ignore
    private ArrayList<Float> listaTiempoEnMin=new ArrayList<>();

    public Entrenamiento(Date fecha, int horas, int minutos, int segundos, int distanciaMts) {  //int hora, int minutos, int segundos

        this.fecha = fecha;
        this.horas=horas;
        this.minutos=minutos;
        this.segundos=segundos;
        this.distanciaMts = distanciaMts;

        listaDistancia.add(distanciaMts);
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
        listaTiempoEnMin.add(tiempoEnMinutos);
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

    public int getKey(){
        return key;
    }

    public int getHoras(){

        return horas;
    }

    public int getMinutos(){

        return minutos;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setFecha(@NonNull Date fecha) {
        this.fecha = fecha;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }

    public void setDistanciaMts(int distanciaMts) {
        this.distanciaMts = distanciaMts;
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

    public String getkmNadadosTotal(){  //todo: recoger en la lista todos los entrenamientos de creados para hacer el cálculo.

        int distanciaTotal=0;

        for(int i=0;i<listaDistancia.size();i++){
            int distancia=listaDistancia.get(i);
            distanciaTotal=distanciaTotal+distancia;
        }
        float distanciaEnKm=(float)distanciaTotal/1000;
        String distanciaEnKmParseada=distanciaEnKm+" Km";

        return distanciaEnKmParseada;
    }

    public String getMediaMinPorKm(){

        DecimalFormat formateoDecimal3 = new DecimalFormat("#.00");
        String resultado=null;
        int distanciaTotal=0;
        float tiempoTotal=0;

        for(int i=0;i<listaTiempoEnMin.size();i++){
            float tiempoMin=listaTiempoEnMin.get(i);
            tiempoTotal=tiempoTotal+tiempoMin;
        }

        for(int i=0;i<listaDistancia.size();i++){
            int distancia=listaDistancia.get(i);
            distanciaTotal=distanciaTotal+distancia;
        }
        float calculo=(float)distanciaTotal/tiempoTotal;
        resultado=formateoDecimal3.format(calculo)+" m/km";

        return resultado;
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

    public float velocidadMediaEntreno(){

        //todo: Hacer método velocidadMedia() para cada entreno.

        float kms=distanciaMts/(float)1000;
        float minTotales=(segundos/(float)60)+minutos;
        float tiempoTotalHoras=(minTotales/(float)60)+horas;
        float velKmH=kms/tiempoTotalHoras;


        return velKmH;
    }

    public String toStringVelMedEntreno(){

        DecimalFormat formateoDecimal2 = new DecimalFormat("#0.00");
        String velMedEntreno=formateoDecimal2.format(velocidadMediaEntreno())+" km/h";

        return velMedEntreno;
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

