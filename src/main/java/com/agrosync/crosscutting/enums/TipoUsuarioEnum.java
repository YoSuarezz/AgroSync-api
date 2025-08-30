package com.agrosync.crosscutting.enums;

import com.agrosync.application.secondaryports.entity.usuarios.TIpoUsuarioEntity;
import com.agrosync.application.secondaryports.repository.TipoUsuarioRepository;
import com.agrosync.crosscutting.exception.custom.CrosscuttingAgroSyncException;

import java.util.Optional;

public enum TipoUsuarioEnum {
    PROVEEDOR("Proveedor"),
    CLIENTE("Cliente");

    private final String nombre;
    private Long id;
    private static TipoUsuarioRepository repository;

    TipoUsuarioEnum(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public static void setRepository(TipoUsuarioRepository repository) {
        TipoUsuarioEnum.repository = repository;
    }

    public Long getId() {
        synchronized (this) {
            if (id == null) {
                Optional<TIpoUsuarioEntity> resultados = repository.findByNombreIgnoreCase(this.nombre);

                if (resultados.isEmpty()) {
                    throw new CrosscuttingAgroSyncException(
                            "TipoUsuario no encontrado: " + this.nombre,
                            "No existe registro en la base de datos para este tipo de usuario",
                            new IllegalStateException("Verificar los tipos de usuario iniciales en la base de datos")
                    );
                }

                TIpoUsuarioEntity tipoUsuarioEntity = resultados.get();
                if (tipoUsuarioEntity.getId() == null) {
                    throw new CrosscuttingAgroSyncException(
                            "TipoUsuario sin ID asignado",
                            "El tipo de usuario encontrado no tiene identificador único",
                            new IllegalStateException("Revisar migraciones de base de datos")
                    );
                }

                this.id = tipoUsuarioEntity.getId();
            }
        }
        return id;
    }
}
