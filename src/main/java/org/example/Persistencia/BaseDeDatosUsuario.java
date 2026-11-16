package org.example.Persistencia;

import org.example.Dominio.Administrador;

import java.util.HashMap;
import java.util.Map;

public class BaseDeDatosUsuario {


    private final Map<String, Administrador> usuarios = new HashMap<>();

    public BaseDeDatosUsuario() {
        // Usuario principal para este proyecto
        registrar(new Administrador("8829282", "Geronimo", "gmijaresa@gmail.com", "12345", "3002467575"));
    }

    public void registrar(Administrador admin) {
        if (admin != null && admin.getCorreo() != null) {
            usuarios.put(normalizar(admin.getCorreo()), admin);
        }
    }

    /** Devuelve el usuario con ese correo en O(1), o null si no existe. */
    public Administrador buscarPorCorreo(String correo) {
        return usuarios.get(normalizar(correo));
    }

    public int cantidadUsuarios() {
        return usuarios.size();
    }

    private String normalizar(String correo) {
        return correo == null ? "" : correo.trim().toLowerCase();
    }
}