package edu.gmu.cs321;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Watchlist 
{
    private String watchlistID;
    private final List<Stock> stocks = new ArrayList<>();

    public Watchlist(String watchlistID) 
    {
        this.watchlistID = watchlistID;
    }

    public void addStock(Stock stock) 
    {
        // TODO: mght need to add validations and duplicates check to make sure
        
        stocks.add(stock);
    }

    public void removeStock(Stock stock) 
    {
        stocks.remove(stock);
    }

    public List<Stock> getStocks() 
    {
        return Collections.unmodifiableList(stocks);
    }

    public String getWatchlistID() 
    {
        return watchlistID;
    }

    public void setWatchlistID(String watchlistID) 
    {
        this.watchlistID = watchlistID;
    }
}

