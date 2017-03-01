package com.appliepi.android.appplepie;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
 * A simple {@link Fragment} subclass.
 */
public class SeeFragment extends Fragment {

    private RecyclerView mRVFishPrice;
    private myAdapter mAdapter;

    public SeeFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_see, container, false);
        // Inflate the layout for this fragment
        Bundle args = getArguments();
        int admin = args.getInt("isAdmin");
        String token = args.getString("token");
        String result = args.getString("result");

        //Log.d("result", result);
        List<DataForms> data = new ArrayList<>();

        if(admin == 1) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json_data = jsonArray.getJSONObject(i);
                    DataForms dataForms = new DataForms();

                    JSONObject temp = json_data.getJSONObject("user");
                    dataForms.applicationId = temp.getString("id");
                    dataForms.nick_name = temp.getString("nick_name");
                    dataForms.schoolNumber = temp.getString("student_id");

                    dataForms.phoneNumber = json_data.getString("number");
                    dataForms.about_me = json_data.getString("about_me");
                    dataForms.reason_for_application = json_data.getString("reason_for_application");
                    dataForms.future_goal = json_data.getString("future_goal");
                    dataForms.determination = json_data.getString("determination");

                    Log.d("json" + i, dataForms.schoolNumber);
                    data.add(dataForms);
                }

                mRVFishPrice = (RecyclerView) v.findViewById(R.id.recycler_view);
                mAdapter = new myAdapter(data, getContext());
                mRVFishPrice.setAdapter(mAdapter);
                mRVFishPrice.setLayoutManager(new LinearLayoutManager(getActivity()));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject temp = jsonObject.getJSONObject("user");
                DataForms dataForms = new DataForms();

                dataForms.applicationId = temp.getString("id");
                dataForms.nick_name = temp.getString("nick_name");
                dataForms.schoolNumber = temp.getString("student_id");

                dataForms.phoneNumber = jsonObject.getString("number");
                dataForms.about_me = jsonObject.getString("about_me");
                dataForms.reason_for_application = jsonObject.getString("reason_for_application");
                dataForms.future_goal = jsonObject.getString("future_goal");
                dataForms.determination = jsonObject.getString("determination");

                Log.d("json", dataForms.schoolNumber);
                data.add(dataForms);

                mRVFishPrice = (RecyclerView) v.findViewById(R.id.recycler_view);
                mAdapter = new myAdapter(data, getContext());
                mRVFishPrice.setAdapter(mAdapter);
                mRVFishPrice.setLayoutManager(new LinearLayoutManager(getActivity()));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return v;


    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textFishName;
        TextView textSize;
        TextView textType;
        List<DataForms> mDataFormses = new ArrayList<DataForms>();
        Context ctx;
        private DataForms df;

        public ViewHolder(View itemView, Context ctx, List<DataForms> df) {
            super(itemView);
            this.mDataFormses = df;
            this.ctx = ctx;
            itemView.setOnClickListener(this);
            textFishName = (TextView) itemView.findViewById(R.id.textFishName);
            textSize = (TextView) itemView.findViewById(R.id.textSize);
            textType = (TextView) itemView.findViewById(R.id.textType);
        }

        public void bindData(DataForms df){
            this.df = df;
            textFishName.setText(this.df.getPhoneNumber());
            textSize.setText(this.df.getNick_name());
            textType.setText(this.df.getSchoolNumber());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            DataForms df = this.mDataFormses.get(position);
            Intent intent = new Intent(this.ctx, FormActivity.class);
            intent.putExtra("phone_number", df.getPhoneNumber());
            intent.putExtra("nick_name", df.getNick_name());
            intent.putExtra("school_number", df.getSchoolNumber());
            intent.putExtra("about_me", df.getAbout_me());
            intent.putExtra("reason", df.getReason_for_application());
            intent.putExtra("future_goal", df.getFuture_goal());
            intent.putExtra("determination", df.getDetermination());
            this.ctx.startActivity(intent);
        }
    }

    public class myAdapter extends RecyclerView.Adapter<ViewHolder> {

        List<DataForms> dfs = new ArrayList<DataForms>();
        Context ctx;

        public myAdapter(List<DataForms> df, Context ctx) {
            this.dfs = df;
            this.ctx = ctx;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.container_form, parent, false);
            return new ViewHolder(view, ctx, dfs);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            DataForms df = dfs.get(position);
            holder.bindData(df);
        }

        @Override
        public int getItemCount() {
            return dfs.size();
        }
    }
    /**
     * Created by ilhoon on 28/02/2017.
     */


}
