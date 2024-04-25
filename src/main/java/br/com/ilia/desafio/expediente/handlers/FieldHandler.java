package br.com.ilia.desafio.expediente.handlers;

import br.com.ilia.desafio.exceptions.FieldValidationException;
import br.com.ilia.desafio.expediente.Batida;
import br.com.ilia.desafio.expediente.Expediente;

import java.util.regex.Pattern;

public final class FieldHandler extends BatidaHandler {

    private static final Pattern ACCEPTED_DATE_PATTERN = Pattern
            .compile("\\d{4}-\\d{2}-\\d{2}T(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d");

    public FieldHandler(BatidaHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    public Expediente handle(Batida batida) {
        if (batida.momento() == null) {
            throw new FieldValidationException("Campo obrigatório não informado");
        }

        boolean isMomentoValid = ACCEPTED_DATE_PATTERN.matcher(batida.momento()).matches();
        if (!isMomentoValid) {
            throw new FieldValidationException("Data e hora em formato inválido");
        }
        return next(batida);
    }
}
