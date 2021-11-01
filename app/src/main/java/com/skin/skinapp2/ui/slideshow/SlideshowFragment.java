package com.skin.skinapp2.ui.slideshow;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skin.skinapp2.MainApplication;
import com.skin.skinapp2.R;
import com.skin.skinapp2.databinding.FragmentSlideshowBinding;
import com.skin.skinapp2.models.Recommendations;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private FragmentSlideshowBinding binding;
    private View root;
    private ArrayList<Treatment> treatments;
    private RecyclerView rvtreatments;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_slideshow,container,false);
        rvtreatments = root.findViewById(R.id.recycle_list);
        treatmentData();
        showTreatment();

        return root;
    }

    private void showTreatment() {
        rvtreatments.setLayoutManager(new LinearLayoutManager(getContext()));
        TreatmentAdapter treatmentAdapter = new TreatmentAdapter(getContext());
        treatmentAdapter.setTreatmentList(treatments);
        rvtreatments.setAdapter(treatmentAdapter);
    }

    private void treatmentData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String disease = sharedPreferences.getString("DiseaseName","");
        System.out.println("Inside treatement");
        treatments = new ArrayList<Treatment>();
        Recommendations recommendations = new Recommendations(disease);
        MainApplication.apiManager.recommendations(recommendations, new Callback<List<Recommendations>>() {
            @Override
            public void onResponse(Call<List<Recommendations>> call, Response<List<Recommendations>> response) {
                System.out.println("On Response rec");
                List<Recommendations> recommendations1 = response.body();
//                            sharedPreferences = getSharedPreferences("MyPref",MODE_PRIVATE);
//                            editor = sharedPreferences.edit();
                System.out.println("Response rec body " + response.body().toString());
                if(response.isSuccessful() && recommendations1 != null){
                    //Toast.makeText(getContext(),"Maps Loading Successful!", Toast.LENGTH_LONG).show();
                    for(Recommendations rec : recommendations1){
                        String  disease = rec.getDisease();
                        String recommendation = rec.getRecommendations();
                        System.out.println("Disease details"+disease);
                        System.out.println("recommendation details"+recommendation);
                        // ArrayList<Treatment> treatments = new ArrayList<>();
                        treatments.add(new Treatment(disease, recommendation));

                        System.out.println("Disease details"+disease);
                        System.out.println("recommendation details"+recommendation);
                    }
                    showTreatment();
                    System.out.println("treatments ==>>"+treatments);



                } else {
                    System.out.println("Response  = " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Recommendations>> call, Throwable t) {
                System.out.println("On Failure " + t);

            }
        });
        /*treatments = new ArrayList<Treatment>();
        for(int i = 0; i < 2; i++) {
            treatments.add(new Treatment("Immunotherapy", "Immunotherapy is a drug treatment that helps your immune system to fight cancer. Your body's disease-fighting immune system might not attack cancer because the cancer cells produce proteins that help them hide from the immune system cells. Immunotherapy works by interfering with that process."));
        }*/

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}