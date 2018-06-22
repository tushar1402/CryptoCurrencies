package com.easyroads.bitcoins;

public class APIUtils {
    public static final String BASE_URL = "https://www.worldcoinindex.com/";

    public static PricesService getPricesService() {
        return RetrofitClient.getClient(BASE_URL).create(PricesService.class);
    }
}
