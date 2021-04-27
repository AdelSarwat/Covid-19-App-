package com.example.covid_19.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid_19.Adapter.CountryAdapter;
import com.example.covid_19.Model.Country;
import com.example.covid_19.R;

public class CountryDetailsActivity extends AppCompatActivity {

    TextView totalAffected,totalDeaths,NewAFFceed,New_recover,New_Deaths,Coun_Name;
    Country country;
    Button btn_all_Country,btn_myCountry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);
        Coun_Name = findViewById(R.id.txt_name);
        totalAffected = findViewById(R.id.total_af);
        totalDeaths = findViewById(R.id.tot_deaths);
        NewAFFceed = findViewById(R.id.new_aff);
        New_recover = findViewById(R.id.new_rec);
        New_Deaths = findViewById(R.id.new_dis);
        btn_all_Country = findViewById(R.id.btn_allCountry);
        btn_myCountry = findViewById(R.id.btn_Mycountry);

        Coun_Name.setText(getIntent().getExtras().getString("Country"));
        totalAffected.setText(String.valueOf(getIntent().getExtras().getInt("TotalConfirmed")));
        totalDeaths.setText(String.valueOf(getIntent().getExtras().getInt("TotalDeaths")));
        NewAFFceed.setText(String.valueOf(getIntent().getExtras().getInt("NewConfirmed")));
        New_recover.setText(String.valueOf(getIntent().getExtras().getInt("NewRecovered")));
        New_Deaths.setText(String.valueOf(getIntent().getExtras().getInt("NewDeaths")));

        btn_all_Country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountryDetailsActivity.this,ShowAllCountriesActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_myCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
