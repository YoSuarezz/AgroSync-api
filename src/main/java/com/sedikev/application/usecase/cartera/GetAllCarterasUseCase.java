package com.sedikev.application.usecase.cartera;

import com.sedikev.application.usecase.UseCaseWithReturn;
import com.sedikev.domain.model.CarteraDomain;
import com.sedikev.application.secondaryports.repository.CarteraRepository;
import com.sedikev.application.primaryports.mapper.CarteraMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetAllCarterasUseCase implements UseCaseWithReturn<Void, List<CarteraDomain>> {

    private final CarteraRepository carteraRepository;
    private final CarteraMapper carteraMapper;

    @Override
    public List<CarteraDomain> ejecutar(Void unused) {
        // Obtener todas las carteras
        return carteraRepository.findAll().stream()
                .map(carteraMapper::toDomain)
                .collect(Collectors.toList());
    }
}
