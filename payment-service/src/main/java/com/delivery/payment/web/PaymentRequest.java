package com.delivery.payment.web;

import java.math.BigDecimal;

public record PaymentRequest(BigDecimal amount) {
}
