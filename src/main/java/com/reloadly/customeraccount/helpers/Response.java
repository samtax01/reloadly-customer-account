package com.reloadly.customeraccount.helpers;

import lombok.Data;

@Data
public class Response<T> {
    private boolean status;
    private String message;
    private T data;


    public Response(T data, boolean status, String message) {
        this.status = status;
        this.message = message;
        this.data = data;
    }


    public static <T> Response<T> create(T data, boolean status, String message) {
        return new Response<>(data, status, message);
    }

    public static <T>  Response<T> success(T data) {
        return new Response<>(data, true, "Success");
    }

    public static <T> Response<T> errorMessage(String message) {
        return new Response<>(null, false, message);
    }
}
