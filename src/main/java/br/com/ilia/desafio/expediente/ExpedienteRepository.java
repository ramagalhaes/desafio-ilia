package br.com.ilia.desafio.expediente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpedienteRepository extends JpaRepository<Expediente, Long> {

    Optional<Expediente> findByDia(String dia);

    List<Expediente> findAllByDiaContains(String dia);
}
