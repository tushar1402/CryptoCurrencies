package com.easyroads.bitcoins;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface PricesService {




    @GET("apiservice/json?key=dZBerU79abO7ntlX6vTqfFk7zJWK58")
    Call<MarketCoin> getPrices();
}
