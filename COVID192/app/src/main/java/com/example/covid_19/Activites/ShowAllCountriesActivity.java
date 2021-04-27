package com.example.covid_19.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_19.Adapter.CountryAdapter;
import com.example.covid_19.Model.Country;
import com.example.covid_19.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowAllCountriesActivity extends AppCompatActivity {

    RecyclerView recyclerView ;
    CountryAdapter adapter;
    List<Country> countryList;
    EditText textSaerch;
    RequestQueue requestQueue;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_countries);

        recyclerView = (RecyclerView)findViewById(R.id.Rv);
        countryList = new ArrayList<>();
        progressBar = findViewById(R.id.pro_id);
        progressBar.setVisibility(View.INVISIBLE);
        textSaerch = findViewById(R.id.txtSreach);

        FetchCountry();

        textSaerch.addTextChangedListener(new  TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(textSaerch.getText());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }



    private void FetchCountry() {

        progressBar.setVisibility(View.VISIBLE);
        String url = "https://api.covid19api.com/summary";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array=response.getJSONArray("Countries");
                    for (int i=0;i<array.length();i++)
                    {
                        Country country=new Country();
                        JSONObject object = array.getJSONObject(i);
                        country.setCountry(object.getString("Country"));
                        country.setCountryCode(object.getString("CountryCode"));
                        country.setNewConfirmed(object.getInt("NewConfirmed"));
                        country.setTotalConfirmed(object.getInt("TotalConfirmed"));
                        country.setNewDeaths(object.getInt("NewDeaths"));
                        country.setTotalDeaths(object.getInt("TotalDeaths"));
                        country.setNewRecovered(object.getInt("NewRecovered"));
                        country.setTotalRecovered(object.getInt("TotalRecovered"));
                        countryList.add(country);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }


                adapter = new CountryAdapter(ShowAllCountriesActivity.this,countryList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.INVISIBLE);


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue= Volley.newRequestQueue(ShowAllCountriesActivity.this);
        requestQueue.add(request);

    }

}
