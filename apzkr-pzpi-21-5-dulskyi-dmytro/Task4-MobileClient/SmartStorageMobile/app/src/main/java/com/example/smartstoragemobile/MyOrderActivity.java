package com.example.smartstoragemobile;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class MyOrderActivity extends AppCompatActivity {
    private RecyclerView recyclerViewCart;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        recyclerViewCart = findViewById(R.id.recyclerViewOrdering);
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCart.setHasFixedSize(true);
        scrollView = findViewById(R.id.scrollViewOrdering);

        Arrays.orderingArray = new ArrayList<>();
        getProductByCustomerId();
    }


    private void getProductByCustomerId() {
        String urlGetProductByCustomerId = "http://10.0.2.2:25016/api/getProductByCustomerId/" + User.id;

        JsonArrayRequest json = new JsonArrayRequest(
                Request.Method.PUT, urlGetProductByCustomerId, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response.toString());
                            JSONArray innerArray;

                            for (int i = 0; i < jsonArray.length(); i++) {
                                innerArray = jsonArray.optJSONArray(i);

                                for (int j = 0; j < innerArray.length(); j++) {
                                    Arrays.orderingArray.add(innerArray.getString(j));
                                }
                                Arrays.orderingArray.remove(Arrays.orderingArray.size() -1);
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
                        Toast.makeText(MyOrderActivity.this, "Error" + error.toString(), Toast.LENGTH_LONG).show();
                    }

                }) {
            @Override
            public Map<String, String> getHeaders() {
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
        scrollView.setVisibility(View.VISIBLE);

        CartAdapterOrdering cartAdapterOrdering = new CartAdapterOrdering(this);
        recyclerViewCart.setAdapter(cartAdapterOrdering);
    }
}