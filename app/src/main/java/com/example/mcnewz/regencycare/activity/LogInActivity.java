package com.example.mcnewz.regencycare.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mcnewz.regencycare.R;
import com.example.mcnewz.regencycare.fragment.LoginFragment;
import com.example.mcnewz.regencycare.manager.CheckNetwork;
import com.example.mcnewz.regencycare.manager.config;
import com.google.firebase.iid.FirebaseInstanceId;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.util.HashMap;
import java.util.Map;


public class LogInActivity extends AppCompatActivity {
    private boolean loggedIn = false;
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        final String token = FirebaseInstanceId.getInstance().getToken();
        config.token = token;
        Log.d("TGTG",token);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean(config.LOGGEDIN_SHARED_PREF, false);
        if(loggedIn){
            SharedPreferences sp = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            user_id = sp.getString(config.USERNAME_SHARED_PREF,"");

            // CheckInternet
            if (new CheckNetwork(Contextor.getInstance().getContext()).isNetworkAvailable()) {
                // your get/post related code..like HttpPost = new HttpPost(url);
                updatetoken();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                // No Internet
                Toast.makeText(Contextor.getInstance().getContext(), "no internet!", Toast.LENGTH_SHORT).show();
            }


        }else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer,
                            LoginFragment.newInstance(),
                            "RegencyTabShowFunction")
                    .commit();

        }
    }
    private void  updatetoken(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.TOKEN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "SplashSreenERROR", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("department_id", user_id);
                params.put("department_token", config.token);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
