package com.skin.skinapp2.ui.slideshow;

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

import com.skin.skinapp2.R;
import com.skin.skinapp2.databinding.FragmentSlideshowBinding;

import java.util.ArrayList;

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
        treatments = new ArrayList<Treatment>();
        for(int i = 0; i < 2; i++) {
            treatments.add(new Treatment("Immunotherapy", "Immunotherapy is a drug treatment that helps your immune system to fight cancer. Your body's disease-fighting immune system might not attack cancer because the cancer cells produce proteins that help them hide from the immune system cells. Immunotherapy works by interfering with that process."));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}