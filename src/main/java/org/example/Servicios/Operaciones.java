package org.example.Servicios;

import org.example.AccionesSistema.ValidadorDatos;
import org.example.Dominio.Administrador;
import org.example.Dominio.Prioridad;
import org.example.Dominio.Producto;
import org.example.Persistencia.BaseDeDatosProducto;
import org.example.Persistencia.BaseDeDatosUsuario;
import org.example.UX_GUI.Pantallas;

import java.util.List;
import java.util.Set;

// Controlador: valida con ValidadorDatos, accede a las bases de datos y reporta el resultado con Pantallas.
public class Operaciones {

    private static final BaseDeDatosProducto bdProductos = new BaseDeDatosProducto();
    private static final BaseDeDatosUsuario bdUsuarios = new BaseDeDatosUsuario();
    private static final int UNIDADES_REPOSICION = 10;

    // ───────────── Sesión ─────────────

    public static boolean iniciarSesion(String correo, String contrasena) {
        try {
            ValidadorDatos.validaDatosVacio(correo);
            ValidadorDatos.validaDatosVacio(contrasena);
            Administrador admin = bdUsuarios.buscarPorCorreo(correo.trim());
            if (admin == null || !admin.getContrasena().equals(contrasena))
                throw new IllegalArgumentException("Correo o contraseña incorrectos.");
            Pantallas.pantallaExito("Acceso concedido.");
            return true;
        } catch (IllegalArgumentException e) {
            Pantallas.pantallaError(e.getMessage());
            return false;
        }
    }

    // ───────────── Opciones del menú ─────────────

    // Opción 1
    public static void registrarProducto(String codigo, String nombre, String categoria, int cantidad, int opcionPrioridad) {
        ejecutar(() -> {
            ValidadorDatos.validaDatosVacio(codigo);
            ValidadorDatos.validaDatosVacio(nombre);
            ValidadorDatos.validaDatosVacio(categoria);
            ValidadorDatos.validarNegativos(cantidad);
            ValidadorDatos.validarRango(opcionPrioridad, 1, Prioridad.values().length);
            if (bdProductos.consultar(codigo.trim()) != null)
                throw new IllegalArgumentException("El código " + codigo.trim() + " ya existe.");
            bdProductos.agregar(new Producto(codigo.trim(), nombre.trim(), categoria.trim(),
                    cantidad, Prioridad.values()[opcionPrioridad - 1]));
            Pantallas.pantallaExito("Producto registrado: " + codigo.trim());
        });
    }

    // Opción 2
    public static void consultarProducto() {
        mostrar(bdProductos.listar());
    }

    // Opción 3
    public static void consultarProducto(String codigo) {
        ejecutar(() -> mostrar(List.of(buscarExistente(codigo))));
    }

    // Opción 4: usa TreeSet<String> para obtener categorías únicas y ordenadas alfabéticamente.
    public static void ordenarCategorias() {
        Set<String> categorias = bdProductos.obtenerCategorias();
        Pantallas.pantallaCategorias();
        for (String c : categorias) Pantallas.pantallaLinea("- " + c);
        Pantallas.pantallaLinea("Total: " + categorias.size() + " categoría(s)");
    }

    // Opción 5
    public static void ordenarProductosPorPrioridad() {
        mostrar(bdProductos.ordenarPorPrioridad());
    }

    // Opción 6
    public static void priorizarProducto(String codigo) {
        ejecutar(() -> {
            Producto p = buscarExistente(codigo);
            if (bdProductos.estaEnCola(p))
                throw new IllegalArgumentException("El producto " + p.getCodigo() + " ya está en la cola de reposición.");
            bdProductos.encolar(p);
            Pantallas.pantallaExito("Enviado a reposición: " + p.getNombre());
        });
    }

    // Opción 7
    public static void consultarProximo() {
        Producto p = bdProductos.proximo();
        if (p == null) Pantallas.pantallaError("La cola de reposición está vacía.");
        else mostrar(List.of(p));
    }

