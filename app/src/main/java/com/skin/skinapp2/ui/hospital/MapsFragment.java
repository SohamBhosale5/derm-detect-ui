package com.skin.skinapp2.ui.hospital;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.skin.skinapp2.MainApplication;
import com.skin.skinapp2.R;
import com.skin.skinapp2.SignInPage;
import com.skin.skinapp2.models.HosptialsNearby;
import com.skin.skinapp2.models.SignIn;
import com.skin.skinapp2.navigation_drawer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsFragment extends Fragment {
    ArrayList<Double> lat = new ArrayList<Double>();
    ArrayList<Double> longit = new ArrayList<Double>();
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
            System.out.println("Inside onMapReady");
            //System.out.println("Hospitals details"+hospitals);
            /*for(int i = 0; i < lat.size(); i++){
                System.out.println("Latitude and Longitude On Map Ready");
                System.out.println(lat.get(i) + " " + longit.get(i));
            }*/
            /*LatLng bothell = new LatLng(47.760113, -122.205444);
            LatLng one = new LatLng(47.770332, -122.223951);
            LatLng two = new LatLng(47.774616, -122.205770);
            LatLng three = new LatLng(47.825638, -122.182774);*/
            /*for(int i = 0; i < lat.size(); i++) {
                googleMap.addMarker(new MarkerOptions().position(new LatLng(lat.get(i), longit.get(i))).title("Marker in " + " Evergreen Hospital").icon(BitmapDescriptorFactory.fromResource((R.drawable.hospital_64))));
            }*/
            googleMap.addMarker(new MarkerOptions().position(new LatLng(47.762030, -122.208760)).title("The Everett Clinic Dermatology").icon(BitmapDescriptorFactory.fromResource((R.drawable.hospital_64))));
            googleMap.addMarker(new MarkerOptions().position(new LatLng(47.804390, -122.206790)).title("Susan Leu, M.D.").icon(BitmapDescriptorFactory.fromResource((R.drawable.hospital_64))));
            //googleMap.addMarker(new MarkerOptions().position(two).title("Marker in " + two).icon(BitmapDescriptorFactory.fromResource((R.drawable.hospital_64))));
            //googleMap.addMarker(new MarkerOptions().position(three).title("Marker in " + three).icon(BitmapDescriptorFactory.fromResource((R.drawable.hospital_64))));
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(bothell));
            //googleMap.addMarker(new MarkerOptions().position(new LatLng(lat.get(i), longit.get(i))).title("Marker in " + " Evergreen Hospital").icon(BitmapDescriptorFactory.fromResource((R.drawable.hospital_64))));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom((new LatLng(47.760113, -122.205444)),15.0f));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        System.out.println("Inside onCreatedView");
        HosptialsNearby hospitalsNearby = new HosptialsNearby();
        MainApplication.apiManager.findHospitals(hospitalsNearby, new Callback<List<HosptialsNearby>>() {
            @Override
            public void onResponse(Call<List<HosptialsNearby>> call, Response<List<HosptialsNearby>> response) {
                System.out.println("On Response Hosptials");
                List<HosptialsNearby> hospitals = response.body();
//                            sharedPreferences = getSharedPreferences("MyPref",MODE_PRIVATE);
//                            editor = sharedPreferences.edit();
                System.out.println("Response Hospitals body " + response.body().toString());
                if(response.isSuccessful() && hospitals != null){
                    //Toast.makeText(getContext(),"Maps Loading Successful!", Toast.LENGTH_LONG).show();
                    for(HosptialsNearby hosptial : hospitals){
                        String name = hosptial.getName();
                        String speciality = hosptial.getSpeciality();
                        String address =hosptial.getAddress();
                        String latitude = hosptial.getLatitude();
                        lat.add(Double.parseDouble(latitude));
                        String longitude = hosptial.getLongitude();
                        longit.add(Double.parseDouble(longitude));
                        String doctor_name = hosptial.getDoctor_name();
                        String contact  = hosptial.getContact();
                        System.out.println("Name ==========>>>"+name);
                        System.out.println("speciality ==========>>>"+speciality);
                        System.out.println("address ==========>>>"+address);
                        System.out.println("latitude ==========>>>"+latitude);
                        System.out.println("logitude ==========>>>"+longitude);
                        System.out.println("doctor_name ==========>>>"+doctor_name);
                    }
                    System.out.println("Hospitals details"+hospitals);
                    for(int i = 0; i < lat.size(); i++){
                        System.out.println("Latitude and Longitude");
                        System.out.println(lat.get(i) + " " + longit.get(i));
                    }

                } else {
                    System.out.println("Response  = " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<HosptialsNearby>> call, Throwable t) {
                System.out.println("On Failure " + t);

            }
        });
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