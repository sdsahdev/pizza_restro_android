package com.example.pizza_app.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizza_app.Modal.DelivaryModal.Datum;
import com.example.pizza_app.Modal.DelivaryModal.DelivaryModal;
import com.example.pizza_app.R;
import com.google.android.material.card.MaterialCardView;

import java.util.List;
public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.ViewHolder> {
    private List<Datum> deliveryList;
    private OnItemClickListener mListener;

    // Constructor
    public DeliveryAdapter(List<Datum> deliveryList) {
        this.deliveryList = deliveryList;
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textFirstName;
        public TextView textMobile;
        public TextView textTotal;
        MaterialCardView cardview;

        public ViewHolder(View view) {
            super(view);
            cardview = view.findViewById(R.id.cardview);
            textFirstName = view.findViewById(R.id.textFirstName);
            textMobile = view.findViewById(R.id.textMobile);
            textTotal = view.findViewById(R.id.textTotal);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_delivery, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Datum delivery = deliveryList.get(position);

        holder.textFirstName.setText(delivery.getFirstName() + ' ' + delivery.getLastName());
        holder.textMobile.setText(delivery.getMobile());
        holder.textTotal.setText(delivery.getTotal());

        // Set OnClickListener on MaterialCardView
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("clickk", "onClick: click ");
                if (mListener != null) {
                    Log.d("clickk", "onClick: click 2  ");

                    mListener.onItemClick(delivery);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return deliveryList.size();
    }

    // Interface for item click listener
    public interface OnItemClickListener {
        void onItemClick(Datum item);
    }

    // Method to set item click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}