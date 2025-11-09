package edu.gmu.cs321;

public class Alert 
{
    private String alertID;
    private String ticker;
    private double targetPrice;
    private String triggerCondition; // like "above", "below", "equal"
    private String alertType;        // "email", "sms", "push"
    private String status;           //  "active", "triggered", "cancelled"

    public Alert(String alertID, String ticker, double targetPrice, String triggerCondition, String alertType, String status) 
    {
        this.alertID = alertID;
        this.ticker = ticker;
        this.targetPrice = targetPrice;
        this.triggerCondition = triggerCondition;
        this.alertType = alertType;
        this.status = status;
    }

    public boolean checkTrigger(double currentPrice) 
    {
        if (triggerCondition == null) return false;
        String c = triggerCondition.toLowerCase();
        switch (c) {
            case "above":
                return currentPrice > targetPrice;
            case "below":
                return currentPrice < targetPrice;
            case "equal":
                return Double.compare(currentPrice, targetPrice) == 0;
            default:
                return false;
        }
    }

    public void updateStatus(String newStatus) 
    {
        this.status = newStatus;
    }

    // Getters and setters
    public String getAlertID() { return alertID; }
    public String getTicker() { return ticker; }
    public double getTargetPrice() { return targetPrice; }
    public String getTriggerCondition() { return triggerCondition; }
    public String getAlertType() { return alertType; }
    public String getStatus() { return status; }

    public void setTargetPrice(double targetPrice) { this.targetPrice = targetPrice; }
    public void setTriggerCondition(String triggerCondition) { this.triggerCondition = triggerCondition; }
    public void setAlertType(String alertType) { this.alertType = alertType; }
    public void setTicker(String ticker) { this.ticker = ticker; }
}
