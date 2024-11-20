package com.example.firstexampleapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Menu extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUser;
    private EditText editTextPassword;
    private ClienteContpaq cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        editTextUser = (EditText) findViewById(R.id.editTextUser);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        Button b = (Button) findViewById(R.id.button2);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        String user = editTextUser.getText().toString();

        tryLogIn(user, new LoginCallback() {
            @Override
            public void onLoginSuccess(ClienteContpaq clienteLogeado) {
                cliente = clienteLogeado;
                Toast.makeText(Menu.this, "Hola: " + cliente.crazonsocial, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLoginFailure(String errorMessage) {
                Toast.makeText(Menu.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void tryLogIn(String nombre, LoginCallback callback) {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        // Llamada al endpoint
        Call<ApiResponse> call = apiService.getClienteByNombre(nombre);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();

                    if (apiResponse.success && !apiResponse.data.isEmpty()) {
                        ClienteContpaq cliente = apiResponse.data.get(0); // Obtiene el primer cliente
                        callback.onLoginSuccess(cliente);
                    } else {
                        callback.onLoginFailure("Usuario no encontrado.");
                    }
                } else {
                    callback.onLoginFailure("Error en la solicitud: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                callback.onLoginFailure("Error de red: " + t.getMessage());
            }
        });
    }
}
