package org.example.AccionesSistema;

/** Valida datos de entrada. Si algo es inválido lanza IllegalArgumentException con el motivo. */
public class ValidadorDatos {

    public static void validarNegativos(double numero) {
        if (numero < 0) throw new IllegalArgumentException("El valor no puede ser negativo: " + numero);
    }

    public static void validarNegativos(int numero) {
        if (numero < 0) throw new IllegalArgumentException("El valor no puede ser negativo: " + numero);
    }

    public static void validaDatosVacio(String datos) {
        if (datos == null || datos.isBlank()) throw new IllegalArgumentException("El dato no puede estar vacío.");
    }

    public static void validarRango(int valor, int min, int max) {
        if (valor < min || valor > max)
            throw new IllegalArgumentException("El valor debe estar entre " + min + " y " + max + ".");
    }

    public static void validarExistencia(Object objeto, String descripcion) {
        if (objeto == null) throw new IllegalArgumentException(descripcion + " no existe.");
    }
}