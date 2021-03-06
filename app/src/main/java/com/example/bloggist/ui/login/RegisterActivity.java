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
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bloggist.HomeActivity;
import com.example.bloggist.R;
import com.example.bloggist.ui.login.LoginViewModel;
import com.example.bloggist.ui.login.LoginViewModelFactory;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    private TextInputLayout username ;
    private TextInputLayout firstname ;
    private TextInputLayout lastname ;
    private TextInputLayout password ;
    private TextInputLayout confirm_password ;
    private Button register ;
    private TextView login;
    private JSONObject user_data ;
    private LinearLayout erreur;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstname = findViewById(R.id.activity_register_firstname_input);
        lastname = findViewById(R.id.activity_register_lastname_input);
        username = findViewById(R.id.activity_register_mail_input);
        password = findViewById(R.id.activity_register_password_input);
        confirm_password = findViewById(R.id.activity_register_password_confirm_input);
        register = findViewById(R.id.activity_register_login_button);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postUrl = "https://bloggistapp.herokuapp.com/api/auth/signup";
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                JSONObject postData = new JSONObject();
                try {
                    postData.put("email", username.getEditText().getText());
                    postData.put("password", password.getEditText().getText());
                    postData.put("name", firstname.getEditText().getText());
                    postData.put("lastname", lastname.getEditText().getText());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        user_data = response ;
                        Intent i = new Intent(RegisterActivity.this, HomeActivity.class);
                        startActivity(i);

                        // close this activity
                        finish();
                        /*SharedPreferences sharedPref = RegisterActivity.this.getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(getString(R.string.preference_file_key), "1234");
                        editor.apply()*/;}
                }, new Response.ErrorListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Vos mots de passe ne correspondent pas",Toast.LENGTH_SHORT) .show();
                    }
                });

                requestQueue.add(jsonObjectRequest);

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        });
    }
}