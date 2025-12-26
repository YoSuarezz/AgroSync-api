package com.agrosync.application.usecase.usuarios.impl;

import com.agrosync.application.primaryports.dto.usuarios.request.UsuarioDetalladoFiltroDTO;
import com.agrosync.application.secondaryports.entity.abonos.AbonoEntity;
import com.agrosync.application.secondaryports.entity.cobros.CobroEntity;
import com.agrosync.application.secondaryports.entity.compras.CompraEntity;
import com.agrosync.application.secondaryports.entity.cuentascobrar.CuentaCobrarEntity;
import com.agrosync.application.secondaryports.entity.cuentaspagar.CuentaPagarEntity;
import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.application.secondaryports.entity.ventas.VentaEntity;
import com.agrosync.application.secondaryports.mapper.abonos.AbonoEntityMapper;
import com.agrosync.application.secondaryports.mapper.carteras.CarteraEntityMapper;
import com.agrosync.application.secondaryports.mapper.cobros.CobroEntityMapper;
import com.agrosync.application.secondaryports.mapper.compras.CompraEntityMapper;
import com.agrosync.application.secondaryports.mapper.cuentascobrar.CuentaCobrarEntityMapper;
import com.agrosync.application.secondaryports.mapper.cuentaspagar.CuentaPagarEntityMapper;
import com.agrosync.application.secondaryports.mapper.ventas.VentaEntityMapper;
import com.agrosync.application.secondaryports.repository.*;
import com.agrosync.application.usecase.usuarios.ObtenerUsuarioDetallado;
import com.agrosync.application.usecase.usuarios.rulesvalidator.ObtenerUsuarioDetalladoRulesValidator;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import com.agrosync.domain.enums.abonos.EstadoAbonoEnum;
import com.agrosync.domain.enums.cobros.EstadoCobroEnum;
import com.agrosync.domain.enums.cuentas.EstadoCuentaEnum;
import com.agrosync.domain.enums.ventas.EstadoVentaEnum;
import com.agrosync.domain.usuarios.UsuarioDetalladoDomain;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ObtenerUsuarioDetalladoImpl implements ObtenerUsuarioDetallado {

    private final UsuarioRepository usuarioRepository;
    private final CompraRepository compraRepository;
    private final VentaRepository ventaRepository;
    private final CuentaPagarRepository cuentaPagarRepository;
    private final CuentaCobrarRepository cuentaCobrarRepository;
    private final CobroRepository cobroRepository;
    private final AbonoRepository abonoRepository;
    private final CarteraEntityMapper carteraEntityMapper;
    private final CompraEntityMapper compraEntityMapper;
    private final CuentaPagarEntityMapper cuentaPagarEntityMapper;
    private final AbonoEntityMapper abonoEntityMapper;
    private final VentaEntityMapper ventaEntityMapper;
    private final CuentaCobrarEntityMapper cuentaCobrarEntityMapper;
    private final CobroEntityMapper cobroEntityMapper;
    private final ObtenerUsuarioDetalladoRulesValidator obtenerUsuarioDetalladoRulesValidator;

    public ObtenerUsuarioDetalladoImpl(UsuarioRepository usuarioRepository, CompraRepository compraRepository, VentaRepository ventaRepository, CuentaPagarRepository cuentaPagarRepository,
                                       CuentaCobrarRepository cuentaCobrarRepository, CobroRepository cobroRepository, AbonoRepository abonoRepository, CarteraEntityMapper carteraEntityMapper, CarteraEntityMapper carteraEntityMapper1,
                                       CompraEntityMapper compraEntityMapper, CuentaPagarEntityMapper cuentaPagarEntityMapper, AbonoEntityMapper abonoEntityMapper, VentaEntityMapper ventaEntityMapper,
                                       CuentaCobrarEntityMapper cuentaCobrarEntityMapper, CobroEntityMapper cobroEntityMapper, ObtenerUsuarioDetalladoRulesValidator obtenerUsuarioDetalladoRulesValidator) {
        this.usuarioRepository = usuarioRepository;
        this.compraRepository = compraRepository;
        this.ventaRepository = ventaRepository;
        this.cuentaPagarRepository = cuentaPagarRepository;
        this.cuentaCobrarRepository = cuentaCobrarRepository;
        this.cobroRepository = cobroRepository;
        this.abonoRepository = abonoRepository;
        this.carteraEntityMapper = carteraEntityMapper1;
        this.cuentaPagarEntityMapper = cuentaPagarEntityMapper;
        this.abonoEntityMapper = abonoEntityMapper;
        this.ventaEntityMapper = ventaEntityMapper;
        this.cuentaCobrarEntityMapper = cuentaCobrarEntityMapper;
        this.cobroEntityMapper = cobroEntityMapper;
        this.compraEntityMapper = compraEntityMapper;
        this.obtenerUsuarioDetalladoRulesValidator = obtenerUsuarioDetalladoRulesValidator;
    }

    @Override
    public UsuarioDetalladoDomain ejecutar(UsuarioDetalladoFiltroDTO filtro) {
        obtenerUsuarioDetalladoRulesValidator.validar(filtro);
        UsuarioEntity usuario = usuarioRepository.findByIdAndSuscripcion_Id(filtro.getUsuarioId(), filtro.getSuscripcionId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<VentaEntity> ventas = ventaRepository.findAll(buildVentaSpecification(filtro));
        List<CompraEntity> compras = compraRepository.findAll(buildCompraSpecification(filtro));
        List<CuentaCobrarEntity> cuentasCobrar = cuentaCobrarRepository.findAll(buildCuentaCobrarSpecification(filtro));
        List<CuentaPagarEntity> cuentasPagar = cuentaPagarRepository.findAll(buildCuentaPagarSpecification(filtro));
        List<CobroEntity> cobros = cobroRepository.findAll(buildCobroSpecification(filtro));
        List<AbonoEntity> abonos = abonoRepository.findAll(buildAbonoSpecification(filtro));

        return UsuarioDetalladoDomain.create(usuario.getId(), usuario.getNombre(), usuario.getTelefono(), usuario.getTipoUsuario(), usuario.getEstado(), carteraEntityMapper.toDomain(usuario.getCartera()) ,
                compraEntityMapper.toDomainCollection(compras), cuentaPagarEntityMapper.toDomainCollection(cuentasPagar), abonoEntityMapper.toDomainCollection(abonos),
                ventaEntityMapper.toDomainCollection(ventas), cuentaCobrarEntityMapper.toDomainCollection(cuentasCobrar), cobroEntityMapper.toDomainCollection(cobros));
    }

    private Specification<CompraEntity> buildCompraSpecification(UsuarioDetalladoFiltroDTO filtro) {
        List<Specification<CompraEntity>> specs = new ArrayList<>();

        if (!UUIDHelper.isDefault(filtro.getSuscripcionId())) {
            specs.add((root, query, cb) -> cb.equal(root.get("suscripcion").get("id"), filtro.getSuscripcionId()));
        }
        if (filtro.getFechaInicio() != null) {
            specs.add((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("fechaVenta"), filtro.getFechaInicio()));
        }
        if (filtro.getFechaFin() != null) {
            specs.add((root, query, cb) -> cb.lessThanOrEqualTo(root.get("fechaVenta"), filtro.getFechaFin()));
        }
        if (!UUIDHelper.isDefault(filtro.getUsuarioId())) {
            specs.add((root, query, cb) -> cb.equal(root.get("proveedor").get("id"), filtro.getUsuarioId()));
        }
        specs.add((root, query, cb) -> cb.notEqual(root.get("estado"), EstadoVentaEnum.ANULADA));

        return Specification.allOf(specs);
    }

    private Specification<CuentaPagarEntity> buildCuentaPagarSpecification(UsuarioDetalladoFiltroDTO filtro) {
        List<Specification<CuentaPagarEntity>> specs = new ArrayList<>();

        if (!UUIDHelper.isDefault(filtro.getSuscripcionId())) {
            specs.add((root, query, cb) ->
                    cb.equal(root.get("suscripcion").get("id"), filtro.getSuscripcionId()));
        }

        if (!UUIDHelper.isDefault(filtro.getUsuarioId())) {
            specs.add((root, query, cb) ->
                    cb.equal(root.get("proveedor").get("id"), filtro.getUsuarioId()));
        }

        if (filtro.isSaldoPendiente()) {
            specs.add((root, query, cb) ->
                    cb.greaterThan(root.get("saldoPendiente"), BigDecimal.ZERO));
        }
        if (filtro.getFechaInicio() != null) {
            specs.add((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("fechaEmision"), filtro.getFechaInicio()));
        }
        if (filtro.getFechaFin() != null) {
            specs.add((root, query, cb) -> cb.lessThanOrEqualTo(root.get("fechaEmision"), filtro.getFechaFin()));
        }

        specs.add((root, query, cb) ->
                cb.notEqual(root.get("estado"), EstadoCuentaEnum.ANULADA));

        return Specification.allOf(specs);
    }

    private Specification<AbonoEntity> buildAbonoSpecification(UsuarioDetalladoFiltroDTO filtro) {
        List<Specification<AbonoEntity>> specs = new ArrayList<>();

        if (!UUIDHelper.isDefault(filtro.getSuscripcionId())) {
            specs.add((root, query, cb) ->
                    cb.equal(root.get("suscripcion").get("id"), filtro.getSuscripcionId()));
        }

        if (!UUIDHelper.isDefault(filtro.getUsuarioId())) {
            specs.add((root, query, cb) ->
                    cb.equal(root.get("cuentaPagar").get("proveedor").get("id"), filtro.getUsuarioId()));
        }

        if (filtro.getFechaInicio() != null) {
            specs.add((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("fechaPago"), filtro.getFechaInicio().atStartOfDay()));
        }

        if (filtro.getFechaFin() != null) {
            specs.add((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("fechaPago"), filtro.getFechaFin().atTime(23, 59, 59)));
        }

        specs.add((root, query, cb) ->
                cb.notEqual(root.get("estado"), EstadoAbonoEnum.ANULADO));

        return Specification.allOf(specs);
    }

    private Specification<VentaEntity> buildVentaSpecification(UsuarioDetalladoFiltroDTO filtro) {
        List<Specification<VentaEntity>> specs = new ArrayList<>();

        if (!UUIDHelper.isDefault(filtro.getSuscripcionId())) {
            specs.add((root, query, cb) ->
                    cb.equal(root.get("suscripcion").get("id"), filtro.getSuscripcionId()));
        }

        if (!UUIDHelper.isDefault(filtro.getUsuarioId())) {
            specs.add((root, query, cb) ->
                    cb.equal(root.get("cliente").get("id"), filtro.getUsuarioId()));
        }

        if (filtro.getFechaInicio() != null) {
            specs.add((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("fechaVenta"), filtro.getFechaInicio()));
        }

        if (filtro.getFechaFin() != null) {
            specs.add((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("fechaVenta"), filtro.getFechaFin()));
        }

        specs.add((root, query, cb) ->
                cb.notEqual(root.get("estado"), EstadoVentaEnum.ANULADA));

        return Specification.allOf(specs);
    }

    private Specification<CuentaCobrarEntity> buildCuentaCobrarSpecification(UsuarioDetalladoFiltroDTO filtro) {
        List<Specification<CuentaCobrarEntity>> specs = new ArrayList<>();

        if (!UUIDHelper.isDefault(filtro.getSuscripcionId())) {
            specs.add((root, query, cb) ->
                    cb.equal(root.get("suscripcion").get("id"), filtro.getSuscripcionId()));
        }

        if (!UUIDHelper.isDefault(filtro.getUsuarioId())) {
            specs.add((root, query, cb) ->
                    cb.equal(root.get("cliente").get("id"), filtro.getUsuarioId()));
        }

        if (filtro.isSaldoPendiente()) {
            specs.add((root, query, cb) ->
                    cb.greaterThan(root.get("saldoPendiente"), BigDecimal.ZERO));
        }

        if (filtro.getFechaInicio() != null) {
            specs.add((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("fechaEmision"), filtro.getFechaInicio()));
        }
        if (filtro.getFechaFin() != null) {
            specs.add((root, query, cb) -> cb.lessThanOrEqualTo(root.get("fechaEmision"), filtro.getFechaFin()));
        }

        specs.add((root, query, cb) ->
                cb.notEqual(root.get("estado"), EstadoCuentaEnum.ANULADA));

        return Specification.allOf(specs);
    }

    private Specification<CobroEntity> buildCobroSpecification(UsuarioDetalladoFiltroDTO filtro) {
        List<Specification<CobroEntity>> specs = new ArrayList<>();

        if (!UUIDHelper.isDefault(filtro.getSuscripcionId())) {
            specs.add((root, query, cb) ->
                    cb.equal(root.get("suscripcion").get("id"), filtro.getSuscripcionId()));
        }

        if (!UUIDHelper.isDefault(filtro.getUsuarioId())) {
            specs.add((root, query, cb) ->
                    cb.equal(
                            root.get("cuentaCobrar").get("cliente").get("id"),
                            filtro.getUsuarioId()
                    ));
        }

        if (filtro.getFechaInicio() != null) {
            specs.add((root, query, cb) ->
                    cb.greaterThanOrEqualTo(
                            root.get("fechaCobro"),
                            filtro.getFechaInicio().atStartOfDay()
                    ));
        }

        if (filtro.getFechaFin() != null) {
            specs.add((root, query, cb) ->
                    cb.lessThanOrEqualTo(
                            root.get("fechaCobro"),
                            filtro.getFechaFin().atTime(23, 59, 59)
                    ));
        }

        specs.add((root, query, cb) ->
                cb.notEqual(root.get("estado"), EstadoCobroEnum.ANULADO));

        return Specification.allOf(specs);
    }


}
