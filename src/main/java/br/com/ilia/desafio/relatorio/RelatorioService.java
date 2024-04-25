package br.com.ilia.desafio.relatorio;

import br.com.ilia.desafio.expediente.Expediente;
import br.com.ilia.desafio.expediente.ExpedienteDto;
import br.com.ilia.desafio.expediente.ExpedienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j

public class RelatorioService {

    private final ExpedienteService expedienteService;

    public Relatorio getReportByAnoMes(String anoMes) {
        return generateMonthlyReport(anoMes, expedienteService.findAllByAnoMes(anoMes));
    }

    private Relatorio generateMonthlyReport(String anoMes, List<Expediente> expedientes) {
        long totalSeconds = 0;
        List<ExpedienteDto> expedienteDtos = new ArrayList<>(expedientes.size());
        for (Expediente expediente : expedientes) {
            List<String> pontos = expediente.getPontos();
            long workedSeconds = 0;
            for (int j = 0; j < pontos.size() - 1; j += 2) {
                LocalTime start = LocalTime.parse(pontos.get(j));
                LocalTime end = LocalTime.parse(pontos.get(j + 1));

                Duration duration = Duration.between(start, end);
                workedSeconds += duration.getSeconds();
            }
            totalSeconds += workedSeconds;
            expedienteDtos.add(ExpedienteDto.fromEntity(expediente));
        }
        return new Relatorio(anoMes, totalSeconds, expedienteDtos);
    }

}
