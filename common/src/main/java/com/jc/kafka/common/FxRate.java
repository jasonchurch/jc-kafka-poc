package com.jc.kafka.common;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Represents a Foreign Exchange (FX) rate for a currency pair at a specific point in time.
 *
 * @param pair        the currency pair identifier (e.g., "USD_CAD")
 * @param rate        the exchange rate
 * @param inverseRate the inverse of the exchange rate (1 / rate)
 * @param timestamp   the time when this rate was captured or generated
 */
public record FxRate(
    String pair,
    BigDecimal rate,
    BigDecimal inverseRate,
    Instant timestamp
) {}