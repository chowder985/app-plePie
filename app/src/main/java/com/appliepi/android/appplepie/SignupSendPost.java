package com.appliepi.android.appplepie;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ilhoon on 27/02/2017.
 */

public class SignupSendPost extends AsyncTask<Object, Object, JSONObject> {

    private String number;
    private String name;
    private String id;
    private String password;
    private boolean signupped = false;
    private String token = "Token";

    public SignupSendPost(String number, String name, String id, String password){
        this.number = number;
        this.name = name;
        this.id = id;
        this.password = password;
    }

    protected void onPostExecute(JSONObject result) {
        // 모두 작업을 마치고 실행할 일 (메소드 등등)
        super.onPostExecute(result);
        if(result != null){
            if(result.has("token")){
                try {
                    token = result.getString("token");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                signupped = true;
            }else{
                signupped = false;
            }
        }

        Log.d("Token", token);

        if (signupped)
            SignupActivity.getInstance().onSignupSuccess(token);
        else
            SignupActivity.getInstance().onSignUpFailed();
    }

    @Override
    protected JSONObject doInBackground(Object... objects) {
        JSONObject content = executeClient();
        return content;
    }

    public JSONObject executeClient() {
        ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
        post.add(new BasicNameValuePair("username", id));
        post.add(new BasicNameValuePair("password", password));
        post.add(new BasicNameValuePair("student_id", number));
        post.add(new BasicNameValuePair("nick_name", name));

        // 연결 HttpClient 객체 생성
        HttpClient client = new DefaultHttpClient();

        // 객체 연결 설정 부분, 연결 최대시간 등등
        HttpParams params = client.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 5000);
        HttpConnectionParams.setSoTimeout(params, 5000);

        // Post객체 생성
        HttpPost httpPost = new HttpPost("http://kafuuchino.moe:8282/users/");
        JSONObject json = null;

        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(post, "UTF-8");
            httpPost.setEntity(entity);
            HttpResponse response = client.execute(httpPost);
            String str = EntityUtils.toString(response.getEntity());
            json = new JSONObject(str);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
