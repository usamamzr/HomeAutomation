package com.example.usama.homeautomation.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usama.homeautomation.API.LaravelAPI;
import com.example.usama.homeautomation.Models.User;
import com.example.usama.homeautomation.R;
import com.example.usama.homeautomation.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity {

    EditText et_name, et_email, et_password, et_CPassword;
    Button btn_signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_CPassword = findViewById(R.id.et_CPassword);
        btn_signUp = findViewById(R.id.btn_signUp);

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_name.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String confirmPass = et_CPassword.getText().toString().trim();

                Retrofit retrofit = RetrofitClient.getRetrofit();
                final LaravelAPI service = retrofit.create(LaravelAPI.class);
                Call<User> signUp = service.registerUser(name, email, password, confirmPass);

                signUp.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Toast.makeText(SignUpActivity.this, "User registered!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                        Log.i("responseSignUp", "onResponse(): " + "call: " + call + " response: " + response);
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(SignUpActivity.this, "Failed to register user", Toast.LENGTH_LONG).show();
                        Log.i("responseSignUp", "onFailure(): " + "call: " + call + " t: " + t);
                    }
                });
            }
        });
    }
}
