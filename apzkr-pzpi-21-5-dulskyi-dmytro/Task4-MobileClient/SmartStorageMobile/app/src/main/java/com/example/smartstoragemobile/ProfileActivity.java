package com.example.smartstoragemobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    Button update;
    TextInputEditText textInputEditText1, textInputEditText2, textInputEditText3, textInputEditText4, textInputEditText5;
    private static final String urlUser = "http://10.0.2.2:25016/api/user";
    private static final String urlUserUpdate = "http://10.0.2.2:25016/api/userUpdate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textInputEditText1 = findViewById(R.id.textInput1);
        textInputEditText2 = findViewById(R.id.textInput2);
        textInputEditText3 = findViewById(R.id.textInput3);
        textInputEditText4 = findViewById(R.id.textInput4);
        textInputEditText5 = findViewById(R.id.textInput5);
        update = findViewById(R.id.profile);

        getData();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
    }

    private void getData() {
        JsonObjectRequest json = new JsonObjectRequest(
                Request.Method.GET, urlUser, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jObj = new JSONObject(response.toString());
                            String firstName = jObj.getString("lName");
                            String lustName = jObj.getString("fName");
                            String age = jObj.getString("age");
                            String email = jObj.getString("email");
                            String phone = jObj.getString("phone");
                            setText(firstName, lustName, age, email, phone);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ProfileActivity.this, "Error" + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(ProfileActivity.this, "Error" + error.toString(), Toast.LENGTH_LONG).show();
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

    private void setText(String lName, String fName, String age, String email, String phone) {
        textInputEditText1.setText(lName);
        textInputEditText2.setText(fName);
        textInputEditText3.setText(age);
        textInputEditText4.setText(email);
        textInputEditText5.setText(phone);
    }

    private void updateData() {
        JSONObject js = new JSONObject();
        try {
            js.put("username", User.username);
            js.put("lName", textInputEditText1.getText().toString());
            js.put("fName", textInputEditText2.getText().toString());
            js.put("age", textInputEditText3.getText().toString());
            js.put("email", textInputEditText4.getText().toString());
            js.put("phone", textInputEditText5.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.PUT, urlUserUpdate, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(ProfileActivity.this, "Data updated", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ProfileActivity.this, MenuActivity.class));
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(ProfileActivity.this, "Update Error" + error.toString(), Toast.LENGTH_LONG).show();
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