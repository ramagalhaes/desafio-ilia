package br.com.ilia.desafio.expediente;

import br.com.ilia.desafio.expediente.handlers.DateHandler;
import br.com.ilia.desafio.expediente.handlers.ExpedienteHandler;
import br.com.ilia.desafio.expediente.handlers.FieldHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpedienteService {

    private final ExpedienteRepository repository;

    public ExpedienteDto baterPonto(Batida batida) {
        Expediente expediente = createBatida(batida);
        return ExpedienteDto.fromEntity(repository.save(expediente));
    }

    public List<Expediente> findAllByAnoMes(String anoMes) {
        return repository.findAllByDiaContains(anoMes);
    }

    private Expediente createBatida(Batida batida) {
        return new FieldHandler(
                new DateHandler(
                        new ExpedienteHandler(null, repository)
                )
        ).handle(batida);
    }

}
