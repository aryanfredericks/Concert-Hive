package com.aryan.javaminiproject.TicketBooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class SignUp extends AppCompatActivity {
    TextView openLoginActvity;
    BlurView blurView;
    Button signup_btn;
    TextInputEditText signup_name,signup_email,signup_password,signup_confirm_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Intent intent = getIntent();

        blurView = findViewById(R.id.blurView);
        signup_name = findViewById(R.id.signup_name);
        signup_email = findViewById(R.id.signup_email);
        signup_password = findViewById(R.id.signup_password);
        signup_confirm_password = findViewById(R.id.signup_confirm_password);
        signup_btn = findViewById(R.id.signup_btn);
        blurBackground();

        openLoginActvity= findViewById(R.id.openLoginActvity);
        openLoginActvity.setOnClickListener(V->{
            Intent in = new Intent(SignUp.this, Login.class);
            SignUp.this.startActivity(in);
            finish();
        });

        signup_btn.setOnClickListener(view->{
            if (signup_name.getText().toString().isEmpty()||signup_email.getText().toString().isEmpty()||signup_password.getText().toString().isEmpty()||signup_confirm_password.getText().toString().isEmpty()){
                signup_password.setError("invalid");
                signup_confirm_password.setError("invalid");
                signup_name.setError("invalid");
                signup_email.setError("invalid");
            }
            //Performing Validation Checks For Password
            if (!signup_password.getText().toString().equals(signup_confirm_password.getText().toString())){
                signup_password.setError("Password Does Not Match");
                signup_confirm_password.setError("Password Does Not Match");
            }
            else{
                String username = signup_name.getText().toString();
                String email = signup_email.getText().toString();
                String password = signup_password.getText().toString();
                String url = "http://192.168.29.115:8080/api/v1/user/register-user";
                RequestQueue rq = Volley.newRequestQueue(this);
                StringRequest request = new StringRequest(
                        Request.Method.POST,
                        url,
                        response->{
                            signup_name.setText("");
                            signup_email.setText("");
                            signup_password.setText("");
                            signup_confirm_password.setText("");
                            Toast.makeText(this, "You are now registered.Please Login", Toast.LENGTH_LONG).show();

                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                Intent in = new Intent(SignUp.this, Login.class);
                                SignUp.this.startActivity(in);
                                finish();
                            },2000);

                        },
                        error->{
                            if (error != null && error.networkResponse != null) {
                                int statusCode = error.networkResponse.statusCode;
                                if (statusCode ==400){
                                    String responseBody = new String(error.networkResponse.data);
                                    if (responseBody.equals("account with that email already exists")){
                                        signup_email.setError("Email Exists");
                                    }
                                    else if (responseBody.equalsIgnoreCase("username taken")){
                                        signup_name.setError("username not available");
                                    }
                                }
                            }
                            else{
                                Log.d("myapp", "springboot server might need restart");
                            }
                        }
                ){
                    @NonNull
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> map = new HashMap<>();
                        map.put("username",username);
                        map.put("email",email);
                        map.put("password",password);
                        return map;
                    }
                };
                rq.add(request);
            }
        });
    }

    public void blurBackground() {
        float radius = 10f;
        View decorView = getWindow().getDecorView();
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();
        blurView.setupWith(rootView, new RenderScriptBlur(this))
                .setFrameClearDrawable(windowBackground)
                .setBlurRadius(radius);

    }
}