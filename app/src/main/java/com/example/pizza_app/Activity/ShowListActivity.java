package com.example.pizza_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.pizza_app.Adapter.DeliveryAdapter;
import com.example.pizza_app.Interface.ApiService;
import com.example.pizza_app.Interface.ClientAPI;
import com.example.pizza_app.Modal.DelivaryModal.Datum;
import com.example.pizza_app.Modal.DelivaryModal.DelivaryModal;
import com.example.pizza_app.Modal.DelivaryModal.OrderDetail;
import com.example.pizza_app.Modal.LoginModal.LoginModal;
import com.example.pizza_app.R;
import com.example.pizza_app.databinding.ActivityPrintBinding;
import com.example.pizza_app.databinding.ActivityShowListBinding;
import com.example.pizza_app.storeManager.SharedPrefManager;
import com.google.gson.Gson;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowListActivity extends AppCompatActivity implements DeliveryAdapter.OnItemClickListener{
    private ActivityShowListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        // Set up RecyclerView with LinearLayoutManager
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        binding.recyclerview.setLayoutManager(layoutManager);
        String sessionId = getIntent().getStringExtra("value");

        getOrderList(sessionId);

}

    private void getOrderList(String type ) {
        binding.loading.setVisibility(View.VISIBLE);
        String token = SharedPrefManager.getInstance(this).getToken();
        Toast.makeText(this, "token" + token, Toast.LENGTH_SHORT).show();

//        String token = "5|Ux3mdGerr7dfhMuNHw7A4GvvS5YwOGIltZc7U6Ew05237ac5";
        ApiService apiInterface = ClientAPI.getClient(token).create(ApiService.class);
        Call<DelivaryModal> userCall = apiInterface.orderDetails(type);
        userCall.enqueue(new Callback<DelivaryModal>() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onResponse(Call<DelivaryModal> call, Response<DelivaryModal> response) {
                binding.loading.setVisibility(View.GONE);

                Log.d("check_dev ==> ", "onResponse: " + response.raw());
                if (response.isSuccessful()) {

//                    Log.d("check_dev ==> ", "onResponse: " + response.body().getData().get(0).getEmail());
                    // Inside onResponse method of your API call
                    if (response.isSuccessful()) {
                        DelivaryModal data = response.body();
                        // Assuming you have the RecyclerView already initialized as recyclerViewDelivery
                        String deliveryDatass = new Gson().toJson(data.getData());
                        Log.d("check_sdata", "onCreate: "+deliveryDatass);
                        DeliveryAdapter adapter = new DeliveryAdapter(data.getData());
                        adapter.setOnItemClickListener(ShowListActivity.this); // Assuming your activity implements OnItemClickListener
                        binding.recyclerview.setAdapter(adapter);
                        if (data.getData().size() == 0){
                            binding.nodata.setVisibility(View.VISIBLE);
                        }
                }
                }
            }

            @Override
            public void onFailure(Call<DelivaryModal> call, Throwable t) {
                binding.loading.setVisibility(View.GONE);
                Toast.makeText(ShowListActivity.this, "fail order" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(Datum item) {
        Log.d("clickk", "onClick: click 3");
        Intent intent = new Intent(this, PrintActivity.class);
        intent.putExtra("DELIVERY_DATA", (Parcelable) item);
        startActivity(intent);
    }
}