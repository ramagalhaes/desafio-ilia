package br.com.ilia.desafio.expediente.handlers;

import br.com.ilia.desafio.expediente.Batida;
import br.com.ilia.desafio.expediente.Expediente;

public abstract sealed class BatidaHandler permits DateHandler, ExpedienteHandler, FieldHandler {

    private final BatidaHandler nextHandler;


    protected BatidaHandler(BatidaHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract Expediente handle(Batida batida);

    protected Expediente next(Batida batida) {
        if(nextHandler != null) {
            return nextHandler.handle(batida);
        }
        return null;
    }

}
