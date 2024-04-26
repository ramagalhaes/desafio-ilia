package br.com.ilia.desafio.expediente;

import br.com.ilia.desafio.exceptions.BatidaExistsException;
import br.com.ilia.desafio.exceptions.ExpedienteValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExpedienteTest {

    private Expediente underTest;

    @BeforeEach
    void setUp() {
            underTest = new Expediente();
    }

    @Test
    void ShouldAddPonto_WhenPontosArrayIsEmpty() {
        // Setup
        String ponto = "09:00:00";

        // Execute
        underTest.addPonto(ponto);

        // Verify
        assertFalse(underTest.getPontos().isEmpty());
        assertEquals(ponto, underTest.getPontos().get(0));
    }

    @Test
    void ShouldAddPonto_WhenPontosArrayHasTwoElements() {
        // Setup
        String ponto1 = "09:00:00";
        underTest.setPontos(new ArrayList<>(List.of(ponto1)));
        String ponto2 = "10:00:00";

        // Execute
        underTest.addPonto(ponto2);

        // Verify
        assertFalse(underTest.getPontos().isEmpty());
        assertEquals(2, underTest.getPontos().size());
        assertEquals(ponto1, underTest.getPontos().get(0));
        assertEquals(ponto2, underTest.getPontos().get(1));
    }

    @Test
    void ShouldNotAddPonto_WhenThirdPontoDoesNotHaveLunchTime() {
        // Setup
        String ponto1 = "09:00:00";
        String ponto2 = "12:00:00";
        underTest.setPontos(new ArrayList<>(List.of(ponto1, ponto2)));
        String ponto3 = "12:59:59";

        // Execute & Verify
        assertThrows(ExpedienteValidationException.class, () -> underTest.addPonto(ponto3));
    }

    @Test
    void ShouldNotAddPonto_WhenPontoWithSameTimeExists() {
        // Setup
        String ponto1 = "09:00:00";
        String ponto2 = "09:00:00";
        underTest.setPontos(new ArrayList<>(List.of(ponto1)));

        // Execute & Verify
        assertThrows(BatidaExistsException.class, () -> underTest.addPonto(ponto2));
    }

    @Test
    void ShouldNotAddPonto_WhenThereAreFourPontos() {
        // Setup
        String ponto1 = "08:00:00";
        String ponto2 = "12:00:00";
        String ponto3 = "13:00:00";
        String ponto4 = "18:00:00";
        underTest.setPontos(new ArrayList<>(List.of(ponto1, ponto2, ponto3, ponto4)));

        // Execute & Verify
        assertThrows(ExpedienteValidationException.class, () -> underTest.addPonto("19:00:00"));
    }
}
