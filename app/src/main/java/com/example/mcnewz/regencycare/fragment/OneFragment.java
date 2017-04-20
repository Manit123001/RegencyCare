package com.example.mcnewz.regencycare.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mcnewz.regencycare.R;
import com.example.mcnewz.regencycare.activity.LogInActivity;
import com.example.mcnewz.regencycare.manager.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class OneFragment extends Fragment {

    private Button btnOut;
    private String user_id;
    private ImageView iv_logo;
    private TextView txt_nameDepart;
    private TextView txt_address;
    private TextView txt_phone;
    private TextView txt_email;

    String nameDepart,address,phone,email,typeDepart;

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // return inflater.inflate(R.layout.fragment_profile, container, false);

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        initInstances(rootView);
        return rootView;


    }

    private void initInstances(View rootView) {
        iv_logo = (ImageView)rootView.findViewById(R.id.iv_logo);
        txt_nameDepart = (TextView)rootView.findViewById(R.id.txtNameDepart);
        txt_address = (TextView)rootView.findViewById(R.id.txtAddress);
        txt_email = (TextView)rootView.findViewById(R.id.txtEmail);
        txt_phone = (TextView)rootView.findViewById(R.id.txtPhon);



        getData();

        btnOut = (Button)rootView.findViewById(R.id.btnOut);
        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Getting out sharedpreferences
                SharedPreferences preferences = getActivity().getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                //Getting editor
                SharedPreferences.Editor editor = preferences.edit();
                //Puting the value false for loggedin
                editor.putBoolean(config.LOGGEDIN_SHARED_PREF, false);
                //Putting blank value to email
                editor.putString(config.USERNAME_SHARED_PREF, "");
                //Saving the sharedpreferences
                editor.apply();
                //Starting login activity
                Intent intent = new Intent(getContext(), LogInActivity.class);
                getActivity().finish();
                startActivity(intent);


            }
        });

        // init instance with rootView.findViewById here
    }


    private void getData() {
        SharedPreferences sp = getActivity().getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        user_id = sp.getString(config.USERNAME_SHARED_PREF,"");

        //loading = ProgressDialog.show(getActivity(),"Please wait...","Fetching...",false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,config.URL_DATA, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //loading.dismiss();
                showJSON(response);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(config.USERNAME_SHARED, user_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    private void showJSON(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");
            JSONObject collegeData = result.getJSONObject(0);


            nameDepart = collegeData.getString("name");
            address = collegeData.getString("address");
            phone = collegeData.getString("tel");
            email = collegeData.getString("email");
            typeDepart = collegeData.getString("typeDepart");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        txt_address.setText(address);
        txt_email.setText(email);
        txt_nameDepart.setText(nameDepart);
        txt_phone.setText(phone);

        // tvName.setText(firstname +" "+ lastname);
        // tvMail.setText(email);

        // tvName.setText(firstname+" "+lastname);
        // tvMail.setText(email);

    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_send, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_settings:
                Toast.makeText(getContext(), "click One", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }

}
