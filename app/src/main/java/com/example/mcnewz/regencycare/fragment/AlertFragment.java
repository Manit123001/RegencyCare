package com.example.mcnewz.regencycare.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mcnewz.regencycare.R;
import com.example.mcnewz.regencycare.activity.DetailActivity;
import com.example.mcnewz.regencycare.adapter.AlertListAdapter;
import com.example.mcnewz.regencycare.dao.ItemCollectionDao;
import com.example.mcnewz.regencycare.dao.ItemDao;
import com.example.mcnewz.regencycare.manager.AlertListManager;
import com.example.mcnewz.regencycare.manager.HttpManager;
import com.example.mcnewz.regencycare.view.AlertItem;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class AlertFragment extends Fragment {
    private ListView listView;
    private AlertListAdapter listAdapter;

    public interface FragmentListener{
        void onPhotoItemClicked(ItemDao dao);
    }

    public AlertFragment() {
        super();
    }

    public static AlertFragment newInstance() {
        AlertFragment fragment = new AlertFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_alert, container, false);
        initInstances(rootView);
        return rootView;
    }
    private ItemCollectionDao dao;


    AlertListManager  alertListManager;
    private void initInstances(View rootView) {
        // init instance with rootView.findViewById here

        listView = (ListView) rootView.findViewById(R.id.listView);
        listAdapter = new AlertListAdapter();
        listView.setAdapter(listAdapter);

        alertListManager = new AlertListManager();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemDao dao = alertListManager.getDao().getData().get(position);
                FragmentListener listener =  (FragmentListener) getActivity();

                listener.onPhotoItemClicked(dao);
                // todo : send DAO
            }
        });
         refreshData();

    }

    private void refreshData() {

        Call<ItemCollectionDao> call = HttpManager.getInstance().getService().loadItemList();
        call.enqueue(new Callback<ItemCollectionDao>() {
            @Override
            public void onResponse(Call<ItemCollectionDao> call, Response<ItemCollectionDao> response) {
                if ( response.isSuccessful()){
                    ItemCollectionDao dao = response.body();

                    Toast.makeText(Contextor.getInstance().getContext(), "Complete"+dao.getData().get(0).getId(), Toast.LENGTH_SHORT).show();

                    listAdapter.setDao(dao);
                    listAdapter.notifyDataSetChanged();

                    alertListManager.setDao(dao);


                }else{
                    try {
                        Toast.makeText(getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<ItemCollectionDao> call, Throwable t) {
                Toast.makeText(Contextor.getInstance().getContext(), t.toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
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
