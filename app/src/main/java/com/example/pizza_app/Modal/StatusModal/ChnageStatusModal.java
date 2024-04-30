
package com.example.pizza_app.Modal.StatusModal;

import com.example.pizza_app.Modal.DelivaryModal.Datum;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChnageStatusModal {

    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
