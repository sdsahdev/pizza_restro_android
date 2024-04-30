package com.example.pizza_app.Interface;

import com.example.pizza_app.Modal.DelivaryModal.DelivaryModal;
import com.example.pizza_app.Modal.DelivaryModal.OrderDetail;
import com.example.pizza_app.Modal.LoginModal.LoginModal;
import com.example.pizza_app.Modal.StatusModal.ChnageStatusModal;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("0api/login")
     Call<LoginModal> login(
            @Query("email") String email,
            @Query("password") String password,
            @Query("fcm") String fcm
    );
      @GET("api/orders")
     Call<DelivaryModal> orderDetails(
            @Query("status") String status
    );
    @POST("api/orders/update/{orderId}")
    Call<ChnageStatusModal> updateOrder(@Path("orderId") int orderId,@Body RequestBody body);

}
