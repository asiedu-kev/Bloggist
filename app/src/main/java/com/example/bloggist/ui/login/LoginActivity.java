package com.example.bloggist.ui.login;

import android.app.Activity;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bloggist.HomeActivity;
import com.example.bloggist.R;
import com.example.bloggist.ui.login.LoginViewModel;
import com.example.bloggist.ui.login.LoginViewModelFactory;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private TextInputLayout username ;
    private TextInputLayout password ;
    private Button login ;
    private TextView forgot_password;
    private JSONObject user_data ;
    private LinearLayout erreur;
    public TextView register;
    private Context context = LoginActivity.this;
   /* SharedPreferences sharedPref = context.getSharedPreferences(
            "token", Context.MODE_PRIVATE);*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.activity_login_mail_input);
        password = findViewById(R.id.activity_login_password_input) ;
        login = findViewById(R.id.login) ;
        forgot_password = findViewById(R.id.forgotten_password_text);
         register = findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String postUrl = "https://bloggistapp.herokuapp.com/api/auth/login";
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                    JSONObject postData = new JSONObject();
                    try {
                        postData.put("email", username.getEditText().getText());
                        postData.put("password", password.getEditText().getText());

                        System.out.println(postData);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println("res" + response);
                            user_data = response;
                            SharedPreferences sharedPref = LoginActivity.this.getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            try {
                                editor.putString("userId", user_data.getString("userId"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                editor.putString("token", user_data.getString("token"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            editor.apply();
                            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(i);

                            // close this activity
                            finish();
                        }
                    }, new Response.ErrorListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Identiants Invalides,Réessayer", Toast.LENGTH_SHORT).show();
                        }
                    });

                    requestQueue.add(jsonObjectRequest);
                }
                });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        });
    }
}