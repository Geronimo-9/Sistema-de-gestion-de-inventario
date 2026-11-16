package org.example.Persistencia;

import org.example.Dominio.Prioridad;
import org.example.Dominio.Producto;

import java.util.ArrayList;
import java.util.List;

public class BaseDeDatos{

    // ArrayList: cada add() al final es O(1) amortizado y recorrer es barato.
    private final List<Producto> productos = new ArrayList<>(40);

    BaseDeDatos() {
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
        agregar("H001", "Destornillador de precisión","Herramientas",40,  Prioridad.MEDIA);
        agregar("H002", "Taladro inalámbrico",        "Herramientas",4,   Prioridad.CRITICA);
        agregar("H003", "Martillo de uña",            "Herramientas",17,  Prioridad.MEDIA);
        agregar("H004", "Cinta métrica 5m",           "Herramientas",55,  Prioridad.BAJA);
        agregar("H005", "Alicates universales",       "Herramientas",10,  Prioridad.ALTA);
        agregar("H006", "Caja de tornillos surtidos", "Herramientas",80,  Prioridad.BAJA);
        agregar("H007", "Linterna recargable",        "Herramientas",1,   Prioridad.CRITICA);

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

    // Método auxiliar para no repetir "productos.add(new Producto(...))" 40 veces.
    @Override
    private void agregar(String codigo, String nombre, String categoria, int cantidad, Prioridad prioridad) {
        productos.add(new Producto(codigo, nombre, categoria, cantidad, prioridad));
    }







    public List<Producto> getProductos() {
        return productos;
    }
}