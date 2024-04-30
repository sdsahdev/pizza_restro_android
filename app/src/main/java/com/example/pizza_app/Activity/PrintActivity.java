package com.example.pizza_app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.pizza_app.Interface.ApiService;
import com.example.pizza_app.Interface.ClientAPI;
import com.example.pizza_app.Modal.DelivaryModal.Datum;
import com.example.pizza_app.Modal.DelivaryModal.DelivaryModal;
import com.example.pizza_app.Modal.DelivaryModal.OrderDetail;
import com.example.pizza_app.Modal.StatusModal.ChnageStatusModal;
import com.example.pizza_app.databinding.ActivityPrintBinding;
import com.example.pizza_app.storeManager.SharedPrefManager;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrintActivity extends AppCompatActivity {
    private ActivityPrintBinding binding;

    String firstName, lastname, mobile, email, add1, add2, state, city, zip, countrycode, paymentmethod, total;
    int status, id;
    List<OrderDetail> orderDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrintBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Datum deliveryData = getIntent().getParcelableExtra("DELIVERY_DATA");
        if (deliveryData != null) {
            // Use the data to display in PrintActivity
            String deliveryDatass = new Gson().toJson(deliveryData);
            Log.d("check_sdata", "onCreate: " + deliveryDatass);
            id = deliveryData.getId();
            firstName = deliveryData.getFirstName();
            mobile = deliveryData.getMobile();
            total = deliveryData.getTotal();
            lastname = deliveryData.getLastName();
            email = deliveryData.getEmail();
            add1 = deliveryData.getAddress();
            add2 = deliveryData.getAddress2();
            state = deliveryData.getState();
            city = deliveryData.getCity();
            zip = deliveryData.getZip();
            countrycode = deliveryData.getCountryCode();
            paymentmethod = deliveryData.getPaymentMethod();
            status = deliveryData.getStatus();
            orderDetails = deliveryData.getOrderDetails();

            // Display the data in TextViews or other UI components
        }
        binding.billbtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, BillActivity.class);
            intent.putParcelableArrayListExtra("ORDER_DATA", (ArrayList<? extends Parcelable>) orderDetails);
            startActivity(intent);
        });
        binding.pre.setOnClickListener(v -> {
            int temstatus = status - 1;

            changeStatus(id, String.valueOf(temstatus));
        });
        binding.next.setOnClickListener(v -> {

            int temstatus = status + 1;
            changeStatus(id, String.valueOf(temstatus));
        });
        setdata();

    }

    private void changeStatus(int id, String temstatus) {
        Map<String, Object> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("status", temstatus);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        String token = SharedPrefManager.getInstance(this).getToken();
        Toast.makeText(this, "token" + token, Toast.LENGTH_SHORT).show();
//        String token = "5|Ux3mdGerr7dfhMuNHw7A4GvvS5YwOGIltZc7U6Ew05237ac5";
        ApiService apiInterface = ClientAPI.getClient(token).create(ApiService.class);
        Call<ChnageStatusModal> userCall = apiInterface.updateOrder(id, body);
        userCall.enqueue(new Callback<ChnageStatusModal>() {
            @Override
            public void onResponse(Call<ChnageStatusModal> call, Response<ChnageStatusModal> response) {
                Log.d("check_devs =>", "onResponse: " + response.raw());
                if (response.isSuccessful()) {
                    Log.d("check_devs =>", "onResponse: " + response.body().getMessage());
                    Intent i = new Intent(PrintActivity.this, HomeActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<ChnageStatusModal> call, Throwable t) {
                Toast.makeText(PrintActivity.this, "change status fail" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setdata() {
        binding.name.setText(firstName + " " + lastname);
        binding.mobile.setText(mobile);
        binding.email.setText(email);
        binding.add1.setText(add1);
        binding.add2.setText(add2);
        binding.state.setText(state);
        binding.city.setText(city);
        binding.zip.setText(zip);
    }


}