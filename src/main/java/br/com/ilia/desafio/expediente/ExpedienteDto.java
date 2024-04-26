package br.com.ilia.desafio.expediente;

import java.util.List;

public record ExpedienteDto(String dia, List<String> pontos) {

    public static ExpedienteDto fromEntity(Expediente expediente) {
        return new ExpedienteDto(expediente.getDia(), expediente.getPontos());
    }
}
