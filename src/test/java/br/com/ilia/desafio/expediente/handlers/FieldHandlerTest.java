package br.com.ilia.desafio.expediente.handlers;

import br.com.ilia.desafio.exceptions.FieldValidationException;
import br.com.ilia.desafio.expediente.Batida;
import br.com.ilia.desafio.expediente.Expediente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class FieldHandlerTest {

    @Test
    void handle_ValidMoment_ReturnsNextHandlerResult() {
        // Setup
        Batida batida = new Batida("2024-04-25T08:30:00");
        BatidaHandler nextHandler = mock(DateHandler.class);
        FieldHandler underTest = new FieldHandler(nextHandler);
        Expediente expectedResult = new Expediente();
        when(nextHandler.handle(batida)).thenReturn(expectedResult);

        // Execute
        Expediente result = underTest.handle(batida);

        // Verify
        assertEquals(expectedResult, result);
        verify(nextHandler, times(1)).handle(batida);
    }

    @Test
    void handle_NullMoment_ThrowsFieldValidationException() {
        // Setup
        Batida batida = new Batida(null);
        FieldHandler fieldHandler = new FieldHandler(null);

        // Execute & Verify
        assertThrows(FieldValidationException.class, () -> fieldHandler.handle(batida));
    }

    @Test
    void handle_InvalidMomentFormat_ThrowsFieldValidationException() {
        // Setup
        Batida batida = new Batida("2024-04-25 08:30:00");
        FieldHandler fieldHandler = new FieldHandler(null);

        // Execute & Verify
        assertThrows(FieldValidationException.class, () -> fieldHandler.handle(batida));
    }
}
