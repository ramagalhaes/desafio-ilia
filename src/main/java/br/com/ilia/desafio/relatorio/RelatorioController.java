package br.com.ilia.desafio.relatorio;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/folhas-de-ponto")
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioService service;

    @GetMapping("/{anoMes}")
    public ResponseEntity<Relatorio> getReport(@PathVariable("anoMes") String anoMes) {
        return ResponseEntity.ok().body(service.getReportByAnoMes(anoMes));
    }

}
