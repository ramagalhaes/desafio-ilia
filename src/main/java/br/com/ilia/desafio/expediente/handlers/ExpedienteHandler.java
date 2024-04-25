package br.com.ilia.desafio.expediente.handlers;

import br.com.ilia.desafio.exceptions.BatidaExistsException;
import br.com.ilia.desafio.exceptions.ExpedienteValidationException;
import br.com.ilia.desafio.expediente.Batida;
import br.com.ilia.desafio.expediente.Expediente;
import br.com.ilia.desafio.expediente.ExpedienteRepository;

import java.time.LocalTime;
import java.util.Optional;

public final class ExpedienteHandler extends BatidaHandler {

    private final ExpedienteRepository repository;

    public ExpedienteHandler(BatidaHandler nextHandler, ExpedienteRepository repository) {
        super(nextHandler);
        this.repository = repository;
    }

    @Override
    public Expediente handle(Batida batida) {
        String[] splittedMomento = batida.momento().split("T");
        String dia = splittedMomento[0];
        String hora = splittedMomento[1];
        Optional<Expediente> hasExpediente = repository.findByDia(dia);
        Expediente expediente = hasExpediente.orElseGet(Expediente::new);

        if (hasExpediente.isEmpty()) {
            expediente.setDia(dia);
            expediente.addPonto(hora);
            return expediente;
        }

        Optional<String> existingPonto = expediente.getPontos().stream().filter(p -> p.equals(hora)).findFirst();
        if (existingPonto.isPresent()) {
            throw new BatidaExistsException("Horário já registrado");
        }

        if (expediente.getPontos().size() == 2) {
            LocalTime saidaAlmoco = LocalTime.parse(expediente.getPontos().get(1));
            LocalTime voltaAlmoco = LocalTime.parse(hora);

            if (saidaAlmoco.plusHours(1).isAfter(voltaAlmoco)) {
                throw new ExpedienteValidationException("Deve haver no mínimo 1 hora de almoço");
            }

        }

        if (expediente.getPontos().size() == 4) {
            throw new ExpedienteValidationException("Apenas 4 horários podem ser registrados por dia");
        }

        expediente.addPonto(hora);
        return expediente;
    }
}
