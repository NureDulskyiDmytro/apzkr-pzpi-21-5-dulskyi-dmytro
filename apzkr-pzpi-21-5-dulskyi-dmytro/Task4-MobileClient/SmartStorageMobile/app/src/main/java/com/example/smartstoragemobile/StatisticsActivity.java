package com.example.smartstoragemobile;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StatisticsActivity extends AppCompatActivity {
    private ArrayList<String> countries = new ArrayList<>();
    private RecyclerView recyclerViewCart;
    private static final String urlStatistics1 = "http://10.0.2.2:25016/api/getAnalise2ByArrayList";
    private static final String urlStatistics2 = "http://10.0.2.2:25016/api/getAnalise3ByArrayList";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        countries.add(getResources().getString(R.string.statistics2));
        countries.add(getResources().getString(R.string.statistics1));

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        recyclerViewCart = findViewById(R.id.recyclerStatistics);
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCart.setHasFixedSize(true);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Arrays.statisticsArray = new ArrayList<>();

                switch (position) {
                    case 0:
                        getStatisticsOne();
                        break;
                    case 1:
                        getStatisticsTwo();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getStatisticsOne() {
        JsonArrayRequest json = new JsonArrayRequest(
                Request.Method.GET, urlStatistics1, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response.toString());
                            JSONArray innerArray;

                            for (int i = 0; i < jsonArray.length(); i++) {
                                innerArray = jsonArray.optJSONArray(i);

                                for (int j = 0, k = 0; j < innerArray.length(); j++, k++) {
                                   if(k == 0){
                                       Arrays.statisticsArray.add(getResources().getString(R.string.total_amount) + " " + innerArray.getString(j));
                                   } else if (k == 1){
                                       Arrays.statisticsArray.add(getResources().getString(R.string.item_size) + " " + innerArray.getString(j));
                                   } else {
                                       Arrays.statisticsArray.add(getResources().getString(R.string.total_count) + " " + innerArray.getString(j));
                                       k = -1;
                                   }
                                }
                            }

                            displayCartItems();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(StatisticsActivity.this, "Error" + error.toString(), Toast.LENGTH_LONG).show();
                    }

                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + User.token);
                headers.put("Accept", "application/json; charset=UTF-8");
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };

        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        rq.add(json);
    }

    private void getStatisticsTwo() {
        JsonArrayRequest json = new JsonArrayRequest(
                Request.Method.GET, urlStatistics2, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response.toString());
                            JSONArray innerArray;

                            for (int i = 0; i < jsonArray.length(); i++) {
                                innerArray = jsonArray.optJSONArray(i);

                                for (int j = 0, k = 0; j < innerArray.length(); j++, k++) {
                                    if(k == 0){
                                        Arrays.statisticsArray.add(getResources().getString(R.string.total_amount) + " " + innerArray.getString(j));
                                    } else if (k == 1){
                                        Arrays.statisticsArray.add(getResources().getString(R.string.type_of_product_) + " " + innerArray.getString(j));
                                    } else {
                                        Arrays.statisticsArray.add(getResources().getString(R.string.total_count) + " " + innerArray.getString(j));
                                        k = -1;
                                    }
                                }
                            }

                            displayCartItems();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(StatisticsActivity.this, "Error " + error.toString(), Toast.LENGTH_LONG).show();
                    }

                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + User.token);
                headers.put("Accept", "application/json; charset=UTF-8");
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };

        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        rq.add(json);
    }

    private void displayCartItems() {
        CartAdapterSearch cartAdapterSearch = new CartAdapterSearch(this);
        recyclerViewCart.setAdapter(cartAdapterSearch);
    }
}