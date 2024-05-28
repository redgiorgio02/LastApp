package com.example.ergasia2024;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LogInActivity extends AppCompatActivity {

    EditText txt_name, txt_password;

    Button signup_btn;
    Button login_btn;
    String url_login = "http://192.168.56.1/Tutorial/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signup_btn = (Button) findViewById(R.id.button_signup);
        txt_name = findViewById(R.id.nameFieldlogin);
        txt_password = findViewById(R.id.passFieldlogin);
        login_btn = findViewById(R.id.button_login);


    }

    private void GoLogin() {
        String username = txt_name.getText().toString().trim();
        String password = txt_password.getText().toString().trim();

        if (username.isEmpty()) {
            Toast.makeText(getApplicationContext(), "PLEASE ENTER USERNAME", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "PLEASE ENTER PASSWORD", Toast.LENGTH_SHORT).show();
        } else {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();


            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = new jsonObject.getJSONArray("login");
                                if (success.equals("1")) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        String ojbuserid = object.getString("userid");
                                        String ojbusername = object.getString("username").trim();
                                        Toast.makeText(LogInActivity.this, "User Id: " + ojbuserid + " Name: " + ojbusername, Toast.LENGTH_SHORT);
                                        progressDialog.dismiss();
                                    }
                                }
                                progressDialog.dismiss();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(LogInActivity.this, "Error" + e.toString(), Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LogInActivity.this, "Error" + error.toString(), Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("password", password);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }







    public void SignUp(View v) {
        startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
    }



}
