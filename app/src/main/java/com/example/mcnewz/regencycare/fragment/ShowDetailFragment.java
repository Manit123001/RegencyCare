package com.example.mcnewz.regencycare.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.mcnewz.regencycare.dao.ItemDao;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class ShowDetailFragment extends Fragment {
    TextView txtSubject,txtName,txtTimer;
    TextView tvDexcription;
    ImageView ivImg;
    Button btnAccept;
    private ProgressDialog loading;
    String statsu;
    ItemDao dao;
    private Button btnLocation;
    private String totalTitleDetail;
    private TextView tvTel;
    private TextView tvEmail;
    private TextView tvUser;

    public ShowDetailFragment() {
        super();
    }

    public static ShowDetailFragment newInstance(ItemDao dao) {
        ShowDetailFragment fragment = new ShowDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("dao",dao);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = getArguments().getParcelable("dao");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_show_detail, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // init instance with rootView.findViewById here
        txtSubject = (TextView) rootView.findViewById(R.id.tvName);
        tvDexcription = (TextView) rootView.findViewById(R.id.tvDescription);
        ivImg = (ImageView) rootView.findViewById(R.id.ivImg);
        txtName = (TextView) rootView.findViewById(R.id.txtMember);
        txtTimer = (TextView)rootView.findViewById(R.id.txtTimer);
        tvUser = (TextView)rootView.findViewById(R.id.tvUser);
        tvTel = (TextView)rootView.findViewById(R.id.tvTel);
        btnLocation = (Button) rootView.findViewById(R.id.btnLocation);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Toast.makeText(getContext(),dateFormat.format(dao.getCreate_date()),Toast.LENGTH_LONG).show();

        txtName.setText(dao.getMemberFrist()+" "+dao.getMemberLast());
        txtTimer.setText(dateFormat.format(dao.getCreate_date()));
        txtSubject.setText(dao.getSubject());
        tvDexcription.setText(dao.getDetail());
        statsu = String.valueOf(dao.getStatusAccept());
        tvUser.setText(dao.getMemberFrist()+" "+dao.getMemberLast());
        tvTel.setText(dao.getMemberTel());

        btnLocation .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("geo:0,0?q="+ dao.getLat()+","+dao.getLng()));
                startActivity(intent);
            }
        });

        Toast.makeText(getContext(),statsu,Toast.LENGTH_LONG).show();

        btnAccept = (Button)rootView.findViewById(R.id.btnAccecpt);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAccept();
                btnAccept.setEnabled(false);
                btnAccept.setBackgroundColor(Color.RED);
            }
        });



        if(dao.getDepartStatus() == 0){
            btnAccept.setEnabled(false);
            btnAccept.setBackgroundColor(Color.RED);
        }else {
            btnAccept.setEnabled(true);
        }

        Glide.with(ShowDetailFragment.this)
                .load(dao.getPhoto())

                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivImg);

        totalTitleDetail =  "หน่วยงานกำลังดำเนินการให้ความช่วยเหลือ ณ. จุดเกิด"+" "+dao.getSubject();

    }

    private void onAccept(){
        loading = ProgressDialog.show(getContext(),"Please wait...","Fetching...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://icareuserver.comscisau.com/icare/androidTest/upDateStatus.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id_ac",String.valueOf(dao.getId()));
                params.put("member_token",dao.getMemberToken());
                params.put("departId",String.valueOf(dao.getDepartId()));
                params.put("detail",totalTitleDetail);



                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
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
}
