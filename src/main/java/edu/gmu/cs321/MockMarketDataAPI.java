package edu.gmu.cs321;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple mockable market data provider. Callers can set prices for tickers.
 */
public class MockMarketDataAPI extends MarketDataAPI {
    private final Map<String, Double> prices = new ConcurrentHashMap<String, Double>();

    public MockMarketDataAPI() { }

    public void setPrice(String ticker, double price) { prices.put(ticker, price); }

    @Override
    public double fetchCurrentPrice(String ticker) {
        Double p = prices.get(ticker);
        return p == null ? 0.0 : p.doubleValue();
    }
}
