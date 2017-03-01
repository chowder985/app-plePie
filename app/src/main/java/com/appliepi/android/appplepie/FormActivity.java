package com.appliepi.android.appplepie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class FormActivity extends AppCompatActivity {

    TextView phoneNumber;
    TextView nickName;
    TextView schoolNumber;
    TextView aboutMe;
    TextView reason;
    TextView futureGoal;
    TextView determination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        phoneNumber = (TextView) findViewById(R.id.phone_number);
        nickName = (TextView) findViewById(R.id.nick_name);
        schoolNumber = (TextView) findViewById(R.id.schoolNum);

        aboutMe = (TextView) findViewById(R.id.about_me2);
        reason = (TextView) findViewById(R.id.reason2);
        futureGoal = (TextView) findViewById(R.id.goal2);
        determination = (TextView) findViewById(R.id.determination2);

        phoneNumber.setText(getIntent().getStringExtra("phone_number"));
        nickName.setText(getIntent().getStringExtra("nick_name"));
        schoolNumber.setText(getIntent().getStringExtra("school_number"));

        aboutMe.setText(getIntent().getStringExtra("about_me"));
        reason.setText(getIntent().getStringExtra("reason"));
        futureGoal.setText(getIntent().getStringExtra("future_goal"));
        determination.setText(getIntent().getStringExtra("determination"));
    }
}
