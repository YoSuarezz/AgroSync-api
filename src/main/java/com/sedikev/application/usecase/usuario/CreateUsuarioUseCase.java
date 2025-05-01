package com.sedikev.application.usecase.usuario;

import com.sedikev.application.usecase.UseCaseWithReturn;
import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.domain.model.CarteraDomain;
import com.sedikev.domain.model.UsuarioDomain;
import com.sedikev.domain.repository.UsuarioRepository;
import com.sedikev.application.mapper.UsuarioMapper;
import com.sedikev.domain.service.CarteraService;
import com.sedikev.infrastructure.adapter.entity.UsuarioEntity;
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
            throw new BusinessSedikevException("Ya existe un usuario con ese nombre");
        }
        // 2) NO permitir teléfono duplicado
        if (usuarioRepository.existsByTelefono(usuarioDomain.getTelefono())) {
            throw new BusinessSedikevException("Ya existe un usuario con ese teléfono");
        }

        // 3) Validación de negocio: El nombre debe tener menos de 50 caracteres
        if (usuarioDomain.getNombre().length() > 50) {
            throw new BusinessSedikevException("El nombre no puede tener más de 50 caracteres");
        }

        // 4) Validación de negocio: El teléfono debe tener exactamente 10 caracteres
        if (usuarioDomain.getTelefono().length() != 10) {
            throw new BusinessSedikevException("El teléfono debe tener exactamente 10 caracteres");
        }

        // 5) Validación de negocio: El tipo de usuario debe ser "proveedor" o "cliente"
        if (!usuarioDomain.getTipo_usuario().equalsIgnoreCase("proveedor") &&
                !usuarioDomain.getTipo_usuario().equalsIgnoreCase("cliente")) {
            throw new BusinessSedikevException("El tipo de usuario debe ser 'proveedor' o 'cliente'");
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
