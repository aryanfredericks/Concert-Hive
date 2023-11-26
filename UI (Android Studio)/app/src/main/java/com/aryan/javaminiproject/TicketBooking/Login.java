package com.aryan.javaminiproject.TicketBooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class Login extends AppCompatActivity {
    BlurView blurView ;
    TextView openSignUpActivity;
    TextInputEditText login_username,login_password;
    Button login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();

        blurView = findViewById(R.id.blurView);
        login_btn = findViewById(R.id.login_btn);
        openSignUpActivity = findViewById(R.id.openSignUpActvity);
        login_username = findViewById(R.id.login_username);
        login_password = findViewById(R.id.login_password);

        blurBackground();

        openSignUpActivity.setOnClickListener(v->{
            Intent in = new Intent(Login.this, SignUp.class);
            Login.this.startActivity(in);
            finish();
        });

        login_btn.setOnClickListener(vd->{
            if (login_username.getText().toString().isEmpty()||login_password.getText().toString().isEmpty()){
                login_username.setError("invalid username");
                login_password.setError("invalid password");
            }
            else{
                String username = login_username.getText().toString();
                String password = login_password.getText().toString();
                HashMap<String,String> param = new HashMap<>();
                param.put("username",username);
                param.put("password",password);
                RequestQueue rq= Volley.newRequestQueue(this);
                JsonObjectRequest jor = new JsonObjectRequest(
                        Request.Method.POST,
                        "http://192.168.29.115:8080/api/v1/user/login-user",
                        new JSONObject(param),
                        response -> {
                            Intent i = new Intent(Login.this,HomeActivity.class);
                            try {
                                i.putExtra("username",response.getString("username"));
                                i.putExtra("email",response.getString("email"));
                                i.putExtra("password",response.getString("password"));
                                i.putExtra("id",response.getInt("id"));
                                Login.this.startActivity(i);
                                finish();
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        },
                        error->{
                            if (error!=null && error.networkResponse!=null){
                                int code = error.networkResponse.statusCode;
                                if (code ==400){
                                    String response = new String(error.networkResponse.data);
                                    if (response.equals("User Doesnt Exist")){
                                        Toast.makeText(this, "User Doesnt Exist", Toast.LENGTH_SHORT).show();
                                    } else if (response.equals("Incorrect Password")) {
                                        login_password.setError("Incorrect Password");
                                    }
                                }
                            }
                            else{
                                Log.d("errors", error.getMessage());
                                Toast.makeText(this, "some error occurred", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                login_username.setText("");
                login_password.setText("");
                rq.add(jor);
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