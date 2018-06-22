package com.easyroads.bitcoins;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    BroadcastReceiver receiver;
    private RecyclerView mRecyclerView;
    private EditText search;
    private PricesService mService;
    private PricesAdapter mAdapter;
    private Spinner mDropDown;
    private String[] items;
    private ArrayAdapter<String> mSpinneradapter;
    private static List<CoinPrice> names = new ArrayList<>();
    private Intent intentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mService = APIUtils.getPricesService();
        mRecyclerView = findViewById(R.id.recycler_view);
        search = findViewById(R.id.search);
        mDropDown = findViewById(R.id.spinner1);
        intentService = new Intent(MainActivity.this, MyService.class);
        items = new String[]{"USD", "CNY", "EUR", "GBP", "RUR"};
        mSpinneradapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, items);
        mAdapter = new PricesAdapter(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
     //   names = (ArrayList<CoinPrice>)getIntent().getSerializableExtra("prices");

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mDropDown.setAdapter(mSpinneradapter);
        addTextListener();
        startService(intentService);


        mDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                switch (position) {
                    case 0:
                        mAdapter.updateCurrency(0);
                        Toast.makeText(MainActivity.this, "USD", Toast.LENGTH_SHORT).show();
                        break;

                    case 1:
                        mAdapter.updateCurrency(1);
                        Toast.makeText(MainActivity.this, "YEN", Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        mAdapter.updateCurrency(2);
                        Toast.makeText(MainActivity.this, "EURO", Toast.LENGTH_SHORT).show();
                        break;

                    case 3:
                        mAdapter.updateCurrency(3);
                        Toast.makeText(MainActivity.this, "POUNDS", Toast.LENGTH_SHORT).show();
                        break;

                    case 4:
                        mAdapter.updateCurrency(4);
                        Toast.makeText(MainActivity.this, "RUSSIAN CURRENCY", Toast.LENGTH_SHORT).show();
                        break;

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        loadPrices();

 receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getSerializableExtra("prices") != null) {
                    if ((List<CoinPrice>) intent.getSerializableExtra("prices") != null) {
                        names = (List<CoinPrice>) intent.getSerializableExtra("prices");
                        mAdapter.updatePrices(names);
                    }else{
                        Log.e("HELLO","NO DATA");
                    }
                    Log.i("Data Updated", "In Service");
                }
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction("ABCDE");
//        registerReceiver(receiver, filter);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private void addTextListener() {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                s = s.toString().toLowerCase();
                final List<CoinPrice> filteredList = new ArrayList<>();
                for (int i = 0; i < names.size(); i++) {
                    final String text = names.get(i).getName().toLowerCase();
                    if (text.contains(s.toString())) {
                        filteredList.add(names.get(i));
                    }
                }
                if (filteredList.size() > 0) {
                    mAdapter.updatePrices(filteredList);
                } else {
                    mAdapter.updatePrices(names);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void loadPrices() {

        mService.getPrices().enqueue(new Callback<MarketCoin>() {
            @Override
            public void onResponse(Call<MarketCoin> call, Response<MarketCoin> response) {
                Log.e("MAD", "GOTUPDATE");
                names = response.body().getMarkets();
                mAdapter.updatePrices(names);

               // Handler handler = new Handler();
                //handler.postDelayed(new Runnable() {
                  //  @Override
                    //public void run() {
                      //  loadPrices();
                    //}
                //}, 300000);
            }

            @Override
            public void onFailure(Call<MarketCoin> call, Throwable t) {
                try {
                    Log.e("MAD", "NOGOTUPDATE");
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        if (item.getItemId() == R.id.up) {
            Collections.sort(names, new Comparator<CoinPrice>() {
                @Override
                public int compare(CoinPrice firstPrice, CoinPrice secondPrice) {
                    return Double.valueOf(secondPrice.getPriceUsd()).compareTo(firstPrice.getPriceUsd());

                }
            });
            mAdapter.updatePrices(names);
        } else {
            Collections.sort(names, new Comparator<CoinPrice>() {
                @Override
                public int compare(CoinPrice firstPrice, CoinPrice secondPrice) {

                    return Double.valueOf(firstPrice.getPriceUsd()).compareTo(secondPrice.getPriceUsd());

                }
            });
            mAdapter.updatePrices(names);
        }
        return super.onOptionsItemSelected(item);

    }

}


