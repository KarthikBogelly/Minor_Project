package org.tensorflow.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
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

public class Register extends AppCompatActivity {
    EditText name, id, mobile, email, password;
    private static final String URL = "http://wizzie.tech/leaf/register.php";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        id = findViewById(R.id.id);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mob);
        password = findViewById(R.id.ps);
    }

    public void reg(View view) {
        if (name.getText().toString().equals("")) {
            Toast.makeText(this, "Enter User name", Toast.LENGTH_SHORT).show();
        } else if (id.getText().toString().equals("")) {
            Toast.makeText(this, "Enter User ID", Toast.LENGTH_SHORT).show();
        } else if (mobile.getText().toString().equals("")) {
            Toast.makeText(this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
        } else if (password.getText().toString().equals("")) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        } else if (email.getText().toString().matches(emailPattern)) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getString("result").equals("success")) {
                                    startActivity(new Intent(Register.this, Login.class));

                                    Toast.makeText(Register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Register.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("u", name.getText().toString());
                    params.put("i", id.getText().toString());
                    params.put("e", email.getText().toString());
                    params.put("m", mobile.getText().toString());
                    params.put("p", password.getText().toString());
                    return params;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        } else {
            Toast.makeText(this, "Enter Correct Email", Toast.LENGTH_SHORT).show();
        }

    }
}
