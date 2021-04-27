package com.example.covid_19.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.covid_19.Activites.CountryDetailsActivity;
import com.example.covid_19.Model.Country;
import com.example.covid_19.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.MyViewHolder> implements Filterable {

    Context mContent;
    List<Country>mData;
    List<Country>FilterData;


    public CountryAdapter(Context mContent, List<Country> mData) {
        this.mContent = mContent;
        this.mData = mData;
        this.FilterData = new ArrayList<>(mData);
    }

    @NonNull
    @Override
    public CountryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContent).inflate(R.layout.country_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.MyViewHolder holder, int position) {

        String ImageUrl ="https://www.countryflags.io/"+mData.get(position).getCountryCode()+"/flat/64.png";

        holder.CountryName.setText(mData.get(position).getCountry());
        Glide.with(mContent).load(ImageUrl).into(holder.CountryImage);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public Filter getFilter() {
        return  FilterListCountry;
    }


    private Filter FilterListCountry = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Country> FCountry= new ArrayList<>();
            if (constraint==null ||constraint.length()==0){
                FCountry.addAll(FilterData);
            }
            else {
                String key = constraint.toString().toLowerCase().trim();
                for (Country  item :FilterData){
                    if (item.getCountry().toLowerCase().contains(key)){

                        FCountry.add(item);
                    }
                }

            }
           FilterResults results =new FilterResults();
            results.values=FCountry;
            return  results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mData.clear();
            mData.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView CountryName;
        CircleImageView CountryImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            CountryImage= itemView.findViewById(R.id.country_img);
            CountryName= itemView.findViewById(R.id.country_name);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent countryDetailActivity = new Intent(mContent, CountryDetailsActivity.class);
                    countryDetailActivity.putExtra("Country",mData.get(position).getCountry());
                    countryDetailActivity.putExtra("NewConfirmed",mData.get(position).getNewConfirmed());
                    countryDetailActivity.putExtra("NewDeaths",mData.get(position).getNewDeaths());
                    countryDetailActivity.putExtra("NewRecovered",mData.get(position).getNewRecovered());
                    countryDetailActivity.putExtra("TotalConfirmed",mData.get(position).getTotalConfirmed());
                    countryDetailActivity.putExtra("TotalDeaths",mData.get(position).getTotalDeaths());
                    countryDetailActivity.putExtra("TotalRecovered",mData.get(position).getTotalRecovered());
                    mContent.startActivity(countryDetailActivity);

                }
            });
        }
    }
}
