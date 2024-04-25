package br.com.ilia.desafio.expediente.handlers;

import br.com.ilia.desafio.exceptions.BatidaExistsException;
import br.com.ilia.desafio.exceptions.ExpedienteValidationException;
import br.com.ilia.desafio.expediente.Batida;
import br.com.ilia.desafio.expediente.Expediente;
import br.com.ilia.desafio.expediente.ExpedienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ExpedienteHandlerTest {

    @Mock
    private ExpedienteRepository mockRepository;

    private ExpedienteHandler underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new ExpedienteHandler(null, mockRepository);
    }

    @Test
    void handle_NewExpediente_ReturnsExpedienteWithNewBatida() {
        // Setup
        String batidaMoment = "2024-04-25T08:30:00";
        Batida batida = new Batida(batidaMoment);
        when(mockRepository.findByDia("2024-04-25")).thenReturn(Optional.empty());
        Expediente expectedExpediente = new Expediente();

        // Execute
        Expediente result = underTest.handle(batida);

        // Verify
        assertEquals("2024-04-25", result.getDia());
        assertEquals(1, result.getPontos().size());
        assertEquals("08:30:00", result.getPontos().get(0));
        verify(mockRepository, times(1)).findByDia("2024-04-25");
    }

    @Test
    void handle_ExistingExpediente_ReturnsExpedienteWithNewBatida() {
        // Setup
        String batidaMoment = "2024-04-25T09:00:00";
        Batida batida = new Batida(batidaMoment);
        Expediente existingExpediente = new Expediente();
        existingExpediente.setDia("2024-04-25");
        when(mockRepository.findByDia("2024-04-25")).thenReturn(Optional.of(existingExpediente));

        // Execute
        Expediente result = underTest.handle(batida);

        // Verify
        assertEquals("2024-04-25", result.getDia());
        assertEquals(1, result.getPontos().size());
        assertEquals("09:00:00", result.getPontos().get(0));
        verify(mockRepository, times(1)).findByDia("2024-04-25");
    }

    @Test
    void handle_ExistingExpedienteWith2Batidas_ThrowsLunchTimeException() {
        // Setup
        String batidaMoment = "2024-04-25T12:30:00";
        Batida batida = new Batida(batidaMoment);
        String startMoment = "09:00:00";
        String outToLunch = "12:00:00";
        Expediente existingExpediente = new Expediente();
        existingExpediente.setDia("2024-04-25");
        existingExpediente.setPontos(Arrays.asList(startMoment, outToLunch));
        when(mockRepository.findByDia("2024-04-25")).thenReturn(Optional.of(existingExpediente));

        // Verify
        assertThrows(ExpedienteValidationException.class, () -> underTest.handle(batida));
    }

    @Test
    void handle_ExistingExpedienteWith2Batidas_ReturnsExpedienteWithComeBackFromLunch() {
        // Setup
        String batidaMoment = "2024-04-25T13:00:00";
        Batida batida = new Batida(batidaMoment);
        String startMoment = "09:00:00";
        String outToLunch = "12:00:00";
        Expediente existingExpediente = new Expediente();
        existingExpediente.setDia("2024-04-25");
        List<String> pontos = new ArrayList<>(3);
        pontos.add(startMoment);
        pontos.add(outToLunch);
        existingExpediente.setPontos(pontos);
        when(mockRepository.findByDia("2024-04-25")).thenReturn(Optional.of(existingExpediente));

        // Execute
        Expediente result = underTest.handle(batida);

        // Verify
        assertEquals("2024-04-25", result.getDia());
        assertEquals(3, result.getPontos().size());
        assertEquals(startMoment, result.getPontos().get(0));
        assertEquals(outToLunch, result.getPontos().get(1));
        assertEquals("13:00:00", result.getPontos().get(2));
        verify(mockRepository, times(1)).findByDia("2024-04-25");
    }

    @Test
    void handle_ExistingExpediente_ShouldThrowExceptionWhenBatidaAlreadyExists() {
        // Setup
        String batidaMoment = "2024-04-25T08:30:00";
        String existingPonto = "08:30:00";
        Batida batida = new Batida(batidaMoment);
        Expediente existingExpediente = new Expediente();
        String dia = "2024-04-25";
        existingExpediente.setDia(dia);
        existingExpediente.addPonto(existingPonto);
        when(mockRepository.findByDia(dia)).thenReturn(Optional.of(existingExpediente));

        // Execute
        assertThrows(BatidaExistsException.class, () -> underTest.handle(batida));
    }

    @Test
    void handle_ExistingExpediente_ShouldThrowExceptionWhenLimitOfBatidasIsReached() {
        // Setup
        String batidaMoment = "2024-04-25T19:30:00";
        String ponto1 = "08:30:00";
        String ponto2 = "09:30:00";
        String ponto3 = "11:30:00";
        String ponto4 = "18:30:00";
        Batida batida = new Batida(batidaMoment);
        Expediente existingExpediente = new Expediente();
        String dia = "2024-04-25";
        existingExpediente.setDia(dia);
        existingExpediente.setPontos(new ArrayList<>(Arrays.asList(ponto1, ponto2, ponto3, ponto4)));
        when(mockRepository.findByDia(dia)).thenReturn(Optional.of(existingExpediente));

        // Execute
        assertThrows(ExpedienteValidationException.class, () -> underTest.handle(batida));
    }
}
