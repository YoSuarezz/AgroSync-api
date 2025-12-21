package com.agrosync.application.secondaryports.mapper.lotes;

import com.agrosync.application.secondaryports.entity.animales.AnimalEntity;
import com.agrosync.application.secondaryports.entity.compras.CompraEntity;
import com.agrosync.application.secondaryports.entity.lotes.LoteEntity;
import com.agrosync.application.secondaryports.mapper.animales.AnimalEntityMapper;
import com.agrosync.domain.animales.AnimalDomain;
import com.agrosync.domain.compras.CompraDomain;
import com.agrosync.domain.lotes.LoteDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {AnimalEntityMapper.class})
public interface LoteEntityMapper {

    @Mapping(target = "compra", ignore = true)
    @Mapping(target = "animales", source = "animales")
    LoteEntity toEntity(LoteDomain domain);

    @Mapping(target = "compra", source = "compra", qualifiedByName = "mapCompraBasico")
    @Mapping(target = "animales", source = "animales", qualifiedByName = "mapAnimalesBasico")
    @Mapping(target = "suscripcionId", source = "suscripcion.id")
    LoteDomain toDomain(LoteEntity entity);

    List<LoteEntity> toEntityCollection(List<LoteDomain> domainCollection);

    List<LoteDomain> toDomainCollection(List<LoteEntity> entityCollection);

    @Named("mapCompraBasico")
    default CompraDomain mapCompraBasico(CompraEntity entity) {
        if (entity == null) {
            return null;
        }
        CompraDomain domain = new CompraDomain();
        domain.setId(entity.getId());
        domain.setNumeroCompra(entity.getNumeroCompra());
        return domain;
    }

    @Named("mapAnimalesBasico")
    default List<AnimalDomain> mapAnimalesBasico(List<AnimalEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        }
        List<AnimalDomain> domains = new ArrayList<>();
        for (AnimalEntity entity : entities) {
            AnimalDomain domain = new AnimalDomain();
            domain.setId(entity.getId());
            domain.setNumeroAnimal(entity.getNumeroAnimal());
            domain.setSexo(entity.getSexo());
            domain.setPeso(entity.getPeso());
            domain.setEstado(entity.getEstado());
            domain.setPrecioKiloCompra(entity.getPrecioKiloCompra());
            domain.setPrecioKiloVenta(entity.getPrecioKiloVenta());
            domains.add(domain);
        }
        return domains;
    }
}
