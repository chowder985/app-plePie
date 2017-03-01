package com.appliepi.android.appplepie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ApplicationFragment extends Fragment {

    EditText phoneNumber;
    EditText aboutMe;
    EditText reasonForApplication;
    EditText futureGoal;
    EditText determination;
    Button submit;

    private String phoneNum;
    private String intro;
    private String motive;
    private String target;
    private String last;

    public ApplicationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_application, container, false);
        // Inflate the layout for this fragment
        phoneNumber = (EditText) v.findViewById(R.id.phone_number);
        aboutMe = (EditText) v.findViewById(R.id.introduction);
        reasonForApplication = (EditText) v.findViewById(R.id.motive);
        futureGoal = (EditText) v.findViewById(R.id.target);
        determination = (EditText) v.findViewById(R.id.last_word);
        submit = (Button) v.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNum = phoneNumber.getText().toString();
                intro = aboutMe.getText().toString();
                motive = reasonForApplication.getText().toString();
                target = futureGoal.getText().toString();
                last = determination.getText().toString();

                Bundle args = getArguments();
                String token = args.getString("token");
                if(!phoneNum.isEmpty() && !intro.isEmpty() && !motive.isEmpty() && !target.isEmpty() && !last.isEmpty()) {
                    Log.d("Form", "" + token + " " + phoneNum + " " + intro + " " + motive + " " + target + " " + last);
                    new SendApplication(token, phoneNum, intro, motive, target, last).execute();
                } else {
                    Toast.makeText(getActivity(), "칸이 비어있습니다", Toast.LENGTH_LONG);
                }
            }
        });

        return v;
    }

}
