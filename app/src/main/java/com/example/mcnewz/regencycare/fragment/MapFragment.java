package com.example.mcnewz.regencycare.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mcnewz.regencycare.R;
import com.example.mcnewz.regencycare.dao.ItemAcidentCollectionDao;
import com.example.mcnewz.regencycare.dao.ItemAcidentDao;
import com.example.mcnewz.regencycare.manager.HttpManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */


public class MapFragment extends  Fragment implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMarkerClickListener{
    private final String LOG = "LaurenceTestApp";

    // googleMap
    MapView mMapView;
    private GoogleMap mMap;
    boolean mapReady = false;

    // Location
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    LatLng latLng;

    // Marker
    Marker mCurrLocationMarker;
    Marker marker1;
    private ArrayList<Marker> mMarkerArray = new ArrayList<Marker>();
    private ArrayList<Marker> mMarkerArray2 = new ArrayList<Marker>();
    private ArrayList<Marker> mMarkerArray3 = new ArrayList<Marker>();
    private ArrayList<Marker> mMarkerArray4 = new ArrayList<Marker>();
    private Marker markerSearchLocation;
    ArrayList<Integer> localClick = new ArrayList<Integer>();
    ArrayList<Integer> localClickDepartment = new ArrayList<Integer>();
    // Button Sheet
    View parentView;
    AcidentsBottomSheetDialog bottomSheetDialog;
    View bottomSheetNewsAcidents;
    private BottomSheetBehavior<View> behavior;
    private ListView listView;



    public MapFragment() {
        super();
    }

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(this);

        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // init instance with rootView.findViewById here
        callBackItem();

    }


    private void callBackItem() {
        Call<ItemAcidentCollectionDao> call = HttpManager.getInstance().getService().loadAcidentItemList();
        call.enqueue(new Callback<ItemAcidentCollectionDao>() {
            @Override
            public void onResponse(Call<ItemAcidentCollectionDao> call, Response<ItemAcidentCollectionDao> response) {
                if(response.isSuccessful()){
                    String detailDao,subject;
                    ItemAcidentDao dao;
                    ItemAcidentCollectionDao collectionDao = response.body();

                    int sizeDao = collectionDao.getData().size();
                    //Toast.makeText(Contextor.getInstance().getContext(), sizeDao+dao.getData().get(0).getLat(), Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < sizeDao; i++){
                        dao = collectionDao.getData().get(i);

                        int id = dao.getId();
                        String latDao = dao.getLat();
                        String lngDao = dao.getLng();
                        detailDao = dao.getDetail();
                        subject = dao.getSubject();
                        int type = dao.getType();

                        int typeAc = 0;
                        latLng = new LatLng(Double.parseDouble(latDao), Double.parseDouble(lngDao));

                        // type Marker
                        if(type == 1){
                            typeAc = R.drawable.a1;
                            marker1 = getMarker(detailDao, subject, typeAc, latLng);
                            marker1.setTag(id);
                            mMarkerArray.add(marker1);

                        } else if (type == 2){
                            typeAc = R.drawable.a2;
                            marker1 = getMarker(detailDao, subject, typeAc, latLng);
                            marker1.setTag(id);
                            mMarkerArray2.add(marker1);

                        }else if (type == 3){
                            typeAc = R.drawable.a3;
                            marker1 = getMarker(detailDao, subject, typeAc, latLng);
                            marker1.setTag(id);
                            mMarkerArray3.add(marker1);

                        }else {
                            typeAc = R.drawable.a4;
                            marker1 = getMarker(detailDao, subject, typeAc, latLng);
                            marker1.setTag(id);
                            mMarkerArray4.add(marker1);
                        }

                        localClick.add(id);
                        // show marker
                    }

//                    marker1 = mMap.addMarker(new MarkerOptions()
//                            .position(BRISBANE)
//                            .title("Brisbane")
//                            .snippet("Marker Description")
//                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
//                    marker1.setTag(100);
                } else {
                    try {
                        Toast.makeText(Contextor.getInstance().getContext(),
                                response.errorBody().string(), Toast.LENGTH_LONG).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ItemAcidentCollectionDao> call, Throwable t) {
                Toast.makeText(Contextor.getInstance().getContext(), t.toString()+"error 555", Toast.LENGTH_LONG).show();
            }
        });
    }
    private Marker getMarker(String snippet, String title, int typeAc, LatLng latLng) {
        return mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(typeAc))
                .snippet(snippet)
                .title(title));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapReady = true;

        // For showing a move to my location button
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);

        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
        }
        // Marker Click Here
        mMap.setOnMarkerClickListener(this);
    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();
        String snippet = marker.getSnippet();
        int sizeInArray = localClick.size();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
             if(snippet.equals("Support")){


            }else{

                int indexArray = localClick.indexOf(clickCount);
                showBottomSheetDialog(clickCount , indexArray);
                showToast("//Click Tag "+ clickCount + "//IndexArray = " + indexArray+ "//size" + sizeInArray+"//position" + marker.getPosition() );
            }
        } else {
            Toast.makeText(Contextor.getInstance().getContext(), "Not found Tag", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void showBottomSheetDialog(int clickCount ,int indexArray) {
        bottomSheetDialog = AcidentsBottomSheetDialog.newInstance(clickCount, indexArray);
        bottomSheetDialog.show(getFragmentManager(), "bottomsheet");
    }

    private void showToast(String text){
        Toast.makeText(Contextor.getInstance().getContext(),
                text,
                Toast.LENGTH_SHORT).show();
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        updateLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(LOG, "Coinnect suspended");
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        Log.i(LOG, "Coinnect failed"+ result.getErrorCode());

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(LOG, location.toString());
        mLastLocation = location;

        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Toast.makeText(Contextor.getInstance().getContext(), ""+String.valueOf(mLastLocation.getLatitude())+String.valueOf(mLastLocation.getLongitude()), Toast.LENGTH_LONG).show();

        //Place current location marker
        latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        MarkerOptions options = new MarkerOptions()
                .title("Current Position")
                .draggable(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
                .position(latLng)
                .snippet("I am Here");

        mCurrLocationMarker = mMap.addMarker(options);
        // mCurrLocationMarker.setTag(1);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    private void updateLocation() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *1hronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(getContext(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }




}