    // Opción 8: extrae de la PriorityQueue y repone el stock del producto.
    public static void procesarSiguiente() {
        Producto p = bdProductos.procesarSiguiente();
        if (p == null) {
            Pantallas.pantallaError("La cola de reposición está vacía.");
        } else {
            p.setCantidad(p.getCantidad() + UNIDADES_REPOSICION);
            Pantallas.pantallaExito("Reposición procesada: " + p.getNombre() + " (" + p.getCodigo()
                    + ") | +" + UNIDADES_REPOSICION + " uds. (Stock actual: " + p.getCantidad() + ")");
        }
    }

    // Opción 9: actualiza atributos y reubica el producto en la PriorityQueue si estaba en cola.
    public static void actualizarProducto(String codigo, int nuevaCantidad, int opcionPrioridad) {
        ejecutar(() -> {
            Producto p = buscarExistente(codigo);
            ValidadorDatos.validarNegativos(nuevaCantidad);
            ValidadorDatos.validarRango(opcionPrioridad, 1, Prioridad.values().length);
            p.setCantidad(nuevaCantidad);
            p.setPrioridad(Prioridad.values()[opcionPrioridad - 1]);
            bdProductos.actualizarEnCola(p);
            Pantallas.pantallaExito("Producto actualizado: " + p.getCodigo());
        });
    }

    // Opción 10
    public static void eliminarProducto(String codigo) {
        ejecutar(() -> {
            Producto p = buscarExistente(codigo);
            bdProductos.eliminar(p.getCodigo());
            Pantallas.pantallaExito("Producto eliminado: " + p.getCodigo());
        });
    }

    // Opción 11: resumen de las colecciones activas en el sistema.
    public static void informacionColecciones() {
        Pantallas.pantallaBaseDeDatos();
        Pantallas.pantallaLinea(String.format("%-28s %-28s %5d", "Inventario (código->Prod)", "LinkedHashMap", bdProductos.cantidadProductos()));
        Pantallas.pantallaLinea(String.format("%-28s %-28s %5d", "Cola de reposición", "PriorityQueue", bdProductos.tamanoCola()));
        Pantallas.pantallaLinea(String.format("%-28s %-28s %5d", "Categorías únicas", "TreeSet", bdProductos.obtenerCategorias().size()));
        Pantallas.pantallaLinea(String.format("%-28s %-28s %5d", "Usuarios (correo->Admin)", "HashMap", bdUsuarios.cantidadUsuarios()));
        Pantallas.pantallaLinea(String.format("%-28s %-28s %5d", "Vista de listado", "List (ArrayList)", bdProductos.listar().size()));
    }

    // ───────────── Auxiliares privados ─────────────

    /** Busca por código; si el código está vacío o no existe, lanza el error que corresponda. */
    private static Producto buscarExistente(String codigo) {
        ValidadorDatos.validaDatosVacio(codigo);
        Producto p = bdProductos.consultar(codigo.trim());
        ValidadorDatos.validarExistencia(p, "El producto con código " + codigo.trim());
        return p;
    }

    /** Ejecuta una acción y, si una validación falla, muestra el error en lugar de romper el programa. */
    private static void ejecutar(Runnable accion) {
        try {
            accion.run();
        } catch (IllegalArgumentException e) {
            Pantallas.pantallaError(e.getMessage());
        }
    }

    private static void mostrar(List<Producto> lista) {
        Pantallas.pantallaInventarios();
        if (lista.isEmpty()) {
            Pantallas.pantallaLinea("No hay productos para mostrar.");
            return;
        }
        Pantallas.tablaEncabezado();
        for (Producto p : lista)
            Pantallas.tablaFila(p.getCodigo(), p.getNombre(), p.getCategoria(), p.getCantidad(), p.getPrioridad().name());
        Pantallas.pantallaLinea("Total: " + lista.size() + " producto(s)");
    }
}