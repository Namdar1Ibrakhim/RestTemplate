package com.example.resttemplate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
//Способ RestTemplate
public class PaymentsProxy {
    private final RestTemplate rest;

    @Value("${name.service.url}")
    private String paymentsServiceUrl;

    public PaymentsProxy(RestTemplate rest) {
        this.rest = rest;
    }
    //Получаем из файла свойств
    //URL сервиса платежей


    //С помощью DI внедряем
    //в конструкторе RestTemplate
    //из контекста Spring

    public Payment createPayment(Payment payment) {
        String uri = paymentsServiceUrl + "/payment";
        //создаем путь по каторому наше приложение должно сделать запрос

        HttpHeaders headers = new HttpHeaders();
        headers.add("requestId",
                UUID.randomUUID().toString());
        //Создаем объект HttpHeaders,
        //в котором определяются
        //заголовки HTTP-запроса

        HttpEntity<Payment> httpEntity =
                new HttpEntity<>(payment, headers);
        //Строим объект HttpEntity
        //с данными запроса

        ResponseEntity<Payment> response =
                rest.exchange(uri,
                        HttpMethod.POST,
                        httpEntity,
                        Payment.class);
        //Отправляем HTTP-запрос
        //и извлекаем данные из HTTP-ответа

        return response.getBody();
    }
}
