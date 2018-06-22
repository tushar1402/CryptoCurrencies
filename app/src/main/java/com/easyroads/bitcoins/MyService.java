package com.easyroads.bitcoins;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyService extends Service {
    PricesAdapter adapter;
    List<CoinPrice> mPrices = new ArrayList<>();

    Handler handler = new Handler();
    Timer timer = new Timer();
    private PricesService mService = APIUtils.getPricesService();


    @Override
    public void onCreate() {
        adapter = new PricesAdapter(this);

        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callApi();
                    }
                });
            }
        };
        timer.schedule(timerTask,0, 300000);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void callApi() {
        mService.getPrices().enqueue(new Callback<MarketCoin>() {
            @Override
            public void onResponse(Call<MarketCoin> call, Response<MarketCoin> response) {

                //adapter.updatePrices(response.body().getMarkets());
                mPrices = response.body().getMarkets();

                Intent intent = new Intent("ABCDE");
                intent.putExtra("prices", (Serializable) mPrices);
                LocalBroadcastManager.getInstance(MyService.this).sendBroadcast(intent);

            }

            @Override
            public void onFailure(Call<MarketCoin> call, Throwable t) {
                Toast.makeText(MyService.this, "Failed to Update", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

