package br.com.ilia.desafio.expediente.handlers;

import br.com.ilia.desafio.exceptions.DateException;
import br.com.ilia.desafio.expediente.Batida;
import br.com.ilia.desafio.expediente.Expediente;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public final class DateHandler extends BatidaHandler {

    public DateHandler(BatidaHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    public Expediente handle(Batida batida) {
        DayOfWeek day = LocalDateTime.parse(batida.momento()).getDayOfWeek();
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            throw new DateException("Sábado e domingo não são permitidos como dia de trabalho");
        }
        return next(batida);
    }
}
