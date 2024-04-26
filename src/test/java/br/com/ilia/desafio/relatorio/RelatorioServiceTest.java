package br.com.ilia.desafio.relatorio;

import br.com.ilia.desafio.expediente.Expediente;
import br.com.ilia.desafio.expediente.ExpedienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class RelatorioServiceTest {

    @Mock
    private ExpedienteService expedienteService;

    private RelatorioService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new RelatorioService(expedienteService);
    }

    @Test
    void ShouldGenerateRelatorio_WhenExpedienteExists() {
        // Setup
        String anoMes = "2024-04";
        String horasTrabalhadas = "PT8H";
        String horasExcedentes = "PT0S";
        String horasDevidas = "PT0S";
        String momento1 = "09:00:00";
        String momento2 = "12:00:00";
        String momento3 = "13:00:00";
        String momento4 = "18:00:00";
        Expediente expediente = Expediente.builder()
                .dia("2024-04-25")
                .pontos(new ArrayList<>(List.of(momento1, momento2, momento3, momento4)))
                .build();

        when(expedienteService.findAllByAnoMes(anoMes)).thenReturn(new ArrayList<>(List.of(expediente)));

        // Execute
        Relatorio relatorio = underTest.getReportByAnoMes(anoMes);

        // Verify
        assertNotNull(relatorio);
        assertEquals(horasTrabalhadas, relatorio.getHorasTrabalhadas());
        assertEquals(horasExcedentes, relatorio.getHorasExcedentes());
        assertEquals(horasDevidas, relatorio.getHorasDevidas());
        assertFalse(relatorio.getExpedientes().isEmpty());
        assertFalse(relatorio.getExpedientes().get(0).pontos().isEmpty());
        assertEquals(momento1, relatorio.getExpedientes().get(0).pontos().get(0));
        assertEquals(momento2, relatorio.getExpedientes().get(0).pontos().get(1));
        assertEquals(momento3, relatorio.getExpedientes().get(0).pontos().get(2));
        assertEquals(momento4, relatorio.getExpedientes().get(0).pontos().get(3));
    }

    @Test
    void ShouldGenerateEmptyRelatorio_WhenExpedienteDoesNotExist() {
        // Setup
        String anoMes = "2024-04";
        String horasTrabalhadas = "PT0S";
        String horasExcedentes = "PT0S";
        String horasDevidas = "PT0S";

        Expediente expediente = Expediente.builder()
                .dia("2024-04-25")
                .pontos(new ArrayList<>(List.of()))
                .build();

        when(expedienteService.findAllByAnoMes(anoMes)).thenReturn(new ArrayList<>(List.of(expediente)));

        // Execute
        Relatorio relatorio = underTest.getReportByAnoMes(anoMes);

        // Verify
        assertNotNull(relatorio);
        assertEquals(horasTrabalhadas, relatorio.getHorasTrabalhadas());
        assertEquals(horasExcedentes, relatorio.getHorasExcedentes());
        assertEquals(horasDevidas, relatorio.getHorasDevidas());
        assertTrue(relatorio.getExpedientes().get(0).pontos().isEmpty());
    }
}
