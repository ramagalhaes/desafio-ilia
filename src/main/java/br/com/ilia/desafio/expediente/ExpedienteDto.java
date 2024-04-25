package br.com.ilia.desafio.expediente;

import java.util.List;

public record ExpedienteDto(String dia, List<String> pontos) {

    public static ExpedienteDto fromEntity(Expediente expediente) {
        return new ExpedienteDto(expediente.getDia(), expediente.getPontos());
    }

/*A medida que o record fosse crescendo poderiamos utilizar um builder.
* Não foi implementado nesse momento pela falta de complexidade
* */
}
