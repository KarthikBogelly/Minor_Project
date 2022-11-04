package org.tensorflow.demo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

public class Login extends AppCompatActivity {
    EditText lui, lps;
    private static final String URL = "http://wizzie.tech/leaf/login.php";
    private static final String URLF = "http://wizzie.tech/leaf/fp.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        lui = findViewById(R.id.lui);
        lps = findViewById(R.id.lps);
    }

    public void signup(View view) {
        startActivity(new Intent(Login.this,Register.class));
    }

    public void login(View view) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                     if(jsonObject.getString("result").equals("success")){
                         Toast.makeText(Login.this, "Login "+jsonObject.getString("result"), Toast.LENGTH_LONG).show();
                         startActivity(new Intent(Login.this,CameraRollActivity.class));
                     }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();
            }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                })
                {
                    @Override
                    protected Map<String,String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("u", lui.getText().toString().trim());
                        params.put("p",lps.getText().toString().trim());
                        return params;
                    }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void fp(View view) {
        final EditText editText=new EditText(Login.this);
        final AlertDialog alertDialog=new AlertDialog.Builder(Login.this)
                .setTitle("Enter Your Email")
                .setView(editText)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLF,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        JSONObject jsonObject =null;
                                        try {
                                            jsonObject =new JSONObject(response);
                                            Toast.makeText(Login.this, ""+jsonObject.getString("mobile"), Toast.LENGTH_LONG).show();

                                        }
                                        catch (JSONException e){
                                            e.printStackTrace();
                                        }

                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                            @Override
                            protected Map<String,String> getParams() throws AuthFailureError {
                                Map<String,String> params = new HashMap<String, String>();
                                params.put("u",editText.getText().toString().trim());
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
                        requestQueue.add(stringRequest);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
        alertDialog.show();
    }
}
