package main.model;

import java.util.Map;

/**
 * Модель для работы с курсами валют от openexchangerates.org
 * Пример json`а:
 * https://openexchangerates.org/api/historical/2022-12-21.json?app_id=3b8c120bb3624e3eb59a8002fdc0c03e
 */
public class ExchangeRates {

    private String disclaimer;
    private String license;
    private Integer timestamp;
    private String base;
    private Map<String, Double> rates;

    public ExchangeRates() {
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
}
