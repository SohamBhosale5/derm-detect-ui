package com.skin.skinapp2.ui.hospital;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.skin.skinapp2.R;

public class MapsFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng bothell = new LatLng(47.760113, -122.205444);
            LatLng one = new LatLng(47.770332, -122.223951);
            LatLng two = new LatLng(47.774616, -122.205770);
            LatLng three = new LatLng(47.825638, -122.182774);

            googleMap.addMarker(new MarkerOptions().position(bothell).title("Marker in " + " Evergreen Hospital").icon(BitmapDescriptorFactory.fromResource((R.drawable.hospital_64))));
            googleMap.addMarker(new MarkerOptions().position(one).title("Marker in " + one).icon(BitmapDescriptorFactory.fromResource((R.drawable.hospital_64))));
            googleMap.addMarker(new MarkerOptions().position(two).title("Marker in " + two).icon(BitmapDescriptorFactory.fromResource((R.drawable.hospital_64))));
            googleMap.addMarker(new MarkerOptions().position(three).title("Marker in " + three).icon(BitmapDescriptorFactory.fromResource((R.drawable.hospital_64))));
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(bothell));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom((new LatLng(47.760113, -122.205444)),15.0f));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}