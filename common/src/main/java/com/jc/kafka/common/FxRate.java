package com.jc.kafka.common;

import java.math.BigDecimal;
import java.time.Instant;

public record FxRate(
    String pair,
    BigDecimal rate,
    BigDecimal inverseRate,
    Instant timestamp
) {}