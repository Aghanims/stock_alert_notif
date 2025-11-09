package edu.gmu.cs321;

public class MarketDataAPI 
{
    public double fetchCurrentPrice(String ticker) 
    {
        if (ticker == null || ticker.trim().isEmpty()) {
            throw new IllegalArgumentException("Ticker cannot be empty");
        }

        // For now, generate a random price between 10 and 1000
        // In a real implementation, this would call an external market data API
        return 10.0 + Math.random() * 990.0;
    }
}
