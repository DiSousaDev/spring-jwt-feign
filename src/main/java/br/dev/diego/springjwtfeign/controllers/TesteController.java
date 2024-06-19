package br.dev.diego.springjwtfeign.controllers;

import br.dev.diego.springjwtfeign.services.CasalDTO;
import br.dev.diego.springjwtfeign.services.TesteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TesteController {

    private final TesteService service;

    public TesteController(TesteService service) {
        this.service = service;
    }

    @GetMapping("/teste")
    public CasalDTO teste() {
        return service.teste(4611L);
    }

}
