package main.service.impl;

import main.client.ExchangeRatesClient;
import main.model.ExchangeRates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.service.ExchangeRatesService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Сервис для работы с openexchangerates.org
 */
@Service
public class ExchangeRatesServiceImpl implements ExchangeRatesService {

    private ExchangeRatesClient exchangeRatesClient;
    private String appId = "109cf6e38a2b411e83a4bb1ca4d37b5b";

    @Autowired
    public ExchangeRatesServiceImpl(ExchangeRatesClient exchangeRatesClient) {
        this.exchangeRatesClient = exchangeRatesClient;
    }

    @Override
    public List<String> getCharCodes() {
        ExchangeRates latestRates = exchangeRatesClient.getLatestRates(this.appId);
        return new ArrayList<>(latestRates.getRates().keySet());
    }

    @Override
    public int getKeyForTag(String charCode) {
        ExchangeRates latestRates = exchangeRatesClient.getLatestRates(this.appId);
        String newPrevDate = getYesterday();
        ExchangeRates oldRates = exchangeRatesClient.getHistoricalRates(newPrevDate, appId);

        Double prevCoefficient = oldRates.getRates().get(charCode);
        Double currentCoefficient = latestRates.getRates().get(charCode);

        return Double.compare(currentCoefficient, prevCoefficient);
    }

    private String getYesterday() {
        Calendar prevCalendar = Calendar.getInstance();
        prevCalendar.setTimeInMillis(System.currentTimeMillis());
        prevCalendar.add(Calendar.DAY_OF_YEAR, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(prevCalendar.getTime());
    }
}
