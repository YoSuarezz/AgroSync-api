package com.agrosync.application.usecase.usuarios;

import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.crosscutting.exception.custom.BusinessAgroSyncException;
import com.agrosync.domain.model.CarteraDomain;
import com.agrosync.domain.usuarios.UsuarioDomain;
import com.agrosync.application.secondaryports.repository.UsuarioRepository;
import com.agrosync.application.primaryports.mapper.UsuarioMapper;
import com.agrosync.domain.service.CarteraService;
import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CreateUsuarioUseCase implements UseCaseWithReturn<UsuarioDomain, UsuarioDomain> {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final CarteraService carteraService;

    @Override
    public UsuarioDomain ejecutar(UsuarioDomain usuarioDomain) {
        // 1) NO permitir nombre duplicado
        if (usuarioRepository.existsByNombre(usuarioDomain.getNombre())) {
            throw new BusinessAgroSyncException("Ya existe un usuario con ese nombre");
        }
        // 2) NO permitir teléfono duplicado
        if (usuarioRepository.existsByTelefono(usuarioDomain.getTelefono())) {
            throw new BusinessAgroSyncException("Ya existe un usuario con ese teléfono");
        }

        // 3) Validación de negocio: El nombre debe tener menos de 50 caracteres
        if (usuarioDomain.getNombre().length() > 50) {
            throw new BusinessAgroSyncException("El nombre no puede tener más de 50 caracteres");
        }

        // 4) Validación de negocio: El teléfono debe tener exactamente 10 caracteres
        if (usuarioDomain.getTelefono().length() != 10) {
            throw new BusinessAgroSyncException("El teléfono debe tener exactamente 10 caracteres");
        }

        System.out.println("usuario : " + usuarioDomain);
        // 6) Mapear y guardar
        UsuarioEntity usuarioEntity = usuarioMapper.toEntity(usuarioDomain);
        UsuarioEntity usuarioSaved   = usuarioRepository.save(usuarioEntity);

        UsuarioDomain usuarioCartera = usuarioMapper.toDomain(usuarioSaved);
        System.out.println(usuarioCartera);
        CarteraDomain cartera = new CarteraDomain();
        cartera.setUsuario(usuarioCartera);
        cartera.setSaldo(BigDecimal.ZERO);
        CarteraDomain carteraSaved = carteraService.save(cartera);
        System.out.println("Creando cartera : " + carteraSaved);

        return usuarioMapper.toDomain(usuarioSaved);
    }
}
