package com.example.usama.homeautomation.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usama.homeautomation.API.LaravelAPI;
import com.example.usama.homeautomation.Models.User;
import com.example.usama.homeautomation.R;
import com.example.usama.homeautomation.RetrofitClient;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    TextView tv_email, tv_password;
    EditText et_email, et_password;
    Button btn_signup, btn_login;
    SharedPreferences sharedPreferences;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onStart() {
        super.onStart();

        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        if (!token.equals("")) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_email = findViewById(R.id.tv_email);
        tv_password = findViewById(R.id.tv_password);

        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);

        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_email.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                Retrofit retrofit = RetrofitClient.getRetrofit();
                final LaravelAPI service = retrofit.create(LaravelAPI.class);
                Call<User> login = service.loginUser(email, password);

                login.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Gson gson = new Gson();
                        User userDetail = response.body();
                        if (!(response.body() == null)) {
                            if (userDetail != null) {
                                String apiToken = userDetail.getApiToken();
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("token", apiToken);
                                editor.apply();
                            }

                            sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            et_password.getText().clear();
                            finish();
                        }
                        Log.i("responseLogin", "onResponse(): " + "call: " + call + " response: " + response);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Failed to log in", Toast.LENGTH_LONG).show();
                        Log.i("responseLogin", "onFailure(): " + "call: " + call + " t: " + t);
                    }
                });
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
//                super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Exiting...", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moveTaskToBack(true);
                doubleBackToExitPressedOnce = false;
            }
        }, 1000);
    }
}
