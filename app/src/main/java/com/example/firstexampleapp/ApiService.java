package com.example.firstexampleapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("ClienteProveedor/ByNombre")
    Call<ApiResponse> getClienteByNombre(@Query("nombre") String nombre);
}
