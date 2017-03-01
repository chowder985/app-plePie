package com.appliepi.android.appplepie;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
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

public class SendApplication extends AsyncTask<Object, Object, JSONObject> {

    private String phoneNum;
    private String intro;
    private String motive;
    private String target;
    private String last;
    private String token;

    public SendApplication(String token, String phoneNum, String intro, String motive, String target, String last){
        this.token = token;
        this.phoneNum = phoneNum;
        this.intro = intro;
        this.motive = motive;
        this.target = target;
        this.last = last;
    }

    protected void onPostExecute(JSONObject result){ //사실상 get
        super.onPostExecute(result);
        Log.d("result", result.toString());

        MainActivity.getInstance().runGetUserInfo();
    }

    @Override
    protected JSONObject doInBackground(Object... objects) {
        JSONObject content = executeClient();
        return content;
    }

    public JSONObject executeClient() {
        ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
        post.add(new BasicNameValuePair("number", phoneNum));
        post.add(new BasicNameValuePair("about_me", intro));
        post.add(new BasicNameValuePair("reason_for_application", motive));
        post.add(new BasicNameValuePair("future_goal", target));
        post.add(new BasicNameValuePair("determination", last));

        // 연결 HttpClient 객체 생성
        HttpClient client = new DefaultHttpClient();

        // 객체 연결 설정 부분, 연결 최대시간 등등
        HttpParams params = client.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 5000);
        HttpConnectionParams.setSoTimeout(params, 5000);

        // Post객체 생성
        HttpPost httpPost = new HttpPost("http://kafuuchino.moe:8282/applications/");
        httpPost.addHeader("Authorization", "Token "+token);
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
