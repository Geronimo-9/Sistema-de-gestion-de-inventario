package org.example.UX_GUI;


public class Pantallas {

    private static final String DOBLE  = "=".repeat(66);
    private static final String SIMPLE = "-".repeat(66);

    private static void encabezado(String titulo) {
        System.out.println();
        System.out.println(DOBLE);
        System.out.println("  " + titulo);
        System.out.println(DOBLE);
    }

    public static void pantallaLogin() {
        encabezado("SISTEMA DE GESTIÓN DE INVENTARIO");
        System.out.println("  Bienvenido. Ingrese sus credenciales para continuar.");
        System.out.println(SIMPLE);
    }

    public static void pantallaMenu() {
        encabezado("MENÚ PRINCIPAL");
        System.out.println("   1. Registrar producto");
        System.out.println("   2. Consultar todos los productos");
        System.out.println("   3. Buscar producto por código");
        System.out.println("   4. Consultar categorías (únicas)");
        System.out.println("   5. Mostrar productos ordenados por prioridad");
        System.out.println("   6. Enviar producto a reposición (cola prioritaria)");
        System.out.println("   7. Consultar próximo producto a reponer");
        System.out.println("   8. Procesar siguiente producto a reponer");
        System.out.println("   9. Modificar producto");
        System.out.println("  10. Eliminar producto");
        System.out.println("  11. Mostrar información de las colecciones");
        System.out.println("   0. Salir");
        System.out.println(SIMPLE);
        solicitar("Seleccione una opción entre 0 y 11");
    }

    public static void pantallaInventarios() {
        encabezado("INVENTARIO");
    }

    public static void pantallaCategorias() {
        encabezado("CATEGORÍAS");
    }

    public static void pantallaBaseDeDatos() {
        encabezado("INFORMACIÓN DE LAS COLECCIONES");
    }

    public static void pantallaError(String mensaje) {
        System.out.println();
        System.out.println(SIMPLE);
        System.out.println("  [ERROR] " + mensaje);
        System.out.println(SIMPLE);
    }

    public static void pantallaExito(String mensaje) {
        System.out.println();
        System.out.println("  [OK] " + mensaje);
    }

    public static void pantallaSalida() {
        encabezado("HASTA PRONTO");
        System.out.println("  Sesión finalizada.");
        System.out.println(DOBLE);
    }

    // Tabla de productos: recibe datos simples, así esta clase no conoce a Producto.
    public static void tablaEncabezado() {
        System.out.printf("  %-6s %-28s %-13s %8s  %-9s%n", "CÓDIGO", "NOMBRE", "CATEGORÍA", "CANTIDAD", "PRIORIDAD");
        System.out.println(SIMPLE);
    }

    public static void tablaFila(String codigo, String nombre, String categoria, int cantidad, String prioridad) {
        System.out.printf("  %-6s %-28s %-13s %8d  %-9s%n", codigo, nombre, categoria, cantidad, prioridad);
    }

    public static void pantallaLinea(String texto) {
        System.out.println("  " + texto);
    }

    /** Imprime una pregunta sin salto de línea para que el usuario escriba a continuación. */
    public static void solicitar(String etiqueta) {
        System.out.print("  " + etiqueta + ": ");
    }
}