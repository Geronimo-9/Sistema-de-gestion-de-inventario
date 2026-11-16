package org.example.Persistencia;

import org.example.Dominio.Prioridad;
import org.example.Dominio.Producto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class BaseDeDatosProducto {

    // LinkedHashMap: búsqueda por código en O(1) y conserva el orden de registro para listar().
    private final Map<String, Producto> productos = new LinkedHashMap<>(40);

    // Criterio de urgencia: primero la prioridad (CRITICA va antes por el orden del enum) y luego la menor cantidad.
    private static final Comparator<Producto> URGENCIA =
            Comparator.comparing(Producto::getPrioridad).thenComparingInt(Producto::getCantidad);

    // PriorityQueue: mantiene siempre en la cabeza el producto más urgente según URGENCIA.
    private final Queue<Producto> productosEnCola = new PriorityQueue<>(URGENCIA);

    public BaseDeDatosProducto() {
        // ── Electrónica ──
        agregar("E001", "Teclado mecánico",           "Electrónica", 12,  Prioridad.ALTA);
        agregar("E002", "Mouse inalámbrico",          "Electrónica", 45,  Prioridad.BAJA);
        agregar("E003", "Monitor 24 pulgadas",        "Electrónica", 3,   Prioridad.CRITICA);
        agregar("E004", "Cable HDMI",                 "Electrónica", 60,  Prioridad.BAJA);
        agregar("E005", "Memoria USB 64GB",           "Electrónica", 25,  Prioridad.MEDIA);
        agregar("E006", "Audífonos con micrófono",    "Electrónica", 8,   Prioridad.ALTA);
        agregar("E007", "Cargador universal",         "Electrónica", 0,   Prioridad.CRITICA);

        // ── Papelería ──
        agregar("P001", "Cuaderno cuadriculado",      "Papelería",   120, Prioridad.BAJA);
        agregar("P002", "Resma de papel carta",       "Papelería",   18,  Prioridad.MEDIA);
        agregar("P003", "Bolígrafos azules x12",      "Papelería",   5,   Prioridad.CRITICA);
        agregar("P004", "Marcadores permanentes",     "Papelería",   30,  Prioridad.MEDIA);
        agregar("P005", "Carpeta archivadora",        "Papelería",   14,  Prioridad.ALTA);
        agregar("P006", "Calculadora científica",     "Papelería",   9,   Prioridad.ALTA);
        agregar("P007", "Tijeras escolares",          "Papelería",   50,  Prioridad.BAJA);

        // ── Hogar ──
        agregar("H001", "Lámpara de escritorio",      "Hogar",       11,  Prioridad.ALTA);
        agregar("H002", "Silla ergonómica",           "Hogar",       2,   Prioridad.CRITICA);
        agregar("H003", "Extensión eléctrica 3m",     "Hogar",       35,  Prioridad.MEDIA);
        agregar("H004", "Juego de sábanas",           "Hogar",       22,  Prioridad.MEDIA);
        agregar("H005", "Termo de 1 litro",           "Hogar",       48,  Prioridad.BAJA);
        agregar("H006", "Reloj de pared",             "Hogar",       7,   Prioridad.ALTA);
        agregar("H007", "Organizador de escritorio",  "Hogar",       26,  Prioridad.MEDIA);

        // ── Herramientas ──
        agregar("T001", "Destornillador de precisión","Herramientas",40,  Prioridad.MEDIA);
        agregar("T002", "Taladro inalámbrico",        "Herramientas",4,   Prioridad.CRITICA);
        agregar("T003", "Martillo de uña",            "Herramientas",17,  Prioridad.MEDIA);
        agregar("T004", "Cinta métrica 5m",           "Herramientas",55,  Prioridad.BAJA);
        agregar("T005", "Alicates universales",       "Herramientas",10,  Prioridad.ALTA);
        agregar("T006", "Caja de tornillos surtidos", "Herramientas",80,  Prioridad.BAJA);
        agregar("T007", "Linterna recargable",        "Herramientas",1,   Prioridad.CRITICA);

        // ── Alimentos ──
        agregar("A001", "Arroz 1kg",                  "Alimentos",   200, Prioridad.BAJA);
        agregar("A002", "Aceite vegetal 1L",          "Alimentos",   15,  Prioridad.ALTA);
        agregar("A003", "Café molido 500g",           "Alimentos",   6,   Prioridad.ALTA);
        agregar("A004", "Azúcar 1kg",                 "Alimentos",   90,  Prioridad.BAJA);
        agregar("A005", "Galletas integrales",        "Alimentos",   0,   Prioridad.CRITICA);
        agregar("A006", "Atún en lata",               "Alimentos",   28,  Prioridad.MEDIA);

        // ── Aseo ──
        agregar("S001", "Detergente líquido 2L",      "Aseo",        13,  Prioridad.ALTA);
        agregar("S002", "Jabón de manos",             "Aseo",        70,  Prioridad.BAJA);
        agregar("S003", "Papel higiénico x12",        "Aseo",        20,  Prioridad.MEDIA);
        agregar("S004", "Desinfectante 1L",           "Aseo",        3,   Prioridad.CRITICA);
        agregar("S005", "Esponjas de cocina x3",      "Aseo",        33,  Prioridad.MEDIA);
        agregar("S006", "Bolsas de basura x30",       "Aseo",        16,  Prioridad.MEDIA);
    }

    private void agregar(String codigo, String nombre, String categoria, int cantidad, Prioridad prioridad) {
        agregar(new Producto(codigo, nombre, categoria, cantidad, prioridad));
    }

    private String normalizar(String codigo) {
        return codigo == null ? "" : codigo.trim().toUpperCase();
    }

    // ───────────── CRUD ─────────────

    /** Inserta en O(1) usando el código normalizado como clave. */
    public void agregar(Producto p) {
        productos.put(normalizar(p.getCodigo()), p);
    }

    /** Vista de solo lectura respetando el orden de registro. */
    public List<Producto> listar() {
        return Collections.unmodifiableList(new ArrayList<>(productos.values()));
    }

    /** Búsqueda directa por código en O(1). */
    public Producto consultar(String codigo) {
        return productos.get(normalizar(codigo));
    }

    public int cantidadProductos() {
        return productos.size();
    }

    /** TreeSet: garantiza categorías únicas ordenadas alfabéticamente (A-Z). */
    public Set<String> obtenerCategorias() {
        Set<String> categorias = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (Producto p : productos.values()) {
            categorias.add(p.getCategoria());
        }
        return Collections.unmodifiableSet(categorias);
    }

    /** Devuelve una COPIA ordenada por urgencia, sin alterar el orden del mapa original. */
    public List<Producto> ordenarPorPrioridad() {
        List<Producto> copia = new ArrayList<>(productos.values());
        copia.sort(URGENCIA);
        return copia;
    }

    /** Elimina de TODAS las estructuras en O(1) en el mapa. Devuelve true si existía. */
    public boolean eliminar(String codigo) {
        Producto eliminado = productos.remove(normalizar(codigo));
        if (eliminado != null) {
            productosEnCola.remove(eliminado);
            return true;
        }
        return false;
    }

    public void eliminarTodo() {
        productos.clear();
        productosEnCola.clear();
    }

    // ───────────── Cola de reposición (PriorityQueue) ─────────────

    public void encolar(Producto p) {
        if (p != null && !productosEnCola.contains(p)) {
            productosEnCola.offer(p);
        }
    }

    /** Si el producto ya estaba en la cola, lo reposiciona tras modificar cantidad o prioridad. */
    public void actualizarEnCola(Producto p) {
        if (p != null && productosEnCola.remove(p)) {
            productosEnCola.offer(p);
        }
    }

    public boolean estaEnCola(Producto p) {
        return productosEnCola.contains(p);
    }

    public int tamanoCola() {
        return productosEnCola.size();
    }

    /** Mira el siguiente a reponer en O(1) SIN sacarlo (null si la cola está vacía). */
    public Producto proximo() {
        return productosEnCola.peek();
    }

    /** Saca y devuelve el siguiente más urgente en O(log n) (null si la cola está vacía). */
    public Producto procesarSiguiente() {
        return productosEnCola.poll();
    }
}