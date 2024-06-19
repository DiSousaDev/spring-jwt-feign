package br.dev.diego.springjwtfeign.config;

import br.dev.diego.springjwtfeign.services.JwtTokenService;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FeignRequestInterceptor implements RequestInterceptor {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        System.out.println("Interceptando requisição");
        String token = jwtTokenService.getToken();
        System.out.println("Token: " + token);
        requestTemplate.header("Authorization", "Bearer " + token);
    }
}
