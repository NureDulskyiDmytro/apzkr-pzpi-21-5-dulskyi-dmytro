package com.example.smartstoragemobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout root;
    private static final String urlRegister = "http://10.0.2.2:25016/api/register";
    private static final String urlAuthenticate = "http://10.0.2.2:25016/api/authenticate";
    private static final String urlUser = "http://10.0.2.2:25016/api/user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSignIn = findViewById(R.id.btnSignIn);
        Button btnRegister = findViewById(R.id.btnRegister);
        root = findViewById(R.id.root_element);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterWindow();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignInWindow();
            }
        });
    }

    private void showSignInWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getResources().getString(R.string.log_in));
        dialog.setMessage(getResources().getString(R.string.enter_your_login_information));
        LayoutInflater inflater = LayoutInflater.from(this);
        View sign_in_window = inflater.inflate(R.layout.sign_in_window, null);
        dialog.setView(sign_in_window);

        final MaterialEditText username = sign_in_window.findViewById(R.id.username);
        final MaterialEditText password = sign_in_window.findViewById(R.id.password);

        dialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton(getResources().getString(R.string.log_in), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if (TextUtils.isEmpty(username.getText().toString())) {
                    Snackbar.make(root, getResources().getString(R.string.enter_your_login), Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (password.getText().toString().length() < 5) {
                    Snackbar.make(root, getResources().getString(R.string.please_enter_a_password_that_is_more_than_5_characters), Snackbar.LENGTH_SHORT).show();
                    return;
                }

                // Auth User
                getToken(username, password);
                User.username = username;
                User.password = password;
            }
        });

        dialog.show();
    }

    private void showRegisterWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getResources().getString(R.string.registration_form));
        dialog.setMessage(getResources().getString(R.string.please_fill_in_all_fields));

        LayoutInflater inflater = LayoutInflater.from(this);
        View register_window = inflater.inflate(R.layout.register_window, null);
        dialog.setView(register_window);

        final MaterialEditText email = register_window.findViewById(R.id.emailField);
        final MaterialEditText username = register_window.findViewById(R.id.username);
        final MaterialEditText password = register_window.findViewById(R.id.password);
        final MaterialEditText phone = register_window.findViewById(R.id.phone);

        dialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton(getResources().getString(R.string.sign_up), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if (TextUtils.isEmpty(email.getText().toString())) {
                    Snackbar.make(root, getResources().getString(R.string.enter_mail), Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(username.getText().toString())) {
                    Snackbar.make(root, getResources().getString(R.string.enter_login), Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phone.getText().toString())) {
                    Snackbar.make(root, getResources().getString(R.string.enter_your_phone_number), Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (password.getText().toString().length() < 5) {
                    Snackbar.make(root, getResources().getString(R.string.enter_a_password_that_is_more_than_5_characters), Snackbar.LENGTH_SHORT).show();
                    return;
                }

                // Registration User
                registration(username, password, phone, email);
            }
        });
        dialog.show();
    }

    private void registration
            (MaterialEditText username, MaterialEditText password,
             MaterialEditText phone, MaterialEditText email) {
        JSONObject js = new JSONObject();
        try {
            js.put("username", username.getText().toString());
            js.put("email", email.getText().toString());
            js.put("password", password.getText().toString());
            js.put("phone", phone.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, urlRegister, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(MainActivity.this, "Register Success", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(MainActivity.this, "Register Error" + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);

    }

    private void getToken(MaterialEditText username, MaterialEditText password) {
        JSONObject js = new JSONObject();

        try {
            js.put("username", username.getText().toString());
            js.put("password", password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, urlAuthenticate, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            User.token = response.getString("id_token");
                            Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_LONG).show();
                            getData(User.token);
                            startActivity(new Intent(MainActivity.this, MenuActivity.class));
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Fail" + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MainActivity.this, "Fail" + error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(jsonObjReq);
    }

    private void getData(String token) {
        JsonObjectRequest json = new JsonObjectRequest(
                Request.Method.GET, urlUser, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jObj = new JSONObject(response.toString());
                            User.id = jObj.getString("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Authorization Error" + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(MainActivity.this, "Authorization Error" + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                headers.put("Accept", "application/json; charset=UTF-8");
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };

        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        rq.add(json);
    }
}


//private void registration
//        (MaterialEditText username, MaterialEditText password,
//         MaterialEditText phone, MaterialEditText email) {
//    JSONObject js = new JSONObject();
//
//    try {
//        js.put("username", username.getText().toString());
//        js.put("email", email.getText().toString());
//        js.put("password", password.getText().toString());
//        js.put("phone", phone.getText().toString());
//    } catch (JSONException e) {
//        e.printStackTrace();
//    }
//
//    JsonObjectRequest jsonObjReq = new JsonObjectRequest(
//            Request.Method.POST, urlRegister, js,
//            response -> Toast.makeText(MainActivity.this,
//                    "Register Success", Toast.LENGTH_LONG).show(),
//            error -> {
//                error.printStackTrace();
//                Toast.makeText(MainActivity.this, "Register Error"
//                        + error.toString(), Toast.LENGTH_LONG).show();
//            });
//
//    RequestQueue requestQueue = Volley.newRequestQueue(this);
//    requestQueue.add(jsonObjReq);
//}
//
//private void getToken(MaterialEditText username, MaterialEditText password) {
//    JSONObject js = new JSONObject();
//
//    try {
//        js.put("username", username.getText().toString());
//        js.put("password", password.getText().toString());
//    } catch (JSONException e) {
//        e.printStackTrace();
//    }
//
//    JsonObjectRequest jsonObjReq =
//            new JsonObjectRequest(Request.Method.POST, urlAuthenticate, js,
//            response -> {
//                try {
//                    User.token = response.getString("id_token");
//                    Toast.makeText(MainActivity.this, "Welcome",
//                            Toast.LENGTH_LONG).show();
//                    getData(User.token);
//                    startActivity(new Intent(MainActivity.this, MenuActivity.class));
//                    finish();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(MainActivity.this, "Fail"
//                            + e.toString(), Toast.LENGTH_LONG).show();
//                }
//            }, error -> {
//                error.printStackTrace();
//                Toast.makeText(MainActivity.this, "Fail"
//                        + error.toString(), Toast.LENGTH_LONG).show();
//            }) {
//    };
//
//    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//    queue.add(jsonObjReq);
//}
//
//private void getData(String token) {
//    JsonObjectRequest json = new JsonObjectRequest(
//            Request.Method.GET, urlUser, null,
//            response -> {
//                try {
//                    JSONObject jObj = new JSONObject(response.toString());
//                    User.id = jObj.getString("id");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(MainActivity.this, "Authorization Error"
//                            + e.toString(), Toast.LENGTH_LONG).show();
//                }
//            },
//            error -> {
//                error.printStackTrace();
//                Toast.makeText(MainActivity.this, "Authorization Error"
//                        + error.toString(), Toast.LENGTH_LONG).show();
//            }) {
//
//        @Override
//        public Map<String, String> getHeaders() {
//            HashMap<String, String> headers = new HashMap<>();
//            headers.put("Authorization", "Bearer " + token);
//            headers.put("Accept", "application/json; charset=UTF-8");
//            headers.put("Content-Type", "application/json; charset=UTF-8");
//            return headers;
//        }
//    };
//
//    RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
//    rq.add(json);
//}