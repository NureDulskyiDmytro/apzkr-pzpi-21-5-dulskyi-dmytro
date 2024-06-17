package com.example.smartstoragemobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class IndicatorActivity extends AppCompatActivity {
    private TextInputEditText textSearch;
    private TextView textView1, textView2, textView3;
    private EditText editText1, editText2;
    private String productId;
    private RelativeLayout relativeLayout;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);

        relativeLayout = findViewById(R.id.cardID);
        linearLayout = findViewById(R.id.updateButton);

        textSearch = findViewById(R.id.text_search);
        textView1 = findViewById(R.id.productId);

        textView2 = findViewById(R.id.temperatureRange);
        editText1 = findViewById(R.id.temperatureNow);
        textView3 = findViewById(R.id.humidityRange);
        editText2 = findViewById(R.id.humidityNow);

        Button btnSearch = findViewById(R.id.btn_search);
        Button btnUpdate = findViewById(R.id.btn_update);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProductById();
                relativeLayout.setVisibility(View.INVISIBLE);
                linearLayout.setVisibility(View.INVISIBLE);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
    }

    private void getProductById() {
        String urlGetProductById = "http://10.0.2.2:25016/api/getProductById/" + textSearch.getText();

        JsonObjectRequest json = new JsonObjectRequest(
                Request.Method.GET, urlGetProductById, null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
//                            JSONArray jsonarray = new JSONArray(response.toString());
//                            JSONObject jsonobject = jsonarray.getJSONObject(0);

                            JSONObject jsonobject = new JSONObject(response.toString());
                            productId = jsonobject.getString("productId");
                            textView1.setText(getResources().getString(R.string.product_id) + " "  + productId);

                            textView2.setText(getResources().getString(R.string.temperature_range_) + " " + jsonobject.getString("temperatureRange"));

                            editText1.setText(jsonobject.getString("temperatureNow"));

                            textView3.setText(getResources().getString(R.string.humidity_range_)+ " " + jsonobject.getString("humidityRange"));

                            editText2.setText(jsonobject.getString("humidityNow"));

                            relativeLayout.setVisibility(View.VISIBLE);
                            linearLayout.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(IndicatorActivity.this, "Error " + error.toString(), Toast.LENGTH_LONG).show();
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

        private void updateData() {
        String urlUpdateIndicator = "http://10.0.2.2:25016/api/updateIndicator/" + productId + "/" + editText1.getText().toString() + "/" + editText2.getText().toString() + "/" + User.id;

        JsonArrayRequest jsonObjReq = new JsonArrayRequest(
                Request.Method.PUT, urlUpdateIndicator, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Toast.makeText(IndicatorActivity.this, "Data updated", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(IndicatorActivity.this, MenuActivity.class));
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(IndicatorActivity.this, "Error" + error.toString(), Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);
    }
}