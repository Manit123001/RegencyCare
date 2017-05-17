package com.example.mcnewz.regencycare.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.mcnewz.regencycare.R;
import com.example.mcnewz.regencycare.activity.MainActivity;
import com.example.mcnewz.regencycare.manager.config;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class LoginFragment extends Fragment {

    private EditText edtEmailLogin;
    private EditText edtPasswordLogin;
    private String uID;
    private Button btnSigninLogin;

    public LoginFragment() {
        super();
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        edtEmailLogin = (EditText) rootView.findViewById(R.id.edtEmailLogin);
        edtPasswordLogin = (EditText) rootView.findViewById(R.id.edtPasswordLogin);
        btnSigninLogin = (Button) rootView.findViewById(R.id.btnSigninLogin);
        btnSigninLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        // init instance with rootView.findViewById here
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
}

    private void login(){
        //Getting values from edit texts
        final String username = edtEmailLogin.getText().toString().trim();
        final String password = edtPasswordLogin.getText().toString().trim();
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equalsIgnoreCase(config.LOGIN_SUCCESS)){
                            SharedPreferences sharedPreferences = getContext().getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(config.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(config.USERNAME_SHARED_PREF, username);
                            editor.commit();
                            edtEmailLogin.setText("");
                            edtPasswordLogin.setText("");
                            uID = username;
                            updatetoken();
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getContext(), "Invalid username or password"+response, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(config.LOGIN_USERNAME, username);
                params.put(config.LOGIN_PASSWORD, password);
                return params;
            }
        };
        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void  updatetoken(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.TOKEN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("GGTT","อัพโทเคน"+config.token);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("department_id", edtEmailLogin.getText().toString().trim());
                params.put("department_token", config.token.toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}
