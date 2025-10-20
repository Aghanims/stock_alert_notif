package edu.gmu.cs321;

public class Stock 
{
    private String tickerSymbol;
    private String companyName;
    private double currentPrice;

    public Stock(String tickerSymbol, String companyName, double currentPrice) 
    {
        this.tickerSymbol = tickerSymbol;
        this.companyName = companyName;
        this.currentPrice = currentPrice;
    }

    public void updatePrice(double newPrice) 
    {
        // TODO: Add validations
        
        this.currentPrice = newPrice;
    }

    public double getPrice() 
    {
        return currentPrice;
    }

    // Getters and setters
    public String getTickerSymbol() { return tickerSymbol; }
    public String getCompanyName() { return companyName; }

    public void setCompanyName(String companyName) { this.companyName = companyName; }
}
