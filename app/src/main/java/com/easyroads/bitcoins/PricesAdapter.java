package com.easyroads.bitcoins;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PricesAdapter extends RecyclerView.Adapter<PricesAdapter.ViewHolder> {
    private List<CoinPrice> mItems = Collections.emptyList();
    private LayoutInflater layoutInflater;
    private Context mcontext;
    private Integer currencyType = 0;
    private int selected_pos = RecyclerView.NO_POSITION;

    public PricesAdapter(Context mcontext) {
        this.mcontext = mcontext;
        this.layoutInflater = LayoutInflater.from(mcontext);
    }


    @Override
    public PricesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.rows, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final PricesAdapter.ViewHolder holder, final int position) {
        final CoinPrice item = mItems.get(position);
        holder.myTextViewName.setText(item.getName());
        holder.itemView.setSelected(selected_pos == position);
        switch (currencyType) {
            case 0:
                holder.myTextViewPrice.setText("$ " + item.getPriceUsd());
                break;
            case 1:
                holder.myTextViewPrice.setText("¥ " + item.getPriceCny());
                break;
            case 2:
                holder.myTextViewPrice.setText("€ " + item.getPriceEur());
                break;
            case 3:
                holder.myTextViewPrice.setText("£ " + item.getPriceGbp());
                break;
            case 4:
                holder.myTextViewPrice.setText("\u20BD " + item.getPriceRur());
                break;

        }
        holder.myTextViewTime.setText(item.getVolume24h() +"");

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void updatePrices(List<CoinPrice> mPrices) {
        mItems = mPrices;
        for(int i=0;i<mItems.size();i++) {
            if(mItems.get(i).getName().compareToIgnoreCase("bitcoin")==0) {
                Log.e("BITCOIN",mItems.get(i).getPriceUsd()+" $");
            }
        }
        notifyDataSetChanged();
    }

    public void updateCurrency(int i) {
        currencyType = i;
        notifyDataSetChanged();
    }

    public void setFilter(List<CoinPrice> newList) {
        newList = new ArrayList<>();
        newList.addAll(newList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextViewName, myTextViewPrice, myTextViewTime;
        Spinner mySpinner;

        public ViewHolder(View itemView) {
            super(itemView);
            mySpinner = itemView.findViewById(R.id.spinner1);
            myTextViewName = itemView.findViewById(R.id.nName);
            myTextViewPrice = itemView.findViewById(R.id.pPrice_num);
            myTextViewTime = itemView.findViewById(R.id.tVol_num);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
        notifyItemChanged(selected_pos);
        selected_pos = getLayoutPosition();
        notifyItemChanged(selected_pos);

        }
    }
}
