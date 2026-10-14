package com.delivery.payment.web;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    private final Random random = new Random();
    private final Environment environment;

    public PaymentController(Environment environment) {
        this.environment = environment;
    }

    @PostMapping("/payments")
    public ResponseEntity<PaymentResponse> pay(@RequestBody PaymentRequest request) {
        int port = Integer.parseInt(environment.getProperty("local.server.port", "8081"));
        if (random.nextBoolean()) {
            log.warn("Payment failed on instance {} for amount {}", port, request.amount());
            return ResponseEntity.internalServerError().build();
        }
        log.info("Payment approved on instance {} for amount {}", port, request.amount());
        return ResponseEntity.ok(new PaymentResponse("APPROVED", port));
    }
}
