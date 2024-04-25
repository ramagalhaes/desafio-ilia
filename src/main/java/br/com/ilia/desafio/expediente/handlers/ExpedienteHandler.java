package br.com.ilia.desafio.expediente.handlers;

import br.com.ilia.desafio.expediente.Batida;
import br.com.ilia.desafio.expediente.Expediente;
import br.com.ilia.desafio.expediente.ExpedienteRepository;

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
        }
        expediente.addPonto(hora);
        return expediente;
    }
}
