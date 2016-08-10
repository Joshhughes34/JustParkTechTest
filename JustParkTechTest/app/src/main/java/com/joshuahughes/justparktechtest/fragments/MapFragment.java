package com.joshuahughes.justparktechtest.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.joshuahughes.justparktechtest.R;
import com.joshuahughes.justparktechtest.models.Datum;
import com.joshuahughes.justparktechtest.models.RegionSearchResponse;

import java.util.List;


public class MapFragment extends Fragment implements OnMapReadyCallback {


    private MapView mapView;
    private GoogleMap googleMap;

    private OnFragmentInteractionListener mListener;

    public MapFragment() {
        // Required empty public constructor
    }


    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        mapView = (MapView) view.findViewById(R.id.googleMap);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume(){
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause(){
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy(){
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory(){
        mapView.onLowMemory();
        super.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap map){
        googleMap = map;

        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setCompassEnabled(false);

        googleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(51.5560241,-0.2817075) , 9.0f) );
    }


    public void AddResultsToMap(RegionSearchResponse regionSearchResponse){

        LatLng inputLatLng = new LatLng(
                regionSearchResponse.getMetadata().getLocationLat(),
                regionSearchResponse.getMetadata().getLocationLng()
        );
        googleMap.addMarker(new MarkerOptions()
                .position(inputLatLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
        );
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(inputLatLng, 14.0f));


        for(int i = 0; i < regionSearchResponse.getData().size(); i++){

            LatLng latLng = new LatLng(
                regionSearchResponse.getData().get(i).getLocation().getLatitude(),
                regionSearchResponse.getData().get(i).getLocation().getLongitude()
            );
            googleMap.addMarker(new MarkerOptions()
                .position(latLng)
            );
        }

    }

}
