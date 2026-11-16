package org.example;

import org.example.Servicios.Operaciones;
import org.example.UX_GUI.Pantallas;

import java.util.Scanner;

public class Main {

    private static final Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {

        // Login: se repite hasta que el correo y la contraseña coincidan con un usuario registrado.
        boolean autenticado;
        do {
            Pantallas.pantallaLogin();
            String correo = leerTexto("Correo");
            String contrasena = leerTexto("Contraseña");
            autenticado = Operaciones.iniciarSesion(correo, contrasena);
        } while (!autenticado);

        // Menú: solo lee datos y delega en Operaciones. Aquí no hay reglas de negocio.
        int opcion;
        do {
            Pantallas.pantallaMenu();
            opcion = leerEntero();
            switch (opcion) {
                case 1 -> Operaciones.registrarProducto(leerTexto("Código"), leerTexto("Nombre"), leerTexto("Categoría"),
                        leerEntero("Cantidad"), leerEntero("Prioridad (1=CRITICA 2=ALTA 3=MEDIA 4=BAJA)"));
                case 2 -> Operaciones.consultarProducto();
                case 3 -> Operaciones.consultarProducto(leerTexto("Código"));
                case 4 -> Operaciones.ordenarCategorias();
                case 5 -> Operaciones.ordenarProductosPorPrioridad();
                case 6 -> Operaciones.priorizarProducto(leerTexto("Código"));
                case 7 -> Operaciones.consultarProximo();
                case 8 -> Operaciones.procesarSiguiente();
                case 9 -> Operaciones.actualizarProducto(leerTexto("Código"), leerEntero("Nueva cantidad"),
                        leerEntero("Nueva prioridad (1=CRITICA 2=ALTA 3=MEDIA 4=BAJA)"));
                case 10 -> Operaciones.eliminarProducto(leerTexto("Código"));
                case 11 -> Operaciones.informacionColecciones();
                case 0 -> { }
                default -> Pantallas.pantallaError("Opción no válida. Elija un número entre 0 y 11.");
            }
        } while (opcion != 0);

        Pantallas.pantallaSalida();
    }

    private static String leerTexto(String etiqueta) {
        Pantallas.solicitar(etiqueta);
        return teclado.nextLine();
    }

    private static int leerEntero(String etiqueta) {
        Pantallas.solicitar(etiqueta);
        return leerEntero();
    }

    /** Lee una línea y la convierte a entero; si no es un número, avisa y vuelve a pedirlo. */
    private static int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(teclado.nextLine().trim());
            } catch (NumberFormatException e) {
                Pantallas.pantallaError("Debe ingresar un número entero.");
                Pantallas.solicitar("Intente de nuevo");
            }
        }
    }
}