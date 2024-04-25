package br.com.ilia.desafio.expediente;

import br.com.ilia.desafio.exceptions.BatidaExistsException;
import br.com.ilia.desafio.exceptions.ExpedienteValidationException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Expediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dia;

    @ElementCollection
    @CollectionTable
    private List<String> pontos = new ArrayList<>();

    public void addPonto(String ponto) {
        canRegisterNewPonto();
        validateIfPontoAlreadyExists(ponto);
        validateIfIsReturnFromLaunch(ponto);
        pontos.add(ponto);
    }

    private void validateIfPontoAlreadyExists(String ponto) {
        if (pontos.contains(ponto)) {
            throw new BatidaExistsException("Horário já registrado");
        }
    }

    private void validateIfIsReturnFromLaunch(String ponto) {
        if (pontos.size() == 2) {
            LocalTime saidaAlmoco = LocalTime.parse(pontos.get(1));
            LocalTime voltaAlmoco = LocalTime.parse(ponto);
            if (saidaAlmoco.plusHours(1).isAfter(voltaAlmoco)) {
                throw new ExpedienteValidationException("Deve haver no mínimo 1 hora de almoço");
            }

        }
    }

    private void canRegisterNewPonto() {
        if (pontos.size() == 4) {
            throw new ExpedienteValidationException("Apenas 4 horários podem ser registrados por dia");
        }
    }

}
