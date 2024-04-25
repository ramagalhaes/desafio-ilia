package br.com.ilia.desafio.expediente;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/batidas")
@RequiredArgsConstructor
public class BatidaController {

    private final ExpedienteService service;

    @PostMapping
    public ResponseEntity<ExpedienteDto> baterPonto(@RequestBody Batida batida) {
        return ResponseEntity.ok().body(service.baterPonto(batida));
    }

}
