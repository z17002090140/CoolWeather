package com.example.admin.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class ProvinceActivity extends AppCompatActivity {
    private String currentlevel="province";
    private List<Integer> cids = new ArrayList<>();
    private List<Integer> pids = new ArrayList<>();
    private List<String> data = new ArrayList<>();
    private TextView textView;
    private ListView listview;
    private int pid = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.province_activity);
        this.textView = (TextView)findViewById(R.id.textView);
        this.listview=(ListView)findViewById(R.id.listview);
        final ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,data);
        listview.setAdapter(adapter);
        this.listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("点击了哪一个",ProvinceActivity.this.pids.get(position)+":"+ProvinceActivity.this.data.get(position));
                pid = ProvinceActivity.this.pids.get(position);
                currentlevel="city";
                getData(adapter);
//                Intent intent = new Intent(ProvinceActivity.this,CityActivity.class);
//                intent.putExtra("pid",ProvinceActivity.this.pids.get(position));
//                if(currentlevel=="city"){intent.putExtra("cid",cids.get(position));}
//                startActivity(intent);
            }
        });

    }

    private void getData(final ArrayAdapter<String> adapter) {
        String weatherUrl = currentlevel=="city"?"http://guolin.tech/api/china"+pid:"http://guolin.tech/api/china";
        HttpUtil.sendOkHttpRequest(weatherUrl,new Callback(){
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
            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    private void parseJSONWithJSONObject(String responseText) {
        JSONArray jsonArray= null;
        this.data.clear();
        try {
            jsonArray = new JSONArray(responseText);
            String[] result=new String[jsonArray.length()];
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = null;
                jsonObject = jsonArray.getJSONObject(i);
                this.data.add(jsonObject.getString("name"));
                if(currentlevel=="city"){
                    this.cids.add(jsonObject.getInt("id"));
                }else{
                    this.pids.add(jsonObject.getInt("id"));
                }

            }
//            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        return null;
    }
}
