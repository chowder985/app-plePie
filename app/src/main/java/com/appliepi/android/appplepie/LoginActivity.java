package com.appliepi.android.appplepie;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private int loggined = -1;
    private String tokenForUser;
    private String urlParameters;

    AQuery aq = new AQuery(this);
    EditText nameInput;
    EditText passwordInput;
    Button loginBtn;
    TextView signupText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nameInput = (EditText) findViewById(R.id.input_name);
        passwordInput = (EditText) findViewById(R.id.input_password);
        loginBtn = (Button) findViewById(R.id.btn_login);
        signupText = (TextView) findViewById(R.id.link_signup);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

    }

    public void login() {

        if(!validate()) {
            onLoginFailed();
            return;
        }

        loginBtn.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("로그인 중...");
        progressDialog.show();

        String name = nameInput.getText().toString();
        String password = passwordInput.getText().toString();

        SendPost sendPost = (SendPost) new SendPost(name, password).execute();
        tokenForUser = sendPost.getToken();



        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (loggined == 1)
                            onLoginSuccess();
                        else
                            onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 2000);
        /*boolean authentication = false;
        if(name.equals("michael985")){
            if(password.equals("1234")){
                authentication = true;
            }else{
                Toast.makeText(LoginActivity.this, "비밀번호가 틀렸습니다", Toast.LENGTH_SHORT);
            }
        }else{
            Toast.makeText(LoginActivity.this, "아이디가 틀렸습니다", Toast.LENGTH_SHORT);
        }

        final boolean finalAuthentication = authentication;*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
            }
        }
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if(name.isEmpty()){
            nameInput.setError("이름이 틀렸습니다");
            valid = false;
        } else {
            nameInput.setError(null);
        }

        if(password.isEmpty()){
            passwordInput.setError("비밀번호가 틀렸습니다");
            valid = false;
        } else {
            passwordInput.setError(null);
        }

        return valid;
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "로그인 실패", Toast.LENGTH_LONG).show();

        loginBtn.setEnabled(true);
    }

    public void onLoginSuccess() {
        loginBtn.setEnabled(true);

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("token", tokenForUser);
        startActivity(intent);
        finish();
    }

}
