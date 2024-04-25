package br.com.ilia.desafio.expediente.handlers;

import br.com.ilia.desafio.exceptions.DateException;
import br.com.ilia.desafio.expediente.Batida;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class DateHandlerTest {

    private DateHandler dateHandler;

    @BeforeEach
    public void setUp() {
        dateHandler = new DateHandler(null);
    }

    @Test
    void Should_Accept_Batida_On_Weekday() {
        Batida validWeekdayBatida = new Batida("2024-04-23T08:00:00");
        assertDoesNotThrow(() -> dateHandler.handle(validWeekdayBatida));
    }

    @Test
    void Should_Not_Accept_Batida_On_Saturday() {
        Batida saturdayBatida = new Batida("2024-04-27T08:00:00");
        DateException exception = assertThrows(DateException.class, () -> dateHandler.handle(saturdayBatida));
        assertEquals("Sábado e domingo não são permitidos como dia de trabalho", exception.getMessage());
    }

    @Test
    void Should_Not_Accept_Batida_On_Sunday() {
        Batida sundayBatida = new Batida("2024-04-28T08:00:00");
        DateException exception = assertThrows(DateException.class, () -> dateHandler.handle(sundayBatida));
        assertEquals("Sábado e domingo não são permitidos como dia de trabalho", exception.getMessage());
    }
}
