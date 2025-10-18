package edu.gmu.cs321;

import java.util.ArrayList;
import java.util.List;

public class AlertForm 
{
    private String ticker;
    private double targetPrice;
    private String condition; // "above", "below"
    private List<String> notificationMethods = new ArrayList<>();

    public AlertForm(String ticker, double targetPrice, String condition, List<String> notificationMethods) 
    {
        this.ticker = ticker;
        this.targetPrice = targetPrice;
        this.condition = condition;

        if (notificationMethods != null) 
            this.notificationMethods = new ArrayList<>(notificationMethods);
    }

    // Getters and setters
    public String getTicker() { return ticker; }
    public double getTargetPrice() { return targetPrice; }
    public String getCondition() { return condition; }
    public List<String> getNotificationMethods() { return new ArrayList<>(notificationMethods); }

    public void setTicker(String ticker) { this.ticker = ticker; }
    public void setTargetPrice(double targetPrice) { this.targetPrice = targetPrice; }
    public void setCondition(String condition) { this.condition = condition; }

    public void setNotificationMethods(List<String> notificationMethods) 
    {
        this.notificationMethods = new ArrayList<>(notificationMethods);
    }
}
