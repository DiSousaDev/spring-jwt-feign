package br.dev.diego.springjwtfeign.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "mcc", url = "https://mcc-production.up.railway.app")
public interface FeignClientMcc {

    @RequestMapping(value = "/casais/{id}", headers = "Teste-HEader=Teste-HeaderValue")
    CasalDTO getCasal(@PathVariable("id") Long id);

}
