package com.easyroads.bitcoins;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CoinPrice implements Serializable {
    @SerializedName("Label")
    @Expose
    private String label;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Price_btc")
    @Expose
    private Double priceBtc;
    @SerializedName("Price_usd")
    @Expose
    private Double priceUsd;
    @SerializedName("Price_cny")
    @Expose
    private Double priceCny;
    @SerializedName("Price_eur")
    @Expose
    private Double priceEur;
    @SerializedName("Price_gbp")
    @Expose
    private Double priceGbp;
    @SerializedName("Price_rur")
    @Expose
    private Double priceRur;
    @SerializedName("Volume_24h")
    @Expose
    private Double volume24h;
    @SerializedName("Timestamp")
    @Expose
    private Double timestamp;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPriceBtc() {
        return priceBtc;
    }

    public void setPriceBtc(Double priceBtc) {
        this.priceBtc = priceBtc;
    }

    public Double getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(Double priceUsd) {
        this.priceUsd = priceUsd;
    }

    public Double getPriceCny() {
        return priceCny;
    }

    public void setPriceCny(Double priceCny) {
        this.priceCny = priceCny;
    }

    public Double getPriceEur() {
        return priceEur;
    }

    public void setPriceEur(Double priceEur) {
        this.priceEur = priceEur;
    }

    public Double getPriceGbp() {
        return priceGbp;
    }

    public void setPriceGbp(Double priceGbp) {
        this.priceGbp = priceGbp;
    }

    public Double getPriceRur() {
        return priceRur;
    }

    public void setPriceRur(Double priceRur) {
        this.priceRur = priceRur;
    }

    public Double getVolume24h() {
        return volume24h;
    }

    public void setVolume24h(Double volume24h) {
        this.volume24h = volume24h;
    }

    public Double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Double timestamp) {
        this.timestamp = timestamp;
    }

}
