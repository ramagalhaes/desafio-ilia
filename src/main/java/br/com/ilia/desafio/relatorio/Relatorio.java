package br.com.ilia.desafio.relatorio;

import br.com.ilia.desafio.expediente.ExpedienteDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class Relatorio {

    private String anoMes;
    private String horasTrabalhadas;
    private String horasExcedentes;
    private String horasDevidas;
    private List<ExpedienteDto> expedientes;

    private static final Long EXPECTED_MONTHLY_HOURS = 168L * 60L * 60L;

    public Relatorio(String anoMes, Long horasTrabalhadasEmSegundos, List<ExpedienteDto> expedientes) {
        this.anoMes = anoMes;
        this.expedientes = expedientes;
        horasTrabalhadas = convertSecondsToIso8601(horasTrabalhadasEmSegundos);
        horasExcedentes = convertSecondsToIso8601(calculateDifference(horasTrabalhadasEmSegundos, EXPECTED_MONTHLY_HOURS));
        horasDevidas = convertSecondsToIso8601(calculateDifference(EXPECTED_MONTHLY_HOURS, horasTrabalhadasEmSegundos));
    }

    private String convertSecondsToIso8601(long seconds) {
        return Duration.of(seconds, ChronoUnit.SECONDS).toString();
    }

    private long calculateDifference(long seconds, long seconds2) {
        long diff = seconds - seconds2;
        if(diff <= 0) { diff = 0; }
        return diff;
    }

}
