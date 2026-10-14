package com.delivery.order.client;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class PaymentClient {

    private static final Logger log = LoggerFactory.getLogger(PaymentClient.class);

    private final RestTemplate restTemplate;

    public PaymentClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Retryable(
            includes = RestClientException.class,
            maxRetries = 8,
            delay = 80,
            multiplier = 2.0,
            jitter = 30,
            maxDelay = 1000)
    public PaymentResult charge(BigDecimal amount) {
        try {
            PaymentResult result = restTemplate.postForObject(
                    "http://PAYMENT-SERVICE/payments",
                    Map.of("amount", amount),
                    PaymentResult.class);
            log.info("Payment approved by instance {}", result == null ? "unknown" : result.instance());
            return result;
        } catch (RestClientException exception) {
            throw exception;
        } catch (RuntimeException exception) {
            throw new RestClientException("Payment service unavailable", exception);
        }
    }
}
