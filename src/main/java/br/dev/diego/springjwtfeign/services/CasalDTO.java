package br.dev.diego.springjwtfeign.services;

import java.time.LocalDate;

public record CasalDTO(
        Long id,
        LocalDate dataCasamentoCivil,
        LocalDate dataCasamentoReligioso
) {
}
