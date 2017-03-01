package com.appliepi.android.appplepie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    static SignupActivity sSignupActivity;
    private static final String TAG = "SignupActivity";

    EditText numberText;
    EditText nameText;
    EditText idText;
    EditText passwordText;
    Button signupBtn;
    TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        sSignupActivity = this;

        numberText = (EditText) findViewById(R.id.input_number);
        nameText = (EditText) findViewById(R.id.input_name);
        idText = (EditText) findViewById(R.id.input_id);
        passwordText = (EditText) findViewById(R.id.input_password);

        signupBtn = (Button) findViewById(R.id.btn_signup);
        loginLink = (TextView) findViewById(R.id.link_login);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void signUp() {
        Log.d(TAG, "Sign up");

        if(!validate()){
            onSignUpFailed();
            return;
        }

        signupBtn.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("계정 만드는 중...");
        progressDialog.show();

        String number = numberText.getText().toString();
        String name = nameText.getText().toString();
        String id = idText.getText().toString();
        String password = passwordText.getText().toString();

        new SignupSendPost(number, name, id, password).execute();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 2000);
    }

    public void onSignupSuccess(String token) {
        signupBtn.setEnabled(true);
        Intent intent = new Intent();
        intent.putExtra("TokenFromSignup", token);
        Log.d(TAG, token);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onSignUpFailed() {
        Toast.makeText(getBaseContext(), "계정 만들기 실패", Toast.LENGTH_LONG).show();

        signupBtn.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String number = numberText.getText().toString();
        String name = nameText.getText().toString();
        String id = idText.getText().toString();
        String password = passwordText.getText().toString();

        if(number.isEmpty()){
            numberText.setError("학번이 비어있습니다");
            valid = false;
        }else{
            numberText.setError(null);
        }

        if(name.isEmpty()){
            nameText.setError("이름이 비어있습니다");
            valid = false;
        }else{
            nameText.setError(null);
        }

        if(id.isEmpty()){
            idText.setError("아이디가 비어있습니다");
            valid = false;
        }else{
            idText.setError(null);
        }

        if(password.isEmpty()){
            passwordText.setError("비밀번호가 비어있습니다");
            valid = false;
        }else{
            passwordText.setError(null);
        }

        return valid;
    }

    public static SignupActivity getInstance(){
        return sSignupActivity;
    }
}
