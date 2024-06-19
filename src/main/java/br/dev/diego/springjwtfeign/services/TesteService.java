package br.dev.diego.springjwtfeign.services;

import org.springframework.stereotype.Service;

@Service
public class TesteService {

    private final FeignClientMcc feignClientMcc;

    public TesteService(FeignClientMcc feignClientMcc) {
        this.feignClientMcc = feignClientMcc;
    }

    public CasalDTO teste(Long id) {
        return feignClientMcc.getCasal(id);
    }

}
