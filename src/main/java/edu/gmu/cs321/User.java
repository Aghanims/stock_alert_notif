package edu.gmu.cs321;

public class User 
{
    private String userID;
    private String username;
    private String passwordHash;
    private String email;
    private String phoneNumber;
    private Watchlist watchlist;

    public User(String userID, String username, String passwordHash, String email, String phoneNumber) 
    {
        this.userID = userID;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.watchlist = new Watchlist("wl-" + userID);
    }

    public boolean login(String username, String password) 
    {
        // TODO: authentication logic
        return false;
    }

    public void logout() 
    {
        // TODO: logout logic
    }

    public void createAlert(AlertForm formData) 
    {
        // TODO: alert creation logic
    }

    public Watchlist viewWatchlist() 
    {
        return watchlist;
    }

    // Getters and setters
    public String getUserID() { return userID; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public Watchlist getWatchlist() { return watchlist; }

    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}

