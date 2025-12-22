package com.agrosync.domain.lotes.rules.impl;

import com.agrosync.application.secondaryports.repository.LoteRepository;
import com.agrosync.domain.lotes.LoteDomain;
import com.agrosync.domain.lotes.exceptions.ContramarcaSemanalExisteException;
import com.agrosync.domain.lotes.rules.ContramarcaSemanalUnicaRule;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.temporal.WeekFields;

@Service
public class ContramarcaSemanalUnicaRuleImpl implements ContramarcaSemanalUnicaRule {

    private final LoteRepository loteRepository;

    public ContramarcaSemanalUnicaRuleImpl(LoteRepository loteRepository) {
        this.loteRepository = loteRepository;
    }

    @Override
    public void validate(LoteDomain lote) {
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 4);
        int weekNumber = lote.getFecha().get(weekFields.weekOfWeekBasedYear());
        int year = lote.getFecha().getYear();

        if (loteRepository.existsByContramarcaAndWeekAndYearAndSuscripcionIdAndCompraNotAnulada(
                lote.getContramarca(),
                weekNumber,
                year,
                lote.getSuscripcionId())) {
            throw ContramarcaSemanalExisteException.create();
        }
    }
}
