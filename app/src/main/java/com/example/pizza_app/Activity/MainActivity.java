package com.example.pizza_app.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.pizza_app.Interface.ApiService;
import com.example.pizza_app.Interface.ClientAPI;
import com.example.pizza_app.Modal.LoginModal.LoginModal;
import com.example.pizza_app.databinding.ActivityMainBinding;
import com.example.pizza_app.storeManager.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MainActivity extends AppCompatActivity  {
    private ActivityMainBinding binding;
    String email, password, fcmtoken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        FirebaseApp.initializeApp(this);
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e("fcmToken:=>",s);
                fcmtoken = s.toString();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Failed to get token", Toast.LENGTH_SHORT).show();
            }
        });
        binding.submitbtn.setOnClickListener(v-> {
            email =  binding.emailtxt.getText().toString();
            password =  binding.passwordtxt.getText().toString();
            callApi(email,password );
//            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//            startActivity(intent);
        });
    }

    public void callApi(String email, String password) {
        ApiService apiInterface = ClientAPI.getClient().create(ApiService.class);
        Log.d("dev_api check", "callApi: "+email +password +  fcmtoken);
        Call<LoginModal> userCall = apiInterface.login(email, password, fcmtoken);

        userCall.enqueue(new Callback<LoginModal>() {
            @Override
            public void onResponse(Call<LoginModal> call, Response<LoginModal> response) {
                Log.d("dev_api =>", "onResponse: "+response.raw());
                if (response.isSuccessful()) {
                    LoginModal loginResponse = response.body();
                    if (loginResponse != null) {
                        String token = loginResponse.getToken();
                        int userId = loginResponse.getData().getId();
                        String userName = loginResponse.getData().getName();
                        String userEmail = loginResponse.getData().getEmail();
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                        // Save token and user data to SharedPreferences
                        SharedPrefManager.getInstance(MainActivity.this).saveUser(token, userId, userName, userEmail);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModal> call, Throwable t) {
                Log.d("dev_api error =>", "onFailure: "+t.getMessage());
            }
        });
    }

}