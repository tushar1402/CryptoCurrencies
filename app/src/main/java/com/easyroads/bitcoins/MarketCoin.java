package com.easyroads.bitcoins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MarketCoin  {
    @SerializedName("Markets")
    @Expose
    private List<CoinPrice> markets = null;

    public List<CoinPrice> getMarkets() {
        return markets;
    }

    public void setMarkets(List<CoinPrice> markets) {
        this.markets = markets;
    }

}
