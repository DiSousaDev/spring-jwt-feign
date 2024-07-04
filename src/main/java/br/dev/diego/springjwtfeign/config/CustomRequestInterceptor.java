package br.dev.diego.springjwtfeign.config;

import br.dev.diego.springjwtfeign.services.JwtTokenService;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLOutput;
import java.util.Collection;
import java.util.Map;

@Configuration
public class CustomRequestInterceptor implements RequestInterceptor {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        System.out.println("Interceptando requisição");

        Map<String, Collection<String>> headers = requestTemplate.headers();

        headers.forEach((s, strings) -> {
            System.out.println("Header Name " + s);
            strings.forEach(s1 -> {
                System.out.println("Values " + s1);
            });
        });

        String token = jwtTokenService.getToken();
        System.out.println("Token: " + token);
        requestTemplate.header("Authorization", "Bearer " + token);
    }
}
