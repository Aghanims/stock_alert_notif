package edu.gmu.cs321;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Lightweight real market data provider using Yahoo Finance public quote endpoint.
 * This is a best-effort demo: no API key required. Network failures return 0.0.
 */
public class RealMarketDataAPI extends MarketDataAPI {

    @Override
    public double fetchCurrentPrice(String ticker) {
        if (ticker == null || ticker.trim().isEmpty()) return 0.0;
        String sym = ticker.trim().toUpperCase();
        String endpoint = "https://query1.finance.yahoo.com/v7/finance/quote?symbols=" + sym;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(endpoint);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            int code = conn.getResponseCode();
            InputStream is = (code >= 200 && code < 300) ? conn.getInputStream() : conn.getErrorStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) sb.append(line);
            rd.close();
            String json = sb.toString();
            // crude parse: look for "regularMarketPrice":<number>
            String key = "\"regularMarketPrice\":";
            int idx = json.indexOf(key);
            if (idx >= 0) {
                int start = idx + key.length();
                int end = start;
                while (end < json.length()) {
                    char c = json.charAt(end);
                    if ((c >= '0' && c <= '9') || c == '.' || c == '-') end++; else break;
                }
                String num = json.substring(start, end);
                try { return Double.parseDouble(num); } catch (NumberFormatException nfe) { return 0.0; }
            }
        } catch (Throwable ex) {
            // ignore network issues for demo
        } finally {
            if (conn != null) conn.disconnect();
        }
        return 0.0;
    }
}
