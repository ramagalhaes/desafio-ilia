package br.com.ilia.desafio.expediente;

import br.com.ilia.desafio.exceptions.BatidaExistsException;
import br.com.ilia.desafio.exceptions.ExpedienteValidationException;
import br.com.ilia.desafio.exceptions.FieldValidationException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ExpedienteServiceTest {

    @Mock
    private ExpedienteRepository expedienteRepository;

    private ExpedienteService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new ExpedienteService(expedienteRepository);
    }

    @Test
    void ShouldCreateExpediente_WhenIsFirstBatida() {
        // Setup
        String momento = "08:00:00";
        String dia = "2024-04-25";
        Batida batida = new Batida("2024-04-25T08:00:00");
        Expediente expediente = Expediente.builder()
                .dia(dia)
                .pontos(List.of(momento))
                .build();
        ExpedienteDto expectedResult = new ExpedienteDto(dia, List.of(momento));
        when(expedienteRepository.findByDia(dia)).thenReturn(Optional.empty());
        when(expedienteRepository.save(any(Expediente.class))).thenReturn(expediente);
        // Execute
        ExpedienteDto result = underTest.baterPonto(batida);

        // Verify
        assertEquals(expectedResult, result);
    }

    @Test
    void ShouldCreateBatida_WhenIsSecondBatida() {
        // Setup
        String momento = "08:00:00";
        String momento2 = "12:00:00";
        String dia = "2024-04-25";
        Batida batida = new Batida("2024-04-25T12:00:00");
        Expediente expediente = Expediente.builder()
                .dia(dia)
                .pontos(new ArrayList<>(List.of(momento)))
                .build();
        ExpedienteDto expectedResult = new ExpedienteDto(dia, List.of(momento, momento2));
        when(expedienteRepository.findByDia(dia)).thenReturn(Optional.of(expediente));
        when(expedienteRepository.save(any(Expediente.class))).thenReturn(expediente);
        // Execute
        ExpedienteDto result = underTest.baterPonto(batida);

        // Verify
        assertEquals(expectedResult, result);
    }

    @Test
    void ShouldCreateBatida_WhenIsComingBackFromLunchOneHourLater() {
        // Setup
        String momento = "08:00:00";
        String momento2 = "12:00:00";
        String momento3 = "13:00:00";
        String dia = "2024-04-25";
        Batida batida = new Batida("2024-04-25T13:00:00");
        Expediente expediente = Expediente.builder()
                .dia(dia)
                .pontos(new ArrayList<>(Arrays.asList(momento, momento2)))
                .build();
        ExpedienteDto expectedResult = new ExpedienteDto(dia, List.of(momento, momento2, momento3));
        when(expedienteRepository.findByDia(dia)).thenReturn(Optional.of(expediente));
        when(expedienteRepository.save(any(Expediente.class))).thenReturn(expediente);
        // Execute
        ExpedienteDto result = underTest.baterPonto(batida);

        // Verify
        assertEquals(expectedResult, result);
    }

    @Test
    void ShouldNotCreateBatida_WhenIsComingBackFromLunchFiftyNineMinutesLater() {
        // Setup
        String momento = "08:00:00";
        String momento2 = "12:00:00";
        String dia = "2024-04-25";
        Batida batida = new Batida("2024-04-25T12:59:59");
        Expediente expediente = Expediente.builder()
                .dia(dia)
                .pontos(List.of(momento, momento2))
                .build();
        when(expedienteRepository.findByDia(dia)).thenReturn(Optional.of(expediente));

        // Execute & Verify
        assertThrows(ExpedienteValidationException.class, () -> underTest.baterPonto(batida));
    }

    @Test
    void ShouldNotCreateBatida_WhenBatidaAlreadyExists() {
        // Setup
        String momento = "08:00:00";
        String dia = "2024-04-25";
        Batida batida = new Batida("2024-04-25T08:00:00");
        Expediente expediente = Expediente.builder()
                .dia(dia)
                .pontos(List.of(momento))
                .build();
        when(expedienteRepository.findByDia(dia)).thenReturn(Optional.of(expediente));

        // Execute & Verify
        assertThrows(BatidaExistsException.class, () -> underTest.baterPonto(batida));
    }

    @Test
    void ShouldNotCreateBatida_WhenFormatIsInvalid() {
        // Setup
        Batida batida = new Batida("2024-04-25T08:00");

        // Execute & Verify
        assertThrows(FieldValidationException.class, () -> underTest.baterPonto(batida));
    }

    @Test
    void ShouldNotCreateBatida_WhenHoursIsOutOfValidRange() {
        // Setup
        Batida batida = new Batida("2024-04-25T34:00:00");

        // Execute & Verify
        assertThrows(FieldValidationException.class, () -> underTest.baterPonto(batida));
    }

    @Test
    void ShouldNotCreateBatida_WhenMinutesIsOutOfValidRange() {
        // Setup
        Batida batida = new Batida("2024-04-25T23:60:00");

        // Execute & Verify
        assertThrows(FieldValidationException.class, () -> underTest.baterPonto(batida));
    }

    @Test
    void ShouldNotCreateBatida_WhenSecondsIsOutOfValidRange() {
        // Setup
        Batida batida = new Batida("2024-04-25T23:00:60");

        // Execute & Verify
        assertThrows(FieldValidationException.class, () -> underTest.baterPonto(batida));
    }

    @Test
    void FindByAnoMes_ShouldFindAllExpedientes_WhenDiaContainsAnoMes() {
        // Setup
        String ponto = "08:00:00";
        String anoMes = "2024-04";
        Expediente expectedExpediente = Expediente.builder()
                        .dia("2024-04-25")
                .pontos(List.of(ponto))
                .build();
        when(expedienteRepository.findAllByDiaContains("2024-04")).thenReturn(List.of(expectedExpediente));

        // Execute
        List<Expediente> result = underTest.findAllByAnoMes(anoMes);

        // Verify
        assertEquals(1, result.size());
        assertEquals(expectedExpediente, result.get(0));
    }
}
