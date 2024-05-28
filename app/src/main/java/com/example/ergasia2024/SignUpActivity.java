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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    Button submit_btn;
    EditText txt_name, txt_password, txt_password2;
    String url_signup = "http://192.168.56.1/Tutorial/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        txt_name = findViewById(R.id.nameFieldsign);
        txt_password = findViewById(R.id.passFieldsign1);
        txt_password2 = findViewById(R.id.passFieldsign2);
        submit_btn = (Button) findViewById(R.id.button_submit);

        Button btn = (Button)findViewById(R.id.button_back);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
            }
        });
    }

    public void GoRegister() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        String username = txt_name.getText().toString().trim();
        String password = txt_password.getText().toString().trim();
        String password2 = txt_password.getText().toString().trim();

        if(username.isEmpty()){
                Toast.makeText(SignUpActivity.this, "Insert Username", Toast.LENGTH_LONG).show();
        } else if(password.isEmpty()){
            Toast.makeText(SignUpActivity.this, "Insert Password", Toast.LENGTH_LONG).show();
        } else if(password2.isEmpty()){
            Toast.makeText(SignUpActivity.this, "Insert Confirm Password", Toast.LENGTH_LONG).show();
        } else if(!password.equals(password2)){
            Toast.makeText(SignUpActivity.this, "Passwords don't match", Toast.LENGTH_LONG).show();
        }else {
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_signup, new Response.Listener<String>(){
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {
                            progressDialog.dismiss();
                            Toast.makeText(SignUpActivity.this,"Register Successful ", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(SignUpActivity.this,e.toString(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                    }
                },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SignUpActivity.this, error.toString(), Toast.LENGTH_LONG).show();
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

}