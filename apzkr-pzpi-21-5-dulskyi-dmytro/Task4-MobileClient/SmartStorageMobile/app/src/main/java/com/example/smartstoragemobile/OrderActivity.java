package com.example.smartstoragemobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class OrderActivity extends AppCompatActivity {
    private final String[] countries = {"XL", "L", "M"};
    private TextInputEditText textWeight, textTypeOfProduct, textQuantity, textStorageLife, textTemperatureRange, textHumidityRange;
    private String size;
    private int sum = 150;
    private static final String urlAddProduct = "http://10.0.2.2:25016/api/addProduct";
    private static final String urlAddOrder = "http://10.0.2.2:25016/api/addOrdering";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Spinner spinner = findViewById(R.id.spinnerSize);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        textWeight = findViewById(R.id.textWeight);
        textTypeOfProduct = findViewById(R.id.textTypeOfProduct);
        textQuantity = findViewById(R.id.textQuantity);
        textStorageLife = findViewById(R.id.textStorageLife);
        textTemperatureRange = findViewById(R.id.textTemperatureRange);
        textHumidityRange = findViewById(R.id.textHumidityRange);
        Button buttonPay = findViewById(R.id.buttonPay);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                size = spinner.getSelectedItem().toString();

                switch (position) {
                    case 0:
                        sum = 150;
                        break;
                    case 1:
                        sum = 100;
                        break;
                    case 2:
                        sum = 50;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sum = sum * Integer.parseInt(Objects.requireNonNull(textQuantity.getText()).toString()) * 100;
                addProduct();
            }
        });

    }

    private void addProduct() {
        JSONObject js = new JSONObject();
        try {
            js.put("weight", textWeight.getText().toString());
            js.put("size", size);
            js.put("typeOfProduct", textTypeOfProduct.getText().toString());
            js.put("quantity", textQuantity.getText().toString());
            js.put("storageLife", textStorageLife.getText().toString());
            js.put("temperatureRange", textTemperatureRange.getText().toString());
            js.put("humidityRange", textHumidityRange.getText().toString());
            js.put("customerId", User.id);
            js.put("summa", sum);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, urlAddProduct, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(OrderActivity.this, "Product added", Toast.LENGTH_LONG).show();
                        getProductId();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(OrderActivity.this, "Error" + error.toString(), Toast.LENGTH_LONG).show();
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

    private void getProductId() {
        String urlGetProductId = "http://10.0.2.2:25016/api/getProductId/" + textWeight.getText() +
                "/" + size + "/" + textTypeOfProduct.getText() + "/" + textQuantity.getText() + "/"
                + textStorageLife.getText() + "/" + User.id;

        JsonArrayRequest json = new JsonArrayRequest(
                Request.Method.PUT, urlGetProductId, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray jsonarray = new JSONArray(response.toString());
                            User.productId = jsonarray.getString(0);
                            addOrdering();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(OrderActivity.this, "Error" + error.toString(), Toast.LENGTH_LONG).show();
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

    private void addOrdering(){
        JSONObject js = new JSONObject();
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        try {
            js.put("date", dateFormat.format(currentDate));
            js.put("productId", User.productId);
            js.put("size", size);
            js.put("status", true);
            js.put("sum", sum);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, urlAddOrder, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        getStorageId();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(OrderActivity.this, "Error" + error.toString(), Toast.LENGTH_LONG).show();
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

    private void getStorageId() {
        String urlGetStorageId = "http://10.0.2.2:25016/api/getStorageId/" + size;

        JsonArrayRequest json = new JsonArrayRequest(
                Request.Method.PUT, urlGetStorageId, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response.toString());
                            JSONArray innerJsonArray = jsonArray.getJSONArray(0);

                            User.StorageId = innerJsonArray.getString(0);
                            User.addressStorageId = innerJsonArray.getString(1);

                            updateStorageAddress();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(OrderActivity.this, "Error" + error.toString(), Toast.LENGTH_LONG).show();
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

    private void updateStorageAddress() {
        String updateStorageAddress = "http://10.0.2.2:25016/api/updateStorageAddress/" + User.productId + "/" + User.StorageId;

        JsonArrayRequest json = new JsonArrayRequest(
                Request.Method.PUT, updateStorageAddress, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        updateStorage();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(OrderActivity.this, "Error " + error.toString(), Toast.LENGTH_LONG).show();
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

    private void updateStorage() {
        String urlUpdateStorage = "http://10.0.2.2:25016/api/updateStorage/" + User.addressStorageId + "/true/" + User.productId;

        JsonArrayRequest json = new JsonArrayRequest(
                Request.Method.PUT, urlUpdateStorage, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        startActivity(new Intent(OrderActivity.this, MenuActivity.class));
                        finish();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(OrderActivity.this, "Authorization Error " + error.toString(), Toast.LENGTH_LONG).show();
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
}