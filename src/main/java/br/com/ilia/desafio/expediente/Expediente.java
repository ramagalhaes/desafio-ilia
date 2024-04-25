package br.com.ilia.desafio.expediente;

import jakarta.persistence.*;
import lombok.*;

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
        this.pontos.add(ponto);
    }

}
