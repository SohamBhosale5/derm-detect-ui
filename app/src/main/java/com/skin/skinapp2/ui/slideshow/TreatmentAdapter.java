package com.skin.skinapp2.ui.slideshow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skin.skinapp2.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TreatmentAdapter extends RecyclerView.Adapter<TreatmentAdapter.TreatmentListViewFolder> {
    Context context;
    private ArrayList<Treatment> treatments;

    public TreatmentAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Treatment> getTreatmentList(){
        return treatments;
    }

    public void setTreatmentList(ArrayList<Treatment> treatment){
        this.treatments = treatment;
    }
    @NonNull
    @NotNull
    @Override
    public TreatmentAdapter.TreatmentListViewFolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_view,parent,false);
        return new TreatmentListViewFolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull TreatmentAdapter.TreatmentListViewFolder holder, int position) {
        Treatment treatment = getTreatmentList().get(position);
        holder.label.setText(treatment.getTreatmentName());
        holder.description.setText(treatment.getDescription());
    }

    @Override
    public int getItemCount() {
        return getTreatmentList().size();
    }

    public class TreatmentListViewFolder extends RecyclerView.ViewHolder {
        public TextView label;
        public TextView description;
        public TreatmentListViewFolder(@NonNull @NotNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.label);
            description = itemView.findViewById(R.id.description);
        }
    }
}
