package com.agrosync.application.usecase.usuarios.impl;

import com.agrosync.application.secondaryports.entity.CarteraEntity;
import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.application.secondaryports.mapper.usuarios.UsuarioEntityMapper;
import com.agrosync.application.secondaryports.repository.CarteraRepository;
import com.agrosync.application.secondaryports.repository.UsuarioRepository;
import com.agrosync.application.usecase.usuarios.RegistrarNuevoUsuario;
import com.agrosync.application.usecase.usuarios.rulesvalidator.RegistrarNuevoUsuarioRulesValidator;
import com.agrosync.domain.usuarios.UsuarioDomain;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RegistrarNuevoUsuarioImpl implements RegistrarNuevoUsuario {

    private final UsuarioRepository usuarioRepository;
    private final RegistrarNuevoUsuarioRulesValidator registrarNuevoUsuarioRulesValidator;
    private final CarteraRepository carteraRepository;

    public RegistrarNuevoUsuarioImpl(UsuarioRepository usuarioRepository, RegistrarNuevoUsuarioRulesValidator registrarNuevoUsuarioRulesValidator, CarteraRepository carteraRepository) {
        this.usuarioRepository = usuarioRepository;
        this.registrarNuevoUsuarioRulesValidator = registrarNuevoUsuarioRulesValidator;
        this.carteraRepository = carteraRepository;
    }

    @Override
    public void ejecutar(UsuarioDomain data) {
        registrarNuevoUsuarioRulesValidator.validar(data);
        UsuarioEntity usuarioEntity = UsuarioEntityMapper.INSTANCE.toEntity(data);
        UsuarioEntity usuarioGuardado = usuarioRepository.save(usuarioEntity);

        // crear la cartera asociada al usuario (Provisional)

        CarteraEntity cartera = new CarteraEntity();
        cartera.setUsuario(usuarioGuardado);
        cartera.setSaldo(BigDecimal.ZERO);
        carteraRepository.save(cartera);
    }
}
