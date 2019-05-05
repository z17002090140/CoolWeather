package com.example.admin.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CountyActivity extends AppCompatActivity {
    private TextView textView;
    private ListView listview;
    private  List<String> Weatherid=new ArrayList<>();
    private List<String> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.county_activity);
        Intent intent = getIntent();
        final int cid = intent.getIntExtra("cid",0);
        final int pid = intent.getIntExtra("pid",0);
        this.textView = (TextView)findViewById(R.id.textView);
        this.listview=(ListView)findViewById(R.id.listview);
        final ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,data);
        listview.setAdapter(adapter);
        this.listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CountyActivity.this,WeatherActivity.class);
                intent.putExtra("Weatherid",CountyActivity.this.Weatherid.get(position));
                intent.putExtra("pid",pid);
                intent.putExtra("cid",cid);
                startActivity(intent);
            }
        });
        String weatherUrl = "http://guolin.tech/api/china/"+pid+"/"+cid;
        HttpUtil.sendOkHttpRequest(weatherUrl,new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException{
                final String responseText = response.body().string();
                parseJSONWithJSONObject(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(responseText);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
    private void parseJSONWithJSONObject(String responseText) {
        JSONArray jsonArray= null;
        try {
            jsonArray = new JSONArray(responseText);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = null;
                jsonObject = jsonArray.getJSONObject(i);
                this.data.add(jsonObject.getString("name"));
                this.Weatherid.add(jsonObject.getString("weather_id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
