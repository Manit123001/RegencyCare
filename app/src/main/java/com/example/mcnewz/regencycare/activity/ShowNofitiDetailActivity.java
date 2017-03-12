package com.example.mcnewz.regencycare.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mcnewz.regencycare.R;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ShowNofitiDetailActivity extends AppCompatActivity {
    String id_name;

    String title,detai,image,acStatus,memToken,namef,namel,timedata;
    Button btnOk;
    // Date timedata;
    TextView txtSubject,txtName,txtTimer;
    TextView tvDexcription;
    ImageView ivImg;

    private ProgressDialog loading;
    String departId;
    String departStatus;
    String totalTitleDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_nofiti_detail);

        txtSubject = (TextView) findViewById(R.id.tvName);
        tvDexcription = (TextView) findViewById(R.id.tvDescription);
        ivImg = (ImageView) findViewById(R.id.ivImg);
        txtName = (TextView)findViewById(R.id.txtMember);
        txtTimer = (TextView)findViewById(R.id.txtTimer);


//        if (bundle != null) {
//            for (String key : bundle.keySet()) {
//                Object value = bundle.get(key);
//                Log.d("TTTGGG", "Key: " + key + " Value: " + value);
//            }
//            Object value2 = bundle.get("picture_url");
//            Log.d("TTTGGG"," Value: " + value2);
//        }

        if(getIntent().getStringExtra("id_title")== null){
            Bundle bundle = getIntent().getExtras();
            Object value2 = bundle.get("picture_url");
            id_name = value2.toString();
            Log.d("TTRR",value2.toString());
        }else{
            id_name = getIntent().getStringExtra("id_title");
        }




        btnOk = (Button)findViewById(R.id.btnAccecpt);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ShowNofitiDetailActivity.this,memToken,Toast.LENGTH_LONG).show();

                accept();
                btnOk.setEnabled(false);
                btnOk.setBackgroundColor(Color.RED);


            }
        });
        getData();

    }


    private void accept(){
        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://icareuserver.comscisau.com/icare/androidTest/upDateStatus.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ShowNofitiDetailActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id_ac", id_name);
                params.put("departId", departId);
                params.put("member_token",memToken);
                params.put("detail", totalTitleDetail);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void getData() {

        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://icareuserver.comscisau.com/icare/androidTest/selectDetail.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ShowNofitiDetailActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("member_id", id_name);
                params.put("department_id", "83");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");
            JSONObject collegeData = result.getJSONObject(0);
//            firstname = collegeData.getString(config.READ_FIRSTNAME);
//            lastname = collegeData.getString(config.READ_LASTNAME);
//            email   = collegeData.getString(config.READ_EMAIL);
            timedata = collegeData.optString("ac_create_date");
            title   = collegeData.getString("ac_subject_type");
            detai   = collegeData.getString("ac_detail");
            acStatus = collegeData.getString("ac_state");
            namef = collegeData.getString("member_f");
            namel = collegeData.getString("member_l");
            memToken = collegeData.getString("member_token");
            departId = collegeData.getString("department_id");
            departStatus = collegeData.getString("department_status");
            Log.d("TTTOOO",departStatus+departId);
            image = collegeData.getString("ac_photo");
            setImageUrl(image);
            totalTitleDetail =  "หน่วยงานกำลังดำเนินการให้ความช่วยเหลือ ณ. จุดเกิด"+" "+title;

        } catch (JSONException e) {
            e.printStackTrace();
        }


        // ลิ้งค์รูป
        // Toast.makeText(ShowNofitiDetailActivity.this,image,Toast.LENGTH_LONG).show();

        if(departStatus.trim().equals("0")){
            btnOk.setEnabled(false);
            btnOk.setBackgroundColor(Color.RED);
        }else {
            btnOk.setEnabled(true);
        }
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        txtName.setText(namef+" "+namel);
        txtTimer.setText(timedata);
        txtSubject.setText(title);
        tvDexcription.setText(detai);
    }

    public void setImageUrl (String url){
        Glide.with(getApplicationContext())
                .load(url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivImg);
    }

}
