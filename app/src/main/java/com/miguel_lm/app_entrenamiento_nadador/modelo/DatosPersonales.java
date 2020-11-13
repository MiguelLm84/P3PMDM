package com.miguel_lm.app_entrenamiento_nadador.modelo;

public class DatosPersonales {

    public DatosPersonales(String n, String ap, String ap2, int ed, float alt, float pe) {
        this.setNombre( n );
        this.setApellido( ap );
        this.setApellido2( ap2 );
        this.setEdad( ed );
        this.setAltura( alt );
        this.setPeso( pe );
    }

    final public String getNombre() {

        return nombre;
    }

    final public void setNombre(String nombre) {

        this.nombre = nombre;
    }

    final public String getApellido() {

        return apellido;
    }
    final public void setApellido(String apellido) {

        this.apellido = apellido;
    }

    final public String getApellido2() {

        return apellido2;
    }
    final public void setApellido2(String apellido2) {

        this.apellido2 = apellido2;
    }

    final public int getEdad() {

        return edad;
    }

    final public void setEdad(int edad) {

        this.edad = edad;
    }

    final public float getAltura() {

        return altura;
    }

    final public void setAltura(float altura) {

        this.altura = altura;
    }

    final public float getPeso() {

        return peso;
    }

    final public void setPeso(float peso) {

        this.peso = peso;
    }

    private String nombre;
    private String apellido;
    private String apellido2;
    private int edad;
    private float altura;
    private float peso;
}
