package com.juanconcha.secc_9_maps.Fragments;


import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.juanconcha.secc_9_maps.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.juanconcha.secc_9_maps.R.id.map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener{

    private View rootView;
    private MapView mapView;
    private GoogleMap gMap;


    private Geocoder geocoder;
    private List<Address> addresses;

    private MarkerOptions marker;


    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_map,container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView)rootView.findViewById(map);
        if(mapView != null){
           mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-33.4488897, -70.6692655);

        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

        //options marker
        marker = new MarkerOptions();
        marker.position(sydney);
        marker.title("Mi ubicación");
        marker.draggable(true);
        marker.snippet("");
        marker.icon(BitmapDescriptorFactory.fromResource(android.R.drawable.star_on));

        gMap.addMarker(marker);
        gMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        gMap.animateCamera(zoom);

        gMap.setOnMarkerDragListener(this);

        geocoder = new Geocoder(getContext(), Locale.getDefault());

    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        marker.hideInfoWindow();

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        double latitude = marker.getPosition().latitude;
        double longitude = marker.getPosition().longitude;

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }


        String address = addresses.get(0).getAddressLine(0);
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();

        marker.setSnippet(address + ", " + city);
        marker.showInfoWindow();

              /*  Toast.makeText(getContext(), "address: " + address + "\n" +
                        "city: " + city + "\n" +
                        "state: " + state + "\n" +
                        "country: " + country + "\n" +
                        "postalCode: " + postalCode + "\n"
                , Toast.LENGTH_SHORT).show();*/
    }

    }

