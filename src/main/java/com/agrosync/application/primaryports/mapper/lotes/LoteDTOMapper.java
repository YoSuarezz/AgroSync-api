package com.agrosync.application.primaryports.mapper.lotes;

import com.agrosync.application.primaryports.dto.animales.response.ObtenerAnimalDTO;
import com.agrosync.application.primaryports.dto.lotes.request.RegistrarNuevoLoteDTO;
import com.agrosync.application.primaryports.dto.lotes.response.ObtenerLoteDTO;
import com.agrosync.application.primaryports.mapper.animales.AnimalDTOMapper;
import com.agrosync.domain.animales.AnimalDomain;
import com.agrosync.domain.lotes.LoteDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

@Mapper(uses = {AnimalDTOMapper.class})
public interface LoteDTOMapper {

    LoteDTOMapper INSTANCE = Mappers.getMapper(LoteDTOMapper.class);

    LoteDomain toDomain(RegistrarNuevoLoteDTO dto);

    @Mapping(target = "compraId", source = "compra.id")
    @Mapping(target = "numeroCompra", source = "compra.numeroCompra")
    @Mapping(target = "cantidadAnimales", source = "animales", qualifiedByName = "contarAnimales")
    @Mapping(target = "animales", source = "animales", qualifiedByName = "mapAnimales")
    ObtenerLoteDTO toObtenerDTO(LoteDomain domain);

    List<ObtenerLoteDTO> toObtenerDTOCollection(List<LoteDomain> domainList);

    default Page<ObtenerLoteDTO> toObtenerDTOCollection(Page<LoteDomain> domainPage) {
        List<ObtenerLoteDTO> dtoList = toObtenerDTOCollection(domainPage.getContent());
        return new PageImpl<>(dtoList, domainPage.getPageable(), domainPage.getTotalElements());
    }

    @Named("contarAnimales")
    default Integer contarAnimales(List<AnimalDomain> animales) {
        return animales != null ? animales.size() : 0;
    }

    @Named("mapAnimales")
    default List<ObtenerAnimalDTO> mapAnimales(List<AnimalDomain> animales) {
        if (animales == null || animales.isEmpty()) {
            return new ArrayList<>();
        }
        return AnimalesDTOMapperHelper.toObtenerDTOCollection(animales);
    }
}

class AnimalesDTOMapperHelper {
    static List<ObtenerAnimalDTO> toObtenerDTOCollection(List<AnimalDomain> animales) {
        return AnimalDTOMapper.INSTANCE.toObtenerDTOCollection(animales);
    }
}
