package com.appliepi.android.appplepie;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilhoon on 28/02/2017.
 */

public class GetForms extends AsyncTask<String, String, String> {

    private String token;

    public GetForms(String token){
        this.token = token;
    }

    @Override
    protected String doInBackground(String... strings) {
        String content = executeClient();
        return content;
    }

    protected void onPostExecute(String result){ //사실상 get
        super.onPostExecute(result);
        //Log.d("result2", result);


        if(result != null) {

            MainActivity.getInstance().Data(result);

        }
    }

    public String executeClient() {
        ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();

        // 연결 HttpClient 객체 생성
        HttpClient client = new DefaultHttpClient();

        // 객체 연결 설정 부분, 연결 최대시간 등등
        HttpParams params = client.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 5000);
        HttpConnectionParams.setSoTimeout(params, 5000);

        // Post객체 생성
        HttpGet httpGet = new HttpGet("http://kafuuchino.moe:8282/applications/");
        httpGet.addHeader("Authorization", "Token " + token);

        String str = null;
        try {
            HttpResponse response = client.execute(httpGet);
            str = EntityUtils.toString(response.getEntity(), "UTF-8");
            Log.d("json", str);
            //json = new JSONObject(str);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}