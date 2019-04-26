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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CityActivity extends AppCompatActivity {

    private TextView textView;
    private  int[] cids=new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    private  String[] data={"","","","","","","","","","","","","","","",""};
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_activity);
        Intent intent = getIntent();
        final int pid = intent.getIntExtra("pid",0);
        this.textView=(TextView)findViewById(R.id.abc);
        this.listView=(ListView)findViewById(R.id.listview);
        final ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CityActivity.this,CountyActivity.class);
                intent.putExtra("cid",CityActivity.this.cids[position]);
                intent.putExtra("pid",pid);
                startActivity(intent);
            }
        });
        String weatherUrl = "http://guolin.tech/api/china/"+pid;
        HttpUtil.sendOkHttpRequest(weatherUrl,new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call,Response response) throws IOException{
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
                this.data[i]=jsonObject.getString("name");
                this.cids[i]=jsonObject.getInt("id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
