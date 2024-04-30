package com.example.pizza_app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pizza_app.R;
import com.example.pizza_app.databinding.ActivityHomeBinding;
import com.example.pizza_app.databinding.ActivityPrintBinding;

public class HomeActivity extends AppCompatActivity {
    public ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.panding.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ShowListActivity.class);
            intent.putExtra("value", "0");
            startActivity(intent);
        });
        binding.delivered.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ShowListActivity.class);
            intent.putExtra("value", "1");
            startActivity(intent);
        });
        binding.dispatched.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ShowListActivity.class);
            intent.putExtra("value", "2");
            startActivity(intent);
        });
        binding.accespted.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ShowListActivity.class);
            intent.putExtra("value", "3");
            startActivity(intent);
        });
        binding.rejected.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ShowListActivity.class);
            intent.putExtra("value", "4");
            startActivity(intent);
        });
    }
}