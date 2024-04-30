package com.example.pizza_app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.EscPosPrinterCommands;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.dantsu.escposprinter.textparser.PrinterTextParserImg;
import com.example.pizza_app.Adapter.OrderDetailAdapter;
import com.example.pizza_app.Modal.DelivaryModal.Datum;
import com.example.pizza_app.Modal.DelivaryModal.OrderDetail;
import com.example.pizza_app.R;
import com.example.pizza_app.databinding.ActivityBillBinding;
import com.example.pizza_app.databinding.ActivityShowListBinding;
import com.google.gson.Gson;

import java.util.ArrayList;

public class BillActivity extends AppCompatActivity {
    private ActivityBillBinding binding;
    private static final int PERMISSION_BLUETOOTH = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBillBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        ArrayList<OrderDetail> orderDetailsList = getIntent().getParcelableArrayListExtra("ORDER_DATA");
        if (orderDetailsList != null) {
            String orderDetailsDatass = new Gson().toJson(orderDetailsList);
            Log.d("check_sdata", "onCreate: " + orderDetailsDatass);

            // Set up RecyclerView with LinearLayoutManager
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            binding.rcvview.setLayoutManager(layoutManager);


            OrderDetailAdapter adapter = new OrderDetailAdapter(orderDetailsList);
            binding.rcvview.setAdapter(adapter);
        }



        binding.billbtn.setOnClickListener(v -> {

//            binding.parentlayout123.setVisibility(View.INVISIBLE);

            int totalHeight = binding.scrolview.getChildAt(0).getHeight();
            int totalWidth = binding.scrolview.getChildAt(0).getWidth();

            Log.d("total_height_width", "onClick: " + 5000 + " " + 3000);

            //Bitmap b = getBitmapFromView(binding.parentlayout123, totalHeight, totalWidth);
            double ratio = (double) totalHeight / (double) totalWidth;
            int newWidtht = 104;
            int newHeight = (int) (104 * ratio);
            Log.d("total_height_width", "onClick: " + ratio);
            Log.d("total_height_width", "newHeight: " + newHeight);
            Log.d("total_height_width", "newWidtht: " + newWidtht);


            Bitmap b = getBitmapFromView(binding.scrolview, totalHeight, totalWidth);
            doPrint1( b);
            binding.img.setImageBitmap(b);
                Log.d("total_height_width", "onClick: " + b);
        });
    }

    @SuppressLint("MissingPermission")
    public void doPrint1(Bitmap bnp) {
        int targetWidth = 719;
        Bitmap rescaledBitmap = Bitmap.createScaledBitmap(
                bnp,
                targetWidth,
                Math.round(((float) bnp.getHeight()) * ((float) targetWidth) / ((float) bnp.getWidth())),
                true
        );

        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, this.PERMISSION_BLUETOOTH);

            } else {
                BluetoothConnection connection = BluetoothPrintersConnections.selectFirstPaired();
                if (connection != null) {
                    EscPosPrinter printer = new EscPosPrinter(connection, 203, 90f, 32);
                    final String text = "[L]<img>" + PrinterTextParserImg.bytesToHexadecimalString(EscPosPrinterCommands.bitmapToBytes(rescaledBitmap)) + "</img>\n";
                    printer.printFormattedText(text);
                } else {
                    Toast.makeText(this, "No printer was connected!", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Log.e("APP123123", "Can't print", e);
        }
    }

    public Bitmap getBitmapFromView(View view, int totalHeight, int totalWidth) {

        Bitmap returnedBitmap = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == this.PERMISSION_BLUETOOTH) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Bluetooth permission is granted, proceed with printing
                // You can put the code for Bluetooth printing here or call a method to handle it
            } else {
                // Bluetooth permission is denied
                Toast.makeText(this, "Bluetooth permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}