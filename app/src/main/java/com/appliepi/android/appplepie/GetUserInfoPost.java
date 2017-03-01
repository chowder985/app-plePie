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

public class GetUserInfoPost extends AsyncTask<Object, Object, JSONObject> {

    private String token;
    private String navHeaderString;
    private int id;
    private int navHeaderType = 0;

    public GetUserInfoPost(String token){
        this.token = token;
    }

    protected void onPostExecute(JSONObject result){ //사실상 get
        super.onPostExecute(result);
        Log.d("result", result.toString());
        if(result != null) {
            try {
                navHeaderString = result.getString("nick_name")+"님 안녕하세요";
                id = result.getInt("application");
                Log.d("GetUserInfo", navHeaderString);
                if(result.getString("nick_name").equals("admin"))
                    navHeaderType = 1;
                MainActivity.getInstance().setNavigationView(navHeaderString, navHeaderType, id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected JSONObject doInBackground(Object... objects) {
        JSONObject content = executeClient();
        return content;
    }

    public JSONObject executeClient() {
        ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();

        // 연결 HttpClient 객체 생성
        HttpClient client = new DefaultHttpClient();

        // 객체 연결 설정 부분, 연결 최대시간 등등
        HttpParams params = client.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 5000);
        HttpConnectionParams.setSoTimeout(params, 5000);

        // Post객체 생성
        HttpGet httpGet = new HttpGet("http://kafuuchino.moe:8282/users/whoami/");
        httpGet.addHeader("Authorization", "Token "+token);
        httpGet.addHeader("Content-Type", "application/json; charset=UTF-8");
        JSONObject json = null;

        try {
            HttpResponse response = client.execute(httpGet);
            String str = EntityUtils.toString(response.getEntity(), "UTF-8");
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
